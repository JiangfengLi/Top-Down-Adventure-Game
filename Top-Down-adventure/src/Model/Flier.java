package Model;

/**
 * Extends the enemy class, this enemy can fly over obstacles.
 * @author Wes Rodgers
 *
 */
public class Flier extends Enemy {
	
	public Flier() {
		direction = 3;
		currentHP = 1;
		maxHP = 1;
		damage = 1;
		speed = 5;
	}

	/**
	 * returns whether the enemy can see the player
	 * @return true, fliers don't collide with objects
	 */
	@Override
	public boolean playerIsVisible() {
		return true;
	}

}
