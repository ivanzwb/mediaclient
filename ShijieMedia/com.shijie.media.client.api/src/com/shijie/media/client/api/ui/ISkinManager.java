package com.shijie.media.client.api.ui;

import java.util.Collection;

/**
 * @author Ivan
 *  皮肤肤管理器。。
 */
public interface ISkinManager {
	/**
	 * 安装皮肤
	 * @param skin
	 */
	public void installSkin (ISkin skin);
	/**
	 * 改变当前皮肤。
	 * @param skin
	 */
	public void changeSkin(ISkin skin);
	/**
	 * 返回当前皮肤。
	 * @return
	 */
	public ISkin getSelectedSkin();
	/**
	 * 根据id返回皮肤。
	 * @param id
	 * @return
	 */
	public ISkin getSkin(String id);
	/**
	 * 返回所有的皮肤。
	 * @return
	 */
	public Collection<ISkin> getSkins();
}
