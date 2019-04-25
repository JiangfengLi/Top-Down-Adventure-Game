package Model;

import javafx.scene.image.Image;

public class Key extends Item {

	private boolean isBoss;
	
	public Key(int[] location, boolean boss) {
		imageFile = new Image(boss ? "/style/bosskey.png" : "/style/key.png");
		this.location = location;
		this.height = 16;
		this.width = 14;
		this.isBoss = boss;
	}
	
	public boolean isBossKey() {
		return isBoss;
	}
}
