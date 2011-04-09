package com.shijie.media.client.platform;

import com.shijie.media.client.api.service.DBService;
import com.shijie.media.client.api.service.DownloadService;
import com.shijie.media.client.api.service.SyncService;
import com.shijie.media.client.api.service.TimerService;
import com.shijie.media.client.api.service.UpdaterService;
import com.shijie.media.client.api.service.WebService;
import com.shijie.media.client.api.ui.IView;

public class Constraints {
	//platform config constraints
	public static final String PLATFORM = "Platform";
	public static final String PLATFORM_ID = "platform.id";
	public static final String PLATFORM_TITLE = "platform.title";
	public static final String PLATFORM_SKIN = "platform.skin";
	public static final String PLATFORM_FONT = "platform.font";
	
	//network config constraints
	public static final String HTTP_PROXY = "HTTPProxy";
	public static final String PROXY_HOST = "proxyHost";
	public static final String NON_PROXY_HOST = "nonProxyHosts";
	public static final String PROXY_PORT = "proxyPort";
	public static final String PROXY_SET = "proxySet";
	public static final String PROXY_USERNAME = "username";
	public static final String PROXY_PASSWORD = "password";
	
	public static final String FTP_PROXY = "FTPProxy";
	public static final String FTP_PROXY_HOST = "ftpProxyHost";
	public static final String FTP_PROXY_PORT = "ftpProxyPort";
	public static final String FTP_PROXY_SET = "ftpProxySet";
	public static final String FTP_PROXY_USERNAME = "username";
	public static final String FTP_PROXY_PASSWORD = "password";
	
	public static final String SOCKS4_PROXY = "Socks4Proxy";
	public static final String SOCKS4_PROXY_HOST = "socksProxyHost";
	public static final String SOCKS4_PROXY_PORT = "socksProxyPort";
	public static final String SOCKS4_PROXY_SET = "socksProxySet";
	public static final String SOCKS4_PROXY_USERNAME = "username";
	public static final String SOCKS4_PROXY_PASSWORD = "password";
	
	//service config constraints
	public static final String DB_SERVICE = DBService.ID;
	public static final String DB_FILE = "db.file";
	public static final String DB_SERVER = "db.server";
	public static final String DB_PORT = "db.port";
	public static final String DB_USERNAME = "username";
	public static final String DB_PASSWORD = "password";
	

	public static final String TIMER_SERVICE = TimerService.ID;
	
	public static final String DOWNLOAD_SERVICE = DownloadService.ID;
	public static final String DOWNLOAD_TEMP = "download.temp";
	public static final String DOWNLOAD_IDLE = "download.idle";
	public static final String DOWNLOAD_MAXSPEED = "download.maxspeed";
	public static final String DOWNLOAD_DATE = "download.date";
	public static final String DOWNLOAD_SERVER = "download.server";
	public static final String DOWNLOAD_USERNAME = "username";
	public static final String DOWNLOAD_PASSWORD = "password";
	
	public static final String WEB_SERVICE = WebService.ID;
	
	public static final String SYNC_SERVICE = SyncService.ID;
	
	public static final String UPDATER_SERVICE = UpdaterService.ID;
	
	
	//function config constraints
	public static final String MOIVE = "Moive";
	public static final String MOIVE_FULLSCREEN = "moive.fullscreen";
	public static final String MOIVE_URL = "moive.url";
	
	public static final String HAPPY = "Happy";
	public static final String HAPPY_URL = "happy.url";
	
	public static final String GOSSIP = "Gossip";
	public static final String GOSSIP_URL = "gossip.url";
	
	public static final String MUSIC = "Music";
	public static final String MUSIC_URL = "music.url";
	
	public static final String EZINE = "Ezine";
	public static final String EZINE_URL = "ezine.url";
	 
	public static final String HAIRSTYLE = "Hairstyle";
	public static final String HAIRSTYLE_URL = "hairstyle.url";
	
	public static final String ENTERTAINMENT = "Entertainment";
	public static final String ENTERTAINMENT_URL = "entertainment.url";
	
	public static final String FEMALE_CLASS = "FemaleClass";
	public static final String FEMALE_CLASS_URL = "femaleclass.url";
	
	//customer function config constraints
	public static final String YONG_QI = "YongQi";
	public static final String YONG_QI_URL = "yongqi.url";
	
	public static final String PARTNER = "Partner";
	public static final String PARTNER_URL = "partner.url";
		 
	public static final String SHOP_STYLE = "ShopStyle";
	public static final String SHOP_STYLE_URL = "shopstyle.url";
	
	public static final String TODAY = "Today";
	public static final String TODAY_URL = "today.url";
	
	//tool function config constraints
	public static final String BROWSER = "Browser";
	public static final String BROWSER_FULLSCREEN = "browser.fullscreen";
	public static final String BROWSER_URL = "browser.url";

	public static final String GAME = "Game";
	public static final String GAME_URL = "game.url";	
	
	public static final String QQ = "QQ";
	public static final String QQ_URL = "qq.url";	
	
	//tray config constraints
	public static final String TIME = "Time";
	public static final String TIME_FORMAT = "time.format";		
	
	public static final String WEATHER = "Weather";
	public static final String WEATHER_LOCATION = "weather.location";		

	public static final String SOUND = "Sound";
	public static final String SOUND_MAXSOUND = "sound.maxsound";
	
	//view config constraints
	public static final String TW = IView.TITLE_WEST;
	public static final String TW_VERSION = "tw.version";
	
	public static final String TC = IView.TITLE_CENTER;
	public static final String TC_MESSAGE = "tc.message";
	public static final String TC_ALERT_TITLE = "tc.alert.title";
	public static final String TC_ALERT_SCROLLTIME = "tc.alert.scrolltime";
	public static final String TC_ALERT_URL = "tc.alert.url";
	public static final String TC_ALERT_DIMENSION = "tc.alert.dimension";
	
	public static final String TE = IView.TITLE_EAST;
	public static final String TE_SCROLLTIME = "te.scrolltime";
	public static final String TE_ADV_LIST = "te.advlist";
	public static final String TE_LINK_LIST = "te.linklist";
	
	public static final String RC = IView.RIGHT_CENTER;
	public static final String RC_SCROLLTIME = "rc.scrolltime";
	public static final String RC_URL = "rc.url";

	public static final String LN = IView.LEFT_NORTH;
	public static final String LN_LOGO = "ln.logo";
	
	public static final String LC = IView.LEFT_CENTER;

	
	public static final String CN = IView.CENTER_NORTH;
	public static final String CN_URL = "cn.url";
	public static final String CN_SCROLLTIME = "cn.scrolltime";

	public static final String CC = IView.CENTER_CENTER;
	
	public static final String CS = IView.CENTER_SOUTH;
	public static final String CS_SCROLLTIME = "cccs.scrolltime";
	public static final String CS_ADV_LIST = "cccs.advlist";
	public static final String CS_LINK_LIST = "cccs.linklist";
	public static final String CS_USER_EDIT_NO = "cccs.usereditno";

	public static final String FW = IView.FOOTER_WEST;
	
	public static final String FC = IView.FOOTER_CENTER;
	
	public static final String FE = IView.FOOTER_EAST;
	
	public static final String PLAY_MOVIE_COMMOND = "movie:";
	public static final String PLAY_MUSIC_COMMOND = "music:";

}
