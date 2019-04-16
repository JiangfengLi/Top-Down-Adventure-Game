package Model;

import java.util.ArrayList;

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
		map = new Area[3][3];
		
		ArrayList<Obstacle> areaOneObstacles = new ArrayList<Obstacle>();
		ArrayList<Enemy> areaOneEnemies = new ArrayList<Enemy>();
		
		map[0][0] = new Area(areaOneEnemies, areaOneObstacles);
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
		return map[0][0];
	}

}
