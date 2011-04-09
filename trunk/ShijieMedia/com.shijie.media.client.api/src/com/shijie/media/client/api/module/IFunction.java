package com.shijie.media.client.api.module;


/**
 * @author Ivan
 * 功能接口
 */
public interface IFunction extends IModule{
	
	/**
	 * 普通功能
	 */
	int FUNCTION = 0;
	
	/**
	 * 客制化功能
	 */
	int CUS_FUNCTION = 1;

	/**
	 * @return
	 */
	public int getType();
	
	/**
	 * 注册网络资源
	 */
	public void registerWeb();
	
	/**
	 * 返回事件链接
	 * @return
	 */
	public String getLink();

}
