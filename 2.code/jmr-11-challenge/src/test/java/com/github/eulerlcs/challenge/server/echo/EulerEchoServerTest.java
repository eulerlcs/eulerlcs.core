package com.github.eulerlcs.challenge.server.echo;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.junit.Before;
import org.junit.Test;

import com.github.eulerlcs.challenge.server.core.EulerHttpsServer;

public class EulerEchoServerTest {
	private EulerEchoServer target;
	private final int port = 443;
	private final String keystore = "euler03.ks";
	private final String pwd = "eulereuler";

	private void ini() {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(keystore);

		target = new EulerEchoServer(in, pwd, port);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_access() throws Exception {
		ini();
		target.start();
		Thread.sleep(3 * 1000);

		if (1 == 2) {
			// javax.net.ssl.SSLHandshakeException: java.security.cert.CertificateException:
			// No subject alternative names present
			// URLのホスト名と証明書のサーバー名が不一致でもエラーにしない
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					session.getPeerHost();
					return true;
				}
			});
		}

		if (1 == 2) {
			// javax.net.ssl.SSLHandshakeException:
			// sun.security.validator.ValidatorException: PKIX path building failed:
			// sun.security.provider.certpath.SunCertPathBuilderException: unable to find
			// valid certification path to requested target
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(keystore);
			SSLContext sslContext = EulerHttpsServer.getSSLContext(in, pwd);

			// 簡易HTTPSサーバのSSLコンテキストのSSLSocketFactoryをクライアントからの接続に使用することで、証明書エラーが発生しないようにする
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		}

		URL url = new URL("https://sj-pc:" + port);
		// URL url = new URL("https://gaohaoyang.github.io/");
		// URL url = new URL("https://www.yahoo.com/");

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
