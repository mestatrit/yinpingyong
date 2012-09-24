1、slf4j-api-1.7.1.jar(slf4j:simple logger facade for java)
 	功能：提供统一的对外调用接口（门面）
 	
2、slf4j-jdk14-1.7.1.jar、slf4j-simple-1.7.1.jar
	功能：slf4j自带的二种slf4j-api-1.7.1实现，二者的不同之处在于日志的输出格式不用。
	
3、slf4j-log4j12-1.7.1.jar
	功能：基于Apache log4j的slf4j-api-1.7.1实现，slf4j-log4j12-1.7.1.jar依赖于log4j-1.2.17.jar包，
		  slf4j-log4j12-1.7.1.jar是slf4j提供给apache log4j实现slf4j-api的调用接口。
		  注意：使用log4j，同时需要提供log4j.properties配置

4、
	