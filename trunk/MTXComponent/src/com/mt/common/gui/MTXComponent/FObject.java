/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.gui.MTXComponent;

import com.mt.common.gui.PinyinHandler;

/**
 * 支持拼音的过滤包装器对象
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-8-10
 */
public class FObject<T> {

    protected T internalObj;
    protected String fStr;

    public FObject(T obj) {
        this.internalObj = obj;
        setFString(obj);
    }

    public T getInternalObject() {
        return internalObj;
    }

    public void setInternalObject(T obj) {
        this.internalObj = obj;
        setFString(obj);
    }

    public boolean contains(String sText) {
        return toFString().contains(sText.toUpperCase());
    }

    public boolean startWith(String sText) {
        return toFString().startsWith(sText.toUpperCase());
    }

    public String toFString() {
        return fStr;
    }

    private void setFString(Object obj) {
        if (obj == null) {
            fStr = "";
            return;
        }
        String str = obj.toString();
        //支持全拼，简拼
        fStr = new StringBuilder(str).append(PinyinHandler.getMixPy(str)).append(PinyinHandler.getMixPinyin(str)).toString();
        fStr = fStr.toUpperCase();
    }
}
