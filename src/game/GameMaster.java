package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import util.Console;
import util.Logger;


/**
* Placer Class
*/
class Placer {
	private SortedMap<Integer, Move> roundMoves = null; // get every moves
	private Table t = null; // Store the table the placer is working on
	
	/**
	* Constructor
	* @param   Table you are working on
	* @param   Every moves played by players
	*/
	public Placer(Table t, SortedMap<Integer, Move> moves) {
		this.roundMoves = moves;
		this.t = t;
	}
	
	/**
	* Rule 4 of the game (card value too low result in taking a deck to place it)
	* @param   Move the placer will play
	*/
	private void rule4(Move mv) {
		Player p = null;
		boolean firstInput = true;
		Scanner scanner = GameMaster.sc;
		
		Logger.logln(this.toString());
		Logger.logln("Pour poser la carte " + mv.getCardValue() + ", " +
					 mv.getPlayerName() + " doit choisir la série qu'il va ramasser.");
		GameMaster.showSeries(t);
		
		/* Get the deck you will chose */
		Integer chosen = -1;
		while (chosen >= 5 || chosen <= 0) {
			try {
				if (firstInput)
					Logger.log("Saisissez votre choix : "); firstInput = false;
				if (scanner.hasNextInt())
					chosen = scanner.nextInt();
				
				if (chosen >= 5 || chosen <= 0)
					throw new IllegalArgumentException("Invalid Card");
			}
			
			catch (Exception e) {
				scanner.nextLine();
				Logger.log("Ce n'est pas une série valide, saisissez votre choix : ");
			}
		}
		
		/* Get the Serie object */
		Serie serie = new Serie();
		int i = 1;
		for (Serie s: t.getSeries().values()) { if (i++ == chosen) { serie = s; break; } }
		
		p = mv.getPlayer();
		p.addPenalties(serie.getCards()); // add the serie to the penalties
		serie.flush(); // remove every cards of the serie
		serie.addCard(mv.getCard()); // add the card to placed to the serie you flushed
		GameMaster.updateKeys(this.t);
	}
	
	/**
	* Rule 3 : if you have to place a card and there is 5 cards already placed, take the serie
	* @param   Move the player played
	* @param   Serie you will have to take
	*/
	private void rule3(Move mv, int serieId) {
		Player p = mv.getPlayer();
		p.addPenalties(t.getSeries().get(serieId).getCards()); // add the serie to the penalties
		t.getSeries().get(serieId).flush(); // flush the serie
	}
	
	/**
	* Play the move
	* @param   Move the player played
	*/
	private void play(Move mv) {
		SortedMap<Integer, Serie> s = t.getSeries();
		Integer serieId = null;
		for (Entry<Integer, Serie> i: s.entrySet()) {
			if (mv.getCardValue() - i.getValue().getLastCardValue() > 0 && serieId == null) {
				serieId = i.getKey(); // initialize the first value
			}
			
			if (serieId != null && mv.getCardValue() - i.getValue().getLastCardValue() > 0 
					&& mv.getCardValue() - i.getValue().getLastCardValue() 
					< mv.getCardValue() - s.get(serieId).getLastCardValue()) {
					serieId = i.getKey(); // if the difference is lower than the past one, replace
			}
		}
		
		/* If not serie found (value too low) */
		if (serieId == null) { rule4(mv); return; }
		
		/* If not serie found (value too low) */
		else if ((t.getSeries().get(serieId).size() + 1) > 5) {
			rule3(mv, serieId);
			this.t.getSeries().get(serieId).addCard(mv.getCard()); return;
		}
		
		this.t.getSeries().get(serieId).addCard(mv.getCard()); // the card to the right serie
		
	}	
	
	/* Place every moves */
	public void placeAll() {
		Move mv = null;
		for (Entry<Integer, Move> i: roundMoves.entrySet()) {
			mv = roundMoves.get(i.getKey());
			this.play(mv);
		}
		Logger.logln(t.placedCards(roundMoves));
	}
	
	/* Print cards that will be placed */
	public String toString() {
		StringBuilder sb = new StringBuilder("Les cartes ");
		for (Entry<Integer, Move> i: roundMoves.entrySet()) {
			sb.append((i.getKey().equals(roundMoves.firstKey()) ? 
					"" : (i.getKey().equals(roundMoves.lastKey()) ? " et " : ", ")));
			sb.append(i.getValue().getCardValue() + " (" + i.getValue().getPlayerName() + ")");
		}
		sb.append(" vont être posées.");
		return sb.toString();
	}
} 

/**
* GameMaster class
*/
public class GameMaster {
	private static final int SERIES = 4;
	private GameMaster() {} // every methods are statics, make object not buildable
	
	/**
	* Mix a deck
	* @param   Deck to mix
	* @return  The mixed deck (keys)
	*/
	public static ArrayList<Integer> mixDeck(Deck d) {
		ArrayList<Integer> keys = d.getKeys();
		Collections.shuffle(keys);
		return keys;
	}
	
	/**
	* Create the game deck
	* @param   Deck of the game
	* @return  Deck used for the entire game
	*/
	public static Deck createGame(int maxCards) {
		Deck gameDeck = new Deck();
		for (int i = 1; i <= maxCards; ++i) {
			gameDeck.addCard(new Card(i));
		}
		GameMaster.mixDeck(gameDeck);
		return gameDeck;
	}
	
