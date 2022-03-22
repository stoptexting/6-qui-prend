package game;

import java.util.Collections;

public class Serie extends Deck {
	
	/**
	* Get the last card of a deck
	* @return  Get last card value of a deck
	*/
	public int getLastCardValue() {
		assert(!this.getKeys().isEmpty());
		Collections.sort(this.getKeys());
		return this.getKeys().get(this.getKeys().size() - 1);
	}
	
	/**
	* Format and return the deck to string
	* @param   serie id to print
	* @return  Formated deck to string
	*/
	public String toString(int serieId) {
		StringBuilder sb = new StringBuilder();
		sb.append("- série n° " + serieId + " : ");
		for (int i = 0; i < this.size(); ++i) {
			Card c = this.getCards().get(this.getKeys().get(i));
			sb.append(c.toString());
			if (c.getHeads() > 1) 
				sb.append(" (" + c.getHeads() +")"); 
			sb.append(i == this.size() - 1 ? "" : ", ");
		}
		return sb.toString();		
	}
	
	

}
