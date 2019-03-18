package dk.via.bowling;

import static org.junit.Assert.*;

import org.junit.*;

public class BowlingScoreTest {
	private BowlingScore game;
	
	@Before
	public void setUp() {
		game = new BowlingScore(10);
	}
	
	@Test
	public void gameStartsAt0Points() {
		assertEquals(0, game.getScore());
	}
	
	@Test
	public void incompleteThrowIsNotScoredYet() {
		game.roll(8);
		assertEquals(0, game.getScore());
	}
	
	@Test
	public void anOpenFrameIsScoredImmediately() {
		game.roll(8);
		game.roll(1);
		assertEquals(9, game.getScore());
	}
	
	@Test
	public void gameMovesToNextFrameAfterOpenFrame() {
		game.roll(8);
		game.roll(1);
		assertEquals(2, game.getCurrentFrame());
	}
	
	@Test
	public void gameKeepsScoreAfterMovingToNext() {
		game.roll(8);
		game.roll(1);
		game.roll(3);
		assertEquals(9, game.getScore());
	}

	@Test
	public void gameMovesToFrameThreeAfterTwoOpenFrames() {
		game.roll(8);
		game.roll(1);
		game.roll(3);
		game.roll(2);
		assertEquals(3, game.getCurrentFrame());
	}
	
	@Test
	public void totalScoreIsTheSumOfTheFrameScores() {
		game.roll(8);
		game.roll(1);
		game.roll(3);
		game.roll(2);
		assertEquals(game.getScore(), game.getFrameScore(1) + game.getFrameScore(2));
	}
	
	@Test
	public void spareIsNotScoredImmediatelyInTotalScore() {
		game.roll(8);
		game.roll(2);
		assertEquals(0, game.getScore());
	}
	
	@Test
	public void spareIsNotScoredImmediatelyInFrameScore() {
		game.roll(8);
		game.roll(2);
		assertEquals(0, game.getFrameScore(1));
	}
	
	@Test
	public void spareIsScoredAfterNextThrow() {
		game.roll(8);
		game.roll(2);
		game.roll(3);
		assertEquals(13, game.getFrameScore(1));
	}
	
	@Test
	public void aStrikeIsNotScoredImmediately() {
		game.roll(10);
		assertEquals(0, game.getFrameScore(1));
	}
	
	@Test
	public void aStrikeMovesToNextFrame() {
		game.roll(10);
		assertEquals(2, game.getCurrentFrame());
	}

	@Test
	public void strikeIsScoredAfterTwoNextThrows() {
		game.roll(10);
		game.roll(3);
		game.roll(3);
		assertEquals(16, game.getFrameScore(1));
	}
	
	@Test
	public void twoStrikeIsScoredAfterTwoNextThrows() {
		game.roll(10);
		game.roll(10);
		game.roll(3);
		game.roll(3);
		assertEquals(23, game.getFrameScore(1));
	}
	
	@Test
	public void extraThrowsWithSpareLastFrame() {
		BowlingScore game1 = new BowlingScore(1);
		game1.roll(7);
		game1.roll(3);
		assertEquals(1, game1.getCurrentFrame());
		game1.roll(10);
		assertEquals(2, game1.getCurrentFrame());
		assertEquals(20, game1.getScore());
	}
	
	@Test
	public void twoExtraThrowsWithStrikeLastFrame() {
		BowlingScore game1 = new BowlingScore(1);
		game1.roll(10);
		assertEquals(1, game1.getCurrentFrame());
		game1.roll(10);
		game1.roll(10);
		assertEquals(2, game1.getCurrentFrame());
		assertEquals(30, game1.getScore());
	}
	
	@Test
	public void maxScoreIs300() {
		for(int i = 1; i <= 12; i++) {
			game.roll(10);
		}
		assertEquals(300, game.getScore());
	}
	
	@Test(expected=IllegalStateException.class)
	public void itsIllegalToThrowPastFinalFrame() {
		BowlingScore game1 = new BowlingScore(1);
		game1.roll(7);
		game1.roll(2);
		game1.roll(10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void itsIllegalToThrowNegativePins() {
		game.roll(-1);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void itsIllegalToThrowMoreThan10Pins() {
		game.roll(11);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void itsIllegalToThrowMoreThanRemainingPins() {
		game.roll(7);
		game.roll(4);
	}
}
