/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.dynamicDataDef;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个字段对象
 * 如果希望静态定义又希望弱类型的话可以使用它
 * 特别是不同模块要共享和处理的数据结构，那么静态定义有利于查看了解字段的意义而不需要额外的文档
 * <p/>
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-4-28
 * Time:9:12:06
 */
public abstract class FieldObject {

    private String name;

    public FieldObject() throws IllegalArgumentException, IllegalAccessException{
    	java.lang.reflect.Field[] dfs = this.getClass().getDeclaredFields();
        for (java.lang.reflect.Field ff : dfs) {
            if (ff.getType().isAssignableFrom(Field.class)) {
                if (ff.get(this) == null) {
                    ff.set(this, new Field(ff.getName(), null));
                }
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<Field> toFieldList() throws IllegalArgumentException, IllegalAccessException {
        List<Field> rsList = new ArrayList<Field>();
        java.lang.reflect.Field[] dfs = this.getClass().getDeclaredFields();
        for (java.lang.reflect.Field ff : dfs) {
            if (ff.getType().isAssignableFrom(Field.class)) {
                rsList.add((Field) ff.get(this));
            }
        }
        return rsList;
    }

    public FieldMap toFieldMap() throws IllegalArgumentException, IllegalAccessException {
        return new FieldMap(name == null ? this.getClass().getSimpleName() : name).addFieldList(toFieldList());
    }

    public void setFieldList(List<Field> fList) throws IllegalArgumentException, IllegalAccessException {
    	java.lang.reflect.Field[] dfs = this.getClass().getDeclaredFields();
        for (java.lang.reflect.Field ff : dfs) {
            if (ff.getType().isAssignableFrom(Field.class)) {
                String ffName = ff.getName();
                for (Field f : fList) {
                    if (f.getName().equals(ffName)) {
                        ff.set(this, f);
                    }
                }
            }
        }
    }

    public void setFieldMap(FieldMap fm) throws IllegalArgumentException, IllegalAccessException {
        this.name = fm.getName();
        setFieldList(fm.toFieldList());
    }
}
