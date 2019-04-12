package Model;

import java.util.ArrayList;

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
