package com.mt.log;

import com.mt.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.util.Date;

public class MTLog {

    public static Logger logger;

    public static void setLogger(Logger logger) {
        MTLog.logger = logger;
    }

    public static void print(Object msg) {
        System.err.println(msg);
    }

    public static void error(final String message) {
        if (MTLog.logger != null) {
            MTLog.logger.error(message);
        } else {
            System.err.printf("ERROR: %s\n", message);
        }
    }

    public static void error(final String message, final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.error(message, t);
        } else {
            System.err.printf("ERROR: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void error(final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.error(t.getMessage(), t);
        } else {
            System.err.printf("ERROR: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void warn(final String message) {
        if (MTLog.logger != null) {
            MTLog.logger.warn(message);
        } else {
            System.err.printf("WARN: %s\n", message);
        }
    }

    public static void warn(final String message, final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.warn(message, t);
        } else {
            System.err.printf("WARN: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void warn(final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.warn(t.getMessage(), t);
        } else {
            System.err.printf("WARN: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void info(final String message) {
        if (MTLog.logger != null) {
            MTLog.logger.info(message);
        } else {
            System.err.printf("INFO: %s\n", message);
        }
    }

    public static void info(final String message, final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.info(message, t);
        } else {
            System.err.printf("INFO: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void info(final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.info(t.getMessage(), t);
        } else {
            System.err.printf("INFO: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void debug(final String message) {
        if (MTLog.logger != null) {
            MTLog.logger.debug(message);
        } else {
            System.err.printf("DEBUG: %s\n", message);
        }
    }

    public static void debug(final String message, final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.debug(message, t);
        } else {
            System.err.printf("DEBUG: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void debug(final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.debug(t.getMessage(), t);
        } else {
            System.err.printf("DEBUG: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void trace(final String message) {
        if (MTLog.logger != null) {
            MTLog.logger.trace(message);
        } else {
            System.err.printf("TRACE: %s\n", message);
        }
    }

    public static void trace(final String message, final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.trace(message, t);
        } else {
            System.err.printf("TRACE: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void trace(final Throwable t) {
        if (MTLog.logger != null) {
            MTLog.logger.trace(t.getMessage(), t);
        } else {
            System.err.printf("TRACE: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    public static void procLogFile() {
        logger.info("扫描日志文件");
        File logDir = new File("./logs");
        if(logDir.exists()){
            File [] files = logDir.listFiles();
            Date delDate = DateUtil.getDateAfter(DateUtil.getFormattedDate(new Date()), -20);
            logger.info("DELDATE:{}",DateUtil.getDateString(delDate,"yyyy-MM-dd"));
            for(File f:files){
                String fn = f.getName();
                logger.info("扫描:{}", fn);
                int sIdx = fn.indexOf("-");
                int eIdx = fn.indexOf(".");
                if(sIdx >=0 && eIdx>=0){
                    try{
                        String dateStr = fn.substring(sIdx+1,eIdx);
                        Date date = DateUtil.getDate(dateStr,"yyyy-MM-dd");
                        if(delDate.after(date)){
                            logger.info("删除:{}",fn);
                            FileUtils.forceDelete(f);
                        }
                    }catch(Throwable t){
                        logger.error("删除文件失败", t);
                    }
                }else{
                    //logs目录下存在非标准日志文件，删除
//                    try {
//                        logger.info("删除:{}",fn);
//                        FileUtils.forceDelete(f);
//                    } catch (IOException e) {
//                        logger.error("删除文件失败", e);
//                    }
                }
            }
        }
        logger.info("扫描日志文件结束");
        //这是一个安装程序生成的文件，有时候会非常之大，新的安装包已经不会有了
        //但已经安装的还可能存在
        logger.info("查看error.log文件");
        File errorlog = new File("./error.log");
        if(errorlog.exists()){
            try{
                logger.info("删除error.log文件");
                FileUtils.forceDelete(errorlog);
            }catch(Throwable t){
                logger.error("删除文件失败", t);
            }
        }else{
            logger.info("error.log文件不存在");
        }

        //系统产生的一些临时文件
        logger.info("查看临时文件夹");
        File tempDir = new File("temp");
        if(tempDir.exists()){
            try{
                logger.info("删除临时文件夹");
                FileUtils.forceDelete(tempDir);
            }catch(Throwable t){
                logger.error("删除临时文件夹失败", t);
            }
        }else{
            logger.info("临时文件夹不存在");
        }

    }
}
