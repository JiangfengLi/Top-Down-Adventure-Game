package View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Model.Area;
import Model.Enemy;
import Model.GameModel;
import Model.Obstacle;
import Model.Player;
import Model.buttonMaker;
import controller.GameController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
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
		int xMovement = dPressed ? 5 : 0;
		xMovement += aPressed ? -5 : 0;
		int yMovement = wPressed ? -5 : 0;
		yMovement += sPressed ? 5 : 0;
		
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
				wPressed = true;
			}
			if(e.getCode() == KeyCode.S) {
				sPressed = true;
			}
			
			if(e.getCode() == KeyCode.A) {
				aPressed = true;
			}
			if(e.getCode() == KeyCode.D) {
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
		model.addObserver(this);
		update(model, controller.getCurrentArea());		
	}

	@Override
	public void update(Observable model, Object area) {
		currentScreen = (Area) area;
		ArrayList<Obstacle> obstacles = ((Area) area).getObstacles();
		ArrayList<Enemy> enemies = ((Area) area).getEnemies();
		Player player = ((GameModel) model).getPlayer();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		//background = area's background
		for(Obstacle obstacle : obstacles) {
			//put this obstacle on top of background
		}
		for(Enemy enemy : enemies) {
			//put this enemy on top of background
		}
		gc.fillRect(player.getLocation()[0], player.getLocation()[1], 50, 100);
	}

	public boolean gameStarted() {
		// TODO Auto-generated method stub
		return gameStarted;
	}
}
