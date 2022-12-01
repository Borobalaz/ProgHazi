package gravitysim;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;

public class ObjectSet extends Thread implements PersistentObjectSet{
	
	private ArrayList<MassObject> massObjects;
	private ArrayList<MovingObject> movingObjects;
	public boolean stop;
	
	public ObjectSet() {
		massObjects = new ArrayList<MassObject>();
		movingObjects = new ArrayList<MovingObject>();
	}
	
	public void addMassObj(MassObject mo) {	massObjects.add(mo);}
	public void addMovObj(MovingObject mo) {	movingObjects.add(mo);	massObjects.add(mo);}
	public ArrayList<MassObject> getMassObjects()	{	return massObjects;}
	public ArrayList<MovingObject> getMovObjects()	{	return movingObjects;}
	
	public boolean isMovingObject(MassObject mo) {
		
		if(movingObjects.contains(mo)) return true;
		else return false;
	}
	
	@Override
	public void run() {
		
		
		while(!stop) {
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(MovingObject tmp : movingObjects) {
			
				tmp.move(massObjects);
				System.out.println("Frame position: " + tmp.getPos() + "\nWorld position: " + tmp.getWPos());
			}		
		}
	}
	
	public void save(String filename) {
		
		for(MassObject tmp : massObjects) {
			
			if(isMovingObject(tmp)) {
				
				JsonObject massValue = Json.createObjectBuilder()
						.add("type", "massObject")
						.add("position", tmp.getPos().toString())
						.add("mass", tmp.getMass())
						.add("radius", tmp.getRadius())
						.build();
						
				// CONTINUE
						
								
			}
			else {
				
				JsonObject massValue = Json.createObjectBuilder()
				.add("type", "massObject")
				.add("position", tmp.getPos().toString())
				.add("mass", tmp.getMass())
				.add("radius", tmp.getRadius())
				.build();
				
				JsonObject jobj = Json.createObjectBuilder()
						.add("massObject", massValue)
						.build();
				
			}
			
		}
		//TODO
		
	}
	
	public void load(String filename) {
		
		// TODO
	}
}
