import java.awt.Point;


public abstract class Agent {
	
	public static final int MOVE_COST = 5;
	public static final int TURN_COST = 5;
	public static final int MAXIMUM_ENERGY = 1000;
	
	private static int idCount = 0;
	protected int id;
	private String name;
	boolean isDead;
	
	char displayChar = '@';
	char deadDisplayChar = '#';
	
	public enum Move {
		NORTH, SOUTH, EAST, WEST, NE, NW, SE, SW
	}
	
	Point position;
	int pinkEnergy = 1000, greenEnergy = 1000;
	
	public Agent(Point initialPosition) {
		this.position = initialPosition;
		this.id = idCount++;
	}
	
	protected void moveToPosition(Environment e, Point position) {
		if (e.canMove(position)) {
			//return if agent doesn't have energy to move
			if (pinkEnergy < MOVE_COST || greenEnergy < MOVE_COST) return;
			// todo remove output
			System.out.println(this + " moves to " + position.x + ", " + position.y);
			
			this.position = position;
			pinkEnergy -= MOVE_COST;
			greenEnergy -= MOVE_COST;
			testPulse();
		}
	}
	
	public void update(Environment e) {
		if (!isDead) {
			pinkEnergy -= TURN_COST;
			greenEnergy -= TURN_COST;
		}
		testPulse();
	}
	
	private void testPulse() {
		if (isDead) {
			System.out.println(this + " is dead");
		} else if (pinkEnergy <= 0 || greenEnergy <= 0) {
			isDead = true;
			System.out.println(this + " died");
		}
	}
	
	public void increaseGreenEnergy(int amount) {
		greenEnergy += amount;
		greenEnergy = greenEnergy > MAXIMUM_ENERGY ? MAXIMUM_ENERGY : greenEnergy;
	}
	
	public void increasePinkEnergy(int amount) {
		pinkEnergy += amount;
		pinkEnergy = pinkEnergy > MAXIMUM_ENERGY ? MAXIMUM_ENERGY : pinkEnergy;
	}

	public char getDisplayChar() {
		// TODO Auto-generated method stub
		return isDead ? deadDisplayChar : displayChar;
	}
}
