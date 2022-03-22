package game;

import java.util.Map.Entry;
import java.util.SortedMap;

public class Penalties extends Deck {
	private int totalHeads = 0; // total heads
	private int lastRound = 0; // last round heads
	
	/**
	* Add penalties
	* @param  Penalties to add
	*/
	public void addPenalties(SortedMap<Integer, Card> newPenalties) {
		this.getCards().clear(); // clear because new round
		this.getCards().putAll(newPenalties); // keep in memory for a round
		this.totalHeads += this.getDeckCards(newPenalties); // update total heads
		this.lastRound = this.getDeckCards(this.getCards()); // update last round heads
	}
	
	/**
	* Get the round heads
	* @return  Round's heads of the deck (new cards)
	*/
	public int getRoundHeads() {
		int tmp = this.lastRound; 
		this.lastRound = 0; // reset after the round
		return tmp;
	}
	
	/**
	* Get the total heads
	* @return  Get total heads of the deck
	*/
	public int getTotalHeads() {
		return this.totalHeads;
	}
	
	/**
	* Get the head's value of a deck
	* @param   Penalties deck to add
	* @return  Heads of the new deck
	*/
	public int getDeckCards(SortedMap<Integer, Card> newPenalties) {
		int total = 0;
		for (Entry<Integer, Card> e: newPenalties.entrySet())
			total += e.getValue().getHeads();
		return total;
	}
}
