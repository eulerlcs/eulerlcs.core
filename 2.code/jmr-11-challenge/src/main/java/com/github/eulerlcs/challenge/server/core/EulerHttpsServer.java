package com.github.eulerlcs.challenge.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EulerHttpsServer implements Server {
	private final static Logger log = LoggerFactory.getLogger(EulerHttpsServer.class);
	private final static String SELF_NAME = EulerHttpsServer.class.getSimpleName();

	private HttpServer server;
	private SSLContext sslContext;
	private int port;
	private UriHttpRequestHandlerMapper handlerMapper;

	public EulerHttpsServer(InputStream keystore, String password, int port,
			UriHttpRequestHandlerMapper handlerMapper) {
		this.sslContext = getSSLContext(keystore, password);
		this.port = port;
		this.handlerMapper = handlerMapper;
	}

	public EulerHttpsServer(SSLContext sslContext, int port, UriHttpRequestHandlerMapper handlerMapper) {
		this.sslContext = sslContext;
		this.port = port;
		this.handlerMapper = handlerMapper;
	}

	@Override
	public void start() {
		if (server != null) {
			return;
		}

		this.server = getServer();

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

	public static SSLContext getSSLContext(InputStream keystore, String password) {
		SSLContext sslContext = null;

		try {
			sslContext = SSLContext.getInstance("TLS");

			// キーストア取得
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(keystore, password.toCharArray());

			// キーマネージャのファクトリを初期化
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, password.toCharArray());

			// 証明書マネージャのファクトリを初期化
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);

			// ファクトリからキーストアのキーと証明書を取得し、SSLコンテキストを初期化
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		} catch (Exception e) {
			sslContext = null;
			log.error("getSSLContext", e);
		}

		return sslContext;
	}

	private HttpServer getServer() {
		ServerBootstrap sb = ServerBootstrap.bootstrap().setListenerPort(port).setServerInfo(SELF_NAME)
				.setHandlerMapper(handlerMapper);

		if (sslContext != null) {
			sb.setSslContext(sslContext);
		}

		HttpServer srv = sb.create();

		return srv;
	}
}
