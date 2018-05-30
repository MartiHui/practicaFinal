package base_classes;

import java.awt.Font;

import javax.swing.JButton;

public class KeyPad extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3993324738815523687L;

	public KeyPad(int x, int y, String text) {
		super(text);
		this.setFont(new Font("Tahoma", Font.BOLD, 50));
		this.setBounds(x, y, 70, 70);
	}
}
