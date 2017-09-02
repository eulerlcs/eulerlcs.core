package com.github.eulerlcs.challenge.server.echo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eulerlcs.challenge.server.core.EulerHttpsServer;

/**
 * <pre>
 * keytool -genkey -keystore euler01.ks -alias euler -storepass eulereuler
 * keytool -export -keystore euler01.ks -alias euler -storepass eulereuler -file euler01.cer
 * keytool -import -keystore euler01.ks -file euler01.cer
 * </pre>
 */
public class EulerEchoServer implements EchoServer {
	private final static Logger log = LoggerFactory.getLogger(EulerEchoServer.class);
	private final static String SELF_NAME = EulerEchoServer.class.getSimpleName();

	private EulerHttpsServer server;

	public EulerEchoServer(InputStream keystore, String password, int port) {
		HttpRequestHandler handler = new HttpRequestHandler() {
			@Override
			public void handle(HttpRequest request, HttpResponse response, HttpContext context)
					throws HttpException, IOException {
				String body = "welcome! " + "at " + new Date() + " in " + SELF_NAME;
				response.setStatusCode(HttpStatus.SC_OK);
				response.setEntity(new StringEntity(body));

				log.info(body);
			}
		};

		UriHttpRequestHandlerMapper handlerMapper = new UriHttpRequestHandlerMapper();
		handlerMapper.register("*", handler);
		server = new EulerHttpsServer(keystore, password, port, handlerMapper);
	}

	@Override
	public void start() {
		server.start();
	}

	@Override
	public void shutdown() {
		server.shutdown();
	}
}
