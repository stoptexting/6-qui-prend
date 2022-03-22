package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import game.GameMaster;
import game.Deck;
import util.FileHandler;
import game.Player;
import util.Logger;

class GameMasterTest {
	private static final String CONFIG = "config.txt";

	void mixCards() {
		Deck d = GameMaster.createGame(104);
		Logger.logln(d.toString());
		GameMaster.mixDeck(d);
		Logger.logln(d.toString());
	}
	

	void getPlayers() {
		for (Player p: FileHandler.getPlayers(CONFIG)) {
			Logger.logln(p.getName());
		}
		assertTrue(FileHandler.getPlayers(CONFIG).size() == 4);
	}
	

	void distributeCards() {
		Deck d = GameMaster.createGame(104);
		GameMaster.mixDeck(d);
		Logger.logln("Game Deck : " + d.toString());
		
		ArrayList<Player> players = new ArrayList<>(FileHandler.getPlayers(CONFIG));
		GameMaster.distributeCards(d, players, 10);
		
		for (Player p: players) {
			Logger.logln(p.getName() + " " + p.getCards());
		}
	}
	

}
