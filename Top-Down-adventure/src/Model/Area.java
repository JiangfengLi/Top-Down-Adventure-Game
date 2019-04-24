package Model;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class holds the things that are present on one screen of the game.
 * We will have at least 10 of these, one for each of the 9 screens of the overland
 * and at least 1 for the dungeon.
 * 
 * @author Wes Rodgers
 *
 */
public class Area {
	private CopyOnWriteArrayList<Obstacle> obstacles;
	private CopyOnWriteArrayList<Enemy> enemies;
	private int[] coords = new int[2];
	private ArrayList<Character> projectiles;
	private ArrayList<Item> loot;
	private Boss boss;
	
	public Area(CopyOnWriteArrayList<Enemy> areaOneEnemies, CopyOnWriteArrayList<Obstacle> areaOneObstacles, int x, int y) {
		this.obstacles = areaOneObstacles;
		this.enemies = areaOneEnemies;
		this.projectiles = new ArrayList<Character>();
		this.loot = new ArrayList<Item>();
		coords[0] = x;
		coords[1] = y;
		for(Enemy enemy : areaOneEnemies) {
			if(enemy instanceof Boss) {
				this.boss = (Boss) enemy;
			}
		}
	}
	
	/**
	 * adds the passed in Item to the Area's loot list
	 * @param loot the Item that we want to add to the Area's loot list
	 */
	public void addLoot(Item loot) {
		this.loot.add(loot);
	}
	
	/**
	 * getter for the area's loot list
	 * @return an ArrayList<Item> of the Area's loot items
	 */
	public ArrayList<Item> getLoot(){
		return loot;
	}
	
	/**
	 * removes a loot item from the area's loot list
	 * @param loot the item to be removed from this Area's loot list.
	 */
	public void removeLoot(Item loot) {
		if(this.loot.contains(loot)) this.loot.remove(loot);
	}
	
	/**
	 * getter for the obstacles in an area
	 * 
	 * @return an ArrayList of the obstacles in an area
	 */
	public CopyOnWriteArrayList<Obstacle> getObstacles(){
		return obstacles;
	}
	
	/**
	 * getter for the enemies in an area
	 * 
	 * @return an ArrayList of the enemies in an area
	 */
	public CopyOnWriteArrayList<Enemy> getEnemies(){
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

	/**
	 * adds the specified projectile to the area
	 * @param projectile
	 */
	public void addProjectile(Character projectile) {
		projectiles.add(projectile);
	}
	
	public ArrayList<Character> getProjectiles() {
		// TODO Auto-generated method stub
		return projectiles;
	}

	public void removeProjectile(Character projectile) {
		projectiles.remove(projectile);
		
	}

	public Boss getBoss() {
		return boss;
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);		
	}
}
