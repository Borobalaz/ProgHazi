package gravitysim;

import java.awt.*;

public class MassObject {
	
	private vec2 wPos;
	private vec2 position;
	private int mass;
	private int radius;
	private Color color;
	
	public MassObject() {
		mass = 0;
		radius = 0;
		color = Color.BLACK;
		position = new vec2(0,0);
		wPos = new vec2(position.x, -position.y);
	}
	
	public MassObject(vec2 p, int m, int r, Color c) {
		mass = m;
		radius = r;
		color = c;
		position = p;
		wPos = new vec2(position.x, -position.y);
	}
	
	public boolean collide(vec2 p, int r) {
		
		float dist = wPos.minus(p).length();
		if(dist > radius + r)	return false;
		else return true;
	}
	
	public int getMass() {	return mass;}
	public int getRadius() {	return radius;}
	public Color getColor()	{	return color;}
	public vec2 getPos() {	return position;}
	public vec2 getWPos() {	return wPos;}
	public void setMass(int m)	{	mass = m;}
	public void setRadius(int r)	{	radius = r;}
	public void setColor(Color c)	{	color = c;}
	public void setPos(vec2 p)	{	position = p;}
	public String toString() {	return "pos: " + position + " mass: " + mass + " radius: " + radius;}
	public void setWPos(vec2 _wpos) {	wPos = _wpos;}
	
	//@Override
	//public Class<?> getClass()	{	return MassObject.class;}
	
	public void draw(Graphics g) {
		position = new vec2(wPos.x, -wPos.y);
		g.setColor(color);
		g.fillOval((int)position.x - radius, (int)position.y - radius, radius*2, radius*2);
	}
	
}
