package Model;


/**
 * extends item, this is a possible enemy drop
 * and restores player health.
 * @author Wes Rodgers
 *
 */
public class Heart extends Item {
	private static final long serialVersionUID = 1L;
	private int HP;
	
	public Heart(int[] location) {
		this.width = 40;
		this.height = 45;
		this.location = location;
		this.HP = System.nanoTime()%3 >= 2 ? 2 : 1;
		this.topHeight = 0;
	}
	
	/**
	 * returns the amount of HP that this drop heals
	 * @return HP, an integer telling us how much this drops heals for.
	 */
	public int getHP() {
		return HP;
	} 
}
