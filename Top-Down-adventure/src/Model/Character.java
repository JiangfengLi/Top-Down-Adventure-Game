package Model;

public abstract class Character {

	protected int currentHP;
	protected int maxHP;
	protected int xPosition;
	protected int yPosition;

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
	 * updates the x coordinate of this character
	 * 
	 * @param xMovement the amount to move the character
	 */
	public void updateX(int xMovement) {
		xPosition += xMovement;
	}

	/**
	 * updates the y coordinate of this character
	 * 
	 * @param yMovement the amount to move the character
	 */
	public void updateY(int yMovement) {
		yPosition += yMovement;
	}
}
