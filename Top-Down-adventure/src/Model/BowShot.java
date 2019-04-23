package Model;

public class BowShot extends Character {

	public BowShot(int direction) {
		imageArray[0] = "/style/playerSprites/bow north.png";
		imageArray[1] = "/style/playerSprites/bow left.png";
		imageArray[2] = "/style/playerSprites/bow south.png";
		imageArray[3] = "/style/playerSprites/bow right.png";
		this.direction = direction;
		width = 54;
		height = 73;
	} 
}
