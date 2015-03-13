package pl.edu.agh.jtp.zad8;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 * View from MVC Pattern
 * @author Zbigniew Krolikowski
 *
 */
public class AppView {

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
	
	/** Controller of the view (MVC) */
	private AppController controller;
	
	/** Model represented in view (MVC)*/
	private Particle particle;
	
	/** Sets the controller to a given controller */
	public void setAppControler(AppController controller) {
		this.controller = controller;
	}
	
	/** Sets the model to a given particle */
	public void setParticle(Particle particle) {
		this.particle = particle;
	}
	
	/** Displays the information about a particle(model) in the fields */
	private void updateValues() {
		electronNumberField.setText(Integer.toString(particle.getComponentCount(ComponentType.ELECTRON)));
		protonNumberField.setText(Integer.toString(particle.getComponentCount(ComponentType.PROTON)));
		neutronNumberField.setText(Integer.toString(particle.getComponentCount(ComponentType.NEUTRON)));
		velocityField.setText(Double.toString(particle.getVelocity()));
		kineticEnergyField.setText(Double.toString(particle.getKinEnergy()));
		velocityField.setBackground(new Color(255, 255, 255));
		kineticEnergyField.setBackground(new Color(255, 255, 255));
	}

	/**
	 * Launches the application.
	 */
	public void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//AppView window = new AppView();
					frame.setVisible(true);
					updateValues();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setFont(new Font("Dialog", Font.PLAIN, 12));
		frame.setBackground(new Color(102, 153, 153));
		frame.setBounds(100, 100, 400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		basicOptionButton_1 = new JButton("Electron");
		basicOptionButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.buttonPressed("electron");
				updateValues();			
			}
		});
		basicOptionButton_1.setBounds(12, 27, 181, 25);
		frame.getContentPane().add(basicOptionButton_1);
		
		basicOptionButton_2 = new JButton("Proton");
		basicOptionButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonPressed("proton");
				updateValues();
			}
		});
		basicOptionButton_2.setBounds(205, 27, 181, 25);
		frame.getContentPane().add(basicOptionButton_2);
		
		basicOptionButton_3 = new JButton("Neutron");
		basicOptionButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.buttonPressed("neutron");
				updateValues();
			}
		});
		basicOptionButton_3.setBounds(12, 62, 181, 25);
		frame.getContentPane().add(basicOptionButton_3);
		
		basicOptionButton_4 = new JButton("Alfpha Particle");
		basicOptionButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonPressed("alpha");
				updateValues();
			}
		});
		basicOptionButton_4.setBounds(205, 62, 181, 25);
		frame.getContentPane().add(basicOptionButton_4);
		
		basicOptionButton_5 = new JButton("Lithium Nucleus");
		basicOptionButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonPressed("lithium");
				updateValues();
			}
		});
		basicOptionButton_5.setBounds(12, 99, 181, 25);
		frame.getContentPane().add(basicOptionButton_5);
		
		basicOptionButton_6 = new JButton("Carbon nucleus");
		basicOptionButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonPressed("carbon");
				updateValues();;
			}
		});
		basicOptionButton_6.setBounds(205, 99, 181, 25);
		frame.getContentPane().add(basicOptionButton_6);
		
		basicOptionButton_7 = new JButton("Iron nucleus");
		basicOptionButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonPressed("iron");
				updateValues();
			}
		});
		basicOptionButton_7.setBounds(12, 136, 181, 25);
		frame.getContentPane().add(basicOptionButton_7);
		
		basicOptionButton_8 = new JButton("Plutonium nucleus");
		basicOptionButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonPressed("plutonium");
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
				double value;
				try {
					value = Double.parseDouble(velocityField.getText());
				} catch (NumberFormatException exc) {
					velocityField.setBackground(new Color (255, 0, 0));
					return;
				}
				if (value < 0) {
					velocityField.setBackground(new Color (255, 0, 0));
					return;
				}
				velocityField.setBackground(new Color(255, 255, 255));
				particle.changeVelocity(value);
				updateValues();
			}
		});
		velocityField.setBounds(50, 270, 300, 30);
		frame.getContentPane().add(velocityField);
		velocityField.setColumns(10);

		kineticEnergyField = new JTextField();
		kineticEnergyField.setText("0.0");
		kineticEnergyField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double value;
				try {
					value = Double.parseDouble(kineticEnergyField.getText());
				} catch (NumberFormatException exc) {
					kineticEnergyField.setBackground(new Color (255, 0, 0));
					return;
				}
				if (value < 0) {
					kineticEnergyField.setBackground(new Color (255, 0, 0));
					return;
				}
				kineticEnergyField.setBackground(new Color(255, 255, 255));
				particle.changeKinEnergy(value);
				updateValues();
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
		electronNumberField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// This is for feature build up of the app
			}
		});
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
		protonNumberField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// This is for feature build up of the app
			}
		});
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
		neutronNumberField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// This is for feature build up of the app
			}
		});
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
		lblHelp.setBounds(34, 382, 330, 64);
		frame.getContentPane().add(lblHelp);
	}
}
