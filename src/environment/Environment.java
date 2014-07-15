package environment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import environment.items.GreenPlant;
import environment.items.Item;
import environment.items.PinkPlant;

import agents.Agent;
 
/**
 * Class that represents the environment including all the agents and items
 * that exist in the environment. 
 * 
 * @author DinoRatcliffe
 *
 */
public class Environment {
	private static final char BLANK_CHAR = '.'; // the visual representation of an area with no items
	
	private Random _random;
	
	private List<Agent> _agents; 	// the agents in the world
	private int _width, _height; 	// the dimensions of the grid environment.
	double visibility = 0.8;		// the visibility in the environment for agents that have vision
	
	private Item[][] _world;		//the grid that represents the environment
	
	/**
	 * Simple constructor that initialises the world with random items 
	 * dispersed throughout.
	 * 
	 * @param width 	the width of the world to be generated.
	 * @param height	the height of the world to be generated.
	 */
	public Environment(int width, int height) {
		_world = new Item[height][width];
		_agents = new ArrayList<Agent>();
		_random = new Random();
		
		_width = width;
		_height = height;
		populateWorld();
	}
	 
	/**
	 * update method that simply tells all agents to perform an action.
	 * should be called once per time step.
	 */
	public void update() {
		for (Agent a : _agents) {
			a.update(this);
		}
	}
	
	/**
	 * adds given agent to the environment.
	 * 
	 * @param agent
	 */
	public void addAgent(Agent agent) {
		_agents.add(agent);
	}
	
	/**
	 * removes a given agent from the environment.
	 * @param agent
	 */
	public void removeAgent(Agent agent) {
		_agents.remove(agent);
	}
	
	/**
	 * populates the world with random items
	 */
	private void populateWorld() {
		
		for (int i = 0; i < _world.length; i++) {
			for (int j = 0; j < _world[i].length; j++) {
				_world[i][j] = getRandomItem();
			}
		}
		
	}

	@Override
	public String toString() {
		String output = "\n";
		
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				boolean agentLocation = false;
				
				for (Agent a : _agents) {
					if (a.position.x == i && a.position.y == j) {
						output += a.getDisplayChar();
						agentLocation = true;
					}
				}
				
				if (!agentLocation){
					if (_world[i][j] != null)
						output += _world[i][j].getDisplayChar();
					else
						output += BLANK_CHAR;
				}
			}
			output += "\n";
		}
		return output;
	}

	/**
	 * gets the possible moves that an agent could make given a specific position.
	 * 
	 * @param position	the position that the agent is at
	 * @return	a list of Points that represent the positions the agent could move.
	 */
	public List<Point> getPossibleMoves(Point position) {
		List<Point> possibleMoves = new ArrayList<Point>();
		
		//lower
		if (position.x + 1 < _height) {
			possibleMoves.add(new Point(position.x + 1, position.y));
			if (position.y + 1 < _width) possibleMoves.add(new Point(position.x+1, position.y+1));
			if (position.y - 1 >= 0) possibleMoves.add(new Point(position.x+1, position.y-1));
		}
		
		//mid
		if (position.y + 1 < _width) possibleMoves.add(new Point(position.x, position.y+1));
		if (position.y - 1 >= 0) possibleMoves.add(new Point(position.x, position.y-1));
		
		//upper
		if (position.x - 1 >= 0) {
			possibleMoves.add(new Point(position.x - 1, position.y));
			if (position.y + 1 < _width) possibleMoves.add(new Point(position.x-1, position.y+1));
			if (position.y - 1 >= 0) possibleMoves.add(new Point(position.x-1, position.y-1));
		}
		
		return possibleMoves;
	}

	/**
	 * test if an agent can move to a position without bumping into another agent.
	 * 
	 * @param point	the position the agent would like to move to.
	 * @return
	 */
	public boolean canMove(Point point) {
		boolean canMove = true;
		for(Agent a : _agents) {
			if (a.position.x == point.x && a.position.y == point.y) canMove = false;
		}
		return canMove;
	}

	/**
	 * gets the item at the given position
	 * 
	 * @param position
	 * @return
	 */
	public Item getItem(Point position) {
		return _world[position.x][position.y];
	}

	/**
	 * allows an agent to view an item at a position. this will either return the 
	 * item at that position, or a random item depending on random chance and the 
	 * agents sight.
	 * 
	 * @param sight		number between 0 and 1, represents how good the agents sight is
	 * @param position	the position to be viewed
	 * @return		the item at that position or a random item.
	 */
	public Item viewItemAtPosition(double sight, Point position) {
		double chanceOfCorrect = sight * visibility;
		
		return _random.nextFloat() > chanceOfCorrect ? _world[position.x][position.y] : getRandomItem();
	}

	/**
	 * Indicates if the environment currently has 
	 * any active agents.
	 *
	 * @return
	 */
	public boolean hasActiveAgents() {
		//should probaby have callback that simply removes agent as soon as
		//it dies
		boolean activeAgents = false;
		for(Agent a : _agents) {
			if (!a.isDead()) {
				activeAgents = true;
			}
		}
		return activeAgents;
	}

	/**
	 * generates a random item
	 * @return	a random item
	 */
	private Item getRandomItem() {
		int num = _random.nextInt(3);
		Item item;
		switch (num) {
		case 0:
			item = null;
			break;
		case 1:
			item = new PinkPlant();
			break;
		case 2:
			item = new GreenPlant();
			break;
		default:
			item = null;
			break;
		}
		return item;
	}

}
