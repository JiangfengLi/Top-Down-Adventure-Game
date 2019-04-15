package Model;

/**
 * Grass extends the Obstacle class, this is a destroyable
 * object, with collision until destroyed.
 * @author Wes Rodgers
 *
 */
public class Grass extends Obstacle{
	
	public Grass() {
		destructible = true;
	}
}
