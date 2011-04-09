package com.shijie.media.client.platform.ui.title;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.platform.Constraints;

public class TCAdv implements IView{
	
	private JDialog dialog;
	private JWebBrowser browser;
	private JLabel label;
	private Timer timer;
	private boolean visible = false;
	
	private int scrollTime = 320;
	private int temp = 320;
	private String title;
	private String url;
	private Dimension dimension;
	private String message;

	
	private IViewManager viewManager;
	
	@Override
	public void init(Config config) {
		scrollTime = (Integer) config.getProps().get(Constraints.TC_ALERT_SCROLLTIME);
		temp = scrollTime;
		Object dim = config.getProps().get(Constraints.TC_ALERT_DIMENSION);
		if(dim!=null)
			dimension = (Dimension)dim;
		else
			dimension = Toolkit.getDefaultToolkit().getScreenSize();
		title = (String)config.getProps().get(Constraints.TC_ALERT_TITLE);
		url = (String)config.getProps().get(Constraints.TC_ALERT_URL);
		message = (String)config.getProps().get(Constraints.TC_MESSAGE);
		
		
	}
	
	@Override
	public JComponent getView() {
		if(label==null){
			label = new JLabel(message+scrollTime+"秒");
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setName(TITLE_CENTER);
			timer = new Timer(1000,new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!visible){
						if(temp<0){
							visible = true;
							showAdv();
							temp=scrollTime;
						}else{
							label.setText(message+(temp)+"秒");
							temp--;
						}
					}
				}
			});
			timer.setInitialDelay(5000);
			timer.setCoalesce(false);
			timer.setRepeats(true);
			timer.start();
		}
		return label;
	}

	@Override
	public String getLocation() {
		return TITLE_CENTER;
	}
	
	public void showAdv(){
		Runnable doRun = new Runnable(){
			@Override
			public void run() {
				if(dialog==null){
					dialog = new JDialog((Frame)null,true);
					dialog.setLayout(new BorderLayout());
					browser = new JWebBrowser(JWebBrowser.destroyOnFinalization());
					browser.setOpaque(false);
					browser.setDefaultPopupMenuRegistered(false);
					dialog.add(browser,BorderLayout.CENTER);
					dialog.setTitle(title);
					dialog.setAlwaysOnTop(true);
					dialog.setSize(dimension);
					dialog.setResizable(false);
					dialog.addWindowListener(new WindowAdapter(){
						public void windowClosing(WindowEvent e) {
							dialog.setVisible(false);
							dialog.dispose();
							visible = false;
						}						
					});
				}
				browser.navigate(url);
				viewManager.exitFullScreen();
				dialog.setVisible(true);
			}
			
		};
		SwingUtilities.invokeLater(doRun);
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}

	@Override
	public void updateView() {
		dialog.setTitle(title);
		dialog.setSize(dimension);
		browser.navigate(url);
	}
	
	

}
