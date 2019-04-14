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
}
