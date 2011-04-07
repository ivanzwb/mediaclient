package com.shijie.media.client.api.ui;

/**
 * @author Ivan
 * 事件
 */
public interface IEvent {
	
	/**
	 * 显示没有装饰的浏览器事件
	 */
	public static int NO_DECORATED_BROWSER = 2001;
	/**
	 * 显示装饰的浏览器事件
	 */
	public static int DECORATED_BROWSER = 2002;
	/**
	 *  显示装饰的电影播放事件
	 */
	public static int DECORATED_MOVIE_PLAYER = 2003;
	/**
	 *  显示装饰的音乐播放事件
	 */
	public static int DECORATED_MUSIC_PLAYER = 2003;

}
