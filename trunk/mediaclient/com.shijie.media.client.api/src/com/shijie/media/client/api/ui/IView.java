package com.shijie.media.client.api.ui;

import javax.swing.JComponent;

import com.shijie.media.client.entity.Config;

/**
 * @author Ivan
 * 视图接口。
 */
public interface IView {
	
	/*
	 * ------------------------------------------| -------
	 * |   TW    |          TC            |   TE |  Title(T)
	 * |-----------------------------------------| -------
	 * |   CCNW  |   CCNC                |       |
	 * |         |                       |       |
	 * |---------------------------------|       |
	 * |         |                       |       |
	 * |         |                       |       | Content(C)
	 * |   CCW   |                       |       |
	 * |         |        CCCC           |  CEC  |
	 * |         |                       |       |
	 * |         |                       |       |
	 * |         |                       |       |
	 * |         |                       |       |
	 * |         |-----------------------|       |
	 * |         |       CCCS            |       |
	 * |-----------------------------------------| --------
	 * |   FW   |            FC         |   FE   |  Footer(F)
	 * ------------------------------------------| --------
	 * */
	
	/**
	 * 标题视图类型。
	 */
	public final static char TITLE= 'T';
	/**
	 * 内容视图类型。
	 */
	public final static char CONTENT= 'C';
	/**
	 * 底部视图类型。
	 */
	public final static char FOOTER= 'F';
	/**
	 * West标题视图
	 */
	public final static String TITLE_WEST = "TW";
	/**
	 * Center标题视图
	 */
	public final static String TITLE_CENTER = "TC";
	/**
	 * East标题视图
	 */
	public final static String TITLE_EAST = "TE";
	/**
	 * West底部视图
	 */
	public final static String FOOTER_WEST = "FW";
	/**
	 * Center底部视图
	 */
	public final static String FOOTER_CENTER = "FC";
	/**
	 * East底部视图
	 */
	public final static String FOOTER_EAST = "FE";
	/**
	 * East内容视图
	 */
	public final static String CONTENT_EAST = "CE";
	/**
	 * Center内容视图
	 */
	public final static String CONTENT_CENTER = "CC";
	/**
	 * Center North 内容视图
	 */
	public final static String CONTENT_CENTER_NORTH="CCN";
	/**
	 * Center West 内容视图
	 */
	public final static String CONTENT_CENTER_WEST = "CCW";
	/**
	 * East Center 内容视图
	 */
	public final static String CONTENT_EAST_CENTER = "CEC";
	/**
	 * Center Center 内容视图
	 */
	public final static String CONTENT_CENTER_CENTER = "CCC";
	/**
	 * Center North West 内容视图
	 */
	public final static String CONTENT_CENTER_NORTH_WEST = "CCNW";
	/**
	 * Center North Center 内容视图
	 */
	public final static String CONTENT_CENTER_NORTH_CENTER = "CCNC";
	/**
	 * Center North East 内容视图
	 */
	public final static String CONTENT_CENTER_NORTH_EAST = "CCNE";
	
	/**
	 * Center Center Center 内容视图
	 */
	public final static String CONTENT_CENTER_CENTER_CENTER = "CCCC";
	/**
	 * Center Center South 内容视图
	 */
	public final static String CONTENT_CENTER_CENTER_SOUTH = "CCCS";
	/**
	 * 初始化视图配置。
	 * @param config
	 */
	public void init(Config config);
	/**
	 * 返回视图组件。
	 * @return
	 */
	public JComponent getView();
	/**
	 * 返回视图位置。
	 * @return
	 */
	public String getLocation();
	/**
	 * 更新视图。
	 */
	public void updateView();
	/**
	 * 设置视图管理器。
	 * @param viewManager
	 */
	public void setViewManager(IViewManager viewManager);

}
