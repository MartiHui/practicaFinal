package controller;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import view.Order_Manager;

public class Main_Window {
	public view.Main_Window view;
	
	public Order_Tables ot_controller;
	public controller.Order_Manager om_controller;
	
	public KeyEventDispatcher keyEventDispatcher;
	public int currentPanel;
	
	public Main_Window() {
		view = new view.Main_Window();
		ot_controller = new Order_Tables(this);
		
		view.getContentPane().add(BorderLayout.CENTER, ot_controller.view);
		keyBoardController();
		
		currentPanel = 1;
		
		view.repaint();
		view.revalidate();
	}
	
	private void keyBoardController() {
		keyEventDispatcher = new KeyEventDispatcher() {
			  @Override
			  public boolean dispatchKeyEvent(final KeyEvent e) {
			    if (e.getID() == KeyEvent.KEY_TYPED) {
					if (currentPanel == 1) {
						//mw.console.setText(Integer.toString(e.getKeyChar()));
						ot_controller.consoleEvent(e.getKeyChar());
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
	
	public static void main(String[] args) {
		new Main_Window();
	}
}
