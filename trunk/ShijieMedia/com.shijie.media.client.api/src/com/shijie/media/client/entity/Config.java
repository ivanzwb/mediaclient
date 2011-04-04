package com.shijie.media.client.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Ivan
 * 配置类
 */
public class Config implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3010728633980596648L;
	
	/**
	 * 配置名称
	 */
	private String name;
	/**
	 * 配置代码
	 */
	private String code;
	/**
	 * 配置类型
	 */
	private Category category;
	/**
	 * 配置的属性和值。
	 */
	private HashMap<String, Object> props;
	/**
	 * 配置是否启用。
	 */
	private boolean enabled;
	/**
	 * 配置描述。
	 */
	private String description;
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
	public Category getCategory() {
		return category;
	}
	/**
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	/**
	 * @return
	 */
	public HashMap<String, Object> getProps() {
		return props;
	}
	/**
	 * @param props
	 */
	public void setProps(HashMap<String, Object> props) {
		this.props = props;
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
	public String getDescription() {
		return description;
	}
	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
