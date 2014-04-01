package agents;

import java.awt.Point;

import environment.Environment;

/**
 * representation of an agent that exist in an environment.
 * 
 * @author DinoRatcliffe
 *
 */
public abstract class Agent {
	
	public static final int MOVE_COST = 5;	//cost of moving one space
	public static final int TURN_COST = 5;	//cost of staying alive for a turn
	public static final int MAXIMUM_ENERGY = 1000; // the maximum energy an agent can hold of a given resource
	
	//unique id
	private static int idCount = 0;
	protected int id;
	
	private String name;
	boolean isDead;
	
	//visual representation of an agent
	char displayChar = '@';
	char deadDisplayChar = '#';
	
	public Point position;	// current position of agent
	int pinkEnergy = 1000, greenEnergy = 1000;	//current energy levels
	
	/**
	 * simple constructor 
	 * 
	 * @param initialPosition
	 */
	public Agent(Point initialPosition) {
		this.position = initialPosition;
		this.id = idCount++;
	}
	
	/**
	 * moves the agent to a given position, decrements energy levels based on cost.
	 * @param e			the environment that the agent is moving in
	 * @param position  the position the agent would like to move to
	 */
	protected void moveToPosition(Environment e, Point position) {
		if (e.canMove(position)) {
			//return if agent doesn't have energy to move
			if (pinkEnergy < MOVE_COST || greenEnergy < MOVE_COST) return;
			System.out.println(this + " moves to " + position.x + ", " + position.y);
			
			this.position = position;
			pinkEnergy -= MOVE_COST;
			greenEnergy -= MOVE_COST;
			testPulse();
		}
	}
	
	/**
	 * This method should have the agent perform any actions that it would like.
	 * this should be called once per time step
	 * @param e
	 */
	public void update(Environment e) {
		if (!isDead) {
			pinkEnergy -= TURN_COST;
			greenEnergy -= TURN_COST;
		}
		testPulse();
	}
	
	/**
	 * tests to see if the agent is still alive.
	 */
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
