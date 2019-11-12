import java.util.*;
import java.io.*; 

public class Main{

	public static void main(String[] args) throws Exception{
		FileRead fileRead = new FileRead();
		File file = new File("test1.txt");
		List<String> fileToSplit = fileRead.readFile(file);
		
		PDA pda = new PDA();
		pda.splitFile(fileToSplit);
	}
}