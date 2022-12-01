package gravitysim;

import java.io.File;
import java.util.ArrayList;

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
	
	public void save(File file) {
		
		// TODO
		
	}
	
	public void load(File file) {
		
		// TODO
	}
}
