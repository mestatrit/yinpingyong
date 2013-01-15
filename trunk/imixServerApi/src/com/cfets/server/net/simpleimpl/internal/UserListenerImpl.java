package com.cfets.server.net.simpleimpl.internal;

import imix.FieldNotFound;
import imix.IncorrectDataFormat;
import imix.IncorrectTagValue;
import imix.Message;
import imix.SessionID;
import imix.core.api.UserListener;
import imix.engine.core.RejectLogon;
import imix.engine.core.Session;
import imix.field.Password;
import imix.field.SenderCompID;
import imix.field.Username;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端登录验证监听器
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class UserListenerImpl implements UserListener {

	private final static Logger logger = LoggerFactory.getLogger(UserListenerImpl.class);
	
	private Map<String, SessionID> user2session;
	
	public UserListenerImpl(Map<String, SessionID> user2session) {
		this.user2session = user2session;
	}
	
	@Override
	public void fillLogon(Message message, SessionID sessionID) {
		
		// server do not need to fill username and password
		// ignore
		logger.info("fillLogon();sessionID={}", sessionID);
	}

	@Override
	public void verifyLogon(Message message, SessionID sessionID) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		
		logger.info("sessionID={},客户端登陆验证... ...", sessionID.toString());
		
		Session session = Session.lookupSession(sessionID);
		
		if (message.isSetField(Username.FIELD) == false ) {
			
			logger.warn("登录失败,缺少用户名. SessionId={}", sessionID.toString());
			
			session.logout("Username is null.");
		} else if (message.isSetField(Password.FIELD) == false) {
			
			logger.warn("登录失败,缺少密码. SessionId={}", sessionID.toString());
			
			session.logout("Password is null.");
		} else {
			Integer cusNumber = Integer.valueOf(message.getHeader().getString(SenderCompID.FIELD));
			
			String userName = message.getString(Username.FIELD);
			
			String password = message.getString(Password.FIELD);
			
			String key = cusNumber + "<" + userName;
			
			SessionID sID = user2session.get(key);
			
			if (sID == null) {
				
				//TODO 验证用户身份
				user2session.put(key, sessionID);
				
				logger.info("sessionID={},用户登陆成功.", sessionID.toString());
				
			} else {
				
				//该用户已经登陆，则不能重复登陆；此处其实在底层API处已经做了处理，即相同用户不能同时登陆
				logger.warn("sessionID={},用户登陆验证失败，该用户已经登陆，不能重复登陆！", sessionID.toString());
				
				session.logout("Multi Logon!");
			}
		}
	}

}
