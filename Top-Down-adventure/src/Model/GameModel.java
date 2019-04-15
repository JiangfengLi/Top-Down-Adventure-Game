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
	
	private GameState gameState;
	
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

	/**
	 * moves the player character
	 * 
	 * @param xMovement the amount to move along the x-axis
	 * @param yMovement the amount to move along the y-axis
	 */
	public void updatePlayerPosition(int xMovement, int yMovement) {
		player.updatePosition(xMovement, yMovement);	
	}
	
	/**
	 * returns the Area that the player is currently in
	 * @return the Area that the player is currently in
	 */
	public Area getCurrentArea() {
		return currArea;
	}
	
}
