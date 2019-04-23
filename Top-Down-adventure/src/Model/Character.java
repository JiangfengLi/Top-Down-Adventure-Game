package Model;

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
	
	//direction is as follows: 1 is up, 2 is left, 3 is down, 4 is right.
	protected int direction;
	
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
	 * gets the character's speed
	 * @return the character's speed factor
	 */
	public int getSpeed() {
		return speed;
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
}
