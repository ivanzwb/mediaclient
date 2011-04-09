package com.shijie.media.client.api.ui;

import com.shijie.media.client.api.service.IServiceManager;

/**
 * @author Ivan
 * 平台接口。
 */
public interface IPlatform {
	
	/**
	 * 安装视图管理器。
	 * @param viewManager
	 */
	public void installViewMananger(IViewManager viewManager);
	/**
	 * 安装皮肤管理器。
	 * @param skinMananger
	 */
	public void installSkinMananger(ISkinManager skinMananger);
	/**
	 * 安装服务管理器。
	 * @param serviceManager
	 */
	public void installServiceManager(IServiceManager serviceManager);
	/**
	 * 加载平台
	 */
	public void launch();


}
