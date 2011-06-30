package com.shijie.media.client.entity;

import java.util.HashMap;
import java.util.List;

import com.shijie.media.client.api.activator.Activator;
import com.shijie.media.client.api.service.DBService;

public class ConfigWrapper{
	
	private String id;
	private String module;
	private DBService dbService;
	
	public ConfigWrapper(String module,String id){
		this.id = id;
		this.module = module; 
		dbService = (DBService) Activator.getServiceManager().getService(DBService.ID);
	}

	public String getId() {
		return this.id;
	}
	
	public String getModule(){
		return this.module;
	}

	public Config load() {
		return getConfig(dbService,getModule(),getId());
	}

	public void store(Config config) {
		try {
			dbService.openConnection();
			dbService.store(config);
			dbService.commit();
		} finally {
			dbService.closeConnection();
		}

	}
	
	public Config getConfig(DBService dbService,String categoryCode,String configCode){
		
		dbService.openConnection();
		try{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("code", categoryCode);
			List<Category> catList = dbService.query(Category.class,map);
			Category category = null;
			if(catList.size()==0){
				return null;
			}else{
				category = catList.get(0);
			}
			
			map.clear();
			
			map.put("code", configCode);
			map.put("category", category);
			List<Config> conList = dbService.query(Config.class,map);
			if(conList.size()==0){
				return null;
			}else{
				return conList.get(0);
			}
			
		}finally{
			dbService.closeConnection();
		}
	}
	
	

}
