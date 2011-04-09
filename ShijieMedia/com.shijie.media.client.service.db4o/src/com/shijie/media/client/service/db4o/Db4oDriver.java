package com.shijie.media.client.service.db4o;

import org.osgi.framework.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.cs.*;
import com.db4o.cs.config.*;
import com.db4o.cs.internal.config.*;
import com.db4o.ext.*;
import com.db4o.reflect.jdk.*;

public class Db4oDriver{

	private final Bundle _bundle;

	public Db4oDriver(Bundle bundle) {
		_bundle = bundle;
	}

	public Configuration newConfiguration() {
		@SuppressWarnings("deprecation")
		Configuration config = Db4o.newConfiguration();
		configureReflector(config);
		return config;
	}

	@SuppressWarnings("deprecation")
	public ObjectContainer openClient(String hostName, int port, String user, String password) throws Db4oException {
		return openClient(Db4o.cloneConfiguration(), hostName, port, user, password);
	}

	public ObjectContainer openClient(Configuration config, String hostName, int port, String user, String password) throws Db4oException {
		return Db4oClientServer.openClient(asClientConfiguration(config(config)), hostName, port, user, password);
	}

	@SuppressWarnings("deprecation")
	public ObjectContainer openFile(String databaseFileName) throws Db4oException {
		return openFile(Db4o.cloneConfiguration(), databaseFileName);
	}

	@SuppressWarnings("deprecation")
	public ObjectContainer openFile(Configuration config, String databaseFileName) throws Db4oException {
		return Db4o.openFile(config(config), databaseFileName);
	}

	@SuppressWarnings("deprecation")
	public ObjectServer openServer(String databaseFileName, int port) throws Db4oException {
		return openServer(Db4o.cloneConfiguration(), databaseFileName, port);
	}

	public ObjectServer openServer(Configuration config, String databaseFileName, int port) throws Db4oException {
		return Db4oClientServer.openServer(asServerConfiguration(config(config)), databaseFileName, port);
	}

	@SuppressWarnings("deprecation")
	private Configuration config(Configuration config) {
		if (config == null) {
			config = Db4o.newConfiguration();
		}
		configureReflector(config);
		return config;
	}

	private void configureReflector(Configuration config) {
		config.reflectWith(new JdkReflector(new OSGiLoader(_bundle, new ClassLoaderJdkLoader(getClass().getClassLoader()))));
	}
	
	private ServerConfiguration asServerConfiguration(Configuration config) {
		return Db4oClientServerLegacyConfigurationBridge.asServerConfiguration(config);
	}
	
	private ClientConfiguration asClientConfiguration(Configuration config) {
		return Db4oClientServerLegacyConfigurationBridge.asClientConfiguration(config);
	}

}
