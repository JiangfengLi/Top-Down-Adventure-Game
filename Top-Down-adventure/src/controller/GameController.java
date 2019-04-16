package controller;

import java.util.ArrayList;

import Model.Area;
import Model.Enemy;
import Model.GameModel;
import Model.Obstacle;

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

	public int[] getPlayerPosition() {		
		return model.getPlayer().getLocation();
	}
	
	/**
	 * Updates the character model with the distance that the player
	 * moved during that tick
	 * 
	 * @param xMovement the amount and direction to move along the x-axis
	 * @param yMovement the amount and direction to move along the y-axis
	 */
	public void updatePlayerPosition(int xMovement, int yMovement) {
		/********NOTE********
		 * These are placeholder values, but 
		 * this is probably the easiest way to tell how
		 * much to move during a given turn
		 */		
		
		Area curr = getCurrentArea();
		ArrayList<Obstacle> obstacles = curr.getObstacles();
		for(Obstacle obstacle : obstacles) {
			if(collision(getPlayerPosition(), obstacle)) {
				int[] collisionCoords = collisionUpdate(getPlayerPosition(), obstacle);
				setPlayerPosition(collisionCoords[0], collisionCoords[1]);
				return;
			}
		}
		
		if(offScreen()) {
			int[] collisionCoords = offScreenCoords();
			int[] area = new int[2];
			if(collisionCoords[0] == getPlayerPosition()[0]) {
				area[0] = 0;
				area[1] = collisionCoords[1] == 1 ? 1 : -1;
			}
			else {
				area[1] = 0;
				area[0] = collisionCoords[0] == 1 ? 1 : -1;
			}
			model.shiftCurrentArea(area);
			model.setPlayerPosition(collisionCoords[0], collisionCoords[1]);
			return;
		}
		
		model.updatePlayerPosition(xMovement, yMovement);		
	}
	
	private int[] offScreenCoords() {
		int[] temp = new int[2];
		if(getPlayerPosition()[0] < 0) {
			temp[1] = getPlayerPosition()[1];
			temp[0] = 1449;
			return temp;
		}
		if(getPlayerPosition()[0] > 1450){
			temp[1] = getPlayerPosition()[1];
			temp[0] = 1;
			return temp;
		}
		if(getPlayerPosition()[1] > 0) {
			temp[0] = getPlayerPosition()[0];
			temp[1] = 1;
			return temp;
		}
		if(getPlayerPosition()[1] < 0) {
			temp[0] = getPlayerPosition()[0];
			temp[1] = 899;
			return temp;
		}
		return null;
	}

	private boolean offScreen() {
		if(getPlayerPosition()[0] < 0 || getPlayerPosition()[0] + 50 > 1500 ||
				getPlayerPosition()[1] < 0 || getPlayerPosition()[1]  > 900) {
			return true;
		}
		return false;
	}

	private void setPlayerPosition(int i, int j) {
		model.getPlayer().setLocation(i, j);
		
	}

	private int[] collisionUpdate(int[] playerPosition, Obstacle obstacle) {
		int[] newCoords = new int[2];
		
		if(Math.abs(obstacle.getLocation()[0] - playerPosition[0]) > Math.abs(obstacle.getLocation()[1] - (playerPosition[1] + 50))) {		
			if(playerPosition[0] < obstacle.getLocation()[0]) {
				newCoords[0] = obstacle.getLocation()[0] - 50;
				newCoords[1] = playerPosition[1];
			}
			else {
				newCoords[0] = obstacle.getLocation()[0] + obstacle.getWidth();
				newCoords[1] = playerPosition[1];
			}
		}
		else {
			if(playerPosition[1] + 50 < obstacle.getLocation()[1]) {
				newCoords[0] = playerPosition[0];
				newCoords[1] = obstacle.getLocation()[1] - 100;
			}
			else {
				newCoords[0] = playerPosition[0];
				newCoords[1] = obstacle.getLocation()[1] + obstacle.getHeight() - 50;
			}
		}
		
		return newCoords;
	}

	private boolean collision(int[] playerPosition, Obstacle obstacle) {
		if(playerPosition[0] < obstacle.getLocation()[0] + obstacle.getWidth() && 
				playerPosition[0] + 50 > obstacle.getLocation()[0] &&
				playerPosition[1] + 50 < obstacle.getLocation()[1] + obstacle.getHeight() && 
				playerPosition[1] + 100 > obstacle.getLocation()[1]) {
			return true;
		}
		return false;
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
