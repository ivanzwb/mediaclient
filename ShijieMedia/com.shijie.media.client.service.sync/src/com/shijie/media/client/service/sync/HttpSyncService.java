package com.shijie.media.client.service.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.service.IServiceManager;
import com.shijie.media.client.api.service.SyncService;
import com.shijie.media.client.entity.Module;

public class HttpSyncService implements SyncService {

	private Logger logger = LoggerFactory.getLogger(HttpSyncService.class);

	public HttpSyncService() {

	}

	@Override
	public void init() {
		logger.info("init HTTP sync service...");

	}

	@Override
	public void start() {
		logger.info("start HTTP sync service...");

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getServiceID() {
		return "SyncService";
	}

	@Override
	public void setServiceManager(IServiceManager serviceManager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sync(Module module) {
		// TODO Auto-generated method stub

	}

}
