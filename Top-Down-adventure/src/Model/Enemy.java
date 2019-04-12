package Model;

/**
 * Abstract class for enemies. Provides methods and fields that all types
 * of enemies will need
 * 
 * @author Wes Rodgers
 *
 */
public abstract class Enemy {
	private int currentHP;
	private int maxHP;
	
	/**
	 * Getter for current HP
	 * 
	 * @return the enemy's current HP
	 */
	public int getHP() {
		return this.currentHP;
	}
	
	/**
	 * Adds health to the enemy's current HP
	 * 
	 * @param amount the amount of health to be restored
	 */
	public void addHP(int amount) {
		currentHP = currentHP + amount >= maxHP ? maxHP : currentHP + amount;
	}
	
	/**
	 * Subtracts health from the enemy's current HP
	 * 
	 * @param amount the amount of health the enemy loses
	 */
	public void loseHP(int amount) {
		currentHP = currentHP - amount <= 0 ? 0 : currentHP - amount;
	}
	
	/**
	 * Boolean check for whether the enemy is dead or not
	 * 
	 * @return true if currentHP == 0, false otherwise
	 */
	public boolean isDead() {
		return currentHP == 0;
	}
}
