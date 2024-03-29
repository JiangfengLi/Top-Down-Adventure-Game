package View;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import Model.*;
import Model.Character;
import controller.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton; 
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utils.MusicPlayer;

/**
 * View part of the MVC design pattern. This takes in all
 * the data given by the model and updates what the player sees. It also listens
 * for player input and passes that to the controller as appropriate.
 * 
 * @author Wes Rodgers, TianZeHu
 *
 */
public class gameView implements Observer{
	private final String BACK_GROUND = "/title.png";
	private static final int HEIGHT = 666;
	private static final int WIDTH = 999;
	private AnchorPane myPane;
	private Scene myScene;
	private Stage myStage;
	private boolean gameStarted = false;
	private Canvas canvas;
	private Overlay overlay;
	
	private GameController controller;
	private boolean wPressed, aPressed, sPressed, dPressed;
	private AnimationTimer animationTimer;
	private boolean isStop = false;
	private GameImages images = new GameImages();
	
	private Image bg = new Image("background.png");
	private Image dungeonBg = new Image("dungeon bg.png");
	
	private MusicPlayer backgroundMusic;
	private boolean previousCheck = false;
	private boolean bgmStart = true;
	private int deathTick;
	
	
	public gameView() {
		myPane = new AnchorPane();
		myScene = new Scene(myPane,WIDTH,HEIGHT);
		myStage = new Stage();
		myStage.setScene(myScene);
		
		makeButton("PLAY NOW!",400,583);
		overlay = new Overlay();
		backgroundMusic = new MusicPlayer();
		backgroundMusic.playMusic("/Backgroundmusic/titlemusic.wav", 1);
		
		Button button = new Button("Link");
		myPane.getChildren().add(button);
		button.setPrefWidth(63);
		button.setPrefHeight(50);
		button.setLayoutX(452);
		button.setLayoutY(412);
		githubLink(button);
		setBackground(BACK_GROUND);
		
		myStage.setResizable(false);
	}
	
