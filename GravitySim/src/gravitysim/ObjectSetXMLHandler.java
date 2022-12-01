package gravitysim;

import java.awt.Color;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ObjectSetXMLHandler extends DefaultHandler{
	
	private ObjectSet objectSet;
	
	public ArrayList<MassObject> getMassObjects() {	return objectSet.getMassObjects();}
	public ArrayList<MovingObject> getMovObjects()	{return objectSet.getMovObjects();}
	
	@Override
	public void startDocument() {
		
		objectSet = new ObjectSet();
	}
	
	@Override
	public void startElement(String uri,
			String localName, String qName,
			Attributes atts) {
		
		if(qName.equals("movingObject")) {
			
			objectSet.addMovObj(
					new MovingObject(
						vec2.parseVec2(atts.getValue("position")),
						Integer.parseInt(atts.getValue("mass")),
						Integer.parseInt(atts.getValue("radius")),
						Color.RED,
						Float.parseFloat(atts.getValue("velocity")),
						vec2.parseVec2(atts.getValue("direction"))
					));
		}
		else if(qName.equals("massObject")) {
			
			objectSet.addMassObj(
					new MassObject(
						vec2.parseVec2(atts.getValue("position")),
						Integer.parseInt(atts.getValue("mass")),
						Integer.parseInt(atts.getValue("radius")),
						Color.BLACK
					));
		}
	}
}
