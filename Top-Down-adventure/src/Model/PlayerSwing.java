package Model;


/**
 * Animation character for player sword swings, with directionality.
 * @author Wes Rodgers
 *
 */
public class PlayerSwing extends Character {
	private static final long serialVersionUID = 1L;

	public PlayerSwing(int direction) {
		this.direction = direction;
		width = 75;
		height = 73;
	} 

}
