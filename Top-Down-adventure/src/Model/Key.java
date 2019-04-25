package Model;

import javafx.scene.image.Image;

public class Key extends Item {

	private boolean isBoss;
	private String name;
	
	public Key(int[] location, boolean boss) {
		imageFile = new Image(boss ? "" : "");
		this.location = location;
		this.height = 0;
		this.width = 0;
		this.isBoss = boss;
		this.name = (boss ? "BossKey" : "SmallKey");
	}
	
	public boolean isBossKey() {
		return isBoss;
	}

	/**
	 * getter for name of Key 
	 * @return the String of key name
	 */
	public String toString() {
		return this.name;
	} 

}
