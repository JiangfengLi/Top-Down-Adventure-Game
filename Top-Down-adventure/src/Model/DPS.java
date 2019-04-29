package Model;

/**
 * fills fields specific to DPS type enemies
 * @author Wes Rodgers
 *
 */
public class DPS extends Enemy {
	private static final long serialVersionUID = 1L;

	public DPS(int x, int y) {		
		damage = 2;
		currentHP = 2;
		maxHP = 2;
		speed = 5;		
		location[0] = x;
		location[1] = y;
		width = 30;
		height = 30;
		hitbox = new int[2];
		hitboxHeight = 30;
		hitboxWidth = 30;
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
