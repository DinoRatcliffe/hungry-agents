package environment.items;
import java.util.Random;

import agents.Agent;

/**
 * Abstract class that represents an item that can be found in the
 * environment
 * 
 * @author DinoRatcliffe
 *
 */
public abstract class Item {
	private static Random r = new Random();
	
	public int quantity; //quantity remaining of item
	
	//gain in energy when consumed
	public int greenGain;
	public int pinkGain;
	
	//visual representation of default empty item
	char displayChar = '.';
	char consumedDisplayChar = '.';
	
	public boolean isConsumed = false;
	String name;
	
	/**
	 * simple constructor;
	 * 
	 * @param name the name that identifies this type of item
	 */
	public Item(String name) {
		setupDisplayChar();
		quantity = r.nextInt(10)+1;
		this.name = name;
	}
	
	/**
	 * this method should perform any necessary steps to modify the agent
	 * in the appropriate manner when the item is consumed.
	 * 
	 * @param a the agent that is consuming this item.
	 */
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
	
	/**
	 * this method should set the visual display characters to something
	 * appropriate for the item
	 */
	protected abstract void setupDisplayChar();
	
	/**
	 * gets the character that should be used to display this item.
	 * 
	 * @return the character that represents this item and its current state
	 */
	public char getDisplayChar() {
		return isConsumed ? consumedDisplayChar : displayChar;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
