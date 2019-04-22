package Model;

/**
 * Abstract class for Obstacles, which are impassable object on
 * the map
 * 
 * @author Wes Rodgers
 *
 */
public abstract class Obstacle {

	private int[] corners;
	private String imageFile;
	
	/**
	 * Getter for the location of the obstacles corners,
	 * in order as left x value, bottom y value, right x value,
	 * top y value
	 * 
	 * @return an array of the corners of an obstacle
	 */
	public int[] getCorners() {
		return corners;
	}
	
	/**
	 * Getter for the filename of the obstacle's
	 * image file
	 * 
	 * @return A String of the filename of the obstacle's image
	 */
	public String getImageName() {
		return imageFile;
	}
}
