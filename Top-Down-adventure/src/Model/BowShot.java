package Model;

import javafx.scene.image.Image;

/**
 * Projectile object to be drawn on screen. Acts like a character to help
 * depict directionality
 * @author Wes Rodgers
 *
 */
public class BowShot extends Character {

	public BowShot(int direction) {
		imageArray[0] = new Image("/style/playerSprites/bow north.png");
		imageArray[1] = new Image("/style/playerSprites/bow left.png");
		imageArray[2] = new Image("/style/playerSprites/bow south.png");
		imageArray[3] = new Image("/style/playerSprites/bow right.png");
		this.direction = direction;
		width = 54;
		height = 73;
	} 
}
