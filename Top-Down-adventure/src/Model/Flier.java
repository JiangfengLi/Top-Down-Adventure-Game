package Model;

import javafx.scene.image.Image;

/**
 * Extends the enemy class, this enemy can fly over obstacles.
 * @author Wes Rodgers
 *
 */
public class Flier extends Enemy {
	
	public Flier(int x, int y) {
		currentHP = 1;
		maxHP = 1;
		damage = 1;
		speed = 3; 
		
		location[0] = x;
		location[1] = y;
		width = 30;
		height = 30;
		hitbox = new int[2];
		hitboxHeight = 30;
		hitboxWidth = 30;
		direction = 3;
		imageArray[0] = new Image("/style/flier image.png");
		imageArray[1] = new Image("/style/flier image.png");
		imageArray[2] = new Image("/style/flier image.png");
		imageArray[3] = new Image("/style/flier image.png");
		topHeight = 0;
		idleImage = new Image("/style/flier idle.png");
		active = false;
		scaredyCat = false;
		Arrow arrowDrop = new Arrow(location);
		drops.put(0, arrowDrop);
		lootChance = 100;
	}

}
