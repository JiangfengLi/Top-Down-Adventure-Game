package Model;

import javafx.scene.image.Image;

/**
 * Loot item that increases the player's speed, provides the necessary fields
 * @author Wes Rodgers
 *
 */
public class SpeedBuff extends Item{

	public SpeedBuff(int[] location) {
		this.imageFile = new Image("/style/speed buff.png");
		this.height = 35;
		this.width = 40;
		this.location = location;
	} 
	
}
