1、slf4j-api-1.7.1.jar(slf4j:simple logger facade for java)
 	功能：提供统一的对外调用接口（门面）
 	
2、slf4j-jdk14-1.7.1.jar、slf4j-simple-1.7.1.jar
	功能：slf4j自带的二种slf4j-api-1.7.1实现，二者的不同之处在于日志的输出格式不用。
	
3、slf4j-log4j12-1.7.1.jar、log4j-1.2.17.jar
	功能：基于Apache log4j的slf4j-api-1.7.1实现，slf4j-log4j12-1.7.1.jar依赖于log4j-1.2.17.jar包，
		  slf4j-log4j12-1.7.1.jar是slf4j提供给apache log4j实现slf4j-api的调用接口。
		 注意：使用log4j，同时需要提供log4j.properties配置

4、logback-core-1.0.7.jar、logback-classic-1.0.7.jar
	功能：logbak提供的slf4j-api-1.7.1实现，是log4j的增强替换版。
		注意：logback-classic-1.0.7.jar实现了slf4j-api-1.7.1接口，无需在依赖于其他的jar包。
			logback-classic-1.0.7.jar扩展了logback-core-1.0.7.jar的功能。
		
		
	
	