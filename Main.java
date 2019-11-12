import java.util.*;
import java.io.*; 

public class Main{

	public static void main(String[] args) throws Exception{
		FileRead fileRead = new FileRead();
		File file = new File("test1.txt");
		fileRead.readFile(file);
	}
}