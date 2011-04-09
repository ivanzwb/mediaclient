package com.shijie.media.client.utils;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.shijie.media.client.api.service.DBService;
import com.shijie.media.client.entity.Category;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.platform.Constraints;

public class InitConfig {

	/**
	 * @param args
	 */
	public static void initCatPlatform(DBService dbService) {
		try {
			dbService.openConnection();
			Category cat = null;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", Category.CAT_PLATFROM);
			List<Category> catList = dbService.query(Category.class,map);
			if(catList.size()>0)
				cat = catList.get(0);
			else
				cat = new Category();
			
			cat.setCode(Category.CAT_PLATFROM);
			cat.setName("基础信息");
			cat.setDiscription("客户端基础信息配置");
			cat.setEnabled(true);
			
			map.clear();
			
			Config config = null;
			
			map.put("code", Constraints.PLATFORM);
			map.put("category", cat);
			List<Config> conList = dbService.query(Config.class,map);
			if(conList.size()>0)
				config = conList.get(0);
			else
				config = new Config();
			
			config.setName("基础信息");
			config.setCode(Constraints.PLATFORM);
			config.setCategory(cat);
			HashMap<String, Object> props = new HashMap<String, Object>();
			props.put(Constraints.PLATFORM_ID, "sjm001");
			props.put(Constraints.PLATFORM_TITLE, "视界传媒客户端");
			props.put(Constraints.PLATFORM_SKIN, "com.shijie.media.client.skin.defaut.BusinessBlackSteelSkin");
			props.put(Constraints.PLATFORM_FONT, new Font("宋体", Font.PLAIN, 12));
			config.setProps(props);
			config.setEnabled(true);
			
			dbService.store(config);
			
			dbService.commit();
		} finally {
			dbService.closeConnection();
		}
	}
	
	public static void initCatNetwork(DBService dbService) {
		try {
			dbService.openConnection();
			Category cat = null;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", Category.CAT_NETWORK);
			List<Category> catList = dbService.query(Category.class,map);
			if(catList.size()>0)
				cat = catList.get(0);
			else
				cat = new Category();
			
			cat.setCode(Category.CAT_NETWORK);
			cat.setName("网络信息");
			cat.setDiscription("网络信息配置");
			cat.setEnabled(true);
			
			map.clear();
			//******************************
			Config httpConfig = null;
			
			map.put("code", Constraints.HTTP_PROXY);
			map.put("category", cat);
			List<Config> httpConList = dbService.query(Config.class,map);
			if(httpConList.size()>0)
				httpConfig = httpConList.get(0);
			else
				httpConfig = new Config();
			
			httpConfig.setName("HTTP代理");
			httpConfig.setCode(Constraints.HTTP_PROXY);
			httpConfig.setCategory(cat);
			HashMap<String, Object> httpProps = new HashMap<String, Object>();
			httpProps.put("http.proxyHost", "");
			httpProps.put("http.nonProxyHosts","");
			httpProps.put("http.proxyPort", "");		
			httpProps.put("http.username","");
			httpProps.put("http.password","");
			httpConfig.setProps(httpProps);
			httpConfig.setEnabled(false);
			
			dbService.store(httpConfig);
			
			//******************************
			map.clear();
			
			Config httpsConfig = null;
			
			map.put("code", "HTTPSproxy");
			map.put("category", cat);
			List<Config> httpsConList = dbService.query(Config.class,map);
			if(httpsConList.size()>0)
				httpsConfig = httpsConList.get(0);
			else
				httpsConfig = new Config();
			
			httpsConfig.setName("HTTPS代理");
			httpsConfig.setCode("HTTPSProxy");
			httpsConfig.setCategory(cat);
			HashMap<String, Object> httpsProps = new HashMap<String, Object>();
			httpsProps.put("https.proxyHost", "");
			httpsProps.put("https.proxyPort", "");
			httpsProps.put("https.username","");
			httpsProps.put("https.password","");
			httpsConfig.setProps(httpsProps);
			httpsConfig.setEnabled(false);
			
			dbService.store(httpsConfig);
			
			//******************************
			map.clear();
			
			Config ftpConfig = null;
			
			map.put("code", "FTPProxy");
			map.put("category", cat);
			List<Config> ftpConList = dbService.query(Config.class,map);
			if(ftpConList.size()>0)
				ftpConfig = ftpConList.get(0);
			else
				ftpConfig = new Config();
			
			ftpConfig.setName("FTP代理");
			ftpConfig.setCode("FTPProxy");
			ftpConfig.setCategory(cat);
			HashMap<String, Object> ftpProps = new HashMap<String, Object>();
			ftpProps.put("ftp.proxyHost", "");
			ftpProps.put("ftp.noproxyHost", "");
			ftpProps.put("ftp.proxyPort", "");
			ftpProps.put("ftp.username","");
			ftpProps.put("ftp.password","");
			ftpConfig.setProps(ftpProps);
			ftpConfig.setEnabled(false);
			
			dbService.store(ftpConfig);
			
			//******************************
			map.clear();
			
			Config socksConfig = null;
			
			map.put("code", "SOCKSproxy");
			map.put("category", cat);
			List<Config> socksConList = dbService.query(Config.class,map);
			if(socksConList.size()>0)
				socksConfig = socksConList.get(0);
			else
				socksConfig = new Config();
			
			socksConfig.setName("SOCKS代理");
			socksConfig.setCode("SOCKSProxy");
			socksConfig.setCategory(cat);
			HashMap<String, Object> socksProps = new HashMap<String, Object>();
			socksProps.put("socksProxyHost", "");
			socksProps.put("socksProxyHost", "");
			socksProps.put("socks.username","");
			socksProps.put("socks.password","");
			socksConfig.setProps(socksProps);
			socksConfig.setEnabled(false);
			
			dbService.store(socksConfig);
			
			dbService.commit();
		} finally {
			dbService.closeConnection();
		}
	}
	
