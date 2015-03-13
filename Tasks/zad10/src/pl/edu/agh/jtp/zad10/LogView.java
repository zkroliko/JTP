package pl.edu.agh.jtp.zad10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class LogView extends JFrame implements IView {

	/**
	 *  Serial UID
	 */
	private static final long serialVersionUID = -5676932322238186692L;
	
	private JFrame frame;

	/**
	 * The log object
	 */
	Log log;
	
	/**
	 * Components
	 */
	private JTextArea textArea;
	private JLabel titleLbl;
	private JScrollPane scrollPane;
	
	/**
	 * Makes a window
	 */
	public void makeVisible() {
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
	 * Create the frame.
	 */
	public LogView(Log log) {
		// First the stats
		this.log = log;
		log.addView(this);
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 600, 425);
		frame.getContentPane().setLayout(null);
		
		titleLbl = new JLabel("Events that occured");
		titleLbl.setFont(new Font("Dialog", Font.BOLD, 14));
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setBounds(120, 11, 360, 15);
		frame.getContentPane().add(titleLbl);
		
		textArea = new JTextArea();
		textArea.setText("Statistics");
		textArea.setEditable(false);
		textArea.setBounds(12, 38, 580, 350);
		
		scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 38, 580, 350);
		frame.getContentPane().add(scrollPane);			
	}
	
	/**
	 * Updates the text in text area
	 */
	public void updateValues() {
		String lines = new String();
		String[] raw = log.getEvents();
		for (int i = 0; i < raw.length -1; i++ ) {
			lines += raw[i];
			lines += "\n";
		}
		this.textArea.setText(lines);
	}
}
