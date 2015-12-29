package mzon.adam.parser;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame{

	private MyJPanel mainPanel;
	private JLabel header;
	private JLabel information;
	private JLabel createdBy;
	private JButton submit;
	
	private JFilePicker inFileP;
	private JFilePicker outFileP;
	
	private String example = "1.(tab)Example Question 1<br>This must be a blank line.<br>A.(tab)Answer A<br>B.(tab)Answer B<br>This must be a blank line.<br>2.(tab)Example Question 2<br>This must be a blank line.<br>A.(tab)Answer A<br>B.(tab)Answer B<br>C.(tab)Answer C<br><br></html>";
	
	public Window(){
		super("MZON Paper to Online Exam Parser");
		initWindow();
	}
	
	private void initWindow(){
		try{
			URL url = Main.class.getResource("/mizzou_paw.png");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		setFilePickers();
		createLayout();
        this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	private void setFilePickers(){
		inFileP = new JFilePicker("Select Input Text File:", "Browse...", "Select Input File");
		outFileP = new JFilePicker("Select Output Folder:", "Browse...", "Select Output Folder");
		inFileP.setMode(JFilePicker.MODE_OPEN);
		outFileP.setMode(JFilePicker.MODE_FOLDER);
		inFileP.addFileTypeFilter(".txt", "Text Files");
	}
	
	private void createLayout(){
		mainPanel = new MyJPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS ));
		mainPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		header = new JLabel("Welcome to Mizzou Online Paper to Online Exam Parser!");
		header.setFont(new Font("Serif", Font.BOLD, 16));
		addToLayout(header);
		
		information = new JLabel("<html><center><br>How to Use:<br>Select an input file and output folder.<br>Make sure your input file is in the following format and begins with question 1.<br>You can have varying amounts of answer choices.<br>There must be a tab between question number and the question.<br>There also must be a tab between the answer letter and the answer.<br>If there is a not in the question it will be wrapped with bold tags.</center><br>" + example, SwingConstants.CENTER);
		addToLayout(information);
		addToLayout(inFileP);
		addToLayout(outFileP);
		
		submit = new JButton();		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				MyDialogJFrame frame = new MyDialogJFrame();
				if(inFileP.getSelectedFilePath().isEmpty() || outFileP.getSelectedFilePath().isEmpty()){					
					JOptionPane.showMessageDialog(frame, "You must select an input file and output folder!", "File Selection Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					SubmitHandler submit = new SubmitHandler(inFileP.getSelectedFilePath(), outFileP.getSelectedFilePath());
					if(submit.parseFile()){
						Object[] options = {"Okay", "Open File"};
						int selection = JOptionPane.showOptionDialog(frame, "File Successfully Parsed. Path to file: " + outFileP.getSelectedFilePath() + "\\MZONParserOutput.txt", "Success", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
						if(selection == JOptionPane.NO_OPTION){
							try {
								File file = new File(outFileP.getSelectedFilePath() + "\\MZONParserOutput.txt");
								Desktop dt = Desktop.getDesktop();
								dt.open(file);
							} 
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					else{
						JOptionPane.showMessageDialog(frame, "Error in parsing file. Please check format of file.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		submit.setText("Submit");
		addToLayout(submit);
		
		createdBy = new JLabel("<html><br><center>Created By Adam Nolte</center></html>", SwingConstants.CENTER);
		createdBy.setFont(new Font("Serif", Font.ITALIC, 10));
		addToLayout(createdBy);
		
		this.add(mainPanel);
	}
	
	private void addToLayout(JComponent component){
		component.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(component);
	}
}
