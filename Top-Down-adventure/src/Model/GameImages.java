package Model;

import javafx.scene.image.Image;

/**
 * Single class to hold Images for all game objects, that way we can still access them without
 * having to waste massive amounts of overhead creating them 60/second. This way we also don't have to waste memory
 * saving an individual copy of the object for each instance of the object, or worry about serializing them via pixelgrabs 
 * when we save. Honestly, I maybe should've just written this as an enum or something, not sure how that would work memory-wise
 * as far as creating the images goes though and this was mainly a work around to allow us to save the game.
 * @author Wes Rodgers
 *
 */
public class GameImages {
	public Image[] player = new Image[4];
	public Image playerDeath;
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
		playerDeath = new Image(GameImages.class.getResource("/playerSprites/link death.png").toString());
		player[0] = new Image(GameImages.class.getResource("/playerSprites/link north.png").toString());
		player[1] = new Image(GameImages.class.getResource("/playerSprites/link left.png").toString());
		player[2] = new Image(GameImages.class.getResource("/playerSprites/Link south.png").toString());
		player[3] = new Image(GameImages.class.getResource("/playerSprites/link right.png").toString());
		
		tank[0] = new Image(GameImages.class.getResource("/tank north.png").toString());
		tank[1] = new Image(GameImages.class.getResource("/tank left.png").toString());
		tank[2] = new Image(GameImages.class.getResource("/tank down.png").toString());
		tank[3] = new Image(GameImages.class.getResource("/tank right.png").toString());		
		tankIdle = new Image(GameImages.class.getResource("/tank idle.png").toString());
		
		shieldPillar = new Image(GameImages.class.getResource("/shield gen.png").toString());
		
		rock = new Image(GameImages.class.getResource("/rock.png").toString());
		
		grass = new Image(GameImages.class.getResource("/Grass and Cut.png").toString());
		
		playerSwing[0] = new Image(GameImages.class.getResource("/playerSprites/sword north.png").toString());
		playerSwing[1] = new Image(GameImages.class.getResource("/playerSprites/sword left.png").toString());
		playerSwing[2] = new Image(GameImages.class.getResource("/playerSprites/sword south.png").toString());
		playerSwing[3] = new Image(GameImages.class.getResource("/playerSprites/sword right.png").toString());
		
		speedBuff = new Image(GameImages.class.getResource("/speed buff.png").toString());
		
		tree = new Image(GameImages.class.getResource("/tree.png").toString());
		
		mainBoss[0] = new Image(GameImages.class.getResource("/boss.png").toString());
		mainBoss[1] = new Image(GameImages.class.getResource("/boss.png").toString());
		mainBoss[2] = new Image(GameImages.class.getResource("/boss.png").toString());
		mainBoss[3] = new Image(GameImages.class.getResource("/boss.png").toString());
		
		miniBoss [0] = new Image(GameImages.class.getResource("/miniboss.png").toString());
		miniBoss[1] = new Image(GameImages.class.getResource("/miniboss.png").toString());
		miniBoss[2] = new Image(GameImages.class.getResource("/miniboss.png").toString());
		miniBoss[3] = new Image(GameImages.class.getResource("/miniboss.png").toString());
		
		heart = new Image(GameImages.class.getResource("/health.png").toString());
		
		dungeonEntrance = new Image(GameImages.class.getResource("/dungeon entrance.png").toString());
		
		flier[0] = new Image(GameImages.class.getResource("/flier image.png").toString());
		flier[1] = new Image(GameImages.class.getResource("/flier image.png").toString());
		flier[2] = new Image(GameImages.class.getResource("/flier image.png").toString());
		flier[3] = new Image(GameImages.class.getResource("/flier image.png").toString());
		flierIdle = new Image(GameImages.class.getResource("/flier idle.png").toString());
		
		dps[0] = new Image(GameImages.class.getResource("/dps north.png").toString());
		dps[1] = new Image(GameImages.class.getResource("/dps left.png").toString());
		dps[2] = new Image(GameImages.class.getResource("/dps south.png").toString());
		dps[3] = new Image(GameImages.class.getResource("/dps right.png").toString());
		dpsIdle = new Image(GameImages.class.getResource("/dps idle.png").toString());
		
		door = new Image(GameImages.class.getResource("/door.png").toString());
		
		fireball[0] = new Image(GameImages.class.getResource("/fireball.png").toString());
		fireball[1] = new Image(GameImages.class.getResource("/fireball.png").toString());
		fireball[2] = new Image(GameImages.class.getResource("/fireball.png").toString());
		fireball[3] = new Image(GameImages.class.getResource("/fireball.png").toString());
		
		bowShot[0] = new Image(GameImages.class.getResource("/playerSprites/bow north.png").toString());
		bowShot[1] = new Image(GameImages.class.getResource("/playerSprites/bow left.png").toString());
		bowShot[2] = new Image(GameImages.class.getResource("/playerSprites/bow south.png").toString());
		bowShot[3] = new Image(GameImages.class.getResource("/playerSprites/bow right.png").toString());
		
		arrowShot[0] = new Image(GameImages.class.getResource("/arrow up.png").toString());
		arrowShot[1] = new Image(GameImages.class.getResource("/arrow left.png").toString());
		arrowShot[2] = new Image(GameImages.class.getResource("/arrow down.png").toString());
		arrowShot[3] = new Image(GameImages.class.getResource("/arrow right.png").toString());
		
		arrow = new Image(GameImages.class.getResource("/arrow drop.png").toString());
	
		key = new Image(GameImages.class.getResource("/bosskey.png").toString()); 
	}
}
