package Model;

import javafx.scene.image.Image;

public class SpeedBuff extends Item{

	public SpeedBuff(int[] location) {
		this.imageFile = new Image("/style/speed buff.png");
		this.height = 35;
		this.width = 40;
		this.location = location;
	} 
	
}
