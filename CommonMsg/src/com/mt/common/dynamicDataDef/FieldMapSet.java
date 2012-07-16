package com.mt.common.dynamicDataDef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.mt.common.selectionBind.NameCodeItem;
import java.util.Comparator;

/**
 * 一个FieldMap的集合
 * @author hanhui
 *
 */
public class FieldMapSet {

    /**
     * FieldMapSet的名字
     */
    private String name;
    /**
     * FieldMapSet的属性
     */
    private FieldMap attrs = new FieldMap("");
    /**
     * FieldMapSet包含的FieldMap集合
     */
    private List<FieldMap> fieldMaps = new ArrayList<FieldMap>();

    public FieldMapSet(String name) {
        this.name = name;
    }

    /**
     * 设置名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得名称
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置属性
     * @param name
     * @param value
     * @return
     */
    public FieldMapSet setAttr(String name, Object value) {
        this.attrs.putField(name, value);
        return this;
    }

    /**
     * 增加单个属性
     * @param attr
     * @return
     */
    public FieldMapSet addAttr(Field attr) {
        this.attrs.addField(attr);
        return this;
    }
    
    /**
     * 删除某个属性
     * @param name
     * @return
     */
    public FieldMapSet removeAttr(String name){
    	this.attrs.removeField(name);
    	return this;
    }

    /**
     * 增加一批属性
     * @param attrs
     * @return
     */
    public FieldMapSet addAttrList(List<Field> attrs) {
        this.attrs.addFieldList(attrs);
        return this;
    }

    /**
     * 通过属性名获得属性
     * @param name
     * @return
     */
    public Field getAttr(String name) {
        return this.attrs.getField(name);
    }

    /**
     * 获得属性的数量
     * @return
     */
    public int getAttrCount() {
        return this.attrs.getFieldCount();
    }

    /**
     * 获得所有属性的列表
     * @return
     */
    public List<Field> getAttrList() {
        return this.attrs.toFieldList();
    }

    /**
     * 获得所有属性的数组
     * @return
     */
    public Field[] getAttrArray() {
        return this.attrs.toFieldArray();
    }

    /**
     * 获得所有属性的FieldMap形式
     * @return
     */
    public FieldMap getAttrFieldMap() {
        return this.attrs;
    }

    /**
     * 增加一个FieldMap
     * @param fm
     */
    public FieldMapSet addFieldMap(FieldMap fm) {
        fieldMaps.add(fm);
        return this;
    }

    /**
     * 增加一批FieldMap
     * @param fmList
     * @return
     */
    public FieldMapSet addFieldMapList(List<FieldMap> fmList) {
        fieldMaps.addAll(fmList);
        return this;
    }

    /**
     * 插入一个FieldMap
     * @param index
     * @param fm
     */
    public void addFieldMap(int index, FieldMap fm) {
        fieldMaps.add(index, fm);
    }

    /**
     * 更新FieldMap
     * @param index
     * @param fm
     */
    public void updateFieldMap(int index, FieldMap fm) {
        fieldMaps.remove(index);
        fieldMaps.add(index, fm);
    }

    /**
     * 移除一个FieldMap
     * @param index
     */
    public FieldMapSet removeFieldMap(int index) {
        fieldMaps.remove(index);
        return this;
    }

    /**
     * 通过index获取FieldMap
     * @param index
     */
    public FieldMap getFieldMap(int index) {
        return fieldMaps.get(index);
    }

    /**
     * 通过名称获取FieldMap
     * @param fieldMapName
     * @return
     */
    public FieldMap getFieldMap(String fieldMapName) {
        for (FieldMap fm : fieldMaps) {
            if (fm.getName().equals(fieldMapName)) {
                return fm;
            }
        }

        return null;
    }

    /**
     * 通过名称获取一批FieldMap
     * @param fieldMapName
     * @return
     */
    public List<FieldMap> getFieldMapList(String fieldMapName) {
        List<FieldMap> temp = new ArrayList<FieldMap>();
        for (FieldMap fm : fieldMaps) {
            if (fm.getName().equals(fieldMapName)) {
                temp.add(fm);
            }
        }
        return temp;
    }

    /**
     * 获取某个字段名等于某个值的所有FieldMap
     * @param fName
     * @param fValue
     * @return
     */
    public List<FieldMap> getFieldMapList(String fName,Object fValue){
        List<FieldMap> temp = new ArrayList<FieldMap>();
        for (FieldMap fm : fieldMaps) {
            Object fv = fm.getFieldValue(fName);
            if(fv != null){
                if(fv.equals(fValue)){
                    temp.add(fm);
                }
            }
        }
        return temp;
    }

    /**
     * 获得FieldMapSet中某个字段的所有值，自动去除重复
     * @param fName
     * @return
     */
    @SuppressWarnings("unchecked")
	public List getFieldMapValue(String fName){
        List rs = new ArrayList();
        for(FieldMap fm : fieldMaps){
            Object v = fm.getFieldValue(fName);
            if(!rs.contains(v)){
                rs.add(v);
            }
        }
        return rs;
    }

