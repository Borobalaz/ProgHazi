package gravitysim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

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
	
	public void save(String filename) throws IOException {
		
		
		String path = "src\\resources\\saves\\" + filename + ".xml";
		FileWriter fw = new FileWriter(new File(path));
		XMLOutputter xmlOutputter = new XMLOutputter();
		Document doc = new Document();
		Element root = new Element("root");
		doc.setRootElement(root);
		Element massElements = new Element("massObjects");	root.addContent(massElements);
		Element movingElements = new Element("movingObjects");	root.addContent(movingElements);
		
		for(MovingObject tmp : movingObjects) {
			
			movingElements.addContent(
					new Element("movingObject")
						.setAttribute("position", tmp.getPos().toString())
						.setAttribute("mass", String.valueOf(tmp.getMass()))
						.setAttribute("radius", String.valueOf(tmp.getRadius()))
						.setAttribute("velocity", String.valueOf(tmp.getVelo()))
						.setAttribute("direction", tmp.getDir().toString())	
					);
		}
		for(MassObject tmp : massObjects) {
			
			if(!isMovingObject(tmp)) {
				
				massElements.addContent(
					new Element("massObject")
						.setAttribute("position", tmp.getPos().toString())
						.setAttribute("mass", String.valueOf(tmp.getMass()))
						.setAttribute("radius", String.valueOf(tmp.getRadius()))
					);
			}
		}
		
		xmlOutputter.output(doc, fw);
		
		fw.close();
	}
	
	public void load(String filename) throws FileNotFoundException {
		
		String path = "src\\resources\\saves\\" + filename;
		ObjectSetXMLHandler handler = new ObjectSetXMLHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			parser.parse(new File(path), handler);
			
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		massObjects = handler.getMassObjects();
		movingObjects = handler.getMovObjects();
	}
}
