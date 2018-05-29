package controller;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main_Window {
	public view.Main_Window mw_view;
	
	public Order_Tables ot_controller;
	
	public KeyEventDispatcher keyEventDispatcher;
	private int currentPanel;
	
	public Main_Window() {
		mw_view = new view.Main_Window();
		ot_controller = new Order_Tables();
		
		mw_view.getContentPane().add(BorderLayout.CENTER, ot_controller.ot_view);
		keyBoardController();
		
		currentPanel = 1;
		
		mw_view.repaint();
		mw_view.revalidate();
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

		mw_view.addWindowListener(new WindowAdapter() {
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
