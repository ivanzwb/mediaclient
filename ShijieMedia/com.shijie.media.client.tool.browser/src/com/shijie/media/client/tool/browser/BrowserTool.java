package com.shijie.media.client.tool.browser;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.shijie.media.client.api.module.IModule;
import com.shijie.media.client.api.module.ITool;
import com.shijie.media.client.api.ui.IEvent;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Category;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.entity.ConfigWrapper;

public class BrowserTool implements ITool {

	private IViewManager viewManager;
	private JButton toolBut;
	
	private String id;
	
	private String displayName;
	
	private URL iconURL;
	private URL selectedIconURL;
	private int order;
	
	private ImageIcon icon;
	private ImageIcon selectedIcon;
	
	public BrowserTool(){
		initDefault();
	}
	
	private void initDefault(){
		id = "browser";
		displayName = "";
		iconURL = this.getClass().getResource("/ICON-INF/"+id+".png");
		selectedIconURL = iconURL;
		order = 3;
	}
	
	@Override
	public void init() {
		Config config = new ConfigWrapper(Category.CAT_TOOL, getId()).load();
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
	public JComponent getToolView() {
		if(toolBut==null){
			toolBut = new JButton(getDisplayName(),getIcon());
			toolBut.setOpaque(false);
			toolBut.setContentAreaFilled(false);
			toolBut.setBorderPainted(false);
			toolBut.setMinimumSize(new Dimension(80,65));
			toolBut.setPreferredSize(new Dimension(80,65));
			toolBut.setMaximumSize(new Dimension(80,65));
			toolBut.setHorizontalAlignment(JButton.CENTER);
			toolBut.setVerticalTextPosition(JButton.BOTTOM);    
			toolBut.setHorizontalTextPosition(JButton.CENTER); 
			if(getSelectIcon()!=null)
				toolBut.setSelectedIcon(getSelectIcon());
			
			toolBut.addActionListener(new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent e) {
					final ActionEvent event = new ActionEvent(toolBut,IEvent.DECORATED_BROWSER,null);
		        	Runnable doRun = new Runnable(){
		    			@Override
		    			public void run() {
		    				viewManager.postEvent(event);
		    			}
		        	};
		        	SwingUtilities.invokeLater(doRun);
				}
			});
			
			toolBut.addFocusListener(new FocusListener(){
	
				@Override
				public void focusGained(FocusEvent e) {
					toolBut.setBorderPainted(true);
				}
	
				@Override
				public void focusLost(FocusEvent e) {
					toolBut.setBorderPainted(false);
				}
			});
			
			toolBut.addMouseListener(new MouseAdapter(){
	
				@Override
				public void mouseEntered(MouseEvent e) {
					toolBut.setBorderPainted(true);
					toolBut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					toolBut.setBorderPainted(false);
					toolBut.setCursor(Cursor.getDefaultCursor());
				}
				
			});
		}
		return toolBut;
	}

}
