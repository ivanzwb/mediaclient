package com.shijie.media.client.tray.weather;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WeatherPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2408784139860433842L;
	private List<DayWeather> weathers = null;
	private JLabel cityLabel;
	private JLabel currentIconLabel;
	private JLabel currentTempLabel;
	private JLabel nowStatusLabel;
	private JLabel windLabel;
	private JLabel humidityLabel;
	private JLabel date1Label,date2Label,date3Label,date4Label;
	private JLabel for1Icon,for2Icon,for3Icon,for4Icon;
	private JLabel temp1Label,temp2Label,temp3Label,temp4Label;
	
	
	
	public WeatherPanel(){
		this.setLayout(new BorderLayout());
		
		JPanel pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.X_AXIS));
        pNorth.setPreferredSize(new Dimension(1, 35));
        pNorth.add(Box.createHorizontalStrut(10));
        cityLabel = new JLabel();
        pNorth.add(cityLabel);
        
        this.add(pNorth,BorderLayout.NORTH);
        
        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));
        weatherPanel.add(Box.createVerticalGlue());
        
        JPanel currentPanel = new JPanel();
        currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.X_AXIS));
        currentPanel.add(Box.createHorizontalGlue());
        currentIconLabel = new JLabel();
        currentPanel.add(currentIconLabel);
        currentPanel.add(Box.createHorizontalGlue());
        currentTempLabel = new JLabel();
        currentPanel.add(currentTempLabel);
        currentPanel.add(Box.createHorizontalGlue());
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        nowStatusLabel = new JLabel();
        detailPanel.add(nowStatusLabel);
        windLabel = new JLabel();
        detailPanel.add(windLabel);
        humidityLabel = new JLabel();
        detailPanel.add(humidityLabel);
        currentPanel.add(detailPanel);
        currentPanel.add(Box.createHorizontalGlue());
        weatherPanel.add(currentPanel);
        weatherPanel.add(Box.createVerticalGlue());
        
        JPanel forCasePanel = new JPanel();
        forCasePanel.setLayout(new BoxLayout(forCasePanel, BoxLayout.X_AXIS));
        forCasePanel.add(Box.createHorizontalGlue());
        
        JPanel forCast1Panel = new JPanel();
        forCast1Panel.setLayout(new BoxLayout(forCast1Panel, BoxLayout.Y_AXIS));
        date1Label = new JLabel();
        forCast1Panel.add(date1Label);
        for1Icon = new JLabel();
        forCast1Panel.add(for1Icon);
        temp1Label = new JLabel();
        forCast1Panel.add(temp1Label);
        
        forCasePanel.add(forCast1Panel);
        forCasePanel.add(Box.createHorizontalGlue());
        
        JPanel forCast2Panel = new JPanel();
        forCast2Panel.setLayout(new BoxLayout(forCast2Panel, BoxLayout.Y_AXIS));
        date2Label = new JLabel();
        forCast2Panel.add(date2Label);
        for2Icon = new JLabel();
        forCast2Panel.add(for2Icon);
        temp2Label = new JLabel();
        forCast2Panel.add(temp2Label);
        
        forCasePanel.add(forCast2Panel);
        forCasePanel.add(Box.createHorizontalGlue());
        
        JPanel forCast3Panel = new JPanel();
        forCast3Panel.setLayout(new BoxLayout(forCast3Panel, BoxLayout.Y_AXIS));
        date3Label = new JLabel();
        forCast3Panel.add(date3Label);
        for3Icon = new JLabel();
        forCast3Panel.add(for3Icon);
        temp3Label = new JLabel();
        forCast3Panel.add(temp3Label);
        
        forCasePanel.add(forCast3Panel);
        forCasePanel.add(Box.createHorizontalGlue());
        
        JPanel forCast4Panel = new JPanel();
        forCast4Panel.setLayout(new BoxLayout(forCast4Panel, BoxLayout.Y_AXIS));
        date4Label = new JLabel();
        forCast4Panel.add(date4Label);
        for4Icon = new JLabel();
        forCast4Panel.add(for4Icon);
        temp4Label = new JLabel();
        forCast4Panel.add(temp4Label);
        
        
        forCasePanel.add(forCast4Panel);
        forCasePanel.add(Box.createHorizontalGlue());
        
        weatherPanel.add(forCasePanel);
        weatherPanel.add(Box.createVerticalGlue());
        
        this.add(weatherPanel,BorderLayout.CENTER);
        setPreferredSize(new Dimension(280, 180));
	}

	protected void updateWeather() {
		DayWeather weather = weathers.get(0);
		cityLabel.setText("城市："+weather.getCity());
		currentIconLabel.setIcon(new ImageIcon(weather.getStatusIcon()));
		currentTempLabel.setText(weather.getTempCurrent()+"℃");
		nowStatusLabel.setText("当前："+weather.getStatusText());
		windLabel.setText("风向："+weather.getWind());
		humidityLabel.setText("湿度："+weather.getHumidity());
		
		weather = weathers.get(1);
		date1Label.setText(weather.getDayOfWeek());
		for1Icon.setIcon(new ImageIcon(weather.getStatusIcon()));
		temp1Label.setText(weather.getTempLow()+" | "+weather.getTempHigh());
		
		weather = weathers.get(2);
		date2Label.setText(weather.getDayOfWeek());
		for2Icon.setIcon(new ImageIcon(weather.getStatusIcon()));
		temp2Label.setText(weather.getTempLow()+" | "+weather.getTempHigh());
		
		weather = weathers.get(3);
		date3Label.setText(weather.getDayOfWeek());
		for3Icon.setIcon(new ImageIcon(weather.getStatusIcon()));
		temp3Label.setText(weather.getTempLow()+" | "+weather.getTempHigh());
		
		weather = weathers.get(4);
		date4Label.setText(weather.getDayOfWeek());
		for4Icon.setIcon(new ImageIcon(weather.getStatusIcon()));		
		temp4Label.setText(weather.getTempLow()+" | "+weather.getTempHigh());


	}

	public List<DayWeather> getWeathers() {
		return weathers;
	}

	public void setWeathers(List<DayWeather> weathers) {
		this.weathers = weathers;
		updateWeather();
	}

}
