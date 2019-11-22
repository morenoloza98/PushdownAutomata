/**
* This class defines objects that contain dictionaries
* and emulate the process of the top-down parsing method.
* @author  Alejandro Moreno Loza - A01654319
* @author  Fabricio Andre Fuentes Fuentes - A01338527
*/

import java.util.*;
import java.io.*;
import java.lang.Character.*;

public class TDP{
    Dictionary<String, List<String>> productionsDictionary = new Hashtable<>(); //The keys are the non terminal symbols of
                                                                                //the grammar and the lists of each one of
                                                                                //them are their rules (productions)
    int listSize; 
    
    String nonTerminalSymbolsLine;
    List<String> nonTerminalSymbols = new LinkedList<String>(); //List that contains the non terminal symbols
                                                                //of the grammar
    
    String terminalSymbolsLine;
    List<Character> terminalSymbols = new LinkedList<>(); //List that contains the terminal symbols
                                                          //of the grammar

    String initialSymbol; //Contains the initial symbol of the grammar given in the txt file

    int remainingLines;
    String[] productions; //Contains the set of rules of the given grammar
    String[] divideProduction; //Helps as a support to divide each production into two 
    List<String> leftSide = new LinkedList<String>(); //Contains all the left sides of the grammar rules
    List<String> rightSide = new LinkedList<String>(); //Contains all the right sides of the grammar rules

    /**
    * Splits the list of lines withdrawed from the file, divides them and stores them
    * in variables.
    * Fills the dictionary used later on in the top down parsing method.
    * @param list List of strings containing the lines of the file
    */
	public void splitFile(List<String> list){
        productionsDictionary = new Hashtable<>();
        listSize = list.size(); 
        
        nonTerminalSymbols = new LinkedList<String>();
        
        terminalSymbols = new LinkedList<>();

        initialSymbol = list.get(2);

        remainingLines = listSize - 3;
        productions = new String[remainingLines];

        
        nonTerminalSymbolsLine = list.get(0);
        String[] nonTerminalSymbolsSplit = nonTerminalSymbolsLine.split(",");
        for (String i : nonTerminalSymbolsSplit) {
            nonTerminalSymbols.add(i);
        }

        terminalSymbolsLine = list.get(1);
        String[] terminalSymbolsSplit = terminalSymbolsLine.split(",");

        for (int i = 0; i<terminalSymbolsSplit.length; i++) {
            terminalSymbols.add( terminalSymbolsSplit[i].charAt(0) );
        }

        for (int i = 0; i<remainingLines; i++) {
	        productions[i] = list.get(3+i);
        }
        
        for (int i = 0; i < productions.length; i++){
	        divideProduction = productions[i].split("->");
	        String[] temp = divideProduction;
	        leftSide.add(temp[0]);
	        rightSide.add(temp[1]);
        }
        
        for (String ls : nonTerminalSymbols) {
            List<String> pd = new LinkedList<>();
            for(int i = 0; i<leftSide.size(); i++){
                if(ls.equals(leftSide.get(i) )){
                    pd.add(rightSide.get(i));
                }
                
            }
            productionsDictionary.put(ls, pd);
        }

    }

    /**
    * This method follows the top-down parsing algorithm for determining if a given
    * string belongs or not to a given grammar.
    * @param inputStr The string given by the user and retrieved from the textfield.
    * @return a boolean value depending on whethter the string belongs (true) or not (false)
    */

    public boolean topDown(String inputStr){

        Queue<String> goal = new LinkedList<>(); //Queue used for storing the productions
                                                 //done by the cicles bellow; simulating the substitution
                                                 //of each non terminal symbol
        goal.add(initialSymbol);
        String p = inputStr;
        String uwv = null;
        boolean done = false;
        List<String> toSplit = new LinkedList<>();

        while (!goal.isEmpty() || !p.equals(uwv)) {
            String q = goal.poll(); //Stores the head of the queue for its analysis.
            if(q == null)
                break;
            else
                toSplit = splitStringInThree(q);
            String u = toSplit.get(0);
            String leftMost = toSplit.get(1);
            String v = toSplit.get(2);

            for (int i = 0; i < productionsDictionary.get(leftMost).size() && (!p.equals(uwv)); i++) {
                if (productionsDictionary.get(leftMost).isEmpty()){
                    done = true;
                }
                else 
                {
                    String w = productionsDictionary.get(leftMost).get(i);
                    if(w.equals("lmd"))
                        w = "";
                    uwv = u + w + v;
                    uwv.replaceAll("\\s+","");
                    List<String> splitted = splitStringInThree(uwv);
                    String u2 = splitted.get(0);
                    String leftMost2 = splitted.get(1);
                    String v2 = splitted.get(2);
                    if(u2.length()<=p.length()){
                        if( leftMost2 != null && u2.equals(p.substring(0, u2.length()) ) ){
                            goal.add(uwv);
                        } 
                    }
                                  
                }
                   
            }

         
        }

        //Verifies if the string belongs to the grammar
        if (p.equals(uwv)){
            return true;
        }else{
            return false;
        }
    }

    /**
    * This method divides a string into three for its use in the top down method.
    * @param str String given to split
    * @return List with three elements which are the three parts of the string.
    */

    public List<String> splitStringInThree(String str){
        List<String> toReturn = new LinkedList<>(); //List to be returned with the splitted string
        String u = null;
        String leftMost = null;
        String v = null;
        for (int i = 0; i < str.length() && leftMost == null; i++) {
            for (int j = 0; j < nonTerminalSymbols.size(); j++) {
                if(leftMost == null && str.charAt(i) == nonTerminalSymbols.get(j).charAt(0)){
                    leftMost = String.valueOf(str.charAt(i));
                }
                else
                {
                    u = str;
                }
                if(leftMost != null && i == 0){
                    u = str.substring(0, i);
                    v = str.substring(i+1,str.length());
                }
                else if(leftMost != null && i!=0){
                    u = str.substring(0, i);
                    v = str.substring(i+1,str.length());
                }
            }
            
        }
        toReturn.add(u);
        toReturn.add(leftMost);
        toReturn.add(v);

        return toReturn;
    }

}