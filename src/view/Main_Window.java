package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Main_Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7125048035869540441L;

	public Main_Window() {
		setProperties();
	}
	
	private void setProperties() {
		this.setTitle("TERMINAL PUNTO DE VENTA");
		this.setVisible(true);
		this.setResizable(false);
		setSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
	}
	
	private void setSize() {
		int width = 1440;
		int height = 900;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension monitor = tk.getScreenSize();
		Dimension ventana = new Dimension(width, height);
		
		this.setBounds((monitor.width-ventana.width)/2,
				(monitor.height-ventana.height)/2,
				ventana.width, ventana.height);
	}
}