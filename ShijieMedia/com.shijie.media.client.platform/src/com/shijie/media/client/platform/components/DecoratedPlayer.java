package com.shijie.media.client.platform.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import chrriis.dj.nativeswing.swtimpl.components.JVLCPlayer;
import chrriis.dj.nativeswing.swtimpl.components.VLCInput;
import chrriis.dj.nativeswing.swtimpl.components.VLCInput.VLCMediaState;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;

public class DecoratedPlayer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4263327829504143965L;
	private JVLCPlayer vlcPlayer;
	private VLCPlayerControlBar controllBar;
	private Thread updateThread;
	private int defaultSound = 50;
	private int maxsound = 100;

	public DecoratedPlayer() {
		setLayout(new BorderLayout());

		vlcPlayer = new JVLCPlayer(JVLCPlayer.destroyOnFinalization());
		WebBrowserAdapter webBrowserListener = new WebBrowserAdapter() {
	        @Override
	        public void locationChanged(WebBrowserNavigationEvent e) {
	        	controllBar.adjustButtonState();
	        }
	    };
	    vlcPlayer.getWebBrowser().addWebBrowserListener(webBrowserListener);
		add(vlcPlayer, BorderLayout.CENTER);

		controllBar = new VLCPlayerControlBar();
		add(controllBar, BorderLayout.SOUTH);

	}

	public VLCPlayerControlBar getControllBar() {
		return controllBar;
	}

	protected String getTimeDisplay(int currentTime, int totalTime) {
		boolean showHours = totalTime >= 3600000;
		return formatTime(currentTime, showHours) + " / "+ formatTime(totalTime, showHours);
	}

	private String formatTime(int milliseconds, boolean showHours) {
		int seconds = milliseconds / 1000;
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = seconds % 60;
		StringBuilder sb = new StringBuilder();
		if (hours != 0 || showHours) {
			sb.append(hours).append(':');
		}
		sb.append(minutes < 10 ? "0" : "").append(minutes).append(':');
		sb.append(seconds < 10 ? "0" : "").append(seconds);
		return sb.toString();
	}
	
	public DecoratedPlayer getThis(){
		return this;
	}

	public class VLCPlayerControlBar extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8868411614472704775L;
		private JButton playButton;
		private JButton stopButton;
		private JSlider seekBarSlider;
		private volatile boolean isAdjustingSeekBar;
		private JLabel timeLabel;
		private JButton volumeButton;
		private JSlider volumeSlider;
		private JButton fullButton;

		public static final int STOP = 0;
		public static final int PLAY = 1;
		public static final int PAUSE = 2;

		private int playerStatus = STOP;
		
		private ImageIcon playIcon = new ImageIcon(this.getClass().getResource("/ICON-INF/play.png"));
		private ImageIcon pauseIcon = new ImageIcon(this.getClass().getResource("/ICON-INF/pause.png"));
		private ImageIcon soundIcon = new ImageIcon(this.getClass().getResource("/ICON-INF/sound.png"));
		private ImageIcon muteIcon = new ImageIcon(this.getClass().getResource("/ICON-INF/mute.png"));

		public VLCPlayerControlBar() {
			this.setLayout(new BorderLayout());

			JPanel playButtonPanel = new JPanel();
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			playButtonPanel.setLayout(layout);


			playButton = new JButton(playIcon);
			playButton.setOpaque(false);
			playButton.setContentAreaFilled(false);
			playButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (playerStatus == STOP) {
						vlcPlayer.getVLCPlaylist().play();
						playButton.setIcon(pauseIcon);
						seekBarSlider.setEnabled(true);
						stopButton.setEnabled(true);
						startUpdateThread();
						playerStatus = PLAY;
					} else if (playerStatus == PLAY) {
						vlcPlayer.getVLCPlaylist().togglePause();
						playButton.setIcon(playIcon);
						playerStatus = PAUSE;
					} else if (playerStatus == PAUSE) {
						vlcPlayer.getVLCPlaylist().togglePause();
						playButton.setIcon(pauseIcon);
						playerStatus = PLAY;
					}
					ActionListener action = (ActionListener)getThis().getClientProperty("player.status.action");
					action.actionPerformed(e);
				}
			});
			playButtonPanel.add(playButton);

			stopButton = new JButton(new ImageIcon(this.getClass().getResource("/ICON-INF/stop.png")));
			stopButton.setOpaque(false);
			stopButton.setEnabled(false);
			stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vlcPlayer.getVLCPlaylist().stop();
					playerStatus = STOP;
					playButton.setIcon(playIcon);
					stopButton.setEnabled(false);
					seekBarSlider.setEnabled(false);
					seekBarSlider.setValue(0);
					stopUpdateThread();
					ActionListener action = (ActionListener)getThis().getClientProperty("player.status.action");
					action.actionPerformed(e);
				}
			});
			playButtonPanel.add(stopButton);
			add(playButtonPanel, BorderLayout.WEST);

			seekBarSlider = new JSlider(0, 10000, 0);
			seekBarSlider.setEnabled(false);
			seekBarSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (!isAdjustingSeekBar) {
						vlcPlayer.getVLCInput().setRelativePosition(((float) seekBarSlider.getValue()) / 10000);
					}
				}
			});
			seekBarSlider.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
			add(seekBarSlider, BorderLayout.CENTER);

			JPanel volumePanel = new JPanel();
			FlowLayout volumeLayout = new FlowLayout();
			volumeLayout.setAlignment(FlowLayout.LEFT);
			
			timeLabel = new JLabel("00:00/00:00");
			volumePanel.add(timeLabel);
			
			volumeButton = new JButton(soundIcon);
			volumeButton.setOpaque(false);
			volumeButton.setContentAreaFilled(false);
			volumeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vlcPlayer.getVLCAudio().toggleMute();
					adjustButtonState();
				}
			});

			volumePanel.add(volumeButton);

			volumeSlider = new JSlider();
			volumeSlider.setPreferredSize(new Dimension(120,20));
			volumeSlider.setValue(defaultSound);
			volumeSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if(volumeSlider.getValue()<=maxsound)
						vlcPlayer.getVLCAudio().setVolume(volumeSlider.getValue());
					
				}
			});
			volumePanel.add(volumeSlider);
			fullButton = new JButton(); 
			fullButton.setOpaque(false);
			fullButton.setContentAreaFilled(false);
			fullButton.setIcon(new ImageIcon(this.getClass().getResource("/ICON-INF/fullscreen.gif")));
			fullButton.setEnabled(true);
			fullButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ActionListener action = (ActionListener)getThis().getClientProperty("fullscreen.action");
					action.actionPerformed(e);
				}
			});
			fullButton.getClientProperty("full");
			volumePanel.add(fullButton);
			
			add(volumePanel, BorderLayout.EAST);
		}

		public void adjustButtonState() {
			boolean mute = vlcPlayer.getVLCAudio().isMute();
			if (!mute) {
				volumeButton.setIcon(soundIcon);
				volumeSlider.setEnabled(true);
			} else {
				volumeButton.setIcon(muteIcon);
				volumeSlider.setEnabled(false);
			}			
		}

		public JButton getPlayButton() {
			return playButton;
		}

		public JButton getStopButton() {
			return stopButton;
		}

		public JSlider getSeekBarSlider() {
			return seekBarSlider;
		}

		public JLabel getTimeLabel() {
			return timeLabel;
		}

		public JButton getVolumeButton() {
			return volumeButton;
		}

		public JSlider getVolumeSlider() {
			return volumeSlider;
		}

		public boolean isAdjustingSeekBar() {
			return isAdjustingSeekBar;
		}

		public void setAdjustingSeekBar(boolean isAdjustingSeekBar) {
			this.isAdjustingSeekBar = isAdjustingSeekBar;
		}
		
		public int getPlayerStatus(){
			return playerStatus;
		}
	}
	
	public int getPlayerStatus(){
		return controllBar.getPlayerStatus();
	}
	
	public void addPlayList(String resourcePath){
		vlcPlayer.getVLCPlaylist().addItem(resourcePath);
	}
	
	public void startUpdateThread(){
		if(updateThread != null) {
	        return;
	      }
	      if(vlcPlayer.isNativePeerDisposed()) {
	        return;
	      }
	      updateThread = new Thread("NativeSwing - VLC Player control bar update") {
	        @Override
	        public void run() {
	          final Thread currentThread = this;
	          while(currentThread == updateThread) {
	            if(vlcPlayer.isNativePeerDisposed()) {
	              stopUpdateThread();
	              return;
	            }
	            try {
	              sleep(1000);
	            } catch(Exception e) {}
	            SwingUtilities.invokeLater(new Runnable() {
	              public void run() {
	                if(currentThread != updateThread) {
	                  return;
	                }
	                if(!vlcPlayer.isNativePeerValid()) {
	                  return;
	                }
	                updateControlBar();
	              }
	            });
	          }
	        }
	      };
	      updateThread.setDaemon(true);
	      updateThread.start();
	}
	
	private void stopUpdateThread() {
	      updateThread = null;
	}
	
	private void updateControlBar(){
		 VLCInput vlcInput = vlcPlayer.getVLCInput();
	     VLCMediaState state = vlcInput.getMediaState();
	     boolean isValid = state == VLCMediaState.OPENING || state == VLCMediaState.BUFFERING || state == VLCMediaState.PLAYING || state == VLCMediaState.PAUSED || state == VLCMediaState.STOPPING;
	     if(isValid) {
	    	 int time = vlcInput.getAbsolutePosition();
	         int length = vlcInput.getDuration();
	         isValid = time >= 0 && length > 0;
	         if(isValid) {
	        	 controllBar.setAdjustingSeekBar(true);
	        	 controllBar.getSeekBarSlider().setValue(Math.round(time * 10000f / length));
	        	 controllBar.setAdjustingSeekBar(false);
	        	 controllBar.getTimeLabel().setText(getTimeDisplay(time, length));
	        	 ActionListener action = (ActionListener)getThis().getClientProperty("player.status.action");
				 action.actionPerformed(null);
	         }
	      }
	      if(!isValid) {
	    	  controllBar.getStopButton().doClick();
	    	  controllBar.setAdjustingSeekBar(true);
	    	  controllBar.getSeekBarSlider().setValue(0);
	    	  controllBar.setAdjustingSeekBar(false);
	    	  controllBar.getTimeLabel().setText("00:00/00:00");
	    	  ActionListener action = (ActionListener)getThis().getClientProperty("player.status.action");
	    	  action.actionPerformed(null);
	      }
	}

	public void setMaxsound(int maxsound) {
		this.maxsound = maxsound;
	}
}
