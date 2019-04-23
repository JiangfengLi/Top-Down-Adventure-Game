package Model;

/**
 * extends obstacle, this is a small, indestructible,
 * collision causing obstacle.
 * @author Wes Rodgers
 *
 */
public class Rock extends Obstacle {

	public Rock() {
		destructible = false;
	}
}
