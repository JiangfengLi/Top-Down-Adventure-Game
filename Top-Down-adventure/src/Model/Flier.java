package Model;

/**
 * Extends the enemy class, this enemy can fly over obstacles.
 * @author Wes Rodgers
 *
 */
public class Flier extends Enemy {
	
	public Flier() {
		
	}

	@Override
	public boolean playerIsVisible() {
		return true;
	}

}
