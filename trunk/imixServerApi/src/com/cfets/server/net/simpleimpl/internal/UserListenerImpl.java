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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.server.net.simpleimpl.ImixSessionInfo;
import com.cfets.server.net.simpleimpl.RequestHandler;
import com.cfets.server.net.simpleimpl.ResponseHandler;

/**
 * 客户端登录验证监听器
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class UserListenerImpl implements UserListener {

	private final static Logger logger = LoggerFactory.getLogger(UserListenerImpl.class);
	
	/**
	 * 用户回话信息，长连接
	 */
	private Map<SessionID, ImixSessionInfo> user2session;
	
	public UserListenerImpl(Map<SessionID, ImixSessionInfo> user2session) {
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
			
			if (user2session.get(sessionID) == null) {
				
				//TODO 1、验证用户身份
				Integer cusNumber = Integer.valueOf(message.getHeader().getString(SenderCompID.FIELD));
				String userName = message.getString(Username.FIELD);
				String password = message.getString(Password.FIELD);
				
				// 2、创建用户会话
				ImixSessionInfo info = new ImixSessionInfo();
				info.setSessionID(sessionID);
				info.setRequestQueue(new MsgBlockingQueue());
				info.setResponseQueue(new MsgBlockingQueue());
				
				ExecutorService responseThread = Executors.newSingleThreadExecutor();
				info.setResponseThread(responseThread);
				
				ExecutorService requestThread = Executors.newSingleThreadExecutor();
				info.setRequestThread(requestThread);
				
				// 3、启动响应和请求处理器
				responseThread.submit(new ResponseHandler());
				requestThread.submit(new RequestHandler());
				
				// 4、保存用户会话
				user2session.put(sessionID, info);
				
				logger.info("用户={}登陆成功.", sessionID);
				
			} else {
				
				//该用户已经登陆，则不能重复登陆；此处其实在底层API处已经做了处理，即相同用户不能同时登陆
				logger.warn("sessionID={},用户登陆验证失败，该用户已经登陆，不能重复登陆！", sessionID.toString());
				
				session.logout("Multi Logon!");
			}
		}
	}

}
