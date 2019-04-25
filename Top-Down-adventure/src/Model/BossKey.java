package Model;

public class BossKey extends Key{
	
	public BossKey(int[] location, boolean boss) {
		super(location, boss);
	}
	
	
	/**
	 * getter for arrow quantity
	 * @return the number of arrows in this drop
	 */
	public String toString() {
		return "BossKey";
	} 

}
