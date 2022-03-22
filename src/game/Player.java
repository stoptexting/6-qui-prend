package game;

import java.util.ArrayList;
import java.util.SortedMap;

public class Player {
	private String name; // Player name
	private Deck cards; // Player's cards
	private Penalties penalties; // Player's penalties
	
	/**
	* Constructor
	* @param  name of the player
	*/
	public Player(String name) {
		this.setName(name);
		this.cards = new Deck();
		this.penalties = new Penalties();
	}

	/**
	* Get the player's name
	* @return  player's name
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Set the player's name
	* @param  player's name
	*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	* Get the heads of the player
	* @return  Player's total heads
	*/
	public int getHeadCount() {
		return this.penalties.getTotalHeads();
	}
	
	/**
	* Get the last round heads of the player
	* @return  Player's round heads
	*/
	public int getLastRoundHeads() {
		return this.penalties.getRoundHeads();
	}

	/**
	* Add penalties to the player
	* @param  Penalties to add
	*/
	public void addPenalties(SortedMap<Integer, Card> s) {
		penalties.addPenalties(s);
	}
	
	/**
	* Get the cards of the players (keys)
	* @return  Cards of the player
	*/
	public ArrayList<Integer> getCards() {
		return this.cards.getKeys();
	}
	
	/**
	* Get the cards of the players (Deck)
	* @return  Cards of the player
	*/
	public Deck getDeck() {
		return this.cards;
	}
	
	/**
	* Add a card to the player
	* @param  Card to add
	*/
	public void addCard(Card c) {
		this.cards.addCard(c);
	}
	
	/**
	* Check if a player has a certain card or not
	* @return  true if has card, else false
	*/
	public boolean hasCard(Integer chosen) {
		return this.cards.getKeys().contains(chosen);
	}
	
	/**
	* Pop a card from the player's deck
	* @param   value of the card
	* @return  chosen card
	*/
	public Card pop(int i) {
		return this.cards.pop(i);
	}

}
