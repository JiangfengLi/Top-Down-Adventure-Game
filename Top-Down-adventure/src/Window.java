import View.gameView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application{
	
	private static final int ticksPerFrame = 5;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnimationTimer at = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for(int i=0; i< ticksPerFrame; i++) {
					tick();
				}
			}
		};
		
		try {
			gameView view = new gameView();
			primaryStage = view.getStage();
			primaryStage.setTitle("Adventure game");
			primaryStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		at.start();
	}
	
	public void tick() {
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
