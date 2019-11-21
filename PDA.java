    /**
* This class is for reading a text file
* @author  Alejandro Moreno Loza - A01654319
* @author  Fabricio Andre Fuentes Fuentes - A01338527
*/

import java.util.*;
import java.io.*;
import java.lang.Character.*;

public class PDA{
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

        do {
            
            String q = goal.poll();
            splitStringInThree(q);
            String u = splitStringInThree(q).get(0);
            String leftMost = splitStringInThree(q).get(1);
            String v = splitStringInThree(q).get(2);

            for (int i = 0; i < productionsDictionary.get(leftMost).size() && !p.equals(uwv); i++) {
                if (productionsDictionary.get(leftMost).isEmpty()){
                    done = true;
                }
                else 
                {
                    String w = productionsDictionary.get(leftMost).get(i);
                    uwv = u + w + v;
                    uwv.replaceAll("\\s+","");
                    List<String> splitted = splitStringInThree(uwv);
                    String u2 = splitted.get(0);
                    String leftMost2 = splitted.get(1);
                    String v2 = splitted.get(2);
                    // if(u2 != null){
                        // if(p.length() >= u2.length()){
                            if( leftMost2 != null && u2.equals(p.substring(0, u2.length()) ) ){
                                goal.add(uwv);
                            } else if(leftMost2 == null){
                                if(p.equals(uwv)){
                                    return true;
                                }
                                else
                                    return false;
                            }
                        // }else{
                            // return false;
                        // }
                    // }
                                  
                }
                   
            }
            
        } while (!goal.isEmpty() || !p.equals(uwv));

        if (p.equals(uwv)){
            return true;
        }else{
            return false;
        }
    }

    public List<String> splitStringInThree(String str){
        List<String> toReturn = new LinkedList<>();
        String u = null;
        String leftMost = null;
        String v = null;
        for (int i = 0; i < str.length() && leftMost == null; i++) {
            for (int j = 0; j < nonTerminalSymbols.size(); j++) {
                if(str.charAt(i) == nonTerminalSymbols.get(j).charAt(0)){
                    leftMost = String.valueOf(str.charAt(i));
                    System.out.println("Left most: " + leftMost);
                }
                if(leftMost != null && i == 0){
                    System.out.println("If divide");
                    u = str.substring(0, i);
                    v = str.substring(i+1,str.length());
                    System.out.println("u: " + u);
                    System.out.println("v: " + v);
                }
                else if(leftMost != null && i!=0){
                    System.out.println("Else divide");
                    u = str.substring(0, i);
                    v = str.substring(i+1,str.length());
                    System.out.println("u: " + u);
                    System.out.println("v: " + v);
                }
            }
            
        }
        toReturn.add(u);
        toReturn.add(leftMost);
        toReturn.add(v);

        return toReturn;
    }

}