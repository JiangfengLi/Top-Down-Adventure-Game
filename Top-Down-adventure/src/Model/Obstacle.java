package Model;

/**
 * Abstract class for Obstacles, which are impassable object on
 * the map
 * 
 * @author Wes Rodgers
 *
 */
public abstract class Obstacle extends GameObject {

	protected boolean destructible;
	protected boolean destroyed = false;
	protected boolean animationDone = false;
	protected int destroyedFrame = 0;
	protected int lastFrame;
	protected boolean topImage;
	
	/**
	 * returns topImage field
	 * @return true if the obstacle has a top image, false otherwise
	 */
	public boolean hasTopImage() {
		return topImage;
	}
	
	/**
	 * getter for the obstacle's destructible flag
	 * @return true if the obstacle is destructible, false otherwise
	 */
	public boolean isDestructible() {
		return destructible;
	}

	/**
	 * returns true once the object has been destroyed
	 * @return true if the object has been destroyed, false otherwise.
	 */
	public boolean destroyed() {
		return destroyed;
	}

	/**
	 * sets the destroyed variable to true
	 */
	public void toggleDestroyed() {
		destroyed = true;
	}
	
	/**
	 * tells the obstacle to stop playing its destruction animation
	 */
	public void endAnimation() {
		animationDone = true;
	}
	
	/**
	 * returns whether the destruction animation is done yet or not
	 * @return true if the destruction animation is over, false otherwise
	 */
	public boolean animationDone() {
		return animationDone;
	}
	
	/**
	 * increments the current frame of the destroyed animation, returns that frame
	 * @return
	 */
	public int destroyedFrame() {
		destroyedFrame++;
		return destroyedFrame;
	}

	/**
	 * returns the last frame as that is the post destruction image
	 * @return the frame of the post-destruction image
	 */
	public int lastFrame() {
		return lastFrame;
	}
}
