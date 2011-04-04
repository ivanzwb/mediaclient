package com.shijie.media.client.api.service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Ivan
 * 数据库服务。
 */
public interface DBService extends IService{
	
	/**
	 * 数据库服务ID
	 */
	public final static String ID = "DBService";
	/**
	 * 打开数据库链接。
	 */
	public void openConnection();	
	/**
	 * 关闭数据库链接。
	 */
	public void closeConnection();	
	/**
	 * 打开事务。
	 */
	public void openTransaction();	
	/**
	 * 关闭事务。
	 */
	public void closeTransaction();	
	/**
	 * 提交事务。
	 */
	public void commit();	
	/**
	 * 回滚事务。
	 */
	public void rollback();	
	/**
	 * 保存对象obj.
	 * @param <Type>
	 * @param obj
	 */
	public <Type> void store(Type obj);	
	/**
	 * 批量保存对象 。
	 * @param <Type>
	 * @param objList
	 */
	public <Type> void store(List<Type> objList);	
	/**
	 * 删除对象obj。
	 * @param <Type>
	 * @param obj
	 */
	public <Type> void delete(Type obj);	
	/**
	 * 删除type类型所有对象。
	 * @param <Type>
	 * @param type
	 * @return
	 */
	public <Type> int delete(Class<Type> type);	
	/**
	 * 删除type类型所有属性和值满足map的对象。
	 * @param <Type>
	 * @param type
	 * @param map
	 * @return
	 */
	public <Type> int delete(Class<Type> type, HashMap<String,Object> map);
	/**
	 * 查询和obj一样的对象。
	 * @param <Type>
	 * @param obj
	 * @return
	 */
	public <Type> List<Type> query(Type obj);
	/**
	 * 查询type类型所有对象。
	 * @param <Type>
	 * @param type
	 * @return
	 */
	public <Type> List<Type> query(Class<Type> type);
	/**
	 * 查询type类型所有属性和值满足map的对象
	 * @param <Type>
	 * @param type
	 * @param map
	 * @return
	 */
	public <Type> List<Type> query(Class<Type> type, HashMap<String,Object> map);
	/**
	 * 用本地SQL查询。
	 * @param <Type>
	 * @param nativeSQL
	 * @return
	 */
	public <Type> List<Type> executeQuery(String nativeSQL);
	/**
	 * 执行本地SQL。
	 * @param nativeSQL
	 * @return
	 */
	public int execute(String nativeSQL);

}
