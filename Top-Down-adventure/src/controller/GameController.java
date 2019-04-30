package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import Model.*;
import Model.Character;
import javafx.scene.media.AudioClip;

/**
 * The controller part of the MVC paradigm for the game.
 * Provides methods to access and modify various parts of the model.
 * Can be constructed from scratch or from an existing GameModel
 * 
 * @author Wes Rodgers
 *
 */
public class GameController {

	private GameModel model;
	private Player player;
	private ArrayList<AudioClip> soundfx = new ArrayList<AudioClip>();
	
	public GameController() {
		this.model = new GameModel();
		player = model.getPlayer();
	}
	
	public GameController(GameModel model) {
		this.model = model;
		player = model.getPlayer();
	}

	/**
	 * boolean check for whether the player is
	 * dead.
	 * @return true if the player is dead, false otherwise
	 */
	public boolean playerDead() {
		return player.isDead();
	}

	/**
	 * returns the x,y coordinates of the player in int[2] form
	 * @return int[2] representing the x,y coordinates of the player, int[0] = x, int[1] = y
	 */
	public int[] getPlayerPosition() {		
		return player.getLocation();
	}
	
	
	/**
	 * Updates the character model with the distance that the player
	 * moved during that tick
	 * 
	 * @param xMovement the amount and direction to move along the x-axis
	 * @param yMovement the amount and direction to move along the y-axis
	 */
	public void updatePlayerPosition(int xMovement, int yMovement) {
		
		//if the player is stalled, decrement his stall
		if(playerStalled()) player.decrementStall();
		if(player.getDamageStall() > 0) player.decrementDamageStall();
		if(player.buffed()) player.decrementBuff();
		
		//check to see if the player has been damaged recently
		if(player.damaged()) {
		
			//if he is still in the stall that caused the damaged flag, he is experiencing knockback.
			if(player.getDamageStall() > 0) {
				Enemy attacker = player.lastEnemy();
				xMovement = attacker.getLocation()[0] > getPlayerPosition()[0] ? -player.getSpeed()*2 : 0;
				xMovement += attacker.getLocation()[0] < getPlayerPosition()[0] ? player.getSpeed()*2 : 0;
				yMovement = attacker.getLocation()[1] > getPlayerPosition()[1] ? -player.getSpeed()*2 : 0;
				yMovement += attacker.getLocation()[1] < getPlayerPosition()[1] ? player.getSpeed()*2 : 0;
				
				//if the knockback would knock him off screen, remove that directions movement.
				if(getPlayerPosition()[0] + xMovement < 0) xMovement = 0;
				if(getPlayerPosition()[0] + xMovement > 999 - player.getWidth()) xMovement = 0;
				if(getPlayerPosition()[1] + yMovement < 0) yMovement = 0;
				if(getPlayerPosition()[1] + yMovement > 666 - player.getHeight()) yMovement = 0;
			}
			
			//otherwise, the damage is no longer recent and needs to have its flag changed
			else player.toggleDamaged();
		}
		
		//if the player is stalled and it wasn't because he took damage, then he has no movement.
		if(playerStalled()) {
			xMovement = 0;
			yMovement = 0;
		}
		
		//if the player isn't stalled, or if he is stalled but he took damage
		if(!playerStalled()) {
		
			Area curr = getArea();
			CopyOnWriteArrayList<Obstacle> obstacles = curr.getObstacles();
			
			//checks if the player collides with any obstacles on screen. if so, remove that direction's movement
			for(Obstacle obstacle : obstacles) {
				int[] futurePosition = new int[2];
				futurePosition[0] = getPlayerPosition()[0] + xMovement;
				futurePosition[1] = getPlayerPosition()[1] + yMovement;
				if(collision(futurePosition, obstacle)) {
					if(obstacle instanceof DungeonEntrance && !model.inDungeon()) {
						model.toggleInDungeon();
						model.swapToDungeon();
					}
					else if(obstacle instanceof DungeonEntrance && model.inDungeon()) {
						model.toggleInDungeon();
						model.swapToOverland();
					}
					if(obstacle instanceof Door && player.hasBossKey()) {
						soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Door_Unlock.wav")));
						soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Door.wav")));
						obstacles.remove(obstacle);
						player.removeBossKey();
					}
					
					int[] futureX = new int[2];
					int[] futureY = new int[2];
					futureX[0] = getPlayerPosition()[0] + xMovement;
					futureX[1] = getPlayerPosition()[1];
					futureY[0] = getPlayerPosition()[0];
					futureY[1] = getPlayerPosition()[1] + yMovement;
					
					if(collision(futureX, obstacle)) {
						xMovement = 0;
					}
					if(collision(futureY, obstacle)) {
						yMovement = 0;
					}					 
				}
			}
			
			//determines whether there is a boss on this screen since we can't leave until the boss is defeated.
			boolean boss = false;
			for(Enemy enemy : getArea().getEnemies()) {
				if(enemy instanceof Boss) {
					boss = true;
				}
			}
			if(offScreen() && boss) {
				player.setLocation(player.getOldLocation()[0],  player.getOldLocation()[1]);
			}
			//if the player walked off the screen, shift to the screen that they walked to
			else if(offScreen() && !player.damaged()) {
				
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
		}
		model.updatePlayerPosition(xMovement, yMovement);
		
		//checks player collision with loot, correctly changes the right attribute when loot is picked up
		Iterator<Item> drops = getArea().getLoot().iterator();
		while(drops.hasNext()) {
			Item loot = drops.next();
			if(collision(getPlayerPosition(), loot)) {
				if(!(loot instanceof Key)) {
					soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Item.wav")));
				}
				if(loot instanceof Arrow) {
					player.addArrows(((Arrow) loot).getQuantity());
				}
				if(loot instanceof Heart) {
					player.addHP(((Heart) loot).getHP());
				}
				if(loot instanceof SpeedBuff) {
					player.addBuff(200);
				}
				if(loot instanceof Key) {
					soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Get_Key_StereoL.wav")));
					if(((Key)loot).isBossKey()) {
						player.giveBossKey();
					}
					else {
						player.giveKey();
					}
				}
				drops.remove();
			}
		}
	}

	/**
	 * determines where to place the character on a new screen
	 * @return the x,y coordinates of the players new position in int[2] form
	 */
	private int[] offScreenCoords() {
		int[] temp = new int[2];
		
		//if the player is off the screen to the left, leave y coordinates the same
		//and set him on the far right side of the new screen.
		if(getPlayerPosition()[0] < 0) {
			temp[1] = getPlayerPosition()[1];
			temp[0] = 948;
			return temp;
		}
		
		//if off screen to the right, y is the same and x is the left side of the new screen
		if(getPlayerPosition()[0] > 949){
			temp[1] = getPlayerPosition()[1];
			temp[0] = 1;
			return temp;
		}
		
		//if offscreen to the bottom, set x the same and y to the top of the new screen
		if(getPlayerPosition()[1] > 0) {
			temp[0] = getPlayerPosition()[0];
			temp[1] = 1;
			return temp;
		}
		
		//if off screen to the top, x is the same and y is to the bottom of the new screen
		if(getPlayerPosition()[1] < 0) {
			temp[0] = getPlayerPosition()[0];
			temp[1] = 615;
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
		if(getPlayerPosition()[0] < 0 || getPlayerPosition()[0] > 949 ||
				getPlayerPosition()[1] < 0 || getPlayerPosition()[1]  > 616) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks to see if the player collided with a given obstacle
	 * @param playerPosition the array of integer int[2] of the player's x and y coordinates
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
				playerPosition[0] + player.getHitboxWidth() > obstacle.getLocation()[0] &&
				playerPosition[1] + player.getHitboxHeight() < obstacle.getLocation()[1] + obstacle.getHeight() && 
				playerPosition[1] + player.getHeight() > obstacle.getLocation()[1] + obstacle.getTopHeight()) {
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
		for(Enemy enemy : getArea().getEnemies()) {
			//regular enemies and the mini-boss all have the same behavioral patterns
			if(!(enemy instanceof Boss) || (enemy instanceof Boss && !((Boss) enemy).isMainBoss())) {
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
						int[] referencePoint = getPathingTarget(enemy, player);
						int x = enemy.getLocation()[0] > referencePoint[0] ? -enemy.getSpeed() : 0;
						x += enemy.getLocation()[0] < referencePoint[0] ? enemy.getSpeed() : 0;
						int y = enemy.getLocation()[1] > referencePoint[1] ? -enemy.getSpeed() : 0;
						y += enemy.getLocation()[1] < referencePoint[1] ? enemy.getSpeed() : 0;
						
						//smooths out enemy movement if their speed is greater than the distance to the player's coordinate
						if(Math.abs(enemy.getLocation()[0] - referencePoint[0]) < Math.abs(x)) x = x > 0 ?  
							 referencePoint[0] - enemy.getLocation()[0] : enemy.getLocation()[0] - referencePoint[0];
						if(Math.abs(enemy.getLocation()[1] - referencePoint[1]) < Math.abs(y)) y = y > 0 ?
							referencePoint[1] - enemy.getLocation()[1] : enemy.getLocation()[1] - referencePoint[1];
							
						//knocks the enemy back if they are in a stall
						if(enemy.stalled()) {
							
							enemy.decrementStall();
							x = -x*4;
							y = -y*4;
							int[] futurePosition = new int[2];
							futurePosition[0] = enemy.getLocation()[0] + x;
							futurePosition[1] = enemy.getLocation()[1] + y;
							for(Obstacle obs : getArea().getObstacles()) {
								if(collision(futurePosition, obs)) {
									x = 0;
									y = 0;
								}
							}
							
							enemy.updatePosition(x, y);
							
							//if the enemy is offscreen, don't let them be.
							if(enemy.getLocation()[0] < 0 || enemy.getLocation()[0] > 999 - enemy.getWidth() || enemy.getLocation()[1] < 0 || 
									enemy.getLocation()[1] > 666 - enemy.getHeight()) enemy.setLocation(enemy.getOldLocation()[0], enemy.getOldLocation()[1]);
						}
						
						//if they aren't stalled
						else {
							
							//if the enemy is a scaredy cat, make him run away when his health is under 1/2
							if(enemy.getHP() <= enemy.getMaxHP()/2 && enemy.willFlee()) {
								x = -x;
								y = -y;
							}
							
							//sets the enemy's direction based on which way they are moving
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
							
							//smooths out enemy directional setting
							if(y != 0 && x != 0) {
								if(Math.abs(enemy.getLocation()[0] - referencePoint[0]) > Math.abs(enemy.getLocation()[1] - referencePoint[1])) {
									enemy.setDirection(x > 0 ? 4 : 2);
								}
								else {
									enemy.setDirection(y > 0 ? 3 : 1);
								}
							}
							
							//checks to see where the movement will take the enemy
							int[] futurePosition = new int[2];
							futurePosition[0] = enemy.getLocation()[0] + x;
							futurePosition[1] = enemy.getLocation()[1] + y;
							
							//iterate through the obstacles to see if the enemy will run into them
							for(Obstacle obs : getArea().getObstacles()) {
								int[] futureX = new int[2];
								int[] futureY = new int[2];
								
								futureX[0] = enemy.getLocation()[0] + x;
								futureX[1] = enemy.getLocation()[1];
								futureY[0] = enemy.getLocation()[0];
								futureY[1] = enemy.getLocation()[1] + y;
								if(!(enemy instanceof Flier)) {
									if(collision(futureX, obs) && y == 0) {
										y = enemy.getSpeed();
									}
									else if(collision(futureY, obs) && x == 0) {
										x = enemy.getSpeed();
									}
									if(collision(futureX, obs)) {
										x = 0;
									}
									if(collision(futureY, obs)) {
										y = 0;
									}
								}
							}
							enemy.updatePosition(x, y);
						}
					}
				}
			}
			
			//otherwise we set the behavior for the main boss.
			else {	
				Boss boss = (Boss) enemy;
				enemy.activate();
				
				int x = 0;
				int y = 0;
					
				//boss has 3 phases, shielded, moving, and preparing to attack
				if(getGameClock()%400 == 0) soundfx.add(new AudioClip(sformat("/BossSounds/OOT_Navi_Hey1.wav")));
				
				
				if(boss.preAttack()) {
					x = 0;
					y = 0;		
					if(boss.timeToAttack()) {
						int[] missileStart = new int[2];
						soundfx.add(new AudioClip(sformat("/BossSounds/oot_navi_watchout1.mp3")));
						missileStart[0] = enemy.getLocation()[0] + 50;
						missileStart[1] = enemy.getLocation()[1] + 50;
						getArea().addProjectile(new BossAttack(player, missileStart));
					}
				}
				
				//otherwise, the boss is moving. This has the boss move in a specific pattern across the screen
				else {
					if(!boss.shielded() && boss.timeToShield()) {
						soundfx.add(new AudioClip(sformat("/BossSounds/OOT_Navi_Listen1.wav")));
						boss.addShield();
						getArea().addEnemy(new ShieldPillar(boss.getPillar()));
					}
					if(boss.getBossTimer()%300 <= 2) {

						boss.addPreAttack();
					}
					
					//this is all just to set the movement based on the boss' internal timer.
					int directional = boss.getBossTimer()%360;
					if(directional <= 180) {
						if(directional >= 45 && directional < 135) {
							x = -boss.getSpeed();
							y = -boss.getSpeed();
						}
						else {
							y = boss.getSpeed();
							x = -boss.getSpeed();
						}
					}
					else {
						if(directional >= 225 && directional < 315) {
							x = boss.getSpeed();
							y = -boss.getSpeed();
						}
						else {
							y = boss.getSpeed();
							x = boss.getSpeed();
						}
					}
					
				}
				
				enemy.updatePosition(x,y);
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
		return player.getLocation();
	}

	/**
	 * returns the distance between the enemy's and the player's anchor points.
	 * @param enemy the enemy we are finding the distance from
	 * @return a double representing the enemy's distance to the player
	 */
	private double distanceToPlayer(Enemy enemy) {
		return Math.sqrt(Math.pow(enemy.getLocation()[0] - getPlayerPosition()[0], 2)
				+ Math.pow((enemy.getLocation()[1] - getPlayerPosition()[1]), 2));
	}

	/**
	 * returns the current Area that the player is in.
	 * @return the area the player is currently in
	 */
	public Area getArea() {
		return model.getCurrentArea();
	}

	/**
	 * runs the player's sword attack
	 */
	public void swordAttack() {
		//doesn't let us swing if we have attacked or been damaged recently
		if(!playerStalled()) {
			
			soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Sword1.wav")));
			
			//add a stall and create the sword swing animation
			player.addStall(6);
			model.addAnimation(new PlayerSwing(player.getDirection()));
			
			//checks for collision with obstacles
			for(Obstacle obstacle : getArea().getObstacles()) {
				if(obstacle.isDestructible() && weaponCollision(player, obstacle)) {
					if(obstacle.didLootDrop()) getArea().addLoot(obstacle.lootDrop());
					obstacle.toggleDestroyed();
					model.addAnimation(obstacle);
				}
			}
			
			//checks for collision with enemies
			for(Enemy enemy : getArea().getEnemies()) {
				if(weaponCollision(player, enemy)) {
					// enemy was damaged
					if(!(enemy instanceof Boss) || !((Boss) enemy).shielded()) {
						soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Enemy_Hit.wav")));
						enemy.loseHP(player.getDamage());
						enemy.addStall(5);
					}// enemy wasn't damaged
					else {
						soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Sword_Tap.wav")));
					}
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
			playerPosition[1] = player.getLocation()[1] - player.getHeight() - player.getHitboxOffset() + 7;
		}
		//if west, square is west
		else if(player.getDirection() == 2) {
			playerPosition[0] = player.getLocation()[0] - player.getWidth() + 7;
			playerPosition[1] = player.getLocation()[1];
		}
		//if south, square is south
		else if(player.getDirection() == 3) {
			playerPosition[0] = player.getLocation()[0];
			playerPosition[1] = player.getLocation()[1] + player.getHeight() - 7;			
		}
		//else east, then square is east
		else {
			playerPosition[0] = player.getLocation()[0] + player.getWidth() - 7;
			playerPosition[1] = player.getLocation()[1];
		}
		
		return collision(playerPosition, obstacle);
	}

	/**
	 * sets player's direction to the passed in integer
	 * @param i either 1 for north, 2 for west, 3 for south, or 4 for east
	 */
	public void setPlayerDirection(int i) {
		player.setDirection(i);
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
	 * returns the current animations that need to be played as an arraylist of gameobjects form
	 * @return the current animations that need to be played as an arraylist of gameobjects
	 */
	public ArrayList<GameObject> getAnimations() {
		 return model.getAnimations();
	}

	/**
	 * performs a bow attack
	 */
	public void bowAttack() {
		//doesn't let us attack if we have attacked or been damaged recently
		if(!playerStalled()) {
			soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Arrow_Shoot.wav")));
			
			//add in the stall and the new bow shot animation
			player.addStall(5);
			model.addAnimation(new BowShot(player.getDirection()));
			
			//if we have arrows in inventory, add an arrow projectile to the screen and decrement the player's inventory of arrows
			if(player.getArrowQuantity() > 0) {
				getArea().addProjectile(new ArrowShot(player.getDirection(), getPlayerPosition()));
				player.decrementArrows();
			}
		}		
	}

	/**
	 * removes enemies that are dead from the map
	 */
	public void removeDeadEnemies() {
		CopyOnWriteArrayList<Enemy> dead = new CopyOnWriteArrayList<Enemy>();
		
		//gathers up all dead enemies
		for(Enemy enemy : getArea().getEnemies()) {
			if(enemy.isDead()) {
				soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Enemy_Kill.wav")));
				if(enemy.didLootDrop()) getArea().addLoot(enemy.lootDrop());
				dead.add(enemy);
				if(enemy instanceof ShieldPillar) {
					model.getCurrentArea().getBoss().removeShield();
				}
			}
		}
		
		//adds them to the animation list so we can play their death animation
		for(Enemy enemy : dead) {
			model.addAnimation(enemy);
		}
		
		//removes them from the area so they can't bother us anymore
		getArea().getEnemies().removeAll(dead);
		
	}

	/**
	 * returns the player's speed
	 * @return player's speed in pixels/tick as an integer
	 */
	public int getPlayerSpeed() {
		return player.getSpeed();
	}

	/**
	 * returns whether the player is stalled or not
	 * @return true if the player is stalled, false otherwise
	 */
	public boolean playerStalled() {
		return player.stalled();
	}

	/**
	 * checks for enemy collision with player character
	 */
	public void enemyAttack() {
		
		//for every enemy
		for(Enemy enemy : getArea().getEnemies()) {
			
			//if the enemy collides with the player
			if(collision(getPlayerPosition(), enemy)) {
				
				//if the player hasn't been damaged recently, stall him, decrement his hp, 
				//and set a field to store which the last enemy to hit him was to help with simpler knockback calculations
				if(!player.damaged()){
					enemy.addStall(2);
					player.toggleDamaged();
					soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Link_Hurt.wav")));
					player.loseHP(enemy.getDamage());
					player.addDamageStall(4);
					player.setLastEnemy(enemy);
				}
			}
		}	
	}

	/**
	 * updates the projectiles' position on the screen based on their speed
	 */
	public void updateProjectilePosition() {
		
		//iterate through the projectiles
		Iterator<Character> projectiles = getArea().getProjectiles().iterator();
		while(projectiles.hasNext()) {
			Character projectile = projectiles.next(); 
			int x = 0;
			int y = 0;
			
			if(!(projectile instanceof BossAttack)) {
			//switch tells us which way to move the arrow
				switch(projectile.getDirection()) {
					case 1:
						x = 0;
						y = -projectile.getSpeed();
						break;
					case 3:
						x = 0;
						y = projectile.getSpeed();
						break;
					case 2:
						y = 0;
						x = -projectile.getSpeed();
						break;
					case 4:
						y = 0;
						x = projectile.getSpeed();
						break;
					default:
						x = 0;
						y = 0;
				}
			}
			else {
				int[] target = ((BossAttack) projectile).getTarget().getLocation();
				x = projectile.getLocation()[0] > target[0] ? -projectile.getSpeed() : 0;
				x += projectile.getLocation()[0] < target[0] ? projectile.getSpeed() : 0;
				y = projectile.getLocation()[1] > target[1] ? -projectile.getSpeed() : 0;
				y += projectile.getLocation()[1] < target[1] ? projectile.getSpeed() : 0;
				
				//smooths out enemy movement if their speed is greater than the distance to the player's coordinate
				if(Math.abs(projectile.getLocation()[0] - target[0]) < Math.abs(x)) x = x > 0 ? 
						target[0] - projectile.getLocation()[0] : projectile.getLocation()[0] - target[0];
					if(Math.abs(projectile.getLocation()[1] - target[1]) < Math.abs(y)) y = y > 0 ?
							target[1] - projectile.getLocation()[1] : projectile.getLocation()[1] - target[1];
			}
			projectile.updatePosition(x, y);
			CopyOnWriteArrayList<GameObject> things = new CopyOnWriteArrayList<GameObject>();
			things.addAll(getArea().getObstacles());
			
			//check player attack projectile collision
			if(!(projectile instanceof BossAttack)) {
				//grab all of the things an arrow can collide with and iterate through them
				things.addAll(getArea().getEnemies());				
				for(GameObject obj : things) {
					
					//destroyed obstacles are pathable.
					if(obj instanceof Obstacle && ((Obstacle) obj).destroyed()) continue;
					
					//if we collide with an object, the projectile disappears
					if(projectileCollision(projectile, obj)) {
						if(!(obj instanceof Enemy)) soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Arrow_Hit.wav")));
						projectiles.remove();
						
						//if it was an enemy, they lose health and suffer a minor knockback.
						if(obj instanceof Enemy) {
							if(!(obj instanceof Boss) || !((Boss) obj).shielded()) {
								soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Arrow_Hit.wav")));
								((Enemy) obj).addStall(1);
								((Enemy) obj).loseHP(1); 
							}
							else {
								soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Sword_Tap.wav")));
							}
						}
						break;
					}
					
					//if the projectile goes off screen, remove it
					else if(projectile.getLocation()[0] > 999 || projectile.getLocation()[0] < 0 ||
							projectile.getLocation()[1] > 666 || projectile.getLocation()[1] < 0) {
						projectiles.remove();
						break;
					}
				}
			}
			
			//check boss attack projectile collision
			else {
				
				if (((BossAttack) projectile).getTimer() > 90){
					projectiles.remove();
					break;
				}
				
				things.add(player);
				for(GameObject obj : things) {
					if(projectileCollision(projectile, obj)){
						projectiles.remove();
						
						if(obj instanceof Player) {
							soundfx.add(new AudioClip(sformat("/soundfx/LTTP_Link_Hurt.wav")));
							((Player) obj).addDamageStall(1);
							((Player) obj).loseHP(1);
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * checks projectile collision
	 * @param projectile the projectile we are checking
	 * @param obj the object against which we are checking for collision
	 * @return true if the projectile and the object collide, false otherwise.
	 */
	private boolean projectileCollision(Character projectile, GameObject obj) {
		//return true if the projectile rectangle overlaps the obstacle rectangle.
		
		int[] position = projectile.getLocation();
		if(position[0] <= obj.getLocation()[0] + obj.getWidth() && 
				position[0] + projectile.getWidth() >= obj.getLocation()[0] &&
				position[1] + projectile.getHeight() <= obj.getLocation()[1] + obj.getHeight() && 
				position[1] + projectile.getHeight() >= obj.getLocation()[1] + obj.getTopHeight()) {
			return true;
		}
		return false;
	}

	/**
	 * returns true if the player is in a dungeon, false otherwise.
	 * @return true if the player is in a dungeon, false otherwise.
	 */
	public boolean inDungeon() {
		// TODO Auto-generated method stub
		return model.inDungeon();
	}

	/**
	 * returns the Dungeon's GameMap
	 * @return the dungeon's GameMap
	 */
	public GameMap getDungeonMap() {
		return model.getDungeonMap();
	}

	/**
	 * returns the overland GameMap
	 * @return the overland GameMap
	 */
	public GameMap getOverlandMap() {
		return model.getOverlandMap();
	}

	/**
	 * returns a list of soundfx that need played
	 * @return an arraylist of audioclips
	 */
	public ArrayList<AudioClip> getSoundFX() {
		return soundfx;
	}
	
	/**
	 * formats a passed in string as a URL string to be
	 * used in getting audio resources
	 * @param s the string representing the audio clip's path relative to the project src folder
	 * @return the url string representing the string's location
	 */
	private String sformat(String s) {
		URL url = GameController.class.getResource(s);
		return url.toString();
	}
	
	/**
	 * getter for the GameModel
	 * @return the GameModel
	 */
	public GameModel getModel() {
		return model;
	}

	public void setModel(GameModel model2) {
		this.model = model2;		
	}
}
