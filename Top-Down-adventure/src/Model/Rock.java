package Model;

/**
 * extends obstacle, this is a small, indestructible,
 * collision causing obstacle.
 * @author Wes Rodgers
 *
 */
public class Rock extends Obstacle {
	private static final long serialVersionUID = 1L;

	public Rock(int x, int y) {
		destructible = false;
		width = 50;
		height = 50;
		location[0] = x;
		location[1] = y;
		topImage = false;
		topHeight = 0;
	} 
}
