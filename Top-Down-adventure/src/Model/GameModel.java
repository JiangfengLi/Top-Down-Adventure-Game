package Model;

/**
 * The main model class for the game,
 * has fields and methods to access and update
 * them for all of the various parts of the model
 * paradigm
 * 
 * @author Wes Rodgers
 */

public class GameModel {
	
	private Player player;
	private Area currArea;
	private GameMap map;
	
	public GameModel() {
		player = new Player();
		map = new GameMap();
		currArea = map.getStartArea();
	}

	/**
	 * getter for the Player
	 * 
	 * @return the Player object
	 */
	public Player getPlayer() {
		return this.player;
	}
	
}
