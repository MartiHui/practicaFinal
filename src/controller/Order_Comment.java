package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Order_Comment {
	protected view.Order_Comment view;
	public Order_Manager om;
	private Main_Window main;
	
	public Order_Comment(Order_Manager om, String s, Main_Window main) {
		this.main = main;
		this.om = om;
		view = new view.Order_Comment(s);
		
		buttonsListeners();
		
		view.setModal(true);
		view.setVisible(true);
	}
	
	protected void buttonsListeners() {
		view.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				om.order.comment = view.commentText.getText();
				om.view.commentText.setText(om.order.comment);
				main.currentPanel = 2;
				exit();
			}
		});
		
		view.cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
	
	protected void exit() {
		view.setVisible(false);
		view.dispose();
	}

}
