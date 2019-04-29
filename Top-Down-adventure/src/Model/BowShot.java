package Model;

/**
 * Projectile object to be drawn on screen. Acts like a character to help
 * depict directionality
 * @author Wes Rodgers
 *
 */
public class BowShot extends Character {
	private static final long serialVersionUID = 1L;

	public BowShot(int direction) {
		this.direction = direction;
		width = 54;
		height = 73;
	} 
}
