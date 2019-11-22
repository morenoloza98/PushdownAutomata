/**
* This class is for reading a text file
* @author  Alejandro Moreno Loza - A01654319
* @author  Fabricio Andre Fuentes Fuentes - A01338527
*/

import java.util.*;
import java.io.*;

public class FileRead{

	/**
	* Reads and stores in a list the file given to obtain the grammar's properties
	* @param file File that contains the grammar's properties
 	* @throws IOException  When a file is being read
	*/

	public List<String> readFile(File file){
		List<String> list = new LinkedList<>(); //Stores the lines of the file

    	try{
    		BufferedReader br = new BufferedReader(new FileReader(file));
    		String st;
			while ((st = br.readLine()) != null){
		      list.add(st);
		    }
    	}catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}