package com.shijie.media.client.platform.ui.left;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.platform.Constraints;

public class LNView implements IView{

	private JLabel logo;
	private String url;
	private ImageIcon icon;
	@Override
	public void init(Config config) {
		url = (String) config.getProps().get(Constraints.LN_LOGO);
	}
	
	@Override
	public JComponent getView() {
		if(logo==null){
			logo = new JLabel();
			icon = new ImageIcon(url);
			logo.setIcon(icon);
			logo.setPreferredSize(new Dimension(210,130));
		}
		return logo;
	}

	@Override
	public String getLocation() {
		return LEFT_NORTH;
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		;
		
	}

	@Override
	public void updateView() {
		icon = new ImageIcon(url);
		logo.updateUI();
		
	}

}
