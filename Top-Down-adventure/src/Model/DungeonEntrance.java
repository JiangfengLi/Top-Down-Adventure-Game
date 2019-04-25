package Model;

import javafx.scene.image.Image;

public class DungeonEntrance extends Obstacle{

	public DungeonEntrance(int x, int y) {
		destructible = false;
		width = 48;
		height = 52;
		location = new int[2];
		location[0] = x;
		location[1] = y;
		imageFile = new Image("/style/dungeon entrance.png");
		topImage = false;
		topHeight = 0;
	}
}
