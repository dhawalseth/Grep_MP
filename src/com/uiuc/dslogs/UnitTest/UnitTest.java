package com.uiuc.dslogs.UnitTest;

import com.uiuc.dslogs.Client.*;
import java.io.*;
import java.util.Scanner;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Implementation of Unit Test
 * 
 * @author dhawalseth
 * 
 */
@SuppressWarnings("deprecation")
public class UnitTest {

	private static final String IpAddress = "192.17.11.3"; // Change Server IP
															// accordingly

	@Test
	public void check() {

		try {
			System.out.println("Unit Test Begins");
			// Generate Random Log file
			UnitTestLogGenerator.generateRandomLogs();
			// Generate Cmd String
			String cmd = ClientClass.generateCommand(IpAddress, "seth", "v",
					true);
			// Run Local Grep;
			localGrep(cmd);
			// Connecting to Server
			ConnectorService connectorService = new ConnectorService();
			connectorService.connect(IpAddress, cmd);
			InputStream FirstFileStream = new FileInputStream(
					"/tmp/localoutput_unitTest.txt");
			InputStream SecondFileStream = new FileInputStream(
					"/tmp/output_main.txt");
			System.out.println("Comparing the two outputs...");
			boolean result = fileComparison(FirstFileStream, SecondFileStream);
			Assert.assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void localGrep(String input) throws IOException,
			FileNotFoundException, UnsupportedEncodingException {
		String line = "";
		System.out.println("Localhost grep cmd: " + input);
		Process process = java.lang.Runtime.getRuntime().exec(input);
		PrintWriter writer = new PrintWriter("/tmp/localoutput_unitTest.txt",
				"UTF-8");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		System.out.println("Reading local grep output...");
		while ((line = br.readLine()) != null) {
			writer.write(line + "\n");
		}
		writer.close();
	}

	public static boolean fileComparison(InputStream is1, InputStream is2)
			throws IOException {
		Scanner sc1 = new Scanner(is1);
		Scanner sc2 = new Scanner(is2);
		while (sc1.hasNext() && sc2.hasNext()) {
			String str1 = sc1.next();
			String str2 = sc2.next();
			if (!str1.equals(str2)) {
				System.out.println(str1 + " != " + str2);
				return false;
			}
		}
		while (sc1.hasNext()) {
			System.out.println(sc1.next() + " != EOF");
			return false;
		}
		while (sc2.hasNext()) {
			System.out.println("EOF != " + sc2.next());
			return false;
		}
		sc1.close();
		sc2.close();
		System.out.println("Test Passed");
		return true;
	}
}
