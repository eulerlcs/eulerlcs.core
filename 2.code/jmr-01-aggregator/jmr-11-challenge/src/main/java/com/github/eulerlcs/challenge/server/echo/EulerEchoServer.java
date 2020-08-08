package com.github.eulerlcs.challenge.server.echo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;

import com.github.eulerlcs.challenge.server.core.EulerServer;

/**
 * point: CN=server name
 * 
 * <pre>
 * keytool -genkeypair -keystore euler03.ks -alias euler03 -storepass xxxxxx -keypass xxxxxx -dname "CN=sj-pc, OU=test Project, O=test\, Inc., L=Chuo-ku, ST=Tokyo, C=JP" -validity 730
 * keytool -export     -keystore euler03.ks -alias euler03 -storepass xxxxxx -file euler03.cer
 * keytool -import -file euler03.cer -alias euler03 -storepass changeit -keystore "%JAVA_HOME%\jre\lib\security\cacerts"
 * keytool -list  -v -storepass changeit -keystore "%JAVA_HOME%\jre\lib\security\cacerts"
 * keytool -delete -alias euler01 -storepass changeit -keystore "%JAVA_HOME%\jre\lib\security\cacerts"
 * </pre>
 */
public class EulerEchoServer extends EulerServer implements EchoServer {
	public void create(int port, InputStream keystore, String password) {
		HttpRequestHandler handler = new HttpRequestHandler() {
			@Override
			public void handle(HttpRequest request, HttpResponse response, HttpContext context)
					throws HttpException, IOException {
				handleAll(request, response, context);
			}
		};

		UriHttpRequestHandlerMapper handlerMapper = new UriHttpRequestHandlerMapper();
		handlerMapper.register("*", handler);

		create(port, handlerMapper, keystore, password);
	}
}
