package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import util.FileHandler;
import util.Logger;

public class Table {
	private static final int MAXCARDS = 104; // Default : 104
	private static final int MAXPLAYERS = 10;
	private static final int TODISTRIBUTE = 10; // Default : 10
	private int playerCount = 0;
	
	private Deck gameCards = null;
	private ArrayList<Player> players = null;
	private SortedMap<Integer, Serie> series = null;
	
	/* Constructor */
	public Table() {
		this.players = FileHandler.getPlayers("config.txt"); // get the players
		this.playerCount = players.size();
		assert(this.playerCount >= 2 && this.playerCount <= MAXPLAYERS);
		this.gameCards = GameMaster.createGame(MAXCARDS); // create the game deck
		this.series = GameMaster.initSeries(this.gameCards); // create the series
	}
	
	/**
	* Get players of the game
	* @return  Players of the game
	*/
	public List<Player> getPlayers() {
		return this.players;
	}
	
	/* Start the game */
	public void start() {
		GameMaster.distributeCards(gameCards, players, TODISTRIBUTE);
		Logger.logln(this.toString());
		this.play();
	}
	
	/* Play until no more cards in player's deck */
	public void play() {
		while (this.nextRound());
		GameMaster.sc.close();
	}
	
	/**
	* Play the next round
	* @return  boolean true if players still have cards
	*/
	public boolean nextRound() {
		if (this.getPlayers().get(0).getCards().isEmpty()) {
			Logger.logln(GameMaster.finalScore(this)); return false;
		}
			
		SortedMap<Integer, Move> moves = GameMaster.getMoves(this);
		
		GameMaster.placeCards(this, moves);
		GameMaster.showSeries(this);
		Logger.logln(this.printPenalties());
		return true;
	}
	
	/**
	* Print players who received a penalty
	* @return  Penalties at the end of each rounds
	*/
	private String printPenalties() {
		StringBuilder heads = new StringBuilder();
		SortedMap<Integer, String> penalties = new TreeMap<>();
		Integer lastRoundHeads = 0;
		for (Player p: this.getPlayers()) {
			lastRoundHeads = p.getLastRoundHeads();
			if (lastRoundHeads > 0)
				penalties.put(lastRoundHeads, p.getName());
		}
		
		for (Entry<Integer, String> i: penalties.entrySet())
			heads.append(i.getValue() + " a ramassé " + i.getKey() + (i.getKey() > 1 
					? " têtes " : " tête ") 
					+ "de boeufs"
					+ (i.getKey().equals(penalties.lastKey()) ? "" : System.lineSeparator()));

		if (penalties.size() == 0)
			return "Aucun joueur ne ramasse de tête de boeufs.";
		else
			return heads.toString();
	}
	
	/**
	* Get cards that have been placed
	* @param   Moves played by the game
	* @return  Placed cards to string
	*/
	public String placedCards(SortedMap<Integer, Move> moves) {
		StringBuilder sb = new StringBuilder("Les cartes ");
		for (Entry<Integer, Move> m: moves.entrySet()) {
			sb.append((m.getKey().equals(moves.lastKey()) ? 
				" et " : (m.getKey().equals(moves.firstKey()) ? "" : ", ")) 
				+ m.getValue().getCardValue() 
				+ " (" + m.getValue().getPlayerName() + ")" 
				+ (m.getKey().equals(moves.lastKey()) ? " ont été posées." : ""));
		}
		
		return sb.toString();
	}
	
	/**
	* Get the series of the table
	* @return  Series deck on the table
	*/
	public SortedMap<Integer, Serie> getSeries() {
		return this.series;
	}
	
	/**
	* Welcome message of the game
	* @return  Welcome message to string
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder("Les " + this.playerCount + " joueurs sont ");
		for (int i = 0; i < this.playerCount; ++i) {
			sb.append(this.players.get(i).getName() + (i == this.playerCount-1 ? 
					"." : (i == this.playerCount-2 ? " et " : ", ")));
		}
		sb.append(" Merci de jouer à 6 qui prend !");
		return sb.toString();
	}
}
