package test;

import static org.junit.jupiter.api.Assertions.*;

import game.Card;

class CardTest {

	void cardHeads() {
		Card c = new Card(55); // 5 last digit case & 2 same digits
		assertTrue(c.getHeads() == 7);
		
		Card c1 = new Card(10); // 0 last digit case
		assertTrue(c1.getHeads() == 3);
		
		Card c2 = new Card(15); // 5 last digit case
		assertTrue(c2.getHeads() == 2);
		
		Card c3 = new Card(12); // no case
		assertTrue(c3.getHeads() == 1);
	}

}


