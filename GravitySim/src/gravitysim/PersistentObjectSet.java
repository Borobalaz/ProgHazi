package gravitysim;

import java.io.File;

public interface PersistentObjectSet {
	
	
	public void save(File file);
	public void load(File file);
}
