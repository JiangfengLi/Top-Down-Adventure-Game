package Model;

/**
 * Extends obstacle, this is an indestructible, large,
 * collision causing obstacle.
 * @author Wes Rodgers
 *
 */
public class Tree extends Obstacle{

	public Tree() {
		imageFile = "/style/].png";
		destructible = false;
	}
}
