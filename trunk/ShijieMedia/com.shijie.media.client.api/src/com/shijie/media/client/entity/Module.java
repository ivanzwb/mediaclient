package com.shijie.media.client.entity;

import java.util.Date;
import java.util.List;

/**
 * @author Ivan
 *
 */
public class Module {
	/**
	 * 同步模块代码。
	 */
	private String code;
	/**
	 * 同步模块名称。
	 */
	private String name;
	/**
	 * 同步模块同步时间列表
	 */
	private List<Date> syncTimeList;
	/**
	 * 上次同步时间。
	 */
	private Date lastTime;
	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Date> getSyncTimeList() {
		return syncTimeList;
	}
	public void setSyncTimeList(List<Date> syncTimeList) {
		this.syncTimeList = syncTimeList;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

}
