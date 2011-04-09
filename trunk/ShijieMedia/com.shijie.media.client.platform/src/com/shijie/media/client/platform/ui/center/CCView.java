package com.shijie.media.client.platform.ui.center;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

import com.shijie.media.client.api.ui.IEvent;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.platform.Constraints;
import com.shijie.media.client.platform.components.DecoratedPlayer;
import com.shijie.media.client.platform.components.DecoratedPlayer.VLCPlayerControlBar;
import com.shijie.media.client.platform.components.DecoratorWebBrowser;

public class CCView implements IView {
	
	private JPanel cardPanel;
	private DecoratorWebBrowser decoratorWebBrowser;
	private JWebBrowser nonDecoratorWebBrowser;
	private JLayeredPane embeddedAdvDecoratorPlayer;
	private JPanel embeddedAdvPanel;
	private JWebBrowser embeddedAdvWebBrowser;
	private DecoratedPlayer decoratorPlayer;
	private IViewManager viewManager;
	private String home;
	private String[] browserFullscreen;
	private String[] playerFullscreen;
	private String title;
	private int advWidth;
	private int advHeight;
	private String advUrl;
	private int maxsound;
	private int width;
	private int height;
	
	@Override
	public void init(Config config) {
		home = (String)config.getProps().get("cc.home");
		browserFullscreen = ((String)config.getProps().get("cc.browser.fullscreen")).split(",");
		playerFullscreen = ((String)config.getProps().get("cc.player.fullscreen")).split(",");
		title = (String)config.getProps().get("cc.embeddedadv.title");
		advWidth = (Integer)config.getProps().get("cc.embeddedadv.width");
		advHeight = (Integer)config.getProps().get("cc.embeddedadv.height");
		advUrl = (String) config.getProps().get("cc.embeddedadv.url");
		maxsound = (Integer)config.getProps().get("cc.player.maxsound");
		
	}

	@Override
	public JComponent getView() {
		if(cardPanel==null)	{
			cardPanel = new JPanel();
			cardPanel.setLayout(new CardLayout());
		
			decoratorWebBrowser = new DecoratorWebBrowser(home);
			decoratorWebBrowser.putClientProperty("fullscreen.action", new ActionListener(){
				private boolean isFull = false;
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isFull){
						viewManager.exitFullScreen();
						isFull = false;
					}else{
						viewManager.fullScreen(browserFullscreen);
						isFull = true;
					}
				}
			});
		
			cardPanel.add(decoratorWebBrowser,""+IEvent.DECORATED_BROWSER);
			
			createNonDecoratedBrowser();
			
			cardPanel.add(nonDecoratorWebBrowser,""+IEvent.NO_DECORATED_BROWSER);
			
			createEembeddedAdvDecoratorPlayer();
			
