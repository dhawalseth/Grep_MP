package com.uiuc.dslogs.Client;

/**
 * Implementation of ThreadClass for for spawning individual connector service to different nodes
 * 
 * @author dhawalseth
 * 
 */
public class ThreadClass extends Thread {

	private String ip;
	private String inputPattern;


	public ThreadClass(String tName, String pattern) {
		ip = tName;
		inputPattern = pattern;
		this.setName(ip);
	}

	public void run() {
		System.out
				.println("Inside thread: " + Thread.currentThread().getName());
		ConnectorService connectorService = new ConnectorService();
		connectorService.connect(ip, inputPattern);
	}
}
