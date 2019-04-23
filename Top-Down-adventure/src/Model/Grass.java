package Model;

/**
 * Grass extends the Obstacle class, this is a destroyable
 * object, with collision until destroyed.
 * @author Wes Rodgers
 *
 */
public class Grass extends Obstacle{
	
	public Grass(int x, int y) {
		destructible = true;
		width = 50;
		height = 50;
		location = new int[2];
		location[0] = x;
		location[1] = y;
		imageFile = "/style/Grass and Cut.png";
		lastFrame = 9;
		
	}
}
