package com.shijie.media.client.api.module;

import java.util.List;

import javax.swing.Icon;

import com.shijie.media.client.entity.Config;

/**
 * @author Ivan
 * 功能接口
 */
public interface IFunction {
	
	/**
	 * 菜单功能。
	 */
	public static final int FUNCTION = 0;
	/**
	 * 客制化功能。
	 */
	public static final int CUS_FUNCTION = 1;
	/**
	 * 工具。
	 */
	public static final int TOOL = 2;
	/**
	 * 托盘。
	 */
	public static final int TRAY = 3;
	
	/**
	 * @param config
	 */
	public void init(Config config);
	
	/**
	 * 返回显示名称。
	 * @return
	 */
	public String getDisplayName();
	/**
	 * 返回功能的标识。
	 * @return
	 */
	public String getId();
	/**
	 * @return
	 */
	public int getType();
	
	/**
	 * 返回显示的图标。
	 * @return
	 */
	public Icon getIcon();
	/**
	 * 返回选中显示的图标。
	 * @return
	 */
	public Icon getSelectIcon();
	/**
	 * 返回事件链接
	 * @return
	 */
	public String getLink();
	/**
	 * 返回事件子功能菜单。
	 * @return
	 */
	public List<IFunction> getSubFunctions();
	/**
	 * 加入子功能菜单。
	 * @param function
	 */
	public void addSubFunction(IFunction function);
	/**
	 * 加入子功能菜单。
	 * @param functions
	 */
	public void addSubFunctions(List<IFunction> functions);
	/**
	 * 返回显示菜单显示顺序。
	 * @return
	 */
	public int getOrder();

}
