package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import View.gameView;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class buttonMaker extends Button{
	
	private final String OFF_CLICK = "-fx-background-image:url('/style/blue_button04.png');";
	private final String ON_CLICK = "-fx-background-image:url('/style/blue_button03.png');";
	private final String FONT_STYLE = "src/style/Kenney Pixel.ttf";
	
	public buttonMaker(String name) {
		setText(name);
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_STYLE), 25));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPrefWidth(190);
		setPrefHeight(46);
		setStyle(OFF_CLICK);
		buttonListener();
	}

	
	
	private void setClickedStyle() {
		setStyle(ON_CLICK);
		setPrefHeight(44);
		// when click the button, make the button 3 pixel lower than initial position
		setLayoutY(getLayoutY()+3);
	}
	
	private void setReleasedStyle() {
		setStyle(OFF_CLICK);
		setPrefHeight(46);
		setLayoutY(getLayoutY()-3);
	}
	
	private void buttonListener() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			/**
			 * when press the button
			 */
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setClickedStyle();
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setReleasedStyle();
				}
			}
		});
		
		// when the mouse move onto the button area, add drop shadow
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
				
			}
			
		});
		
		//when the mouse exit the button area, clean the drop shadow
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
			}
		});
	}
}