			decoratorPlayer.putClientProperty("fullscreen.action", new ActionListener(){
				private boolean isFull = false;
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isFull){
						viewManager.exitFullScreen();
						isFull = false;
					}else{
						viewManager.fullScreen(playerFullscreen);
						isFull = true;
					}
				}
			});
			
			cardPanel.add(embeddedAdvDecoratorPlayer,""+IEvent.DECORATED_MOVIE_PLAYER);
			
			viewManager.registerActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String command = e.getActionCommand();
					if(e.getID()==IEvent.DECORATED_BROWSER){
						CardLayout cl = (CardLayout)(cardPanel.getLayout());
						if(command!=null&&!"".equals(command))
							decoratorWebBrowser.addNewPage(command);
						cl.show(cardPanel, ""+IEvent.DECORATED_BROWSER);
					}else if(e.getID()==IEvent.NO_DECORATED_BROWSER){
						CardLayout cl = (CardLayout)(cardPanel.getLayout());
						if(command!=null&&!"".equals(command))
							nonDecoratorWebBrowser.navigate(command);
						cl.show(cardPanel, ""+IEvent.NO_DECORATED_BROWSER);
					}else if(e.getID()==IEvent.DECORATED_MOVIE_PLAYER){
						CardLayout cl = (CardLayout)(cardPanel.getLayout());
						if(command!=null&&!"".equals(command))
							decoratorPlayer.addPlayList(command);
						decoratorPlayer.getControllBar().getPlayButton().doClick();
						cl.show(cardPanel, ""+IEvent.DECORATED_MOVIE_PLAYER);
					}else if(e.getID()==IEvent.DECORATED_MUSIC_PLAYER){
						CardLayout cl = (CardLayout)(cardPanel.getLayout());
						if(command!=null&&!"".equals(command))
							decoratorPlayer.addPlayList(command);
						decoratorPlayer.getControllBar().getPlayButton().doClick();
						cl.show(cardPanel, ""+IEvent.DECORATED_MUSIC_PLAYER);
					}
				}
			});
		}
		return cardPanel;
	}
	
	public void createNonDecoratedBrowser(){
		nonDecoratorWebBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());
		nonDecoratorWebBrowser.setOpaque(false);
		nonDecoratorWebBrowser.setPreferredSize(new Dimension(250,getHeight()-90));
		nonDecoratorWebBrowser.setDefaultPopupMenuRegistered(false);
		nonDecoratorWebBrowser.addWebBrowserListener(new WebBrowserAdapter(){
			public void locationChanged(WebBrowserNavigationEvent e){
				nonDecoratorWebBrowser.executeJavascript("window.document.body.scroll = \"no\";"+"window.document.body.style.overflow = \"hidden\";" );
		    }

			@Override
			public void commandReceived(WebBrowserCommandEvent e) {
				String command = e.getCommand();
				if(command.startsWith(Constraints.PLAY_MOVIE_COMMOND)){
					String resource = command.replace(Constraints.PLAY_MOVIE_COMMOND, "");
					ActionEvent event = new ActionEvent(embeddedAdvWebBrowser, IEvent.DECORATED_MOVIE_PLAYER, resource);
					viewManager.postEvent(event);
				}else if(command.startsWith(Constraints.PLAY_MUSIC_COMMOND)){
					String resource = command.replace(Constraints.PLAY_MUSIC_COMMOND, "");
					ActionEvent event = new ActionEvent(embeddedAdvWebBrowser, IEvent.DECORATED_MUSIC_PLAYER, resource);
					viewManager.postEvent(event);
				}
			}


			@Override
			public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
				e.getNewWebBrowser().addWebBrowserListener(new WebBrowserAdapter() {
					@Override
					public void locationChanging(WebBrowserNavigationEvent e) {
						String newResourceLocation = e.getNewResourceLocation();
						final JWebBrowser webBrowser = e.getWebBrowser();
						nonDecoratorWebBrowser.navigate(newResourceLocation);
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
	}
	
	public void createEembeddedAdvDecoratorPlayer(){
		int layerIndex = 0;
		width = getWidth()-460;
		height = getHeight()-245;
		embeddedAdvDecoratorPlayer =   new JLayeredPane();
		decoratorPlayer = new DecoratedPlayer();
		decoratorPlayer.putClientProperty("player.status.action", new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(decoratorPlayer.getPlayerStatus()==VLCPlayerControlBar.PAUSE){
					embeddedAdvPanel.setVisible(true);
				}else if(decoratorPlayer.getPlayerStatus()==VLCPlayerControlBar.STOP){
					embeddedAdvPanel.setVisible(true);
				}else if(decoratorPlayer.getPlayerStatus()==VLCPlayerControlBar.PLAY){
					embeddedAdvPanel.setVisible(false);
				}
				
			}
			
		});
		decoratorPlayer.setBounds(0, 0, width, height);
		decoratorPlayer.setPreferredSize(new Dimension(width, height));
		decoratorPlayer.setMaxsound(maxsound);
		embeddedAdvDecoratorPlayer.setLayer(decoratorPlayer, layerIndex++); 
		embeddedAdvDecoratorPlayer.add(decoratorPlayer);
		
		embeddedAdvPanel = new JPanel();
		embeddedAdvPanel.setOpaque(false);
		embeddedAdvPanel.setBounds(width/2-advWidth/2, height/2-advHeight/2, advWidth, advHeight);
		embeddedAdvPanel.setPreferredSize(new Dimension(advWidth,advHeight));
		embeddedAdvPanel.setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel(title);
	    JLabel closeButton = new JLabel(new ImageIcon(this.getClass().getResource("/ICON-INF/close.gif")));
	    titlePanel.add(titleLabel,BorderLayout.CENTER);
	    titlePanel.add(closeButton,BorderLayout.EAST);
	    
	    embeddedAdvPanel.add(titlePanel,BorderLayout.NORTH);
	    
	    embeddedAdvWebBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());
	    embeddedAdvWebBrowser.setOpaque(false);
	    embeddedAdvWebBrowser.setPreferredSize(new Dimension(advWidth,advHeight));
	    embeddedAdvWebBrowser.setDefaultPopupMenuRegistered(false);
	    embeddedAdvWebBrowser.addWebBrowserListener(new WebBrowserAdapter(){
			public void locationChanged(WebBrowserNavigationEvent e){
				embeddedAdvWebBrowser.executeJavascript("window.document.body.scroll = \"no\";"+"window.document.body.style.overflow = \"hidden\";" );
		    }

			@Override
			public void commandReceived(WebBrowserCommandEvent e) {
				String command = e.getCommand();   
				if("close".equals(command)){
					embeddedAdvPanel.setVisible(false);
				}
			}

			@Override
			public void locationChanging(WebBrowserNavigationEvent e) {
				String example = CENTER_CENTER+"_EMBEDDED_ADV";
				String newLocation = e.getNewResourceLocation().toUpperCase();
				if(!newLocation.contains(example)){
					ActionEvent event = new ActionEvent(embeddedAdvWebBrowser, IEvent.DECORATED_BROWSER, e.getNewResourceLocation());
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
						ActionEvent event = new ActionEvent(embeddedAdvWebBrowser,IEvent.DECORATED_BROWSER, newResourceLocation);
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
	    embeddedAdvWebBrowser.navigate(advUrl);
	    embeddedAdvPanel.add(embeddedAdvWebBrowser,BorderLayout.CENTER);
	    
	    embeddedAdvDecoratorPlayer.setLayer(embeddedAdvPanel, layerIndex++); 
		embeddedAdvDecoratorPlayer.add(embeddedAdvPanel);
		
		embeddedAdvDecoratorPlayer.addComponentListener(new ComponentAdapter(){

			@Override
			public void componentResized(ComponentEvent e) {
				JLayeredPane layeredPane = (JLayeredPane) e.getComponent();
				width = layeredPane.getWidth();
				height = layeredPane.getHeight();
				decoratorPlayer.setBounds(0, 0, width, height);
				decoratorPlayer.setPreferredSize(new Dimension(width, height));
				embeddedAdvPanel.setBounds(width/2-advWidth/2, height/2-advHeight/2-30, advWidth, advHeight);
				embeddedAdvPanel.setPreferredSize(new Dimension(advWidth,advHeight));
			}
	    	
	    });
	}
	
	@Override
	public String getLocation() {
		return CENTER_CENTER;
	}

	@Override
	public void updateView() {

	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}
	
	private int getHeight(){
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}
	
	private int getWidth(){
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
}
