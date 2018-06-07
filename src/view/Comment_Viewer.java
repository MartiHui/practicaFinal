package view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import base_classes.Panel_Base;
import controller.Main_Window_c;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Toolkit;

public class Comment_Viewer extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4686822957233913974L;
	private Main_Window_c main;

	public Comment_Viewer(String s, Main_Window_c main, int panel) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Marti\\Java_Workspace\\practicaFinal\\images\\info.png"));
		this.main = main;
		main.currentPanel = -1;
		
		properties();
		getContentPane().setLayout(null);
		
		textElements(s);
		button(panel);
		
		setModal(true);
		setVisible(true);
	}
	
	private void properties() {
		this.setTitle("Comentario");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(500, 400));
	}
	
	private void textElements(String s) {
		JLabel commentLabel = new JLabel("Comentario:");
		commentLabel.setBounds(10, 11, 77, 33);
		getContentPane().add(commentLabel);
		
		JTextArea commentText = new JTextArea();
		commentText.setText(s);
		commentText.setLineWrap(true);
		commentText.requestFocus();
		commentText.setEditable(false);
		
		JScrollPane commentScrollPane = new JScrollPane();
		commentScrollPane.setBounds(20, 55, 390, 132);
		commentScrollPane.setViewportView(commentText);
		getContentPane().add(commentScrollPane);
	}
	
	private void button(int panel) {
		JButton okButton = new JButton("OK");
		okButton.setBounds(117, 208, 89, 23);
		getContentPane().add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.currentPanel = panel;
				setVisible(false);
				dispose();
			}
		});
	}
}
