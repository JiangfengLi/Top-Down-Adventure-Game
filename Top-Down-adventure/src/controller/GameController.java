package controller;

import Model.Area;
import Model.Enemy;
import Model.GameModel;

/**
 * The controller part of the MVC paradigm for the game.
 * 
 * @author Wes Rodgers
 *
 */
public class GameController {

	private GameModel model;
	
	public GameController() {
		this.model = new GameModel();
	}
	
	public GameController(GameModel model) {
		this.model = model;
	}

	/**
	 * boolean check for whether the player is
	 * dead.
	 * @return true if the player is dead, false otherwise
	 */
	public boolean playerDead() {
		return model.getPlayer().isDead();
	}

	/**
	 * Updates the character model with the distance that the player
	 * moved during that tick
	 * 
	 * @param xMovement the amount and direction to move along the x-axis
	 * @param yMovement the amount and direction to move along the y-axis
	 */
	public void updatePlayerPosition(int xMovement, int yMovement) {
		model.updatePlayerPosition(xMovement, yMovement);		
	}
	
	/**
	 * Calculates pathing for enemies, moves flying enemies randomly
	 * and moves ground enemies towards player.
	 */
	public void updateEnemyPositions() {
		for(Enemy enemy : model.getCurrentArea().getEnemies()) {
			enemy.moveTowardsPlayer();
		}
	}

	public Area getCurrentArea() {
		// TODO Auto-generated method stub
		return model.getCurrentArea();
	}


	
}
