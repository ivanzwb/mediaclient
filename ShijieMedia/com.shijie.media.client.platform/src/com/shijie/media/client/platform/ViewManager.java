package com.shijie.media.client.platform;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.pushingpixels.substance.api.DecorationAreaType;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.activator.Activator;
import com.shijie.media.client.api.service.DBService;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Category;
import com.shijie.media.client.utils.DBUtils;

public class ViewManager implements IViewManager{
	private Logger logger = LoggerFactory.getLogger(ViewManager.class);
	private List<ActionListener> listenerList = new ArrayList<ActionListener>();
	private HashMap<String,IView> map = new HashMap<String,IView>();
	private JLayeredPane base = null;
	private JPanel titlePanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel centerPanel;
	private JPanel footPanel;	
	
	public ViewManager(){
		logger.info("view manager instance created...");
		Runnable doRun = new Runnable(){

			@Override
			public void run() {
				base = new JLayeredPane();
				base.setLayout(new BorderLayout());
				titlePanel = new JPanel();
				titlePanel.setName(""+IView.TITLE);
				titlePanel.setPreferredSize(new Dimension(getWidth(),25));
				titlePanel.setLayout(new BorderLayout());
				
				leftPanel  = new JPanel();
				leftPanel.setPreferredSize(new Dimension(210,getHeight()-90));
				leftPanel.setName(""+IView.LEFT);
				leftPanel.setLayout(new BorderLayout());
				
				rightPanel  = new JPanel();
				rightPanel.setPreferredSize(new Dimension(250,getHeight()-90));
				rightPanel.setName(""+IView.RIGHT);
				rightPanel.setLayout(new BorderLayout());
				
				centerPanel  = new JPanel();
				centerPanel.setName(""+IView.CENTER);
				centerPanel.setLayout(new BorderLayout());
				
				footPanel = new JPanel();
				footPanel.setName(""+IView.FOOTER);
				footPanel.setPreferredSize(new Dimension(getWidth(),65));
				footPanel.setLayout(new BorderLayout());
				
				base.add(titlePanel,BorderLayout.NORTH);
				base.add(leftPanel,BorderLayout.WEST);
				base.add(rightPanel,BorderLayout.EAST);
				base.add(footPanel,BorderLayout.SOUTH);
				base.add(centerPanel,BorderLayout.CENTER);
				
				SubstanceLookAndFeel.setDecorationType(titlePanel, DecorationAreaType.PRIMARY_TITLE_PANE);
				SubstanceLookAndFeel.setDecorationType(footPanel, DecorationAreaType.TOOLBAR);
			}
			
		};
		try {
			SwingUtilities.invokeAndWait(doRun);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
	}
	
	private int getHeight(){
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}
	
	private int getWidth(){
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	@Override
	public void installView(final IView view) {
		view.setViewManager(this);
		logger.info("install view:"+view.getLocation());
		map.put(view.getLocation(), view);
	}
	public void uninstallView(IView view) {
		map.remove(view.getLocation());
		view.setViewManager(null);
	}
	
	private void addView(JPanel rootPanel,JComponent target, String loc){
		char chr = loc.charAt(0);

		switch (chr) {
		case 'N': {
			if (loc.length() > 1) {
				String sub = loc.substring(1, loc.length());
				BorderLayout layout = (BorderLayout) rootPanel.getLayout();
				addView((JPanel) layout.getLayoutComponent(BorderLayout.NORTH),target,sub);
			} else {
				rootPanel.add(target,BorderLayout.NORTH);
			}
			break;

		}
		case 'E': {
			if (loc.length() > 1) {
				String sub = loc.substring(1, loc.length());
				BorderLayout layout = (BorderLayout) rootPanel.getLayout();
				addView((JPanel) layout.getLayoutComponent(BorderLayout.EAST),target,sub);
			} else {
				rootPanel.add(target,BorderLayout.EAST);
			}
			break;
		}
		case 'S': {
			if (loc.length() > 1) {
				String sub = loc.substring(1, loc.length());
				BorderLayout layout = (BorderLayout) rootPanel.getLayout();
				addView((JPanel) layout.getLayoutComponent(BorderLayout.SOUTH),target,sub);
			} else {
				rootPanel.add(target,BorderLayout.SOUTH);
			}
			break;
		}
		case 'W': {
			if (loc.length() > 1) {
				String sub = loc.substring(1, loc.length());
				BorderLayout layout = (BorderLayout) rootPanel.getLayout();
				addView((JPanel) layout.getLayoutComponent(BorderLayout.WEST),target,sub);
			} else {
				rootPanel.add(target,BorderLayout.WEST);
			}
			break;
		}
		case 'C': {
			if (loc.length() > 1) {
				String sub = loc.substring(1, loc.length());
				BorderLayout layout = (BorderLayout) rootPanel.getLayout();
				addView((JPanel) layout.getLayoutComponent(BorderLayout.CENTER),target,sub);
			} else {
				rootPanel.add(target,BorderLayout.CENTER);
			}
			break;
		}
		default:
			break;
		}
		rootPanel.updateUI();
	}

	@Override
	public JLayeredPane getViews() {
		for(final IView view:map.values()){
			logger.info("Create View:"+view.getLocation());
			Runnable doRun = new Runnable(){
				@Override
				public void run() {
					view.init(DBUtils.getConfig((DBService) Activator.getServiceManager().getService(DBService.ID),Category.CAT_UI_VIEW,view.getLocation()));
					String location = view.getLocation();
					char type = location.charAt(0);
					switch(type){
						case IView.TITLE:{
							addView(titlePanel,view.getView(),location.substring(1, location.length()));
							break;
						}
						case IView.LEFT:{
							addView(leftPanel,view.getView(),location.substring(1, location.length()));
							break;
						}
						case IView.RIGHT:{
							addView(rightPanel,view.getView(),location.substring(1, location.length()));
							break;
						}
						case IView.FOOTER:{
							addView(footPanel,view.getView(),location.substring(1, location.length()));
							break;
						}
						case IView.CENTER:{
							addView(centerPanel,view.getView(),location.substring(1, location.length()));
							break;
						}
						default:break;
					}
				}
			};
			SwingUtilities.invokeLater(doRun);
		}
		return base;
	}

	@Override
	public void fullScreen(String ...locs) {
		setViewsVisible(false);
		int t =0;
		int l = 0;
		int r = 0;
		int c = 0;
		int f = 0;
		for(String loc:locs){
			char type = loc.charAt(0);
			switch(type){
				case IView.TITLE:{
					t++;
					break;
				}
				case IView.LEFT:{
					l++;
					break;
				}
				case IView.RIGHT:{
					r++;
					break;
				}
				case IView.CENTER:{
					c++;
					break;
				}
				case IView.FOOTER:{
					f++;
					break;
				}
			}
			map.get(loc).getView().setVisible(true);
		}
		
		if(t>0){
			titlePanel.setVisible(true);
		}
		if(l>0){
			leftPanel.setVisible(true);
		}
		if(r>0){
			rightPanel.setVisible(true);
		}
		if(c>0){
			centerPanel.setVisible(true);
		}
		if(f>0){
			footPanel.setVisible(true);
		}
		
	}
	
	private void setViewsVisible(boolean bool){
		Iterator<String> keyIterator = map.keySet().iterator();
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			JComponent view = map.get(key).getView();
			view.setVisible(bool);
		}
		titlePanel.setVisible(bool);
		leftPanel.setVisible(bool);
		rightPanel.setVisible(bool);
		centerPanel.setVisible(bool);
		footPanel.setVisible(bool);
	};

	@Override
	public void exitFullScreen() {
		setViewsVisible(true);
	}
	
	public void postEvent(ActionEvent event){
		int n = listenerList.size();
		for(int i=0;i<n;i++){
			ActionListener listener = listenerList.get(i);
			listener.actionPerformed(event);
		}
	}
	
	public void registerActionListener(ActionListener listener){
		listenerList.add(listener);
	}
	
	public void removeActionListener(ActionListener listener){
		listenerList.remove(listener);
	}

}

