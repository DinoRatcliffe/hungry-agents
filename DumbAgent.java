import java.awt.Point;
import java.util.Collections;
import java.util.List;

public class DumbAgent extends Agent {

	public DumbAgent(Point initialPosition) {
		super(initialPosition);
	}

	@Override
	public void update(Environment e) {
		super.update(e);
		if (isDead) return;
		
		Item currentItem = e.getItem(this.position);
		if (currentItem != null && !currentItem.isConsumed && ((currentItem.greenGain > 0 && greenEnergy < MAXIMUM_ENERGY && (greenEnergy <= pinkEnergy || greenEnergy < MAXIMUM_ENERGY / 2)) || (currentItem.pinkGain > 0 && pinkEnergy < MAXIMUM_ENERGY && (pinkEnergy <= greenEnergy || pinkEnergy < MAXIMUM_ENERGY / 2))))  {
			currentItem.consume(this);
		} else {
			// TODO Auto-generated method stub
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
