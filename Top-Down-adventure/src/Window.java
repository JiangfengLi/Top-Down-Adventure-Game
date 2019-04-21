

import java.util.ArrayList;

import Model.GameModel;
import View.gameView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application{
	
	private gameView view;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			
		view = new gameView();
		primaryStage = view.getStage();
		primaryStage.setTitle("Adventure game");
		primaryStage.show();		
		new AnimationTimer() {
			@Override
			public void handle(long now) {				
				tick();
			}
		}.start();
	}
	
	/**
	 * heart of the game engine, calls various methods that do all of the things
	 * that need to be done each tick of the game clock
	 */
	public void tick() {
		if(view.gameStarted()) {
			view.incrementGameClock();
			//if(view.getGameClock()%2 > 0) {
				view.updateCharacterPosition();
				view.updateEnemyPosition();
				view.updateEnemyCollision();
				view.checkDeath();
				/*/////TO-DO//////
				Write methods to update everything that needs updated
				during a tick
				something like
				updateBoss (since it will have a different movement pattern than a standard enemy.
				checkDeath
				checkWin
				setChanged()
				notifyObservers()
				*/
			//}
		}
	}
}
