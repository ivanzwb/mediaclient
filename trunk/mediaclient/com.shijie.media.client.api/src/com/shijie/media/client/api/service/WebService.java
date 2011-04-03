package com.shijie.media.client.api.service;

import javax.servlet.Servlet;

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
	 * @param context
	 * @param resourcePath
	 */
	public void registerWebContext(String context, String resourcePath);
	/**
	 * 卸载Web服务上下文和对应的资源路径。
	 * @param context
	 * @param resourcePath
	 */
	public void unregisterWebContext(String context, String resourcePath);
	/**
	 * 注册Web Servlet服务
	 * @param requestPath
	 * @param servlet
	 */
	public void registerServlet(String requestPath,Servlet servlet);
	/**
	 * 卸载Web Servlet服务
	 * @param requestPath
	 * @param servlet
	 */
	public void unregisterServlet(String requestPath,Servlet servlet);


}
