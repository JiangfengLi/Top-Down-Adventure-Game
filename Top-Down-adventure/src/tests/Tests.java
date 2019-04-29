package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.Test;

import Model.*;
import controller.GameController;
/**
 * Class to test all the functions and codes of program except GUI, network code.
 * @author Jiangfeng Li
 *
 */
class Tests {

	@Test
	void testArea() {
		
	}

	@Test
	void testDPS() {
	}
	
	@Test
	void testFlier() {
		
	}
	
	@Test
	void testTank() {
		
	}
	
	@Test
	void testPlayer() {
		Player player = new Player();
		assertEquals(player.getHP(), 6);
		assertFalse(player.isDead());
		
		player.loseHP(4);
		assertEquals(player.getHP(), 2);
		assertFalse(player.isDead());
		
		player.addHP(1);
		assertEquals(player.getHP(), 3);
		assertFalse(player.isDead());
		
		player.addHP(4);
		assertEquals(player.getHP(), 6);
		assertFalse(player.isDead());

		player.loseHP(8);
		assertEquals(player.getHP(), 0);
		assertTrue(player.isDead());
		
	}
	
	@Test
	void testGameModel() {
		GameModel model = new GameModel();
		assertEquals(model.getPlayer().getHP(), 6);
	}
	
	@Test
	void testGameController() {
		GameModel model = new GameModel();
		GameController controller = new GameController(model);
		assertFalse(controller.playerDead());
				
		// test player position
		int[] playerPosition = {100, 100};
		assertEquals(controller.getPlayerPosition()[0], 100);
		assertEquals(controller.getPlayerPosition()[1], 100);
		controller.updatePlayerPosition(1, 1);
		playerPosition[0]++;
		playerPosition[1]++;
		assertEquals(controller.getPlayerPosition()[0], 101);
		assertEquals(controller.getPlayerPosition()[1], 101);
		controller.updateEnemyPositions();
		
		// test current GameMap
		GameMap map = new GameMap();
		assertNotNull(controller.getArea());
		
		controller.swordAttack();
		controller.setPlayerDirection(3);
		controller.incrementGameClock();
		assertEquals(controller.getGameClock(), 1);

		assertEquals(controller.getGameClock(), 1);
		assertNotNull(controller.getAnimations());
		
		controller.bowAttack();
		for(int i = 0; i < 100; i++)
			controller.updateProjectilePosition();

		controller.removeDeadEnemies();
		
		assertEquals(controller.getPlayerSpeed(), 8);
		assertTrue(controller.playerStalled());
		
		controller.enemyAttack();
		
		assertFalse(controller.inDungeon());
		assertNotNull(controller.getDungeonMap());
		assertEquals(controller.getDungeonMap(), model.getDungeonMap());
		assertNotNull(controller.getOverlandMap());
		assertEquals(controller.getOverlandMap(), model.getOverlandMap());
		assertNotNull(controller.getSoundFX());

		// test URL
//		URL url = GameController.class.getResource("/style/soundfx/LTTP_Arrow_Shoot.wav");
//		assertEquals(controller.sformat("LTTP_Arrow_Shoot.wav"), url.toString());
		assertNotNull(controller.getModel());
		assertEquals(controller.getModel(), model);
		
		model.getPlayer().loseHP(6);
		controller.setModel(model);
		assertTrue(controller.playerDead());
		
		
		//test enemy attack
		controller = new GameController();
		controller.updatePlayerPosition(399, 233);
		for(int i = 0; i < 1000; i++) {
			controller.updateEnemyPositions();
			controller.enemyAttack();
		}
		System.out.println("Player position: x: " + controller.getPlayerPosition()[0] + " y: " + controller.getPlayerPosition()[1]);
	//	assertTrue(controller.playerDead());
		
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
	void testArrow() {
		
	}
	
	void testHeart() {
		
	}
}
