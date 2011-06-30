package com.shijie.media.client.platform.ui.footer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Comparator;
import java.util.EventListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.module.ITray;
import com.shijie.media.client.api.module.ITrayManager;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.utils.SortValueMap;

public class FEView implements IView,ITrayManager {
	private Logger logger = LoggerFactory.getLogger(FEView.class);
	private JScrollPane pane;
	private JPanel trayPanel;
	private IViewManager viewManager;
	
	private Comparator<ITray> sort = new Comparator<ITray>(){
		
		public int compare(ITray tray1, ITray  tray2) {
			if(tray1.getOrder()> tray2.getOrder())
				return 1;
			else if(tray1.getOrder()< tray2.getOrder())
				return -1;
			else 
				return 0;
		}

	};
	private SortValueMap<String,ITray> trayMap =new SortValueMap<String,ITray>(sort);
	
	@Override
	public void init() {
		
	}
	@Override
	public JComponent getView() {
		if(pane==null){
			trayPanel = new JPanel();
			BoxLayout layout = new BoxLayout(trayPanel, BoxLayout.X_AXIS);
			trayPanel.setLayout(layout);
		    List<ITray> list = (List<ITray>) getTrays();
		    
		    addTrayChangeListener(new PropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					final String operate = evt.getPropertyName();
					final ITray tray = (ITray)evt.getOldValue();
					final int index = (Integer)evt.getNewValue();
					Runnable doRun = new Runnable(){
						@Override
						public void run() {
							
							if("add".equals(operate)){
								insert(trayPanel,tray,index);
							}else if("remove".equals(operate)){
								remove(trayPanel,index);
							}
							trayPanel.updateUI();
							logger.info(operate+" Customer tray:"+tray.getDisplayName());
						}
					};
					SwingUtilities.invokeLater(doRun);
				}
		    });
		    trayPanel.add(Box.createHorizontalStrut(10));
		    createTray(list,trayPanel);

		    pane = new JScrollPane(trayPanel);
		    pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		    pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return pane;
	}
	
	private void createTray(List<ITray> list,JPanel root){
		for(ITray tray:list){
			tray.init();
			tray.setViewManager(viewManager);
			insert(root, tray,-1);
	    }
	}
	
	protected void remove(JPanel root, int index) {
		root.remove(index);
		
	}
	protected void insert(JPanel root, ITray tray,int index) {
		JComponent comp = tray.getTrayView();
		
		if(index<0)
			root.add(comp);
		else
			root.add(comp,index);
	}
	
	@Override
	public String getLocation() {
		return FOOTER_EAST;
	}
	@Override
	public void updateView() {

		
	}
	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}
	@Override
	public void installTray(ITray tray) {
		trayMap.put(tray.getId(),tray);
		
	}
	@Override
	public void uninstallTray(ITray tray) {
		trayMap.remove(tray.getId());
		
	}
	@Override
	public ITray getTray(String id) {
		return trayMap.get(id);
	}
	@Override
	public Collection<ITray> getTrays() {
		return trayMap.values();
	}
	@Override
	public void addTrayChangeListener(EventListener listener) {
		trayMap.addPropertyChangeListener((PropertyChangeListener)listener);
		
	}
	@Override
	public void removeTrayChangeListener(EventListener listener) {
		trayMap.removePropertyChangeListener((PropertyChangeListener)listener);
		
	}
	
}
