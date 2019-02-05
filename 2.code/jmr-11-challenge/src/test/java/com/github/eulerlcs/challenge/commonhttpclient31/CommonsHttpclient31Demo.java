package com.github.eulerlcs.challenge.commonhttpclient31;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;

public class CommonsHttpclient31Demo {
	private static String url = "http://www.apache.org/";
	private static String urlNg = "http://167.10.0.45/";

	@Test
	public void test() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.closeIdleConnections(20 * 4000);

		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		// params.setStaleCheckingEnabled(diamondConfigure.isConnectionStaleCheckingEnabled());
		// params.setMaxConnectionsPerHost(hostConfiguration,
		// diamondConfigure.getMaxHostConnections());
		// params.setMaxTotalConnections(diamondConfigure.getMaxTotalConnections());
		params.setConnectionTimeout(2 * 1000);

		params.setSoTimeout(1 * 1000);

		connectionManager.setParams(params);

		// Create an instance of HttpClient.
		HttpClient client = new HttpClient(connectionManager);

		DefaultHttpMethodRetryHandler retryhandler = new DefaultHttpMethodRetryHandler(0, false);
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		// Provide custom retry handler is necessary
		// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new
		// DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			System.out.println(new String(responseBody));

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
	}
}
