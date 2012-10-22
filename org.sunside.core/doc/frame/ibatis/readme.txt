一、简介
1、iBatis理念
	1.1、起源（内联Sql（实现难度大）、动态Sql（实现不优雅）、存储过程、O/R M）
	1.2、何处使用（持久层：抽象接口（DAO）、O/R M、访问接口（JDBC））
	1.3、使用数据库（不同数据库使用会有差异，App、企业、私有、遗留数据库）
	1.4、遇到的困难（负责的建值关系、去规范化和过度的规范化）
2、iBatis是什么
	何时使用、何时不适用、开始使用、未来发展
二、基础知识
3、安装和配置iBatis
	3.1、下载二进制库或是源码
	3.2、发布包中包含的内容（common、sqlMap）
	3.3、依赖性（实现：事物管理（DBCP）、缓存和延迟加载）
	3.4、和JDBC关系（简化了僵硬代码，例如连接打开、关闭）
	3.5、SqlMapConfig文件的配置：
		1、全局配置：Setter：使用启用缓存、命名空间、延迟加载等
		2、事物配置：transactionManager
		3、SqlMap映射文件配置：sqlMap

4、使用已映射语句
	4.1、从基础开始
		SqlMapClient API(
		queryForObject(id,prepared,result)\
		queryForList(id,prepared,result,skip,max)\
		queryForMap(id,prepared,key,value))
		
		类关系：
		SqlMapExexutor、SqlMapTransactionManager、java.sql.connection
		SqlMapSession、SqlMapClient、SqlMapClientImpl
	4.2、使用Select标签
		使用内联参数映射#、$
		注意：方式Sql注入
	4.3、使用参数映射
	
	注意：显示配置参数和结果集的映射（少用自动映射以及map），可以提高效率。	
			
三、在真实世界中的应用

四、使用秘诀

补充：
1、ibatorConfig.xml中daoGenerator type标签，
	如果是单独使用iBatis执行事物，使用GENERIC-CI值；直接注入sqlMapclient;
	如果是集成Sprinf，使用SPRING值；采用基于模板方法执行事务；
	
2、java.sql.Statement和java.sql.PreparedStatement:
	1、PreparedStatement继承Statement
	2、PreparedStatement是预编译的，所以对执行批处理可以大大提高性能，同时安全性也高于Statement
	3、如果执行单个sql，因为PreparedStatement需要预处理，所以开销比Statement大
