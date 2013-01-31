										imixClientApi
1、创建
	1、连接属性
	2、全局设置
2、初始化
	1、通讯模式
	2、监听器
3、连接
	1、连接
	2、登录
	3、启动消息监听
4、使用
	1、发送请求
	2、处理响应
	3、心跳
5、关闭
	1、关闭
	2、重连

							
一、要求
1、请求支持同步+异步（异步、带本地缓存的异步、不要求有回调的异步）+注册
	异步：
	requestRemoterService(String fid,Object obj,int timeOut,Object callBack);
	requestRemoterService_localSave(String fid,Object obj,int timeOut,Object callBack);
	requestRemoterService(String fid,Object obj,int timeOut);
	同步：
	syncRequestRemoterService(String fid,Object obj,int timeOut,Object callBack);
	注册：
	registerRemoteServiceListener(String fid,Object callBack);
	
2、	
										