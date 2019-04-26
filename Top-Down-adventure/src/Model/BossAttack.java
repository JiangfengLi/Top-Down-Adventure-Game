package Model;

import javafx.scene.image.Image;

/**
 * Projectile class for boss' attacks.
 * @author Wes Rodgers
 *
 */
public class BossAttack extends Character {
	public int timer = 0;
	public Player target;
	
	public BossAttack(Player player, int[] location) {
		target = player;
		hitbox = new int[2];
		this.location[0] = location[0];
		this.location[1] = location[1];
		this.oldLocation[0] = location[0];
		this.oldLocation[1] = location[1];
		speed = 5;
		direction = 3;
		
		imageArray[0] = new Image("/style/fireball.png");
		imageArray[1] = new Image("/style/fireball.png");
		imageArray[2] = new Image("/style/fireball.png");
		imageArray[3] = new Image("/style/fireball.png");
		
		//change these as appropriate once we have the arrow .png file
		height = 25;
		width = 25;
	}
	
	/**
	 * increments the attack's timer and returns the timer depicting how long it has been on screen
	 * @return the number of times this method has been called. 
	 */
	public int getTimer() {
		timer++;
		return timer;
	}
	
	/**
	 * returns the target this projectile is moving towards
	 * @return the target this projectile is homing in on
	 */
	public Player getTarget() {
		return target;
	}
}
