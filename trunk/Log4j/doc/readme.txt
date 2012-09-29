1、slf4j-api-1.7.1.jar(slf4j:simple logger facade for java)
 	功能：对外开发的接口，具体的实现，依赖于不同的实现类Jar包；
 	
2、slf4j-jdk14-1.7.1.jar、slf4j-simple-1.7.1.jar
	功能：slf4j实现的二种不同实现类，只是将日志输出到控制台，不依赖于配置文件。
	
3、slf4j-log4j12-1.7.1.jar、log4j-1.2.17.jar、log4j.properties
	功能：基于apache log4j的slf4j接口实现类。
	slf4j-log4j12-1.7.1.jar依赖于log4j-1.2.17.jar，log4j-1.2.17.jar通过调用slf4j-log4j12-1.7.1.jar，
	实现了slf4j的接口。
	通过log4j.properties，配置日志输出样式

4、logback-core-1.0.7.jar、logback-classic-1.0.7.jar、logback.xml
	功能：基于logback的slf4j接口实现类。
	logback-classic-1.0.7.jar实现了slf4j的接口，但是他依赖于logback-core-1.0.7.jar。
	通过logback.xml，配置日志输出样式

总结：
slf4j目前的实现类总共有三大类：
第一：slf4j的自带实现
第二：apache的log4j实现
第三：logback的实现
(logback、log4j是同一个作者)