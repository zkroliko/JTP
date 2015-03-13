package pl.edu.agh.jtp.zad10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class StatsView extends JFrame implements IView {

	/**
	 *  Serial UID
	 */
	private static final long serialVersionUID = -5676932322238186692L;
	
	private JFrame frame;

	/**
	 * The statistic object
	 */
	Statistic stats;
	
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
	public StatsView(Statistic stats) {
		// First the stats
		this.stats = stats;
		stats.addView(this);
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 400);
		frame.getContentPane().setLayout(null);
		
		titleLbl = new JLabel("Usage of the application");
		titleLbl.setFont(new Font("Dialog", Font.BOLD, 14));
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setBounds(12, 11, 424, 15);
		frame.getContentPane().add(titleLbl);
		
		textArea = new JTextArea();
		textArea.setText("Statistics");
		textArea.setEditable(false);
		textArea.setBounds(12, 38, 421, 312);
		
		scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 38, 424, 321);
		frame.getContentPane().add(scrollPane);			
	}
	
	/**
	 * Updates the text in text area
	 */
	public void updateValues() {
		String lines = new String();
		String[] raw = stats.getStats();
		for (int i = raw.length -1 ; i >= 0; i-- ) {
			lines += raw[i];
			lines += "\n";
		}
		this.textArea.setText(lines);
	}
}
