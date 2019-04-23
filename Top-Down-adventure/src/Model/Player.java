package Model;

/**
 * Class for the player character, stores things about him/her
 * 
 * @author Wes Rodgers
 *
 */
public class Player extends Character{
	
	private int arrowQuantity = 3;
	private boolean smallKey = false;
	private boolean bossKey = false;
	private String imageArray[] = new String[4]; 
	
	/**
	 * Constructor. Initializes the player character with 3 max HP and 3 current HP. This can be changed
	 * to fit whatever we actually decide on.
	 */
	public Player() {
		this.currentHP = 3;
		this.maxHP = 3;
		direction = 3;
		speed = 5;
		width = 50;
		height = 50;
		location = new int[2];
		location[0] = 100;
		location[1] = 100;
		imageArray[0] = "/style/playerSprites/link north.png";
		imageArray[1] = "/style/playerSprites/link left.png";
		imageArray[2] = "/style/playerSprites/Link south.png";
		imageArray[3] = "/style/playerSprites/link right.png";
		hitbox = new int[3];
		hitbox[0] = 25;
		hitbox[1] = 30;
		hitbox[2] = 25;		
	}	
	
	/**
	 * Getter for arrowQuantity
	 * 
	 * @return the number of arrows in the player's inventory
	 */
	public int getArrowQuantity() {
		return arrowQuantity;
	}
	
	/**
	 * increases the player's arrowQuantity, max of 30 at one time.
	 * 
	 * @param quantity number of arrows to add to the inventory
	 */
	public void addArrows(int quantity) {
		arrowQuantity = arrowQuantity + quantity > 30 ? 30 : arrowQuantity + quantity;
	}
	
	/**
	 * getter for smallKey
	 * @return true if the player has found the small key, false otherwise
	 */
	public boolean hasKey() {
		return smallKey;
	}
	
	/**
	 * getter for bossKey
	 * 
	 * @return true if the player has the bossKey, false otherwise
	 */
	public boolean hasBossKey() {
		return bossKey;
	}

	public String[] getImageArray() {
		// TODO Auto-generated method stub
		return imageArray;
	}
}
