package com.shijie.media.client.platform.ui.right;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

import com.shijie.media.client.api.ui.IEvent;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.platform.Constraints;

public class RCAdv implements IView{

	private JWebBrowser browser;
	private String url;
	private int scrollTime;
	private IViewManager viewManager;
	
	@Override
	public void init(Config config) {
		url = (String) config.getProps().get(Constraints.RC_URL);
		scrollTime =  (Integer) config.getProps().get(Constraints.RC_SCROLLTIME);
	}
	
	@Override
	public JComponent getView() {
		if(browser==null){
			browser = new JWebBrowser(JWebBrowser.destroyOnFinalization());
			browser.setOpaque(false);
			browser.setPreferredSize(new Dimension(250,getHeight()-90));
			browser.setDefaultPopupMenuRegistered(false);
			browser.addWebBrowserListener(new WebBrowserAdapter(){
				public void locationChanged(WebBrowserNavigationEvent e){
					browser.executeJavascript("window.document.body.scroll = \"no\";"+"window.document.body.style.overflow = \"hidden\";" );
			    }
	
				@Override
				public void locationChanging(WebBrowserNavigationEvent e) {
					String example = RIGHT_CENTER+"_ADV";
					String newLocation = e.getNewResourceLocation().toUpperCase();
					if(!newLocation.contains(example)){
						ActionEvent event = new ActionEvent(browser, IEvent.DECORATED_BROWSER, e.getNewResourceLocation());
						viewManager.postEvent(event);
						e.consume();
					}
					
				}
	
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
			
			browser.navigate(url);
			Timer timer = new Timer(scrollTime*1000,new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent e) {
					browser.navigate(url);
				}
			});
			timer.setRepeats(true);
			timer.start();
		}
		return browser;
	}

	@Override
	public String getLocation() {
		return RIGHT_CENTER;
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}

	@Override
	public void updateView() {
		browser.navigate(url);
	}

	private int getHeight(){
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

}
