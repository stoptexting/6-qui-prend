package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class Deck {
	private SortedMap<Integer, Card> cards; // Cards of the deck
	private ArrayList<Integer> keys; // Values of the cards in the deck
	
	/* Constructor */
	public Deck() {
		this.cards = new TreeMap<>();
		this.keys = new ArrayList<>();
	}
	
	/**
	* Add a card to the deck
	* @param  Card you want to add
	*/
	public void addCard(Card c) {
		cards.put(c.getValue(), c);
		keys.add(c.getValue());
	}
	
	/**
	* Get the cards of the players (keys)
	* @return  Cards
	*/
	public SortedMap<Integer, Card> getCards() {
		return this.cards;
	}
	
	/**
	* Get how many cards the deck has
	* @return  Deck cards number
	*/
	public int size() {
		return cards.size();
	}
	
	/**
	* Pop a card in the deck
	* @return  Cards of the player
	*/
	public Card pop(Integer index) {
		Card c = this.cards.get(index);
		this.cards.remove(index); // remove the card from the SortedMap
		this.keys.remove(this.keys.indexOf(index)); // remove the card from the keys
		return c; 
	}
	
	/**
	* Get the cards of a deck (keys)
	* @return  Keys of the deck
	*/
	public ArrayList<Integer> getKeys() {
		Collections.sort(this.keys);
		return this.keys;
	}
	
	/* Update the keys */
	public void updateKeys() {
		SortedMap<Integer, Card> series = new TreeMap<>(this.getCards()); // copy the current
		this.cards = new TreeMap<>(); 
		this.keys = new ArrayList<>();
		
		for (Entry<Integer, Card> i : series.entrySet()) {
			this.cards.put(i.getKey(), i.getValue());
			this.keys.add(i.getKey());
		}
	}
	
	/* Flush a deck (remove all cards) */
	public void flush() {
		this.cards.clear();
		this.keys.clear();
	}
	
	/**
	* Format and return a deck to a string
	* @return  Deck formated as a string
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.size(); ++i) {
			Card c = this.cards.get(this.getKeys().get(i));
			sb.append(c.toString());
			if (c.getHeads() > 1) 
				sb.append(" (" + c.getHeads() +")"); 
			sb.append(i == this.size() - 1 ? "" : ", ");
		}
		return sb.toString();		
	}
}
