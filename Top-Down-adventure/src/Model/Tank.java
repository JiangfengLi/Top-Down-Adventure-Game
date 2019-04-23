package Model;

public class Tank extends Enemy{
	
	public Tank() {
		currentHP = 4;
		maxHP = 4;
		damage = 1;
		speed = 5;
	}

	@Override
	public boolean playerIsVisible() {
		// TODO Auto-generated method stub
		return false;
	}

}
