package View;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class gameView implements Observer{
	private final String BACK_GROUND = "/style/title.png";
	private static final int HEIGHT = 666;
	private static final int WIDTH = 999;
	private AnchorPane myPane;
	private Scene myScene;
	private Stage myStage;
	private boolean gameStarted = false;
	private Canvas canvas; 
	
	private GameController controller;
	private boolean wPressed, aPressed, sPressed, dPressed;
	private AnimationTimer animationTimer;
	private boolean isStop = false;
	
	private Image bg = new Image("/style/background.png");
	
	public gameView() {
		myPane = new AnchorPane();
		myScene = new Scene(myPane,WIDTH,HEIGHT);
		myStage = new Stage();
		myStage.setScene(myScene);
		makeButton("PLAY NOW!",400,583);
		
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
		buttonMaker button = new buttonMaker(text);
		myPane.getChildren().add(button);
		setClicked(button);
		button.setLayoutX(x);
		button.setLayoutY(y);
	}
	
	/**
	 * sets up the listener for the initial button to start the game
	 * @param button the button passed in
	 */
	public void setClicked(buttonMaker button) {
		button.setOnMouseReleased((e)->{
			startGame();
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
	 * @return 
	 */
	public void checkDeath() {
		if(controller.playerDead()) {
			deathAnimation();
			/*********TODO***********
			 * after playing a death animation,
			 * set area and player position to 
			 * after-death location, fill health 
			 */
		}
		
		//remove any dead enemies from the map and add in their death animations
		controller.removeDeadEnemies();
	}
	
	/**
	 * Plays a death animation for the character
	 */
	private void deathAnimation() {
		// TODO Auto-generated method stub
		
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
					isStop  = true;
					animationTimer.stop();
				}
				else {
					isStop = false;
					animationTimer.start();
				}
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
	 */
	public void startGame() {
		GameModel model= new GameModel();
		controller = new GameController(model);
		gameStarted = true;
		canvas = new Canvas(WIDTH, HEIGHT);
		myScene = new Scene(new Group(canvas));
		myStage.setScene(myScene);		
		setupMovementListeners();
		setupMouseClickListeners();
		model.addObserver(this);
		update(model, controller.getArea());
	}

	/**
	 * redraws things on screen when the model updates and notifies us
	 * this is basically our animation engine, runs once a tick on the fact that
	 * the player position gets updated once per tick even if they didn't move or attack
	 */
	@Override
	public void update(Observable model, Object area) {
		
		Player player = ((GameModel) model).getPlayer();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		CopyOnWriteArrayList<Obstacle> obstacles = ((Area) area).getObstacles();
		CopyOnWriteArrayList<Enemy> enemies = ((Area) area).getEnemies();
		
		//clears the old screen
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		
		//draws the background layer
		gc.drawImage(bg, 0, 0, WIDTH, HEIGHT);		
		
		//this should be obvious, but the draw order here is important because it helps to force perspective.
		//like players should be on top of back ground, but should be able to go underneath the top part of large obstacles
		//but walk over loot instead of under it.
		
		
		//iterates through the obstacles in current area, draws them to screen
		for(Obstacle obstacle : obstacles) {
			Image image = obstacle.getImageFile(); 
			if(!obstacle.destroyed()) {
				gc.drawImage(image, 0,0,obstacle.getWidth(), obstacle.getHeight(), obstacle.getLocation()[0], 
						obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getHeight());
			}
			
			//obstacles leave behind remains, this draws those remains as pathable objects.
			if(obstacle.destroyed()) {
				gc.drawImage(image, obstacle.getWidth()*obstacle.lastFrame(),0, obstacle.getWidth(), obstacle.getHeight(),
						obstacle.getLocation()[0], obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getHeight());
			}
		}
		
		
		
		
		//draws loot items to screen
		for(Item loot : ((GameModel) model).getCurrentArea().getLoot()) {
			if(loot instanceof Arrow) {
				Image image = loot.getImageFile();
				gc.drawImage(image, 15*(((Arrow) loot).getQuantity() == 5 ? 0 : 1), 0, loot.getWidth()/2, 
						loot.getHeight()/2, loot.getLocation()[0], loot.getLocation()[1], loot.getWidth(), loot.getHeight());
			}
				
			if(loot instanceof Heart) {
				Image image = loot.getImageFile();
				gc.drawImage(image, loot.getWidth()*((getGameClock()%40)/8), 0, loot.getWidth(), loot.getHeight(), loot.getLocation()[0], loot.getLocation()[1], loot.getWidth(), loot.getHeight());
			}
				
			if(loot instanceof SpeedBuff) {
				Image image = loot.getImageFile();
				gc.drawImage(image, loot.getWidth()*((getGameClock()%40)/8), 0, loot.getWidth(), loot.getHeight(), loot.getLocation()[0], loot.getLocation()[1], loot.getWidth(), loot.getHeight());
			}
		}
		
		//draw Player to screen
		Image playerImage = player.getImageArray()[player.getDirection()-1];		
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
		
		//iterates through the projectiles that are currently on the screen and draws each one.
		for(Character projectile : ((GameModel) model).getCurrentArea().getProjectiles()) {
			Image projectileImage = projectile.getImageArray()[projectile.getDirection()-1];
			if(!(projectile instanceof BossAttack)) {
				gc.drawImage(projectileImage, 0, 0, projectile.getWidth()/2, projectile.getHeight()/2, 
					projectile.getLocation()[0], projectile.getLocation()[1], projectile.getWidth(), projectile.getHeight());
			}
			else {
				gc.drawImage(projectileImage, 0, 0 , projectile.getWidth(), projectile.getHeight(), 
						projectile.getLocation()[0], projectile.getLocation()[1], projectile.getWidth(), projectile.getHeight());
			}
		}
		
		//iterates through enemies in current area, draws them to screen
		for(Enemy enemy : enemies) {
			if(enemy.active()) {
				if((enemy instanceof Boss)) {
					if(!((Boss) enemy).preAttack()) {
						Image enemyImage = enemy.getImageArray()[enemy.getDirection()-1];
						gc.drawImage(enemyImage, enemy.getWidth()*((getGameClock()%60)/12), 0, enemy.getWidth(), enemy.getHeight(),
								enemy.getLocation()[0], enemy.getLocation()[1], enemy.getWidth(), enemy.getHeight());
					}
					else {
						Image enemyImage = enemy.getImageArray()[enemy.getDirection()-1];
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
				else {
						Image enemyImage = enemy.getImageArray()[enemy.getDirection()-1];
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
		
		//draw animations currently in progress
		ArrayList<GameObject> finished = new ArrayList<GameObject>();
		for(GameObject obj : ((GameModel) model).getAnimations()) {
			
			//obstacle animations are destruction animations. Play it.
			if(obj instanceof Obstacle) {
				int currFrame = ((Obstacle) obj).destroyedFrame()/2;
				Image image = obj.getImageFile();
				gc.drawImage(image, 50*currFrame, 0, 50, 50, obj.getLocation()[0], obj.getLocation()[1], 50, 50);
				if(currFrame >= 9) {
					finished.add(obj);
				}
			}
			
			//draws the animation frame of the player swinging his sword in the appropriate direction
			if(obj instanceof PlayerSwing) {
				Image image = ((PlayerSwing) obj).getImageArray()[((PlayerSwing) obj).getDirection() - 1];
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
					Image image = ((Enemy) obj).getIdleImage();
					gc.drawImage(image, obj.getWidth()*((getGameClock()%64)/4), 0, obj.getWidth(), obj.getHeight(), obj.getLocation()[0], obj.getLocation()[1], obj.getWidth(), obj.getHeight());				
				}
				else {
					Image image = ((Enemy) obj).getIdleImage();
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
				Image image = ((BowShot) obj).getImageArray()[((BowShot) obj).getDirection() - 1];
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
				Image image = obstacle.getImageFile();
				gc.drawImage(image, 0, 0, obstacle.getWidth(), obstacle.getTopHeight(), obstacle.getLocation()[0],
						obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getTopHeight());
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
	 * @return
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

	public void setAnimationTimer(AnimationTimer animationTimer) {
		this.animationTimer = animationTimer;
		
	}
}
