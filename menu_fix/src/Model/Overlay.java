package Model;

import javafx.scene.image.Image;

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
		lifeBarImage = new Image("/style/life bar.png");
		lifeBarWidth = 100;
		lifeBarHeight = 16;
		
		heartLocation[0] = 590;
		heartLocation[1] = 61;
		heartImage[0] = new Image("/style/full heart.png");
		heartImage[1] = new Image("/style/half heart.png");
		heartImage[2] = new Image("/style/empty heart.png");
		heartHeight = 20;
		heartWidth = 20;
		
		arrowLocation[0] = 330;
		arrowLocation[1] = 40;
		arrowImage = new Image("/style/inventory arrow.png");
		arrowWidth = 28;
		arrowHeight = 16;
		
		keyLocation[0] = 455;
		keyLocation[1] = 36;
		keyImage = new Image("/style/inventory key.png");
		keyWidth = 20;
		keyHeight = 20;
	}
	
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
