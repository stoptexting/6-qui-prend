package test;

import static org.junit.Assert.assertTrue;

import java.util.SortedMap;
import java.util.TreeMap;

import game.Card;
import game.Penalties;
import util.Logger;

class PenaltiesTest {


	void createPenalties() {
		Penalties p = new Penalties();
		SortedMap<Integer, Card> round1 = new TreeMap<>();
		Card c = new Card(10);
		Card c2 = new Card(55);
		round1.put(c.getValue(), c); // 3 heads
		round1.put(c2.getValue(), c2); // 7 heads
		p.addPenalties(round1);
		assertTrue(p.getRoundHeads() == 10);
		
		
		SortedMap<Integer, Card> round2 = new TreeMap<>();
		Card c3 = new Card(15);
		Card c4 = new Card(100);
		round2.put(c3.getValue(), c3); // 2 heads
		round2.put(c4.getValue(), c4); // 3 heads
		p.addPenalties(round2);
		assertTrue(p.getRoundHeads() == 5);
		assertTrue(p.getTotalHeads() == 15);
		
		Logger.logln("Le joueur a obtenu " + p.getRoundHeads() + " têtes de boeuf au dernier round.");
		Logger.logln("Le joueur a obtenu " + p.getTotalHeads() + " têtes de boeuf au cours de la partie.");
	}

}
