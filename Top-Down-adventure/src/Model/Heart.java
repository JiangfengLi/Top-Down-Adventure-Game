package Model;

/**
 * extends item, this is a possible enemy drop
 * and restores player health.
 * @author Wes Rodgers
 *
 */
public class Heart extends Item {
	private int HP;
	
	public Heart(int[] location) {
		this.imageFile = "";
		this.location = location;
		this.HP = System.nanoTime()%3 > 2 ? 2 : 1;
	}

	/**
	 * returns the amount of HP that this drop heals
	 * @return HP, an integer telling us how much this drops heals for.
	 */
	public int getHP() {
		// TODO Auto-generated method stub
		return HP;
	} 
}
