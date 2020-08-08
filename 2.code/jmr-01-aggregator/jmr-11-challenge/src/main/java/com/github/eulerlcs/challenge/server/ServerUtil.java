package com.github.eulerlcs.challenge.server;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.util.Assert;

public interface ServerUtil {
	static SSLContext getSSLContext(InputStream keystore, String password) {
		Assert.notNull(keystore, "[Assertion failed] - keystore argument is required; it must not be null");
		Assert.notNull(password, "[Assertion failed] - password argument is required; it must not be null");

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
		}

		return sslContext;
	}

	static void ignoreHostnameVerification() {
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

	static void setTrustCert(InputStream keystore, String password) {
		// javax.net.ssl.SSLHandshakeException:
		// sun.security.validator.ValidatorException: PKIX path building failed:
		// sun.security.provider.certpath.SunCertPathBuilderException: unable to find
		// valid certification path to requested target
		SSLContext sslContext = getSSLContext(keystore, password);

		// 簡易HTTPSサーバのSSLコンテキストのSSLSocketFactoryをクライアントからの接続に使用することで、証明書エラーが発生しないようにする
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	}
}
