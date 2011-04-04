package com.shijie.media.client.api.service;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author Ivan
 * 计划任务服务。
 */
public interface TimerService extends IService {

	/**
	 * 计划任务服务ID
	 */
	public final static String ID = "TimerService";
	/**
	 * 计划任务task在延迟delay后开始。
	 * @param task
	 * @param delay
	 */
	public void schedule(TimerTask task, long delay);

	/**
	 * 计划任务task在time执行。
	 * @param task
	 * @param time
	 */
	public void schedule(TimerTask task, Date time);

	/**
	 * 计划任务task在延迟delay后以period周期开始。
	 * @param task
	 * @param delay
	 * @param period
	 */
	public void schedule(TimerTask task, long delay, long period);

	/**
	 * 计划任务task在time以period周期开始执行。
	 * @param task
	 * @param firstTime
	 * @param period
	 */
	public void schedule(TimerTask task, Date firstTime, long period);

	/**
	 * 取消计划任务task。
	 * @param task
	 */
	public void cancel(TimerTask task);

	/**
	 * 根据ID返回计划任务。
	 * @param id
	 * @return
	 */
	public TimerTask getTask(String id);

	/**
	 * 取消所有计划任务task。
	 */
	public void clear();

}
