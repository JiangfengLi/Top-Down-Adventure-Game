package Model;

/**
 * Class for the game boss
 * @author Wes Rodgers
 *
 */
public class Boss extends Enemy{
	private static final long serialVersionUID = 1L;
	boolean mainBoss;
	private boolean shielded = false;
	private boolean preAttack = false;
	private int attackTimer = 0;
	private int shieldTimer = 45;
	private int totalAttacks = 0;
	private int bossTimer = 0;
	private int[] shieldLocation = new int[2];
	
	//note, I could have made Boss abstract and extended that to main/mini bosses, but 
	//there's no real need to make it that robust for a game with only 2 bosses.
	public Boss(int x, int y, boolean finalBoss) {
		mainBoss = finalBoss;
		
		currentHP = mainBoss ? 20 : 12;
		maxHP = mainBoss ? 20 : 12;
		damage = 1;
		speed = mainBoss ? 5 : 7;
		location[0] = x;
		location[1] = y;
		shieldLocation[0] = 464;
		shieldLocation[1] = 372;
		
		width = 100;
		height = finalBoss ? 84 : 39;
		hitbox = new int[2];
		hitboxHeight = finalBoss ? 100 : 100;
		hitboxWidth = finalBoss ? 84 : 39;
		direction = 3;
		topHeight = 0;
		active = false;
		scaredyCat = false;
		if(!mainBoss) drops.put(0, new Key(this.location, true));
		else drops.put(0, new Heart(this.location));
		lootChance = 100;
	}
	
	/**
	 * returns true when this is the main boss, false when it is a mini boss
	 * @return true when this is the main boss, false when it is a mini boss
	 */
	public boolean isMainBoss() {
		return mainBoss;
	}

	/**
	 * removes the boss' shield
	 */
	public void removeShield() {
		shielded = false;
	}
	
	/**
	 * returns true when the boss is shielded, false otherwise
	 * @return true when the boss is shielded, false otherwise
	 */
	public boolean shielded() {
		return shielded ;
	}
	
	/**
	 * adds a shield to the boss
	 */
	public void addShield() {
		shielded = true;
	}
	
	/**
	 * returns true when the boss is preparing to attack, false otherwise
	 * @return true when the boss is preparing to attack, false otherwise
	 */
	public boolean preAttack() {
		return preAttack;
	}
	
	/**
	 * tells the boss to enter their preattack phase
	 */
	public void addPreAttack() {
		preAttack = true;
	}

	/**
	 * keeps track of how many times the boss has attacked in this attack phase and turns preattack
	 * off when it hits 4. Keeps track of when the boss needs to attack by incrementing its own timer
	 * every time it is called.
	 * Returns true when it is time for the boss to attack, false otherwise.
	 * @return true when it is time for the boss to attack, false otherwise.
	 */
	public boolean timeToAttack() {
		if(attackTimer > 50) {
			attackTimer = 0;
			totalAttacks++;
			if(totalAttacks  >= 4) {
				totalAttacks = 0;
				preAttack = false;
			}
			return true;
		}
		else {
			attackTimer++;
			return false;
		}
	}


	/**
	 * decrements a timer for the boss' shield mechanic, returns true when it is time for the boss to shield.
	 * @return true when it is time for the boss to shield, false otherwise
	 */
	public boolean timeToShield() {
		if(shieldTimer <= 0) {
			shieldTimer = 300;
			return true;
		}
		else {
			shieldTimer --;
			return false;
		}
	}

	/**
	 * keeps track of an implements a separate gameclock unique to the boss. Can be used to
	 * set behavioral options based on the tick.
	 * @return the number of times this method has been called.
	 */
	public int getBossTimer() {
		bossTimer ++;
		return bossTimer;
	}

	/**
	 * returns the location that the boss' shield pillar needs to spawn
	 * @return the location that the boss' shield pillar needs to spawn.
	 */
	public int[] getPillar() {
		return shieldLocation;
	}
}
