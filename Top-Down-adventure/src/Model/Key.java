package Model;

import javafx.scene.image.Image;

/**
 * Extends the item class and provides all of the fields for a key item
 * @author Wes Rodgers
 *
 */
public class Key extends Item {

	private boolean isBoss;
	private String name;
	
	public Key(int[] location, boolean boss) {
		imageFile = new Image(boss ? "/style/bosskey.png" : "/style/key.png");
		this.location = location;
		this.height = 16;
		this.width = 14;
		this.isBoss = boss;
		this.name = (boss ? "BossKey" : "SmallKey");
	}
	
	/**
	 * returns true if this is a bosskey, false otherwise.
	 * @return
	 */
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
