package com.shijie.media.client.api.service;

import com.shijie.media.client.entity.Module;

/**
 * @author Ivan
 * 同步服务。
 */
public interface SyncService extends IService{
	
	/**
	 * 同步服务ID
	 */
	public final static String ID = "SyncService";
	/**
	 * 同步module模块
	 * @param module
	 */
	public void sync(Module module);
}
