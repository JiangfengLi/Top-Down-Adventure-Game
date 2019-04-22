package Model;

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
		imageArray[0] = "/style/tank north.png";
		imageArray[1] = "/style/tank left.png";
		imageArray[2] = "/style/tank down.png";
		imageArray[3] = "/style/tank right.png";
		direction = 3;
		topHeight = 0;
		idleImage = "/style/tank idle.png";
		active = false;
		scaredyCat = false;
	}

	/**
	 * returns whether the tank can see the player or not
	 */
	@Override
	public boolean playerIsVisible() {
		// TODO Auto-generated method stub
		return false;
	}

}
