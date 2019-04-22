package Model;

public class PlayerSwing extends Character {
	
	public PlayerSwing(int direction) {
		imageArray[0] = "/style/playerSprites/sword north.png";
		imageArray[1] = "/style/playerSprites/sword left.png";
		imageArray[2] = "/style/playerSprites/sword south.png";
		imageArray[3] = "/style/playerSprites/sword right.png";
		this.direction = direction;
		width = 75;
		height = 73;
	}

}
