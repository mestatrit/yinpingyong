一、Spring的核心
1、Spring之旅
2、基本Bean装配
	2.1、容纳Spring
		1、BeanFactory->AppliactionContext
		2、ClassPathXmlAppliationContext
		3、FileSystemXmlAppliationContext
			...
	2.2、创建Bean
	2.3、注入Bean
		1、单一属性注入（构造、setter）
		2、集合注入（list\set\map\property）
	2.4、自动注入
		1、基于Name\基于Type
	2.5、控制Bean
		1、Bean的范围（单例、每次创建新实例）
		2、Bean的初始化和销毁
	2.6、总结
	
3、高级Bean装配
	3.1、父子Bean
	3.2、方法注入
	...
	
4、通知Bean
	4.1、AOP简介
		1、通知（Advice）:切面的功能被称为通知，其定义了切面是做什么，何时去做
		2、连接点（JoinPoint）：可以被切面插入的功能点
		3、切入点（CutPoint）：切面织入的点，叫做切入点，其指出了切面在哪里切入交叉事务
		4、
	4.2、
二、企业Spring
三、Spring客户端

总结：
1、DI实现对象之间的解耦
2、AOP实现交叉事物和对象的解耦
3、IOC负责Bean的创建、管理等