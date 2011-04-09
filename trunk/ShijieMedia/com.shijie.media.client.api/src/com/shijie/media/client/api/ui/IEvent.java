package com.shijie.media.client.api.ui;

/**
 * @author Ivan
 * 事件
 */
public interface IEvent {
	
	/**
	 * 显示没有装饰的浏览器事件
	 */
	public final static int NO_DECORATED_BROWSER = 2001;
	/**
	 * 显示装饰的浏览器事件
	 */
	public final static int DECORATED_BROWSER = 2002;
	/**
	 *  显示装饰的电影播放事件
	 */
	public final static int DECORATED_MOVIE_PLAYER = 2003;
	/**
	 *  显示装饰的音乐播放事件
	 */
	public final static int DECORATED_MUSIC_PLAYER = 2003;
	
	/**
	 * 执行命令的固定前缀。可以加事件，和参数，格式：command://event type[arg]
	 */
	public final static String COMMAND = "command://";
	
}
