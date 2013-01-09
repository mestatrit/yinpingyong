package com.cfets.client.net.simpleimpl;

import imix.ConfigError;
import imix.client.core.ImixApplication;
import imix.client.core.ImixSession;
import imix.client.core.ImixSessionExistingException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.client.net.simpleimpl.internal.ImixMsgListener;

/**
 * ImixServerConnection
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class ImixServerConnection {
	
	private final static Logger logger = LoggerFactory.getLogger(ImixServerConnection.class);
	
	private final static String CONFIG_FILE = "cfg/client.cfg";
	
	private ImixSession imixSession;
	
	private void startImix() throws ConfigError, IOException, ImixSessionExistingException {
		
		ImixMsgListener listener = new ImixMsgListener();
		
		ImixApplication.initialize(listener, CONFIG_FILE);
		
		ImixApplication.initialize(listener, CONFIG_FILE);
		
		InputStream in = new BufferedInputStream(new FileInputStream(CONFIG_FILE));
		
		Properties prop = new Properties();	
		
		prop.load(in);
		
		String userName=prop.getProperty("SenderSubID");
	    
		String password=prop.getProperty("PASSWORD");
		
		imixSession = new ImixSession(userName, password);
		
		imixSession.start();
	}
	
	public static void main(String[] args) {
		
		ImixServerConnection connection = new ImixServerConnection();
		
		try {
			connection.startImix();
		} catch (ConfigError e) {
			logger.error(e.getMessage());
			logger.error("客户端imix配置文件异常：ConfigError,ConfigError info={}", e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error("读取配置文件异常：IOException,IOException info={}", e);
		} catch (ImixSessionExistingException e) {
			logger.error(e.getMessage());
			logger.error("回话已经存在异常：ImixSessionExistingException,ImixSessionExistingException info={}", e);
		}
	}
	
}
