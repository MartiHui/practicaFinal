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

public class Product_Comment extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4686822957233913974L;
	private Main_Window_c main;

	public Product_Comment(String s, Main_Window_c main) {
		this.main = main;
		
		properties();
		getContentPane().setLayout(null);
		
		textElements(s);
		button();
		
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
	
	private void button() {
		JButton okButton = new JButton("OK");
		okButton.setBounds(117, 208, 89, 23);
		getContentPane().add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.currentPanel = 2;
				setVisible(false);
				dispose();
			}
		});
	}
}
