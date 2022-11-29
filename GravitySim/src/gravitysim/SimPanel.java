package gravitysim;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SimPanel extends JPanel{
	
	Image dbImage;
	Graphics dbGraphics;
	private static final long serialVersionUID = 1L;
	ObjectSet objectSet;
	
	public SimPanel(ObjectSet os) {
		objectSet = os;
	}
	
	public void draw(Graphics g) {
		
		for(MassObject tmp : objectSet.getMassObjects()) {
			
			tmp.draw(g);
		}
		
	}
	
	public void paint(Graphics g) {
		
		dbImage = createImage(getWidth(), getHeight());
		dbGraphics = dbImage.getGraphics();
		draw(dbGraphics);
		File output = new File("kep.png");
		try {
			ImageIO.write((RenderedImage) dbImage, "png", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g.drawImage(dbImage, 0, 0, this);
	}

}
