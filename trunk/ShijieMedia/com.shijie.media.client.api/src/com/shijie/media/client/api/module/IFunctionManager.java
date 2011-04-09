package com.shijie.media.client.api.module;

import java.util.Collection;
import java.util.EventListener;

/**
 * @author Ivan
 * 管理所有功能菜单
 */
public interface IFunctionManager {
	
	/**
	 * 安装功能菜单。
	 * @param function
	 */
	public void installFunction(IFunction function);
	/**
	 * 卸载功能菜单。
	 * @param function
	 */
	public void uninstallFunction(IFunction function);
	
	/**
	 * 根据功能菜单id，和类型返回功能菜单。
	 * @param id
	 * @return
	 */
	public IFunction getFunction(String id);
	/**
	 * 根据功能类型返回功能菜单。
	 * @return
	 */
	public Collection<IFunction> getFunctions();
	
	/**
	 * 加入监听功能改变事件
	 * @param listener
	 */
	public void addFunctionChangeListener(EventListener listener);
	
	/**
	 * 移除监听功能改变事件
	 * @param listener
	 */
	public void removeFunctionChangeListener(EventListener listener);

}
