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

        System.out.println(productionsDictionary);
    }

    public void topDown(String inputStr){
        Queue<String> goal = new LinkedList<>();
        // Queue<Character> inputQ = new LinkedList<>();
        // Queue<Character> inputButInQ = new LinkedList<>();

        // for(int i = 0; i<input.length(); i++){
        //     inputQ.add(input.charAt(i));
        //     inputButInQ.add(input.charAt(i));
        // }

        // for(int i = 0; i<initialSymbol.length(); i++){
        //     goal.add(initialSymbol.charAt(i));
        // }

        //Put S as root of 'T' (in this case, the graph)
        graph.addVertice(initialSymbol);

        //Add the init symbol to the queue
        goal.add("AbbbB");
        
        do {
            
            String q = goal.poll();
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
                    else if(leftMost != null && i!=0){
                        u = q.substring(0, i);
                        v = q.substring(i+1,q.length());
                    }
                }
                
            }
            System.out.println("u: " + u);
            System.out.println("leftMot: " +leftMost);
            System.out.println("v: " +v);

            for (int i = 0; i < productionsDictionary.get(leftMost).size(); i++) {
                if (productionsDictionary.get(leftMost).isEmpty()){
                    done = true;
                }
                else 
                {
                    w = productionsDictionary.get(leftMost).get(i);
                    String uwv = u + w + v;
                    // if (uwv){

                    // }
                    // for (int j = 0; j < w.length(); j++) {
                    //     if(terminalSymbols.contains(nextRule.charAt(j)) || nextRule.charAt(j) == inputStr.charAt(0)){
                    //         goal.add(w);
                    //         graph.addVertice(nextRule);
                    //         graph.addArista(initialSymbol,nextRule);
                    //     }
                    //     i = j;
                }
                    
                
            }

                // String nextRule = productionsDictionary.get(q).get(i);
                // for (int j = 0; j < nextRule.length(); j++) {
                //     if(nonTerminalSymbols.contains(nextRule.charAt(j))){
                //         q = nextRule;
                //         String newRules = productionsDictionary.get(q).get(j);
                //         if(newRules)
                //     }    
                // }
                

                // System.out.println("NR: " + q);
                // System.out.println("Goal: " + goal);
            
            /*if(terminalSymbols.contains(goal.peek() )){
                if(goal.peek().equals(inputQ.peek())){
                    goal.remove();
                    inputQ.remove();
                }
                else{
                    System.out.println("String does not belong to the grammar");
                    done = true;
                } 
            }
            else{
                Character q = goal.peek();
                //Checar producciones y checar cuantas son
                if(productionsDictionary.get(q).size() == 1){
                    //Sustituir por esa única producción
                }
                else if(productionsDictionary.get(q).size() > 1){
                    for(int i = 0; i<productionsDictionary.get(q).size(); i++){
                        Character[] c = productionsDictionary.get(q).get(i);
                        if(c[0].equals(inputQ.peek()) || nonTerminalSymbols.contains(c[0]) ){
                            //Sustituir por esas producciones
                        }
                    }
                }
            }*/

            
            
        } while (!goal.isEmpty() || inputStr.equals(goal));
        
        


    }

}