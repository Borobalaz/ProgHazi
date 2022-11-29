package gravitysim;

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
	public void addMovObj(MovingObject mo) {movingObjects.add(mo);}
	public ArrayList<MassObject> getMassObjects()	{	return massObjects;}
	public ArrayList<MovingObject> getMovObjects()	{	return movingObjects;}
	
	@Override
	public void run() {
		
		while(!stop) {
			for(MovingObject tmp : movingObjects) {
			
				tmp.move(massObjects);
				System.out.println(tmp.nextState.getPos());
			}
			for(MovingObject tmp : movingObjects) {
			
				tmp = tmp.nextState;
				System.out.println("double buffer");
			}	
			
		}
	}
}
