package com.shijie.media.client.service.jetty;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.eclipse.equinox.http.helper.BundleEntryHttpContext;
import org.eclipse.equinox.http.helper.ContextPathServletAdaptor;
import org.eclipse.equinox.jsp.jasper.JspServlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.service.IServiceManager;
import com.shijie.media.client.api.service.WebService;

public class JettyService implements WebService{
	
	private Logger logger = LoggerFactory.getLogger(JettyService.class);
	private HttpContext commonContext;
	public JettyService(){
		
	}

	private HttpService getHttpService(BundleContext bundleContext){
		ServiceReference<HttpService> reference = bundleContext.getServiceReference(HttpService.class);
		HttpService httpService = bundleContext.getService(reference);
		return httpService;
	}
	
	@Override
	public void registerWebContext(BundleContext bundleContext,String context, String resourcePath) {
		
		try {
			HttpService httpService = getHttpService(bundleContext);
			
			commonContext = new BundleEntryHttpContext(bundleContext.getBundle(), resourcePath);
			httpService.registerResources(context, "/", commonContext); 

			Servlet adaptedJspServlet = new ContextPathServletAdaptor(new JspServlet(bundleContext.getBundle(), resourcePath), context); 
			httpService.registerServlet(context+"/*.jsp", adaptedJspServlet, null, commonContext); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void registerServlet(BundleContext bundleContext, String requestPath, Servlet servlet) {
		try {
			HttpService httpService = getHttpService(bundleContext);
			httpService.registerServlet(requestPath, servlet, null, commonContext);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (NamespaceException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setServiceManager(IServiceManager serviceManager) {
		
	}

	@Override
	public void unregisterWebContext(BundleContext bundleContext,String context, String resourcePath) {
		
	}

	@Override
	public void unregisterServlet(BundleContext bundleContext,String requestPath, Servlet servlet) {
		
	}

	@Override
	public String getServiceID() {
		return "WebService";
	}
	
	
	@Override
	public void init() {
		logger.info("init Web Service...");
	}

	@Override
	public void start() {
		logger.info("start Web Service...");
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
