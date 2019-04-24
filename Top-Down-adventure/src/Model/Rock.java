package Model;

import javafx.scene.image.Image;

/**
 * extends obstacle, this is a small, indestructible,
 * collision causing obstacle.
 * @author Wes Rodgers
 *
 */
public class Rock extends Obstacle {

	public Rock(int x, int y) {
		imageFile = new Image("/style/rock.png");
		destructible = false;
		width = 50;
		height = 50;
		location[0] = x;
		location[1] = y;
		topImage = false;
		topHeight = 0;
	} 
}
