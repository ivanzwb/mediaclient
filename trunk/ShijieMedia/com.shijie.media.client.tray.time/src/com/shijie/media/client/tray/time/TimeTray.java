package com.shijie.media.client.tray.time;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

import com.shijie.media.client.api.module.IModule;
import com.shijie.media.client.api.module.ITray;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.tray.time.components.CalendarPanel;

public class TimeTray implements ITray {

	private static final Object TIME_FORMAT = "time.format";
	private static final Object DATE_FORMAT = "date.format";
	
	private IViewManager viewManager;
	private JPanel trayPanel;
	private JLabel timeLabel;
	private JLabel dateLabel;
	
	private String id;
	
	private String displayName;
	
	private URL iconURL;
	private URL selectedIconURL;
	private int order;
	private String timeFt;
	private String dateFt;
	
	private ImageIcon icon;
	private ImageIcon selectedIcon;
	
	private DateFormat timeDf;
	private DateFormat dateDf;
	
	public TimeTray(){
		initDefault();
	}
	
	private void initDefault(){
		id = "time";
		displayName = "";
		iconURL = this.getClass().getResource("/ICON-INF/"+id+".png");
		selectedIconURL = iconURL;
		order = 3;
		timeFt = "hh:mm:ss";
		dateFt = "yyyy/MM/dd";
		
	}
	
	@Override
	public void init(Config config) {
		if(config!=null){
			id = (String) ignoreNull(config.getProps().get(IModule.MODULE_ID),id);
			displayName = (String)ignoreNull(config.getProps().get(IModule.MODULE_DISPLAYNAME),displayName);
			iconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_ICON),iconURL);
			selectedIconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_SELECTEDICON),selectedIconURL);
			order = (Integer)ignoreNull(config.getProps().get(IModule.MODULE_ORDER),order);	
			
			timeFt = (String)ignoreNull(config.getProps().get(TimeTray.TIME_FORMAT),timeFt);
			dateFt = (String)ignoreNull(config.getProps().get(TimeTray.DATE_FORMAT),dateFt);
		}
		
		icon = new ImageIcon(iconURL);
		selectedIcon = new ImageIcon(selectedIconURL);
		
		timeDf = new SimpleDateFormat(timeFt);
		dateDf = new SimpleDateFormat(dateFt);
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
		if(trayPanel==null){
			trayPanel = new JPanel();
			trayPanel.setLayout(new BoxLayout(trayPanel, BoxLayout.Y_AXIS));
			trayPanel.add(Box.createVerticalStrut(20));
			Calendar cal = Calendar.getInstance();

			timeLabel = new JLabel(timeDf.format(cal.getTime()),JLabel.CENTER);
			timeLabel.setOpaque(false);
			trayPanel.add(timeLabel);

			dateLabel = new JLabel(dateDf.format(cal.getTime()),JLabel.CENTER);
			dateLabel.setOpaque(false);
			trayPanel.add(dateLabel);
			
			final JPopupMenu menuPopup = new JPopupMenu();
			
			CalendarPanel calendarPanel = new CalendarPanel();
			menuPopup.add(calendarPanel);
			
			trayPanel.add(menuPopup);
			
			trayPanel.setMinimumSize(new Dimension(60,60));
			trayPanel.setPreferredSize(new Dimension(60,60));
			trayPanel.setMaximumSize(new Dimension(60,60));
			
			trayPanel.addFocusListener(new FocusListener(){
	
				@Override
				public void focusGained(FocusEvent e) {
					
				}
	
				@Override
				public void focusLost(FocusEvent e) {
					menuPopup.setVisible(false);
				}
			});
			
			trayPanel.addMouseListener(new MouseAdapter(){
	
				@Override
				public void mouseEntered(MouseEvent e) {
					
					trayPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					
					trayPanel.setCursor(Cursor.getDefaultCursor());
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if(menuPopup.isVisible())
						return;
					
					trayPanel.requestFocus();
					int x = (int) (trayPanel.getLocationOnScreen().x);
					int y = (int) (trayPanel.getLocationOnScreen().y-menuPopup.getPreferredSize().getHeight());
					menuPopup.setLocation(x,y);
					menuPopup.setVisible(true);
				}
				
				
			});
		}
		Timer timer = new Timer(1000,new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Calendar cal = Calendar.getInstance();
				timeLabel.setText(timeDf.format(cal.getTime()));
				dateLabel.setText(dateDf.format(cal.getTime()));
			}
		});
		timer.setCoalesce(false);
		timer.setRepeats(true);
		timer.start();
		return trayPanel;
	}
	
	

}
