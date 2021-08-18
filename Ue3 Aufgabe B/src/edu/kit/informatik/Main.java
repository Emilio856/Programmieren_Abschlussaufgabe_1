package edu.kit.informatik;

import java.util.HashMap;

public class Main {
    
    static String file = "C:\\Users\\emili\\Desktop\\Programmieren\\dictionary.txt";
    private static String command;
    
    public static void main(String args[]) {
        Dictionary dictionary = new Dictionary();
        String[] fileArray;
        String myFile = args[0];
        fileArray = Terminal.readFile(myFile);
        
        /*for (String s : fileArray) {
            String[] dictionaryLine = s.split(" - ");
            dictionary.add(dictionaryLine[0], dictionaryLine[1]);
            
        }*/
        //HashMap<String, String> map = new HashMap<String, String>();
        //Terminal.printLine(map.size());
        //map.put("YO", "TU");
        //Terminal.printLine(map.size());
        //Terminal.printLine(map.get("YO"));
        
        
        
        while (!dictionary.getQuit()) {
            command = Terminal.readLine();
            String[] input1 = command.split(" ", 2);
            
            if (input1.length == 1) {
                switch (input1[0]) {
                case "print":
                    dictionary.print();
                    //DO SOMETHING!
                    break;
                    
                case "quit":
                    //DO SOMETHING!
                    break;
                    
                default:
                    //ERROR
                }
            }
            else if (input1.length == 2) {
                String[] input2 = input1[1].split(" ");
                switch (input1[0]) {
                case "remove":
                    //DO SOMETHING
                    break;
                    
                case "print":
                    //DO SOMETHING
                    break;
                
                case "translate":
                    //DO SOMETHING
                    break;
                    
                case "add":
                    if (isWord(input2[0]) && isWord(input2[1])) {
                        dictionary.add(input2[0], input2[1]);
                    }
                    else {
                        Terminal.printError("wrong parameters!");
                    }
                    
                default:
                    //ERROR
                }
            }
        }
    }
    
    public static boolean isWord(String word) {
        return word.matches("[a-zA-Zƒ‹÷‰¸ˆﬂ]+");
    }
}