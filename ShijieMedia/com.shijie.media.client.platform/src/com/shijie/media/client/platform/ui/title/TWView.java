package com.shijie.media.client.platform.ui.title;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.platform.Constraints;

public class TWView implements IView{
	
	private JLabel label;
	private String version;

	@Override
	public void init(Config config) {
		version = (String) config.getProps().get(Constraints.TW_VERSION);
	}

	
	@Override
	public JComponent getView() {
		if(label==null){
			label = new JLabel(" "+version);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setName(TITLE_WEST);
		}
		return label;
	}

	@Override
	public String getLocation() {
		return TITLE_WEST;
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		;		
	}


	@Override
	public void updateView() {
		label.setText(" "+version);
	}
	
}