	/**
	* Distribute cards to players
	* @param   Game deck (where all cards are in)
	* @param   Players of the game
	* @param   How many cards to distribute
	*/
	public static void distributeCards(Deck d, List<Player> lp, int toDistribute) {
		ArrayList<Integer> al = GameMaster.mixDeck(d);
		for (Player p: lp) {
			for (int i = 0; i < toDistribute; ++i) {
				p.addCard(d.pop(al.get(i)));
			}
		}
	}
	
	/**
	* Pick a card in a deck
	* @param   Deck you will pick in
	* @return  Pick the first card (top of the deck) 
	*/
	private static Card pickCard(Deck d) {
		ArrayList<Integer> al = d.getKeys();
		Collections.shuffle(al);
		return d.pop(al.get(0));
	}
	
	/**
	* Initialize the series
	* @param   Deck of the game (deck on the table)
	* @return  Series of the game
	*/
	public static SortedMap<Integer, Serie> initSeries(Deck tableDeck) {
		SortedMap<Integer, Serie> series = new TreeMap<>();
		
		for (int i = 0; i < SERIES; ++i) {
			Serie s = new Serie();
			Card c = GameMaster.pickCard(tableDeck);
			s.addCard(c);
			series.put(c.getValue(), s);
		}
		return series;
	}
	
	/**
	* Update series keys
	* @param   Table you are working on
	*/
	public static void updateKeys(Table t) {
		for (Entry<Integer, Serie> s: t.getSeries().entrySet()) {
			s.getValue().updateKeys();
		}
	}
	
	/**
	* Show series of the table
	* @param   Table you are working on
	*/
	public static void showSeries(Table t) {
		SortedMap<Integer, Serie> series = t.getSeries();
		int i = 1;
		for (Serie s: series.values()) {
			Logger.logln(s.toString(i++));
		}
	}
	
	/* Final scanner to avoid bug related to sc.close() every function call */
	public static final Scanner sc = new Scanner(System.in);
	
	/**
	* Get a card from the player
	* @param   Table you are working on
	* @param   Player that is choosing a card
	* @return  Card chosen by the player
	*/
	private static Card getCard(Table t, Player p) {
		Integer chosen = null;
		boolean firstInput = true;
		GameMaster.showSeries(t);
		Logger.logln("- Vos cartes : " + p.getDeck().toString());
		
		do {
			
			try {
				if (firstInput)
					Logger.log("Saisissez votre choix : "); firstInput = false; // avoid multiple print
					
				if (sc.hasNextInt()) { chosen = sc.nextInt(); }
				
				if (!p.hasCard(chosen)) { throw new IllegalArgumentException("Invalid Card"); } // if invalid card
			}
			
			catch (Exception e) {
				sc.nextLine(); // avoid loop due to type mismatch
				Logger.log("Vous n'avez pas cette carte, saisissez votre choix : ");
			}
		} while((!p.hasCard(chosen)));
		
		return p.pop(chosen);
	}
	
	/**
	* Get moves of the round
	* @param   Table you are working on
	* @return  Get moves from every players
	*/
	public static SortedMap<Integer, Move> getMoves(Table t) {
		SortedMap<Integer, Move> moves = new TreeMap<>(); // automatic sort
		Card c = null;
		for (Player p: t.getPlayers()) {
			Logger.logln("A " + p.getName() + " de jouer.");
			Console.pause();
			c = GameMaster.getCard(t, p); // get a card
			Move move = new Move(p, c);
			moves.put(c.getValue(), move);
			Console.clearScreen();
		}
		return moves;
	}
	
	/**
	* Moves to play (place cards)
	* @param   Table you are working on
	* @param   Moves to play
	*/
	public static void placeCards(Table t, SortedMap<Integer, Move> moves) {
		Placer p = new Placer(t, moves);
		p.placeAll();
	}
	
	/**
	* Sort the final score
	* @param   Leaderboard of the game
	*/
	private static void sortScore(ArrayList<Player> leaderboard) {
		leaderboard.sort((player1, player2) -> Integer.compare(player1.getHeadCount(), player2.getHeadCount()));
	}
	
	/**
	* Sort the leader board names
	* @param   Leaderboard of the game
	*/
	private static void sortNames(ArrayList<Player> leaderboard) {
		leaderboard.sort((player1, player2) -> player1.getName().compareTo(player2.getName()));
	}
	
	/**
	* Get the final score to string
	* @param   Table you are working on
	* @return  Final score to string
	*/
	public static String finalScore(Table t) {
		
		ArrayList<Player> leaderboard = new ArrayList<>(t.getPlayers());
		GameMaster.sortNames(leaderboard);
		GameMaster.sortScore(leaderboard);
		StringBuilder sb = new StringBuilder("** Score final" + System.lineSeparator());
		
		for (Player p: leaderboard) {
			sb.append(p.getName() + " a ramassé " 
					+ p.getHeadCount() + (p.getHeadCount() > 1 ? " têtes " : " tête ") + "de boeufs"
					+ (p.equals(leaderboard.get(leaderboard.size()-1)) ? "" : System.lineSeparator()));
		}
			
		return sb.toString();
	}
}
