package com.uiuc.dslogs.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;

/**
 * Implementation of Connector Service to connect different nodes
 * 
 * @author dhawalseth
 * 
 */
public class ConnectorService {

	private static final Integer TIMEOUT = 1000; // 1 sec
	private static final int SocketNumber = 5989;

	public void connect(String ipAddress, String cmd) {

		Socket socket = new Socket();
		InetSocketAddress endPoint = new InetSocketAddress(ipAddress,
				SocketNumber);
		try {
			// Inside Connect
			if (endPoint.isUnresolved()) {
				System.out.println("Unresolved InetSocketAddress: " + endPoint);
			} else {
				socket.connect(endPoint, TIMEOUT);
				Calendar cal = Calendar.getInstance();
				System.out.println("Start time: " + cal.getTimeInMillis());
				System.out.printf("Connection Success:    %s  \n", endPoint);
				// Writing in Buffer
				writeCmdInBuffer(cmd, socket);
				// Reading cmd Output
				receiveCmdOutput(socket);
			}
		} catch (IOException ioe) {
			System.out.printf("Failure:    %s message: %s - %s \n", endPoint,
					ioe.getClass().getSimpleName(), ioe.getMessage());
		} finally {
		}
	}

	private void writeCmdInBuffer(String cmd, Socket socket) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("Writing cmd in Buffer");
			bufferedWriter.write(cmd);
			bufferedWriter.write("\n");
			System.out.println("Command written in buffer");
			bufferedWriter.flush();
		} catch (IOException ioe) {
		}
	}

	private void receiveCmdOutput(Socket socket) {
		try {
			String line = "";
			System.out.println(Thread.currentThread().getName()
					+ ": Waiting for Command output: " + socket);
			PrintWriter writer = new PrintWriter("/tmp/output_"
					+ Thread.currentThread().getName() + ".txt", "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			System.out.println(Thread.currentThread().getName()
					+ ": Reading cmd output...");
			while ((line = bufferedReader.readLine()) != null) {
				writer.write(line + "\n");
			}
			Calendar cal = Calendar.getInstance();
			System.out.println("Read End Time: " + cal.getTimeInMillis());
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
