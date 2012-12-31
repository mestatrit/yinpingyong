一、java网络编程原因

二、基本网络概念
1、网络分层：
	应用层（HTTP、FTP、XML、IMIX...）
	传输层（TCP、UDP）
	网络层（IP）
	物理层
	
三、基本WEB概念
1、URI：URL、URN
2、HTTP响应码：
	连接成功：2XX
	请求重定向：3XX
	客户端错误：4XX
	服务顿错误5XX
	例：404：请求资源服务端不存在、500：服务器发生意外情况，不知道如何处理
3、HTTP是无状态的协议
4、通过在MIME中标记文档的类型，例如：文本、视频、图片、声音等
	text/html、text/css等
	
四、流
1、读入流：
java.io.InPutStream
 read、close

2、写入流：
java.io.OutPutStram
 write、flush、close
 
3、缓存流、加密流、解压流等 	
	
五、线程
1、join\yield\sleep

六、查找Internet地址
1、java.net.InetAddress
2、java.net.Inet4Address
3、java.net.Inet6Address
4、IPv4：4字节无符号数组成
   IPv6: 8字节16进制数组成

七、URL和URI
1、java.net.URL
2、java.net.URI

八、Swing中的HTML
1、javax.swing.JEditorPanel

九、客户端Socket	
十、服务端Socket
十一、安全Socket
十二、非阻塞IO
十三、UDP和socket
十四、组播
十五、URLConnection
十六、协议处理器
十七、内容处理器
十八、远程调用
十九、JMail

		