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
    JTextField inputTF;
	JButton runPDA, chooseFile;
	JLabel titleLabel, authorA, authorB, inputL;
	
	public Main(){
		titleLabel = new JLabel("Top Down Parsing");
		titleLabel.setBounds(150,-30,400,100);
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
		titleLabel.setForeground(new Color(255,255,255));
		authorA = new JLabel("Alejandro Moreno");
		authorA.setBounds(30,0,400,100);
		authorA.setForeground(new Color(255,255,255));
		authorB = new JLabel("Fabricio Fuentes");
		authorB.setBounds(150,0,400,100);
		authorB.setForeground(new Color(255,255,255));

		inputL = new JLabel("String to Process");
		inputL.setBounds(30,60,400,100);
		inputL.setForeground(Color.white);
		inputTF = new JTextField();
		inputTF.setBounds(30,120,50,20);

		runPDA = new JButton("Run");
		runPDA.setBounds(130,390,120,50);

		chooseFile = new JButton("Choose File");
		chooseFile.setBounds(130,250,120,50);

		setContentPane(new JLabel(new ImageIcon("img/Wall1.png")));

		add(titleLabel);
		add(authorA);
		add(authorB);
		add(inputL);
		add(inputTF);
		add(runPDA);
		add(chooseFile);

		runPDA.addActionListener(this);
		chooseFile.addActionListener(this);
		setSize(400,500);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == runPDA){
			input = inputTF.getText();
			if(input == null || input == "")
				System.out.println("Please insert a valid string");
			else
				pushDown(input);
		}
		
		if(evt.getSource() == chooseFile){
			ChooseFile();
		}
	}
	
	public static void main(String[] args){	
		new Main();
	}

	public static void ChooseFile(){
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
		}
	}

	public static void pushDown(String input){
		FileRead fileRead = new FileRead();
		java.util.List<String> fileToSplit = new LinkedList<String>();
		// File file = new File("test1.txt");
		if(selectedFile == null)
			System.out.println("Please Select a file with the grammar");
		else{
			fileToSplit = fileRead.readFile(selectedFile);
			PDA pda = new PDA();
			pda.splitFile(fileToSplit);
			System.out.println(pda.topDown(input));
			//pda.topDown("abba");
		}

	}
}