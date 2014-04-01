package agents;

import java.awt.Point;
import java.util.Collections;
import java.util.List;

import environment.Environment;
import environment.items.Item;

/**
 * an agent that just consumes an item if that what it desires or moves randomly to a new location.
 * 
 * @author DinoRatcliffe
 *
 */
public class DumbAgent extends Agent {

	public DumbAgent(Point initialPosition) {
		super(initialPosition);
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
			// move randomly
			List<Point> possibleMoves = e.getPossibleMoves(this.position);
			Collections.shuffle(possibleMoves);
			
			moveToPosition(e, possibleMoves.get(0));
		}

	}

	@Override
	public String toString() {
		return "Dumb Agent " + this.id + " Pink: " + pinkEnergy + ", Green: " + greenEnergy;
	}
	
	

}
