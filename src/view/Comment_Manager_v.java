package view;

import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utils.Panel_Base;

import javax.swing.JButton;

public class Comment_Manager_v extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9097120917323237233L;
	public String s;
	public JTextArea commentText;
	public JButton cancel;
	public JButton save;
	
	public Comment_Manager_v(String s) {
		this.s = s;
		
		properties();
		getContentPane().setLayout(null);
		
		buttons();
		textElements();
		
		repaint();
		revalidate();
	}
	
	private void properties() {
		this.setTitle("Comentario");
		this.setResizable(false);
		this.setBounds(Panel_Base.centrar(460, 280));
	}
	
	private void buttons() {
		cancel = new JButton("Cancelar");
		cancel.setBounds(286, 195, 90, 25);
		getContentPane().add(cancel);
		
		save = new JButton("Guardar");
		save.setBounds(70, 195, 90, 25);
		getContentPane().add(save);
	}
	
	private void textElements() {
		JLabel commentLabel = new JLabel("Comentario:");
		commentLabel.setBounds(30, 20, 75, 30);
		getContentPane().add(commentLabel);
		
		commentText = new JTextArea();
		commentText.setText(s);
		commentText.setLineWrap(true);
		commentText.requestFocus();
		
		JScrollPane commentScrollPane = new JScrollPane();
		commentScrollPane.setBounds(30, 50, 390, 130);
		commentScrollPane.setViewportView(commentText);
		getContentPane().add(commentScrollPane);
	}
}