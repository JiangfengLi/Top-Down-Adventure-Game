package Model;

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
	public int length;
	
	/**
	 * this constructor sets up the actual map design by creating a bunch of
	 * objects in their correct positions and passing them to Area constructors
	 */
	public GameMap() {
		map = new Area[3][3];
		length = 3;
		/*******TODO**********
		 * All of the Areas will be different
		 * so they will have to be initialized
		 * individually.
		 */
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				CopyOnWriteArrayList<Enemy> areaEnemies = new CopyOnWriteArrayList<Enemy>();
				CopyOnWriteArrayList<Obstacle> areaObstacles = new CopyOnWriteArrayList<Obstacle>();
				
				//these first checks just add a border around the exterior of the map
				if(j == 0) {
					for(int k = 0; k < 15; k++) {
						areaObstacles.add(new Tree(k*100, -50));
					}
				}
				if(i == 0 || (i==1 && (j==0 || j==1))) {
					for(int k = 0; k < 20; k++) {
						areaObstacles.add(new Tree(-50, k*50));
					}
				}
				if(i == 2 || (i==0 && (j==0 || j==1))) {
					for(int k = 0; k < 20; k++) {
						areaObstacles.add(new Tree(949, k*50));
					}
				}
				if(j == 2) {
					for(int k = 0; k < 15; k++) {
						areaObstacles.add(new Tree(k*100, 610));
					}
				}
				
				//from here on each if statement determines the stuff
				//to be put in a single map space
				if(i==0 && j==0) {
					
				}
				if(i==0 && j==1) {
					
				}
				if(i==0 && j==2) {
					areaObstacles.add(new Tree(949, -50));
				}
				if(i==1 && j==0) {
					areaEnemies.add(new Boss(700, 200, true));
					for(int k=0; k<4; k++) {
						areaObstacles.add(new Tree(100*k, 610));
					}
					for(int k=0; k<4; k++) {
						areaObstacles.add(new Tree(899-(100*k), 610));
					}
					areaObstacles.add(new Tree(499, 610));
				}
				if(i==1 && j==1) {
					areaObstacles.add(new Tree(949, -50));
					areaObstacles.add(new Tree(949, 616));
					for(int k=0; k<4; k++) {
						areaObstacles.add(new Tree(100*k, -50));
					}
					for(int k=0; k<4; k++) {
						areaObstacles.add(new Tree(899-(100*k), -50));
					}
					areaObstacles.add(new Tree(499, -50));
					areaObstacles.add(new Door(400, -16));
				}
				if(i==1 && j==2) {
					areaObstacles.add(new Tree(-50, -50));
					areaObstacles.add(new Tree(949, -50));
				}
				if(i==2 && j==0) {
					for(int k=0; k<15; k++) {
						areaObstacles.add(new Tree(100*k, 616));
					}
				}
				if(i==2 && j==1) {
					for(int k=0; k<6; k++) {
						areaObstacles.add(new Tree(100*k, 610));
					}
					for(int k=0; k<14; k++) {
						areaObstacles.add(new Tree(100*k, -50));
					}
				}
				if(i==2 && j==2) {
					for(int k=0; k<5; k++) {
						areaObstacles.add(new Tree(100*k, -50));
					}
					for(int k=0; k<14; k++) {
						areaObstacles.add(new Tree(500, -50));
						areaObstacles.add(new Tree(500, 50*k));
					}
					
					areaObstacles.add(new Rock(750, 525));
					areaObstacles.add(new Rock(800, 525));
					areaObstacles.add(new Rock(800, 425));
					areaObstacles.add(new Rock(700, 425));
					areaObstacles.add(new Rock(700, 525));
					areaObstacles.add(new Rock(700,475));
					areaObstacles.add(new Rock(800, 475));
					areaObstacles.add(new DungeonEntrance(750, 475));
					areaObstacles.add(new Grass(750, 425));
				}
				map[i][j] = new Area(areaEnemies, areaObstacles, i, j);
			}
		}
	}
	
	/**
	 * this constructor sets up the design of the dungeon map in the same manner
	 * as the previous did for the overland map
	 * @param dungeon
	 */
	public GameMap(boolean dungeon) {
		length = 2;
		map = new Area[2][2];
		for(int i=0; i<2; i++) {
			for(int j=0; j<2; j++) {
				CopyOnWriteArrayList<Enemy> areaEnemies = new CopyOnWriteArrayList<Enemy>();
				CopyOnWriteArrayList<Obstacle> areaObstacles = new CopyOnWriteArrayList<Obstacle>();
				
				if(i==0 && j==0) {
					areaObstacles.add(new DungeonEntrance(400, 0));
					for(int k=0; k<20; k++) {
						areaObstacles.add(new Rock(450+(k*50), 0));
						areaObstacles.add(new Rock(350-(k*50), 0));
						areaObstacles.add(new Rock(0, k*50));
						areaObstacles.add(new Rock(949, k*50));
					}
				}
				if(i==0 && j==1) {
					areaObstacles.add(new Rock(949, 0));
					for(int k=0; k<20; k++) {
						areaObstacles.add(new Rock(50*k, 616));
						areaObstacles.add(new Rock(0, k*50));
					}
				}
				if(i==1 && j==0) {
					areaEnemies.add(new Boss(449, 283, false));
					for(int k=0; k<20; k++) {
						areaObstacles.add(new Rock(k*50, 0));
						areaObstacles.add(new Rock(0, k*50));
						areaObstacles.add(new Rock(949, k*50));
					}
				}
				if(i==1 && j==1) {
					areaObstacles.add(new Rock(0, 0));
					for(int k=0; k<20; k++) {
						areaObstacles.add(new Rock(50*k, 616));
						areaObstacles.add(new Rock(949, k*50));
					}
				}
				map[i][j] = new Area(areaEnemies, areaObstacles, i, j);
				
			}
		}
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
