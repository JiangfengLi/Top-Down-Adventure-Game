import View.gameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			gameView view = new gameView();
			primaryStage = view.getStage();
			primaryStage.setTitle("Adventure game");
			primaryStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
