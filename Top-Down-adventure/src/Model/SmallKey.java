package Model;

import javafx.scene.image.Image;

public class SmallKey extends Key{
	
	public SmallKey(int[] location, boolean boss) {
		super(location, boss);
	}
	
	
	/**
	 * getter for arrow quantity
	 * @return the number of arrows in this drop
	 */
	public String toString() {
		return "SmallKey";
	} 

}
