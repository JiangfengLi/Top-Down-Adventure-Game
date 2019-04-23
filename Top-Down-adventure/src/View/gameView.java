package View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Model.Area;
import Model.Enemy;
import Model.GameModel;
import Model.GameObject;
import Model.Obstacle;
import Model.Player;
import Model.buttonMaker;
import controller.GameController;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class gameView implements Observer{
	private final String BACK_GROUND = "/style/back_cave.png";
	private static final int HEIGHT = 1000;
	private static final int WIDTH = 1500;
	private AnchorPane myPane;
	private Scene myScene;
	private Stage myStage;
	private Area currentScreen;
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
		/********NOTE********
		 * These are placeholder values, but 
		 * this is probably the easiest way to tell how
		 * much to move during a given turn
		 */
		int xMovement = dPressed ? 8 : 0;
		xMovement += aPressed ? -8 : 0;
		int yMovement = wPressed ? -8 : 0;
		yMovement += sPressed ? 8 : 0;
		
		controller.updatePlayerPosition(xMovement, yMovement);
	}
	
	/**
	 * updates the enemies' positions in the area
	 */
	public void updateEnemyPosition() {
	}
	
	/**
	 * checks for player death
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
	public void checkWeaponCollision() {
		
	}
	
	/**
	 * sets up listeners for key press and key release
	 * so we can tell when a combination of keys are pressed
	 * and move diagonally as appropriate. 
	 */
	public void setupMovementListeners() {
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
	
	public void setupMouseClickListeners() {
		myScene.setOnMouseClicked((e)->{
			if(e.getButton() == MouseButton.PRIMARY) {
				controller.swordAttack(canvas);
			}
			else if(e.getButton() == MouseButton.SECONDARY) {
				controller.bowAttack(canvas);
			}
		});
	}

	public void startGame() {
		gameStarted = true;
		canvas = new Canvas(WIDTH, HEIGHT);
		myScene = new Scene(new Group(canvas));
		myStage.setScene(myScene);
		GameModel model= new GameModel();
		controller = new GameController(model);
		currentScreen = controller.getCurrentArea();
		setupMovementListeners();
		setupMouseClickListeners();
		model.addObserver(this);
		update(model, controller.getCurrentArea());
	}

	@Override
	public void update(Observable model, Object area) {
		
		Player player = ((GameModel) model).getPlayer();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		currentScreen = (Area) area;
		ArrayList<Obstacle> obstacles = ((Area) area).getObstacles();
		ArrayList<Enemy> enemies = ((Area) area).getEnemies();
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		Image bg = new Image("/style/background.png");
		gc.drawImage(bg, 0, 0, WIDTH, HEIGHT);		
		
		for(Obstacle obstacle : obstacles) {
			Image image = new Image(obstacle.getImageFile()); 
			if(!obstacle.destroyed()) {
				gc.drawImage(image, 0,0,obstacle.getWidth(), obstacle.getHeight(), obstacle.getLocation()[0], 
						obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getHeight());
			}
			if(obstacle.destroyed()) {
				gc.drawImage(image, obstacle.getWidth()*obstacle.lastFrame(),0, obstacle.getWidth(), obstacle.getHeight(),
						obstacle.getLocation()[0], obstacle.getLocation()[1], obstacle.getWidth(), obstacle.getHeight());
			}
		}
		for(Enemy enemy : enemies) {
			//put this enemy on top of background
		}
		gc.setFill(Paint.valueOf("BLACK"));
		
		//draw Player
		Image playerImage = new Image(player.getImageArray()[player.getDirection()-1]);
		if(wPressed || aPressed || sPressed || dPressed) {
			gc.drawImage(playerImage, 30*((getGameClock()%16)/4), 0, 29, 24, player.getLocation()[0], player.getLocation()[1], 58, 48);
		}
		else {
			gc.drawImage(playerImage, 0, 0, 29, 24, player.getLocation()[0], player.getLocation()[1], 58, 48);
		}
		
		//animations
		ArrayList<GameObject> finished = new ArrayList<GameObject>();
		for(GameObject obj : ((GameModel) model).getAnimations()) {
			int currFrame = ((Obstacle) obj).destroyedFrame()/2;
			Image image = new Image(obj.getImageFile());
			gc.drawImage(image, 50*currFrame, 0, 50, 50, obj.getLocation()[0], obj.getLocation()[1], 50, 50);
			if(currFrame >= 9) {
				finished.add(obj);
			}
		}
		((GameModel) model).getAnimations().removeAll(finished);
	}

	private int getGameClock() {
		return controller.getGameClock();
	}

	public boolean gameStarted() {
		// TODO Auto-generated method stub
		return gameStarted;
	}

	public void incrementGameClock() {
		controller.incrementGameClock();		
	}
}
