package Model;

import javafx.scene.image.Image;

/**
 * Class for the game boss
 * @author Wes Rodgers
 *
 */
public class Boss extends Enemy{

	boolean mainBoss;
	private boolean shielded = false;
	private boolean preAttack = false;
	private int attackTimer = 0;
	private int shieldTimer = 0;
	private int totalAttacks = 0;
	private int bossTimer = 0;
	private int[] shieldLocation = new int[2];
	
	//note, I could have made Boss abstract and extended that to main/mini bosses, but 
	//there's no real need to make it that robust for a game with only 2 bosses.
	public Boss(int x, int y, boolean finalBoss) {
		imageArray[0] = new Image(finalBoss ? "/style/boss.png" : "/style/miniboss.png");
		imageArray[1] = new Image(finalBoss ? "/style/boss.png" : "/style/miniboss.png");
		imageArray[2] = new Image(finalBoss ? "/style/boss.png" : "/style/miniboss.png");
		imageArray[3] = new Image(finalBoss ? "/style/boss.png" : "/style/miniboss.png");
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
		idleImage = new Image(finalBoss ? "/style/boss.png" : "/style/miniboss.png");
		active = false;
		scaredyCat = false;
		
		drops.put(0, new Key(this.location, true));
		lootChance = 100;
	}
	
	
	public boolean isMainBoss() {
		return mainBoss;
	}

	public void removeShield() {
		shielded = false;
	}
	
	public boolean shielded() {
		return shielded ;
	}
	
	
	public void addShield() {
		shielded = true;
	}
	
	
	public boolean preAttack() {
		return preAttack;
	}
	
	
	public void addPreAttack() {
		preAttack = true;
	}


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


	public int getBossTimer() {
		bossTimer ++;
		return bossTimer;
	}


	public int[] getPillar() {
		return shieldLocation;
	}


}
