package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Addig_Product_Comment extends Order_Comment {
	
	public Addig_Product_Comment(Order_Manager om, String s) {
		super(om, s);
	}
	
	@Override
	protected void buttonsListeners() {
		view.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				om.product_comment = view.commentText.getText();
				exit();
			}
		});
		
		view.cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
}
