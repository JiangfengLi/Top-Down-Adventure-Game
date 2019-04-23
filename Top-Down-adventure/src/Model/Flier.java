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
		speed = 5; 
		
		location[0] = x;
		location[1] = y;
		width = 40;
		height = 74;
		hitbox = new int[2];
		hitboxHeight = 74;
		hitboxWidth = 40;
		imageArray[0] = new Image("/style/flier north.png");
		imageArray[1] = new Image("/style/flier left.png");
		imageArray[2] = new Image("/style/flier down.png");
		imageArray[3] = new Image("/style/flier right.png");
		direction = 3;
		topHeight = 0;
		idleImage = new Image("/style/flier idle.png");
		active = false;
		scaredyCat = false;
		Arrow arrowDrop = new Arrow(location);
		drops.put(0, arrowDrop);
		lootChance = 100;
	}

}
