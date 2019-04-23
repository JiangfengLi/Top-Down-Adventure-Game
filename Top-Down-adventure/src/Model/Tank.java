package Model;

import javafx.scene.image.Image;

public class Tank extends Enemy{
	
	public Tank(int x, int y) {
		currentHP = 4;
		maxHP = 4;
		damage = 1;
		speed = 6;
		location[0] = x;
		location[1] = y;
		width = 40;
		height = 74;
		hitbox = new int[2];
		hitboxHeight = 74;
		hitboxWidth = 40;
		imageArray[0] = new Image("/style/tank north.png");
		imageArray[1] = new Image("/style/tank left.png");
		imageArray[2] = new Image("/style/tank down.png");
		imageArray[3] = new Image("/style/tank right.png");
		direction = 3;
		topHeight = 0;
		idleImage = new Image("/style/tank idle.png");
		active = false;
		scaredyCat = false;
		Arrow arrowDrop = new Arrow(location);
		drops.put(0, arrowDrop);
		lootChance = 100;
	} 
}
