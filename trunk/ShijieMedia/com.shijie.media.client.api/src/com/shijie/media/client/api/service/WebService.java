package com.shijie.media.client.api.service;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;

/**
 * @author Ivan
 * Web服务
 */
public interface WebService extends IService{
	
	/**
	 * Web服务ID
	 */
	public final static String ID = "WebService";
	
	/**
	 * 注册Web服务上下文和对应的资源路径。
	 * @param bundleContext
	 * @param context
	 * @param resourcePath
	 */
	public void registerWebContext(BundleContext bundleContext,String context, String resourcePath);
	/**
	 * 卸载Web服务上下文和对应的资源路径。
	 * @param bundleContext
	 * @param context
	 * @param resourcePath
	 */
	public void unregisterWebContext(BundleContext bundleContext,String context, String resourcePath);
	/**
	 * 注册Web Servlet服务
	 * @param bundleContext
	 * @param requestPath
	 * @param servlet
	 */
	public void registerServlet(BundleContext bundleContext,String requestPath,Servlet servlet);
	/**
	 * 卸载Web Servlet服务
	 * @param bundleContext
	 * @param requestPath
	 * @param servlet
	 */
	public void unregisterServlet(BundleContext bundleContext,String requestPath,Servlet servlet);


}
