import java.util.Random;


public abstract class Item {
	private static Random r = new Random();
	
	int quantity;
	int greenGain;
	int pinkGain;
	
	char displayChar = '.';
	char consumedDisplayChar = '.';
	boolean isConsumed = false;
	String name;
	
	public Item(String name) {
		setupDisplayChar();
		quantity = r.nextInt(10)+1;
		this.name = name;
	}
	
	public void consume(Agent a) {
		
		if (!isConsumed) {
			System.out.println(a + " eats " + this);
			quantity--;
		} else {
			System.out.println(a + " tried to eat consumed item");
		}
		
		if (quantity <= 0) {
			isConsumed = true;
			System.out.println(a + " finishes " + this);
		}
	}
	
	protected abstract void setupDisplayChar();
	
	public char getDisplayChar() {
		return isConsumed ? consumedDisplayChar : displayChar;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
