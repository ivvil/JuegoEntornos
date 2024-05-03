package org.example.Datatypes;

public class Vector2 {
	public double x;
	public double y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
		this.x = 0;
		this.y = 0;
	}

	public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 normalize() {
        double len = length();
        return new Vector2(x / len, y / len);
    }

    public Vector2 scale(double factor) {
        return new Vector2(x * factor, y * factor);
    }

	public static Vector2 clamp(Vector2 v, double max) {
		double length = v.length();
		if (length > max) {
			v = v.normalize().scale(max);
		}
		return v;
	}

	public static Vector2 clamp(Vector2 v, double min, double max) {
		double length = v.length();
		if (length > max) {
			v = v.normalize().scale(max);
		} else if (length < min) {
			v = v.normalize().scale(min);
		}
		return v;
	}
}
