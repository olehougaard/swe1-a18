package dk.via.bowling;

public class Frame {
	private int[] scores;
	private int roll;

	public Frame() {
		scores = new int[3];
		roll = 0;
	}

	public void roll(int pins) {
		scores[roll] = pins;
		roll++;
	}

	public boolean isPlayed() {
		boolean isStrike = scores[0] == 10;
		boolean isSpare = !isStrike && scores[0] + scores[1] == 10;
		return isStrike || isSpare || roll == 2;
	}

	public boolean isUnscored() {
		boolean isStrike = scores[0] == 10;
		boolean isSpare = !isStrike && scores[0] + scores[1] == 10;
		return (isStrike && roll < 3) || (isSpare && roll < 3) || roll < 2;
	}

	public int getScore() {
		if (isUnscored())
			return 0;
		else
			return scores[0] + scores[1] + scores[2];
	}
	
	public int getRemainingPins() {
		if (roll == 0)
			return 10;
		else if (roll == 1 && scores[0] < 10)
			return 10 - scores[0];
		else if (isUnscored())
			return 10;
		else
			return 0;
	}
}
