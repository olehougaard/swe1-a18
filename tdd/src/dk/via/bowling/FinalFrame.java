package dk.via.bowling;

public class FinalFrame extends Frame {
	@Override
	public boolean isPlayed() {
		return !isUnscored();
	}
}
