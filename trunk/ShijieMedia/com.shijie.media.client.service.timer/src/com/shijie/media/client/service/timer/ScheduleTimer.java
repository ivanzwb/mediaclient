package com.shijie.media.client.service.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.service.IServiceManager;
import com.shijie.media.client.api.service.TimerService;
import com.shijie.media.client.entity.Config;


public class ScheduleTimer implements TimerService{
	private Logger logger = LoggerFactory.getLogger(ScheduleTimer.class);
	private Timer timer = new Timer(true);
	
	public ScheduleTimer(){
		
	}
	
	public void schedule(TimerTask task,long delay){
		timer.schedule(task, delay);
	}
	
	public void schedule(TimerTask task,Date time){
		timer.schedule(task, time);
	}

	public void schedule(TimerTask task,
            long delay,
            long period){
		timer.schedule(task, delay, period);
	}
	
	public void schedule(TimerTask task,
            Date firstTime,
            long period){
		timer.schedule(task, firstTime, period);
	}

	public void scheduleAtFixedRate(TimerTask task,
            long delay,
            long period){
		timer.scheduleAtFixedRate(task, delay, period);
	}
	
	public void scheduleAtFixedRate(TimerTask task,
            Date firstTime,
            long period){
		timer.scheduleAtFixedRate(task, firstTime, period);
		
	}

	@Override
	public String getServiceID() {
		return "TimerService";
	}

	@Override
	public void setServiceManager(IServiceManager serviceManager) {
		;
	}

	@Override
	public void cancel(TimerTask task) {
		task.cancel();
	}

	@Override
	public TimerTask getTask(String id) {
	
		return null;
	}

	@Override
	public void clear() {
		timer.purge();
		
	}
	
	@Override
	public void init(Config config) {
		logger.info("init Schedule Timer...");
	}

	@Override
	public void start() {
		logger.info("start Schedule Timer...");
	}

	@Override
	public void stop() {
		
	}

}
