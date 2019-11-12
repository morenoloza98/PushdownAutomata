/**
* This class is for reading a text file
* @author  Alejandro Moreno Loza - A01654319
* @author  Fabricio Andre Fuentes Fuentes - A01338527
*/

import java.util.*;
import java.io.*;

public class FileRead{
	public List<String> readFile(File file){
		List<String> list = new LinkedList<>();

    	try{
    		BufferedReader br = new BufferedReader(new FileReader(file));
    		String st;
			while ((st = br.readLine()) != null){
		      list.add(st);
		    }
    	}catch (IOException e) {
			e.printStackTrace();
		}

		printFile(list);
		return list;
	}

	public void printFile(List<String> fileDivided){
		for (int i = 0; i < fileDivided.size(); i++) {
			System.out.println(fileDivided.get(i));
		}
	}
}