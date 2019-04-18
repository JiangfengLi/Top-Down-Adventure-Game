package controller;

import java.util.ArrayList;

import Model.Area;
import Model.Enemy;
import Model.GameModel;
import Model.GameObject;
import Model.Obstacle;
import Model.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
		
		if(Math.abs(obstacle.getLocation()[0] - playerPosition[0]) > 
		Math.abs(obstacle.getLocation()[1] - (playerPosition[1] + model.getPlayer().getHitbox()[0]) + 20)) {		
			if(playerPosition[0] < obstacle.getLocation()[0]) {
				newCoords[0] = obstacle.getLocation()[0] - model.getPlayer().getHitbox()[1];
				newCoords[1] = playerPosition[1];
			}
			else {
				newCoords[0] = obstacle.getLocation()[0] + obstacle.getWidth();
				newCoords[1] = playerPosition[1];
			}
		}
		else {
			if(playerPosition[1] + model.getPlayer().getHitbox()[0] < obstacle.getLocation()[1]) {
				newCoords[0] = playerPosition[0];
				newCoords[1] = obstacle.getLocation()[1] - model.getPlayer().getHeight();
			}
			else{
				newCoords[0] = playerPosition[0];
				newCoords[1] = obstacle.getLocation()[1] + obstacle.getHeight() - model.getPlayer().getHitbox()[0];
			}
		}
		
		return newCoords;
	}

	private boolean collision(int[] playerPosition, Obstacle obstacle) {
		if(obstacle.destroyed()) {
			return false;
		}
		if(playerPosition[0] < obstacle.getLocation()[0] + obstacle.getWidth() && 
				playerPosition[0] + model.getPlayer().getHitbox()[2] > obstacle.getLocation()[0] &&
				playerPosition[1] + model.getPlayer().getHitbox()[0] < obstacle.getLocation()[1] + obstacle.getHeight() && 
				playerPosition[1] + model.getPlayer().getHeight() > obstacle.getLocation()[1]) {
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

	public void swordAttack(Canvas canvas) {
		for(Obstacle obstacle : model.getCurrentArea().getObstacles()) {
			if(obstacle.isDestructible() && weaponCollision(model.getPlayer(), obstacle)) {
				obstacle.toggleDestroyed();
				model.addAnimation(obstacle);
			}
		}
		for(Enemy enemy : model.getCurrentArea().getEnemies()) {
			if(weaponCollision(model.getPlayer(), enemy)) {
				enemy.loseHP(model.getPlayer().getDamage());
			}
		}
		
	}

	private boolean weaponCollision(Player player, GameObject obstacle) {
		if(((Obstacle) obstacle).destroyed()) {
			return false;
		}
		int[] playerPosition = new int[2];
		
		//if player is facing north, weapon affect 50x50 pixel square north of him
		if(player.getDirection() == 1) {
			playerPosition[0] = player.getLocation()[0];
			playerPosition[1] = player.getLocation()[1] - 50;
		}
		//if west, square is west
		else if(player.getDirection() == 2) {
			playerPosition[0] = player.getLocation()[0] - 50;
			playerPosition[1] = player.getLocation()[1];
		}
		//if south, square is south
		else if(player.getDirection() == 3) {
			playerPosition[0] = player.getLocation()[0];
			playerPosition[1] = player.getLocation()[1] + player.getHeight();			
		}
		//else east, then square is east
		else {
			playerPosition[0] = player.getLocation()[0] + player.getWidth();
			playerPosition[1] = player.getLocation()[1];
		}
		
		return collision(playerPosition, (Obstacle) obstacle);
	}

	public void setPlayerDirection(int i) {
		model.getPlayer().setDirection(i);
		
	}

	public void incrementGameClock() {
		model.incrementGameClock();
		
	}

	public int getGameClock() {
		return model.getGameClock();
		
	}

	public Object getAnimations() {
		 return model.getAnimations();
	}

	public void bowAttack(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	
}
