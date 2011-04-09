package com.shijie.media.client.platform.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.pushingpixels.substance.api.SubstanceConstants.TabCloseKind;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.tabbed.TabCloseCallback;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeWebBrowser;


public class DecoratorWebBrowser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2432532672884574101L;

	private NavigateBar navigateBar;
	private StatusBar statusBar;

	private JTabbedPane tabPane;
	private JWebBrowser webBrowser;
	private String home = "about:blank";

	public DecoratorWebBrowser(final String home) {
		this.setLayout(new BorderLayout());
		this.home = home;
		tabPane = new JTabbedPane();
		webBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());
		webBrowser.setOpaque(false);
		webBrowser.navigate(home);
		webBrowser.addWebBrowserListener(new NWebBrowserListener());
		tabPane.add(home,webBrowser);
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPane.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane panel = (JTabbedPane)e.getSource();
				if(panel!=null){
					JWebBrowser webBrowser = (JWebBrowser)panel.getSelectedComponent();
					updateNavigateButtons(webBrowser);
					updateStatusLabel(webBrowser);
					updateStatusProcessBar(webBrowser);
					updateLocation(webBrowser);
					updateStopButton(webBrowser);
				}
			}
		});

		// register the callback on the tabbed pane
		tabPane.putClientProperty(SubstanceLookAndFeel.TABBED_PANE_CLOSE_CALLBACK,
				new TabCloseCallback() {
					public TabCloseKind onAreaClick(JTabbedPane tabbedPane,int tabIndex, MouseEvent mouseEvent) {
						return null;
					}

					public TabCloseKind onCloseButtonClick(JTabbedPane tabbedPane,int tabIndex, MouseEvent mouseEvent) {
						if(tabIndex==0&&tabbedPane.getTabCount()==1){
							JWebBrowser webBrowser = (JWebBrowser)tabbedPane.getComponentAt(0);
							webBrowser.navigate(home);
							return TabCloseKind.NONE;
						}
						else 
							return TabCloseKind.THIS;
					}

					public String getAreaTooltip(JTabbedPane tabbedPane, int tabIndex) {
						return null;
					}

					public String getCloseButtonTooltip(JTabbedPane tabbedPane,int tabIndex) {
						return null;
					}
		});
		
		navigateBar = new NavigateBar();
		add(navigateBar, BorderLayout.NORTH);
		add(tabPane, BorderLayout.CENTER);
		statusBar = new StatusBar();
		add(statusBar, BorderLayout.SOUTH);
		setNavigateBarVisible(true);
		setStatusBarVisible(true);
	}
	
	private void updateLocation(JWebBrowser webBrowser){
		if(!navigateBar.getLocationField().isFocusOwner())
			navigateBar.updateLocation(webBrowser.getResourceLocation());
	}
	
	private void updateStatusProcessBar(JWebBrowser webBrowser){
		INativeWebBrowser nativeWebBrowser = (INativeWebBrowser)webBrowser.getNativeComponent();
		int loadingProgress = nativeWebBrowser.isNativePeerInitialized()? nativeWebBrowser.getLoadingProgress(): 100;
		statusBar.getProgressBar().setValue(loadingProgress);
		statusBar.getProgressBar().setVisible(loadingProgress < 100);
	}
	
	private void updateStatusLabel(JWebBrowser webBrowser){
		INativeWebBrowser nativeWebBrowser = (INativeWebBrowser)webBrowser.getNativeComponent();
		String status = nativeWebBrowser.isNativePeerInitialized()? nativeWebBrowser.getStatusText(): "";
	    statusBar.getStatusLabel().setText(status.length() == 0? " ": status);
	}
	
	private void updateNavigateButtons(JWebBrowser webBrowser){
		navigateBar.updateLocation(webBrowser.getResourceLocation());
		navigateBar.getStopButton().setEnabled(false);
		INativeWebBrowser nativeWebBrowser = (INativeWebBrowser)webBrowser.getNativeComponent();
		if(!nativeWebBrowser.isNativePeerDisposed()) {
		      boolean isBackEnabled = nativeWebBrowser.isNativePeerInitialized()? nativeWebBrowser.isBackNavigationEnabled(): false;
		      navigateBar.getBackButton().setEnabled(isBackEnabled);
		      boolean isForwardEnabled = nativeWebBrowser.isNativePeerInitialized()? nativeWebBrowser.isForwardNavigationEnabled(): false;
		      navigateBar.getForwardButton().setEnabled(isForwardEnabled);
		 }
	}
	
	public void updateStopButton(JWebBrowser webBrowser){
		if(webBrowser.getLoadingProgress()<100)
			navigateBar.getStopButton().setEnabled(true);
		else
			navigateBar.getStopButton().setEnabled(false);
	}
	
	public void addNewPage(String resourceLocation){
		JWebBrowser webBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());
		webBrowser.setOpaque(false);
		webBrowser.addWebBrowserListener(new NWebBrowserListener());
		webBrowser.navigate(resourceLocation);
		tabPane.add(webBrowser);
	}
	
	private DecoratorWebBrowser getThis(){
		return this;
	}
	
	public class NavigateBar extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7460164118319879800L;
		private JButton homeButton;
		private JButton backButton;
		private JButton forwardButton;
		private JTextField locationField;
		private JButton reloadButton;
		private JButton stopButton;
		private JButton fullButton;

		public NavigateBar() {
			setLayout(new BorderLayout());
			homeButton = new JButton();
			homeButton.setOpaque(false);
			homeButton.setContentAreaFilled(false);
			homeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JWebBrowser webBrowser = (JWebBrowser)tabPane.getSelectedComponent();
					webBrowser.navigate(home);
					webBrowser.getNativeComponent().requestFocus();
				}
			});
			homeButton.setIcon(new ImageIcon(this.getClass().getResource("/ICON-INF/home.gif")));
			backButton = new JButton();
			backButton.setOpaque(false);
			backButton.setContentAreaFilled(false);
			backButton.setIcon(new ImageIcon(this.getClass().getResource("/ICON-INF/back.gif")));
			backButton.setEnabled(false);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JWebBrowser webBrowser = (JWebBrowser)tabPane.getSelectedComponent();
					webBrowser.navigateBack();
					webBrowser.getNativeComponent().requestFocus();
				}
			});
			forwardButton = new JButton();
			forwardButton.setOpaque(false);
			forwardButton.setContentAreaFilled(false);
			forwardButton.setIcon(new ImageIcon(this.getClass().getResource("/ICON-INF/forward.gif")));
			forwardButton.setEnabled(false);
			forwardButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JWebBrowser webBrowser = (JWebBrowser)tabPane.getSelectedComponent();
					webBrowser.navigateForward();
					webBrowser.getNativeComponent().requestFocus();
				}
			});

			JPanel navigatePanel = new JPanel();
			FlowLayout navigateLayout = new FlowLayout();
			navigateLayout.setHgap(0);
			navigateLayout.setVgap(0);
			navigatePanel.setLayout(navigateLayout);
			navigatePanel.add(homeButton);
			navigatePanel.add(backButton);
			navigatePanel.add(forwardButton);

			add(navigatePanel, BorderLayout.WEST);

			locationField = new JTextField(40);
			locationField.setOpaque(false);
			locationField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						locationField.selectAll();
					} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						JWebBrowser webBrowser = (JWebBrowser)tabPane.getSelectedComponent();
						webBrowser.navigate(locationField.getText());
						webBrowser.getNativeComponent().requestFocus();
					}
				}
			});
			locationField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JWebBrowser webBrowser = (JWebBrowser)tabPane.getSelectedComponent();
					webBrowser.navigate(locationField.getText());
					webBrowser.getNativeComponent().requestFocus();
				}
			});
			add(locationField, BorderLayout.CENTER);
			
			JPanel controllPanel = new JPanel();
			FlowLayout controllLayout = new FlowLayout();
			controllLayout.setHgap(0);
			controllLayout.setVgap(0);
			controllPanel.setLayout(controllLayout);			
			reloadButton = new JButton();
			reloadButton.setOpaque(false);
			reloadButton.setContentAreaFilled(false);
			reloadButton.setIcon(new ImageIcon(this.getClass().getResource("/ICON-INF/reload.gif")));
			reloadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JWebBrowser webBrowser = (JWebBrowser)tabPane.getSelectedComponent();
					webBrowser.reloadPage();
					webBrowser.getNativeComponent().requestFocus();
				}
			});
			stopButton = new JButton();
			stopButton.setOpaque(false);
			stopButton.setContentAreaFilled(false);
			stopButton.setIcon(new ImageIcon(this.getClass().getResource("/ICON-INF/stop.gif")));
			stopButton.setEnabled(false);
			stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JWebBrowser webBrowser = (JWebBrowser)tabPane.getSelectedComponent();
					webBrowser.stopLoading();
				}
			});
			
			fullButton = new JButton(); 
			fullButton.setOpaque(false);
			fullButton.setContentAreaFilled(false);
			fullButton.setIcon(new ImageIcon(this.getClass().getResource("/ICON-INF/fullscreen.gif")));
			fullButton.setEnabled(true);
			fullButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ActionListener action = (ActionListener)getThis().getClientProperty("fullscreen.action");
					action.actionPerformed(e);
				}
			});
			fullButton.getClientProperty("full");
			controllPanel.add(reloadButton);
			controllPanel.add(stopButton);
			controllPanel.add(fullButton);
			add(controllPanel, BorderLayout.EAST);			
		}

		public JButton getHomeButton() {
			return homeButton;
		}

		public JButton getBackButton() {
			return backButton;
		}

		public JButton getForwardButton() {
			return forwardButton;
		}

		public JButton getReloadButton() {
			return reloadButton;
		}

		public JButton getStopButton() {
			return stopButton;
		}

		public JTextField getLocationField() {
			return locationField;
		}

		public void updateLocation(String location) {
			locationField.setText(location);
		}
	}

	public class StatusBar extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5027982223488950566L;
		private JLabel statusLabel;
		private JProgressBar progressBar;

		public StatusBar() {
			super(new BorderLayout());
			setBorder(BorderFactory.createCompoundBorder(STATUS_BAR_BORDER, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
			statusLabel = new JLabel("");
			add(statusLabel, BorderLayout.CENTER);
			progressBar = new JProgressBar() {
			/**
			 * 
			 */
				private static final long serialVersionUID = 1L;

				@Override
				public Dimension getPreferredSize() {
					return new Dimension(getParent().getWidth() / 10, 0);
				}
			};
			add(progressBar, BorderLayout.EAST);
		}
		
		public JLabel getStatusLabel(){
			return statusLabel;
		}
		
		public JProgressBar getProgressBar(){
			return progressBar;
		}

	}

	public void setStatusBarVisible(boolean isStatusBarVisible) {
		statusBar.setVisible(isStatusBarVisible);
	}

	public void setNavigateBarVisible(boolean isNavigateBarVisible) {
		navigateBar.setVisible(isNavigateBarVisible);
	}
	
	private static final Border STATUS_BAR_BORDER = new AbstractBorder() {
		/**
	 * 
	 */
		private static final long serialVersionUID = -3980182544500337157L;

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(1, 1, 1, 1);
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y,int width, int height) {
			Color background = c.getBackground();
			g.setColor(background == null ? Color.LIGHT_GRAY : background.darker());
			g.drawLine(0, 0, width - 1, 0);
			g.drawLine(width - 1, 0, width - 1, height - 1);
			g.drawLine(0, height - 1, width - 1, height - 1);
			g.drawLine(0, 0, 0, height - 1);
		}
	};
	
	private class NWebBrowserListener extends WebBrowserAdapter {

		@Override
		public void locationChanged(WebBrowserNavigationEvent e) {
			JWebBrowser webBrowser = e.getWebBrowser();
			JTabbedPane panel = (JTabbedPane)e.getWebBrowser().getParent();
			if(panel!=null){
				int index = 0;
				for(int i=0;i<panel.getTabCount();i++){
					if(webBrowser==panel.getComponentAt(i)){
						index = i;
						break;
					}
				}
				panel.setTitleAt(index, webBrowser.getPageTitle());
				if(panel.getSelectedComponent()==webBrowser){
					updateNavigateButtons(webBrowser);
					updateLocation(webBrowser);
				}
			}
		}

		@Override
		public void locationChanging(WebBrowserNavigationEvent e) {
			JWebBrowser webBrowser = e.getWebBrowser();
			JTabbedPane panel = (JTabbedPane)e.getWebBrowser().getParent();
			if(panel!=null)
			if(panel.getSelectedComponent()==webBrowser){
				//updateLocation(webBrowser);
				updateStopButton(webBrowser);
			}
		}

		@Override
		public void locationChangeCanceled(WebBrowserNavigationEvent e) {
			JWebBrowser webBrowser = e.getWebBrowser();
			JTabbedPane panel = (JTabbedPane)e.getWebBrowser().getParent();
			if(panel!=null)
			if(panel.getSelectedComponent()==webBrowser){
				updateNavigateButtons(webBrowser);
			}
		}

		@Override
		public void statusChanged(WebBrowserEvent e) {
			JWebBrowser webBrowser = e.getWebBrowser();
			JTabbedPane panel = (JTabbedPane)e.getWebBrowser().getParent();
			if(panel!=null)
			if(panel.getSelectedComponent()==webBrowser){
				//updateLocation(webBrowser);
				updateStatusLabel(webBrowser);
			}
		}

		@Override
		public void loadingProgressChanged(WebBrowserEvent e) {
			JWebBrowser webBrowser = e.getWebBrowser();
			JTabbedPane panel = (JTabbedPane)e.getWebBrowser().getParent();
			if(panel!=null)
			if(panel.getSelectedComponent()==webBrowser){
				//updateLocation(webBrowser);
				updateStatusProcessBar(webBrowser);
				updateStopButton(webBrowser);
			}
			
		}
		
		@Override
		public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
			JWebBrowser newWebBrowser = e.getNewWebBrowser();
			String defLocation = "about:blank";
			String newLocation = newWebBrowser.getResourceLocation();
			if(newLocation!=null&&"".equals(newLocation))
				defLocation = newLocation;
				
			newWebBrowser.addWebBrowserListener(new NWebBrowserListener()); 
			tabPane.add(defLocation,newWebBrowser);	
		}
	}
}
