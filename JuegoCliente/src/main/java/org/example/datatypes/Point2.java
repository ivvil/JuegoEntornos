package org.example.datatypes;

public class Point2 {
	public double x;
	public double y;
	
	public Point2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point2() {
		this.x = 0;
		this.y = 0;
	}

	public static Vector2 toVector2(Point2 p) {
		return new Vector2(p.x, p.y);
	}
	
}
