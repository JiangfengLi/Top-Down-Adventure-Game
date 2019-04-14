package Model;

/**
 * Composed of a 4x4 array of Areas
 * Initializes 1 of each Area in the correct
 * spot on the map, has methods to access various
 * game constants.
 * 
 * @author Wes Rodgers
 */
public class GameMap {
	
	private Area[][] map;
	
	public GameMap() {
		map = new Area[4][4];
		
		/*******TODO**********
		 * All of the Areas will be different
		 * so they will have to be initialized
		 * individually.
		 */
	}

	/**
	 * returns the starting area for the game
	 * 
	 * @return the Area that the game starts in
	 */
	public Area getStartArea() {
		return null;
	}

}
