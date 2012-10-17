package com.mt.common.gui.table;

/**
 * 用于排序的Number类型
 * 如果你的表格模型某一列返回MCNumber.class，那么这一列将进行数值排序
 * 哪怕这里一列的数据类型不是真正的Number类型，只要是符合数值格式的字符串
 * 也会进行数值排序
 * 当然如果你的模型返回真正的Number.class或Double.class类型,那么MultiColumnTableSorter
 * 也会支持数值排序
 *
 * @author hanhui
 */
public class MCNumber {

}
