package gravitysim;

import java.awt.*;
import java.util.ArrayList;

public class MovingObject extends MassObject {
	
	private float velocity;
	private vec2 direction;
	MovingObject nextState;
	
	// DEFAULT CTOR
	public MovingObject() {
		super();
		velocity = 0;
		direction = new vec2(0,0);
		nextState = null;
	}
	
	// CTOR
	public MovingObject(vec2 p, int m, int r, Color c, float v, vec2 dir) {
		
		super(p, m, r, c);
		velocity = v;
		direction = dir;
		nextState = new MovingObject(this);
	}
	
	// COPY CTOR
	public MovingObject(MovingObject tmp) {
		super(tmp.getPos(), tmp.getMass(), tmp.getRadius(), tmp.getColor());
		velocity = tmp.getVelo();
		direction = tmp.getDir();
		nextState = null;
	}
	
	public float getVelo() {	return velocity;}
	public vec2 getDir() {	return direction;}
	
	public void setVelo(float _velo) {velocity = _velo;}
	public void setDir(vec2 _dir) {direction = _dir;}
	
	public void move(ArrayList<MassObject> massObjects) {
		
		vec2 v1 = direction.multiply(velocity);
		vec2 Fe = new vec2(0,0);
		float G = 10000;
		float t = 0.1f;
		
		// calculating Fe
		for(MassObject tmp : massObjects) {
			
			if(tmp != this) {
				
				vec2 FiNormal = tmp.getWPos().minus(this.getWPos());
				FiNormal = FiNormal.normalize();
				float dist2 = tmp.getWPos().minus(this.getWPos()).length();
				dist2 = dist2 * dist2;
				float Flenght = G * this.getMass() * tmp.getMass() / dist2;
				
				Fe = Fe.plus(FiNormal.multiply(Flenght));
			}
		}
		System.out.println("Fe: " + Fe);
		System.out.println("Mass: " + this.getMass());
		
		
		vec2 v = Fe.multiply(t).divide(this.getMass());
		v1 = v1.plus(v);
		System.out.println("v: " + v);
		System.out.println("v1: " + v1);
		vec2 pos1 = this.getWPos().plus(v1.multiply(t));
		
		this.setWPos(pos1);
		this.setVelo(v1.length());
		this.setDir(v1.normalize());
		//this.setPos(getPos().minus(new vec2(1,1)));
	}
	
}
