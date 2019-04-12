package Model;

public abstract class Enemy {
	private int currentHP;
	private int maxHP;
	
	public int getHP() {
		return this.currentHP;
	}
	public void addHP(int amount) {
		currentHP = currentHP + amount >= maxHP ? maxHP : currentHP + amount;
	}
}
