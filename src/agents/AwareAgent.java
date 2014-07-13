package agents;

import java.awt.Point;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import environment.Environment;
import environment.items.Item;


/**
 * an agent that views the world and keeps track of what it has percieved in the past.
 * 
 * @author DinoRatcliffe
 *
 */
public class AwareAgent extends Agent {
	public static final double SIGHT = 0.5; // how good this agents eye sight is
	
	Map<Point, Entry<Item, Double>> _worldMemory; // the representation of the agents view of the world

	Point lastPosition;
	
	public AwareAgent(Point initialPosition) {
		super(initialPosition);
		_worldMemory = new HashMap<Point, Entry<Item, Double>>();
	}

	
	@Override
	public void update(Environment e) {
		super.update(e);
		if (isDead) return;
		
		Item currentItem = e.getItem(this.position);
		_worldMemory.put(position, new AbstractMap.SimpleEntry<Item, Double>(currentItem, 1.0));
		if ( currentItem != null && !currentItem.isConsumed && ((currentItem.greenGain > 0 && greenEnergy < MAXIMUM_ENERGY && (greenEnergy <= pinkEnergy || greenEnergy < MAXIMUM_ENERGY / 2)) || (currentItem.pinkGain > 0 && pinkEnergy < MAXIMUM_ENERGY && (pinkEnergy <= greenEnergy || pinkEnergy < MAXIMUM_ENERGY / 2))))  {
			currentItem.consume(this);
			lastPosition = null;
		} else {
			List<Point> possibleMoves = e.getPossibleMoves(this.position);
			updateMemory(possibleMoves, e);
			
			Point move = pickMove(possibleMoves);
			if (lastPosition != null && lastPosition.equals(move)) {
				Collections.shuffle(possibleMoves);
				move = possibleMoves.get(0);
			}
			lastPosition = this.position;
			moveToPosition(e, move);
		}

	}

	/**
	 * updates the agents memory given what it is seeing this turn.
	 * 
	 * @param possibleMoves
	 * @param e
	 */
	private void updateMemory(List<Point> possibleMoves, Environment e) {
		for (Point p : possibleMoves) {
			Item item = e.viewItemAtPosition(SIGHT, p);
			if (_worldMemory.get(p) != null) {
				Entry<Item, Double> entry = _worldMemory.get(p);
				if (entry.getKey() == item ) {
					Double oldCert = entry.getValue();
					Double newCert = oldCert + (1 - oldCert) * SIGHT;
					entry.setValue(newCert);
				} else if (entry.getValue() < SIGHT) {
					Entry<Item, Double> newEntry = new AbstractMap.SimpleEntry<Item, Double>(item, SIGHT);
					_worldMemory.put(p, newEntry);
				} else {
					Double oldCert = entry.getValue();
					
					//should lower certainty
					Double newCert = oldCert + (1 - oldCert) * (SIGHT*-1);
					entry.setValue(newCert);
				}
				
			} else {
				Entry<Item, Double> newEntry = new AbstractMap.SimpleEntry<Item, Double>(item, SIGHT);
				_worldMemory.put(p, newEntry);
			}
		}
	}
	
	/**
	 * given the possible moves the method will return the perceived best one
	 * 
	 * @param possibleMoves
	 * @return
	 */
	public Point pickMove(List<Point> possibleMoves) {
		Collections.shuffle(possibleMoves);
		Point mostDesired = null;
		for (Point p : possibleMoves) {
			
			if (mostDesired == null || (mostDesired != null && _worldMemory.get(mostDesired).getKey() == null)) {
				mostDesired = p;
			} else if (mostDesired != null) {
				mostDesired = pickBestMove(mostDesired, p);
			}
			
		}
		return mostDesired;
	}

	/**
	 * compares the two moves and returns the better of the two. if they are the same itemA
	 * will be returned.
	 * 
	 * @param pointA
	 * @param pointB
	 * @return
	 */
	private Point pickBestMove(Point pointA, Point pointB) {
		Point best = pointA;
		Item itemA = _worldMemory.get(pointA).getKey();
		Item itemB = _worldMemory.get(pointB).getKey();
		if (greenEnergy <= pinkEnergy) {
			if (itemB != null) {
				best = itemA.greenGain * itemA.quantity > itemB.greenGain * itemB.quantity /* && (_worldMemory.get(pointA).getValue() > _worldMemory.get(pointB).getValue() && itemB.greenGain * itemB.quantity > 0) */ ? pointA : pointB;
			}
		} else {
			if (itemB != null) {
				best = itemA.pinkGain * itemA.quantity > itemB.pinkGain * itemB.quantity /* && (_worldMemory.get(pointA).getValue() > _worldMemory.get(pointB).getValue() && itemB.pinkGain * itemB.quantity > 0) */ ? pointA : pointB;
			}
		}
		return best;
	}


	@Override
	public String toString() {
		return "Aware Agent " + this.id + " Pink: " + pinkEnergy + ", Green: " + greenEnergy;
	}
}
