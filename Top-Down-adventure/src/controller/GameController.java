package controller;

import java.util.ArrayList;
import java.util.Observable;

import Model.Area;
import Model.Enemy;
import Model.GameModel;
import Model.GameObject;
import Model.Obstacle;
import Model.Player;
import Model.PlayerSwing;
import javafx.scene.canvas.Canvas;

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
	 * returns the x,y coordinates of the player in int[2] form
	 * @return int[2] representing the x,y coordinates of the player, int[0] = x, int[1] = y
	 */
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
		
		if(!model.getPlayer().stalled()) {
		
			Area curr = getCurrentArea();
			ArrayList<Obstacle> obstacles = curr.getObstacles();
			
			//checks if the player collides with any obstacles on screen. if so, set him back to his previous location and return
			for(Obstacle obstacle : obstacles) {
				if(collision(getPlayerPosition(), obstacle)) {
					setPlayerPosition(model.getPlayer().getOldLocation()[0], model.getPlayer().getOldLocation()[1]);
					return;
				}
			}
			
			//if the player walked off the screen, shift to the screen that they walked to
			if(offScreen()) {
				
				//determine where to place the player on the next screen
				int[] collisionCoords = offScreenCoords();
				int[] area = new int[2];
				
				//check if the player went off the bottom or top, sets new coordinates appropriately
				if(collisionCoords[0] == getPlayerPosition()[0]) {
					area[0] = 0;
					area[1] = collisionCoords[1] == 1 ? 1 : -1;
				}
				
				//otherwise, we move left/right and we set those coords appropriately
				else {
					area[1] = 0;
					area[0] = collisionCoords[0] == 1 ? 1 : -1;
				}
				
				//shift areas and update the players coordinates appropriately
				model.shiftCurrentArea(area);
				model.setPlayerPosition(collisionCoords[0], collisionCoords[1]);
				return;
			}
			
			//if there were no collisions and no area changes, just move the player by the passed in amount
			model.updatePlayerPosition(xMovement, yMovement);
		}
		else {
			model.updatePlayerPosition(0, 0);
		}
	}
	
	
	/**
	 * determines where to place the character on a new screen
	 * @return the x,y coordinates of the players new position in int[2] form
	 */
	private int[] offScreenCoords() {
		int[] temp = new int[2];
		
		//if the player is off the screen to the left, leave y coordinates the same
		//and set him on the far right side of the screen.
		if(getPlayerPosition()[0] < 0) {
			temp[1] = getPlayerPosition()[1];
			temp[0] = 1149;
			return temp;
		}
		
		//if off screen to the right, y is the same and x is the left side of the screen
		if(getPlayerPosition()[0] > 1150){
			temp[1] = getPlayerPosition()[1];
			temp[0] = 1;
			return temp;
		}
		
		//if offscreen to the bottom, set x the same and y to the top
		if(getPlayerPosition()[1] > 0) {
			temp[0] = getPlayerPosition()[0];
			temp[1] = 1;
			return temp;
		}
		
		//if off screen to the top, x is the same and y is to the bottom
		if(getPlayerPosition()[1] < 0) {
			temp[0] = getPlayerPosition()[0];
			temp[1] = 749;
			return temp;
		}
		
		//this method was called in error if none of the checks return.
		return null;
	}

	/**
	 * checks if the player wandered off screen
	 * @return true if the player is off screen, false otherwise
	 */
	private boolean offScreen() {
		if(getPlayerPosition()[0] < 0 || getPlayerPosition()[0] > 1150 ||
				getPlayerPosition()[1] < 0 || getPlayerPosition()[1]  > 750) {
			return true;
		}
		return false;
	}

	/**
	 * sets the player's position to a specific location
	 * @param i x coordinate of the player's new position
	 * @param j y coordinate of the player's new position
	 */
	private void setPlayerPosition(int i, int j) {
		model.getPlayer().setLocation(i, j);
	}

	/**
	 * checks to see if the player collided with a given obstacle
	 * @param playerPosition the int[2] of the player's x and y coordinates
	 * @param obstacle the obstacle we are checking for collision
	 * @return true if the player collided with the obstacle, false otherwise
	 */
	private boolean collision(int[] playerPosition, GameObject obstacle) {
		
		//if the object is an Obstacle and already destroyed, it is pathable so we return false
		if(obstacle instanceof Obstacle && ((Obstacle) obstacle).destroyed()) {
			return false;
		}
		
		//return true if the player rectangle overlaps the obstacle rectangle.
		if(playerPosition[0] < obstacle.getLocation()[0] + obstacle.getWidth() && 
				playerPosition[0] + model.getPlayer().getHitboxWidth() > obstacle.getLocation()[0] &&
				playerPosition[1] + model.getPlayer().getHitboxHeight() < obstacle.getLocation()[1] + obstacle.getHeight() && 
				playerPosition[1] + model.getPlayer().getHeight() > obstacle.getLocation()[1] + obstacle.getTopHeight()) {
			return true;
		}
		
		//return false if the player rectangle doesn't overlap the obstacle rectangle
		return false;
	}

	/**
	 * Calculates pathing for enemies, moves them appropriately.
	 */
	public void updateEnemyPositions() {
		
		//iterate through all enemies on screen
		for(Enemy enemy : model.getCurrentArea().getEnemies()) {
			
			//if the player hasn't gotten near the enemy yet, the enemy stays inactive
			if(distanceToPlayer(enemy) > 400 && !model.getAnimations().contains(enemy)) {
				if(!enemy.active()) {
					model.getAnimations().add(enemy);
				}
			}
			
			//if the player gets too close
			else {
				if(distanceToPlayer(enemy) <= 400) {
					
					//activate the enemy and remove the idle animation from the model
					enemy.activate();
					if(model.getAnimations().contains(enemy)) {					
						model.getAnimations().remove(enemy);
					}
					
					//finds the point the enemy needs to move to and increments his position towards it based on his speed
					int[] referencePoint = getPathingTarget(enemy, model.getPlayer());
					int x = enemy.getLocation()[0] - referencePoint[0] > 0 ? -enemy.getSpeed() : 0;
					x += enemy.getLocation()[0] - referencePoint[0] < 0 ? enemy.getSpeed() : 0;
					int y = enemy.getLocation()[1] - referencePoint[1] > 0 ? -enemy.getSpeed() : 0;
					y += enemy.getLocation()[1] - referencePoint[1] < 0 ? enemy.getSpeed() : 0;
					
					//smooths out enemy movement if their speed is greater than the distance to the player's coordinate
					if(Math.abs(enemy.getLocation()[0] - referencePoint[0]) < Math.abs(x)) x = x > 0 ? 
								 referencePoint[0] - enemy.getLocation()[0] : enemy.getLocation()[0] - referencePoint[0];
						if(Math.abs(enemy.getLocation()[1] - referencePoint[1]) < Math.abs(y)) y = y > 0 ?
								referencePoint[1] - enemy.getLocation()[1] : enemy.getLocation()[1] - referencePoint[1];
					
					//knocks the enemy back if they are in a stall
					if(enemy.stalled()) {
						enemy.decrementStall();
						enemy.updatePosition(-x*4, -y*4);
					}
					else {
						//if the enemy is a scaredy cat, make him run away when his health is under 1/2
						if(enemy.getHP() < enemy.getMaxHP()/2 && enemy.willFlee()) {
							x = -x;
							y = -y;
						}
						
						
						//update the enemy's position
						enemy.updatePosition(x, y);
						if(x > 0) {
							enemy.setDirection(4);
						}
						if(x < 0) {
							enemy.setDirection(2);
						}
						if(y > 0) {
							enemy.setDirection(3);
						}
						if(y < 0) {
							enemy.setDirection(1);
						}
						if(y != 0 && x != 0) {
							if(Math.abs(enemy.getLocation()[0] - referencePoint[0]) > Math.abs(enemy.getLocation()[1] - referencePoint[1])) {
								enemy.setDirection(x > 0 ? 4 : 2);
							}
							else {
								enemy.setDirection(y > 0 ? 3 : 1);
							}
						}
					}
					
					//if the enemy is offscreen, don't let them be.
					if(enemy.getLocation()[0] < 0 || enemy.getLocation()[0] > 1200 - enemy.getWidth() || enemy.getLocation()[1] < 0 || 
							enemy.getLocation()[1] > 800 - enemy.getHeight()) enemy.setLocation(enemy.getOldLocation()[0], enemy.getOldLocation()[1]);
				}
			}
		}
	}

	/**
	 * returns a reference point for enemy pathing that goes towards the player and avoids obstacles
	 * @param enemy the enemy we are currently finding a path for
	 * @param player the player we want to path to
	 * @return int[2] coordinates for the x,y position the enemy needs to move to to avoid obstacles and get to the player.
	 */
	private int[] getPathingTarget(Enemy enemy, Player player) {
		boolean visible = true;
		int[] referencePoint = new int[2];

		
		for(Obstacle obstacle : model.getCurrentArea().getObstacles()) {
			
		}
		if(visible)	return player.getLocation();
		return referencePoint;
	}

	/**
	 * returns the distance between the enemy's and the player's anchor points.
	 * @param enemy the enemy we are finding the distance from
	 * @return a double representing the enemy's distance to the player
	 */
	private double distanceToPlayer(Enemy enemy) {
		return Math.sqrt(Math.pow(enemy.getLocation()[0] - model.getPlayer().getLocation()[0], 2)
				+ Math.pow((enemy.getLocation()[1] - model.getPlayer().getLocation()[1]), 2));
	}

	/**
	 * returns the current Area that the player is in.
	 * @return
	 */
	public Area getCurrentArea() {
		return model.getCurrentArea();
	}

	/**
	 * runs the player's sword attack
	 * @param canvas
	 */
	public void swordAttack(Canvas canvas) {
		if(!model.getPlayer().stalled()) {
			model.getPlayer().addStall(5);
			model.addAnimation(new PlayerSwing(model.getPlayer().getDirection()));
			//checks for collision with obstacles
			for(Obstacle obstacle : model.getCurrentArea().getObstacles()) {
				if(obstacle.isDestructible() && weaponCollision(model.getPlayer(), obstacle)) {
					obstacle.toggleDestroyed();
					model.addAnimation(obstacle);
				}
			}
			//checks for collision with enemies
			for(Enemy enemy : model.getCurrentArea().getEnemies()) {
				if(weaponCollision(model.getPlayer(), enemy)) {
					enemy.loseHP(model.getPlayer().getDamage());
					enemy.addStall(5);
				}
			}
		}
		
	}

	/**
	 * checks player weapon collision with an obstacle
	 * @param player the player character
	 * @param obstacle the obstacle we are check for collision with
	 * @return true if the weapon collides with an obstacle, false otherwise.
	 */
	private boolean weaponCollision(Player player, GameObject obstacle) {
		if(obstacle instanceof Obstacle && ((Obstacle) obstacle).destroyed()) {
				return false;
		}
		
		int[] playerPosition = new int[2];
		
		//if player is facing north, weapon affect 50x50 square north of him
		if(player.getDirection() == 1) {
			playerPosition[0] = player.getLocation()[0];
			playerPosition[1] = player.getLocation()[1] - player.getHeight() - player.getHitboxOffset() + 15;
		}
		//if west, square is west
		else if(player.getDirection() == 2) {
			playerPosition[0] = player.getLocation()[0] - player.getWidth() + 15;
			playerPosition[1] = player.getLocation()[1];
		}
		//if south, square is south
		else if(player.getDirection() == 3) {
			playerPosition[0] = player.getLocation()[0];
			playerPosition[1] = player.getLocation()[1] + player.getHeight() - 15;			
		}
		//else east, then square is east
		else {
			playerPosition[0] = player.getLocation()[0] + player.getWidth() - 15;
			playerPosition[1] = player.getLocation()[1];
		}
		
		return collision(playerPosition, obstacle);
	}

	/**
	 * sets player's direction to the passed in integer
	 * @param i either 1 for north, 2 for west, 3 for south, or 4 for east
	 */
	public void setPlayerDirection(int i) {
		model.getPlayer().setDirection(i);
		
	}

	/**
	 * increments the game clock
	 */
	public void incrementGameClock() {
		model.incrementGameClock();
		
	}

	/**
	 * returns the tick the game clock is currently on
	 * @return the tick the game clock is currently on
	 */
	public int getGameClock() {
		return model.getGameClock();
		
	}

	/**
	 * returns the current animations that need to be played in ArrayList<GameObject> form
	 * @return the current animations that need to be played as an ArrayList<GameObject>
	 */
	public Object getAnimations() {
		 return model.getAnimations();
	}

	/**
	 * performs a bow attack
	 * @param canvas
	 */
	public void bowAttack(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * checks projectile collision
	 */
	public void checkProjectileCollision() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * removes enemies that are dead from the map
	 */
	public void removeDeadEnemies() {
		ArrayList<Enemy> dead = new ArrayList<Enemy>();
		
		//gathers up all dead enemies
		for(Enemy enemy : model.getCurrentArea().getEnemies()) {
			if(enemy.isDead()) {
				dead.add(enemy);
			}
		}
		
		//adds them to the animation list so we can play their death animation
		for(Enemy enemy : dead) {
			model.addAnimation(enemy);
		}
		
		//removes them from the area so they can't bother us anymore
		model.getCurrentArea().getEnemies().removeAll(dead);
		
	}

	//returns the player's speed.
	public int getPlayerSpeed() {
		// TODO Auto-generated method stub
		return model.getPlayer().getSpeed();
	}

	public boolean playerStalled() {
		// TODO Auto-generated method stub
		return model.getPlayer().stalled();
	}

	public Observable getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void enemyAttack() {
		for(Enemy enemy : model.getCurrentArea().getEnemies()) {
			if(collision(model.getPlayer().getLocation(), enemy)) {
				//add player stall
				//toggle damage flag
				//player knockback code
				//if(playerdamageflag){
				//	model.getPlayer().loseHP(enemy.getDamage());
				//}
			}
		}
		
	}

	
}
