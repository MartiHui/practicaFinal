package controller;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import base_classes.Panel_Base;
import view.Main_Window_v;

public class Main_Window_c {
	public Main_Window_v view;
	
	public Orders_Viewer_c ordersViewer;
	public Order_Manager_c orderManager;
	
	public KeyEventDispatcher keyEventDispatcher;
	public int currentPanel;
	
	public Main_Window_c() {
		view = new view.Main_Window_v();
		ordersViewer = new Orders_Viewer_c(this);
		
		view.getContentPane().add(BorderLayout.CENTER, ordersViewer.view);
		
		keyBoardController();
		menu();
		
		currentPanel = 1;
		
		view.repaint();
		view.revalidate();
	}
	
	private void keyBoardController() {
		keyEventDispatcher = new KeyEventDispatcher() {
			  @Override
			  public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (currentPanel == 1) {
						//mw.console.setText(Integer.toString(e.getKeyChar()));
						ordersViewer.console(e.getKeyChar());
					} else if (currentPanel == 2) {
						if (e.getKeyCode() == KeyEvent.VK_F1) {
							orderManager.newComment();
						} else if (e.getKeyCode() == KeyEvent.VK_F2) {
							orderManager.newPrice();
						} else {
							orderManager.consoleEvent(e.getKeyChar());
						}
					}
				}
			    return false;
			  }
			};


		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
			}
		});
	}
	
	private void menu() {
		view.menuData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openDataManager();
			}
		});
		
		view.menuOrders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openOrdersViewer();
			}
		});
	}
	
	private void saveOrder() {
		if (currentPanel == 2) {
			orderManager.exit();
		}
	}
	
	private void openDataManager() {
		saveOrder();
		swapPanels(-1, (new Data_Viewer_c(this).view));
	}
	
	private void openOrdersViewer() {
		saveOrder();
		swapPanels(1, this.ordersViewer.view);
	}
	
	public void swapPanels(int newPanel, Panel_Base p) {
		view.getContentPane().removeAll();
		view.getContentPane().add(BorderLayout.CENTER, p);
		currentPanel = newPanel;
		view.repaint();
		view.revalidate();
	}

	public static void main(String[] args) {
		new Main_Window_c();
	}
}
