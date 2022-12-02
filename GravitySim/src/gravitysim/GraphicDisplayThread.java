package gravitysim;

public class GraphicDisplayThread extends Thread {
	
	SimPanel sp;
	public boolean stop;
	
	/*
	 * 	Constructor
	 * 
	 * 	@param _sp Simulation frame to draw
	 */
	public GraphicDisplayThread(SimPanel _sp) {
		
		sp = _sp;
		stop = false;
	}
	
	/*
	 * Thread run() method, draws the simulation panel
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		while(true) {
			
			sp.paint(sp.getGraphics());
		}
		
		
	}
}
