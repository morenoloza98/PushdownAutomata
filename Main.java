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
	JButton runPDA, chooseFile;
	static JLabel titleLabel, authorA, authorB, inputL, outputL;
	
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

		runPDA = new JButton("Run");
		runPDA.setBounds(140,360,120,50);

		setContentPane(new JLabel(new ImageIcon("img/Sky.jpg")));

		add(titleLabel);
		add(authorA);
		add(authorB);
		add(inputL);
		add(outputL);
		add(inputTF);
		add(runPDA);
		add(chooseFile);

		runPDA.addActionListener(this);
		chooseFile.addActionListener(this);
		setSize(width,height);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == runPDA){
			input = inputTF.getText();
			pushDown(input);
		}
		
		if(evt.getSource() == chooseFile)
			ChooseFile();
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
		System.out.println(input);
		FileRead fileRead = new FileRead();
		java.util.List<String> fileToSplit = new LinkedList<String>();

		if(selectedFile == null)
			outputL.setText("Please Select a file with the grammar");
		else{
			fileToSplit = fileRead.readFile(selectedFile);
			PDA pda = new PDA();
			pda.splitFile(fileToSplit);
			//System.out.println(pda.topDown(input));

			if(input.equals(null) || input.equals("") || input.equals(" "))
				outputL.setText("Please insert a valid string");
			else{
				boolean check = pda.topDown(input);

				if(check)
					outputL.setText("String belongs to the grammar");
				else if(!check)
					outputL.setText("String does not belong to the grammar");
			}
		}
	}
}