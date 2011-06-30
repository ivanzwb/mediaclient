package com.shijie.media.client.api.service;



/**
 * @author Ivan
 * 服务接口。
 */
public interface IService {
	
	/**
	 * 初始化服务配置。
	 * @param config
	 */
	public void init();
	
	/**
	 * 启动服务。
	 */
	public void start();
	/**
	 * 停止服务。
	 */
	public void stop();
	
	/**
	 * 返回服务ID
	 * @return
	 */
	public String getServiceID();
	/**
	 * 设置服务的管理器。
	 * @param serviceManager
	 */
	public void setServiceManager(IServiceManager serviceManager);
	
	

}
