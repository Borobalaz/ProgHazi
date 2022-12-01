package gravitysim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface PersistentObjectSet {
	
	
	public void save(String filename) throws IOException;
	public void load(String filename) throws FileNotFoundException;
}
