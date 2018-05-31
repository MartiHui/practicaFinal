package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Product_Comment extends Order_Comment {
	private int idx;
	
	public Product_Comment(Order_Manager om, String s, int idx) {
		super(om, s);
		this.idx = idx;
	}
	
	@Override
	protected void buttonsListeners() {
		view.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				om.order.lines.get(idx).comment = view.commentText.getText();
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
