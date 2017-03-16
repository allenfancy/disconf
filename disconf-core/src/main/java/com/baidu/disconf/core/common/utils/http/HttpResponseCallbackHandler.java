package com.baidu.disconf.core.common.utils.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
/**
 * 
 * @author allen 
 * HttpResponse回调处理器
 * @param <T>
 */
public interface HttpResponseCallbackHandler<T> {

    T handleResponse(String requestBody, HttpEntity entity) throws IOException;
}
