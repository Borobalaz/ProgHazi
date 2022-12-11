package gravitysim;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

public class SimulationFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel cards;
	private SimPanel simPanel;
	
	//	MENU
	private JPanel menuPanel;
	private JSlider massSlider;
	private JComboBox<String> objType;
	private JSlider timeSlider;
	
	//	SAVE SET
	private JPanel savePanel;
	private JTextField saveText;
	
	//	LOAD SET
	private JPanel loadPanel;
	private JComboBox<String> savedSets;
	
	ObjectSet objectSet;
	vec2 frameSize;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	CTOR  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
     * Constructs the window of the application
     *
     */
	public SimulationFrame() {
		
		// CREATE FRAME
		super("Gravity Simulation");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		frameSize = new vec2(1200,600);
		this.setSize((int)frameSize.x, (int)frameSize.y);
		this.setBackground(new Color(66,66,66));

		// SET THE WINDOW TO THE MIDDLE OF THE SCREEN
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - super.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - super.getHeight()) / 2);
	    super.setLocation(x, y);
	    
	    // CREATE SIM OBJECTS
	    objectSet = new ObjectSet();
	    
	    simPanel = new SimPanel(objectSet);
	    simPanel.addMouseListener(new MyMouseAdapter());
		this.add(simPanel, BorderLayout.CENTER);
	    // INITIALIZE COMPONENTS
	    initComponents();
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~  INIT_COMPONENTS  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 	Constructs the different components of the program interface
	 */
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
	
	/**
	 * 	Constructs the menu interface
	 */
	public void initMenuPanel(JFrame jf) {
		
		// CREATE PANEL
		menuPanel = new JPanel();
		menuPanel.setBackground(new Color(108,148,184));
		// COMBOBOX
		String[] cb = {"movingObject", "massObject"};
		objType = new JComboBox<String>(cb);	menuPanel.add(objType);
		
		// TEXTS / SLIDERS
		menuPanel.add(new JLabel("Mass: "));
		massSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 30);	
		massSlider.setSnapToTicks(true);
		Dimension d = massSlider.getPreferredSize();
		massSlider.setPreferredSize(new Dimension(d.width-50,d.height));
		menuPanel.add(massSlider);
		
		menuPanel.add(new JLabel("Speed: "));
		timeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);	
		timeSlider.setSnapToTicks(true);
		timeSlider.setPreferredSize(new Dimension(d.width-50,d.height));
		menuPanel.add(timeSlider);
		
		//BUTTONS / LABELS
		JLabel placeObj = new JLabel("<html>Click on the simulation panel to place object</html>"); menuPanel.add(placeObj);
		JButton saveSet = new JButton("Save set"); menuPanel.add(saveSet);
		JButton loadSet = new JButton("Load set");	menuPanel.add(loadSet);
		JButton startSim = new JButton("Start sim");	menuPanel.add(startSim);
		JButton stopSim = new JButton("Stop!");	menuPanel.add(stopSim);
			
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

				
				if(objectSet.getState() == Thread.State.NEW)	
					objectSet.start();
				
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
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				System.exit(1);
				
			}
		});
		
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~  INIT_SAVE_PANEL  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 	Constructs the "save set" interface
	 */
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
				
				try {
					objectSet.save(saveText.getText());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				CardLayout c1 = (CardLayout)cards.getLayout();
				c1.show(cards, "menupanel");
			}
		}); 
		
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~  INIT_LOAD_PANEL  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 	Constructs the "load set" interface
	 */
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
				
				try {
					objectSet.load((String)savedSets.getSelectedItem());
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				
				simPanel.repaint();
				
				CardLayout c1 = (CardLayout)cards.getLayout();
				c1.show(cards, "menupanel");
			}
		});
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~  MOUSE_ADAPTER  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	class MyMouseAdapter extends MouseAdapter{
		
		vec2 pressPoint;
		
		/*
		 * Mouse adapter for placing objects on the simulation frame
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			
			if(objType.getSelectedItem().equals("massObject")) {
				
				MassObject mo = new MassObject(
					new vec2(e.getX(), e.getY()), 
					massSlider.getValue(), 
					massSlider.getValue(),
					Color.BLACK
				);
			
				objectSet.addMassObj(mo);
				simPanel.repaint();
			}
			else if(objType.getSelectedItem().equals("movingObject")) {
				
				pressPoint = new vec2(e.getX(), e.getY());
				simPanel.getGraphics().drawOval(
						(int)pressPoint.x - massSlider.getValue(), 
						(int)pressPoint.y - massSlider.getValue(), 
						massSlider.getValue()*2, 
						massSlider.getValue()*2
					);
			}
			
			
			//objectSet.getMovObjects().get(0).setPos(new vec2(100,100));
			
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			
			if(objType.getSelectedItem().equals("movingObject")) {
				
				vec2 currentPoint = new vec2((float)e.getX(), (float)e.getY());
				simPanel.getGraphics().drawLine(
						(int)pressPoint.x, 
						(int)pressPoint.y, 
						(int)currentPoint.mirrorToPoint(pressPoint).x,
						(int)currentPoint.mirrorToPoint(pressPoint).y
						);
			}
				
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			
			if(objType.getSelectedItem().equals("movingObject")) {
				
				vec2 releasePoint = new vec2(e.getX(), e.getY());
				
				MovingObject mo = new MovingObject(
						pressPoint,
						massSlider.getValue(),
						massSlider.getValue(),
						Color.RED,
						releasePoint.minus(pressPoint).length(),
						releasePoint.mirrorToPoint(pressPoint).minus(pressPoint).toWorldPos().normalize()
						);
					objectSet.addMovObj(mo);
			}
			
			simPanel.repaint();
		}
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  MAIN  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/*
	 * main method
	 */
	public static void main(String[] args) {
		
		SimulationFrame sf = new SimulationFrame();
		sf.setVisible(true);
	}
}
