package org.example.datatypes;

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

	public Vector2 clamp(double max) {
		double length = length();
		if (length > max) {
			normalize().scale(max);
		}
		return this;
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

	public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

	public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }

	public static Point2 toPoint2(Vector2 v) {
		return new Point2(v.x, v.y);
	}
}
