package Model;

/**
 * Abstract class for Obstacles, which are impassable object on
 * the map
 * 
 * @author Wes Rodgers
 *
 */
public abstract class Obstacle extends GameObject {

	private boolean destructible;
	
	/**
	 * getter for the obstacle's destructible flag
	 * @return true if the obstacle is destructible, false otherwise
	 */
	public boolean isDestructible() {
		return destructible;
	}
}
