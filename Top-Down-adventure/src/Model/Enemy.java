package Model;

import java.util.HashMap;

/**
 * Abstract class for enemies. Provides methods and fields that all types
 * of enemies will need
 * 
 * @author Wes Rodgers
 *
 */
public abstract class Enemy extends Character{

	protected HashMap<Item, Float> drops;
	protected String idleImage;
	protected boolean active;
	protected boolean scaredyCat;
	
	/**
	 * Getter for the enemy's drop table
	 * 
	 * @return a HashMap mapping the item as a key and a float representing drop chance as the value
	 */
	public HashMap<Item, Float> getDrops() {
		return this.drops;
	}

	/**
	 * returns the path to an image of the enemy's idle animation
	 * @return a string representing the filepath to an enemy's idle animation
	 */
	public String getIdleImage() {
		return idleImage;
	}
	
	/**
	 * Method stub for determining whether the
	 * enemy has a straight line path to the player
	 * without any collision objects in the way
	 * @return
	 */
	public abstract boolean playerIsVisible();

	/**
	 * returns whether the enemy is active
	 * @return true when the enemy needs to chase after the player, false otherwise
	 */
	public boolean active() {
		return active;
	}
	
	/**
	 * tells the enemy to activate and chase a player
	 */
	public void activate() {
		active = true;
	}

	/**
	 * sets the enemy to either be active or inactive based on the passed in boolean
	 * @param b true if the enemy needs to be active, false otherwise
	 */
	public void setActive(boolean b) {
		active = b;		
	}

	/**
	 * returns true if the enemy is a scaredy cat, false otherwise
	 * @return true if the enemy will flee under certain circumstances, false otherwise
	 */
	public boolean willFlee() {
		return scaredyCat;
	}
}
