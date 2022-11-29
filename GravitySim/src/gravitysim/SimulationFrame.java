package gravitysim;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

/*
 * TODO:
 * 
 */

public class SimulationFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel cards;
	private SimPanel simPanel;
	
	//	MENU
	private JPanel menuPanel;
	private JSlider massSlider;
	private JComboBox objType;
	private JSlider radiusSlider;
	
	//	SAVE SET
	private JPanel savePanel;
	private JTextField saveText;
	
	//	LOAD SET
	private JPanel loadPanel;
	private JComboBox savedSets;
	
	ObjectSet objectSet;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	CTOR  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public SimulationFrame() {
		
		// CREATE FRAME
		super("Gravity Simulation");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1280, 960);
		this.setBackground(new Color(66,66,66));

		// SET THE WINDOW TO THE MIDDLE OF THE SCREEN
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - super.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - super.getHeight()) / 2);
	    super.setLocation(x, y);
	    
	    // CREATE SIM OBJECTS
	    objectSet = new ObjectSet();
	    
	    MovingObject mo = new MovingObject(new vec2(500,500), 2, 3, Color.RED, 4, new vec2(0,1));
	    objectSet.addMassObj(mo);	objectSet.addMovObj(mo);
	    
	    simPanel = new SimPanel(objectSet);
	    simPanel.addMouseListener(new MyMouseAdapter());
		this.add(simPanel, BorderLayout.CENTER);
	    // INITIALIZE COMPONENTS
	    initComponents();
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~  INIT_COMPONENTS  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void initComponents() {
	
		// INIT MENU PANELS
		initMenuPanel(this);
		initSavePanel();
		try {
			initLoadPanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// SET CARD LAYOUT FOR MENU PANELS
		cards = new JPanel(new CardLayout());
		cards.add(menuPanel, "menupanel");
		cards.add(loadPanel, "loadpanel");
		cards.add(savePanel, "savepanel");
		CardLayout c1 = (CardLayout)cards.getLayout();
		c1.show(cards, "menupanel");	// on launch menu is showing
		this.add(cards, BorderLayout.NORTH);	// cards on the top
		

		
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~  INIT_MENU_PANEL  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void initMenuPanel(JFrame jf) {
		
		// CREATE PANEL
		menuPanel = new JPanel();
		menuPanel.setBackground(new Color(108,148,184));
		// COMBOBOX
		String[] cb = {"movingObject", "massObject"};
		objType = new JComboBox<String>(cb);	menuPanel.add(objType);
		
		// TEXTS
		menuPanel.add(new JLabel("Mass: "));
		massSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 55);	menuPanel.add(massSlider);
		menuPanel.add(new JLabel("Radius: "));
		radiusSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 55);	menuPanel.add(radiusSlider);
		
		//BUTTONS
		JButton placeObj = new JButton("Place object");	menuPanel.add(placeObj);
		JButton saveSet = new JButton("Save set");	menuPanel.add(saveSet);
		JButton loadSet = new JButton("Load set");	menuPanel.add(loadSet);
		JButton startSim = new JButton("Start sim");	menuPanel.add(startSim);
		JButton stopSim = new JButton("Stop!");	menuPanel.add(stopSim);
		
		
		// ACTION LISTENERS
		placeObj.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				//TODO
			}
		});
		saveSet.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				CardLayout c1 = (CardLayout)cards.getLayout();
				c1.show(cards, "savepanel");
			}
		});
		loadSet.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				CardLayout c1 = (CardLayout)cards.getLayout();
				c1.show(cards, "loadpanel");
			}
		});
		startSim.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {

				
				if(objectSet.getState() == Thread.State.NEW)	objectSet.start();
				else if(objectSet.getState() == Thread.State.TERMINATED ){
					System.out.println(objectSet.getState().toString());
					objectSet.stop = false;
				}
				GraphicDisplayThread gdt = new GraphicDisplayThread(simPanel);
				gdt.start();
			}
		});
		stopSim.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
		
				objectSet.stop = true;
				
			}
		});
		
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~  INIT_SAVE_PANEL  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void initSavePanel() {
		
		// CREATE PANEL
		savePanel = new JPanel();
		savePanel.setBackground(new Color(108,148,184));
		
		// TEXT FIELDS AND LABELS
		saveText = new JTextField(20);
		savePanel.add(new JLabel("Save file name: "));	savePanel.add(saveText);
		
		// SAVE BUTTON
		JButton saveButton = new JButton("Save");	savePanel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				CardLayout c1 = (CardLayout)cards.getLayout();
				c1.show(cards, "menupanel");
			}
		}); 
		
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~  INIT_LOAD_PANEL  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void initLoadPanel() throws IOException {
		
		// CREATE LOAD PANEL
		loadPanel = new JPanel();
		loadPanel.setBackground(new Color(108,148,184));
		
		// GET THE SAVE FILE NAMES
		File dir = new File("src\\resources\\saves");
		String[] files = dir.list();
		
		// PUT IT IN THE COMBOBOX, PUT THE COMBOBOX ON THE PANEL
		savedSets = new JComboBox<String>(files);	loadPanel.add(savedSets);
		
		// CREATE LOAD BUTTON, ADD IT TO PANEL
		JButton loadButton = new JButton("Load");	loadPanel.add(loadButton);
		loadButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				//TODO
				
				CardLayout c1 = (CardLayout)cards.getLayout();
				c1.show(cards, "menupanel");
			}
		});
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~  MOUSE_ADAPTER  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	class MyMouseAdapter extends MouseAdapter{
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			//TODO
			
			MassObject mo = new MassObject(
					new vec2(e.getX(), e.getY()), 
					massSlider.getValue(), 
					radiusSlider.getValue(),
					new Color(0,0,0)
				);
			
			objectSet.addMassObj(mo);
			//objectSet.getMovObjects().get(0).setPos(new vec2(100,100));
			simPanel.repaint();
		}
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  MAIN  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static void main(String[] args) {
		
		SimulationFrame sf = new SimulationFrame();
		sf.setVisible(true);
	}
}
