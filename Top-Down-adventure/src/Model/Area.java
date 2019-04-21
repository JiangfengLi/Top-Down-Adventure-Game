package Model;

import java.util.ArrayList;

/**
 * This class holds the things that are present on one screen of the game.
 * We will have at least 10 of these, one for each of the 9 screens of the overland
 * and at least 1 for the dungeon.
 * 
 * @author Wes Rodgers
 *
 */
public class Area {
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Enemy> enemies;
	private int[] coords = new int[2];
	
	public Area(ArrayList<Enemy> enemies, ArrayList<Obstacle> obstacles, int x, int y) {
		this.obstacles = obstacles;
		this.enemies = enemies;
		coords[0] = x;
		coords[1] = y;
	}
	
	/**
	 * getter for the obstacles in an area
	 * 
	 * @return an ArrayList of the obstacles in an area
	 */
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}
	
	/**
	 * getter for the enemies in an area
	 * 
	 * @return an ArrayList of the enemies in an area
	 */
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}

	/**
	 * returns where on the GameMap this area is located
	 * @return (x,y) coordinates for this area's location on the GameMap
	 */
	public int[] getCoords() {
		// TODO Auto-generated method stub
		return coords;
	}
}
