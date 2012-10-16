package org.sunside.core.gof.singleton;

/**
 * 结合缓存（首次调用时，才创建对象），实现单例
 * 
 * @author:Ryan
 * @date:2012-10-11
 */
public class Configurator2 {

	private static Configurator2 instance;

	private Configurator2() {

	}

	public synchronized static Configurator2 getInstance() {
		if (instance == null) {
			instance = new Configurator2();
		}
		return instance;
	}

}
