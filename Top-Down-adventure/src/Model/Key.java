package Model;


/**
 * Extends the item class and provides all of the fields for a key item
 * @author Wes Rodgers
 *
 */
public class Key extends Item {
	private static final long serialVersionUID = 1L;
	private boolean isBoss;
	
	public Key(int[] location, boolean boss) {
		this.location = location;
		this.height = 16;
		this.width = 14;
		this.isBoss = boss;
	}
	
	/**
	 * returns true if this is a bosskey, false otherwise.
	 * @return true if this is a bosskey, false otherwise.
	 */
	public boolean isBossKey() {
		return isBoss;
	}
}
