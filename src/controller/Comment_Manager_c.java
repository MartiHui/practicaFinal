package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Comment_Manager_v;

public class Comment_Manager_c {
	protected Comment_Manager_v view;
	public Order_Manager_c om;
	private Main_Window_c main;
	
	public Comment_Manager_c(Order_Manager_c om, String s, Main_Window_c main) {
		this.main = main;
		this.om = om;
		view = new view.Comment_Manager_v(s);
		
		buttons();
		
		view.setModal(true);
		view.setVisible(true);
	}
	
	protected void buttons() {
		view.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				om.order.comment = view.commentText.getText();
				om.view.orderCommentText.setText(om.order.comment);
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
