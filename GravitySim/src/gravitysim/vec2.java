package gravitysim;

public class vec2 {
	
	public float x;
	public float y;
	
	public vec2(float a, float b) {
		x = a;
		y = b;
	}
	
	public vec2 minus(vec2 tmp) {
		return new vec2(x - tmp.x, y - tmp.y);
	} 
	public vec2 plus(vec2 tmp) {
		return new vec2(x + tmp.x, y + tmp.y);
	}
	public float dot(vec2 tmp) {
		return (x * tmp.x) + (y * tmp.y);
	}
	public float length() {
		return (float)Math.sqrt((x * x) + (y * y));
	}
	public vec2 normalize() {

		return new vec2(x / this.length(), y / this.length());
	}
	public String toString() {
		return x + "," + y;
	}
	public vec2 multiply(float f) {
		
		return new vec2(x*f, y*f);
	}
	public vec2 divide(float f) {
		
		return new vec2(x/f, y/f);
	}
	
	static vec2 parseVec2(String string) {
		
		//string.substring(1, string.length()-1);
		String coords[] = string.split(",");
		
		return new vec2(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]));
	}
}
