package com.shijie.media.client.api.service;

import java.util.List;

import com.shijie.media.client.entity.DownloadTask;

/**
 * @author Ivan
 * 下载服务。
 */
public interface DownloadService extends IService{
	
	/**
	 * 下载服务ID
	 */
	public final static String ID = "DownloadService";
	/**
	 * 加入下载功能。
	 * @param task
	 */
	public void add (DownloadTask task);
	/**
	 * 取消下载功能。
	 * @param task
	 */
	public void cancel(DownloadTask task);
	/**
	 * 返回所有下载任务。
	 * @return
	 */
	public List<DownloadTask> getTasks();
	/**
	 * 取消所有的下载任务。
	 */
	public void cancel();
	/**
	 * 暂停下载的任务。
	 */
	public void suspend();
	/**
	 * 恢复下载的任务
	 */
	public void resume();
	/**
	 * 下载任务。
	 * @param task
	 * @return
	 */
	public boolean download(DownloadTask task);

}
