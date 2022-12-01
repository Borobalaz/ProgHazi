package gravitysim;

import java.io.File;

public interface PersistentObjectSet {
	
	
	public void save(String filename);
	public void load(String filename);
}
