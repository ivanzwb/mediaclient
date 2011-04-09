package com.shijie.media.client.starter;

import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.activator.Activator;
import com.shijie.media.client.api.ui.IPlatform;
import com.shijie.media.client.platform.PlatformComponent;

public class Bootstrap {
	private Logger logger = LoggerFactory.getLogger(PlatformComponent.class);
	
	private IPlatform platformComponent;
	
	public Bootstrap(){
		logger.info("loading all the bundles....");
		Activator.context.addFrameworkListener(new FrameworkListener(){

			@Override
			public void frameworkEvent(FrameworkEvent arg0) {
				if(arg0.getType()==FrameworkEvent.STARTED){
					logger.info("complete loading bundles...");
					platformComponent.launch();
				}
			}});
	}
	
	public void startup(IPlatform platformComponent){
		this.platformComponent = platformComponent;
	}
	
	public void shutdown(IPlatform platformComponent){
		this.platformComponent = null;
	}
	
	public IPlatform getPlatformComponent() {
		return platformComponent;
	}

}
