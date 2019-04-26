package Model;

import javafx.scene.image.Image;

public class Shield extends Item{
	private int protect;
	
	public Shield(int[] location) {
		this.imageFile = new Image("");
		this.height = 50;
		this.width = 50;
		this.location = location;
		protect = 1;
	} 
	
  /**
   * Return the speedy that SpeedBuff increases.
   * @return the integer of speedy it increases.
   */
	public int getProtection() {
		return protect;
	}

}
