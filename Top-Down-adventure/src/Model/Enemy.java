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

	private HashMap<Item, Float> drops;
	
	/**
	 * Getter for the enemy's drop table
	 * 
	 * @return a HashMap mapping the item as a key and a float representing drop chance as the value
	 */
	public HashMap<Item, Float> getDrops() {
		return this.drops;
	}
}
