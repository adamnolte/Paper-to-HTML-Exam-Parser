package mzon.adam.parser;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MyDialogJFrame extends JFrame{
	public MyDialogJFrame(){
		super();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
