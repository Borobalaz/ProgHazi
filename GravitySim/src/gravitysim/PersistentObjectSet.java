package gravitysim;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PersistentObjectSet {
	
	/*
	 * Serialize the ObjectSet in an XML file 
	 * Each object is a node (named movingObject or massObject)
	 * Their attributes are stored in the node's attributes.
	 * 
	 * @param filename name of the .xml file, for the set to be saved on (without the .xml extension)
	 */
	public void save(String filename) throws IOException;
	
	/*
	 * Read a serialized ObjectSet from a file, and swap it with the currently stored set
	 * 
	 * @param filename name of the XML file to be read (without .xml extension)
	 */
	public void load(String filename) throws FileNotFoundException;
}
