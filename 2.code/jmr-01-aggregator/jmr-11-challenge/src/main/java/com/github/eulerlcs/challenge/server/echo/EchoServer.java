package com.github.eulerlcs.challenge.server.echo;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import com.github.eulerlcs.challenge.server.core.Server;

public interface EchoServer extends Server {
	default void handleAll(HttpRequest request, HttpResponse response, HttpContext context)
			throws HttpException, IOException {
		String body = "welcome! " + "at " + new Date() + " in " + this.getClass().getSimpleName();
		response.setStatusCode(HttpStatus.SC_OK);
		response.setEntity(new StringEntity(body));
	}
}
