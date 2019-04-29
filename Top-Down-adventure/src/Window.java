import View.gameView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage; 

/**
 * This is the "main" class of the game. Creates the view, starts the game engine
 * has main()
 * 
 * @author TianZeHu, Wes Rodgers
 *
 */
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
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {				
				tick();
			}
		};
		animationTimer.start();
	    view.setAnimationTimer(animationTimer);
	}
	
	/**
	 * calls various methods that do all of the things
	 * that need to be done each tick of the game clock
	 */
	public void tick() {
		if(view.gameStarted()) {
			view.incrementGameClock();
			if(view.getGameClock()%4 == 0) {
				view.updateCharacterPosition();
				view.updateEnemyPosition();
				view.updateEnemyCollision();
				view.updateProjectiles();
				view.checkDeath();
				view.checkWin();
			}
		}
	}
}
