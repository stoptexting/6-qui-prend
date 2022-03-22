package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import game.Player;

public class FileHandler {
	
	private FileHandler(){}
	
	/**
	* Get ArrayList with all the players
	* @param  File name
	*/
	public static ArrayList<Player> getPlayers(String filename) {
		ArrayList<Player> players = new ArrayList<>();
		
		try(Scanner in = new Scanner(new FileInputStream(filename))) {
			players = new ArrayList<>();
			
			while (in.hasNextLine()) {
				players.add(new Player(in.nextLine()));
			}
		} 
		
		catch (FileNotFoundException e) {
			Logger.logln("Filename must be config.txt and located here : " + System.getProperty("user.dir") + "\\config.txt");
		}
		
		return players;
		
	}
}
