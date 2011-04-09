package com.shijie.media.client.utils;

import java.util.HashMap;
import java.util.List;

import com.shijie.media.client.activator.Activator;
import com.shijie.media.client.api.service.DBService;
import com.shijie.media.client.entity.Category;
import com.shijie.media.client.entity.Config;

public class DBUtils {
	
	public static Config getConfig(DBService dbService,String categoryCode,String configCode){
		
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
	
	public static Config getConfig(String catCode,String configCode){
		DBService dbService = (DBService) Activator.getServiceManager().getService(DBService.ID);
		return DBUtils.getConfig(dbService,catCode,configCode);
	}

}
