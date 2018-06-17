package exam;

import java.util.ArrayList;

public class ShapeList {
	private ArrayList<Shape> shapes;
	
	public ShapeList() {
		this.shapes = new ArrayList<>();
	}
	
	public void add(Shape shape) {
		shapes.add(shape);
	}
	
	public double getTotalArea() {
		double totalArea = 0;
		for(Shape shape: shapes) {
			if (shape instanceof Circle) {
				Circle circle = (Circle) shape;
				totalArea += Math.PI * circle.getRadius() * circle.getRadius();
			} else if (shape instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) shape;
				totalArea += rectangle.getWidth() * rectangle.getHeight();
			}
		}
		return totalArea;
	}
}
