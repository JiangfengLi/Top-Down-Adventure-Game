package Model;

import javafx.scene.image.Image;

/**
 * Abstract class for characters that can move and do damage
 * @author Wes Rodgers
 *
 */
public abstract class Character extends GameObject{

	protected int currentHP;
	protected int maxHP;
	protected int damage;
	protected int speed;
	protected int[] hitbox;
	protected int hitboxWidth;
	protected int hitboxHeight;
	protected Image imageArray[] = new Image[4];
	protected int hitboxOffset;
	private int stallTime = 0;
	private boolean stalled = false; 
	
	//direction is as follows: 1 is up, 2 is left, 3 is down, 4 is right.
	protected int direction;
	
	/**
	 * returns true when the character is in a stall, false otherwise
	 * @return true when the character is in a stall, false otherwise
	 */
	public boolean stalled() {
		return stalled;
	}
	
	/**
	 * adds a stall to this character
	 * @param i the number of ticks that the stall should last for
	 */
	public void addStall(int i) {
		stalled = true;
		stallTime += i;
	}
	
	/**
	 * returns the length of time remaining on the stall
	 * @return the length of time remaining on the stall
	 */
	public int getStallTime() {
		return stallTime;
	}
	
	/** decrements the stall time by 1, unstalls
	 * the character once it hits 0
	 */
	public void decrementStall() {
		stallTime--;
		if(stallTime == 0) {
			stalled = false;
		}
	}
	
	/**
	 * returns the hitbox offset
	 * @return the hitbox offset
	 */
	public int getHitboxOffset() {
		return hitboxOffset;
	}
	
	/**
	 * returns the coordinates of the character's last valid location
	 * @return
	 */
	public int[] getOldLocation() {
		return oldLocation;
	}
	
	/**
	 * returns the images for the character's movement
	 * @return an array of Strings where each string is the filepath to an image for the characters movement in a direction
	 */
	public Image[] getImageArray() {
		// TODO Auto-generated method stub
		return imageArray;
	}
	
	/**
	 * returns the player's max HP
	 * @return the player's max HP
	 */
	public int getMaxHP() {
		return maxHP;
	}
	
	/**
	 * getter for hitbox array
	 * 
	 * @return the hitbox for the character as {y_offset, width, height}
	 */
	public int[] getHitbox() {
		return hitbox;
	}
	
	/**
	 * Getter for current HP
	 * 
	 * @return the players current HP
	 */
	public int getHP() {
		return this.currentHP;
	}
	
	/**
	 * Adds health to the player's current HP
	 * 
	 * @param amount the amount of health to be restored
	 */
	public void addHP(int amount) {
		currentHP = currentHP + amount >= maxHP ? maxHP : currentHP + amount;
	}
	
	/**
	 * Subtracts health from the player's current HP
	 * 
	 * @param amount the amount of health the player loses
	 */
	public void loseHP(int amount) {
		currentHP = currentHP - amount <= 0 ? 0 : currentHP - amount;
	}
	
	/**
	 * Boolean check for whether the player is dead or not
	 * 
	 * @return true if currentHP == 0, false otherwise
	 */
	public boolean isDead() {
		return currentHP == 0;
	}
	
	/**
	 * returns the direction the character is facing
	 * @return 1 if the character is facing north, 2 if west, 3 if south, 4 if east.
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * sets the direction the character is facing
	 * @param direction 1 if north, 2 if west, 3 if south, 4 if east.
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * updates the object's position on the board
	 * @param d the amount to increment/decrement x coordinate by
	 * @param e the amount to increment/decrement y coordinate by
	 */
	public void updatePosition(double x, double y) {
		
		if (!stalled){
			oldLocation[0] = location[0];
			oldLocation[1] = location[1];
		}
		location[0] += x;
		location[1] += y;
		
		hitbox[0] = location[0];
		hitbox[1] = location[1] + height - hitboxHeight;
	}
	
	/**
	 * sets the object's anchor point to a specific location
	 * 
	 * @param x the object's x coordinate
	 * @param y the object's y coordinate
	 */
	public void setLocation(int x, int y) {
		
		oldLocation[0] = location[0];
		oldLocation[1] = location[1];
		
		location[0] = x;
		location[1] = y;
		
		hitbox[0] = location[0];
		hitbox[1] = location[1] + height - hitboxHeight;		
	}
	
	/**
	 * gets the character's speed
	 * @return the character's speed factor
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * increments the players speed per tick by the passed in value
	 * @param i the amount to increment the player's speed by
	 */
	public void setSpeed(int i) {
		speed += i;
	}
	
	/**
	 * gets the character's damage per attack
	 * @return the character's damage per attack
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * sets the character's damage per attack
	 * @param damge the damage we want the character to have
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * getter for hitbox width
	 * @return hitbox width
	 */
	public int getHitboxWidth() {
		return hitboxWidth;
	}
	
	/**
	 * getter for hitbox height
	 * @return hitbox height
	 */
	public int getHitboxHeight() {
		return hitboxHeight;
	}
}
