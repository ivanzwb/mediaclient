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

import com.shijie.media.client.api.module.ITool;
import com.shijie.media.client.api.module.IToolManager;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.utils.SortValueMap;

public class FCView implements IView ,IToolManager{
	private Logger logger = LoggerFactory.getLogger(FCView.class);
	private JScrollPane pane;
	private JPanel toolPanel;
	private IViewManager viewManager;
	
	private Comparator<ITool> sort = new Comparator<ITool>(){
		
		public int compare(ITool tool1, ITool tool2) {
			if(tool1.getOrder()>tool2.getOrder())
				return 1;
			else if(tool1.getOrder()<tool2.getOrder())
				return -1;
			else 
				return 0;
		}

	};
	
	private SortValueMap<String,ITool> toolMap = new SortValueMap<String,ITool>(sort);
	
	@Override
	public void init() {

	}

	@Override
	public JComponent getView() {
		if(pane==null){
			toolPanel = new JPanel();
			BoxLayout layout = new BoxLayout(toolPanel, BoxLayout.X_AXIS);
			toolPanel.setLayout(layout);
		    List<ITool> list = (List<ITool>) getTools();
		    
		    addToolChangeListener(new PropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					final String operate = evt.getPropertyName();
					final ITool tool = (ITool)evt.getOldValue();
					final int index = (Integer)evt.getNewValue();
					Runnable doRun = new Runnable(){
						@Override
						public void run() {
							
							if("add".equals(operate)){
								insert(toolPanel,tool,index);
							}else if("remove".equals(operate)){
								remove(toolPanel,index);
							}
							toolPanel.updateUI();
							logger.info(operate+" Customer tool:"+tool.getDisplayName());
						}
					};
					SwingUtilities.invokeLater(doRun);
				}
		    });
		    toolPanel.add(Box.createHorizontalStrut(10));
		    createTool(list,toolPanel);

		    pane = new JScrollPane(toolPanel);
		    pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		    pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return pane;
	}
	
	private void createTool(List<ITool> list,JPanel root){
		for(ITool tool:list){
			tool.init();
			tool.setViewManager(viewManager);
			insert(root, tool,-1);
	    }
	}
	
	protected void remove(JPanel root, int index) {
		root.remove(index);
		
	}
	protected void insert(JPanel root, ITool tool,int index) {
		JComponent comp = tool.getToolView();
		
		if(index<0)
			root.add(comp);
		else
			root.add(comp,index);
	}

	@Override
	public String getLocation() {
		return FOOTER_CENTER;
	}

	@Override
	public void updateView() {

	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}

	@Override
	public void installTool(ITool tool) {
		logger.info("installing"+tool.getDisplayName());
		toolMap.put(tool.getId(),tool);
	}

	@Override
	public void uninstallTool(ITool tool) {
		logger.info("uninstalling"+tool.getDisplayName());
		toolMap.remove(tool.getId());
		
	}

	@Override
	public ITool getTool(String id) {
		return toolMap.get(id);
	}

	@Override
	public Collection<ITool> getTools() {
		return toolMap.values();
	}

	@Override
	public void addToolChangeListener(EventListener listener) {
		toolMap.addPropertyChangeListener((PropertyChangeListener)listener);
	}

	@Override
	public void removeToolChangeListener(EventListener listener) {
		toolMap.removePropertyChangeListener((PropertyChangeListener)listener);
	}


}
