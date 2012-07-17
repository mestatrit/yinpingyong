package com.mt.common.settingManager;

import com.mt.common.dynamicDataDef.FieldMapSet;

/**
 * 设置数据管理器，系统中大多功能各种配置信息保存读取都会用到这个管理器
 * 
 * @Author: Ryan
 * 
 *          2012-7-17
 */
public class SettingDataManager {

	/**
	 * 保存本地设置
	 * 
	 * @param name
	 * @param obj
	 */
	static public void saveLocalSetting(String name, Object obj) {
		LocalStore.toLocalXMLData(name, obj, StorePath.LoginUserPath);
	}

	/**
	 * 读取本地设置,返回FieldMapSet
	 * 
	 * @param name
	 * @return
	 */
	static public FieldMapSet readFieldMapSetLocalSetting(String name) {
		Object rs = LocalStore.fromLocalXMLData(name, FieldMapSet.class, StorePath.LoginUserPath);
		if (rs != null) {
			return (FieldMapSet) rs;
		}
		return null;
	}
	
	/**
     * 读取本地设置
     *
     * @param name
     */
    static public Object readLocalSetting(String name, Class classV) {
        return LocalStore.fromLocalXMLData(name, classV, StorePath.LoginUserPath);
    }
}
