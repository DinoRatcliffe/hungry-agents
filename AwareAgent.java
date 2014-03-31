import java.awt.Point;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class AwareAgent extends Agent {
	public static final double SIGHT = 0.5;
	
	Map<Point, Entry<Item, Double>> _worldMemory;

	public AwareAgent(Point initialPosition) {
		super(initialPosition);
		_worldMemory = new HashMap<Point, Entry<Item, Double>>();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void update(Environment e) {
		super.update(e);
		if (isDead) return;
		
		Item currentItem = e.getItem(this.position);
		_worldMemory.put(position, new AbstractMap.SimpleEntry<Item, Double>(currentItem, 1.0));
		if ( currentItem != null && !currentItem.isConsumed && ((currentItem.greenGain > 0 && greenEnergy < MAXIMUM_ENERGY && (greenEnergy <= pinkEnergy || greenEnergy < MAXIMUM_ENERGY / 2)) || (currentItem.pinkGain > 0 && pinkEnergy < MAXIMUM_ENERGY && (pinkEnergy <= greenEnergy || pinkEnergy < MAXIMUM_ENERGY / 2))))  {
			currentItem.consume(this);
		} else {
			// TODO Auto-generated method stub
			List<Point> possibleMoves = e.getPossibleMoves(this.position);
			
			updateMemory(possibleMoves, e);
			
			Point move = pickMove(possibleMoves);
			moveToPosition(e, move);
		}

	}

	private void updateMemory(List<Point> possibleMoves, Environment e) {
		// TODO Auto-generated method stub
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
	
	public Point pickMove(List<Point> possibleMoves) {
		Point mostDesired = null;
		for (Point p : possibleMoves) {
			
			//todo pick best option
			if (mostDesired == null) {
				mostDesired = p;
			} else if (mostDesired != null) {
				mostDesired = pickBestMove(mostDesired, p);
			}
			
		}
		return mostDesired;
	}


	private Point pickBestMove(Point pointA, Point pointB) {
		Point best = pointA;
		Item itemA = _worldMemory.get(pointA).getKey();
		Item itemB = _worldMemory.get(pointB).getKey();
		if (greenEnergy <= pinkEnergy) {
			if (itemB != null && itemA != null) {
				best = itemA.greenGain * itemA.quantity > itemB.greenGain * itemB.quantity && (_worldMemory.get(pointA).getValue() > _worldMemory.get(pointB).getValue() && itemB.greenGain * itemB.quantity > 0) ? pointA : pointB;
			} else if (itemB != null) {
				best = pointB;
			}
		} else {
			if (itemB != null && itemA != null) {
				best = itemA.pinkGain * itemA.quantity > itemB.pinkGain * itemB.quantity && (_worldMemory.get(pointA).getValue() > _worldMemory.get(pointB).getValue() && itemB.pinkGain * itemB.quantity > 0) ? pointA : pointB;
			} else if (itemB != null) {
				best = pointB;
			}
		}
		return best;
	}


	@Override
	public String toString() {
		return "Aware Agent " + this.id + " Pink: " + pinkEnergy + ", Green: " + greenEnergy;
	}
}
