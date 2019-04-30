package Model;

import javafx.scene.image.Image;

/**
 * provides fields to store where different parts of the HUD need to be drawn
 * @author Wes Rodgers
 *
 */
public class Overlay {

	double[] lifeBarLocation = new double[2];
	double[] heartLocation = new double[2];
	double[] arrowLocation = new double[2];
	double[] keyLocation = new double[2];
	
	double lifeBarWidth;
	double lifeBarHeight;
	double heartWidth;
	double heartHeight;
	double arrowWidth;
	double arrowHeight;
	double keyWidth;
	double keyHeight;
	
	Image lifeBarImage;
	Image[] heartImage = new Image[3];
	Image arrowImage;
	Image keyImage;
	
	public Overlay() {
		lifeBarLocation[0] = 580;
		lifeBarLocation[1] = 40;
		lifeBarImage = new Image("/life bar.png");
		lifeBarWidth = 100;
		lifeBarHeight = 16;
		
		heartLocation[0] = 590;
		heartLocation[1] = 61;
		heartImage[0] = new Image("/full heart.png");
		heartImage[1] = new Image("/half heart.png");
		heartImage[2] = new Image("/empty heart.png");
		heartHeight = 20;
		heartWidth = 20;
		
		arrowLocation[0] = 330;
		arrowLocation[1] = 40;
		arrowImage = new Image("/inventory arrow.png");
		arrowWidth = 28;
		arrowHeight = 16;
		
		keyLocation[0] = 455;
		keyLocation[1] = 36;
		keyImage = new Image("/inventory key.png");
		keyWidth = 20;
		keyHeight = 20;
	}
	
	/**
	 * given a string representing a specific part of the overlay, 
	 * returns that image
	 * @param s a string "life", "full", "half", "empty", "arrow", "key"
	 * @return the image relating to the passed in string.
	 */
	public Image getImage(String s) {
		switch(s) {
			case "life":
				return lifeBarImage;
			case "full":
				return heartImage[0];
			case "half":
				return heartImage[1];
			case "empty":
				return heartImage[2];
			case "arrow":
				return arrowImage;
			case "key":
				return keyImage;
		}
		return null;
	}
	
	/**
	 * given a string for the item, returns that items location as a double[]
	 * @param s a string representing "life", "heart", "arrow", "key"
	 * @return the location of the passed in aspect of the overlay
	 */
	public double[] getLocation(String s) {
		switch(s) {
		case "life":
			return lifeBarLocation;
		case "heart":
			return heartLocation;
		case "arrow":
			return arrowLocation;
		case "key":
			return keyLocation;
		}
		return null;
	}
	
	/**
	 * returns the width of the overlay attribute related to the passed in string
	 * @param s "life", "heart", "arrow", or "key"
	 * @return the width of the item based on the passed in string
	 */
	public double getWidth(String s) {
		switch(s) {
		case "life":
			return lifeBarWidth;
		case "heart":
			return heartWidth;
		case "arrow":
			return arrowWidth;
		case "key":
			return keyWidth;
		}
		return 0;
	}
	
	/**
	 * returns the height of the overlay attribut related to the passed in string
	 * @param s "life", "heart", "arrow", or "key"
	 * @return the height of the item based on the passed in string.
	 */
	public double getHeight(String s) {
		switch(s) {
		case "life":
			return lifeBarHeight;
		case "heart":
			return heartHeight;
		case "arrow":
			return arrowHeight;
		case "key":
			return keyHeight;
		}
		return 0;
	}
}