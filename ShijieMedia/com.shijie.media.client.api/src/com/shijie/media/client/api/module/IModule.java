package com.shijie.media.client.api.module;

import javax.swing.Icon;

/**
 * 所有模块功能的接口。
 * @author Ivan
 *
 */
public interface IModule {
	
	/**
	 * 模块ID标识。
	 */
	public final static String MODULE_ID = "module.id";
	/**
	 * 模功模块导航地址。
	 */
	public final static String MODULE_URL = "module.url";
	/**
	 * 模块显示名称。
	 */
	public final static String MODULE_DISPLAYNAME = "module.dispayname";
	/**
	 * 模块图标
	 */
	public final static String MODULE_ICON = "module.icon";
	/**
	 * 模块选中图标。
	 */
	public final static String MODULE_SELECTEDICON = "module.selectedicon";
	/**
	 * 模块显示顺序.
	 */
	public final static String MODULE_ORDER = "module.order";
	
	/**
	 * 初始化模块配置。
	 * @param config
	 */
	public void init();
	
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
	 * 返回显示菜单显示顺序。
	 * @return
	 */
	public int getOrder();

}
