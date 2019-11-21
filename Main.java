/**
* This program implements an application which follows the Top-down parsing
* algorithm. It receives a string and determines whether or not it belongs to 
* a given grammar.
* This is the main class and where we are setting our window for the graphic interface.
* The class extends form the JFrame class and implements the ActionListener interface.
* @author  Alejandro Moreno Loza - A01654319
* @author  Fabricio Andre Fuentes Fuentes - A01338527
*/

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame implements ActionListener{
	public static final long serialVersionUID = -1;
	
	static String input;
	static File selectedFile;
	int width = 480;
	int height = 720;
    JTextField inputTF;
	JButton runTDP, chooseFile;
	static JLabel titleLabel, authorA, authorB, inputL, outputL;
	
	/**
	* This is a constructor for the Main class and where we are initializing
	* the elements for the interface and for building it.
	*/

	public Main(){
		titleLabel = new JLabel("Top Down Parsing");
		titleLabel.setBounds(0,0,400,100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
		titleLabel.setForeground(new Color(0,0,0));

		authorA = new JLabel("Alejandro Moreno", SwingConstants.CENTER);
		authorA.setBounds(-100,50,400,100);
		authorA.setForeground(new Color(0,0,0));

		authorB = new JLabel("Fabricio Fuentes", SwingConstants.CENTER);
		authorB.setBounds(100,50,400,100);
		authorB.setForeground(new Color(0,0,0));

		inputL = new JLabel("String to Process", SwingConstants.CENTER);
		inputL.setBounds(0,100,400,100);
		inputL.setForeground(new Color(0,0,0));

		inputTF = new JTextField();
		inputTF.setBounds(180,170,50,20);

		outputL = new JLabel("Result", SwingConstants.CENTER);
		outputL.setBounds(0,180,400,100);
		outputL.setForeground(new Color(0,0,0));

		chooseFile = new JButton("Choose File");
		chooseFile.setBounds(140,300,120,50);

		runTDP = new JButton("Run");
		runTDP.setBounds(140,360,120,50);

		setContentPane(new JLabel(new ImageIcon("img/Sky.jpg")));

		add(titleLabel);
		add(authorA);
		add(authorB);
		add(inputL);
		add(outputL);
		add(inputTF);
		add(runTDP);
		add(chooseFile);

		runTDP.addActionListener(this);
		chooseFile.addActionListener(this);
		setSize(width,height);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	/**
	* This method is an action Listener for both buttons in our UI.
	* @param evt Event triggered by a pressed button in the UI.
	*/
	public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == runTDP){
			input = inputTF.getText();
			pushDown(input);
		}
		
		if(evt.getSource() == chooseFile)
			ChooseFile();
	}
	/**
	* In this main method we are creating a new instance of the Main class
	*/
	public static void main(String[] args){	
		new Main();
	}

	/**
	* This method is called when the choose file button is pressed and it opens
	* a file browser in the user's computer.
	*/
	public static void ChooseFile(){
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
		}
	}

	/**
	* This method runs the top-down parsing program with the string given by the user
	* which was retrieved from the input text field in the UI.
	* @param input String given by the user.
	*/
	public static void pushDown(String input){
		System.out.println(input);
		FileRead fileRead = new FileRead();
		java.util.List<String> fileToSplit = new LinkedList<String>();
		boolean check = false;

		if(selectedFile == null)
			outputL.setText("Please Select a file with the grammar");
		else{
			fileToSplit = fileRead.readFile(selectedFile);
			TDP tdp = new TDP();
			tdp.splitFile(fileToSplit);

			if(input.equals(null) || input.equals("") || input.equals(" "))
				outputL.setText("Please insert a valid string");
			else{
				check = tdp.topDown(input);

				if(check)
					outputL.setText("String belongs to the grammar");
				else
					outputL.setText("String does not belong to the grammar");
			}
		}
		System.out.println(check);
	}
}