package utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Panel_Base extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4815461740039399168L;
	public JLabel date;
	public JLabel datetime;

	public Panel_Base() {
		int width = 1440;
		int height = 900;
		this.setBounds(centrar(width, height));
		
		setLayout(null);
		
		clock_v();
		clock_c();
		
		repaint();
		revalidate();
	}
	
	public static Rectangle centrar(int width, int height) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension monitor = tk.getScreenSize();
		Dimension ventana = new Dimension(width, height);
		
		Rectangle r = new Rectangle((monitor.width-ventana.width)/2,
				(monitor.height-ventana.height)/2,
				ventana.width, ventana.height);
		
		return r;
	}
	
	private void clock_v() {
		int width = 340;
		int height = 30;
		int initialX = 1100;
		int initialY = 10;
		
		date = new JLabel("");
		date.setFont(new Font("Tahoma", Font.PLAIN, 20));
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setBounds(initialX, initialY, width, height);
		add(date);
		
		datetime = new JLabel("");
		datetime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		datetime.setHorizontalAlignment(SwingConstants.CENTER);
		datetime.setBounds(initialX, initialY+height, width, height);
		add(datetime);
	}
	
	private void clock_c() {
		int interval = 1000;
		new Timer(interval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				datetime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}).start();
	}
}
