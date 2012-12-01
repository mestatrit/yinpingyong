13、处理WEB请求
13.1、开始Spring之旅
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

13.2、将请求映射到控制器
1、四种请求控制映射器
	默认：BeanNameUrlHandlerMapping
	简单的控制器：SimpleURLHandlerMapping
	基于控制器名称：ControllerClassNameHandlerMapping
	基于注解的映射器：CommonsPathMapHandlerMapping

13.3、用控制器处理请求
1、控制器的分类
	核心控制器	->命令控制器		->表单控制器		->向导控制器
			   	      多动作控制器
	（Controller->AbstractController->BaseCommandController->AbstractCommandController
									  MultiActionController	 AbstractFormController->SimpleFormController
															 						AbstractWizedFormController）
    注意：使用场景：
    	1、extends 	AbstractController(最简单)
    	2、extends 	AbstractCommandController（处理命令）
    	3、extends 	SimpleFormController（处理表单）
    	4、extends 	AbstractWizedFormController	（使用向导-处理复杂表单）										 						
	后三者，3.0建议使用注解方式取代！！！

14、渲染WEB视图	
14.1、视图解析
	ViewResolver
	-->
	BeanNameViewResolver
	XmlViewResolver
	InternalViewResolver
		
14.2、使用Spring模板
1、使用form标签，包括所有场景下的组件标签
	例如：form:form
2、渲染存在在属性文件中
	例如：spring:message
3、显示错误，也是使用form:errors标签		

注意：渲染外部属性文件配置，以及错误放在属性文件中，也是为了国际化。

14.3、使用Tile设计页面布局
	
	
	
	
	













				
	 	