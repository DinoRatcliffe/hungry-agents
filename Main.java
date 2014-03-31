import java.awt.Point;


public class Main {
	
	public static final int WIDTH = 20, HEIGHT = 10;
	
	public static void main(String[] args) throws InterruptedException {
		Environment e = new Environment(WIDTH, HEIGHT);
		e.addAgent(new DumbAgent(new Point(0, 0)));
		e.addAgent(new VisualAgent(new Point(6, 6)));
		e.addAgent(new AwareAgent(new Point(5, 5)));
		
		int t = 0;
		
		while (t < 100		) {
			System.out.println("\n**** TIME "+ t +" ****");
			System.out.println(e);
			e.update();
			t++;
			Thread.sleep(10);
		}

	}
}
