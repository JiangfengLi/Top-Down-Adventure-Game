package Model;

import javafx.scene.image.Image;

public class GameImages {
	
	public Image[] player = new Image[4];	
	public Image[] tank = new Image[4];
	public Image tankIdle;
	public Image shieldPillar;
	public Image rock;
	public Image grass;
	public Image[] playerSwing = new Image[4];
	public Image speedBuff;
	public Image tree;
	public Image[] mainBoss = new Image[4];
	public Image[] miniBoss = new Image[4];
	public Image heart;
	public Image dungeonEntrance;
	public Image[] flier = new Image[4];
	public Image flierIdle;
	public Image[] dps = new Image[4];
	public Image dpsIdle;
	public Image door;
	public Image[] fireball = new Image[4];
	public Image[] bowShot = new Image[4];
	public Image[] arrowShot = new Image[4];
	public Image arrow;
	public Image key;
	
	public GameImages() {
		player[0] = new Image("/style/playerSprites/link north.png");
		player[1] = new Image("/style/playerSprites/link left.png");
		player[2] = new Image("/style/playerSprites/Link south.png");
		player[3] = new Image("/style/playerSprites/link right.png");
		
		tank[0] = new Image("/style/tank north.png");
		tank[1] = new Image("/style/tank left.png");
		tank[2] = new Image("/style/tank down.png");
		tank[3] = new Image("/style/tank right.png");		
		tankIdle = new Image("/style/tank idle.png");
		
		shieldPillar = new Image("/style/shield gen.png");
		
		rock = new Image("/style/rock.png");
		
		grass = new Image("/style/Grass and Cut.png");
		
		playerSwing[0] = new Image("/style/playerSprites/sword north.png");
		playerSwing[1] = new Image("/style/playerSprites/sword left.png");
		playerSwing[2] = new Image("/style/playerSprites/sword south.png");
		playerSwing[3] = new Image("/style/playerSprites/sword right.png");
		
		speedBuff = new Image("/style/speed buff.png");
		
		tree = new Image("/style/Tree.png");
		
		mainBoss[0] = new Image("/style/boss.png");
		mainBoss[1] = new Image("/style/boss.png");
		mainBoss[2] = new Image("/style/boss.png");
		mainBoss[3] = new Image("/style/boss.png");
		
		miniBoss [0] = new Image("/style/miniboss.png");
		miniBoss[1] = new Image("/style/miniboss.png");
		miniBoss[2] = new Image("/style/miniboss.png");
		miniBoss[3] = new Image("/style/miniboss.png");
		
		heart = new Image("/style/health.png");
		
		dungeonEntrance = new Image("/style/dungeon entrance.png");
		
		flier[0] = new Image("/style/flier image.png");
		flier[1] = new Image("/style/flier image.png");
		flier[2] = new Image("/style/flier image.png");
		flier[3] = new Image("/style/flier image.png");
		flierIdle = new Image("/style/flier idle.png");
		
		dps[0] = new Image("/style/dps north.png");
		dps[1] = new Image("/style/dps left.png");
		dps[2] = new Image("/style/dps south.png");
		dps[3] = new Image("/style/dps right.png");
		dpsIdle = new Image("/style/dps idle.png");
		
		door = new Image("/style/door.png");
		
		fireball[0] = new Image("/style/fireball.png");
		fireball[1] = new Image("/style/fireball.png");
		fireball[2] = new Image("/style/fireball.png");
		fireball[3] = new Image("/style/fireball.png");
		
		bowShot[0] = new Image("/style/playerSprites/bow north.png");
		bowShot[1] = new Image("/style/playerSprites/bow left.png");
		bowShot[2] = new Image("/style/playerSprites/bow south.png");
		bowShot[3] = new Image("/style/playerSprites/bow right.png");
		
		arrowShot[0] = new Image("/style/arrow up.png");
		arrowShot[1] = new Image("/style/arrow left.png");
		arrowShot[2] = new Image("/style/arrow down.png");
		arrowShot[3] = new Image("/style/arrow right.png");
		
		arrow = new Image("/style/arrow drop.png");
	
		key = new Image("/style/bosskey.png"); 
	}
}
