package com.shijie.media.client.api.module;

import java.util.Collection;
import java.util.EventListener;

public interface ITrayManager {
	
	/**
	 * 安装托盘菜单。
	 * @param Tray
	 */
	public void installTray(ITray tray);
	/**
	 * 卸载托盘菜单。
	 * @param Tray
	 */
	public void uninstallTray(ITray tray);
	
	/**
	 * 根据托盘菜单id返回功能菜单。
	 * @param id
	 * @return
	 */
	public ITray getTray(String id);
	/**
	 * 根据托盘返回功能菜单。
	 * @return
	 */
	public Collection<ITray> getTrays();
	
	/**
	 * 加入监听托盘改变事件
	 * @param listener
	 */
	public void addTrayChangeListener(EventListener listener);
	
	/**
	 * 移除监听托盘改变事件
	 * @param listener
	 */
	public void removeTrayChangeListener(EventListener listener);


}
