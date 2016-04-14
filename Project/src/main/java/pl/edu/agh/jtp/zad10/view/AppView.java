package pl.edu.agh.jtp.zad10.view;

import java.awt.EventQueue;


import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JLabel;

import org.jfree.chart.JFreeChart;
import pl.edu.agh.jtp.zad10.controller.AppController;
import pl.edu.agh.jtp.zad10.controller.GraphController;
import pl.edu.agh.jtp.zad10.model.Particle;
import pl.edu.agh.jtp.zad10.model.ComponentType;
import pl.edu.agh.jtp.zad10.model.Statistic;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * IView from MVC Pattern
 * @author Zbigniew Krolikowski
 *
 */
public class AppView implements IView {

	private JFrame frame;
	private JTextField velocityField;
	private JTextField kineticEnergyField;
	private JButton basicOptionButton_1;
	private JButton basicOptionButton_2;
	private JButton basicOptionButton_3;
	private JButton basicOptionButton_4;
	private JButton basicOptionButton_5;
	private JButton basicOptionButton_6;
	private JButton basicOptionButton_7;
	private JButton basicOptionButton_8;
	private JTextField protonNumberField;
	private JTextField neutronNumberField;
	private JTextField electronNumberField;
	
	/** Controllers of the view (MVC) */
	private AppController controller;
	private GraphController graphController;
	
	/** IModel which is the statistic object*/
	private Statistic statistic;
	
	/** IModel represented in view (MVC)*/
	private Particle particle;
	
	private JTextField startField;
	private JTextField endField;
	private JLabel lblEnd;
	private JButton btnDrawKE;
	
	/** For the graph */
	private JPanel graphContainer;
	private JLabel Image;
	
	/** For the statistic and help window */
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem fileMenuExit;
	private JMenu mnHelp;
	private JMenuItem helpMenuAbout;
	private JMenu mnDebug;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	
	/**
	 * Launches the application.
	 */
	public void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
					updateValues();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the application.
	 */
	public AppView() {
		initialize();
	}
	
	/** Sets the controller to a given controller */
	public void setAppControler(AppController controller) {
		this.controller = controller;
	}
	
	/** Sets the graph controller to a given controller */
	public void setGraphControler(GraphController controller) {
		this.graphController = controller;
	}
	
	/** Sets the model to a given particle */
	public void setParticle(Particle particle) {
		this.particle = particle;
		particle.addView(this);
		updateValues();
	}
	
