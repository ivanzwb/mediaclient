package com.shijie.media.client.entity;

import java.io.Serializable;

/**
 * @author Ivan
 * 配置分类。
 */
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1257876405452706398L;
	
	public static final String CAT_PLATFROM = "CAT_PLATFROM";
	public static final String CAT_NETWORK = "CAT_NETWORK";
	public static final String CAT_SERVICE = "CAT_SERVICE";
	public static final String CAT_FUNCTION = "CAT_FUNCTION";
	public static final String CAT_CUSTOMER_FUNCTION = "CAT_CUSTOMER_FUNCTION";
	public static final String CAT_TOOL = "CAT_TOOL";
	public static final String CAT_TRAY = "CAT_TRAY";
	public static final String CAT_UI_VIEW = "CAT_UI_VIEW";
	public static final String CAT_USER_RIGHT = "CAT_USER_RIGHT";
	
	
	
	/**
	 * 配置分类名称。
	 */
	private String name;
	/**
	 * 配置分类代码
	 */
	private String code;
	/**
	 * 配置分类是否启用。
	 */
	private boolean enabled;
	/**
	 * 配置分类描述。
	 */
	private String discription;
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return
	 */
	public String getDiscription() {
		return discription;
	}
	/**
	 * @param discription
	 */
	public void setDiscription(String discription) {
		this.discription = discription;
	}
}
