package com.shijie.media.client.service.bundleupdater;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.service.IServiceManager;
import com.shijie.media.client.api.service.UpdaterService;
import com.shijie.media.client.entity.Config;

public class BundleUpdaterService implements UpdaterService{

	private Logger logger = LoggerFactory.getLogger(BundleUpdaterService.class);
	
	@Override
	public void init(Config config) {
		logger.info("init bundle updater service...");
		
	}

	@Override
	public void start() {
		logger.info("start bundle updater service...");
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getServiceID() {
		return "UpdaterService";
	}

	@Override
	public void setServiceManager(IServiceManager serviceManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkAndUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void install(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uninstall(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> checkUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

}
