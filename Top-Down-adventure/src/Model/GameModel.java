package Model;

public class GameModel {
	
	private Player player;
	private Area currArea;
	private Area[][] map;
	
	public GameModel() {
		player = new Player();
		map = new Area[4][4];
	}

	public Player getPlayer() {
		return this.player;
	}
	

}
