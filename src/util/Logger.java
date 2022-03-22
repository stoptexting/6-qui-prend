package util;

import java.io.PrintStream;

public class Logger {
	private static PrintStream outStream = System.out; // Default PrintStream
	
	private Logger() {}
	
	/**
	* Print message
	* @param  Message to print
	*/
	public static void log(String message) {
		outStream.print(message);
	}
	
	/**
	* Print message and break line
	* @param  Message to print
	*/
	public static void logln(String message) {
		outStream.println(message);
	}
	
	/**
	* Change the print stream
	* @param  PrintStream you want to print to
	*/
	public static void setPrintStream(PrintStream newPs) {
		outStream = newPs;
	}
}
