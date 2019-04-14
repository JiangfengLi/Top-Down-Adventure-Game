package controller;

import Model.Enemy;
import Model.GameModel;

/**
 * The controller part of the MVC paradigm for the game.
 * 
 * @author Wes Rodgers
 *
 */
public class GameController {

	private GameModel model;
	
	public GameController(GameModel model) {
		this.model = model;
	}

	public boolean playerDead() {
		return model.getPlayer().isDead();
	}
	
}
