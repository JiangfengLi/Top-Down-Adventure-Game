package Model;

/**
 * extends item, this is a possible enemy drop
 * and restores player health.
 * @author Wes Rodgers
 *
 */
public class Heart extends Item {
	
	public Heart(int[] location) {
		this.imageFile = "";
		this.location = location;
	}
}
