package framework.geom;

/**
 * A representation of a vector starting at the origin, or an ordered pair.
 */
public class Vector2D {
	
	public double x;
	public double y;

	public Vector2D() {
		x = 0;
		y = 0;
	}
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vector2D(Vector2D vec) {
		this.x = vec.x;
		this.y = vec.y;
	}

	public Vector2D add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2D scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	/**
	 * Calculates the dot product between this vector and another vector.
	 * @param other The other vector.
	 * @return The dot product of the two vectors.
	 */
	public double dotProduct(Vector2D other) {
		return this.x * other.x + this.y * other.y;
	}

	/**
	 * Calculates the magnitude, or length/distance of the vector.
	 * @return The magnitude of the vector.
	 */
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Gets the unit vector of this vector. A unit vector is a vector with magnitude 1.
	 * @return A new Vector2D containing the unit vector.
	 */
	public Vector2D getUnitVector() {
		double mag = this.getMagnitude();
		if (mag > 0) {
			Vector2D normalizedVec = new Vector2D(this);
			normalizedVec.x /= mag;
			normalizedVec.y /= mag;
			return normalizedVec;
		} else {
			return new Vector2D();
		}
	}
	
}
