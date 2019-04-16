package Model;

import java.util.Observable;

/**
 * The main model class for the game,
 * has fields and methods to access and update
 * them for all of the various parts of the model
 * paradigm
 * 
 * @author Wes Rodgers
 */

public class GameModel extends Observable {
	
	private Player player;
	private Area currArea;
	private GameMap map;
	
	public GameModel() {
		player = new Player();
		map = new GameMap();
		currArea = map.getStartArea();
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers(currArea);
	}
	
	public void setPlayerPosition(int x, int y) {
		player.setLocation(x, y);
		setChanged();
		notifyObservers(currArea);
	}
	/**
	 * returns the Area that the player is currently in
	 * @return the Area that the player is currently in
	 */
	public Area getCurrentArea() {
		return currArea;
	}
	
	public void shiftCurrentArea(int[] area) {
		int x = currArea.getCoords()[0];
		int y = currArea.getCoords()[1];
		currArea = map.getArea(x + area[0], y + area[1]);
		setChanged();
		notifyObservers(currArea);
	}
	
}
