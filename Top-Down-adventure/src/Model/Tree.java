package Model;

/**
 * Extends obstacle, this is an indestructible, large,
 * collision causing obstacle.
 * @author Wes Rodgers
 *
 */
public class Tree extends Obstacle{

	public Tree(int x, int y) {
		imageFile = "/style/Tree.png";
		destructible = false;
		width = 100;
		height = 100;
		location[0] = x;
		location[1] = y;
		topImage = true;
		topHeight = 50;
	}
}
