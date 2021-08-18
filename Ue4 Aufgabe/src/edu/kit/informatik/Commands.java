package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains methods for a Class editor.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Commands {
    
    /**
     * Sets status of program. Finishes program if true.
     */
    private boolean quit = false;
    
    /**
     * Contains all constructs.
     */
    private ArrayList<Construct> constructsArray = new ArrayList<Construct>();
    
    /**
     * Default visibility in case attribute/method isn't public, protected or private.
     */
    private String defaultVisibility = "default";
    
    /**
     * final configuration for attribute/method.
     */
    private String finalType = "final";
    
    /**
     * Adds a new construct if it doesn't already exist.
     * @param input Input with information about construct type and name.
     * @return "OK" if added, else an error.
     */
    public String addConstruct(String input) {
        boolean canAdd = true;
        String returns = "";
        String construct = input.split(" ")[0];
        String constructName = input.split(" ")[1];
        
        if (Support.isConstruct(construct) && Support.isUpperCamelCase(constructName)
                && input.split(" ").length == 2) {
            
            //Checks if construct exists
            for (Construct con : constructsArray) {
                if (con.getName().equals(constructName)) {
                    canAdd = false;
                }
            }
            
            //Add construct
            if (canAdd && construct.equals("class")) {
                constructsArray.add(new Construct(construct, constructName, true, true));
                Attribute.addNewAttributeType(constructName);
                returns = "OK";
            }
            else if (canAdd && (construct.equals("interface") || construct.equals("enum"))) {
                constructsArray.add(new Construct(construct, constructName, false, false));
                Attribute.addNewAttributeType(constructName);
                returns = "OK";
            }
            else if (!canAdd) {
                returns = "Error, construct already exists!";
            }
        }
        else if (!Support.isConstruct(construct)) {
            returns = "Error, not a valid construct!";
        }
        else if (!Support.isUpperCamelCase(constructName)) {
            returns = "Error incorrect convention!";
        }
        else if (input.split(" ").length != 2) {
            returns = "Error, wrong number of parameters!";
        }
        return returns;
    }
    
    /**
     * Implements inheritance between two constructs. Interfaces and enums can't inheritate, only classes.
     * @param input Input with information about parent and child class.
     * @return "OK" if inherited successfully, else an error.
     */
    public String addExtends(String input) {
        String returns = "";
        String child = input.split(" ")[0];
        String parent = input.split(" ")[1];
        
        if (classExists(child) && classExists(parent) && !child.equals(parent)
                && constructsArray.get(constructIndex(parent)).getFather() == null) {
            constructsArray.get(constructIndex(child)).setFather(parent);
            returns = "OK";
        }
        else if (child.equals(parent)) {
            returns = "Error, same constructs were inserted!";
        }
        else if (!classExists(child) || !classExists(parent)) {
            returns = "Error, at least one of the constructs doesn't exist!";
        }
        else if (constructsArray.get(constructIndex(parent)).getFather() != null) {
            returns = "Error, class can only inherit once!";
        }
        return returns;
    }
    
    /**
     * Implements an interface to a class.
     * @param input Input with information about the class and interface.
     * @return "OK" if implemented successfully, else an error.
     */
    public String addImplements(String input) {
        String returns = "";
        String class1 = input.split(" ")[0];
        String interface1 = input.split(" ")[1];
        
        if (classExists(class1) && interfaceExists(interface1) && canImplement(class1, interface1)) {
            constructsArray.get(constructIndex(class1)).addImplemented(interface1);
            returns = "OK";
        }
        else if (!classExists(class1)) {
            returns = "Error, class doesn't exist!";
        }
        else if (!interfaceExists(interface1)) {
            returns = "Error, interface doesn't exist!";
        }
        return returns;
    }
    
    /**
     * Adds an attribute to a construct.
     * @param input The information about the attribute that will be added.
     * @return "OK" if attribute was added successfully, else returns an error.
     */
    public String addAttribute(String input) {
        //set up parameters
        String returns = "";
        String construct = input.split("::")[0];
        String visibility;
        boolean isFinal;
        String type;
        String name;
        
        if (Support.isVisibility(input.split(" ")[1])) {
            visibility = input.split(" ")[1];
            if (input.split(" ")[2].equals(finalType)) {
                isFinal = true;
                type = input.split(" ")[3];
                name = input.split(" ")[4];
            }
            else {
                isFinal = false;
                type = input.split(" ")[2];
                name = input.split(" ")[3];
            }
        }
        else {
            visibility = defaultVisibility;
            if (input.split(" ")[1].equals(finalType)) {
                isFinal = true;
                type = input.split(" ")[2];
                name = input.split(" ")[3];
            }
            else {
                isFinal = false;
                type = input.split(" ")[1];
                name = input.split(" ")[2];
            }
        }
        
        //Add attribute or print error
        if (constructExists(construct) && Support.isLowerCamelCase(name) && Support.isAttribute(type)
                && !attributeExists(construct, name)) {
            if (Support.isVisibility(visibility) && isFinal && input.split(" ").length == 5) {
                constructsArray.get(constructIndex(construct)).addAttribute(visibility, isFinal, type, name,
                        construct);
                returns = "OK";
            }
            else if ((Support.isVisibility(visibility) && !isFinal && input.split(" ").length == 4)
                || (visibility.equals(defaultVisibility) && isFinal && input.split(" ").length == 4)) {
                constructsArray.get(constructIndex(construct)).addAttribute(visibility, isFinal, type, name,
                        construct);
                returns = "OK";
            }
            else if (visibility.equals(defaultVisibility) && !isFinal && input.split(" ").length == 3) {
                constructsArray.get(constructIndex(construct)).addAttribute(visibility, isFinal, type, name,
                        construct);
                returns = "OK";
            }
        }
        else if (!constructExists(construct)) {
            returns = "Error, the construct doesn't exist!";
        }
        else if (!Support.isLowerCamelCase(name)) {
            returns = "Error, name convention is wrong!";
        }
        else if (!Support.isVisibility(visibility)) {
            returns = "Error, incorrect visibility!";
        }
        else if (!Support.isAttribute(type)) {
            returns = "Error, incorrect type of attribute!";
        }
        else if (attributeExists(construct, name)) {
            returns = "Error, attribute already exists!";
        }
        else {
            returns = "Error, incorrect syntax!";
        }
        return returns;
    }
    
    /**
     * Adds a method to a specific construct. Doesn't implement void type methods or
     * methods without parameters (client specifications (ilias-Forum)).
     * @param input String with the necessary information about the new method.
     * @return "OK" if method was added correctly. Else an error.
     */
    public String addMethod(String input) {
        //Set up parameters
        String returns = "";
        String[] parameters;
        String construct = input.split("::")[0];
        String visibility = input.split(" ")[1];
        boolean isFinal = false;
        String name;
        String type;
        String[] separateParameters = input.split("[\\(||//)]");
        if (Support.isVisibility(input.split(" ")[1])) {
            visibility = input.split(" ")[1];
            if (input.split(" ")[2].equals(finalType)) {
                isFinal = true;
                name = input.split(" ")[3].split("\\(")[0];
                parameters = separateParameters[1].split(",");
                type = separateParameters[2].split(":")[1];
            }
            else {
                isFinal = false;
                name = input.split(" ")[2].split("\\(")[0];
                parameters = separateParameters[1].split(",");
                type = separateParameters[2].split(":")[1];
            }
        }
        else {
            visibility = defaultVisibility;
            if (input.split(" ")[1].equals(finalType)) {
                isFinal = true;
                name = input.split(" ")[2].split("\\(")[0];
                parameters = separateParameters[1].split(",");
                type = separateParameters[2].split(":")[1];
            }
            else {
                isFinal = false;
                name = input.split(" ")[1].split("\\(")[0];
                parameters = separateParameters[1].split(",");
                type = separateParameters[2].split(":")[1];
            }
        }

        //Add method or print error
        if (constructExists(construct) && Support.isLowerCamelCase(name) && Support.correctParameters(parameters)
                && Support.isAttribute(type) && !methodExists(construct, name, parameters)) {
            if (Support.isVisibility(visibility) && isFinal && input.split(" ").length == 4
                && !constructsArray.get(constructIndex(construct)).getConstructType().equals("interface")) {
                constructsArray.get(constructIndex(construct)).addMethod(visibility, isFinal, name, parameters,
                        type, construct);
                returns = "OK";
            }
            else if ((Support.isVisibility(visibility) && !isFinal && input.split(" ").length == 3)
                || (visibility.equals(defaultVisibility) && isFinal && input.split(" ").length == 3
                && !constructsArray.get(constructIndex(construct)).getConstructType().equals("interface"))) {
                constructsArray.get(constructIndex(construct)).addMethod(visibility, isFinal, name, parameters,
                        type, construct);
                returns = "OK";
            }
            else if (visibility.equals(defaultVisibility) && !isFinal && input.split(" ").length == 2) {
                constructsArray.get(constructIndex(construct)).addMethod(visibility, isFinal, name, parameters,
                        type, construct);
                returns = "OK";
            }
            else if (constructsArray.get(constructIndex(construct)).getConstructType().equals("interface")
                    && isFinal) {
                returns = "Error, methods from interfaces can't be final!";
            }
        }
        else if (!constructExists(construct)) 
            returns = "Error, the construct doesn't exist!";
        else if (methodExists(construct, name, parameters))
            returns = "Error, method already exists!";
        else if (!Support.isLowerCamelCase(name)) 
            returns = "Error, name convention is wrong!";
        else if (!Support.isVisibility(visibility))
            returns = "Error, incorrect visibility!";
        else if (!Support.isAttribute(type))
            returns = "Error, incorrect type of method!";
        else if (constructsArray.get(constructIndex(construct)).getConstructType().equals("interface") && isFinal)
            returns = "Error, methods from interfaces can't be final!";
        else
            returns = "Error, incorrect syntax!";
        
        return returns;
    }
    
    /**
     * Lists all existing constructs.
     * @return All constructors line by line.
     */
    public String listConstructs() {
        String returns = "";
        Collections.sort(constructsArray);
        
        for (int i = 0; i < constructsArray.size(); i++) {
            returns += constructsArray.get(i).toString();
            if (i + 1 < constructsArray.size()) {
                returns += "\n";
            }
        }
        return returns;
    }
    
    /**
     * Returns all attributes from a given construct.
     * @param construct The construct whose attributes will be returned.
     * @return The attributes from the construct.
     */
    public String listAttributes(String construct) {
        String returns = "";
        int index = constructIndex(construct);
        Collections.sort(constructsArray.get(index).getAttributes());
        
        if (constructExists(construct) && constructsArray.get(index).getAttributes().size() > 0) {
            for (int i = 0; i < constructsArray.get(index).getAttributes().size(); i++) {
                String isFinal = "";
                if (constructsArray.get(index).getAttributes().get(i).getIsFinal()) {
                    isFinal = finalType;
                }
                
                //List attributes
                if (i + 1 < constructsArray.get(index).getAttributes().size()
                        && isFinal.equals(finalType)) {
                    
                    returns += construct + ":: " + constructsArray.get(index).getAttributes().get(i).getVisibility()
                            + " " + isFinal + " " + constructsArray.get(index).getAttributes().get(i).getType()
                            + " " + constructsArray.get(index).getAttributes().get(i).getName() + "\n";
                }
                else if (i + 1 < constructsArray.get(index).getAttributes().size() && !isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getAttributes().get(i).getVisibility()
                            + " " + constructsArray.get(index).getAttributes().get(i).getType()
                            + " " + constructsArray.get(index).getAttributes().get(i).getName() + "\n";
                }
                else if (!isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getAttributes().get(i).getVisibility()
                            + " " + constructsArray.get(index).getAttributes().get(i).getType()
                            + " " + constructsArray.get(index).getAttributes().get(i).getName();
                }
                else {
                    returns += construct + ":: " + constructsArray.get(index).getAttributes().get(i).getVisibility()
                            + " " + isFinal + " " + constructsArray.get(index).getAttributes().get(i).getType()
                            + " " + constructsArray.get(index).getAttributes().get(i).getName();
                }
            }
        }
        else if (!constructExists(construct)) {
            returns = "Error, construct doesn't exist!";
        }
        else if (constructsArray.get(index).getAttributes().size() == 0) {
            returns = "Error, there are no attributes yet!";
        }
        else {
            returns = "Error, incorrect syntax";
        }
        return returns;
    }
    
    /**
     * Returns all methods from a given construct.
     * @param construct The construct whose methods will be returned.
     * @return The methods from the construct.
     */
    public String listMethods(String construct) {
        String returns = "";
        int index = constructIndex(construct);
        Collections.sort(constructsArray.get(index).getMethods());
        
        if (constructExists(construct) && constructsArray.get(index).getMethods().size() > 0) {
            for (int i = 0; i < constructsArray.get(index).getMethods().size(); i++) {
                String isFinal = "";
                if (constructsArray.get(index).getMethods().get(i).getIsFinal()) {
                    isFinal = finalType;
                }
                
                //List methods
                if (i + 1 < constructsArray.get(index).getMethods().size()  && isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + isFinal + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType() + "\n";
                }
                else if (i + 1 < constructsArray.get(index).getMethods().size() && !isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType() + "\n";
                }
                else if (!isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType();
                }
                else {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + isFinal + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType();
                }
            }
        }
        else if (!constructExists(construct)) {
            returns = "Error, construct doesn't exist!";
        }
        else if (constructsArray.get(index).getMethods().size() == 0) {
            returns = "Error, there are no methods yet!";
        }
        else {
            returns = "Error, incorrect syntax";
        }
        return returns;
    }
    
    /**
     * Finds all methods with same name in a given construct.
     * @param input The needed information for the construct. Contains
     * the name of the construct to analyze and the name of the methods
     * to search for.
     * @return All methods with the same name in the construct.
     */
    public String findMethodByName(String input) {
        String construct = input.split("::")[0];
        String method = input.split("::")[1];
        String returns = "";
        String isFinal = "";
        int index = constructIndex(construct);
        Collections.sort(constructsArray.get(index).getMethods());
        
        if (constructExists(construct) && constructsArray.get(index).getMethods().size() > 0
                && methodNameExists(construct, method)) {
            ArrayList<Integer> matches = findMethods(construct, method);
             
            for (int i = 0; i < matches.size(); i++) {
                if (constructsArray.get(index).getMethods().get(i).getIsFinal()) {
                    isFinal = finalType;
                }
                
                //List methods
                if (i + 1 < matches.size() && isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + isFinal + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType() + "\n";
                }
                else if (i + 1 < matches.size() && !isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType() + "\n";
                }
                else if (!isFinal.equals(finalType)) {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType();
                }
                else {
                    returns += construct + ":: " + constructsArray.get(index).getMethods().get(i).getVisibility()
                            + " " + isFinal + " " + constructsArray.get(index).getMethods().get(i).getName()
                            + "(" + constructsArray.get(index).getMethods().get(i).listParameters() + ")" + ":"
                            + constructsArray.get(index).getMethods().get(i).getType();
                }
            }
        }
        else if (!constructExists(construct)) {
            returns = "Error, construct doesn't exist!";
        }
        else if (constructsArray.get(index).getMethods().size() == 0) {
            returns = "Error, there are no methods yet!";
        }
        else if (!methodNameExists(construct, method)) {
            returns = "Error, this method doesn't exist!";
        }
        else {
            returns = "Error, incorrect syntax!";
        }
        return returns;
    }
    
    /**
     * Lists all attributes that are directly available or available trough inheritance.
     * @param construct The construct whose available attributes will be returned.
     * @return All available Attributes from the construct.
     */
    public String listAllAttributes(String construct) {
        String returns = "";
        String pointer = "";
        int index = constructIndex(construct);
        
        //directly available attributes
        returns += listAttributes(construct);
        
        //inheritance attributes
        while (constructsArray.get(index).getFather() != null) {
            pointer = constructsArray.get(index).getFather();
            index = constructIndex(pointer);
            
            if (constructsArray.get(index).getAttributes().size() > 0) {
                returns += "\n";
                returns += listAttributes(pointer);
            } 
        }
        return returns;
    }
    
    /**
     * Lists all shadowed attributes from a given construct.
     * @param construct The construct whose shadowed attributes will be returned.
     * @return All shadowed attributes in the construct.
     */
    public String listShadowingAttributes(String construct) {
        String returns = "";
        String pointer = "";
        ArrayList<Attribute> tempArray = new ArrayList<Attribute>();
        int index = constructIndex(construct);
        
        while (constructsArray.get(index).getFather() != null) {
            pointer = constructsArray.get(index).getFather();
            index = constructIndex(pointer);
            for (int i = 0; i < constructsArray.get(index).getAttributes().size(); i++) {
                String temp = constructsArray.get(index).getAttributes().get(i).getName();
                
                if (constructsArray.get(index).containsAttribute(temp)) {
                    returns += "\n";
                    returns += printAttribute(pointer, index);
                }
            }
        }
        
        
        return returns;
    }
    
    /**
     * Lists all methods that are directly available or available trough inheritance.
     * @param construct The construct whose available methods will be returned.
     * @return All available methods in the construct.
     */
    public String listAllMethods(String construct) {
        String returns = "";
        String pointer = "";
        int index = constructIndex(construct);
        
        //directly available methods
        returns += listMethods(construct);
        
        //inheritance methods
        while (constructsArray.get(index).getFather() != null) {
            pointer = constructsArray.get(index).getFather();
            index = constructIndex(pointer);
            
            if (constructsArray.get(index).getAttributes().size() > 0) {
                returns += "\n";
                returns += listAttributes(pointer);
            }
        }
        return returns;
    }
    
    /**
     * Returns entire @Override-history of a given method.
     * @param input The necessary infomation about the method to search and it's construct
     * @return Entire @Override-history of a given method.
     */
    public String findMethodOverride(String input) {
        //set up parameters
        String returns = "";
        String[] separateParameters = input.split("[\\(||//)]");
        String[] parameters = separateParameters[1].split(", ");
        String construct = input.split("::")[0];
        String name = input.split("::")[1].split("\\(")[0];
        String type = separateParameters[2].split(":")[1];
        
        String pointer = "";
        int index = constructIndex(construct);
        if (constructExists(construct) && Support.isLowerCamelCase(name) && Support.correctParameters(parameters)
                && Support.isAttribute(type) && methodExists(construct, name, parameters)) {
            returns += printMethod(construct, methodIndex(construct, name, parameters));
            while (constructsArray.get(index).getFather() != null) {
                pointer = constructsArray.get(index).getFather();
                index = constructIndex(pointer);
                
                if (methodExists(pointer, name, parameters)) {
                    returns += "\n";
                    returns += printMethod(pointer, methodIndex(pointer, name, parameters));
                }
            }
        }
        return returns;
    }
    
    /**
     * Changes the value of the quit attribute.
     * @param quit The new value for quit.
     */
    public void setQuit(boolean quit) {
        this.quit = quit;
    }
    
    /**
     * Getter for quit.
     * @return Value of quit.
     */
    public boolean getQuit() {
        return this.quit;
    }
    
    //Auxiliary methods:
    
    /**
     * Checks if a class already exists.
     * @param name Name of the class to search for.
     * @return True if the class already exists.
     */
    public boolean classExists(String name) {
        boolean flag = false;
        for (Construct construct : constructsArray) {
            if (construct.getName().equals(name) && construct.getConstructType().equals("class")) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Checks if an interface already exists.
     * @param name Name of the interface to search for.
     * @return True if the interface already exists.
     */
    public boolean interfaceExists(String name) {
        boolean flag = false;
        for (Construct construct : constructsArray) {
            if (construct.getName().equals(name) && construct.getConstructType().equals("interface")) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Checks if a construct already exists.
     * @param name Name of the construct to search for.
     * @return True if the construct already exists.
     */
    public boolean constructExists(String name) {
        boolean flag = false;
        for (Construct c : constructsArray) {
            if (c.getName().equals(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Returns the index of a given construct.
     * @param name Name of the construct whose index will be returned.
     * @return Index of the construct name.
     */
    public int constructIndex(String name) {
        int index = 0;
        for (int i = 0; i < constructsArray.size(); i++) {
            if (constructsArray.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    /**
     * Checks if an attribute already exists.
     * @param construct The construct to analyze in order to check if the attribute exists.
     * @param name Name of the attribute to search for.
     * @return True if the construct contains the attribute.
     */
    public boolean attributeExists(String construct, String name) {
        boolean flag = false;
        int index = constructIndex(construct);
        for (Attribute a : constructsArray.get(index).getAttributes()) {
            if (a.getName().equals(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Checks if a method already exists.
     * @param construct The construct to analyze in order to check if the method exists.
     * @param name Name of the method to search for.
     * @param parameters Parameters of the method
     * @return True if the construct contains the method.
     */
    public boolean methodExists(String construct, String name, String[] parameters) {
        boolean flag = false;
        int index = constructIndex(construct);
        
        for (int i = 0; i < constructsArray.get(index).getMethods().size(); i++) {
            if (constructsArray.get(constructIndex(construct)).getMethod(i).getName().equals(name)
                    && constructsArray.get(index).getMethod(i).getParameters().length == parameters.length) {
                
                for (int j = 0; j < constructsArray.get(index).getMethod(i).getParameters().length; j++) {
                    if (!constructsArray.get(index).getMethod(i).getParameter(j).equals(parameters[j])) {
                        flag = false;
                        break;
                    }
                    else {
                        flag = true;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
        return flag;
    }
    
    /**
     * Returns the index of a given method in a construct.
     * @param construct The construct to analyze in order to get the index of the method.
     * @param name Name of the method to search for.
     * @param parameters Parameters of the method
     * @return Index of the method in the construct.
     */
    public int methodIndex(String construct, String name, String[] parameters) {
        boolean flag = false;
        int index = constructIndex(construct);
        int myIndex = 0;
        
        for (int i = 0; i < constructsArray.get(index).getMethods().size(); i++) {
            if (constructsArray.get(constructIndex(construct)).getMethod(i).getName().equals(name)
                    && constructsArray.get(index).getMethod(i).getParameters().length == parameters.length) {
                
                for (int j = 0; j < constructsArray.get(index).getMethod(i).getParameters().length; j++) {
                    if (!constructsArray.get(index).getMethod(i).getParameter(j).equals(parameters[j])) {
                        flag = false;
                        break;
                    }
                    else {
                        flag = true;
                        myIndex = i;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
        return myIndex;
    }
    
    /**
     * Checks if a method with a given name exists at least once in the construct.
     * @param construct The construct where the name will be searched.
     * @param name The searched name of the method.
     * @return True if at least one method with name exists.
     */
    public boolean methodNameExists(String construct, String name) {
        boolean flag = false;
        int index = constructIndex(construct);
        
        for (int i = 0; i < constructsArray.get(index).getMethods().size(); i++) {
            if (constructsArray.get(index).getMethod(i).getName().equals(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Searches for all methods with the same given name and returns their indices.
     * @param construct The construct where the name will be searched.
     * @param methodName The searched name of the method.
     * @return ArrayList of Integers with the matching indices.
     */
    public ArrayList<Integer> findMethods(String construct, String methodName) {
        ArrayList<Integer> matches = new ArrayList<Integer>();
        int index = constructIndex(construct);
        
        for (int i = 0; i < constructsArray.get(index).getMethods().size(); i++) {
            if (constructsArray.get(index).getMethod(i).getName().equals(methodName)) {
                matches.add(i);
            }
        }
        return matches;
    }
    
    /**
     * Analyzes if a given interface can be implemented with a given class.
     * @param aClass Class to be analyzed.
     * @param anInterface Interface to be analyzed.
     * @return True if the class contains all methods from the interface.
     */
    public boolean canImplement(String aClass, String anInterface) {
        boolean flag = true;
        Construct myClass = constructsArray.get(constructIndex(aClass));
        Construct myInterface = constructsArray.get(constructIndex(anInterface));
        for (int i = 0; i < myClass.getMethods().size(); i++) {
            for (int j = 0; j < myInterface.getMethods().size(); j++) {
                if (!myClass.getMethod(i).getName().equals(myInterface.getMethod(j).getName())
                        || !myClass.getMethod(i).getIsFinal() == myInterface.getMethod(j).getIsFinal()
                        || !myClass.getMethod(i).getType().equals(myInterface.getMethod(j).getType())
                        || !myClass.getMethod(i).getVisibility().equals(myInterface.getMethod(j).getVisibility())
                        || !Support.sameParameters(myClass.getMethod(i).getParameters(),
                                myInterface.getMethod(j).getParameters())) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
    
    /**
     * Returns a given method.
     * @param construct Construct that contains the method.
     * @param index Index of the method in the construct.
     * @return Searched method.
     */
    public String printMethod(String construct, int index) {
        String returns = "";
        String isFinal = "";
        int thisIndex = constructIndex(construct);
        if (constructsArray.get(thisIndex).getMethods().get(index).getIsFinal()) {
            isFinal = finalType;
        }
        
        if (isFinal.equals(finalType)) {
            returns = construct + ":: " + constructsArray.get(thisIndex).getMethods().get(index).getVisibility()
                    + " " + isFinal + " " + constructsArray.get(thisIndex).getMethods().get(index).getName()
                    + "(" + constructsArray.get(thisIndex).getMethods().get(index).listParameters() + ")" + ":"
                    + constructsArray.get(thisIndex).getMethods().get(index).getType();
        }
        else {
            returns = construct + ":: " + constructsArray.get(thisIndex).getMethods().get(index).getVisibility()
                    + " " + constructsArray.get(thisIndex).getMethods().get(index).getName()
                    + "(" + constructsArray.get(thisIndex).getMethods().get(index).listParameters() + ")" + ":"
                    + constructsArray.get(thisIndex).getMethods().get(index).getType();
        }
        return returns;
    }
    
    /**
     * Returns a given attribute.
     * @param construct Construct that contains the attribute.
     * @param index Index of the attribute in the construct.
     * @return Searched attribute.
     */
    public String printAttribute(String construct, int index) {
        String returns = "";
        String isFinal = "";
        int thisIndex = constructIndex(construct);
        if (constructsArray.get(thisIndex).getAttributes().get(index).getIsFinal()) {
            isFinal = finalType;
        }
        
        if (isFinal.equals(finalType)) {
            returns = construct + ":: " + constructsArray.get(thisIndex).getAttributes().get(index).getVisibility()
                    + " " + isFinal + " " + constructsArray.get(thisIndex).getAttributes().get(index).getType()
                    + " " + constructsArray.get(thisIndex).getAttributes().get(index).getName();
        }
        else {
            returns = construct + ":: " + constructsArray.get(thisIndex).getAttributes().get(index).getVisibility()
                    + " " + constructsArray.get(thisIndex).getAttributes().get(index).getType()
                    + " " + constructsArray.get(thisIndex).getAttributes().get(index).getName();
        }
        return returns;
    }
}