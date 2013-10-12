package com.uiuc.dslogs.Client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.uiuc.dslogs.UnitTest.UnitTestLogGenerator;

/**
 * Implementation of client class for pattern input and generating Grep command
 * 
 * @author dhawalseth
 * 
 */

public class ClientClass {

	private static final String IpAddressList = "/tmp/IpForGrep.txt";
	static String pattern = null;

	public static void main(String[] args) {

		try {
			System.out.println("Welcome to Log-querying program");
			BufferedReader br;
			try {
				System.out.println("Enter pattern to be searched");
				// Scanner scanner = new Scanner(System.in);
				// String inputPattern = scanner.next();

				br = new BufferedReader(new InputStreamReader(System.in));
				String inputPattern = br.readLine();
				System.out.println("Is the input Key or Value? Enter k or v");
				Scanner scanner = new Scanner(System.in);
				String isValue = scanner.next();
				scanner.close();
				System.out.println("Starting Thread creation");
				br = new BufferedReader(new FileReader(IpAddressList));
				String ipString = "";
				while ((ipString = br.readLine()) != null) {
					new ThreadClass(ipString, generateCommand(ipString,
							inputPattern, isValue, false)).start();
				}
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String generateCommand(String ipString, String inputPattern,
			String isValue, Boolean isTest) {
		String cmdString;
		if (isTest) {
			// Only for UnitTest
			UnitTestLogGenerator.generateRandomLogs();
		}
		// Log File path
		String fileName = "/tmp/machine." + ipString + ".log";
		// Cases for Key and Value search
		if (isValue.equalsIgnoreCase("v")) {
			cmdString = "grep \":.*" + inputPattern + ".*\" " + fileName;
		} else {
			cmdString = "grep \"" + inputPattern + ".*:\" " + fileName;
		}
		return cmdString;
	}
}
