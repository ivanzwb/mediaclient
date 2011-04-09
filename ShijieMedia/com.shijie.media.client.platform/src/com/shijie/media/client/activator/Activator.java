package com.shijie.media.client.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import com.shijie.media.client.api.service.IServiceManager;
import com.shijie.media.client.api.ui.ISkinManager;
import com.shijie.media.client.api.ui.IViewManager;

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
	
	public static IServiceManager getServiceManager(){
		return (IServiceManager) context.getService(context.getServiceReference(IServiceManager.class));
	}
	
	public static ISkinManager getSkinManager(){
		return (ISkinManager) context.getService(context.getServiceReference(ISkinManager.class));
	}
	
	public static IViewManager getViewManager(){
		return (IViewManager) context.getService(context.getServiceReference(IViewManager.class));
	}
	
}
