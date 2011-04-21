package com.shijie.media.client.tray.time.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * <p>Title: JDatePicker</p>
 * <p>Description:JDayLable </p>
 * @version 1.0
 */


public class JDayLabel extends JLabel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon todayIcon;

    Date date = null;
    ImageIcon currentIcon = null;


    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    /**
     *
     */
    final SimpleDateFormat dayFormat = new SimpleDateFormat("d");

    public JDayLabel(Date date){
        this(date, true);
        todayIcon = new ImageIcon(this.getClass().getResource("/ICON-INF/today.gif"));
    }

    public JDayLabel(Date date, boolean isSmallLabel){
        setPreferredSize(new Dimension(40, 20));
        setToolTipText(dateFormat.format(date));
        this.date = date;
        if(isSmallLabel){ 
            setHorizontalAlignment(JLabel.CENTER);
            setText(dayFormat.format(date));
            Date d = new Date();
            if(dateFormat.format(date).equals(dateFormat.format(d))){
                currentIcon = todayIcon;
            }
        } else{
            setText("Today:" + dateFormat.format(new Date()));
            setIcon(todayIcon);
            setHorizontalAlignment(JLabel.LEFT);
        }
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void paint(Graphics g){
        super.paint(g);
        if(currentIcon != null && isEnabled()){
            int x = (this.getWidth() - currentIcon.getIconWidth()) / 2;
            int y = (this.getHeight() - currentIcon.getIconHeight()) / 2;
            currentIcon.paintIcon(this, g, x, y);
        }
    }
}
