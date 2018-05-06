package dk.via.collections;

public class Range {
	public static final Range EMPTY = new Range(0, 0);
	private int from;
	private int to;

	public Range(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public boolean contains(int i) {
		return from <= i && i < to;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Range)) {
			return false;
		}
		Range other = (Range)obj;
		if (from >= to)
			return other.from >= other.to;
		else
			return from == other.from && to == other.to;
	}

	public Range intersect(Range other) {
		return new Range(Math.max(from, other.from), Math.min(to, other.to));
	}
}
