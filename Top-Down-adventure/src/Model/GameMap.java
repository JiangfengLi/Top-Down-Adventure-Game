package Model;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Composed of a 3x3 array of Areas
 * Initializes 1 of each Area in the correct
 * spot on the map, has methods to access various
 * game constants.
 * 
 * @author Wes Rodgers
 */
public class GameMap implements Serializable{
	private static final long serialVersionUID = 1L;
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
					for(int k = 0; k < 13; k++) {
						areaObstacles.add(new Tree(-50, k*50));
					}
				}
				if(i == 2 || (i==0 && (j==0 || j==1))) {
					for(int k = 0; k < 13; k++) {
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
					areaObstacles.add(new Rock(200, 200));
					areaObstacles.add(new Grass(700, 590));
					areaEnemies.add(new DPS(499, 333));
				}
				
				if(i==0 && j==1) {
					for(int x=0; x<9; x++) {
						areaObstacles.add(new Rock(275, 100 + x*50));
						areaObstacles.add(new Rock(675, 100 + x*50));
						if(x!=4) areaObstacles.add(new Rock(275 + x*50, 100));
						if(x!=4) areaObstacles.add(new Rock(275 + x*50, 500));
					}
					for(int x=0; x<5; x++) {
						if(x!=2) areaObstacles.add(new Rock(375, 200 + x*50));
						if(x!=2) areaObstacles.add(new Rock(575, 200 + x*50));
						areaObstacles.add(new Rock(375 + x*50, 200));
						areaObstacles.add(new Rock(375 + x*50, 400));
					}
					areaObstacles.add(new Rock(525, 150));
					areaObstacles.add(new Rock(325, 350));
					areaObstacles.add(new Grass(475, 100));
					areaObstacles.add(new Grass(475, 500));
					areaObstacles.add(new Grass(375, 300));
					areaObstacles.add(new Grass(475, 300));
					areaObstacles.add(new Grass(575, 300));
					
					areaEnemies.add(new Flier(100, 300));
					areaEnemies.add(new Flier(850, 300));
				}
				
				if(i==0 && j==2) {
					areaObstacles.add(new Tree(949, -50));
					
					areaObstacles.add(new Rock(499, 333));
					areaEnemies.add(new Tank(499, 433));
					areaEnemies.add(new Flier(100, 590));
					areaObstacles.add(new Grass(600, 333));
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
					
					areaEnemies.add(new DPS(100, 400));
					areaEnemies.add(new DPS(899, 400));
					areaEnemies.add(new Tank(500, 400));
				}
				
				if(i==1 && j==2) {
					areaObstacles.add(new Tree(-50, -50));
					areaObstacles.add(new Tree(949, -50));
					
					for(int x=0; x<9; x++) {
						areaObstacles.add(new Rock(499, 100 + 50*x));
					}
					
					areaObstacles.add(new Grass(449, 150));
					areaObstacles.add(new Grass(549, 150));
					areaObstacles.add(new Grass(399, 200));
					areaObstacles.add(new Grass(599, 200));
					areaObstacles.add(new Grass(349, 250));
					areaObstacles.add(new Grass(649, 250));
				}
				
				//this is the winning room
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
					
					areaObstacles.add(new Rock(150, 200));
					areaEnemies.add(new Tank(300, 350));
					areaObstacles.add(new Rock(600, 450));
					areaEnemies.add(new Tank(800, 600));
				}
				
				
				if(i==2 && j==2) {
					for(int k=0; k<5; k++) {
						areaObstacles.add(new Tree(100*k, -50));
					}
					for(int k=0; k<14; k++) {
						areaObstacles.add(new Tree(500, -50));
						areaObstacles.add(new Tree(500, 50*k));
					}
					
					//left side
					areaObstacles.add(new Grass(100, 150));
					areaObstacles.add(new Grass(300, 150));
					areaObstacles.add(new Grass(100, 550));
					areaObstacles.add(new Grass(300, 550));
					areaEnemies.add(new Flier(100, 150));
					areaEnemies.add(new Flier(300, 150));
					areaEnemies.add(new Flier(100, 550));
					areaEnemies.add(new Flier(300, 550));
					
					
					//right side
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
	 * @param dungeon any boolean, just overrides the constructor to make a dungeon
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
					
					areaEnemies.add(new Tank(225, 580));
					areaEnemies.add(new Tank(800, 560));
					areaEnemies.add(new DPS(499, 333));
				}
				
				if(i==0 && j==1) {
					areaObstacles.add(new Rock(949, 0));
					for(int k=0; k<20; k++) {
						areaObstacles.add(new Rock(50*k, 616));
						areaObstacles.add(new Rock(0, k*50));
					}
					
					areaEnemies.add(new Flier(599, 333));
					areaEnemies.add(new Flier(399, 333));
					areaEnemies.add(new Tank(499, 433));
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
					areaEnemies.add(new DPS(800, 150));
					areaEnemies.add(new DPS(599, 300));
					areaEnemies.add(new DPS(299, 400));
				}
				map[i][j] = new Area(areaEnemies, areaObstacles, i, j);
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
