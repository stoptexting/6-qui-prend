package game;

public class Card {
	private int value;
	private int heads = 1; // Default Card Head value
	
	
	/**
	* Card Constructor
	* @param  value of the card
	*/
	public Card(int value) {
		this.setValue(value);
		this.setHeads(value);
	}
	
	/**
	* Get Card's head value
	* @return  heads of the card
	*/
	public int getHeads() {
		return heads;
	}
	
	/**
	* Calculate the Card's head value
	* @param  value of the card
	*/
	private void setHeads(int value) {
		String cardHeads = Integer.toString(value);
		
		// last digit 5 : 2 heads
        if (cardHeads.charAt(cardHeads.length() - 1) == '5') {
            this.heads = 2;
        }

        // last digit 0 : 3 heads
        else if (cardHeads.charAt(cardHeads.length() - 1) == '0') {
            this.heads = 3;
        }

        // Add 5 if two last digits have the same value (55, 66...)
        if (cardHeads.length() == 2 
        	&& cardHeads.charAt(cardHeads.length() - 1) == cardHeads.charAt(cardHeads.length() - 2)) {
            // if value already changed, sum 5 and the current this.heads
            this.heads = (this.heads != 1 ? this.heads + 5 : 5);
        }
	}
	
	/* Get the value of the card */
	public int getValue() {
		return value;
	}
	
	/**
	* Set the value of the card
	* @param  value of the card
	*/
	private void setValue(int value) {
		this.value = value;
	}
	
	/**
	* Return the value of the card
	* @return  value of the card
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.value);
		return sb.toString();
	}

}
