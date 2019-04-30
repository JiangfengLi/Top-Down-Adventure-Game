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
	void testGrass() {
		Grass testGrass = new Grass(100, 100);
		assertFalse(testGrass.hasTopImage());
		testGrass.toggleDestroyed();
		testGrass.endAnimation();
		assertTrue(testGrass.animationDone());
		assertEquals(testGrass.destroyedFrame(), 1);
		assertEquals(testGrass.lastFrame(), 9);
		testGrass.updatePosition(200, 200);
		testGrass.setLocation(130, 130);
		
	}	
	
	@Test
	void testTank() {
		Tank testTank = new Tank(300, 300);
		assertFalse(testTank.willFlee());
		testTank.updateLocation(100, 100);
		testTank.setLocation(100, 100);
		
	}
	
	@Test
	void testPlayer() {
		Player player = new Player();
		assertEquals(player.getHP(), 6);
		assertFalse(player.isDead());
		
		assertEquals(player.getArrowQuantity(), 30);
		for(int i =0; i < 10; i++)
		   player.decrementArrows();
		assertEquals(player.getArrowQuantity(), 20);
		player.addArrows(10);
		assertEquals(player.getArrowQuantity(), 30);
		assertFalse(player.hasKey());
		assertFalse(player.hasBossKey());
		player.giveKey();
		player.giveBossKey();
		assertTrue(player.hasKey());
		assertTrue(player.hasBossKey());
		player.addBuff(20);
		for(int i =0; i < 20; i++)
			   player.decrementBuff();
		player.updateX(300);
		player.updateY(300);
		player.updatePosition(10, -10);
		player.setLocation(-10, 10);
		
		int[] location = {300, 300};
		BossAttack BA = new BossAttack(player, location);
		assertEquals(BA.getTimer(), 1);
		assertNotNull(BA.getTarget());
		
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
//		GameMap temMap = new GameMap();
		int[] newArea = {2,2};		
		model.shiftCurrentArea(newArea);
		model.setPlayerPosition(750, 475);
		model.swapToDungeon();
		model.swapToOverland();
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
		controller.updatePlayerPosition(0, 5000);
		for(int i = 0; i < 1000; i++) {
			controller.updateEnemyPositions();
			controller.enemyAttack();
		}		
		// assertTrue(controller.playerDead());
		
	}
	
	
	@Test
	void testBoss() {
		Boss testBoss = new Boss(449, 283, false);
		assertFalse(testBoss.isMainBoss());
		testBoss.removeShield();
		assertFalse(testBoss.shielded());
		testBoss.addShield();
		assertTrue(testBoss.shielded());
		assertFalse(testBoss.preAttack());
		testBoss.addPreAttack();
		assertTrue(testBoss.preAttack());
		for(int i = 0; i < 51; i++)
			assertFalse(testBoss.timeToAttack());
		assertTrue(testBoss.timeToAttack());
		for(int i = 0; i < 45; i++)
			assertFalse(testBoss.timeToShield());
		assertTrue(testBoss.timeToShield());
		assertEquals(testBoss.getBossTimer(), 1);
		assertEquals(testBoss.getPillar()[0], 464);
		assertEquals(testBoss.getPillar()[1], 372);

		
	}
	
	@Test
	void testButtonMaker() {
		
	}
	
	@Test
	void testWindow() {
		
	}
	
	@Test
	void testArrow() {
		int[] location = {300, 300};
		ArrowShot newArrowShot = new ArrowShot(1, location);
		ShieldPillar newShieldPillar = new ShieldPillar(location);
		BowShot newBowShot = new BowShot(2);
	}
	
//	void testHeart() {
//		int[] location = {300, 300};
//		Heart newHeart = new Heart(location);
//		boolean res = newHeart.getHP() > 0;
//		assertNotEquals(newHeart.getHP(), 0);
//	}
	
//	void testKey() {
//		int[] location = {300, 300};
//		Key newKey = new Key(location, false);	
//		assertFalse(newKey.isBossKey());
//	}
	
}
