package Model;

/**
 * Enemy class with the fields for the main boss' shield pillar
 * @author Wes Rodgers
 *
 */
public class ShieldPillar extends Enemy{
	private static final long serialVersionUID = 1L;

	public ShieldPillar(int[] location) {
		active = true;
		direction = 3;
		this.location = location;
		hitbox = location;
		scaredyCat = false;
		speed = 0;
		damage = 0;
		width = 30;
		height = 30;
		hitboxHeight = 30;
		currentHP = 2;
		maxHP = 2;
	}
}
