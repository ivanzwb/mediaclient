package com.shijie.media.client.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;

public class Activator implements BundleActivator{
	public static BundleContext context;
	@Override
	public void start(BundleContext arg0) throws Exception {
		context = arg0;
		NativeInterface.open();
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		NativeInterface.runEventPump();
	}
	
}
