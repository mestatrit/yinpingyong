package com.mt.common.selectionBind;

import java.io.Serializable;

/**
 * 表示一个Name和Key的对象，key是这个name的内部表示形式
 * 两个对象的相等会依据key来决定
 *
 * @author hanhui
 *         <p/>
 */
final public class NameCodeItem implements Serializable {

    private String name;
    private String code;
    private boolean isToStringCode = false;

    public NameCodeItem() {
        this(false);
    }

    public NameCodeItem(boolean isToStringCode) {
        this(null, null, isToStringCode);
    }

    public NameCodeItem(String name, String code) {
        this(name, code, false);
    }

    public NameCodeItem(String name, String code, boolean isToStringCode) {
        this.name = name;
        this.code = code;
        this.isToStringCode = isToStringCode;
    }

    public void setToStringCode(boolean isToStringCode) {
        this.isToStringCode = isToStringCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String key) {
        this.code = key;
    }

    public String getCode() {
        return code;
    }

    /**
     * 定义hashCode
     */
    public int hashCode() {
        return code.hashCode();
    }

    /**
     * 定义NameKeyItem的相等
     */
    public boolean equals(Object obj) {
        if (obj instanceof NameCodeItem) {
            NameCodeItem nk = (NameCodeItem) obj;
            return this.code.equals(nk.code);
        } else {
            return false;
        }
    }

    /**
     * 自定义toString
     */
    public String toString() {
        if (code != null && name != null && !code.equals("") && !name.equals("")) {
            if (isToStringCode) {
                return new StringBuilder(name).append('(').append(code).append(')').toString();
            } else {
                return name;
            }
        } else {
            return "";
        }
    }

    /**
     * 从字符串创建NameCodeItem
     * name(code)
     *
     * @param str
     * @return
     */
    static public NameCodeItem fromString(String str) {
        int s = str.indexOf("(");
        int e = str.indexOf(")");
        if (s != 0 && e != -1) {
            return new NameCodeItem(str.substring(0, s), str.substring(s + 1, e));
        } else {
            return new NameCodeItem("", str);
        }
    }
}
