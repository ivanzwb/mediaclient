package com.shijie.media.client.platform;

import java.util.Collection;
import java.util.HashMap;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.ui.ISkin;
import com.shijie.media.client.api.ui.ISkinManager;
import com.shijie.media.client.platform.skin.BusinessBlackSteelSkin;

public class SkinManager implements ISkinManager {

	private Logger logger = LoggerFactory.getLogger(SkinManager.class);
	
	private HashMap<String, ISkin> map = new HashMap<String, ISkin>();
	private ISkin selectedSkin = null;
	
	public SkinManager(){
		logger.info("skin manager instance created...");
	}
	
	@Override
	public void installSkin(ISkin skin) {
		logger.info("install skin:"+skin.getDisplayName());
		map.put(skin.getSkinId(), skin);
	}
	
	public void uninstallSkin(ISkin skin){
		map.remove(skin.getDisplayName());
	}

	@Override
	public void changeSkin(final ISkin skin) {
		Runnable doRun = new Runnable(){

			@Override
			public void run() {
				
				if(skin==null){
					SubstanceSkin defaultSkin = new BusinessBlackSteelSkin();
					try {
						logger.info("set to default skin:"+defaultSkin.getDisplayName());
						SubstanceLookAndFeel.setSkin(defaultSkin);
						installSkin((ISkin) defaultSkin);
					} catch (Exception e) {
						//ignore;
					}
					UIManager.getLookAndFeelDefaults().put("ClassLoader", defaultSkin.getClass().getClassLoader());
				}else{
					logger.info("change skin to:"+skin.getDisplayName());
					SubstanceLookAndFeel.setSkin((SubstanceSkin) skin);
					selectedSkin = skin;
					UIManager.getLookAndFeelDefaults().put("ClassLoader", skin.getClass().getClassLoader());
				}				
			}
			
		};
		SwingUtilities.invokeLater(doRun);
	}

	@Override
	public ISkin getSelectedSkin() {
		return selectedSkin;
	}

	@Override
	public ISkin getSkin(String id) {
		return map.get(id);
	}

	@Override
	public Collection<ISkin> getSkins() {
		return map.values();
	}
	
	

}
