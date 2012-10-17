/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.dynamicDataDef;

/**
 * FieldValue的异常定义
 * <p/>
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-6-19
 * <p/>
 */
public class FieldValueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FieldValueException(Field f, String msg) {
        super(f.getName() + ":" + f.getValue().toString() + "  " + msg);
    }

    public FieldValueException(Field f, String msg, Throwable throwable) {
        super(f.getName() + ":" + f.getValue().toString() + "  " + msg, throwable);
    }
}
