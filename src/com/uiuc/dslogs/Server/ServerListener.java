package com.uiuc.dslogs.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Service Listener to execute Grep Command
 * 
 * @author dhawalseth
 * 
 */
public class ServerListener {
	private static final int SocketNumber = 5989;

	public void startService() {
		new Thread(new Runnable() {
			public void run() {
				startServer();
			}
		}).start();
	}

	public void startServer() {
		Socket server = new Socket();
		try {
			ServerSocket serverSocket = new ServerSocket(SocketNumber);
			System.out.println("Listening on port: "
					+ serverSocket.getLocalPort());

			while (true) {
				server = serverSocket.accept();
				System.out.println("Waiting for Cmd on port: " + SocketNumber);
				processCmd(server);
				server.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (server != null)
				try {
					server.close();
				} catch (IOException ioe) {
				}
		}
	}

	public void processCmd(Socket server) {
		try {
			String line = "", commandString = "";
			// Reading Cmd from Client
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(server.getInputStream()));

			commandString = bufferedReader.readLine();
			System.out
					.println("Command Received from Client: " + commandString);
			// Executing Cmd
			List<String> commands = new ArrayList<String>();
			commands.add("/bin/sh");
			commands.add("-c");
			commands.add(commandString);
			ProcessBuilder pb = new ProcessBuilder(commands);
			Process process = pb.start();
			// Process process = java.lang.Runtime.getRuntime()
			// .exec(commandString);

			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(server.getOutputStream()));

			System.out.println("Bufferring Output...");
			while ((line = bufferedReader.readLine()) != null) {
				bufferedWriter.write(line + "\n");
				bufferedWriter.flush();
			}

			System.out.println("Completed");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
