package Model;

import java.io.Serializable;

public class Data implements Serializable{
	 private static final long serialVersionUID = 1L;
	 private GameModel model;
	 public Data(GameModel model) {
		 this.model = model;
	 }
	 
	 public GameModel getModel() {
		 return this.model;
	 }
}
