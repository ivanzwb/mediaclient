package com.shijie.media.client.tray.time;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.shijie.media.client.api.module.IModule;
import com.shijie.media.client.api.module.ITray;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;

public class TimeTray implements ITray {

	private IViewManager viewManager;
	private JLabel trayLabel;
	
	private String id;
	
	private String displayName;
	
	private URL iconURL;
	private URL selectedIconURL;
	private int order;
	
	private ImageIcon icon;
	private ImageIcon selectedIcon;
	
	public TimeTray(){
		initDefault();
	}
	
	private void initDefault(){
		id = "time";
		displayName = "";
		iconURL = this.getClass().getResource("/ICON-INF/"+id+".png");
		selectedIconURL = iconURL;
		order = 3;
		
	}
	
	@Override
	public void init(Config config) {
		if(config!=null){
			id = (String) ignoreNull(config.getProps().get(IModule.MODULE_ID),id);
			displayName = (String)ignoreNull(config.getProps().get(IModule.MODULE_DISPLAYNAME),displayName);
			iconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_ICON),iconURL);
			selectedIconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_SELECTEDICON),selectedIconURL);
			order = (Integer)ignoreNull(config.getProps().get(IModule.MODULE_ORDER),order);	
		}
		
		icon = new ImageIcon(iconURL);
		selectedIcon = new ImageIcon(selectedIconURL);
	}
	
	private Object ignoreNull(Object v,Object d){
		if(v == null)
			return d;
		else 
			return v;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public Icon getSelectIcon() {
		return selectedIcon;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}

	@Override
	public JComponent getTrayView() {
		if(trayLabel==null){
			trayLabel = new JLabel(getDisplayName(),getIcon(),JLabel.CENTER);
			trayLabel.setOpaque(false);
			trayLabel.setMinimumSize(new Dimension(60,60));
			trayLabel.setPreferredSize(new Dimension(60,60));
			trayLabel.setMaximumSize(new Dimension(60,60));
			trayLabel.setVerticalTextPosition(JLabel.BOTTOM);    
			trayLabel.setHorizontalTextPosition(JLabel.CENTER); 
			
			
			trayLabel.addFocusListener(new FocusListener(){
	
				@Override
				public void focusGained(FocusEvent e) {
					
				}
	
				@Override
				public void focusLost(FocusEvent e) {
					
				}
			});
			
			trayLabel.addMouseListener(new MouseAdapter(){
	
				@Override
				public void mouseEntered(MouseEvent e) {
					
					trayLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					
					trayLabel.setCursor(Cursor.getDefaultCursor());
				}
				
			});
		}
		return trayLabel;
	}

}
