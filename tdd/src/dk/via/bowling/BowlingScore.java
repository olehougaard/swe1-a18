package dk.via.bowling;

public class BowlingScore {
	private int currentFrame;
	private Frame[] frames;
	
	public BowlingScore(int frameNumber) {
		currentFrame = 0;
		frames = new Frame[frameNumber];
		for(int i = 0; i < frames.length; i++) {
			frames[i] = new Frame();
		}
		frames[frames.length - 1] = new FinalFrame();
	}
	
	public void roll(int pins)  {
		for(int i = 0; i < currentFrame; i++) {
			if (frames[i].isUnscored())
				frames[i].roll(pins);
		}
		frames[currentFrame].roll(pins); 
		if (frames[currentFrame].isPlayed()) {
			currentFrame++;
		}
	}
	
	public int getScore() {
		int score = 0;
		for(Frame frame: frames) {
			score += frame.getScore();
		}
		return score;
	}

	public int getCurrentFrame() {
		return currentFrame + 1;
	}
	
	public int getFrameScore(int frameNumber) {
		return frames[frameNumber - 1].getScore();
	}
}
