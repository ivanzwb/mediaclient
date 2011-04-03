package com.shijie.media.client.entity;

public class DownloadTask {
	
	/**
	 * 下载任务状态完成。
	 */
	public final static int COMPLETED = 1;
	/**
	 * 下载任务状态未完成。
	 */
	public final static int PENDDING = 0;
	
	/**
	 * 下载任务ID。
	 */
	private String id;
	/**
	 * 下载任务状态。
	 */
	private int status;
	/**
	 * 下载远程地址。
	 */
	private String remoteURL;
	/**
	 * 下载本地地址
	 */
	private String localURL;
	/**
	 * 下载任务大小。
	 */
	private long size;
	/**
	 * 下载任务花费时间。
	 */
	private long cost;
	/**
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return
	 */
	public String getRemoteURL() {
		return remoteURL;
	}
	/**
	 * @param remoteURL
	 */
	public void setRemoteURL(String remoteURL) {
		this.remoteURL = remoteURL;
	}
	/**
	 * @return
	 */
	public String getLocalURL() {
		return localURL;
	}
	/**
	 * @param localURL
	 */
	public void setLocalURL(String localURL) {
		this.localURL = localURL;
	}
	/**
	 * @return
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size
	 */
	public void setSize(long size) {
		this.size = size;
	}
	/**
	 * @return
	 */
	public long getCost() {
		return cost;
	}
	/**
	 * @param cost
	 */
	public void setCost(long cost) {
		this.cost = cost;
	}
}
