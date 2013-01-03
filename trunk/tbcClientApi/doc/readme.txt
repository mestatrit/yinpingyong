创建并使用TBCServerConnection过程：
	1、创建
		1.1、连接属性
		1.2、全局设置
	2、初始化
		2.1、初始化通讯模式
		2.2、初始化监听器：TBCServerConnection状态监听器、登录登出监听器、异常处理监听器（负责自动重连）等
	3、连接
		3.1、连接服务器
		3.2、登录服务器
		3.3、启动消息监听器、消息分派器
	4、使用
		4.1、发送请求
		4.2、处理响应
		4.3、心跳
	5、关闭
		5.1、关闭
		5.2、重连
		
一、创建
1、创建TBCServerConnectionInfo，设置连接属性（IP、Port、用户名、密码、设置通讯模式等）
1、创建GlobalConnectionSetting，设置客户端数据：定时扫描间隔、发送消息和介绍消息是否记录日志

二、初始化
1、初始化通讯模式
	根据设置，启用不同的通讯方式（socket、xSocket、HTTP）
	注意：三种通讯方式行为抽象：连接、发送数据、接收数据、关闭、设置超时时间
	1.1、Socket
		连接：socket(ip,port);
			启用安全：socket = SSLSocketFactory.getDefault().createSocket(ip,port)
		发送数据：socket.getOutputStream();
		读取数据：socket.getInputStream();
		关闭：socket.close();
		设置超时时间：socket.setSoTimeout(timeout);
	1.2、xSocket
		连接：BlockingConnection(ip,port);
			启用安全：BlockingConnection(ip, port, SSLContext.getDefault(), true);
		发送数据：blockingConnection.write(str1,str2);
		读取数据：blockingConnection.readStringByDelimiter(str1,str2);
		关闭：blockingConnection.close();
		设置超时时间：bc.setReadTimeoutMillis(timeout);
	1.3、HTTP
		连接：JHttpTunnelClient(ip,port);
		发送数据：JHttpTunnelClient.getInputStream();
		读取数据：JHttpTunnelClient.getOutputSteam();
		关闭：JHttpTunnelClient.close();
		设置超时时间：未提供方法
		
	说明：客户端Socket套接字抽象的行为包括：连接、发送数据、读取数据、关闭连接
		  服务端Socket套接字抽象的行为包括：连接、发送数据、读取数据、关闭连接、绑定端口、接收客户端的连接以及监听人站数据
		  套接字可设置相关SO属性，例如读取数据的超时时间，缓冲区的大小等		
2、初始化监听器
	2.1、初始化TBCServerConnection状态变化(连接状况)监听器
	2.2、初始化TBCServerConnection服务器强行要求连接断开的监听器
	2.3、初始化OID消息的监听器
	2.4、初始化NetExceptionHandler网络监听监听器(负责网络异常监听和自动重连)
	
三、连接
1、连接服务端：例如socket(ip,port)... ...
2、登录服务端：发送用户名、密码，登录服务器
3、启动消息监听器、消息分派器
	3.1、消息监听器：阻塞式读取客户端收到的消息、将消息放入阻塞式消息队列
	3.2、消息分派器：启动超时扫描线程、读取阻塞式队列消息、调用回调函数

四、使用
1、发送请求
	1.1、发送异步请求
		(1)一次性请求：
			带有请求参数(obj)、超时、响应回调等参数的请求：
			requestRemoteService(String fid,Object obj,int timeOut,CommonMsgCallback callBack)
			
			带有缓存功能的异步请求：第二次发送请求时，直接从本地缓存中获取结果：
			requestRemoteService_LocalSave(String fid,Object obj,int timeOut,CommonMsgCallback callBack)
			
			不需要处理响应的请求TBCServerConnection.java：
			requestRemoteService(String fid,Object obj)
		
		(2)注册请求：等待服务端主动推送
			registerRemoteServiceListener(String fid, CommonMsgCallback callBack)
	1.2、发送同步请求
			带有请求参数、超时等参数的同步请求：
			syncRequestRemoteService(String fid,Object obj,int timeOut)
		
2、处理响应
	消息监听器阻塞式读取Socket中消息，将其放入阻塞式队列；
	消息分派器读取队列消息，然后调用对应的回调函数；
	
3、心跳
	基于Executors.newScheduledThreadPool(1)，实现定时发送心跳功能
	
五、关闭
1、关闭
	发送登出请求、停止消息监听器、停止消息分派器、关闭客户端套接字	
	
2、重连
	
问题：
1、心跳的处理：如果长时间未收到心跳，客户端的处理方式?


