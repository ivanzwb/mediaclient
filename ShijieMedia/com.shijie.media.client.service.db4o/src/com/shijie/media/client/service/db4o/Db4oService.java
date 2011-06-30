package com.shijie.media.client.service.db4o;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.shijie.media.client.api.service.DBService;
import com.shijie.media.client.api.service.IServiceManager;
import com.spaceprogram.db4o.sql.Sql4o;
import com.spaceprogram.db4o.sql.Sql4oException;
import com.spaceprogram.db4o.sql.parser.SqlParseException;

public class Db4oService extends Db4oDriver implements DBService {
	
	private Logger logger = LoggerFactory.getLogger(Db4oService.class);
	
	
	private Properties config;
	private ObjectContainer client;
	
	public Db4oService(){
		super(Activator.context.getBundle());
		config=new Properties();
		String dbFile = "SHIJIE.db";
		try {
			config.load(this.getClass().getResourceAsStream("/configuration/config.db"));
			dbFile = config.getProperty("dbFile");
		} catch (Exception e) {
			logger.error("DB4o config file not found.");
		}
		client = openFile(dbFile);
		
	}
	
	@Override
	public void init() {
		logger.info("init Db4o serivce...");
	}
	@Override
	public void start() {
		logger.info("start Db4o serivce...");
	}
	@Override
	public void stop() {
		
	}
	@Override
	public String getServiceID() {
		return ID;
		
	}
	@Override
	public void setServiceManager(IServiceManager serviceManager) {
		
	}
	
	@Override
	public void openConnection(){
		;
	}
	@Override
	public void commit(){
		client.commit();
	}
	@Override
	public void closeConnection(){
		;
	}
	
	@Override
	public void openTransaction() {
		;
	}

	@Override
	public void rollback() {
		client.rollback();
		
	}

	@Override
	public void closeTransaction() {
		;
	}
	@Override
	public void store(Object obj){
		client.store(obj);
	}
	
	@Override
	public <TargetType> void store(List<TargetType> objList) {
		for(TargetType type:objList){
			client.store(type);
		}
	}
	@Override
	public void delete(Object obj){
		client.delete(obj);
	}
	
	@Override
	public <TargetType> int delete(Class<TargetType> obj) {
		int count = 0;
		List<TargetType> list = client.query(obj);
		for(TargetType type:list){
			client.delete(type);
			count++;
		}
		return count;
	}

	@Override
	public <TargetType> int delete(Class<TargetType> obj,
			HashMap<String, Object> map) {
		int count = 0;
		List<TargetType> list = query(obj,map);
		for(TargetType type:list){
			client.delete(type);
			count++;
		}
		return count;
	}

	@Override
	public <TargetType> List<TargetType> query(TargetType obj){
		return client.queryByExample(obj);
	}
	@Override
	public <TargetType> List<TargetType> query(Class<TargetType> obj){
		return client.query(obj);
	}
	@Override
	public <TargetType> List<TargetType> query(final Class<TargetType> obj,final HashMap<String,Object> map){
		
		return client.query(new Predicate<TargetType>(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean match(TargetType arg) {
				if(arg.getClass()!=obj)
					return false;
				Set<String> keySet = map.keySet();
				Iterator<String> keyIterator = keySet.iterator();
				while(keyIterator.hasNext()){
					String key = keyIterator.next();
					try {
						Object value = map.get(key);
						String methodName;
						if(value instanceof Boolean){
							methodName = "is"+key.substring(0,1).toUpperCase()+key.substring(1);
						}else{
							methodName = "get"+key.substring(0,1).toUpperCase()+key.substring(1);
						}
							
						Method method = arg.getClass().getDeclaredMethod(methodName);
						Object obj = method.invoke(arg);
						if(obj!=null&&!obj.equals(value)){
							return false;
						}
						else if(obj==null&&value!=null){
							return false;
						}
					} catch (Exception e) {
						// TargetType key error
						logger.error("The get Method for "+ key +" of "+arg+" Error");
					} 
				}
				return true;
			}
			
		});
	}

	public int execute(String nativeSql){
		//haven't implemented.
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <Type> List<Type> executeQuery(String nativeSql) {
		List<Type> list = null;
	    try {
	    	list = (List<Type>) Sql4o.execute(client, nativeSql);
        } catch (SqlParseException e) {
        	logger.error("sql parse exception:"+e.getMessage());
        } catch (Sql4oException e) {
			logger.error("sql execute exception:"+e.getMessage());
		};
		
		return list;
	}
	
}
