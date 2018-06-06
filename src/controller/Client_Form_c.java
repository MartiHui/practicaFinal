package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Client;
import view.Client_Form_v;

public class Client_Form_c {
	public Client_Form_v view;
	public Data_Viewer_c dv;
	public Client c;
	
	public Client_Form_c(Data_Viewer_c dv, Client c) {
		this.dv = dv;
		this.c = c;
		
		this.view = new Client_Form_v(c);
		
		fillData();
		buttons();
		
		view.repaint();
		view.revalidate();
		view.setModal(true);
		view.setVisible(true);
	}
	
	private void fillData() {
		view.phoneText.setText(c.phone_number);
	}
	
	private void buttons() {
		view.save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifyClient();
				exit();
			}
		});
		
		view.cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
	
	private void modifyClient() {
		c.phone_number = view.phoneText.getText();
		c.update();
	}
	
	private void exit() {
		dv.clientTable();
		view.setVisible(false);
		view.dispose();
	}
}
