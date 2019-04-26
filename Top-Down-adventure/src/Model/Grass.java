package Model;

import javafx.scene.image.Image;

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
		imageFile = new Image("/style/Grass and Cut.png");
		lastFrame = 9;
		topImage = false;
		topHeight = 0;
		drops.put(0, new Arrow(new int[] {location[0], location[1]}));
		lootChance = 100;
	} 
}
