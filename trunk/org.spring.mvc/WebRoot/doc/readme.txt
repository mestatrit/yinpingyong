13、开始Spring之旅
1、建议将上下文分散到应用系统的上下文中：
	安全层-xxx_security.xml
	Web层-xxx_servlet.xml
	服务层-xxx_service.xml
	持久层-xxx_data.xml
	
2、	完整的请求过程：
	1、前段控制器DispatchServlet接收到请求
	2、前段控制器通过调用请求映射控制器HandlerMapping，寻找到对应的请求控制器（Action）
	3、请求控制器处理请求
	4、返回ModelAndView对象
	5、视图解析器寻找对应的视图
	6、视图展现响应
	注：前段控制器DispatchServlet、
		请求映射控制器HandlerMapping
		(如果没有配置映射控制器，前端控制器使用BeanNameUrlHandlerMapping作为默认器)、
		例如：
		<!--基于BeanNameUrlHandlerMapping的配置，bena使用name属性，而不是id
		<bean name="/home.htm" class="org.sunside.spring.mvc.controller.HomePageController"></bean>
		-->
	
		控制器Controller
		视图解析器ViewResolver
		(最简单的视图解析器为InternalResourceViewResolver，其通过ModelAndView中的view属性找到对应的视图)	
	 	