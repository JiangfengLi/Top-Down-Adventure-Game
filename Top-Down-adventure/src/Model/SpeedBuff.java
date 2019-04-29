package Model;

/**
 * Loot item that increases the player's speed, provides the necessary fields
 * @author Wes Rodgers
 *
 */
public class SpeedBuff extends Item{
	private static final long serialVersionUID = 1L;

	public SpeedBuff(int[] location) {
		this.height = 35;
		this.width = 40;
		this.location = location;
	} 
	
}
