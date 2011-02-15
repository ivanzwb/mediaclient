package com.whyang.test;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FtpTest {

	/**
	 * @param args
	 * @throws FTPException
	 * @throws FTPIllegalReplyException
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws FTPListParseException
	 * @throws FTPAbortedException
	 * @throws FTPDataTransferException
	 */
	public static void main(String[] args) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException,
			FTPDataTransferException, FTPAbortedException,
			FTPListParseException {
		// TODO Auto-generated method stub
		FTPClient client = new FTPClient();

		client.connect("127.0.0.1", 21);
		client.login("ubuntu", "ubuntu");

		String dir = client.currentDirectory();

		client.changeDirectory("/tools/test");
		client.changeDirectoryUp();

		client.rename("oldname", "newname");

		client.deleteFile("file.txt");
		client.deleteDirectory("directory");

		client.createDirectory("newfolder");

		FTPFile[] list = client.list();

		FTPFile[] specList = client.list("*.jpg");

		Date md = client.modifiedDate("filename.ext");

		client.download("remoteFile.txt", new File("localFile.txt"));

		public class MyTransferListener implements FTPDataTransferListener {

			public void aborted() {
				// TODO Auto-generated method stub

			}

			public void completed() {
				// TODO Auto-generated method stub

			}

			public void failed() {
				// TODO Auto-generated method stub

			}

			public void started() {
				// TODO Auto-generated method stub

			}

			public void transferred(int arg0) {
				// TODO Auto-generated method stub

			}

		}
		
		
		client.download("remoteFile.ext", new File("localFile.ext"), new MyTransferListener());
		client.upload(new java.io.File("localFile.ext"), new MyTransferListener());
		
		client.setType(FTPClient.TYPE_TEXTUAL);
		
		client.setType(FTPClient.TYPE_BINARY);
		
		client.setType(FTPClient.TYPE_AUTO);
		
		

	}
	



}
