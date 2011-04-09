package com.shijie.media.client.service.db4o;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{
	
	public static BundleContext context = null;

	@Override
	public void start(BundleContext arg0) throws Exception {
		context = arg0;
		
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		
		
	}

}
