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
		--基于SqlMapExecuror：
		queryForObject(id,prepared,result)\
		queryForList(id,prepared,result,skip,max)\
		queryForMap(id,prepared,key,value))
		insert(id,prepare);
		update(id,prepare);
		delete(id,prepare);
		--批处理
		startBatch();
		executeBatch();
		--基于SqlMapTransactionManager:
		startTransaction();
		commintTransaction();
		endTransaction();
		
		类关系：
		SqlMapExexutor、SqlMapTransactionManager、java.sql.connection
		SqlMapSession、SqlMapClient、SqlMapClientImpl
	4.2、使用Select标签
		使用内联参数映射#、$
		注意：方式Sql注入
	4.3、使用参数映射
	4.4、自动映射结果集合和显示结果映射
	
	注意：显示配置参数和结果集的映射（少用自动映射以及map），可以提高效率。	

5、执行非查询语句
	5.1、更新数据的基本方法
		--XXSqlMap.xml:
		delete\update\insert\executor
		statement\produce
		preparedMap\resultMap
		sql\typeAlias
		chacheModel
		--SqlMaoConfig.xml
		proprerty
		setter
		transactionManager
		sqlMap
		typeAlias
	5.2、插入数据
	5.3、更新和删除数据
	5.4、批量处理
	5.5、使用存储过程
6、使用高级查询技术
	6.1、在Ibatis中使用XML（参数和结果集）
	6.2、使用已映射的语句（延迟加载和避免N+1次查询）					
7、事务
	7.1、事务是什么（原子性、一致性、隔离性、持久性）
	7.2、自动事务（默认iBatis所有的操作都是包含在事物中）
	7.3、局部事务（一般的事物）
	7.4、全局事务（分布式事务）
	7.5、定制事务（注入Connection，基于Connection定制事务）
	7.6、事务划界(Service层)
8、动态sql调用
							
三、在真实世界中的应用
9、使用高速缓存提高新能
	9.1、简单的实例：（chcheModel Tag）
	9.2、iBatis高速缓存理念（作用于持久层上的缓存）
	9.3、缓存模型中的属性（id\type\readOnly\serial）
	9.4、缓存模型中的标签（flushOnExecute、flushInterva）
	9.5、缓存模型类型（内存、FIFO、LRU、OSCache）
	9.6、缓存模型策略
		内存模式：缓存可读写的
		FIFO：缓存旧的静态数据
		LRU：缓存经常使用的数据
	注意：作用于持久层上的缓存策略，用于DB单独被APP操作的时候。
	例子：
	<cacheModel id='' type='' readOnly='true' serial='false'>
		<flushInterval hour/mininter/secode/=''/>
		<flushExecuter statement=''>
		...
		<property name='size/reference-type' value='30/weak/soft/strong'/>
	</cacheModel>
10、iBatis的数据访问对象		
四、使用秘诀

补充：
1、ibatorConfig.xml中daoGenerator type标签，
	如果是单独使用iBatis执行事物，使用GENERIC-CI值；直接注入sqlMapclient;
	如果是集成Sprinf，使用SPRING值；采用基于模板方法执行事务；
	
2、java.sql.Statement和java.sql.PreparedStatement:
	1、PreparedStatement继承Statement
	2、PreparedStatement是预编译的，所以对执行批处理可以大大提高性能，同时安全性也高于Statement
	3、如果执行单个sql，因为PreparedStatement需要预处理，所以开销比Statement大

3、事物
	使用SqlMapClient.startTranaction/commitTranaction/endTransaction编程式事物；
	SqlMapClientTemplate也是用此方式
4、批量处理	
	使用SqlMapClient.startBatch\executeBatch开始批量处理，注意批量处理的sql类型一定要求是一样的。	
