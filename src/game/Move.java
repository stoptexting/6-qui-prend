package game;

public class Move {
	private Player player = null; // Player who made the move
	private Card card = null; // Chosen Card for the move
	private int cardValue = 0; // Card value
	
	/* Constructor */
	public Move(Player p, Card c) {
		this.player = p;
		this.card = c;
		this.cardValue = c.getValue();
	}
	
	/**
	* Get the player
	* @return  Move Player
	*/
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	* Get the chosen Card
	* @return  Chosen Card
	*/
	public Card getCard() {
		return this.card;
	}
	
	/**
	* Get the cards value
	* @return  Value of the card
	*/
	public int getCardValue() {
		return this.cardValue;
	}
	
	/**
	* Get the player's name
	* @return  Player's name to string
	*/
	public String getPlayerName() {
		return this.player.getName();
	}
}
