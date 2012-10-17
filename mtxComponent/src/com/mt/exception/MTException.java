package com.mt.exception;

/**
 * 系统内通用异常
 */
public class MTException extends RuntimeException {

    /**
     *  构造系统内通用异常
     */
    public MTException() {
        super();
    }

    /**
     * 构造系统内通用异常
     * @param s  异常说明
     */
    public MTException(String s) {
        super(s);
    }

    /**
     * 取得异常信息
     * @param ex  抛出的异常
     * @return
     */
    public static String getExceptionMsg(Throwable ex){
        Throwable temp = ex;
        while(temp.getCause() != null){
            temp = temp.getCause();
        }
        String rs = temp.getMessage() == null ?ex.getMessage():temp.getMessage();
        if(rs.contains("NullPointer")){
            rs = "空指针异常";
        }
        return rs;
    }
}
