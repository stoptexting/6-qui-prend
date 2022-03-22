package test;

import org.junit.jupiter.api.Test;

import game.Table;

class PlayTest {

	@Test
	void automaticGame() {
		Table t = new Table();	
		t.start();
	}

}
