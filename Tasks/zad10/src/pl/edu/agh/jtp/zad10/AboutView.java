package pl.edu.agh.jtp.zad10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.UIManager;

public class AboutView extends JFrame {

	/**
	 *  Version UID
	 */
	private static final long serialVersionUID = 1276547328891652892L;
	
	/**
	 * Container
	 */
	private JFrame frame;
	
	/**
	 * Components
	 */
	//JTextPane mainTextPane;

	/**
	 * Launch the application.
	 */
	public void makeVisible() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame.
	 */
	public AboutView() {
		getContentPane().setLayout(null);
		

		// Setting up a frame
		frame = new JFrame();
		frame.setResizable(false);		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		
		JTextPane txtPane = new JTextPane();
		txtPane.setBackground(UIManager.getColor("Button.background"));
		txtPane.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
		txtPane.setText("This is an application I made for class.\n\nIt basicly calculates atoms and stuff.\n\nHave fun. :)\n\nVersion 1.0\n");
		txtPane.setEditable(false);
		txtPane.setBounds(12, 12, 424, 250);
		frame.getContentPane().add(txtPane);
	}
}
