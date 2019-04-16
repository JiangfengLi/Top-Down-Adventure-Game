package Model;

public abstract class GameObject {

	protected String imageFile;
	protected int[] location;
	protected int height;
	protected int width;
	
	/**
	 * returns the object's height
	 * @return the object's height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * returns the objects width
	 * @return the objects width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * returns the filename / location of this
	 * object's image file
	 * 
	 * @return the object's image file's path.
	 */
	public String getImageFile() {
		return imageFile;
	}
	
	/**
	 * returns the location on the board of
	 * this object
	 * 
	 * @return the objects location on the board
	 */
	public int[] getLocation() {
		return location;
	}
	
	/**
	 * updates the object's position on the board
	 * @param xOffset the amount to increment/decrement x coordinate by
	 * @param yOffset the amount to increment/decrement y coordinate by
	 */
	public void updatePosition(int xOffset, int yOffset) {
		location[0] += xOffset;
		location[1] += yOffset;
	}
	
	/**
	 * sets the object's anchor point to a specific location
	 * 
	 * @param x the object's x coordinate
	 * @param y the object's y coordinate
	 */
	public void setLocation(int x, int y) {
		location[0] = x;
		location[1] = y;
	}
}
