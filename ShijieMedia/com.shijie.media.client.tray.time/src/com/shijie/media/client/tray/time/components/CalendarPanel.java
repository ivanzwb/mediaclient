package com.shijie.media.client.tray.time.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * @author ivan
 * 
 */
public class CalendarPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Border selectedBorder = new LineBorder(Color.black);
    private static Border unselectedBorder = new EmptyBorder(selectedBorder.getBorderInsets(new JLabel()));

    private Calendar calendar = null;
    private JPanel dayPanel = null;
    private JComboBox yearComboBox = null;
    private JComboBox monthComboBox = null;
    private JPanel days = null;
    private MouseListener dayBttListener = null;
    private boolean isSupportDateChangeListener = false;

    protected Color selectedBackground;
    protected Color selectedForeground;
    protected Color background;
    protected Color foreground;
    private java.util.Date selectedDate = null;

    
    final SimpleDateFormat monthFormat
        = new SimpleDateFormat("yyyy-MM");

    public CalendarPanel(){
        this(new Date());
    }

    public CalendarPanel(Date selectedDate){
        this.selectedDate = selectedDate;
        calendar = Calendar.getInstance();

        selectedBackground = UIManager.getColor(
            "ComboBox.selectionBackground");
        selectedForeground = UIManager.getColor(
            "ComboBox.selectionForeground");
        background = UIManager.getColor("ComboBox.background");
        foreground = UIManager.getColor("ComboBox.foreground");

        dayBttListener = createDayBttListener();

        //   << <   yyyy/MM/dd  > >>
        JPanel pNorth = new JPanel();
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.X_AXIS));
        pNorth.setBackground(new Color(0, 0, 128));
        pNorth.setForeground(Color.white);
        pNorth.setPreferredSize(new Dimension(1, 35));

        JLabel label;
        label = createSkipButton(Calendar.YEAR, -1);
        label.setText("<<");
        pNorth.add(Box.createHorizontalStrut(12));
        pNorth.add(label);
        pNorth.add(Box.createHorizontalStrut(12));

        label = createSkipButton(Calendar.MONTH, -1);
        label.setText("< ");
        pNorth.add(label);
        
        dayPanel = new JPanel();
        yearComboBox = new JComboBox();
        yearComboBox.setBackground(Color.white);
        yearComboBox.setForeground(Color.BLACK);
        for(int i=1900;i<9999;i++){
        yearComboBox.addItem(new Integer(i));
        }
       
        yearComboBox.addActionListener(new ActionListener(){
        
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource()==yearComboBox){
				if(calendar.get(Calendar.YEAR)!=((Integer)yearComboBox.getSelectedItem()).intValue()){
				calendar.set(Calendar.YEAR, ((Integer)yearComboBox.getSelectedItem()).intValue());
				updateDays();
				}
				}
			}
        	
        });
        
        monthComboBox = new JComboBox();
        monthComboBox.setBackground(Color.white);
        monthComboBox.setForeground(Color.black);
        for(int i=1;i<=12;i++)
        	monthComboBox.addItem(i);
       
        monthComboBox.addActionListener(new ActionListener(){
        	
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==monthComboBox){
				if(!(monthComboBox.getSelectedItem()).equals((calendar.get(Calendar.MONTH)+1))){
					calendar.set(Calendar.MONTH, (Integer)monthComboBox.getSelectedItem()-1);
					updateDays();
				}
				}
			}
        	
        });
        
        
        dayPanel.add(yearComboBox);
        dayPanel.add(monthComboBox);
        
        pNorth.add(Box.createHorizontalGlue());
        pNorth.add(dayPanel);
        pNorth.add(Box.createHorizontalGlue());

        label = createSkipButton(Calendar.MONTH, 1);
        label.setText(" >");
        pNorth.add(label);

        label = createSkipButton(Calendar.YEAR, 1);
        label.setText(">>");

        pNorth.add(Box.createHorizontalStrut(12));
        pNorth.add(label);
        pNorth.add(Box.createHorizontalStrut(12));

      
        JPanel pWeeks = new JPanel(new GridLayout(0, 7));
        pWeeks.setBackground(background);
        pWeeks.setOpaque(true);
        DateFormatSymbols sy = new DateFormatSymbols(Locale.getDefault());
        String strWeeks[] = sy.getShortWeekdays();
        for(int i = 1; i <= 7; i++){
            label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setForeground(foreground);
            label.setText(strWeeks[i]);
            pWeeks.add(label);
        }

    
        days = new JPanel(new GridLayout(0, 7));
        days.setBorder(new TopBottomLineBorder(Color.black));
        days.setBackground(background);
        days.setOpaque(true);
        JPanel pCenter = new JPanel(new BorderLayout());
        pCenter.setBackground(background);
        pCenter.setOpaque(true);
        pCenter.add(pWeeks, BorderLayout.NORTH);
        pCenter.add(days, BorderLayout.CENTER);


        JLabel lbToday = new JDayLabel(new Date(), false);
       
        lbToday.setForeground(foreground);
        lbToday.addMouseListener(dayBttListener);
        JPanel pSouth = new JPanel(new BorderLayout());
        pSouth.setBackground(background);
        pSouth.setForeground(foreground);
        pSouth.add(lbToday, BorderLayout.CENTER);

        //renderer this
        setPreferredSize(new Dimension(280, 180));
        setForeground(foreground);
        setBackground(background);
        setBorder(BorderFactory.createLineBorder(Color.black));

        setLayout(new BorderLayout());
        add(BorderLayout.NORTH, pNorth);
        add(BorderLayout.CENTER, pCenter);
        add(BorderLayout.SOUTH, pSouth);

        updateDays();
    }

    /**
     * 
     * @param field int
     * @param amount int
     * @return JLabel
     */
    protected JLabel createSkipButton(final int field, final int amount){
        JLabel label = new JLabel();
        label.setBorder(unselectedBorder);
        label.setBackground(new Color(0, 0, 128));
        label.setForeground(Color.white);
        label.setRequestFocusEnabled(false);
        label.addMouseListener(createSkipListener(field, amount));
        return label;
    } 

    protected MouseListener createSkipListener(final int field,
                                               final int amount){
        return new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                calendar.add(field, amount);
                updateDays();
            }

            public void mouseEntered(MouseEvent e){
                JComponent com = (JComponent)e.getComponent();
                com.setBorder(selectedBorder);
            }

            public void mouseExited(MouseEvent e){
                JComponent com = (JComponent)e.getComponent();
                com.setBorder(unselectedBorder);
            }
        };
    }

    /**
     * 
     */
    protected void updateDays(){
        //
        //monthLabel.setText(monthFormat.format(calendar.getTime()));
        yearComboBox.setSelectedItem(calendar.get(Calendar.YEAR));
        monthComboBox.setSelectedItem(calendar.get(Calendar.MONTH)+1);

        days.removeAll();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTime(selectedDate);
        /**
                        1  2  3  4  5  6  7
         2  3  4  5  6  7  8  9  10 11 12 13
         8  9  10 11 12 13 14 15 16 17 18 19
         14 15 16 17 18 19 20 21 22 23 24 25
         20 21 22 23 24 25 26 27 28 29 30 31
         26 27 28 29 30 31
         */
        Calendar setupCalendar = (Calendar)calendar.clone();
        setupCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int first = setupCalendar.get(Calendar.DAY_OF_WEEK);
        setupCalendar.add(Calendar.DATE, -first);

        boolean isCurrentMonth = false;
        for(int i = 0; i < 42; i++){
            setupCalendar.add(Calendar.DATE, 1);
            JLabel label = new JDayLabel(setupCalendar.getTime());
            label.setForeground(foreground);
            label.addMouseListener(dayBttListener);

            if("1".equals(label.getText())){
                isCurrentMonth = !isCurrentMonth;
            }
            label.setEnabled(isCurrentMonth);
            //
            if(setupCalendar.get(Calendar.YEAR) ==
               selectedCalendar.get(Calendar.YEAR) &&
               setupCalendar.get(Calendar.MONTH) ==
               selectedCalendar.get(Calendar.MONTH) &&
               setupCalendar.get(Calendar.DAY_OF_MONTH) ==
               selectedCalendar.get(Calendar.DAY_OF_MONTH)){
                label.setBorder(new LineBorder(selectedBackground, 1));
            }
            days.add(label);
        }
        days.validate();
    }

    protected MouseListener createDayBttListener(){
        return new MouseAdapter(){
            public void mousePressed(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}

            public void mouseReleased(MouseEvent e){
                JDayLabel com = (JDayLabel)e.getComponent();
                if(isEnabled()){
                    com.setOpaque(false);
                    com.setBackground(background);
                    com.setForeground(foreground);
                }
                CalendarPanel.this.isSupportDateChangeListener = true;
                CalendarPanel.this.setSelectedDate(com.getDate());
                CalendarPanel.this.isSupportDateChangeListener = false;
            }

            public void mouseEntered(MouseEvent e){
                if(isEnabled()){
                    JComponent com = (JComponent)e.getComponent();
                    com.setOpaque(true);
                    com.setBackground(selectedBackground);
                    com.setForeground(selectedForeground);
                }
            }

            public void mouseExited(MouseEvent e){
                if(isEnabled()){
                    JComponent com = (JComponent)e.getComponent();
                    com.setOpaque(false);
                    com.setBackground(background);
                    com.setForeground(foreground);
                }
            }
        };
    }

    protected EventListenerList listenerList = new EventListenerList();
    public void addDateChangeListener(ChangeListener l){
        listenerList.add(ChangeListener.class, l);
    }

    public void removeDateChangeListener(ChangeListener l){
        listenerList.remove(ChangeListener.class, l);
    }

    protected void fireDateChanged(ChangeEvent e){
        Object[] listeners = listenerList.getListenerList();
        for(int i = listeners.length - 2; i >= 0; i -= 2){
            if(listeners[i] == ChangeListener.class){
                ((ChangeListener)listeners[i + 1]).stateChanged(e);
            }
        }
    }

    public void setSelectedDate(Date selectedDate){
        this.selectedDate = selectedDate;
        this.calendar.setTime(selectedDate);
        updateDays();
        if(isSupportDateChangeListener){
            fireDateChanged(new ChangeEvent(selectedDate));
        }
    }

    public Date getSelectedDate(){
        return selectedDate;
    }

}

