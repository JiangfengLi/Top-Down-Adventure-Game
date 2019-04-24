package Model;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

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
		
		CopyOnWriteArrayList<Obstacle> areaOneObstacles = new CopyOnWriteArrayList<Obstacle>();
		areaOneObstacles.add(new Grass(300, 300));
		
		/*******TODO**********
		 * All of the Areas will be different
		 * so they will have to be initialized
		 * individually.
		 */
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				CopyOnWriteArrayList<Enemy> areaOneEnemies = new CopyOnWriteArrayList<Enemy>();
				areaOneObstacles = new CopyOnWriteArrayList<Obstacle>();
				if(j == 0) {
					for(int k = 0; k < 15; k++) {
						areaOneObstacles.add(new Tree(k*100, 0));
					}
				}
				if(i == 0) {
					for(int k = 0; k < 20; k++) {
						areaOneObstacles.add(new Tree(-50, k*50));
					}
				}
				if(i == 2) {
					for(int k = 0; k < 20; k++) {
						areaOneObstacles.add(new Tree(949, k*50));
					}
				}
				if(j == 2) {
					for(int k = 0; k < 15; k++) {
						areaOneObstacles.add(new Tree(k*100, 616));
					}
				}
				if(i != 2 || j != 0) {
					areaOneEnemies.add(new Tank(800, 500));
					areaOneEnemies.add(new DPS(700, 500));
					areaOneEnemies.add(new Flier(600,500));
					areaOneObstacles.add(new Grass(300 + i*50, 300 + j*50));
					areaOneObstacles.add(new Rock(450 + i*50, 300 + j*50));
				}
				else {
					areaOneEnemies.add(new Boss(667, 333, true));
				}
				map[i][j] = new Area(areaOneEnemies, areaOneObstacles, i, j);
			}
		}
	}
	
	public GameMap(boolean dungeon) {
		map = new Area[3][2];
		
		//dungeon design here.
	}

	/**
	 * returns the starting area for the game
	 * 
	 * @return the Area that the game starts in
	 */
	public Area getStartArea() {
		return map[0][0];
	}

	/**
	 * returns the current area the game is in
	 * @param i x coordinate of the game area on the map
	 * @param j y coordinate of the game area on the map
	 * @return the area at [i][j];
	 */
	public Area getArea(int i, int j) {
		// TODO Auto-generated method stub
		return map[i][j];
	}
}
