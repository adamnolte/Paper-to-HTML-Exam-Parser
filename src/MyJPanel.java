package mzon.adam.parser;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class MyJPanel extends JPanel{
	public MyJPanel(){
		super();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
