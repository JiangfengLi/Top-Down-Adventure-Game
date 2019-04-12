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
public abstract class Area {
	private ArrayList<Object> obstacles;
	private ArrayList<Enemy> enemies;
	
	public ArrayList<Object> getObstacles(){
		return obstacles;
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
}
