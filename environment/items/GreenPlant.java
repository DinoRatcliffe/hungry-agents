package environment.items;
import agents.Agent;

/**
 * A plant that restores green energy when consumed.
 * 
 * @author DinoRatcliffe
 *
 */
public class GreenPlant extends Item {
	
	public GreenPlant() {
		super("Green Plant");
		greenGain = 100;
	}

	@Override
	public void consume(Agent a) {
		if(!isConsumed) a.increaseGreenEnergy(greenGain);
		super.consume(a);
	}

	@Override
	protected void setupDisplayChar() {
		displayChar = 'G';
		consumedDisplayChar = 'g';
	}

}