	/** Sets the statistics field */
	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}
	
	/** Get statistics */
	public Statistic getStatistics() {
		return this.statistic;
	}
	
	
	/** Displays the information about a particle(model) in the fields */
	public void updateValues() {
		electronNumberField.setText(Integer.toString(particle.getComponentCount(ComponentType.ELECTRON)));
		protonNumberField.setText(Integer.toString(particle.getComponentCount(ComponentType.PROTON)));
		neutronNumberField.setText(Integer.toString(particle.getComponentCount(ComponentType.NEUTRON)));
		velocityField.setText(Double.toString(particle.getVelocity()));
		kineticEnergyField.setText(Double.toString(particle.getKinEnergy()));
		velocityField.setBackground(new Color(255, 255, 255));
		kineticEnergyField.setBackground(new Color(255, 255, 255));
	}
	
	/**
	 * Used for taking the interval for the graph
	 * @return Double array with the interval
	 */
	public double[] getInterval(String parameter) {
		double[] interval = new double[2];
		// Getting start and end from it's field
		double start;
		double end;
		try {
			start = Double.parseDouble(startField.getText());
			end = Double.parseDouble(endField.getText());
		} catch (NumberFormatException exc) {
			startField.setBackground(new Color (255, 0, 0));
			endField.setBackground(new Color (255, 0, 0));
			statistic.increment("Graph interval format error");
			return null;
		}		
		if (start < 0 || end < 0 || end <= start) {
			startField.setBackground(new Color (255, 0, 0));
			endField.setBackground(new Color (255, 0, 0));
			statistic.increment("Graph interval inproper error");
			return null;
		}
		// This is for the case in which we calculate speed
		if (parameter.equals("velocity")) {
			if (start > 1 || end > 1) {
				startField.setBackground(new Color (255, 0, 0));
				endField.setBackground(new Color (255, 0, 0));
				return null;
			}
		}
		interval[0] = start;
		interval[1] = end;
		startField.setBackground(new Color(255, 255, 255));
		endField.setBackground(new Color(255, 255, 255));
		return interval;
	}
	
	/**
	 * For showing a graph, is requested by GraphController
	 */
	public void showChart(JFreeChart chart) {
		BufferedImage image = chart.createBufferedImage(graphContainer.getWidth(), graphContainer.getHeight());
		ImageIcon icon = new ImageIcon(image);
		Image.setIcon(icon);
		Image.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Initializing statistic
		this.statistic = new Statistic();
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setFont(new Font("Dialog", Font.PLAIN, 12));
		frame.setBackground(new Color(102, 153, 153));
		frame.setBounds(100, 100, 825, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		basicOptionButton_1 = new JButton("Electron");
		basicOptionButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statistic.increment("Electron button");
				controller.particleChangeRequested("electron");
				updateValues();			
			}
		});
		basicOptionButton_1.setBounds(12, 27, 181, 25);
		frame.getContentPane().add(basicOptionButton_1);
		
		basicOptionButton_2 = new JButton("Proton");
		basicOptionButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Proton button");
				controller.particleChangeRequested("proton");
				updateValues();
			}
		});
		basicOptionButton_2.setBounds(205, 27, 181, 25);
		frame.getContentPane().add(basicOptionButton_2);
		
		basicOptionButton_3 = new JButton("Neutron");
		basicOptionButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statistic.increment("Neutron button");
				controller.particleChangeRequested("neutron");
				updateValues();
			}
		});
		basicOptionButton_3.setBounds(12, 62, 181, 25);
		frame.getContentPane().add(basicOptionButton_3);
		
		basicOptionButton_4 = new JButton("Alfpha Particle");
		basicOptionButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Alpha button");
				controller.particleChangeRequested("alpha");
				updateValues();
			}
		});
		basicOptionButton_4.setBounds(205, 62, 181, 25);
		frame.getContentPane().add(basicOptionButton_4);
		
		basicOptionButton_5 = new JButton("Lithium Nucleus");
		basicOptionButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Lithium button");
				controller.particleChangeRequested("lithium");
				updateValues();
			}
		});
		basicOptionButton_5.setBounds(12, 99, 181, 25);
		frame.getContentPane().add(basicOptionButton_5);
		
		basicOptionButton_6 = new JButton("Carbon nucleus");
		basicOptionButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Carbon button");
				controller.particleChangeRequested("carbon");
				updateValues();;
			}
		});
		basicOptionButton_6.setBounds(205, 99, 181, 25);
		frame.getContentPane().add(basicOptionButton_6);
		
		basicOptionButton_7 = new JButton("Iron nucleus");
		basicOptionButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Iron button");
				controller.particleChangeRequested("iron");
				updateValues();
			}
		});
		basicOptionButton_7.setBounds(12, 136, 181, 25);
		frame.getContentPane().add(basicOptionButton_7);
		
		basicOptionButton_8 = new JButton("Plutonium nucleus");
		basicOptionButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Plutonium button");
				controller.particleChangeRequested("plutonium");
				updateValues();
			}
		});
		basicOptionButton_8.setBounds(205, 136, 181, 25);
		frame.getContentPane().add(basicOptionButton_8);
		
		/** Number fields */

		velocityField = new JTextField();
		velocityField.setText("0.0");
		velocityField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Velocity field");
				double value;
				try {
					value = Double.parseDouble(velocityField.getText());
				} catch (NumberFormatException exc) {
					velocityField.setBackground(new Color (255, 0, 0));
					statistic.increment("Velocity field number format error");
					return;
				}
				if (value < 0 || value >= 1) {
					velocityField.setBackground(new Color (255, 0, 0));
					statistic.increment("Velocity field range error");
					return;
				}
				statistic.increment("Velocity field modified successfully");
				velocityField.setBackground(new Color(255, 255, 255));
				controller.velocityChangeRequested(value);
			}
		});
		velocityField.setBounds(50, 270, 300, 30);
		frame.getContentPane().add(velocityField);
		velocityField.setColumns(10);

		kineticEnergyField = new JTextField();
		kineticEnergyField.setText("0.0");
		kineticEnergyField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Energy field");
				double value;
				try {
					value = Double.parseDouble(kineticEnergyField.getText());
				} catch (NumberFormatException exc) {
					kineticEnergyField.setBackground(new Color (255, 0, 0));
					statistic.increment("Energy field number format error");
					return;
				}
				if (value < 0) {
					kineticEnergyField.setBackground(new Color (255, 0, 0));
					statistic.increment("Energy field range error");
					return;
				}
				statistic.increment("Energy field modified successfully");
				kineticEnergyField.setBackground(new Color(255, 255, 255));
				controller.energyChangeRequested(value);
			}
		});
		kineticEnergyField.setBounds(50, 340, 300, 30);
		frame.getContentPane().add(kineticEnergyField);
		kineticEnergyField.setColumns(10);
		
		/**
		 *  Field for the number of electrons in the particle
		 */
		electronNumberField = new JTextField();
		electronNumberField.setBackground(new Color(153, 255, 102));
		electronNumberField.setEditable(false);
		electronNumberField.setText("0");
		electronNumberField.setHorizontalAlignment(SwingConstants.CENTER);
		electronNumberField.setColumns(10);
		electronNumberField.setBounds(50, 211, 92, 19);
		frame.getContentPane().add(electronNumberField);

		/**
		 *  Field for the number of protons in the particle
		 */
		protonNumberField = new JTextField();
		protonNumberField.setBackground(new Color(0, 153, 255));
		protonNumberField.setEditable(false);
		protonNumberField.setText("0");
		protonNumberField.setHorizontalAlignment(SwingConstants.CENTER);
		protonNumberField.setBounds(154, 211, 92, 19);
		frame.getContentPane().add(protonNumberField);
		protonNumberField.setColumns(10);

		/**
		 *  Field for the number of neutrons in the particle
		 */
		neutronNumberField = new JTextField();
		neutronNumberField.setBackground(new Color(255, 102, 102));
		neutronNumberField.setEditable(false);
		neutronNumberField.setText("0");
		neutronNumberField.setHorizontalAlignment(SwingConstants.CENTER);
		neutronNumberField.setColumns(10);
		neutronNumberField.setBounds(258, 211, 92, 19);
		frame.getContentPane().add(neutronNumberField);

		/** The labels */
		
		JLabel lblProtons = new JLabel("Protons");
		lblProtons.setHorizontalAlignment(SwingConstants.CENTER);
		lblProtons.setBounds(165, 185, 70, 15);
		frame.getContentPane().add(lblProtons);

		JLabel lblNeutrons = new JLabel("Neutrons");
		lblNeutrons.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeutrons.setBounds(268, 185, 70, 15);
		frame.getContentPane().add(lblNeutrons);

		JLabel lblElectrons = new JLabel("Electrons");
		lblElectrons.setHorizontalAlignment(SwingConstants.CENTER);
		lblElectrons.setBounds(60, 185, 70, 15);
		frame.getContentPane().add(lblElectrons);

		JLabel lblVelocity = new JLabel("Velocity [C]");
		lblVelocity.setHorizontalAlignment(SwingConstants.CENTER);
		lblVelocity.setBounds(60, 245, 290, 15);
		frame.getContentPane().add(lblVelocity);

		JLabel lblKELabel = new JLabel("Kinetic Energy [MeV]");
		lblKELabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblKELabel.setBounds(60, 315, 290, 15);
		frame.getContentPane().add(lblKELabel);
		
		JLabel lblHelp = new JLabel("<html>To calculate a value input a number into the other one's field and press enter.</html>");
		lblHelp.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHelp.setHorizontalAlignment(SwingConstants.CENTER);
		lblHelp.setBounds(34, 382, 330, 77);
		frame.getContentPane().add(lblHelp);
		
		/**
		 * Buttons and fields for graph drawing
		 */
		
		
		btnDrawKE = new JButton("Draw Kinetic Energy / Velocity");
		btnDrawKE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statistic.increment("Draw KE / V button");
				if (getInterval("velocity") != null) {
					double start = getInterval("velocity")[0];
					double end = getInterval("velocity")[1];
					graphController.chartRequested(start, end, "velocity");;
				}
			}
		});
		btnDrawKE.setBounds(499, 337, 250, 25);
		frame.getContentPane().add(btnDrawKE);
		
		
		JButton btnDraw = new JButton("Draw Velocity / Kinetic Energy");
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Draw V / KE button");
				if (getInterval("energy") != null) {
					double start = getInterval("energy")[0];
					double end = getInterval("energy")[1];
					graphController.chartRequested(start, end, "energy");
				}
			}
		});
		btnDraw.setBounds(499, 376, 250, 25);
		frame.getContentPane().add(btnDraw);
		
		startField = new JTextField();
		startField.setBounds(484, 300, 125, 20);
		frame.getContentPane().add(startField);
		startField.setColumns(10);
		
		endField = new JTextField();
		endField.setBounds(661, 300, 125, 20);
		frame.getContentPane().add(endField);
		endField.setColumns(10);
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStart.setBounds(430, 302, 50, 15);
		frame.getContentPane().add(lblStart);
		
		lblEnd = new JLabel("End");
		lblEnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnd.setBounds(616, 302, 44, 15);
		frame.getContentPane().add(lblEnd);

		JButton btnExportCsv = new JButton("Export CSV");
		btnExportCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statistic.increment("Export CSV button");
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showSaveDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File exportFile = fileChooser.getSelectedFile();
					graphController.changeFile(exportFile);
					graphController.export();
		        } 
			}
		});
		btnExportCsv.setBounds(569, 420, 117, 25);
		frame.getContentPane().add(btnExportCsv);	
		
		graphContainer = new JPanel();
		graphContainer.setBackground(Color.GRAY);
		graphContainer.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		graphContainer.setBounds(445, 27, 366, 262);
		graphContainer.setLayout(new BorderLayout());
		frame.getContentPane().add(graphContainer);
		
		Image = new JLabel("Graph");
		Image.setHorizontalAlignment(SwingConstants.CENTER);
		Image.setVisible(false);
		graphContainer.add(Image, BorderLayout.CENTER);
		
		/**
		 * This is for the menu bar
		 */
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileMenuExit = new JMenuItem("Exit");
		fileMenuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statistic.increment("Exit menu option");
				controller.exitRequested();
			}
		});
		fileMenu.add(fileMenuExit);
		
		mnDebug = new JMenu("Debug");
		mnDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Debug menu");
			}
		});
		menuBar.add(mnDebug);
		
		mntmNewMenuItem_1 = new JMenuItem("Statistic");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statistic.increment("Statisic menu option");
				controller.statisticWindowRequested();
			}
		});
		mnDebug.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem = new JMenuItem("Show Log");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statistic.increment("Show Log menu option");
				controller.logWindowRequested();
			}
		});
		mnDebug.add(mntmNewMenuItem);
		
		mnHelp = new JMenu("Help");
		mnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("Help menu");
			}
		});
		menuBar.add(mnHelp);
		
		helpMenuAbout = new JMenuItem("About");
		helpMenuAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic.increment("About menu opiton");
				controller.aboutWindowrequested();
			}
		});
		mnHelp.add(helpMenuAbout);
	}
}