package View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Model.Area;
import Model.Character;
import Model.Enemy;
import Model.GameModel;
import Model.GameObject;
import Model.Obstacle;
import Model.Player;
import Model.PlayerSwing;
import Model.buttonMaker;
import controller.GameController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class gameView implements Observer{
	private final String BACK_GROUND = "/style/back_cave.png";
	private static final int HEIGHT = 800;
	private static final int WIDTH = 1200;
	private AnchorPane myPane;
	private Scene myScene;
	private Stage myStage;
	private boolean gameStarted = false;
	private Canvas canvas;
	
	private GameController controller;
	private boolean wPressed, aPressed, sPressed, dPressed;
	
	public gameView() {
		myPane = new AnchorPane();
		myScene = new Scene(myPane,WIDTH,HEIGHT);
		myStage = new Stage();
		myStage.setScene(myScene);
		makeButton("PLAY NOW!",200,300);
		setBackground(BACK_GROUND);
		
		myStage.minWidthProperty().bind(myScene.heightProperty().multiply(1.5));
		myStage.minHeightProperty().bind(myScene.heightProperty().divide(1.5));
	}
	
	/**
	 * getter for the stage
	 * @return
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
		Image back = new Image(BACK_GROUND,300,400,false,true);
		BackgroundImage backGround = new BackgroundImage(back, null, null, BackgroundPosition.DEFAULT, null);
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
	 * checks to see if the weapon hit an enemy
	 */
	public void checkProjectileCollision() {
		controller.checkProjectileCollision();
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
				controller.swordAttack(canvas);
			}
			else if(e.getButton() == MouseButton.SECONDARY) {
				controller.bowAttack(canvas);
			}
		});
	}

	/**
	 * starts up the game, creates the map and sets appropriate class variables
	 * adds this view as an observer to the model and updates the screen when the model changes
	 * every tick
	 */
	public void startGame() {
		gameStarted = true;
		canvas = new Canvas(WIDTH, HEIGHT);
		myScene = new Scene(new Group(canvas));
		myStage.setScene(myScene);
		GameModel model= new GameModel();
		controller = new GameController(model);
		setupMovementListeners();
		setupMouseClickListeners();
		model.addObserver(this);
		update(model, controller.getCurrentArea());
	}

	/**
	 * redraws things on screen when the model updates and notifies us
	 */
	@Override
	public void update(Observable model, Object area) {
		
		Player player = ((GameModel) model).getPlayer();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		ArrayList<Obstacle> obstacles = ((Area) area).getObstacles();
		ArrayList<Enemy> enemies = ((Area) area).getEnemies();
		
		//clears the old screen
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		
		//draws the background layer
		Image bg = new Image("/style/background.png");
		gc.drawImage(bg, 0, 0, WIDTH, HEIGHT);		
		
		//iterates through the obstacles in current area, draws them to screen
		for(Obstacle obstacle : obstacles) {
			Image image = new Image(obstacle.getImageFile()); 
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
		
		//iterates through enemies in current area, draws them to screen
		for(Enemy enemy : enemies) {
			if(enemy.active()) {
				Image enemyImage = new Image(enemy.getImageArray()[enemy.getDirection()-1]);
				gc.drawImage(enemyImage, enemy.getWidth()*((getGameClock()%6)/3), 0, enemy.getWidth(), enemy.getHeight(), 
						enemy.getLocation()[0], enemy.getLocation()[1], enemy.getWidth(), enemy.getHeight());
			}
		}
		
		//draw Player to screen
		Image playerImage = new Image(player.getImageArray()[player.getDirection()-1]);
		
		if(!controller.playerStalled()) {
			//this is for in motion player characters
			if(wPressed || aPressed || sPressed || dPressed) {
				gc.drawImage(playerImage, 30*((getGameClock()%16)/4), 0, 29, 24, player.getLocation()[0], player.getLocation()[1], 58, 48);
			}
			//this draws stationary player characters
			else {
				gc.drawImage(playerImage, 0, 0, 29, 24, player.getLocation()[0], player.getLocation()[1], 58, 48);
			}
		}
		
		//draw animations currently in progress
		ArrayList<GameObject> finished = new ArrayList<GameObject>();
		for(GameObject obj : ((GameModel) model).getAnimations()) {
			//obstacles have a destruction animation
			if(obj instanceof Obstacle) {
				int currFrame = ((Obstacle) obj).destroyedFrame()/2;
				Image image = new Image(obj.getImageFile());
				gc.drawImage(image, 50*currFrame, 0, 50, 50, obj.getLocation()[0], obj.getLocation()[1], 50, 50);
				if(currFrame >= 9) {
					finished.add(obj);
				}
			}
			
			if(obj instanceof PlayerSwing) {
				Image image = new Image(((PlayerSwing) obj).getImageArray()[((PlayerSwing) obj).getDirection() - 1]);
				gc.drawImage(image, 75*((5 - player.getStallTime())), 0, 75, 73, 
						controller.getPlayerPosition()[0], controller.getPlayerPosition()[1], obj.getWidth()*0.8, obj.getHeight()*0.8);
				player.decrementStall();
				if(!player.stalled()) {
					finished.add(obj);
				}
			}
			
			//plays the enemy's idle animation if they aren't currently chasing the player down
			else if(obj instanceof Enemy && !((Enemy) obj).isDead()) {
				Image image = new Image(((Enemy) obj).getIdleImage());
				gc.drawImage(image, 40*((getGameClock()%64)/4), 0, 40, 74, obj.getLocation()[0], obj.getLocation()[1], obj.getWidth(), obj.getHeight());
			}
			//plays the enemy's death animation when they die
			else if(obj instanceof Enemy && ((Enemy) obj).isDead()) {
				// play death animation
			}
		}
		((GameModel) model).getAnimations().removeAll(finished);
		//draw top part of large objects to help force perspective
		for(Obstacle obstacle : obstacles) {
			if(obstacle.hasTopImage()) {
				Image image = new Image(obstacle.getImageFile());
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
		// TODO Auto-generated method stub
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

	public Observable getModel() {
		// TODO Auto-generated method stub
		return controller.getModel();
	}
}
