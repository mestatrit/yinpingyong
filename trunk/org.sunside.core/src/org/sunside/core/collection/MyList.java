package org.sunside.core.collection;

/**
 * 集合抽象数据模型ADT
 * 
 * @author:Ryan
 * @date:2012-10-21
 */
public interface MyList<E> {
	
	/**
	 * 新增集合元素
	 * @param element 新增对象
	 * @return
	 */
	boolean add(E element);
	
	/**
	 * 删除集合元素
	 * @param obj 删除对象
	 * @return
	 */
	boolean remove(Object obj);
	
	/**
	 * 查找集合元素
	 * @param index 对象所在位置
	 * @return
	 */
	E get(int index);
	
	/**
	 * 更新集合元素
	 * @param index 更新位置
	 * @param element 更新对象
	 * @return
	 */
	E set(int index, E element);
	
	
	
	
}
