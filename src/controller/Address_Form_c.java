package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Address;
import view.Address_Form_v;

public class Address_Form_c {
	public Address_Form_v view;
	public Address a;
	
	public Address_Form_c(Address a, boolean newAddress) {
		this.a = a;
		
		this.view = new Address_Form_v(a);

		fillData();
		buttons();
		
		if (newAddress) {
			view.delete.setEnabled(false);
			view.warning.setVisible(true);
		} else {
			view.warning.setVisible(false);
		}
		
		view.repaint();
		view.revalidate();
		view.setModal(true);
		view.setVisible(true);
	}
	
	private void fillData() {
		if (a != null) {
			try {
				view.addressText.setText(a.address_name);
				view.zoneText.setText(a.zone);
				view.commentText.setText(a.comment);
			} catch (Exception e) {}
		}
	}
	
	private void buttons() {
		view.save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				a.address_name = view.addressText.getText();
				a.zone = view.zoneText.getText();
				a.comment = view.commentText.getText();
				a.update();
				exit();
			}
		});
		
		view.cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		view.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				a.delete();
				exit();
			}
		});
	}
	
	private void exit() {
		view.setVisible(false);
		view.dispose();
	}
}
