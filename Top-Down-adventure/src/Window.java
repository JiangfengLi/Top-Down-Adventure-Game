

import View.gameView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application{
	
	private static final int TICKS_PER_FRAME = 1;
	private gameView view;
	private boolean gameStarted = false;
	
	
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
				for(int i=0; i< TICKS_PER_FRAME; i++) {
					tick();
				}
			}
		}.start();
	}
	
	public void tick() {
		if(view.gameStarted()) {
			view.updateCharacterPosition();
			/*/////TO-DO//////
			Write methods to update everything that needs updated
			during a tick
			something like
			updateCharacterPosition
			updateEnemyPosition
			checkWeaponCollision
			checkDeath
			checkWin
			setChanged()
			notifyObservers()
			*/
		}
	}
}
