package com.shijie.media.client.platform;

import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.service.IService;
import com.shijie.media.client.api.service.IServiceManager;

public class ServiceManager implements IServiceManager {
	
	private Logger logger = LoggerFactory.getLogger(ServiceManager.class);
	
	private HashMap<String,IService> map = new HashMap<String,IService>();

	public ServiceManager(){
		logger.info("service manager instance created...");
	}
	
	@Override
	public void installService(IService service) {
		logger.info("installing "+service.getServiceID()+" to service manager...");
		map.put(service.getServiceID(), service);
		service.setServiceManager(this);
	}

	@Override
	public void uninstallService(IService service) {
		service.stop();
		service.setServiceManager(null);
		map.remove(service.getServiceID());

	}

	@Override
	public IService getService(String id) {
		return map.get(id);
	}
	
	public Collection<IService> getServices(){
		return map.values();
	}

}