	/**
	 * sets up the 'link' button on the title screen to link to the repo that contains this project.
	 * @param button the button that opens the browser
	 */
	private void githubLink(Button button) {
		button.setOnMouseClicked((c) -> {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/CSC335Spring2019/csc335-team-zelda-finalproject-tianze-wes-jiangfeng"));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		});
		
	}

	/**
	 * getter for the stage
	 * @return the stage
	 */
	public Stage getStage() {
		return myStage;
	}
	
	/**
	 * Method to make a new button on the main Pane
	 * @param text text on the button
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	private void makeButton(String text, int x, int y) {
		Button button = new Button("Play");
		myPane.getChildren().add(button);
		button.setPrefWidth(190);
		button.setPrefHeight(46);
		button.setLayoutX(400);
		button.setLayoutY(583);
		setClicked(button);
	}
	
	/**
	 * sets up the listener for the initial button to start the game
	 * @param button the button passed in
	 */
	public void setClicked(Button button) {
		button.setOnMouseReleased((e)->{
			startGame(new GameModel());
		});
	}
	
	/**
	 * Set background image
	 * @param url image's url
	 */
	private void setBackground(String url) {
		Image back = new Image(BACK_GROUND);
		BackgroundImage backGround = new BackgroundImage(back, null, null, BackgroundPosition.DEFAULT, new BackgroundSize(999, 666, false, false, false, true));
		myPane.setBackground(new Background(backGround));
	}
	
	/**
	 * updates the player's position in the area
	 */
	public void updateCharacterPosition() {
		
		//we'll just store the amount to increment the player's position per tick
		int xMovement = dPressed ? controller.getPlayerSpeed() : 0;
		xMovement += aPressed ? -controller.getPlayerSpeed() : 0;
		int yMovement = wPressed ? -controller.getPlayerSpeed() : 0;
		yMovement += sPressed ? controller.getPlayerSpeed() : 0;
		
		controller.updatePlayerPosition(xMovement, yMovement);
	}
	
	/**
	 * updates the enemies' positions in the area
	 */
	public void updateEnemyPosition() {
		controller.updateEnemyPositions();
	}
	
	/**
	 * checks for player and enemy death
	 */
	public void checkDeath() {
		if(controller.playerDead()) {
			animationTimer.stop();
			backgroundMusic.stopMusic();
			bgmStart = false;
			AudioClip death = new AudioClip(getClass().getResource("/soundfx/LTTP_Link_Dying.wav").toString());
			death.play();
			deathAnimation();
			
			
		}
		
		//remove any dead enemies from the map and add in their death animations
		controller.removeDeadEnemies();
	}
	
	/**
	 * Plays a death animation for the character
	 */
	private void deathAnimation() {
		deathTick = 0;
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {				
				if(deathTick > 100) {
					this.stop();
					invokMenu(true);
				}
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, WIDTH, HEIGHT);
				gc.setFill(Paint.valueOf("RED"));
				gc.fillRect(0,0, WIDTH, HEIGHT);
				if(deathTick <= 8) gc.drawImage(images.player[2], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=16) gc.drawImage(images.player[1], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=24) gc.drawImage(images.player[0], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=32) gc.drawImage(images.player[3], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=40) gc.drawImage(images.player[2], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=48) gc.drawImage(images.player[1], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=56) gc.drawImage(images.player[0], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=64) gc.drawImage(images.player[3], 0, 0, 29, 24, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 58, 48);
				else if(deathTick <=72) gc.drawImage(images.playerDeath, 0, 0, 24, 22, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 48, 44);
				else if(deathTick <=80) gc.drawImage(images.playerDeath, 25, 0, 24, 22, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 48, 44);
				else {
					gc.setFill(Paint.valueOf("BLACK"));
					gc.fillRect(0, 0, WIDTH, HEIGHT);
					gc.drawImage(images.playerDeath, 25, 0, 24, 22, controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], 48, 44);					
				}
				deathTick++;
			}
		};
		animationTimer.start();
	}
	
	
	/**
	 * Pulls up the game-menu
	 */
	private void invokMenu() {
		Scene newScene = constructMenu();
		newScene.setOnKeyPressed((e)->{
			if (e.getCode()==KeyCode.M) {
				myStage.setScene(myScene);
				myStage.setTitle("Adventure Game");
				isStop = false;
				animationTimer.start();
			}
		});
		myStage.setTitle("Menu");
		myStage.setScene(newScene);
	}
	
	private void invokMenu(boolean death) {
		Scene newScene = deathMenu();
		myStage.setTitle("Game Over");
		myStage.setScene(newScene);
	}
	
	
	private Scene deathMenu() {
		buttonMaker exit = new buttonMaker("EXIT");
		AnchorPane layout= new AnchorPane();
		layout.getChildren().add(exit);
		exit.setLayoutY(200);
		exit.setLayoutX(WIDTH/2-100);
		exit.setOnMouseReleased((e)->{
			myStage.close();
		});
		buttonMaker restart = new buttonMaker("RESTART");
		layout.getChildren().add(restart);
		restart.setLayoutX(WIDTH/2-100);
		restart.setLayoutY(500);
		restart.setOnAction((e)->{
			isStop = false;
			animationTimer.start();
			startGame(new GameModel());
		});
		buttonMaker load = new buttonMaker("Load Game");
		layout.getChildren().add(load);
		load.setLayoutX(WIDTH/2-100);
		load.setLayoutY(350);
		load.setOnAction((e)-> {
			startGame((GameModel) GameResource.loadGame("save.dat"));
			animationTimer.start();			
		});
		Scene scene1= new Scene(layout, WIDTH, HEIGHT);
		layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), null, null)));
		return scene1;
	}

	/**
	 * sets up listeners for key press and key release
	 * so we can tell when a combination of keys are pressed
	 * and move diagonally as appropriate. 
	 */
	public void setupMovementListeners() {
		
		//this sets up a boolean so we can move until the player releases the key
		myScene.setOnKeyPressed((e)->{
			if(e.getCode() == KeyCode.W) {
				controller.setPlayerDirection(1);
				wPressed = true;
			}
			if(e.getCode() == KeyCode.S) {
				controller.setPlayerDirection(3);
				sPressed = true;
			}
			
			if(e.getCode() == KeyCode.A) {
				controller.setPlayerDirection(2);
				aPressed = true;
			}
			if(e.getCode() == KeyCode.D) {
				controller.setPlayerDirection(4);
				dPressed = true;
			}
			if(e.getCode() == KeyCode.P) {
				if(!isStop) {
					//System.out.println("paused");
					aPressed = false;
					sPressed = false;
					dPressed = false;
					wPressed = false;
					myStage.setTitle("PAUSED");
					isStop  = true;
					animationTimer.stop();
					drawMap();
				}
				else {
					isStop = false;
					myStage.setTitle("Adventure Game");
					animationTimer.start();
				}
			}
			if (e.getCode() == KeyCode.M && !isStop) {
				aPressed = false;
				sPressed = false;
				dPressed = false;
				wPressed = false;
				isStop=true;
				animationTimer.stop();
				invokMenu();
			} else if (e.getCode() == KeyCode.M && isStop) {
				isStop=false;
				animationTimer.start();
				myStage.setTitle("Adventure Game");
			}
		});
		
		//turns the keypress boolean false so we can stop moving when the player releases the key
		myScene.setOnKeyReleased((e)->{
			if(e.getCode() == KeyCode.W) {
				wPressed = false;
			}
			if(e.getCode() == KeyCode.S) {
				sPressed = false;
			}
			
			if(e.getCode() == KeyCode.A) {
				aPressed = false;
			}
			if(e.getCode() == KeyCode.D) {
				dPressed = false;
			}
			
			//these if checks set the direction that the player is moving in so the player character doesn't appear to strafe
			if(wPressed) {
				controller.setPlayerDirection(1);
			}
			if(aPressed) {
				controller.setPlayerDirection(2);
			}
			if(sPressed) {
				controller.setPlayerDirection(3);
			}
			if(dPressed) {
				controller.setPlayerDirection(4);
			}
		});
	}
	
	/**
	 * Constructs the game-menu's scene and returns it
	 * @return the game menu's scene
	 */
	private Scene constructMenu() {
		buttonMaker resume = new buttonMaker("RESUME");
		buttonMaker exit = new buttonMaker("EXIT");
		AnchorPane layout= new AnchorPane();
		layout.getChildren().add(resume);
		resume.setLayoutX(WIDTH/2-100);
        resume.setLayoutY(100);
		layout.getChildren().add(exit);
		exit.setLayoutY(200);
		exit.setLayoutX(WIDTH/2-100);
		exit.setOnMouseReleased((e)->{
			myStage.close();
		});
		resume.setOnMouseReleased((e)->{
			myStage.setScene(myScene);
			isStop = false;
			animationTimer.start();
		});
		buttonMaker bgm = new buttonMaker("Enable/Disable Background Music");
		layout.getChildren().add(bgm);
		bgm.setLayoutX(WIDTH/2-100);
		bgm.setLayoutY(300);
		bgm.setOnAction((e)->{
			// TODO: implement when we add bgm.
			if (bgmStart) {
				backgroundMusic.stopMusic();
				bgmStart = false;
			} else {
				if (controller.inDungeon()) {
					backgroundMusic.stopMusic();
					backgroundMusic.playMusic("/Backgroundmusic/" + (controller.inDungeon() ? "spookydungeonmusic.wav" : "zeldatheme.wav"), 0);
					backgroundMusic.setVolume(controller.inDungeon() ? 0.9 : 0.4);
					bgmStart = true;
				} else {
					backgroundMusic.stopMusic();
					backgroundMusic.playMusic("/Backgroundmusic/zeldatheme.wav", 0);
					backgroundMusic.setVolume(0.4);
					bgmStart = true;
				}
			}
		});
		buttonMaker se = new buttonMaker("Save Game");
		layout.getChildren().add(se);
		se.setLayoutX(WIDTH/2-100);
		se.setLayoutY(400);
		se.setOnAction((e)->{
			try {
				GameResource.saveGame(controller.getModel(), "save.dat");
				System.out.println("saved!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		buttonMaker restart = new buttonMaker("RESTART");
		layout.getChildren().add(restart);
		restart.setLayoutX(WIDTH/2-100);
		restart.setLayoutY(500);
		restart.setOnAction((e)->{
			isStop = false;
			animationTimer.start();
			startGame(new GameModel());
		});
		buttonMaker load = new buttonMaker("Load Game");
		layout.getChildren().add(load);
		load.setLayoutX(WIDTH/2-100);
		load.setLayoutY(600);
		load.setOnAction((e)-> {
			startGame((GameModel) GameResource.loadGame("save.dat"));
			animationTimer.start();			
		});
		Scene scene1= new Scene(layout, WIDTH, HEIGHT);
		layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("black"), null, null)));
		return scene1;
	}
	
	/**
	 * draws the map to screen
	 */
	private void drawMap() {
		GameMap currMap = controller.getOverlandMap();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		Image bg = new Image("/background.png");
		gc.drawImage(bg,  0,  0, WIDTH, HEIGHT);
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				Area area = currMap.getArea(i, j);
				if(controller.getArea() == area) {
					gc.setFill(Paint.valueOf("RED"));
					gc.fillOval(controller.getPlayerPosition()[0]/3 + i*WIDTH/3, controller.getPlayerPosition()[1]/3 + HEIGHT/3*j, 10, 10);
				}
				CopyOnWriteArrayList<Obstacle> toDraw = new CopyOnWriteArrayList<Obstacle>();
				toDraw.addAll(area.getObstacles());
				for(Obstacle obs : toDraw) {
					Image imgFile;
					if(obs instanceof Tree) imgFile = images.tree;
					else if(obs instanceof Rock) imgFile = images.rock;
					else if(obs instanceof Grass) imgFile = images.grass;
					else if(obs instanceof Door) imgFile = images.door;
					else if(obs instanceof DungeonEntrance) imgFile = images.dungeonEntrance;
					else imgFile = new Image("");
					gc.drawImage(imgFile, 0, 0, obs.getWidth(), obs.getHeight(), 
							obs.getLocation()[0]/3 + WIDTH/3*i, obs.getLocation()[1]/3 + HEIGHT/3*j, obs.getWidth()/3, obs.getHeight()/3);
				}
				
			}
		}
		
		
	}

	/**
	 * sets up the event listeners for player mouse clicks, sword swing on left click and bow attack on right
	 */
	public void setupMouseClickListeners() {
		myScene.setOnMousePressed((e)->{
			if(e.getButton() == MouseButton.PRIMARY) {
				controller.swordAttack();
			}
			else if(e.getButton() == MouseButton.SECONDARY) {
				controller.bowAttack();
			}
		});
	}

	/**
	 * starts up the game, creates the map and sets appropriate class variables
	 * adds this view as an observer to the model and updates the screen when the model changes
	 * every tick
	 * @param model the model to build the game from
	 */
	public void startGame(GameModel model) {
		controller = new GameController(model);
		gameStarted = true;
		canvas = new Canvas(WIDTH, HEIGHT);
		myScene = new Scene(new Group(canvas));
		myStage.setScene(myScene);		
		setupMovementListeners();
		setupMouseClickListeners();
		model.addObserver(this);
		backgroundMusic.stopMusic();
		backgroundMusic.playMusic("/Backgroundmusic/zeldatheme.wav", 0);
		backgroundMusic.setVolume(0.4);
		bgmStart = true;
		update(model, controller.getArea());
	}
	

	/**
	 * redraws things on screen when the model updates and notifies us
	 * this is basically our animation engine, runs once a tick on the fact that
	 * the player position gets updated once per tick even if they didn't move or attack
	 */
	@Override
	public void update(Observable model, Object area) {
		// update the sound effect of objects
		Iterator<AudioClip> clips = controller.getSoundFX().iterator();
		while(clips.hasNext()) {
			AudioClip a = clips.next();
			a.play();
			clips.remove();			
		}
		// get the players, obstacles and enemies
		Player player = ((GameModel) model).getPlayer();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		CopyOnWriteArrayList<Obstacle> obstacles = ((Area) area).getObstacles();
		CopyOnWriteArrayList<Enemy> enemies = ((Area) area).getEnemies();		
		
		//clears the old screen
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		
		//draws the background layer
		
		// check whether the player move into or out Dungeon and change the beckground music if the players move to different map layout
		if(previousCheck != controller.inDungeon()) {
			backgroundMusic.stopMusic();
			backgroundMusic.playMusic("/Backgroundmusic/" + (controller.inDungeon() ? "spookydungeonmusic.wav" : "zeldatheme.wav"), 0);
			backgroundMusic.setVolume(controller.inDungeon() ? 0.9 : 0.2);
		}
		previousCheck  = controller.inDungeon();
		gc.drawImage(controller.inDungeon() ? dungeonBg : bg, 0, 0, WIDTH, HEIGHT);		
		
		//this should be obvious, but the draw order here is important because it helps to force perspective.
		//like players should be on top of back ground, but should be able to go underneath the top part of large obstacles
		//but walk over loot instead of under it.
		
		
		//iterates through the obstacles in current area, draws them to screen
		for(Obstacle obstacle : obstacles) {
			Image image;
			if(obstacle instanceof Tree) image = images.tree;
			else if(obstacle instanceof Rock) image = images.rock;
			else if(obstacle instanceof Grass) image = images.grass;
			else if(obstacle instanceof Door) image = images.door;
			else if(obstacle instanceof DungeonEntrance) image = images.dungeonEntrance;
			else image = new Image("");
			
			//obstacles leave behind remains, this draws those remains as pathable objects.
			if(obstacle.destroyed()) {
				gc.drawImage(image, obstacle.getWidth()*obstacle.lastFrame(),0, obstacle.getWidth(), obstacle.getHeight(),
						obstacle.getLocation()[0], obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getHeight());
			}
			
			//just makes sure we draw the dungeon entrance on top of the remains but underneath the obstacle.
			if(obstacle instanceof DungeonEntrance) {
				gc.drawImage(image, 0, 0, obstacle.getWidth(), obstacle.getHeight(), 
						obstacle.getLocation()[0], obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getHeight());
			}
			
			if(!obstacle.destroyed() && !(obstacle instanceof DungeonEntrance)) {
				gc.drawImage(image, 0,0,obstacle.getWidth(), obstacle.getHeight(), obstacle.getLocation()[0], 
						obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getHeight());
			}			
		}
		
		
		
		
		//draws loot items to screen
		for(Item loot : ((GameModel) model).getCurrentArea().getLoot()) {
			if(loot instanceof Arrow) {
				Image image = images.arrow;
				gc.drawImage(image, 15*(((Arrow) loot).getQuantity() == 5 ? 0 : 1), 0, loot.getWidth()/2, 
						loot.getHeight()/2, loot.getLocation()[0], loot.getLocation()[1], loot.getWidth(), loot.getHeight());
			}
				
			else if(loot instanceof Heart) {
				Image image = images.heart;
				gc.drawImage(image, loot.getWidth()*((getGameClock()%40)/8), 0, loot.getWidth(), loot.getHeight(), loot.getLocation()[0], loot.getLocation()[1], loot.getWidth(), loot.getHeight());
			}
				
			else if(loot instanceof SpeedBuff) {
				Image image = images.speedBuff;
				gc.drawImage(image, loot.getWidth()*((getGameClock()%40)/8), 0, loot.getWidth(), loot.getHeight(), loot.getLocation()[0], loot.getLocation()[1], loot.getWidth(), loot.getHeight());
			}
			
			else {
				Image image = images.key;
				gc.drawImage(image, 0, 0, loot.getWidth(), loot.getHeight(), loot.getLocation()[0], loot.getLocation()[1], loot.getWidth()*1.5, loot.getHeight()*1.5);
			}
		}
		
		//draw Player to screen 
		Image playerImage = images.player[player.getDirection()-1];		
		if(!controller.playerStalled() || (controller.playerStalled() && ((GameModel) model).getPlayer().damaged())) {
			//this is for in motion player characters
			if(wPressed || aPressed || sPressed || dPressed) {
				gc.drawImage(playerImage, 30*((getGameClock()%16)/4), 0, 29, 24, player.getLocation()[0], player.getLocation()[1], 58, 48);
			}
			//this draws stationary player characters
			else {
				gc.drawImage(playerImage, 0, 0, 29, 24, player.getLocation()[0], player.getLocation()[1], 58, 48);
			}
		}		
		
		//iterates through enemies in current area, draws them to screen
		for(Enemy enemy : enemies) {
			if(enemy.active()) {
				if(enemy instanceof Boss && ((Boss)enemy).isMainBoss()) {
					if(!((Boss) enemy).preAttack()) {
						Image enemyImage = images.mainBoss[enemy.getDirection()-1];
						gc.drawImage(enemyImage, enemy.getWidth()*((getGameClock()%60)/12), 0, enemy.getWidth(), enemy.getHeight(),
								enemy.getLocation()[0], enemy.getLocation()[1], enemy.getWidth(), enemy.getHeight());
					}
					else {
						Image enemyImage = images.mainBoss[enemy.getDirection()-1];
						gc.drawImage(enemyImage, enemy.getWidth()*((getGameClock()%25)/5), 0, enemy.getWidth(), enemy.getHeight(),
								enemy.getLocation()[0], enemy.getLocation()[1], enemy.getWidth(), enemy.getHeight());
					}
					if(((Boss) enemy).shielded()) {
						gc.setFill(Color.rgb(255,255,255,0.5));
						gc.setStroke(Color.rgb(255,255,255,1));
						gc.setLineWidth(9);
						gc.strokeOval(enemy.getLocation()[0]-10, enemy.getLocation()[1]-10, enemy.getWidth()+20, enemy.getWidth()+20);
						gc.fillOval(enemy.getLocation()[0]-10, enemy.getLocation()[1]-10, enemy.getWidth()+20, enemy.getWidth()+20);
						gc.setFill(Color.rgb(147,112,219, 0.5));
						gc.fillOval(enemy.getLocation()[0]-7, enemy.getLocation()[1]-7,  enemy.getWidth()+14, enemy.getWidth()+14);
					}
				}
				else if(enemy instanceof Boss && !((Boss)enemy).isMainBoss()) {
					Image enImg = images.miniBoss[enemy.getDirection()-1];
					gc.drawImage(enImg, enemy.getWidth()*((getGameClock()%60)/30), 0, enemy.getWidth(), enemy.getHeight(),
							enemy.getLocation()[0], enemy.getLocation()[1], enemy.getWidth(), enemy.getHeight());
				}
				else {
					Image enemyImage;
					if(enemy instanceof Tank) enemyImage = images.tank[enemy.getDirection()-1];
					else if(enemy instanceof DPS) enemyImage = images.dps[enemy.getDirection()-1];
					else if(enemy instanceof Flier) enemyImage = images.flier[enemy.getDirection()-1];
					else if(enemy instanceof ShieldPillar) enemyImage = images.shieldPillar;
					else enemyImage = new Image("");
					gc.drawImage(enemyImage, enemy.getWidth()*((getGameClock()%6)/3), 0, enemy.getWidth(), enemy.getHeight(), 
							enemy.getLocation()[0], enemy.getLocation()[1], enemy.getWidth(), enemy.getHeight());
						
					if(enemy instanceof ShieldPillar) {
						gc.setFill(Color.rgb(255,255,255,0.5));
						gc.setStroke(Color.rgb(255,255,255,1));
						gc.setLineWidth(9);
						gc.strokeOval(enemy.getLocation()[0]-10, enemy.getLocation()[1]-10, enemy.getWidth()+20, enemy.getWidth()+20);
						gc.fillOval(enemy.getLocation()[0]-10, enemy.getLocation()[1]-10, enemy.getWidth()+20, enemy.getWidth()+20);
						gc.setFill(Color.rgb(147,112,219, 0.5));
						gc.fillOval(enemy.getLocation()[0]-7, enemy.getLocation()[1]-7,  enemy.getWidth()+14, enemy.getWidth()+14);
						
						gc.setLineWidth(1);
						gc.strokeLine(enemy.getLocation()[0]+15, enemy.getLocation()[1]+15, 
								((GameModel) model).getCurrentArea().getBoss().getLocation()[0]+50, ((GameModel) model).getCurrentArea().getBoss().getLocation()[1]+50);
					}
				}
			}
		}
		
		//iterates through the projectiles that are currently on the screen and draws each one.
		for(Character projectile : ((GameModel) model).getCurrentArea().getProjectiles()) {
			Image projectileImage;
			if(!(projectile instanceof BossAttack)) {
				projectileImage = images.arrowShot[projectile.getDirection()-1];
				gc.drawImage(projectileImage, 0, 0, projectile.getWidth()/2, projectile.getHeight()/2, 
					projectile.getLocation()[0], projectile.getLocation()[1], projectile.getWidth(), projectile.getHeight());
			}
			else {
				projectileImage = images.fireball[0];
				gc.drawImage(projectileImage, 0, 0 , projectile.getWidth(), projectile.getHeight(), 
						projectile.getLocation()[0], projectile.getLocation()[1], projectile.getWidth(), projectile.getHeight());
			}
		}
		
		//draw animations currently in progress
		ArrayList<GameObject> finished = new ArrayList<GameObject>();
		for(GameObject obj : ((GameModel) model).getAnimations()) {
			
			//obstacle animations are destruction animations. Play it.
			if(obj instanceof Obstacle) {
				int currFrame = ((Obstacle) obj).destroyedFrame()/2;
				Image image = images.grass;
				gc.drawImage(image, 50*currFrame, 0, 50, 50, obj.getLocation()[0], obj.getLocation()[1], 50, 50);
				if(currFrame >= 9) {
					finished.add(obj);
				}
			}
			
			//draws the animation frame of the player swinging his sword in the appropriate direction
			if(obj instanceof PlayerSwing) {
				Image image = images.playerSwing[((PlayerSwing) obj).getDirection() - 1];
				if(player.getDirection() == 1) {
					gc.drawImage(image, 75*((5 - player.getStallTime())), 0, 75, 73, 
							controller.getPlayerPosition()[0] - 10, controller.getPlayerPosition()[1] - 5, obj.getWidth()*0.8, obj.getHeight()*0.8);
				}
				else {
					gc.drawImage(image, 75*((5 - player.getStallTime())), 0, 75, 73, 
						controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], obj.getWidth()*0.8, obj.getHeight()*0.8);
				}
				if(!player.stalled()) {
					finished.add(obj);
				}
			}
			
			//plays the enemy's idle animation if they aren't currently chasing the player down
			else if(obj instanceof Enemy && !((Enemy) obj).isDead()) {
				if(!(obj instanceof Boss)) {
					Image image;
					if(obj instanceof Tank) image = images.tankIdle;
					else if(obj instanceof Flier) image = images.flierIdle;
					else if(obj instanceof DPS) image = images.dpsIdle;
					else image = new Image("");
					gc.drawImage(image, obj.getWidth()*((getGameClock()%64)/4), 0, obj.getWidth(), obj.getHeight(), obj.getLocation()[0], obj.getLocation()[1], obj.getWidth(), obj.getHeight());				
				}
				else if (obj instanceof Boss && ((Boss)obj).isMainBoss()){
					Image image = images.mainBoss[0];
					gc.drawImage(image, obj.getWidth()*((getGameClock()%52)/13), 0, obj.getWidth(), obj.getHeight(), obj.getLocation()[0],
							obj.getLocation()[1], obj.getWidth(), obj.getHeight());
				}
			}
			
			//plays the enemy's death animation when they die
			else if(obj instanceof Enemy && ((Enemy) obj).isDead()) {
				// play death animation
			}
			
			//play bow attack animation frame
			else if(obj instanceof BowShot) {
				Image image = images.bowShot[((BowShot) obj).getDirection() - 1];
				gc.drawImage(image, 54*(5 - player.getStallTime()), 0, 54, 73, controller.getPlayerPosition()[0], 
						controller.getPlayerPosition()[1], obj.getWidth()*0.7, obj.getHeight()*0.7);
				if(!player.stalled()) {
					finished.add(obj);
				}
			}
		}
		((GameModel) model).getAnimations().removeAll(finished);
		
		//draw top part of large objects to help force perspective
		for(Obstacle obstacle : obstacles) {
			if(obstacle.hasTopImage()) {
				Image image = images.tree;
				gc.drawImage(image, 0, 0, obstacle.getWidth(), obstacle.getTopHeight(), obstacle.getLocation()[0],
						obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getTopHeight());
			}
		}
		
		
		//this last part will make sure to draw the HUD/overlay on top of everything else, as is appropriate 
		gc.drawImage(overlay.getImage("life"), 0, 0, overlay.getWidth("life"), overlay.getHeight("life"),
				overlay.getLocation("life")[0], overlay.getLocation("life")[1], overlay.getWidth("life"), overlay.getHeight("life"));
		
		gc.drawImage(overlay.getImage("arrow"), 0, 0, overlay.getWidth("arrow"), overlay.getHeight("arrow"),
				overlay.getLocation("arrow")[0], overlay.getLocation("arrow")[1], overlay.getWidth("arrow"), overlay.getHeight("arrow"));
		gc.setStroke(Paint.valueOf("WHITE"));
		gc.strokeText(Integer.toString(player.getArrowQuantity()/10), overlay.getLocation("arrow")[0] + 4, overlay.getLocation("arrow")[1] + 30);
		gc.strokeText(Integer.toString(player.getArrowQuantity()%10), overlay.getLocation("arrow")[0] + 16, overlay.getLocation("arrow")[1] + 30);
		
		gc.drawImage(overlay.getImage("key"), 0, 0, overlay.getWidth("key"), overlay.getHeight("key"),
				overlay.getLocation("key")[0], overlay.getLocation("key")[1], overlay.getWidth("key"), overlay.getHeight("key"));
		gc.strokeText(Integer.toString(player.hasBossKey() ? 1 : 0), overlay.getLocation("key")[0] + 6, overlay.getLocation("key")[1] + 34);
		
		for(int i=0; i<3; i++) {
			gc.drawImage(overlay.getImage("empty"), 0, 0, overlay.getWidth("heart"), overlay.getHeight("heart"),
					overlay.getLocation("heart")[0] + i*30, overlay.getLocation("heart")[1], overlay.getWidth("heart"), overlay.getHeight("heart"));
			if(player.getHP() >= i*2 + 1) {
				gc.drawImage(overlay.getImage("half"), 0, 0, overlay.getWidth("heart"), overlay.getHeight("heart"),
					overlay.getLocation("heart")[0] + i*30, overlay.getLocation("heart")[1], overlay.getWidth("heart"), overlay.getHeight("heart"));
			}
			if(player.getHP() >= i*2 + 2) {
				gc.drawImage(overlay.getImage("full"), 0, 0, overlay.getWidth("heart"), overlay.getHeight("heart"),
					overlay.getLocation("heart")[0] + i*30, overlay.getLocation("heart")[1], overlay.getWidth("heart"), overlay.getHeight("heart"));
			}
		}
		
		
	}

	/**
	 * returns the current tick of the game clock
	 * @return the tick the game clock is currently on.
	 */
	public int getGameClock() {
		return controller.getGameClock();
	}

	/**
	 * returns whether the game has started or not
	 * @return true if the game has started, false otherwise
	 */
	public boolean gameStarted() {
		return gameStarted;
	}

	/**
	 * increments the game clock by one
	 */
	public void incrementGameClock() {
		controller.incrementGameClock();		
	}

	/**
	 * calls methods to determine whether an enemy collided with the player, does damage if so.
	 */
	public void updateEnemyCollision() {
		controller.enemyAttack();
	}

	/**
	 * updates the location of and collision of projectiles on the map
	 */
	public void updateProjectiles() {
		controller.updateProjectilePosition();
	}

	/**
	 * passes in the animation timer so the view can pause the game engine
	 * @param animationTimer the animation timer running our game engine.
	 */
	public void setAnimationTimer(AnimationTimer animationTimer) {
		this.animationTimer = animationTimer;
	}

	public void checkWin() {
		if(controller.getArea().getCoords()[0] == 2 && controller.getArea().getCoords()[1] == 0) {
			animationTimer.stop();
			backgroundMusic.stopMusic();
			bgmStart = false;
			winScreen();
		}			
	}

	private void winScreen() {
		deathTick = 0;
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {				
				if(deathTick > 500) {
					
					this.stop();
					invokMenu(true);
				}
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, WIDTH, HEIGHT);
				gc.setFill(Paint.valueOf("black"));
				gc.fillRect(0,0, WIDTH, HEIGHT);
				gc.setFill(Paint.valueOf("white"));
				gc.setFont(new Font(20));
				if(deathTick >= 0) gc.fillText("With Navi's death, a twenty year long reign of terror has been brought to an end.", 160, 200);
				if(deathTick >= 100) gc.fillText("The evil fairy has finally harassed her last generation of impressionable youth.", 180, 250);;
				if(deathTick >= 200) gc.fillText("Thanks for playing!", 400, 600);;
				deathTick++;
			}
		};
		animationTimer.start();
		
	}
}