    /**
     * 获得FieldMap的数量
     * @return
     */
    public int getFieldMapCount() {
        return fieldMaps.size();
    }

    /**
     * 获得FieldMapSet的List形式
     * @return
     */
    public List<FieldMap> toFieldMapList() {
        if (getFieldMapCount() == 0) {
            return Collections.emptyList();
        } else {
            List<FieldMap> list = new ArrayList<FieldMap>();
            for (FieldMap fm : fieldMaps) {
                list.add(fm);
            }
            return list;
        }
    }

    /**
     * 排序FieldMap
     * @param com
     */
    @SuppressWarnings("unchecked")
	public void sort(Comparator com) {
        Collections.sort(fieldMaps, com);
    }

    /**
     * 获得FieldMapSet的数组形式
     * @return
     */
    public FieldMap[] toFieldMapArray() {
        if (getFieldMapCount() == 0) {
            return new FieldMap[0];
        } else {
            FieldMap[] array = new FieldMap[getFieldMapCount()];
            return fieldMaps.toArray(array);
        }
    }

    /**
     * 返回一个NameValueObject列表
     * FieldMap必须有 Name和ID这两个属性
     * @return
     */
    public List<NameCodeItem> toNameCodeList() {
        return toNameCodeList("Name", "ID");
    }

    /**
     * 返回一个NameValueObject列表
     * 可以指定作为name的Field和作为value的Field
     * @param nameField
     * @param valueField
     * @return
     */
    public List<NameCodeItem> toNameCodeList(String nameField, String valueField) {
        ArrayList<NameCodeItem> list = new ArrayList<NameCodeItem>();
        for (FieldMap fm : fieldMaps) {
            Field nf = fm.getField(nameField);
            Field vf = fm.getField(valueField);
            if (nf != null && vf != null) {
                list.add(new NameCodeItem(nf.getValue().toString(), vf.getValue().toString()));
            }
        }
        return list;
    }

    /**
     * 如果你的name和value比较复杂通过这个实现NameValueGetter自定义
     * @param getter
     * @return
     */
    public List<NameCodeItem> toNameCodeList(NameCodeGetter getter) {
        ArrayList<NameCodeItem> list = new ArrayList<NameCodeItem>();
        for (FieldMap fm : fieldMaps) {
            if (getter.getName(fm) != null && getter.getCode(fm) != null) {
                list.add(new NameCodeItem(getter.getName(fm), getter.getCode(fm)));
            }
        }
        return list;
    }

    /**
     * 返回一个String类型的HashMap,可以指定哪一个Field的值作为key，哪一个Field的值作为value
     * @param keyField
     * @param valueField
     * @return
     */
    public HashMap<String, String> toHashMap(String keyField, String valueField) {
        HashMap<String, String> hm = new HashMap<String, String>();
        for (FieldMap fm : fieldMaps) {
            Field kf = fm.getField(keyField);
            Field vf = fm.getField(valueField);
            if (kf != null && vf != null) {
                hm.put(kf.getValue().toString(), vf.getValue().toString());
            }
        }
        return hm;
    }

    /**
     * 为所有FieldMap配置一个自定义的toString格式
     * @param fmd
     */
    public void setFieldMapCustomToString(FieldMapCustomToString fmd) {
        for (FieldMap fm : fieldMaps) {
            fm.setFieldMapCustomToString(fmd);
        }
    }

    /**
     * FieldMapSet的相等定义
     */
    public boolean equals(Object obj) {
        if (obj instanceof FieldMapSet) {
            if (!getName().equals(((FieldMapSet) obj).getName())) {
                return false;
            }
            boolean attrE = this.attrs.equals(((FieldMapSet) obj).getAttrFieldMap());
            if (!attrE) {
                return false;
            }
            if (getFieldMapCount() == ((FieldMapSet) obj).getFieldMapCount()) {
                for (int i = 0; i < getFieldMapCount(); i++) {
                    if (!getFieldMap(i).equals(((FieldMapSet) obj).getFieldMap(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 哈希码的定义
     */
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + getName().hashCode();
        hash = 31 * hash + getAttrFieldMap().hashCode();
        for (int i = 0; i < getFieldMapCount(); i++) {
            hash = 31 * hash + getFieldMap(i).hashCode();
        }
        return hash;
    }

    /**
     * 定义toString
     */
    public String toString() {
        String rs = getName() + ":\r\n";
        rs += "Attr:\r\n" + attrs.toString() + "\r\n";
        for (int i = 0; i < getFieldMapCount(); i++) {
            rs += getFieldMap(i).toString() + "\r\n";
        }
        return rs;
    }

    @Override
    public Object clone(){
        FieldMapSet fms = new FieldMapSet(this.getName());
        fms.attrs = this.attrs.copyFieldMap();
        fms.fieldMaps = new ArrayList<FieldMap>();
        for(FieldMap fm:this.fieldMaps){
           fms.fieldMaps.add(fm.copyFieldMap());
        }
        return fms;
    }
}
