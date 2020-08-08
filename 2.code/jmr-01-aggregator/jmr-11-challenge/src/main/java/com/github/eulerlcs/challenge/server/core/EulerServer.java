package com.github.eulerlcs.challenge.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;

import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eulerlcs.challenge.server.ServerUtil;

import lombok.Getter;

public class EulerServer implements Server {
	private final static Logger log = LoggerFactory.getLogger(EulerServer.class);
	private final static String SELF_NAME = EulerServer.class.getSimpleName();

	private HttpServer server;
	@Getter
	private ServerType type;
	@Getter
	private String name;
	@Getter
	private int port;

	public void create(int port, UriHttpRequestHandlerMapper handlerMapper, InputStream keystore, String password) {
		this.port = port;

		if (keystore != null && password != null) {
			SSLContext sslContext = ServerUtil.getSSLContext(keystore, password);
			createServer(port, handlerMapper, sslContext);
		} else {
			createServer(port, handlerMapper, null);
		}
	}

	public void create(int port, UriHttpRequestHandlerMapper handlerMapper, SSLContext sslContext) {
		this.port = port;
		createServer(port, handlerMapper, sslContext);
	}

	@Override
	public void start() {
		try {
			server.start();
			log.info("started.");
		} catch (IOException e) {
			log.error("start error.", e);
		}
	}

	@Override
	public void shutdown() {
		if (server == null) {
			return;
		}

		server.shutdown(0, null);
		server = null;
		log.info("stopped.");
	}

	private void createServer(int port, UriHttpRequestHandlerMapper handlerMapper, SSLContext sslContext) {
		ServerBootstrap sb = ServerBootstrap.bootstrap().setListenerPort(port);

		try {
			String hostname = InetAddress.getLocalHost().getHostName();
			sb.setServerInfo(hostname);
			name = hostname;
		} catch (UnknownHostException e) {
			sb.setServerInfo(SELF_NAME);
			name = null;
		}

		sb.setHandlerMapper(handlerMapper);

		if (sslContext != null) {
			sb.setSslContext(sslContext);
			type = ServerType.HTTPS;
		} else {
			type = ServerType.HTTP;
		}

		server = sb.create();
	}
}
