package agents;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import environment.Environment;
import environment.items.Item;


/**
 * an agent that instead of moving randomly looks around the environment and then bases its decision
 * on where to move on what it sees.
 * 
 * @author DinoRatcliffe
 *
 */
public class VisualAgent extends Agent{

	public static final double SIGHT = 0.5; // how good this agents eye sight is
	
	public VisualAgent(Point initialPosition) {
		super(initialPosition);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(Environment e) {
		super.update(e);
		if (isDead) return;
		
		Item currentItem = e.getItem(this.position);
		
		//if it desires the item
		if (currentItem != null && !currentItem.isConsumed && ((currentItem.greenGain > 0 && greenEnergy < MAXIMUM_ENERGY && (greenEnergy <= pinkEnergy || greenEnergy < MAXIMUM_ENERGY / 2)) || (currentItem.pinkGain > 0 && pinkEnergy < MAXIMUM_ENERGY && (pinkEnergy <= greenEnergy || pinkEnergy < MAXIMUM_ENERGY / 2))))  {
			currentItem.consume(this);
		} else {
			List<Point> possibleMoves = e.getPossibleMoves(this.position);
			Map<Point, Item> sightedItems = new HashMap<Point, Item>();
			
			for (Point p : possibleMoves) {
				sightedItems.put(p, e.viewItemAtPosition(SIGHT, p));
			}
			
			Point move = pickMove(sightedItems);
			moveToPosition(e, move);
		}

	}

	/**
	 * given the possible moves the method will return the perceived best one
	 * 
	 * @param sightedItems
	 * @return
	 */
	protected Point pickMove(Map<Point, Item> sightedItems) {
		Entry<Point, Item> mostDesired = null;
		for (Entry<Point, Item> entry : sightedItems.entrySet()) {
			
			if (mostDesired == null || (mostDesired != null && mostDesired.getValue() == null)) {
				mostDesired = entry;
			} else if (mostDesired != null) {
				mostDesired = pickBestMove(mostDesired, entry);
			}
			
		}
		return mostDesired.getKey();
	}

	/**
	 * compares the two moves and returns the better of the two. if they are the same itemA
	 * will be returned.
	 * 
	 * @param itemA
	 * @param itemB
	 * @return
	 */
	private Entry<Point, Item> pickBestMove(Entry<Point, Item> itemA,
			Entry<Point, Item> itemB) {
		
		Entry<Point, Item> best = itemA;
		
		if (greenEnergy <= pinkEnergy) {
			if (itemB.getValue() != null) {
				best = itemA.getValue().greenGain * itemA.getValue().quantity > itemB.getValue().greenGain * itemB.getValue().quantity ? itemA : itemB;
			}
		} else {
			if (itemB.getValue() != null) {
				best = itemA.getValue().pinkGain * itemA.getValue().quantity > itemB.getValue().pinkGain * itemB.getValue().quantity ? itemA : itemB;
			}
		}
		return best;
	}

	@Override
	public String toString() {
		return "Visual Agent " + this.id + " Pink: " + pinkEnergy + ", Green: " + greenEnergy;
	}

}
