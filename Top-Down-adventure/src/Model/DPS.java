package Model;

import javafx.scene.image.Image;

public class DPS extends Enemy {

	public DPS(int x, int y) {
		this.imageArray = new Image[4];
		
		damage = 2;
		currentHP = 2;
		maxHP = 2;
		speed = 5;
		
		location[0] = x;
		location[1] = y;
		width = 30;
		height = 30;
		hitbox = new int[2];
		hitboxHeight = 30;
		hitboxWidth = 30;
		direction = 3; 
		imageArray[0] = new Image("/style/dps north.png");
		imageArray[1] = new Image("/style/dps left.png");
		imageArray[2] = new Image("/style/dps south.png");
		imageArray[3] = new Image("/style/dps right.png");
		topHeight = 0;
		idleImage = new Image("/style/dps idle.png");
		active = false;
		scaredyCat = true;
		Arrow arrowDrop = new Arrow(location);
		drops.put(0, arrowDrop);
		lootChance = 100;
	}
}
