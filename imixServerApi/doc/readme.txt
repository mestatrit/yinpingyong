									IMIXServerAPI
1、每一个Sesson创建二个队列：请求队列、消息发送队列
	请求队列：用于接收客户端的请求，逐个处理这些请求
	消息发送队列：存储待发送给用户的消息
2、	