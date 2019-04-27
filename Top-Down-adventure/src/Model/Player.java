package Model;

import javafx.scene.image.Image;

/**
 * Class for the player character, stores things about him/her
 * 
 * @author Wes Rodgers
 *
 */
public class Player extends Character{
	
	private int arrowQuantity = 30;
	private boolean smallKey = false;
	private boolean bossKey = false;
	private boolean damaged = false;
	private Enemy lastEnemy;
	private int buffTimer;
	private boolean buffed = false;
	private int damageStall = 0;
	
	/**
	 * Constructor. Initializes the player character with 3 max HP and 3 current HP. This can be changed
	 * to fit whatever we actually decide on.
	 */
	public Player() {
		this.currentHP = 6;
		this.maxHP = 6;
		damage = 1;
		direction = 3;
		speed = 8;
		width = 49;
		height = 49;
		location = new int[2];
		location[0] = 100;
		location[1] = 100;
		imageArray[0] = new Image("/style/playerSprites/link north.png");
		imageArray[1] = new Image("/style/playerSprites/link left.png");
		imageArray[2] = new Image("/style/playerSprites/Link south.png");
		imageArray[3] = new Image("/style/playerSprites/link right.png");
		hitbox = new int[2];
		hitbox[0] = 100;
		hitbox[1] = 125;
		hitboxWidth = 30;
		hitboxHeight = 25;
		
	}	
	
	/**
	 * Getter for arrowQuantity
	 * 
	 * @return the number of arrows in the player's inventory
	 */
	public int getArrowQuantity() {
		return arrowQuantity;
	}
	
	/**
	 * increases the player's arrowQuantity, max of 30 at one time.
	 * 
	 * @param quantity number of arrows to add to the inventory
	 */
	public void addArrows(int quantity) {
		arrowQuantity = arrowQuantity + quantity > 30 ? 30 : arrowQuantity + quantity;
	}
	
	/**
	 * adds a damage stall
	 * @param i the amount to add
	 */
	public void addDamageStall(int i) {
		damageStall += i;
	}
	
	/**
	 * decrements the damage stall
	 */
	public void decrementDamageStall() {
		damageStall --;
	}
	
	/**
	 * returns the damage stall
	 * @return the damage stall
	 */
	public int getDamageStall() {
		return damageStall;
	}
	
	/**
	 * getter for smallKey
	 * @return true if the player has found the small key, false otherwise
	 */
	public boolean hasKey() {
		return smallKey;
	}
	
	/**
	 * getter for bossKey
	 * 
	 * @return true if the player has the bossKey, false otherwise
	 */
	public boolean hasBossKey() {
		return bossKey;
	}

	/**
	 * returns true if the player has been recently damaged, false otherwise
	 * @return true if the player has been recently damaged, false otherwise
	 */
	public boolean damaged() {
		return damaged;
	}
	
	/**
	 * toggles the player's damaged boolean
	 */
	public void toggleDamaged() {
		damaged = !damaged;
	}
	
	/**
	 * returns the last enemy to hit the player
	 * @return the last enemy that damaged the player
	 */
	public Enemy lastEnemy() {
		return lastEnemy;
	}
	
	/**
	 * sets the last enemy to hit the player
	 * @param enemy the last enemy to damage the player
	 */
	public void setLastEnemy(Enemy enemy) {
		lastEnemy = enemy;
	}

	/**
	 * reduces the player's arrow quantity by 1
	 */
	public void decrementArrows() {
		arrowQuantity--;		
	}
	
	/**
	 * adds time to the player's speed buff
	 * @param i the number of ticks to add to the buff timer
	 */
	public void addBuff(int i) {
		if(!buffed) {
			buffed = true;
			speed = speed*2;
		}
		buffTimer += i;		
	}
	
	/**
	 * decrements the buffTimer by one.
	 */
	public void decrementBuff() {
		buffTimer--;
		if(buffTimer == 0) {
			speed = 8;
			buffed  = false;
		}
	}

	/** 
	 * returns true when the player is buffed, false otherwise.
	 * @return true when the player is buffed, false otherwise.
	 */
	public boolean buffed() {
		return buffed;
	}

	/**
	 * gives the player the boss key
	 */
	public void giveBossKey() {
		bossKey = true;
	}

	/**
	 * gives the player the regular key
	 */
	public void giveKey() {
		smallKey = true;
	}

	/**
	 * removes the bosskey from the player's inventory
	 */
	public void removeBossKey() {
		bossKey = false;		
	}
	
	/**
	 * updates just the characters x coordinate
	 * @param x the amount to update the x coord by.
	 */
	public void updateX(int x) {
		location[0] += x;
	}
	
	/**
	 * updates the player's y coordinate
	 * @param y the amount to update the y coord by.
	 */
	public void updateY(int y) {
		location[1] += y;
	}
}
