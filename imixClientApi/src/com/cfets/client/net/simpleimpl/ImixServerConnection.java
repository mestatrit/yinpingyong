package com.cfets.client.net.simpleimpl;

import imix.ConfigError;
import imix.Message;
import imix.client.core.ImixApplication;
import imix.client.core.ImixSession;
import imix.client.core.ImixSessionExistingException;
import imix.field.FreeMsgID;
import imix.field.FreeMsgType;
import imix.field.Text;
import imix.imix10.FreeFormatMessage;

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
	
	private void requestRemoteService(Message message) {
		logger.info("向服务器发送消息:{}", message.toString());
		imixSession.send(message);
	}
	
	public static void main(String[] args) {
		
		ImixServerConnection connection = new ImixServerConnection();
		
		try {
			//启动服务
			connection.startImix();
			
			//发送请求
			FreeFormatMessage freeFormatMessage = new FreeFormatMessage();
			FreeMsgID msgId = new FreeMsgID("0001");
			
			/*FreeMsgType fid = new FreeMsgType("C33B");
			Text text = new Text("<Page><DATE>20130122</DATE></Page>");*/
			
			FreeMsgType fid = new FreeMsgType("C33C");
			Text text = new Text("<Page><INCLUDEFEE>1</INCLUDEFEE>" +
										"<RETURNTYPE>0</RETURNTYPE>" +
										"<STARTDATE>20121211</STARTDATE>" +
										"<ENDDATE>20130111</ENDDATE>" +
										"<DayEnd>0</DayEnd>" +
										"<MarkToMarketPrice>market</MarkToMarketPrice>" +
										"<RepoType>0</RepoType>" +
								 "</Page>");
			freeFormatMessage.set(msgId);
			freeFormatMessage.set(fid);
			freeFormatMessage.set(text);
			
			freeFormatMessage.toString();
			
			connection.requestRemoteService(freeFormatMessage);
			
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
