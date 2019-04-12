package Model;

/**
 * Class for the player character, stores things about him/her
 * 
 * @author Wes Rodgers
 *
 */
public class Player {
	private int currentHP;
	private int maxHP;
	
	public Player() {
		currentHP = 3;
		maxHP = 3;
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
}
