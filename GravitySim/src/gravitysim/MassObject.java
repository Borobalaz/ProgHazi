package gravitysim;

import java.awt.*;

public class MassObject {
	
	private vec2 wPos;
	private vec2 position;
	private int mass;
	private int radius;
	private Color color;
	
	/*
	 * Default constructor
	 */
	public MassObject() {
		mass = 0;
		radius = 0;
		color = Color.BLACK;
		position = new vec2(0,0);
		wPos = new vec2(position.x, -position.y);
	}
	
	/*
	 * Constructor
	 * 
	 * @param p position of the object
	 * @param m mass of the object
	 * @param r radius of the object
	 * @param c color of the object
	 */
	public MassObject(vec2 p, int m, int r, Color c) {
		mass = m;
		radius = r;
		color = c;
		position = p;
		wPos = new vec2(position.x, -position.y);
	}
	
	/*
	 *  Detects if two objects have collided
	 *  
	 *  @param p position of the object that could have collided
	 *  @param r radius of the object that could have collided
	 *  @return true if the objects have collided, else false
	 */
	public boolean collide(vec2 p, int r) {
		
		float dist = wPos.minus(p).length();
		if(dist > radius + r)	return false;
		else return true;
	}
	
	/*
	 * getters
	 */
	public int getMass() {	return mass;}
	public int getRadius() {	return radius;}
	public Color getColor()	{	return color;}
	public vec2 getPos() {	return position;}
	public vec2 getWPos() {	return wPos;}
	
	/*
	 * setters
	 */
	public void setMass(int m)	{	mass = m;}
	public void setRadius(int r)	{	radius = r;}
	public void setColor(Color c)	{	color = c;}
	public void setPos(vec2 p)	{	position = p;}
	public void setWPos(vec2 _wpos) {	wPos = _wpos;}
	
	/*
	 * Converts object data to string
	 * @see java.lang.Object#toString()
	 */
	public String toString() {	return "pos: " + position + " mass: " + mass + " radius: " + radius;}
	
	/*
	 * Draws a circle in the position of the object 
	 * 
	 * @param g the graphic to which the circle will be drawn
	 */
	public void draw(Graphics g) {
		position = new vec2(wPos.x, -wPos.y);
		g.setColor(color);
		g.fillOval((int)position.x - radius, (int)position.y - radius, radius*2, radius*2);
	}
	
}
