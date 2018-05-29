package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Panel_Base extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4815461740039399168L;

	public Panel_Base() {
		int width = 1440;
		int height = 900;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension monitor = tk.getScreenSize();
		Dimension ventana = new Dimension(width, height);
		
		this.setBounds((monitor.width-ventana.width)/2,
				(monitor.height-ventana.height)/2,
				ventana.width, ventana.height);
		
		setLayout(null);
	}
}
