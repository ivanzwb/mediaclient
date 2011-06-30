package com.shijie.media.client.platform.ui.center;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

import com.shijie.media.client.api.ui.IEvent;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Category;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.entity.ConfigWrapper;
import com.shijie.media.client.platform.Constraints;

public class CNAdv implements IView{

	private JFlashPlayer flashPlayer;
	private String url;
	private int scrollTime = 600;
	private IViewManager viewManager;
	
	@Override
	public void init() {
		Config config = new ConfigWrapper(Category.CAT_UI_VIEW,getLocation()).load();
		url = (String) config.getProps().get(Constraints.CN_URL);
		scrollTime = (Integer) config.getProps().get(Constraints.CN_SCROLLTIME);
	}
	
	@Override
	public JComponent getView() {
		if(flashPlayer==null){
			flashPlayer = new JFlashPlayer(JFlashPlayer.destroyOnFinalization());
			flashPlayer.setPreferredSize(new Dimension(getWidth()-460,130));
			flashPlayer.load(url);
			final JWebBrowser browser = flashPlayer.getWebBrowser();
			browser.addWebBrowserListener(new WebBrowserAdapter(){
				@Override
				public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
					e.getNewWebBrowser().addWebBrowserListener(new WebBrowserAdapter() {
						@Override
						public void locationChanging(WebBrowserNavigationEvent e) {
							String newResourceLocation = e.getNewResourceLocation();
							final JWebBrowser webBrowser = e.getWebBrowser();
							ActionEvent event = new ActionEvent(browser,IEvent.DECORATED_BROWSER, newResourceLocation);
							viewManager.postEvent(event);
							e.consume();
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									webBrowser.getWebBrowserWindow().dispose();
								}
							});
						}
					});
				}   
				
			});
			
			Timer timer = new Timer(scrollTime*1000,new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent e) {
					flashPlayer.load(url);
				}
			});
			timer.setRepeats(true);
			timer.start();
		}
		
	    return flashPlayer;
	}

	@Override
	public String getLocation() {
		return CENTER_NORTH;
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
		
	}

	@Override
	public void updateView() {
		flashPlayer.load(url);	
	}
	
	private int getWidth(){
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}


}
