package com.shijie.media.client.service.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shijie.media.client.api.service.DBService;
import com.shijie.media.client.api.service.DownloadService;
import com.shijie.media.client.api.service.IServiceManager;
import com.shijie.media.client.entity.Config;
import com.shijie.media.client.entity.DownloadTask;

public class FTPDownload implements DownloadService {

	private Logger logger = LoggerFactory.getLogger(FTPDownload.class);
	private final static int RUNNING = 0;
	private final static int SUSPEND = 1;
	private final static int STOPPED = 2;

	private List<DownloadTask> queue = new ArrayList<DownloadTask>();
	private FTPClient client = new FTPClient();
	private int status;
	private Thread t = new FTPThread(true);
	
	private IServiceManager serviceManager;
	private Config config;

	public FTPDownload() {
		
	}

	public boolean download(DownloadTask task) {
		try {
			File remote = new File(task.getRemoteURL());
			OutputStream output = new FileOutputStream(task.getLocalURL());
			InputStream input = client.retrieveFileStream(task.getRemoteURL());

			long downloadedSize = 0;
			byte bts[] = new byte[10240];
			while (status == RUNNING && downloadedSize < remote.length()) {
				downloadedSize = downloadedSize + input.read(bts);
				output.write(bts);
				output.flush();
			}
			input.close();
			output.close();
			
			if(downloadedSize<remote.length())
				return false;
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void init(Config config) {
		logger.info("init FTP download service");
		this.config = config;
		this.status = STOPPED;
	}

	@Override
	public void start() {
		logger.info("start FTP download service");
		this.status = RUNNING;
		t.start();
	}

	@Override
	public void stop() {
		status = STOPPED;
	}

	@Override
	public String getServiceID() {
		return "DownloadService";
	}

	@Override
	public void setServiceManager(IServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	@Override
	public void add(DownloadTask task) {
		queue.add(task);
	}

	@Override
	public void cancel(DownloadTask task) {
		queue.remove(task);

	}

	@Override
	public List<DownloadTask> getTasks() {
		return queue;
	}

	@Override
	public void cancel() {
		queue.clear();
	}

	@Override
	public void suspend() {
		status = SUSPEND;
	}

	@Override
	public void resume() {
		status = RUNNING;
	}

	class FTPThread extends Thread {

		public FTPThread(boolean b) {
			this.setDaemon(b);
		}

		public void run() {
			while (status != STOPPED) {
				
				if (status == SUSPEND) {
					try {
						client.disconnect();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
					try {
						wait();
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				}
				
				while(queue.size()==0){
					try {
						client.disconnect();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				}
				
				if(!client.isConnected()){
					try {
						client.connect((String)config.getProps().get("host"), (Integer)config.getProps().get("port"));
						client.login((String)config.getProps().get("username"), (String)config.getProps().get("password"));
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					
				}

				if(download(queue.get(0))){
						DownloadTask task = queue.remove(0);
						task.setStatus(DownloadTask.COMPLETED);
						updateOrSave(task);
				}
			}
		}
	}
	
	private DBService getDBService(){
		return (DBService) serviceManager.getService("DBService");
	}
	
	private void updateOrSave(DownloadTask task){
		DBService dbService = getDBService();
		try{
			dbService.openConnection();
			dbService.store(task);
		}finally{
			dbService.closeConnection();
		}
	}

}
