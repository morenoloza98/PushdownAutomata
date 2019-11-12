/**
* This class is for reading a text file
* @author  Alejandro Moreno Loza - A01654319
* @author  Fabricio Andre Fuentes Fuentes - A01338527
*/

import java.util.*;
import java.io.*;
public class PDA{
	public void splitFile(List<String> list){
        Dictionary<String, List<String>> productionsDictionary = new Hashtable<>();
        int listSize = list.size(); 
        
        String nonTerminalSymbolsLine;
        List<String> nonTerminalSymbols = new LinkedList<String>();
        
        String terminalSymbolsLine;
        List<String> terminalSymbols = new LinkedList<>();

        String initialSymbol = list.get(2);;

        int remainingLines = listSize - 3;
        String[] productions = new String[remainingLines];
        String[] divideProduction;
        List<String> leftSide = new LinkedList<String>();
        List<String> rightSide = new LinkedList<String>();

        
        nonTerminalSymbolsLine = list.get(0);
        String[] nonTerminalSymbolsSplit = nonTerminalSymbolsLine.split(",");
        for (String i : nonTerminalSymbolsSplit) {
            nonTerminalSymbols.add(i);
        }

        terminalSymbolsLine = list.get(1);
        String[] terminalSymbolsSplit = terminalSymbolsLine.split(",");
        for (String i : terminalSymbolsSplit) {
            terminalSymbols.add(i);
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
}