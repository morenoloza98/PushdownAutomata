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
    Grafo<String> graph = new Grafo<>();

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

    public void topDown(String inputStr){
        Queue<String> goal = new LinkedList<>();

        //Put S as root of 'T' (in this case, the graph)
        // graph.addVertice(initialSymbol);

        //Add the init symbol to the queue
        goal.add(initialSymbol);
        String p = inputStr;
        String uwv = null;

        do {
            
            String q = goal.poll();
            String p = inputStr;
            System.out.println(p);
            String u = null;
            String leftMost = null;
            String v = null;
            done = false;
            String w = null;
            for (int i = 0; i < q.length() && leftMost == null; i++) {
                for (int j = 0; j < nonTerminalSymbols.size() ; j++) {
                    if(q.charAt(i) == nonTerminalSymbols.get(j).charAt(0)){
                        leftMost = String.valueOf(q.charAt(i));
                    }
                    else if(leftMost != null && i == 0){
                        v = q.substring(i+1,q.length());
                    }
                    else 
                    {
                        String w = productionsDictionary.get(leftMost).get(i);
                        uwv = u + w + v;
                        uwv.replaceAll("\\s+","");
                        splitStringInThree(uwv);
                        String u2 = splitStringInThree(uwv).get(0);
                        String leftMost2 = splitStringInThree(uwv).get(1);
                        String v2 = splitStringInThree(uwv).get(2);
                        System.out.println("uwv: " + uwv);
                        for(int j = 0; j < uwv.length(); j++){
                            System.out.println("Enter for if j");
                            for(int k = 0; k < nonTerminalSymbols.size(); k++){
                                System.out.println("Enter for if k");
                                if(uwv.charAt(j) == nonTerminalSymbols.get(k).charAt(0) && u2 == p.substring(0, u2.length())){
                                    System.out.println("Enter if uwv");
                                    goal.add(uwv);
                                }
                                
                            }
                        }
                    }
                       
                }
                System.out.println("UWV: " + uwv);

            }
            
        } while (!goal.isEmpty() || !p.equals(uwv));

        if (p.equals(uwv)){
            System.out.println("Accepted");
        }else{
            System.out.println("Rejected");
        }
        
        


    }

    public List<String> splitStringInThree(String str){
        List<String> toReturn = new LinkedList<>();
        String u = null;
        String leftMost = null;
        String v = null;
        done = false;
        String w = null;
        for (int i = 0; i < str.length() && leftMost == null; i++) {
            for (int j = 0; j < nonTerminalSymbols.size() ; j++) {
                if(str.charAt(i) == nonTerminalSymbols.get(j).charAt(0)){
                    leftMost = String.valueOf(str.charAt(i));
                }
                else if(leftMost != null && i == 0){
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