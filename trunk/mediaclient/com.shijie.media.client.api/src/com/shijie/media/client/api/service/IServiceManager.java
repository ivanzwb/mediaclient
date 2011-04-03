package com.shijie.media.client.api.service;

import java.util.Collection;

/**
 * @author Ivan
 * 服务管理器。
 */
public interface IServiceManager{
	/**
	 * 将服务加入管理器。
	 * @param service
	 */
	public void installService(IService service);
	/**
	 * 将服务从管理器移除。
	 * @param service
	 */
	public void uninstallService(IService service);
	
	/**
	 * 根据ID返回服务。
	 * @param id
	 * @return
	 */
	public IService getService(String id);
	
	/**
	 * 返回所有服务。
	 * @return
	 */
	public Collection<IService> getServices();
}
