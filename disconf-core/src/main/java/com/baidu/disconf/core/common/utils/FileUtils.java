package com.baidu.disconf.core.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作的方法集
 *
 * @author liaoqiqi
 * @version 2014-8-20
 */
public final class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {

    }

    /**
     * 关闭写文件流
     */
    public static final void closeWriter(Writer w) {
        if (w != null) {
            try {
                w.close();
            } catch (Exception e) {
                logger.warn(e.toString());
            }
        }
    }

    /**
     * 关闭读文件流
     */
    public static final void closeReader(Reader r) {
        if (r != null) {
            try {
                r.close();
            } catch (Exception e) {
                logger.warn(e.toString());
            }
        }
    }

    /**
     * 关闭文件流
     */
    public static final void closeOutputStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (Exception e) {
                logger.warn(e.toString());
            }
        }
    }

    /**
     * 关闭文件流
     */
    public static final void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception e) {
                logger.warn(e.toString());
            }
        }
    }

    /**
     *
     * @param oldName
     * @param newName
     *
     * @return
     */
    public static boolean isFileUpdate(String oldName, String newName) {
        return isFileEqual(new File(oldName), new File(newName));
    }

    /**
     *
     * @param oldFile
     * @param newFile
     *
     * @return
     */
    public static boolean isFileEqual(File oldFile, File newFile) {
        try {
            return contentEquals(oldFile, newFile);
        } catch (IOException e) {
            logger.warn(e.toString());
            return false;
        }
    }
}
