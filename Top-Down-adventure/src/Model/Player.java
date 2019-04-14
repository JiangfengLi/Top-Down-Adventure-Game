package Model;

/**
 * Class for the player character, stores things about him/her
 * 
 * @author Wes Rodgers
 *
 */
public class Player extends Character{

	/**
	 * Constructor. Initializes the player character with 3 max HP and 3 current HP. This can be changed
	 * to fit whatever we actually decide on.
	 */
	public Player() {
		super();
		this.currentHP = 3;
		this.maxHP = 3;
	}
	
	
}
