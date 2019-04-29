package Model;

/**
 * provides all of the fields necessary for a Tank enemy.
 * @author Wes Rodgers
 *
 */
public class Tank extends Enemy{
	private static final long serialVersionUID = 1L;

	public Tank(int x, int y) {
		currentHP = 4;
		maxHP = 4;
		damage = 1;
		speed = 4;
		location[0] = x;
		location[1] = y;
		width = 40;
		height = 74;
		hitbox = new int[2];
		hitboxHeight = 74;
		hitboxWidth = 40;
		direction = 3;
		topHeight = 0;
		active = false;
		scaredyCat = false;
		Arrow arrowDrop = new Arrow(location);
		Heart heartDrop = new Heart(location);
		SpeedBuff speedDrop = new SpeedBuff(location);
		drops.put(0, arrowDrop);
		drops.put(1, arrowDrop);
		drops.put(2 , arrowDrop);
		drops.put(3, heartDrop);
		drops.put(4, heartDrop);
		drops.put(5 , heartDrop);
		drops.put(6, speedDrop);
		lootChance = 30;
	} 
}
