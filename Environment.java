import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
 

public class Environment {
	private static final char BLANK_CHAR = '.';
	
	private Random _random;
	private List<Agent> _agents;
	private int _width, _height;
	double visibility = 0.8;
	
	private Item[][] _world;
	
	public Environment(int width, int height) {
		_world = new Item[height][width];
		_agents = new ArrayList<Agent>();
		_random = new Random();
		
		_width = width;
		_height = height;
		populateWorld();
	}
	
	public void update() {
		for (Agent a : _agents) {
			a.update(this);
		}
	}
	
	public void addAgent(Agent agent) {
		_agents.add(agent);
	}
	
	public void removeAgent(Agent agent) {
		_agents.remove(agent);
	}
	
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

	public boolean canMove(Point point) {
		boolean canMove = true;
		for(Agent a : _agents) {
			if (a.position.x == point.x && a.position.y == point.y) canMove = false;
		}
		return canMove;
	}

	public Item getItem(Point position) {
		return _world[position.x][position.y];
	}

	public Item viewItemAtPosition(double sight, Point position) {
		double chanceOfCorrect = sight * visibility;
		
		return _random.nextFloat() > chanceOfCorrect ? _world[position.x][position.y] : getRandomItem();
	}

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
