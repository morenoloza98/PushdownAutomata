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
    Dictionary<String, List<String>> productionsDictionary = new Hashtable<>();
    int listSize; 
    boolean done;
    
    String nonTerminalSymbolsLine;
    List<String> nonTerminalSymbols = new LinkedList<String>();
    
    String terminalSymbolsLine;
    List<Character> terminalSymbols = new LinkedList<>();

    String initialSymbol;

    int remainingLines;
    String[] productions;
    String[] divideProduction;
    List<String> leftSide = new LinkedList<String>();
    List<String> rightSide = new LinkedList<String>();

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

    public boolean topDown(String inputStr){
        Queue<String> goal = new LinkedList<>();

        //Add the init symbol to the queue
        goal.add(initialSymbol);
        String p = inputStr;
        String uwv = null;

        while (!goal.isEmpty() || !p.equals(uwv)) {
            String q = goal.poll();
            List<String> toSplit = splitStringInThree(q);
            String u = toSplit.get(0);
            String leftMost = toSplit.get(1);
            String v = toSplit.get(2);

            for (int i = 0; i < productionsDictionary.get(leftMost).size() && (!p.equals(uwv)) || done; i++) {
                if (productionsDictionary.get(leftMost).isEmpty()){
                    done = true;
                }
                else 
                {
                    System.out.println("uLEFTv: " + u + leftMost + v);
                    String w = productionsDictionary.get(leftMost).get(i);
                    if(w.equals("lmd"))
                        w = "";
                    uwv = u + w + v;
                    uwv.replaceAll("\\s+","");
                    System.out.println("Leftmost: " + leftMost);
                    System.out.println("uwv before splitting: " + uwv);
                    List<String> splitted = splitStringInThree(uwv);
                    System.out.println("Splitted: " + splitted);
                    String u2 = splitted.get(0);
                    String leftMost2 = splitted.get(1);
                    String v2 = splitted.get(2);
                    System.out.println("U2: " + u2);
                    if(u2.length()<=p.length()){
                        System.out.println("P subs: " + (p.substring(0, u2.length())));
                        if( leftMost2 != null && u2.equals(p.substring(0, u2.length()) ) ){
                            System.out.println("Enter if u2: " + u2);
                            System.out.println("uwv: " + uwv);
                            goal.add(uwv);
                        } 
                    }
                                  
                }
                   
            }

         
        }


        if (p.equals(uwv)){
            return true;
        }else{
            return false;
        }
    }

    public List<String> splitStringInThree(String str){
        System.out.println("String in split in three: " + str);
        List<String> toReturn = new LinkedList<>();
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