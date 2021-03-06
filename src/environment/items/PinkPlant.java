package environment.items;
import agents.Agent;

/**
 * A plant that restores pink energy when consumed.
 * 
 * @author DinoRatcliffe
 *
 */
public class PinkPlant extends Item {

	public PinkPlant() {
		super("Pink Plant");
		pinkGain = 100;
	}
	
	@Override
	public void consume(Agent a) {
		if(!isConsumed) a.increasePinkEnergy(pinkGain);
		super.consume(a);
	}

	@Override
	protected void setupDisplayChar() {
		displayChar = 'P';
		consumedDisplayChar = 'p';
	}

	
}
