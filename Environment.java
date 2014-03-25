import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Environment {
	private Random _random;
	private List<Agent> _agents;
	
	public enum Block {
		BLANK, PINK_PLANT, GREEN_PLANT
	}
	
	private Block[][] _world;
	
	public Environment(int width, int height) {
		_world = new Block[width][height];
		_agents = new ArrayList<Agent>();
		_random = new Random();
		populateWorld();
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
				int num = _random.nextInt(3);
				switch (num) {
				case 0:
					_world[i][j] = Block.BLANK;
					break;
				case 1:
					_world[i][j] = Block.PINK_PLANT;
					break;
				case 2:
					_world[i][j] = Block.GREEN_PLANT;
					break;
				}
			}
		}
		
	}
	
}
