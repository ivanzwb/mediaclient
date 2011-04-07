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
	 * @param type
	 * @return
	 */
	public IFunction getFunction(String id,int type);
	/**
	 * 根据功能类型返回功能菜单。
	 * @param type
	 * @return
	 */
	public Collection<IFunction> getFunctions(int type);
	
	/**
	 * @param type
	 * @param listener
	 */
	public void addFunctionChangeListener(int type,EventListener listener);
	
	/**
	 * @param type
	 * @param listener
	 */
	public void removeFunctionChangeListener(int type,EventListener listener);

}
