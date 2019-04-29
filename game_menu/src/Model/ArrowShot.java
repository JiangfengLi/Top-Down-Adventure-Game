package Model;

import javafx.scene.image.Image;

/**
 * projectile object to help show arrow movement across the screen, including directionality
 * @author Wes Rodgers
 *
 */
public class ArrowShot extends Character {

	public ArrowShot(int direction, int[] location) {
		hitbox = new int[2];
		this.location[0] = location[0];
		this.location[1] = location[1];
		this.oldLocation[0] = location[0];
		this.oldLocation[1] = location[1];
		speed = 15;
		this.direction = direction;
		
		imageArray[0] = new Image("/style/arrow up.png");
		imageArray[1] = new Image("/style/arrow left.png");
		imageArray[2] = new Image("/style/arrow down.png");
		imageArray[3] = new Image("/style/arrow right.png");
		
		height = 30;
		width = 30;
	} 
}
