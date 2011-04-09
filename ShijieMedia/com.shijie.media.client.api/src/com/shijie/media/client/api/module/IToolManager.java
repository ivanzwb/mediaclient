package com.shijie.media.client.api.module;

import java.util.Collection;
import java.util.EventListener;

public interface IToolManager {
	
	/**
	 * 安装工具菜单。
	 * @param function
	 */
	public void installTool(ITool function);
	/**
	 * 卸载工具菜单。
	 * @param function
	 */
	public void uninstallTool(ITool function);
	
	/**
	 * 根据功能菜单id，返回工具菜单。
	 * @param id
	 * @return
	 */
	public ITool getTool(String id);
	/**
	 * 返回功能菜单。
	 * @return
	 */
	public Collection<ITool> getTools();
	
	/**
	 * 加入监听工具改变事件
	 * @param listener
	 */
	public void addToolChangeListener(EventListener listener);
	
	/**
	 * 移除监听工具改变事件
	 * @param listener
	 */
	public void removeToolChangeListener(EventListener listener);

}
