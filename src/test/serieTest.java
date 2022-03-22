package test;
import static org.junit.Assert.assertTrue;

import game.Card;
import game.Serie;
import util.Logger;

class SerieTest {

	void createSerie() {
		Serie s = new Serie();
		for (int i = 1; i <= 10; ++i) {
			s.addCard(new Card(i));
		}
		Logger.logln(s.toString());
		
	}
	
	
	void testSeries() {
		Serie s = new Serie();
		s.addCard(new Card(55));
		s.addCard(new Card(10));
		String expected = "55 (7), 10 (3)";
		Logger.logln(s.toString());
		assertTrue(expected.equals(s.toString().substring(14)));
	}
	
	void insertCard() {
		Serie s3 = new Serie();
		s3.addCard(new Card(10));
		s3.addCard(new Card(20));
		s3.addCard(new Card(15));
		s3.addCard(new Card(99));
		Logger.logln(s3.toString());
		assertTrue(s3.getKeys().get(0) == 10);
		assertTrue(s3.getKeys().get(s3.getKeys().size() - 1) == 99);
	}

}
