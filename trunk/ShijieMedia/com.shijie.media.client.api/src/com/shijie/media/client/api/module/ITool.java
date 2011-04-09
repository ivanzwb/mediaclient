package com.shijie.media.client.api.module;

import javax.swing.JComponent;

import com.shijie.media.client.api.ui.IViewManager;

public interface ITool extends IModule{
	
	/**
	 * 设置视图管理器。
	 * @param viewManager
	 */
	public void setViewManager(IViewManager viewManager);
	/**
	 * 返回功具组件。
	 * @return
	 */
	public JComponent getToolView();
}