	public static void initTitleView(DBService dbService) {
		try {
			dbService.openConnection();
			Category cat = null;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", Category.CAT_UI_VIEW);
			List<Category> catList = dbService.query(Category.class,map);
			if(catList.size()>0)
				cat = catList.get(0);
			else
				cat = new Category();
			
			cat.setCode(Category.CAT_UI_VIEW);
			cat.setName("页面基础信息");
			cat.setDiscription("页面基础信息配置");
			cat.setEnabled(true);
			
			map.clear();
			
			Config TWconfig = null;
			
			map.put("code", Constraints.TW);
			map.put("category", cat);
			List<Config> TWconList = dbService.query(Config.class,map);
			if(TWconList.size()>0)
				TWconfig = TWconList.get(0);
			else
				TWconfig = new Config();
			
			TWconfig.setName("页面Title West信息");
			TWconfig.setCode(Constraints.TW);
			TWconfig.setCategory(cat);
			HashMap<String, Object> TWprops = new HashMap<String, Object>();
			TWprops.clear();
			TWprops.put(Constraints.TW_VERSION, "视频系统V1.0");
			TWconfig.setProps(TWprops);
			TWconfig.setEnabled(true);
			
			dbService.store(TWconfig);
			
			map.clear();
			
			Config TCconfig = null;
			
			map.put("code", Constraints.TC);
			map.put("category", cat);
			List<Config> TCconList = dbService.query(Config.class,map);
			if(TCconList.size()>0)
				TCconfig = TCconList.get(0);
			else
				TCconfig = new Config();
			
			TCconfig.setName("页面Title Center信息");
			TCconfig.setCode(Constraints.TC);
			TCconfig.setCategory(cat);
			HashMap<String, Object> TCprops = new HashMap<String, Object>();
			TCprops.clear();
			TCprops.put(Constraints.TC_MESSAGE, "离广告弹出还有");
			TCprops.put(Constraints.TC_ALERT_SCROLLTIME, 320);
			TCprops.put(Constraints.TC_ALERT_TITLE, "弹出广告");
			TCprops.put(Constraints.TC_ALERT_URL, "http://www.google.com.hk");
			TCprops.put(Constraints.TC_ALERT_DIMENSION, null);
	
			TCconfig.setProps(TCprops);
			TCconfig.setEnabled(true);
			
			dbService.store(TCconfig);
			
			map.clear();
			
			Config TEconfig = null;
			
			map.put("code", Constraints.TE);
			map.put("category", cat);
			List<Config> TEconList = dbService.query(Config.class,map);
			if(TEconList.size()>0)
				TEconfig = TEconList.get(0);
			else
				TEconfig = new Config();
			
			TEconfig.setName("页面Title East信息");
			TEconfig.setCode(Constraints.TE);
			TEconfig.setCategory(cat);
			HashMap<String, Object> TEprops = new HashMap<String, Object>();
			TEprops.clear();
			List<String> advList = new ArrayList<String>();
			List<String> linkList = new ArrayList<String>();
			advList.add("上海世界传媒祝大家新春快乐0！");
			linkList.add("http://blog.mapfilm.com0");
			advList.add("上海世界传媒祝大家新春快乐1！");
			linkList.add("http://blog.mapfilm.com1");
			advList.add("上海世界传媒祝大家新春快乐2！");
			linkList.add("http://blog.mapfilm.com2");
			TEprops.put(Constraints.TE_ADV_LIST, advList);
			TEprops.put(Constraints.TE_LINK_LIST, linkList);
			
			TEprops.put(Constraints.TE_SCROLLTIME, 2);
			
	
			TEconfig.setProps(TEprops);
			TEconfig.setEnabled(true);
			
			dbService.store(TEconfig);
			
			dbService.commit();
			
			
			
		} finally {
			dbService.closeConnection();
		}
	}
	
