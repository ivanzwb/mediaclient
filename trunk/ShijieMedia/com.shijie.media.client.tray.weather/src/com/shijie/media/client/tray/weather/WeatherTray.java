package com.shijie.media.client.tray.weather;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.shijie.media.client.api.module.IModule;
import com.shijie.media.client.api.module.ITray;
import com.shijie.media.client.api.ui.IViewManager;
import com.shijie.media.client.entity.Config;

public class WeatherTray implements ITray{

	private final static String CITY="weather.city";
	private IViewManager viewManager;
	private JLabel trayLabel;
	
	private String id;
	
	private String displayName;
	
	private URL iconURL;
	private URL selectedIconURL;
	private int order;
	private String city;
	
	private ImageIcon icon;
	private ImageIcon selectedIcon;
	
	private WeatherPanel weatherPanel;
	
	public WeatherTray(){
		initDefault();
	}
	
	private void initDefault(){
		id = "weather";
		displayName = "";
		iconURL = this.getClass().getResource("/ICON-INF/"+id+".png");
		selectedIconURL = iconURL;
		order = 3;
		city = "上海";
		
	}
	
	@Override
	public void init(Config config) {
		if(config!=null){
			id = (String) ignoreNull(config.getProps().get(IModule.MODULE_ID),id);
			displayName = (String)ignoreNull(config.getProps().get(IModule.MODULE_DISPLAYNAME),displayName);
			iconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_ICON),iconURL);
			selectedIconURL = (URL)ignoreNull(config.getProps().get(IModule.MODULE_SELECTEDICON),selectedIconURL);
			order = (Integer)ignoreNull(config.getProps().get(IModule.MODULE_ORDER),order);	
			city = (String)ignoreNull(config.getProps().get(CITY),city);	
		}
		
		icon = new ImageIcon(iconURL);
		selectedIcon = new ImageIcon(selectedIconURL);
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
	public Icon getIcon() {
		return icon;
	}

	@Override
	public Icon getSelectIcon() {
		return selectedIcon;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public void setViewManager(IViewManager viewManager) {
		this.viewManager = viewManager;
	}

	@Override
	public JComponent getTrayView() {
		if(trayLabel==null){
			trayLabel = new JLabel(getDisplayName(),getIcon(),JLabel.CENTER);
			trayLabel.setOpaque(false);
			trayLabel.setMinimumSize(new Dimension(60,60));
			trayLabel.setPreferredSize(new Dimension(60,60));
			trayLabel.setMaximumSize(new Dimension(60,60));
			trayLabel.setVerticalTextPosition(JLabel.BOTTOM);    
			trayLabel.setHorizontalTextPosition(JLabel.CENTER); 
			
			List<DayWeather> list = getForecast(city);
			DayWeather weather = list.get(0);
			
			final JPopupMenu menuPopup = new JPopupMenu();
			
			weatherPanel = new WeatherPanel();
			weatherPanel.setWeathers(list);
			menuPopup.add(weatherPanel);
			
			trayLabel.add(menuPopup);
			trayLabel.setIcon(new ImageIcon(weather.getStatusIcon()));
			trayLabel.addFocusListener(new FocusListener(){
	
				@Override
				public void focusGained(FocusEvent e) {
					
				}
	
				@Override
				public void focusLost(FocusEvent e) {
					menuPopup.setVisible(false);
				}
			});
			
			trayLabel.addMouseListener(new MouseAdapter(){
	
				@Override
				public void mouseEntered(MouseEvent e) {
					
					trayLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					
					trayLabel.setCursor(Cursor.getDefaultCursor());
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					if(menuPopup.isVisible())
						return;
					trayLabel.requestFocus();
					int x = (int) (trayLabel.getLocationOnScreen().x);
					int y = (int) (trayLabel.getLocationOnScreen().y-menuPopup.getPreferredSize().getHeight());
					menuPopup.setLocation(x,y);
					List<DayWeather> list = getForecast(city);
					weatherPanel.setWeathers(list);
					menuPopup.setVisible(true);
				}
				
			});
		}
		return trayLabel;
	}
	
	/**
	 * @param location
	 * @return
	 */
	private List<DayWeather> getForecast(final String location){
		final List<DayWeather> list = new ArrayList<DayWeather>();
		try {
			URL url = new URL("http://www.google.com/ig/api?hl=zh-cn&weather="+URLEncoder.encode(location));
			SAXParserFactory saxfac = SAXParserFactory.newInstance(); 
			SAXParser saxparser = saxfac.newSAXParser(); 
			InputSource is = new InputSource();  
			is.setByteStream(new ByteArrayInputStream(getResponse(url).getBytes()));
			saxparser.parse(is, new DefaultHandler(){
				DayWeather weather = null;
				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					
					if("current_conditions".equals(qName)){
						if(weather!=null)
							list.add(weather);
						weather = new DayWeather();
						weather.setCity(location);
					}
					if("temp_c".equals(qName)){
						weather.setTempCurrent(attributes.getValue("data"));
					}
					
					if("wind_condition".equals(qName)){
						weather.setWind(attributes.getValue("data"));
					}
					if("humidity".equals(qName)){
						weather.setHumidity(attributes.getValue("data"));
					}
					
					if("forecast_conditions".equals(qName)){
						if(weather!=null)
							list.add(weather);
						weather = new DayWeather();
						weather.setCity(location);
					}
				
					if("day_of_week".equals(qName)){
						weather.setDayOfWeek(attributes.getValue("data"));
					}
					if("low".equals(qName)){
						weather.setTempLow(attributes.getValue("data"));
					}
					if("high".equals(qName)){
						weather.setTempHigh(attributes.getValue("data"));
					}
					if("icon".equals(qName)){
						URL url;
						try {
							url = new URL("http://www.google.com"+attributes.getValue("data"));
							weather.setStatusIcon(url);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
					if("condition".equals(qName)){
						weather.setStatusText(attributes.getValue("data"));
					}
					
				}
				@Override
				public void endDocument() throws SAXException {
					if(weather!=null)
						list.add(weather);
				}
			});   
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	protected String getResponse(URL url) {  
        try {  
            URLConnection urlconn = url.openConnection();  
            urlconn.connect();  
            InputStream is = urlconn.getInputStream();  

            StringBuffer buf = new StringBuffer();  
            byte[] read_data = new byte[is.available()];  
            while (is.read(read_data)!=-1) {  
                buf.append(new String(read_data,"GBK"));  
            }  
            return buf.toString();
        } catch (MalformedURLException e) {    
            e.printStackTrace();  
            return "";  
        } catch (IOException e) {  
            e.printStackTrace();  
            return "";  
        }  
    }  
	

}
