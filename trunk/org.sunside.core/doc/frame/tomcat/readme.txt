											深入剖析TOMCAT
														--概述（1-2）、连接器（3-4）、组件（5-15）

一、概述
1、一个简单的WEB服务器：一个简单的ServerSocket
	1.1、HTTP
		HTTP请求：（请求方法+URL+版本号）+请求头+请求体
		HTTP响应：（版本号+响应吗+响应描述）+响应头+响应体
		
		注意：
		HTTP是无状态协议;
		HTTP响应码：2XX（请求成功）、3XX（请求重定向）、4XX（客户端错误）、5XX（服务端错误）
					404：请求的资源不存在、500：服务器发生未知的错误
	
	1.2、客户端Socket
		客户端Socket对象抽象的4种行为：
		连接 new Socket(String ip, int port, InetAddress localAddr, int lport)
		发送请求socket.getInputStream()
		接收响应socket.getOutputStream()
		关闭socket.close()
		
		服务端ServerSocket
		连接new ServerSocket(int port, int backlog, InetAddress bindAddr)
		绑定端口 bind(SocketAddress endpoint, int backlog);
		接收客户端连接Socket socket =ss.accept();
		发送请求socket.getInputStream();
		接收响应socket.getOutputStream();
		关闭socket.close();ss.close();
		监听入站数据
		
	1.3、应用程序
		请求对象：Request
		响应对象：Response
		web伺服器：HttpServer
		
		说明
		HttpServer：启动一个ServerSocket,阻塞式等到客户端（浏览器）的访问
		Request：用于读取数据（字节流）
		Response：用于发送数据（字节流）
		
2、一个简单的Servlet容器：WEB服务器具体转为Servlet容器
	2.1、javax.servlet.Servlet接口
		API：
		创建
		初始化：void init(ServletConfig config)
		服务：void service(ServletRequest req, ServletResponse res)
		销毁:void destroy();
	
	2.2、应用程序
		HttpServer：1、处理Servlet请求；2、处理资源请求	
		
		注意：
		1、将请求一分为二：
			一、连接器：处理客户端的连接，并封建请求和响应
			二、Servlet容器：具体处理请求，返回响应等
		2、建立在套接字和HTTP协议之上，完成表现层的功能
		3、层级划分
			表现层：HTTP、FTP、IMIX
			传输层：TCP、UDP
			网际层：IP
			物理层
		
二、连接器
3、连接器
	3.1、连接器：负责启动ServerSocket，然后将输入、输出流等信息交给处理器（Servlet容器）处理
	
	3.2、容器：创建Request和Response对象，负责消息的解析和处理
		Request：
			ReaderLine：获取请求方法，获取URL
			解析消息头：解析请求参数，Cookie值
		注意：如果是GET，请求参数直接从URL中获取，但是如果是POST，那么必须从请求体中获取
	
	3.3、Tomcat默认的连接器，基于阻塞式的IO，效率上不高。属于传统的模式。
			
4、Tomcat的默认连接器
	4.1、HTTP1.1的新特性
		持久连接、块编码等
	4.2、Connector接口
		绑定Container（容器）、初始化Request、初始化Response
	4.3、Connector接口：HTTPConnector
	4.4、处理器线程：HttpProcessor
	4.5、Request对象
	4.6、Response对象
	4.7、处理请求：解析连接、解析请求(method+URL+version)、解析消息头
	4.8、简单的container容器		

三、组件
5、Servlet容器
	5.1、Container接口
		(1)Servlet容器的作用：处理请求Servlet资源;填充response对象
		(2)4个具体的容器：引擎Enginer、虚拟主机Host、应用上下文Context、独立Servlet Wrapper
			1、Engine：catalina servlet引擎
			2、Host：包含一个或是多个Context容器的虚拟主机
			3、Context：表示一个web应用程序；包含一个或是多个wrapper
			4、Wrapper：表示一个独立的Servlet
					
	5.2、	
----------------------------

说明
一、连接器：Connector
1、ServerSocketFactory
	--连接器使用ServerSocketFactory，创建ServerSocket
	--基于单例模式

2、HttpProcessor：处理器线程
	--连接器借助于HttpProcessor完成初始化请求、响应等功能
	--连接器使用对象池来管理HttpProcessor，默认做多创建20个HttpProcessor对象；对象池满时，将或略客户端请求
	--连接器监听到客户端连接，将socket加载到HttpProcessor，迅速返回，等待处理下一个请求



