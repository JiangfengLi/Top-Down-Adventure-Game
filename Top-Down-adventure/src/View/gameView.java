package View;

import Model.buttonMaker;
import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.stage.Stage;

public class gameView {
	private final String BACK_GROUND = "/style/back_cave.png";
	private static final int HEIGHT = 400;
	private static final int WIDTH = 600;
	private AnchorPane myPane;
	private Scene myScene;
	private Stage myStage;
	
	private GameController controller;
	
	public gameView() {
		myPane = new AnchorPane();
		myScene = new Scene(myPane,WIDTH,HEIGHT);
		myStage = new Stage();
		myStage.setScene(myScene);
		makeButton("PLAY NOW!",200,300);
		setBackground(BACK_GROUND);
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
		button.setLayoutX(x);
		button.setLayoutY(y);
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
	
	private void deathAnimation() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * checks to see if the weapon hit an enemy
	 */
	public void checkWeaponCollision() {
		
	}
}
