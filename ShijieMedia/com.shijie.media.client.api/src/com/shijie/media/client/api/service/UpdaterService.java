package com.shijie.media.client.api.service;

import java.util.List;

/**
 * @author Ivan
 * 更新服务。
 */
public interface UpdaterService extends IService{
	
	/**
	 * 更新服务ID
	 */
	public final static String ID = "UpdaterService";
	/**
	 * 检查并更新。
	 */
	public void checkAndUpdate();	
	
	/**
	 * 检查并返回所有更新地址
	 * @return
	 */
	public List<String> checkUpdate();
	/**
	 * 根据更新地址安装模块。
	 * @param name
	 */
	public void install(String url);
	/**
	 * 根据名称卸模块。
	 * @param name
	 */
	public void uninstall(String name);
	/**
	 * 根据更新地址升级模块。
	 * @param name
	 */
	public void update(String url);

}
