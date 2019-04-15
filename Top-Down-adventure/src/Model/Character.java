package Model;

/**
 * Abstract class for characters that can move and do damage
 * @author Wes Rodgers
 *
 */
public abstract class Character extends GameObject{

	protected int currentHP;
	protected int maxHP;

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
}
