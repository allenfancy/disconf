package com.baidu.disconf.core.common.utils.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient的工具类
 */
public class HttpClientUtil {

    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 连接器
     */
    protected static CloseableHttpClient httpclient;
    /**
     * 初始化httpclient对象
     */
    private static void buildHttpClient() {
    	/**配置连接超时和Socket超时*/
        RequestConfig globalConfig =
                RequestConfig.custom().setConnectTimeout(5000)
                        .setSocketTimeout(5000).build();
        /**设置超时策略*/
        CloseableHttpClient httpclient =
                HttpClients.custom().setKeepAliveStrategy(new HttpClientKeepAliveStrategy())
                        .setDefaultRequestConfig(globalConfig).build();
        HttpClientUtil.httpclient = httpclient;
    }

    /**
     * 处理具体代理请求执行, 入口方法
     * @param HttpRequestBase  request
     * @param HttpResonseCallbackHanlder responseHandler
     * @throws Exception
     */
    public static <T> T execute(HttpRequestBase request, HttpResponseCallbackHandler<T> responseHandler)
            throws Exception {
    	/**响应*/
        CloseableHttpResponse httpclientResponse = null;
        try {
            if (LOGGER.isDebugEnabled()) {
            	/**打印所有的头部信息*/
                Header[] headers = request.getAllHeaders();
                for (Header header : headers) {
                    LOGGER.debug("request: " + header.getName() + "\t" + header.getValue());
                }
            }
            /**执行*/
            httpclientResponse = httpclient.execute(request);
            
            if (LOGGER.isDebugEnabled()) {
            	/***打印所有的响应头部*/
                for (Header header : httpclientResponse.getAllHeaders()) {
                    LOGGER.debug("response header: {}\t{}", header.getName(), header.getValue());
                }
            }
            // 填充状态码
            int statusCode = httpclientResponse.getStatusLine().getStatusCode();
            String requestBody = null;
            /**TODO：是否需要*/
            if (request instanceof HttpEntityEnclosingRequestBase) {
                HttpEntity requestEntity = ((HttpEntityEnclosingRequestBase) request).getEntity();
                if (requestEntity != null) {
                    requestBody = EntityUtils.toString(requestEntity);
                }
            }

            LOGGER.info("execute http request [{}], status code [{}]", requestBody, statusCode);

            if (statusCode != 200) {
                throw new Exception("execute  request failed [" + requestBody + "], statusCode [" + statusCode
                        + "]");
            }

            // 处理响应体
            HttpEntity entity = httpclientResponse.getEntity();
            if (entity != null && responseHandler != null) {
                return responseHandler.handleResponse(requestBody, entity);
            } else {
                LOGGER.info("execute response [{}], response empty", requestBody);
            }
            return null;
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpclientResponse != null) {
                try {
                    httpclientResponse.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * @return void
     *
     * @Description：关闭
     * @author liaoqiqi
     * @date 2013-6-16
     */
    public static void close() {
        if (httpclient != null) {
            try {
                httpclient.close();
                httpclient = null;
            } catch (IOException e) {
            }
        }
    }
    public static void init() {
        buildHttpClient();
    }

}
