package com.uiuc.dslogs.UnitTest;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Implementation of Log File Generator
 * 
 * @author dhawalseth
 * 
 */

public class UnitTestLogGenerator {
	public static void main(String[] args) {
		generateRandomLogs();
	}

	public static void generateRandomLogs() {
		try {
			Random randomGenerator = new Random();
			PrintWriter writer = new PrintWriter("/tmp/machine.log", "UTF-8");
			for (long i = 0; i < 9999; i++) {

				if (i % 3 == 0)
					writer.println(i + ":dhawal"); // Frequent Pattern
				else if (i % 1000 == 0)
					writer.println(i + ":seth"); // Rare Pattern
				else {
					// Generate random words:number
					writer.println(generateRandomWords(10)+":"+randomGenerator.nextInt(1000));
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String generateRandomWords(int wordLength) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(wordLength);
		for (int i = 0; i < wordLength; i++) {
			char tmp = (char) ('a' + r.nextInt('z' - 'a')); // Generate a letter
															// between a and z
			sb.append(tmp); // Add it to the String
		}
		return sb.toString();
	}
}
