package gravitysim;

import java.awt.*;
import java.util.ArrayList;

public class MovingObject extends MassObject {
	
	private float velocity;
	private vec2 direction;
	MovingObject nextState;
	
	/*
	 * Default constructor, every private element is set to 0 (null)
	 */
	public MovingObject() {
		super();
		velocity = 0;
		direction = new vec2(0,0);
		nextState = null;
		super.setColor(Color.RED);
	}
	
	/*
	 * Constructor
	 * 
	 * @param p position of object
	 * @param m mass of the object
	 * @param r radius of object
	 * @param c color of the object
	 * @param v starting velocity of the object
	 * @param dir normal direction vector of starting motion
	 */
	public MovingObject(vec2 p, int m, int r, Color c, float v, vec2 dir) {
		
		super(p, m, r, c);
		velocity = v;
		direction = dir;
		nextState = new MovingObject(this);
	}
	
	/*
	 * Copy constructor
	 * 
	 * @param tmp object to be copied
	 */
	public MovingObject(MovingObject tmp) {
		super(tmp.getPos(), tmp.getMass(), tmp.getRadius(), tmp.getColor());
		velocity = tmp.getVelo();
		direction = tmp.getDir();
		nextState = null;
	}
	
	/*
	 * Getters
	 */
	public float getVelo() {	return velocity;}
	public vec2 getDir() {	return direction;}
	
	/*
	 * Setters
	 */
	public void setVelo(float _velo) {velocity = _velo;}
	public void setDir(vec2 _dir) {direction = _dir;}
	
	public void copyFrom(MovingObject tmp) {
		
		this.setWPos(tmp.getWPos());
		this.setVelo(tmp.getVelo());
		this.setDir(tmp.getDir());
	}
	
	/*
	 * Calculates, and sets the new position of the object
	 * Move() is called iteratively to keep the object in motion
	 * 
	 * @param massObjects objects that attract the movingObject
	 */
	public void move(ArrayList<MassObject> massObjects, long time) {
		
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
		
		nextState.setWPos(pos1);
		nextState.setVelo(v1.length());
		nextState.setDir(v1.normalize());
		//this.setPos(getPos().minus(new vec2(1,1)));
	}
	
}
