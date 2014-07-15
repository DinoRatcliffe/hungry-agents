import java.awt.Point;

import environment.Environment;

import agents.AwareAgent;
import agents.DumbAgent;
import agents.VisualAgent;


public class Main {
	
	public static final int WIDTH = 20, HEIGHT = 10; // width and height of the world
	
	/**
	 * a simple program that creats an environment adds one of each agent and then runs 
	 * a loop incrementing time
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Environment environment = new Environment(WIDTH, HEIGHT);
		environment.addAgent(new DumbAgent(new Point(3, 2)));
		environment.addAgent(new VisualAgent(new Point(0, 0)));
		environment.addAgent(new AwareAgent(new Point(6, 5)));
		
		int t = 0;
		
		while (environment.hasActiveAgents()) {
			System.out.println("\n**** TIME "+ t +" ****");
			System.out.println(environment);
			environment.update();
			t++;
			Thread.sleep(10);
		}

	}
}
