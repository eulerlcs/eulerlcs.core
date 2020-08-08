package com.github.eulerlcs.challenge.server.core;

public interface Server {
	enum ServerType {
		HTTP, HTTPS
	}

	ServerType getType();

	int getPort();

	String getName();

	void start();

	void shutdown();
}
