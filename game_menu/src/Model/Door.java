package Model;

import javafx.scene.image.Image;

/**
 * Provides the fields for a door object
 * @author Wes Rodgers
 *
 */
public class Door extends Obstacle {
	
	public Door(int x, int y){
		imageFile = new Image("/style/door.png");
		destructible = false;
		width = 100;
		height = 66;
		location[0] = x;
		location[1] = y;
		topImage = false;
		topHeight = 0;
	}
}
