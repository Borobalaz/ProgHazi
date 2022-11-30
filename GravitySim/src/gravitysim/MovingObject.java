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
	
	public void move(ArrayList<MassObject> massObjects) {
		
		/*vec2 F = new vec2(0,0);
		// Calculate sum(F)
		for(MassObject tmp : massObjects) {			
		
			vec2 Fi = tmp.getPos().minus(this.getPos());
			Fi = Fi.normalize();
			
			float attraction = 0.005f;
			
			// Fi = normalize(Pi - pos) * G*(mi*m / square(|Pi-pos|))
			Fi = Fi.multiply(
					attraction * tmp.getMass() * this.getMass() 
					/ 
					(tmp.getPos().minus(this.getPos()).length() *
						tmp.getPos().minus(this.getPos()).length())
					);
			
			F = F.plus(Fi);
		}
		
		vec2 Fdir = F.normalize();
		float time = 1;
		vec2 pos1 = this.getPos().plus( 
				Fdir.multiply( F.length() / (this.getMass()*time*time))
			);  
		
		this.setPos(pos1);*/
	
		this.setPos(getPos().minus(new vec2(1,1)));
	}
	
}
