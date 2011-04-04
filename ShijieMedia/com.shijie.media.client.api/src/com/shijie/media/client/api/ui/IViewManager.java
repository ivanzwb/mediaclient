package com.shijie.media.client.api.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

/**
 * @author Ivan
 * 视图管理器
 */
public interface IViewManager {
	
	/**
	 * 安装视图。
	 * @param view
	 */
	public void installView (IView view);
	/**
	 * 返回平台视图组件。
	 * @return
	 */
	public JComponent getViews();
	/**
	 * 全屏只显示locs位置的视图。
	 * @param locs
	 */
	public void fullScreen(String ...locs);
	/**
	 * 退出全屏。
	 */
	public void exitFullScreen();
	/**
	 * 分发事件
	 * @param event
	 */
	public void postEvent(ActionEvent event);
	/**
	 * 注册事件
	 * @param listener
	 */
	public void registerActionListener(ActionListener listener);
	/**
	 * 移除事件。
	 * @param listener
	 */
	public void removeActionListener(ActionListener listener);

}