	public static void initLeftView(DBService dbService){
		try {
			dbService.openConnection();
			Category cat = null;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", Category.CAT_UI_VIEW);
			List<Category> catList = dbService.query(Category.class,map);
			if(catList.size()>0)
				cat = catList.get(0);
			else
				cat = new Category();
			
			cat.setCode(Category.CAT_UI_VIEW);
			cat.setName("页面基础信息");
			cat.setDiscription("页面基础信息配置");
			cat.setEnabled(true);
			
			map.clear();
			
			Config LNconfig = null;
			
			map.put("code", Constraints.LN);
			map.put("category", cat);
			List<Config> LNconList = dbService.query(Config.class,map);
			if(LNconList.size()>0)
				LNconfig = LNconList.get(0);
			else
				LNconfig = new Config();
			
			LNconfig.setName("页面Left North 信息");
			LNconfig.setCode(Constraints.LN);
			LNconfig.setCategory(cat);
			HashMap<String, Object> LNprops = new HashMap<String, Object>();
			LNprops.clear();
			LNprops.put(Constraints.LN_LOGO, "icon/logo.jpg");
			LNconfig.setProps(LNprops);
			LNconfig.setEnabled(true);
			
			dbService.store(LNconfig);			
			
			dbService.commit();
			
		} finally {
			dbService.closeConnection();
		}
	}
	
	public static void initRightView(DBService dbService){
		try {
			dbService.openConnection();
			Category cat = null;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", Category.CAT_UI_VIEW);
			List<Category> catList = dbService.query(Category.class,map);
			if(catList.size()>0)
				cat = catList.get(0);
			else
				cat = new Category();
			
			cat.setCode(Category.CAT_UI_VIEW);
			cat.setName("页面基础信息");
			cat.setDiscription("页面基础信息配置");
			cat.setEnabled(true);
			
			map.clear();
			
			Config RCconfig = null;
			
			map.put("code", Constraints.RC);
			map.put("category", cat);
			List<Config> RCconList = dbService.query(Config.class,map);
			if(RCconList.size()>0)
				RCconfig = RCconList.get(0);
			else
				RCconfig = new Config();
			
			RCconfig.setName("页面Right Center信息");
			RCconfig.setCode(Constraints.RC);
			RCconfig.setCategory(cat);
			HashMap<String, Object> RCprops = new HashMap<String, Object>();
			RCprops.clear();
			RCprops.put(Constraints.RC_URL, new File("").getAbsolutePath()+"/adv/rc_adv.html");
			RCprops.put(Constraints.RC_SCROLLTIME, 600);
			RCconfig.setProps(RCprops);
			RCconfig.setEnabled(true);
			
			dbService.store(RCconfig);			
			
			dbService.commit();
			
		} finally {
			dbService.closeConnection();
		}
	}
	
