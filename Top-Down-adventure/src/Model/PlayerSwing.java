package Model;

import javafx.scene.image.Image;

public class PlayerSwing extends Character {
	
	public PlayerSwing(int direction) {
		imageArray[0] = new Image("/style/playerSprites/sword north.png");
		imageArray[1] = new Image("/style/playerSprites/sword left.png");
		imageArray[2] = new Image("/style/playerSprites/sword south.png");
		imageArray[3] = new Image("/style/playerSprites/sword right.png");
		this.direction = direction;
		width = 75;
		height = 73;
	} 

}
