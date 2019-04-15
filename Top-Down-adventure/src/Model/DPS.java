package Model;

public class DPS extends Enemy {

	public DPS() {
		damage = 2;
		direction = 3;
		currentHP = 2;
		maxHP = 2;
		speed = 7;
	}
	
	/**
	 * returns whether the enemy has a direct path to the player
	 * @return true if there are no collidable objects between enemy and player, false otherwise.
	 */
	@Override
	public boolean playerIsVisible() {
		// TODO Auto-generated method stub
		return false;
	}

}
