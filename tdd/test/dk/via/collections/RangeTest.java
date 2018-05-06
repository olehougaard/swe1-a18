package dk.via.collections;

import static org.junit.Assert.*;

import org.junit.*;

public class RangeTest {
	private Range range;
	
	@Before
	public void setUp() {
		range = new Range(0, 3);
	}

	@Test
	public void rangesContainTheirStartingPoint() {
		assertTrue(range.contains(0));
	}

	@Test
	public void rangesDontContainTheirEndPoint() {
		assertFalse(range.contains(3));
	}
	
	@Test
	public void rangesContainIntermediatePoints() {
		assertTrue(range.contains(2));
	}
	
	@Test
	public void rangesDoesntContainPointsBeforeTheirStartingPoint() {
		assertFalse(range.contains(-1));
	}
	
	@Test
	public void rangesWithEqualFromAndToAreEmpty() {
		Range range = new Range(0, 0);
		assertFalse(range.contains(0));
	}
	
	@Test
	public void rangesWithFromLargerThanToAreEmpty() {
		Range range = new Range(3, 0);
		assertFalse(range.contains(0));
		assertFalse(range.contains(3));
		assertFalse(range.contains(2));
	}
	
	@Test
	public void rangesWithSameFromAndToAreEqual() {
		assertEquals(range, new Range(0, 3));
	}
	
	@Test
	public void rangeWithDifferentFromsAreDifferent() {
		assertNotEquals(range, new Range(1, 3));
	}
	
	@Test
	public void rangeWithDifferentTosAreDifferent() {
		assertNotEquals(range, new Range(0, 2));
	}
	
	@Test
	public void rangesDoNotEqualsNonRanges() {
		assertNotEquals(range, "[0, 3]");
	}
	
	@Test
	public void emptyRangesAreEqual() {
		assertEquals(new Range(0, 0), new Range(3, 0));
	}
	
	@Test
	public void emptyRangesAreNotEqualToNonEmptyRanges() {
		assertNotEquals(new Range(0, 0), range);
	}
	
	@Test
	public void rangeEMPTYisAnEmptyRange() {
		assertEquals(new Range(0, 0), Range.EMPTY);
	}
	
	@Test
	public void intersectionWithAFullyContainedRangeReturnsTheSmallerRange() {
		assertEquals(new Range(1, 2), range.intersect(new Range(1, 2)));
	}
	
	@Test
	public void intersectWithANonIntersectingRangeReturnsAndEmptyRange() {
		assertEquals(Range.EMPTY, range.intersect(new Range(4, 6)));
	}
	
	@Test
	public void intersectWithOverlappingRangeReturnsTheIntersection() {
		assertEquals(new Range(2, 3), range.intersect(new Range(2, 6)));
	}
}
