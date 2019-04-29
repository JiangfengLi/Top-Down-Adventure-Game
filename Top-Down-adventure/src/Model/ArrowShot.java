package Model;

/**
 * projectile object to help show arrow movement across the screen, including directionality
 * @author Wes Rodgers
 *
 */
public class ArrowShot extends Character {
	private static final long serialVersionUID = 1L;

	public ArrowShot(int direction, int[] location) {
		hitbox = new int[2];
		this.location[0] = location[0];
		this.location[1] = location[1];
		this.oldLocation[0] = location[0];
		this.oldLocation[1] = location[1];
		speed = 15;
		this.direction = direction;	
		height = 30;
		width = 30;
	} 
}
