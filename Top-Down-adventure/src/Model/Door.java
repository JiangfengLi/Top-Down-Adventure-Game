package Model;


/**
 * Provides the fields for a door object
 * @author Wes Rodgers
 *
 */
public class Door extends Obstacle {
	private static final long serialVersionUID = 1L;

	public Door(int x, int y){
		destructible = false;
		width = 100;
		height = 66;
		location[0] = x;
		location[1] = y;
		topImage = false;
		topHeight = 0;
	}
}
