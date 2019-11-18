    /**
* This class is for reading a text file
* @author  Alejandro Moreno Loza - A01654319
* @author  Fabricio Andre Fuentes Fuentes - A01338527
*/

import java.util.*;
import java.io.*;
public class PDA{
    Dictionary<String, List<String>> productionsDictionary = new Hashtable<>();
    int listSize; 
    
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

        System.out.println(productionsDictionary);
    }

    public void topDown(String input){
        Queue<Character> goal = new LinkedList<>();
        Queue<Character> inputQ = new LinkedList<>();
        Queue<Character> inputButInQ = new LinkedList<>();

        for(int i = 0; i<input.length(); i++){
            inputQ.add(input.charAt(i));
            inputButInQ.add(input.charAt(i));
        }

        for(int i = 0; i<initialSymbol.length(); i++){
            goal.add(initialSymbol.charAt(i));
        }

        
        boolean done = false;
        
        do {
            
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

            
            
        } while (goal.isEmpty() || inputQ == inputButInQ);
        
        


    }

}