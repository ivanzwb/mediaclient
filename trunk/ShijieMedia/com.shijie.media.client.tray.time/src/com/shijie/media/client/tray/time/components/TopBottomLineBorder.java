package com.shijie.media.client.tray.time.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.AbstractBorder;

/**
 * <p>Description: TopBottomLineBorder</p>
 * @author ivan
 */
public class TopBottomLineBorder extends AbstractBorder{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color lineColor;
    public TopBottomLineBorder(Color color){
        lineColor = color; 
    }

    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height){
        g.setColor(lineColor);
        g.drawLine(0, 0, c.getWidth(), 0);
        g.drawLine(0, c.getHeight() - 1, c.getWidth(),c.getHeight() - 1);
    }
}
