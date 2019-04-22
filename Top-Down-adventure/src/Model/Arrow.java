package Model;

/**
 * extends item, this is a possible drop from enemies
 * and replenishes the player's stock of arrows.
 * @author Wes Rodgers
 *
 */
public class Arrow extends Item {
	
	public int quantity;
	
	public Arrow(int[] location) {
		this.imageFile = "";
		this.quantity = System.nanoTime()%3 == 0 || System.nanoTime()%3 == 1 ? 5 : 10;		
		this.location = location;
	}
	
	/**
	 * getter for arrow quantity
	 * @return the number of arrows in this drop
	 */
	public int getQuantity() {
		return quantity;
	}
}
