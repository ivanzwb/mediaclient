package com.shijie.media.client.platform.ui.footer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Comparator;
import java.util.EventListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.module.IFunction;
import com.shijie.media.client.api.module.IFunctionManager;
import com.shijie.media.client.api.ui.IEvent;
import com.shijie.media.client.api.ui.IView;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.utils.SortValueMap;

public class FWView implements IView ,IFunctionManager{
	private Logger logger = LoggerFactory.getLogger(FWView.class);
	private JPanel cusFunctionBox;
	private JScrollPane pane;
	private IViewManager viewManager;
	
	private Comparator<IFunction> sort = new Comparator<IFunction>(){
		
		public int compare(IFunction fun1, IFunction fun2) {
			if(fun1.getOrder()>fun2.getOrder())
				return 1;
			else if(fun1.getOrder()<fun2.getOrder())
				return -1;
			else 
				return 0;
		}

	};
	
	private SortValueMap<String,IFunction> cusFuncMap = new SortValueMap<String,IFunction>(sort);
	
	@Override
	public void init() {

	}

	@Override
	public JComponent getView() {
		if(pane==null){
			cusFunctionBox = new JPanel();
			BoxLayout layout = new BoxLayout(cusFunctionBox, BoxLayout.X_AXIS);
			cusFunctionBox.setLayout(layout);
		    List<IFunction> list = (List<IFunction>) getFunctions();
		    
		    addFunctionChangeListener(new PropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					final String operate = evt.getPropertyName();
					final IFunction function = (IFunction)evt.getOldValue();
					final int index = (Integer)evt.getNewValue();
					Runnable doRun = new Runnable(){
						@Override
						public void run() {
							
							if("add".equals(operate)){
								insert(cusFunctionBox,function,index);
							}else if("remove".equals(operate)){
								remove(cusFunctionBox,index);
							}
							cusFunctionBox.updateUI();
							logger.info(operate+" Customer function:"+function.getDisplayName());
						}
					};
					SwingUtilities.invokeLater(doRun);
				}
		    });
		    cusFunctionBox.add(Box.createHorizontalStrut(10));
		    createFunction(list,cusFunctionBox);

		    pane = new JScrollPane(cusFunctionBox);
		    pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		    pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return pane;
	}
	
	private void createFunction(List<IFunction> list,JPanel root){
		for(final IFunction func:list){
			func.init();
			insert(root, func,-1);
	    }
	}
	
	protected void remove(JPanel root, int index) {
		root.remove(index);
		
	}
	protected void insert(JPanel root, final IFunction function,int index) {
		final JButton functionBut = new JButton(function.getDisplayName(),function.getIcon());
		functionBut.setOpaque(false);
		functionBut.setContentAreaFilled(false);
		functionBut.setBorderPainted(false);
		functionBut.setMinimumSize(new Dimension(80,65));
		functionBut.setPreferredSize(new Dimension(80,65));
		functionBut.setMaximumSize(new Dimension(80,65));
		functionBut.setHorizontalAlignment(JButton.CENTER);
		functionBut.setVerticalTextPosition(JButton.BOTTOM);    
		functionBut.setHorizontalTextPosition(JButton.CENTER); 
		if(function.getSelectIcon()!=null)
			functionBut.setSelectedIcon(function.getSelectIcon());
		
		functionBut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				final ActionEvent event = new ActionEvent(functionBut,IEvent.NO_DECORATED_BROWSER,function.getLink());
	        	Runnable doRun = new Runnable(){
	    			@Override
	    			public void run() {
	    				viewManager.postEvent(event);
	    			}
	        	};
	        	SwingUtilities.invokeLater(doRun);
			}
		});
		
		functionBut.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				functionBut.setBorderPainted(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				functionBut.setBorderPainted(false);
			}
		});
		
		functionBut.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				functionBut.setBorderPainted(true);
				functionBut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				functionBut.setBorderPainted(false);
				functionBut.setCursor(Cursor.getDefaultCursor());
			}
			
		});
		if(index<0)
			root.add(functionBut);
		else
			root.add(functionBut,index);
	}


	@Override
	public String getLocation() {
		return FOOTER_WEST;
	}

	@Override
	public void updateView() {
		

	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;

	}

	@Override
	public void installFunction(IFunction function) {
		if(function.getType() == IFunction.CUS_FUNCTION){
			logger.info(function.getDisplayName()+" installing....");
			cusFuncMap.put(function.getId(),function);
		}
	}

	@Override
	public void uninstallFunction(IFunction function) {
		if(function.getType() == IFunction.CUS_FUNCTION){
			logger.info("uninstall function:"+function.getDisplayName());
			cusFuncMap.remove(function.getId());
		}
	}

	@Override
	public IFunction getFunction(String id) {
		return cusFuncMap.get(id);
	}

	@Override
	public Collection<IFunction> getFunctions() {
		return cusFuncMap.values();
	}

	@Override
	public void addFunctionChangeListener(EventListener listener) {
		cusFuncMap.addPropertyChangeListener((PropertyChangeListener) listener);
		
	}

	@Override
	public void removeFunctionChangeListener(EventListener listener) {
		cusFuncMap.removePropertyChangeListener((PropertyChangeListener)listener);
		
	}

}
