package com.uiuc.dslogs.Server;

/**
 * Implementation of Listener Server
 * 
 * @author dhawalseth
 * 
 */

public class ServerClass {

	public static void main(String[] args) {
		System.out.println("Starting Server.");
		ServerListener commandListener = new ServerListener();
		commandListener.startService();
	}
}
