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
		areaOneObstacles.add(new Grass(300, 300));
		ArrayList<Enemy> areaOneEnemies = new ArrayList<Enemy>();
		
		/*******TODO**********
		 * All of the Areas will be different
		 * so they will have to be initialized
		 * individually.
		 */
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				areaOneObstacles = new ArrayList<Obstacle>();
				if(i == 0) {
					for(int k = 0; k < 20; k++) {
						areaOneObstacles.add(new Grass(0, k*50));
					}
				}
				if(j == 0) {
					for(int k = 0; k < 30; k++) {
						areaOneObstacles.add(new Grass(k*50, 0));
					}
				}
				if(i == 2) {
					for(int k = 0; k < 20; k++) {
						areaOneObstacles.add(new Grass(1450, k*50));
					}
				}
				if(j == 2) {
					for(int k = 0; k < 30; k++) {
						areaOneObstacles.add(new Grass(k*50, 950));
					}
				}
				areaOneObstacles.add(new Grass(300 + i*50, 300 + j*50));
				map[i][j] = new Area(areaOneEnemies, areaOneObstacles, i, j);
			}
		}
	}

	/**
	 * returns the starting area for the game
	 * 
	 * @return the Area that the game starts in
	 */
	public Area getStartArea() {
		return map[0][0];
	}

	public Area getArea(int i, int j) {
		// TODO Auto-generated method stub
		return map[i][j];
	}
}
