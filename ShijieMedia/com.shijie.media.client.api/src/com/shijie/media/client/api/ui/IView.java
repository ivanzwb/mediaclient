package com.shijie.media.client.api.ui;

import javax.swing.JComponent;

/**
 * @author Ivan
 * 视图接口。
 */
public interface IView {
	
	/*
	 * ------------------------------------------| -------
	 * |   TW    |          TC            |   TE |  Title(T)
	 * |-----------------------------------------| -------
	 * |   LN    |    CN                 |       |
	 * |         |                       |       |
	 * |---------|-----------------------|       |
	 * |         |                       |       |
	 * |         |                       |       | Content(C)
	 * |   LC    |                       |       |
	 * |         |        CC             |  RC   |
	 * |         |                       |       |
	 * |         |                       |       |
	 * |         |                       |       |
	 * |         |                       |       |
	 * |         |-----------------------|       |
	 * |         |       CS              |       |
	 * |-----------------------------------------| --------
	 * |   FW   |            FC         |   FE   |  Footer(F)
	 * ------------------------------------------| --------
	 * */
	
	/**
	 * 标题视图类型。
	 */
	public final static char TITLE= 'T';
	/**
	 * 左视图类型。
	 */
	public final static char LEFT= 'L';
	/**
	 * 右视图类型。
	 */
	public final static char RIGHT= 'R';
	/**
	 * 底部视图类型。
	 */
	public final static char FOOTER= 'F';
	/**
	 * 中央视图类型。
	 */
	public final static char CENTER= 'C';
	
	
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
	 * Left North内容视图
	 */
	public final static String LEFT_NORTH = "LN";
	/**
	 * Left Center内容视图
	 */
	public final static String LEFT_CENTER = "LC";
	
	
	/**
	 * Right Center内容视图
	 */
	public final static String RIGHT_CENTER = "RC";
	
	
	
	/**
	 * Center North内容视图
	 */
	public final static String CENTER_NORTH = "CN";
	/**
	 * Center Center 内容视图
	 */
	public final static String CENTER_CENTER="CC";
	/**
	 * Center South 内容视图
	 */
	public final static String CENTER_SOUTH="CS";
	
	/**
	 * 弹出视图
	 */
	public final static String POPUP = "P";
	
	/**
	 * 初始化视图配置。
	 * @param config
	 */
	public void init();
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