	public static void initCenterView(DBService dbService){
		
		try {
			dbService.openConnection();
			Category cat = null;
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", Category.CAT_UI_VIEW);
			List<Category> catList = dbService.query(Category.class,map);
			if(catList.size()>0)
				cat = catList.get(0);
			else
				cat = new Category();
			
			cat.setCode(Category.CAT_UI_VIEW);
			cat.setName("页面基础信息");
			cat.setDiscription("页面基础信息配置");
			cat.setEnabled(true);
			
			map.clear();
		
			Config CNconfig = null;
		
			map.put("code", Constraints.CN);
			map.put("category", cat);
			List<Config> CNconList = dbService.query(Config.class,map);
			if(CNconList.size()>0)
				CNconfig = CNconList.get(0);
			else
				CNconfig = new Config();
		
			CNconfig.setName("页面 Center North信息");
			CNconfig.setCode(Constraints.CN);
			CNconfig.setCategory(cat);
			HashMap<String, Object> CNprops = new HashMap<String, Object>();
			CNprops.clear();
			CNprops.put(Constraints.CN_URL, "adv/cn_adv/cn_adv.swf");
			CNprops.put(Constraints.CN_SCROLLTIME, 600);
			CNconfig.setProps(CNprops);
			CNconfig.setEnabled(true);
		
			dbService.store(CNconfig);
			
			map.clear();
			
			Config CSconfig = null;
		
			map.put("code", Constraints.CS);
			map.put("category", cat);
			List<Config> CSconList = dbService.query(Config.class,map);
			if(CSconList.size()>0)
				CSconfig = CSconList.get(0);
			else
				CSconfig = new Config();
		
			CSconfig.setName("页面Center South信息");
			CSconfig.setCode(Constraints.CS);
			CSconfig.setCategory(cat);
			HashMap<String, Object> CSprops = new HashMap<String, Object>();
			CSprops.clear();
			List<String> advList = new ArrayList<String>();
			List<String> linkList = new ArrayList<String>();
			advList.add("上海世界传媒祝大家新春快乐0！");
			linkList.add("http://blog.mapfilm.com0");
			advList.add("上海世界传媒祝大家新春快乐1！");
			linkList.add("http://blog.mapfilm.com1");
			advList.add("上海世界传媒祝大家新春快乐2！");
			linkList.add("http://blog.mapfilm.com2");
			CSprops.put(Constraints.CS_ADV_LIST, advList);
			CSprops.put(Constraints.CS_LINK_LIST, linkList);
			CSprops.put(Constraints.CS_SCROLLTIME, 2);
			
			CSconfig.setProps(CSprops);
			CSconfig.setEnabled(true);
		
			dbService.store(CSconfig);
			
			map.clear();
			
			Config CCconfig = null;
		
			map.put("code", Constraints.CC);
			map.put("category", cat);
			List<Config> CCconList = dbService.query(Config.class,map);
			if(CCconList.size()>0)
				CCconfig = CCconList.get(0);
			else
				CCconfig = new Config();
		
			CCconfig.setName("页面Center South信息");
			CCconfig.setCode(Constraints.CC);
			CCconfig.setCategory(cat);
			HashMap<String, Object> CCprops = new HashMap<String, Object>();
			CCprops.clear();
			CCprops.put("cc.home", "http://www.google.com.hk");
			CCprops.put("cc.browser.fullscreen", "RC,CC,CS");
			CCprops.put("cc.player.fullscreen", "CC,CS");
			CCprops.put("cc.embeddedadv.title", "电影播放暂停中。。。");
			CCprops.put("cc.embeddedadv.width", 250);
			CCprops.put("cc.embeddedadv.height", 250);
			CCprops.put("cc.embeddedadv.url", new File("").getAbsolutePath()+"/adv/cc_embedded_adv.html");
			CCprops.put("cc.player.maxsound",80);
			CCconfig.setProps(CCprops);
			CCconfig.setEnabled(true);
		
			dbService.store(CCconfig);
			
			dbService.commit();
			
		} finally {
			dbService.closeConnection();
		}
	}
	
	public static void initGroup(DBService dbService){
		initCatPlatform(dbService);
		initCatNetwork(dbService);
		initTitleView(dbService);
		initLeftView(dbService);
		initRightView(dbService);
		initCenterView(dbService);
	}

}
