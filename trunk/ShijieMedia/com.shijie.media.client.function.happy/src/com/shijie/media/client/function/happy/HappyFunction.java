package com.shijie.media.client.function.happy;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.shijie.media.client.api.module.IFunction;
import com.shijie.media.client.api.module.IModule;
import com.shijie.media.client.api.service.WebService;
import com.shijie.media.client.entity.Config;

public class HappyFunction  implements IFunction{

	private String id;
	
	private String url;
	
	private String displayName;
	
	private URL iconURL;
	private URL selectedIconURL;
	private int order;
	
	private ImageIcon icon;
	private ImageIcon selectedIcon;
	
	public HappyFunction(){
		initDefault();
	}
	
	private void initDefault(){
		id = "happy";
		url = "http://localhost/"+id+"/index.html";
		displayName = "开心一刻";
		iconURL = this.getClass().getResource("/ICON-INF/"+id+".png");
		selectedIconURL = iconURL;
		order = 3;
	}
	
	@Override
	public void init(Config config) {
		if(config!=null){
			id = (String) ignoreNull(config.getProps().get(IModule.MODULE_ID),url);
			url = (String) ignoreNull(config.getProps().get(IModule.MODULE_URL),url);
			displayName = (String)ignoreNull(config.getProps().get(IModule.MODULE_DISPLAYNAME),url);
			iconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_ICON),url);
			selectedIconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_SELECTEDICON),url);
			order = (Integer)ignoreNull(config.getProps().get(IModule.MODULE_ORDER),url);	
		}
		
		icon = new ImageIcon(iconURL);
		selectedIcon = new ImageIcon(selectedIconURL);
		
		registerWeb();
	}
	
	private Object ignoreNull(Object v,Object d){
		if(v == null)
			return d;
		else 
			return v;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getType() {
		return IFunction.FUNCTION;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public Icon getSelectIcon() {
		return selectedIcon;
	}

	@Override
	public String getLink() {
		return url;
	}
	
	@Override
	public int getOrder() {
		return order;
	}
	
	public void registerWeb(){
		WebService webService = (WebService)Activator.getServiceManager().getService(WebService.ID);
		webService.registerWebContext(Activator.context,"/"+id, "/web");
	}
}
