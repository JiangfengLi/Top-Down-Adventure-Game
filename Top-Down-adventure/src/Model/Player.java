package Model;

public class Player {
	private int currentHP;
	private int maxHP;
	
	public Player() {
		currentHP = 3;
		maxHP = 3;
	}
	
	public int getCurrentHP() {
		return this.currentHP;
	}
	
	public void addHP(int amount) {
		currentHP = currentHP + amount >= maxHP ? maxHP : currentHP + amount;
	}
}
