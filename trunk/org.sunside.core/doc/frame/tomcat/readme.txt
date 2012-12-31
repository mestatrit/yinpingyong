深入剖析Tomcat
一、简介
1、一个简单的web服务器
	1.1、HTTP
		请求：URL+Head+Body
			URL:Method+Resource+Version
		响应：Status+Head+Body
			Status:Version+Status
	
	1.2、Socket
		java.net.Socket(ip,port,localAddress,localport)
		java.net.ServerSocket(port,logback,localAddress)
		--
		connection\inputStream\outputStream\close
		accept\bind\listener
		
2、一个简单的servlet服务器


二、连接器
三、组件