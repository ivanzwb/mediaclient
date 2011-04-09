package com.shijie.media.client.platform.ui.title;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;

import com.shijie.media.client.api.ui.IEvent;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.platform.Constraints;

public class TEAdv implements IView{
	private JLabel label;
	private List<String> advList;
	private List<String> linkList;
	private int scrollTime = 1;
	private int i = 0;
	private IViewManager viewManager;

	@SuppressWarnings("unchecked")
	@Override
	public void init(Config config) {
		advList = (List<String>)config.getProps().get(Constraints.TE_ADV_LIST);
		linkList = (List<String>)config.getProps().get(Constraints.TE_LINK_LIST);
		scrollTime = (Integer) config.getProps().get(Constraints.TE_SCROLLTIME);
	}

	@Override
	public JComponent getView() {
		if(label==null){
			String adv = "";
			if(advList!=null&&advList.size()>0)
				adv = advList.get(0);
			label = new JLabel(adv);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setName(TITLE_EAST);
			label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			Timer timer = new Timer(scrollTime*1000,new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent e) {
					label.setText(advList.get(i=i%advList.size()));
					i++;
				}
			});
			label.addMouseListener(new MouseAdapter(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
					ActionEvent event = new ActionEvent(label, IEvent.DECORATED_BROWSER, linkList.get(i-1));
					viewManager.postEvent(event);
				}
				
			});
			timer.setCoalesce(false);
			timer.setRepeats(true);
			timer.start();
		}
		return label;
	}

	@Override
	public String getLocation() {
		return TITLE_EAST;
	}

	@Override
	public void updateView() {
		label.updateUI();
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}

}
