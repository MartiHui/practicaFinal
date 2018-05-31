package view;

import javax.swing.JDialog;
import javax.swing.JLabel;

import model.Order;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import base_classes.Panel_Base;

import javax.swing.JButton;

public class Comment extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9097120917323237233L;
	public String s;
	public JTextArea commentText;
	public JButton cancel;
	public JButton save;
	
	public Comment(String s) {
		this.s = s;
		
		properties();
		getContentPane().setLayout(null);
		
		createButtons();
		createTextElements();
		
		repaint();
		revalidate();
	}
	
	private void properties() {
		this.setTitle("Comentario");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(500, 400));
	}
	
	private void createButtons() {
		cancel = new JButton("CANCELAR");
		cancel.setBounds(267, 211, 89, 23);
		getContentPane().add(cancel);
		
		save = new JButton("GUARDAR");
		save.setBounds(64, 211, 89, 23);
		getContentPane().add(save);
	}
	
	private void createTextElements() {
		JLabel commentLabel = new JLabel("Comentario:");
		commentLabel.setBounds(10, 11, 77, 33);
		getContentPane().add(commentLabel);
		
		commentText = new JTextArea();
		commentText.setText(s);
		commentText.setLineWrap(true);
		commentText.requestFocus();
		
		JScrollPane commentScrollPane = new JScrollPane();
		commentScrollPane.setBounds(20, 55, 390, 132);
		commentScrollPane.setViewportView(commentText);
		getContentPane().add(commentScrollPane);
	}
}
