package Model;

public class ArrowShot extends Character {

	public ArrowShot(int direction, int[] location) {
		hitbox = new int[2];
		this.location[0] = location[0];
		this.location[1] = location[1];
		this.oldLocation[0] = location[0];
		this.oldLocation[1] = location[1];
		speed = 15;
		this.direction = direction;
		
		imageArray[0] = "/style/arrow up.png";
		imageArray[1] = "/style/arrow left.png";
		imageArray[2] = "/style/arrow down.png";
		imageArray[3] = "/style/arrow right.png";
		
		//change these as appropriate once we have the arrow .png file
		height = 30;
		width = 30;
	} 

}
