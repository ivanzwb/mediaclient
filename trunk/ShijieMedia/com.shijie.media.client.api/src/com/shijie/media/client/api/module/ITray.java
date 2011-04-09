package com.shijie.media.client.api.module;

import javax.swing.JComponent;

import com.shijie.media.client.api.ui.IViewManager;

public interface ITray extends IModule{
	
	/**
	 * 设置视图管理器。
	 * @param viewManager
	 */
	public void setViewManager(IViewManager viewManager);
	/**
	 * 返回托盘组件。
	 * @return
	 */
	public JComponent getTrayView();
	
}
