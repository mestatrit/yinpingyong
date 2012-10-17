/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.dynamicDataDef;

/**
 * 一个FieldObject的演示例子
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-4-28
 * Time:10:34:10
 * <p/>
 */
public class FieldObjectDemo extends FieldObject {

    public FieldObjectDemo() throws IllegalArgumentException, IllegalAccessException {
		super();
	}

	public Field id;

    public Field name;

    public Field price;

    public String noFType;

    static public void main(String args[]) throws IllegalArgumentException, IllegalAccessException {
        FieldObjectDemo fDemo = new FieldObjectDemo();
        fDemo.id.setValue("060001");
        fDemo.name.setValue("06国债01");
        fDemo.price.setValue(98.12);
        System.err.println(FieldMapUtil.createXMLString(fDemo.toFieldMap()));

        FieldMap temp = new FieldMap("Test");
        temp.putField("id", "070001");
        temp.putField("name", "07国债01");
        temp.putField("price", 101.23);

        fDemo.setFieldMap(temp);
        System.err.println(fDemo.id.getValue());
        System.err.println(fDemo.name.getValue());
        System.err.println(fDemo.price.getValue());
        System.err.println(FieldMapUtil.createXMLString(fDemo.toFieldMap()));
    }

}
