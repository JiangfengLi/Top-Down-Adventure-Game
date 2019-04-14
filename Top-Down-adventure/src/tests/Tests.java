package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.*;
import controller.GameController;

class Tests {

	@Test
	void testArea() {
		Area area = new Area();
		assertEquals(area.getObstacles(), null);
		assertEquals(area.getEnemies(), null);
	}

	@Test
	void testDPS() {
		Enemy dps = new DPS();
	}
	
	@Test
	void testFlier() {
		Enemy flier = new Flier();
		assertTrue(flier.playerIsVisible());
	}
	
	@Test
	void testTank() {
		Enemy tank = new Tank();
	}
	
	@Test
	void testGameState() {
		GameState gameState = new GameState();
	}
	
	@Test
	void testPlayer() {
		Player player = new Player();
		assertEquals(player.getHP(), 3);
		
		player.loseHP(4);
		assertEquals(player.getHP(), 0);
		assertTrue(player.isDead());
		
		player.addHP(1);
		assertEquals(player.getHP(), 1);
		
		player.addHP(4);
		assertEquals(player.getHP(), 3);
	}
	
	@Test
	void testGameModel() {
		GameModel model = new GameModel();
		assertEquals(model.getPlayer().getHP(), 3);
	}
	
	@Test
	void testGameController() {
		GameModel model = new GameModel();
		GameController controller = new GameController(model);
		assertFalse(controller.playerDead());
		
		model.getPlayer().loseHP(3);
		assertTrue(controller.playerDead());
	}
	
	@Test
	void testGameView() {
		
	}
	
	@Test
	void testButtonMaker() {
		
	}
	
	@Test
	void testWindow() {
		
	}
	
	@Test
	void testItem() {
		
	}
}
