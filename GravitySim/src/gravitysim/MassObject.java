package gravitysim;

import java.awt.*;

public class MassObject {
	
	private vec2 position;
	private int mass;
	private int radius;
	private Color color;
	
	public MassObject() {
		mass = 0;
		radius = 0;
		color = Color.BLACK;
		position = new vec2(0,0);
	}
	
	public MassObject(vec2 p, int m, int r, Color c) {
		mass = m;
		radius = r;
		color = c;
		position = p;
	}
	
	public boolean collide(vec2 p, int r) {
		
		float dist = position.minus(p).length();
		if(dist > radius + r)	return false;
		else return true;
	}
	
	public int getMass() {	return mass;}
	public int getRadius() {	return radius;}
	public Color getColor()	{	return color;}
	public vec2 getPos() {	return position;}
	public void setMass(int m)	{	mass = m;}
	public void setRadius(int r)	{	radius = r;}
	public void setColor(Color c)	{	color = c;}
	public void setPos(vec2 p)	{	position = p;}
	public String toString() {	return "pos: " + position + " mass: " + mass + " radius: " + radius;}
	
	//@Override
	//public Class<?> getClass()	{	return MassObject.class;}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)position.x - radius, (int)position.y - radius, radius*2, radius*2);
	}
	
}
