package Model;

/**
 * Extends the enemy class, this enemy can fly over obstacles.
 * @author Wes Rodgers
 *
 */
public class Flier extends Enemy {
	
	public Flier() {
		
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
