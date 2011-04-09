package com.shijie.media.client.platform;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.module.IFunctionManager;
import com.shijie.media.client.api.service.DBService;
import com.shijie.media.client.api.service.IService;
import com.shijie.media.client.api.service.IServiceManager;
import com.shijie.media.client.api.ui.IPlatform;
import com.shijie.media.client.api.ui.ISkin;
import com.shijie.media.client.api.ui.ISkinManager;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Category;
import com.shijie.media.client.utils.DBUtils;
import com.shijie.media.client.utils.InitConfig;

public class PlatformComponent implements IPlatform{
	
	private Logger logger = LoggerFactory.getLogger(PlatformComponent.class);
	
	private JFrame mainFrame;
	
	public IViewManager viewManager;
	public ISkinManager skinManager;
	public IServiceManager serviceManager;
	public IFunctionManager functonManager;
	
	public PlatformComponent(){
		logger.info("platform instance created.");
	}
	
	@Override
	public void installViewMananger(IViewManager viewManager) {
		this.viewManager = viewManager;
	}
	
	public void uninstallViewMananger(IViewManager viewManager) {
		this.viewManager = null;
	}

	@Override
	public void installSkinMananger(ISkinManager skinManager) {
		this.skinManager = skinManager;
	}
	
	public void uninstallSkinMananger(ISkinManager skinManager) {
		this.skinManager = null;
	}

	@Override
	public void installServiceManager(IServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}
	
	public void uninstallServiceManager(IServiceManager serviceManager) {
		this.serviceManager = null;
	}
	
	/**
	 * 初始所有服务。
	 */
	private void initService(){
		Collection<IService> services = serviceManager.getServices();
		
		DBService dbService = (DBService) serviceManager.getService(DBService.ID);
		dbService.init(null);
		dbService.start();		
		
		Iterator<IService> it = services.iterator();
		while(it.hasNext()){
			IService service = it.next();
			if(!DBService.ID.equals(service.getServiceID())){
				service.init(DBUtils.getConfig(dbService,Category.CAT_SERVICE,service.getServiceID()));
				service.start();
			}
		}	
		
	}

	@Override
	public void launch() {
		logger.info("platform launch...");
		initService();

		InitConfig.initGroup((DBService) serviceManager.getService(DBService.ID));
		
		Runnable appStarter = new Runnable() {
			public void run() {
				
				DBService dbService = (DBService) serviceManager.getService(DBService.ID);
				
				HashMap<String, Object> config = DBUtils.getConfig(dbService,Category.CAT_PLATFROM,Constraints.PLATFORM).getProps();
				
				initGlobalFontSetting((Font) config.get(Constraints.PLATFORM_FONT)); 
				
				UIManager.put(SubstanceLookAndFeel.TABBED_PANE_CLOSE_BUTTONS_PROPERTY,Boolean.TRUE);
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
				
				mainFrame = new JFrame((String)config.get(Constraints.PLATFORM_TITLE));
				mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				mainFrame.setLayeredPane((JLayeredPane) viewManager.getViews());
				mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
				mainFrame.getRootPane().putClientProperty(SubstanceLookAndFeel.WATERMARK_VISIBLE, true);
				ISkin skin = (ISkin) skinManager.getSkin((String) config.get(Constraints.PLATFORM_SKIN));
				skinManager.changeSkin(skin);

				mainFrame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(appStarter);
	}

	/**
	 * 初始默认字体。
	 * @param fnt
	 */
	private void initGlobalFontSetting(Font fnt){
		FontUIResource fontRes = new FontUIResource(fnt);
		for(Enumeration<?> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource)
				UIManager.put(key, fontRes);
			else if(UIManager.get(key) instanceof Font)
			    UIManager.put(key, fnt);

		}
	}



}
