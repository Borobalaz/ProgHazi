package gravitysim;

public class GraphicDisplayThread extends Thread {
	
	SimPanel sp;
	public boolean stop;
	
	public GraphicDisplayThread(SimPanel _sp) {
		
		sp = _sp;
		stop = false;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			sp.paint(sp.getGraphics());
		}
		
		
	}
}
