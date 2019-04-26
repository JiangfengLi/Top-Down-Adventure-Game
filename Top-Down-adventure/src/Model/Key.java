package Model;

import javafx.scene.image.Image;

public class Key extends Item {

	private boolean isBoss;
	
	public Key(int[] location, boolean boss) {
		imageFile = new Image(boss ? "" : "");
		this.location = location;
		this.height = 0;
		this.width = 0;
		this.isBoss = boss;
	}
	
	public boolean isBossKey() {
		return isBoss;
	}
}
