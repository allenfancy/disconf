package com.baidu.disconf.client.scan;

import java.util.List;

/**
 * @note 扫码
 * @author liaoqiqi
 * @version 2014-7-29
 */
public interface ScanMgr {

    /**
     * @throws Exception
     */
    void firstScan(List<String> packageNameLit) throws Exception;

    /**
     * @throws Exception
     */
    void secondScan() throws Exception;

    /**
     * reloadable for specify files
     * 从新加载指定的文件
     * @throws Exception
     */
    void reloadableScan(String fileName) throws Exception;
}
