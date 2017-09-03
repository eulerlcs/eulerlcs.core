package com.github.eulerlcs.challenge.server.echo;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EulerEchoServerTest {
	private EulerEchoServer server;

	@Before
	public void setUp() throws Exception {
		InputStream in = null;
		in = Thread.currentThread().getContextClassLoader().getResourceAsStream("euler03.ks");

		server = new EulerEchoServer() {
			@Override
			public void handleAll(HttpRequest request, HttpResponse response, HttpContext context)
					throws HttpException, IOException {
				String body = "welcome! " + "at " + new Date() + " in test";
				response.setStatusCode(HttpStatus.SC_OK);
				response.setEntity(new StringEntity(body));
			}
		};

		server.create(443, in, "password");
		server.start();
		Thread.sleep(3 * 1000);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	@Test
	public void test_access() throws Exception {
		URL url = new URL(server.getType() + "://" + server.getName() + ":" + server.getPort());

		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String st;
			while ((st = br.readLine()) != null) {
				System.out.println(st);
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}
