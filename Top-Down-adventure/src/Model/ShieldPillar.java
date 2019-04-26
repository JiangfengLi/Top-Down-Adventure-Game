package Model;

import javafx.scene.image.Image;

/**
 * Enemy class with the fields for the main boss' shield pillar
 * @author Wes Rodgers
 *
 */
public class ShieldPillar extends Enemy{
	
	public ShieldPillar(int[] location) {
		active = true;
		imageArray[0] = new Image("/style/shield gen.png");
		imageArray[1] = new Image("/style/shield gen.png");
		imageArray[2] = new Image("/style/shield gen.png");
		imageArray[3] = new Image("/style/shield gen.png");
		direction = 3;
		this.location = location;
		hitbox = location;
		scaredyCat = false;
		speed = 0;
		damage = 0;
		width = 30;
		height = 30;
		hitboxHeight = 30;
		currentHP = 2;
		maxHP = 2;
	}
}
