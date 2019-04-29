package Model;

import View.gameView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.MusicPlayer;

public class WinScene {

    private final String BACK_GROUND = "/style/title.png";
    private static final int HEIGHT = 400;
    private static final int WIDTH = 500;
    private gameView view;
    private AnchorPane myPane;
    private Scene myScene;
    private Stage myStage;
    private MusicPlayer backgroundMusic;

    public WinScene(gameView view) {

        this.view = view;
        myPane = new AnchorPane();
        myScene = new Scene(myPane,WIDTH,HEIGHT);
        myStage = new Stage();
        myStage.setScene(myScene);

        makeButton("NEXT!",200,283);
        backgroundMusic = new MusicPlayer();
        backgroundMusic.playMusic("/style/Backgroundmusic/titlemusic.wav", 1);
        setBackground(BACK_GROUND);

        myStage.setResizable(false);
        myStage.show();
    }

    private void makeButton(String text, int x, int y) {
        Button button = new Button(text);
        myPane.getChildren().add(button);
        button.setPrefWidth(190);
        button.setPrefHeight(46);
        button.setLayoutX(x);
        button.setLayoutY(y);
        setClicked(button);
    }

    public void setClicked(Button button) {
        button.setOnMouseReleased((e)->{
            backgroundMusic.stopMusic();
            view.startGame();
            myStage.close();
        });
    }

    private void setBackground(String url) {
        Image back = new Image(BACK_GROUND);
        BackgroundImage backGround = new BackgroundImage(back, null, null, BackgroundPosition.DEFAULT, new BackgroundSize(999, 666, false, false, false, true));
        myPane.setBackground(new Background(backGround));
    }
}
