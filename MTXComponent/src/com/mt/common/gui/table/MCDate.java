package com.mt.common.gui.table;

/**
 * 用于排序的Date类型
 * 如果你的表格模型某一列返回MCDate.class，那么这一列将进行日期排序
 * 哪怕这里一列的数据类型不是真正的Date类型，只要是符合日期格式的字符串
 * 也会进行日期排序
 * 当然如果你的模型返回真正的Date.class类型,那么MultiColumnTableSorter
 * 也会支持日期排序
 *
 * @author hanhui
 */
public class MCDate {

}
