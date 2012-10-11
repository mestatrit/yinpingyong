package org.sunside.gof.singleton;

/**
 * @author:Ryan
 * @date:2012-10-11
 */
public class Configurator {
	
	private static final Configurator instance = new Configurator();
	
	private Configurator() {
		
	}
	
	public static Configurator getInstance() {
		return instance;
	}
	
}
