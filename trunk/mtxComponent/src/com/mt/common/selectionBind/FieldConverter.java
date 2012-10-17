package com.mt.common.selectionBind;

import com.mt.common.dynamicDataDef.Field;

/**
 * 一个自定义字段转换器接口
 * @author hanhui
 *
 */
public interface FieldConverter {

	/**
	 * 从字段转换为其他值
	 * @param field
	 * @return
	 */
	public Object convertField(Field field);
	
	/**
	 * 将其他值设置到Field
	 * @param field
	 * @param value
	 */
	public void setField(Field field,Object value);
}
