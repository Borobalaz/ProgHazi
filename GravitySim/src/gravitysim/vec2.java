package gravitysim;

public class vec2 {
	
	public float x;
	public float y;
	
	/*
	 * Constructor
	 * 
	 * @param a x coordinate
	 * @param b y coordinate
	 */
	public vec2(float a, float b) {
		x = a;
		y = b;
	}
	
	/*
	 * Vector subtraction 
	 * 
	 * @param tmp subtrahend
	 * @return this - tmp
	 */
	public vec2 minus(vec2 tmp) {
		return new vec2(x - tmp.x, y - tmp.y);
	} 
	
	/*
	 * Vector addition
	 * 
	 * @param tmp addent
	 * @return this + tmp
	 */
	public vec2 plus(vec2 tmp) {
		return new vec2(x + tmp.x, y + tmp.y);
	}
	
	/*
	 * Dot product of two vectors
	 * 
	 * @param tmp 
	 * @return Dot product of this and tmp
	 */
	public float dot(vec2 tmp) {
		return (x * tmp.x) + (y * tmp.y);
	}
	
	/*
	 * 
	 * @return length of the vector
	 */
	public float length() {
		return (float)Math.sqrt((x * x) + (y * y));
	}
	
	/*
	 * @return the vector normalized
	 */
	public vec2 normalize() {

		return new vec2(x / this.length(), y / this.length());
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return x + "," + y;
	}
	
	/*
	 * Multipication by a scalar
	 * 
	 * @param f scalar
	 * @return result
	 */
	public vec2 multiply(float f) {
		
		return new vec2(x*f, y*f);
	}
	
	/*
	 * Division by scalar
	 * 
	 * @param f divisor
	 * @return result
	 */
	public vec2 divide(float f) {
		
		return new vec2(x/f, y/f);
	}
	
	/*
	 * Parsing a string to vec2
	 * 
	 * @param string string to be parsed
	 * @return parsed vec2 type
	 */
	static vec2 parseVec2(String string) {
		
		//string.substring(1, string.length()-1);
		String coords[] = string.split(",");
		
		return new vec2(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]));
	}
}
