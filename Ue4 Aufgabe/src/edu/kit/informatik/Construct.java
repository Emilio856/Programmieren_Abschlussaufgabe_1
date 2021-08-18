package edu.kit.informatik;

import java.util.ArrayList;

/**
 * Class for a Construct.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Construct implements Comparable<Construct> {
    
    /**
     * Types of constructs.
     */
    public static final String[] CONSTRUCTS = {"class", "interface", "enum"};
    
    /**
     * Name of the construct.
     */
    private String name;
    
    /**
     * Attributes of the construct.
     */
    private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
    
    /**
     * Methods of the construct.
     */
    private ArrayList<Method> methods = new ArrayList<Method>();
    
    /**
     * Type of the construct.
     */
    private String constructType;
    
    /**
     * Decides if construct can inherit.
     */
    private boolean canExtend;
    
    /**
     * Parent class of the construct.
     */
    private String father;
    
    /**
     * Decides if construct can implement interfaces.
     */
    private boolean canImplement;
    
    /**
     * Implemented interfaces of the construct.
     */
    private ArrayList<String> implemented = new ArrayList<String>();
    
    /**
     * Constructor for Construct.
     * @param constructType Type of the construct.
     * @param name Name of the construct.
     * @param canExtend Decides if construct can inherit.
     * @param canImplement Decides if construct can implement interfaces.
     */
    public Construct(String constructType, String name, boolean canExtend, boolean canImplement) {
        this.constructType = constructType;
        this.name = name;
        this.canExtend = canExtend;
        this.canImplement = canImplement;
    }
    
    /**
     * Implements interface to construct.
     * @param str Name of interface.
     */
    public void addImplemented(String str) {
        implemented.add(str);
    }
    
    /**
     * Adds attribute to the construct.
     * @param visibility Visibility of the attribute.
     * @param isFinal Decides if attribute is of type final.
     * @param type Type of the attribute.
     * @param name Name of the attribute.
     * @param construct Construct of the attribute.
     */
    public void addAttribute(String visibility, boolean isFinal, String type, String name, String construct) {
        Attribute newAttribute = new Attribute(visibility, isFinal, type, name, construct);
        attributes.add(newAttribute);
    }
    
    /**
     * Adds method to the construct.
     * @param visibility Visibility of the method.
     * @param isFinal Decides if method is of type final.
     * @param name Name of the method.
     * @param parameters Parameters of the method.
     * @param type Type of the method.
     * @param construct Construct of the method.
     */
    public void addMethod(String visibility, boolean isFinal, String name, String[] parameters, String type,
            String construct) {
        Method newMethod = new Method(visibility, isFinal, name, parameters, type, construct);
        methods.add(newMethod);
    }
    
    /**
     * Checks if the construct contains a given attribute,
     * @param attribute Attribute to search.
     * @return True if construct contains attribute.
     */
    public boolean containsAttribute(String attribute) {
        boolean flag = false;
        for (Attribute a : attributes) {
            if (a.getName().equals(attribute)) {
                flag = false;
            }
        }
        return flag;
    }
    
    /**
     * Returns index of given attribute.
     * @param attribute Attribute to search.
     * @return Index of given attribute.
     */
    public int attributeIndex(String attribute) {
        int index = 0;
        
        for (int i = 0; i < attributes.size(); i++) {
            if (attribute.equals(attributes.get(i).getName())) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    /**
     * Getter for attributes.
     * @return Attributes of the construct.
     */
    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }
    
    /**
     * Getter for methods.
     * @return Methods of the construct.
     */
    public ArrayList<Method> getMethods() {
        return this.methods;
    }
    
    /**
     * Getter for a specific method.
     * @param index Index of the method.
     * @return Method with given index.
     */
    public Method getMethod(int index) {
        return this.methods.get(index);
    }
    
    /**
     * Getter for name.
     * @return Name of the construct.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Getter for canExtend.
     * @return Value of canExtend.
     */
    public boolean getCanExtend() {
        return this.canExtend;
    }
    
    /**
     * Getter for constructType.
     * @return Type of the construct.
     */
    public String getConstructType() {
        return this.constructType;
    }
    
    /**
     * Getter for father.
     * @return Father of the construct.
     */
    public String getFather() {
        return this.father;
    }
    
    /**
     * Setter for father
     * @param father Fahther of the construct
     */
    public void setFather(String father) {
        this.father = father;
    }
    
    @Override
    public String toString() {
        String returns = "";
        returns += constructType + " " + name;
        
        if (father != null) {
            returns += " extends " + father; 
        }
        if (implemented.size() > 0) {
            returns += " implements ";
            for (int i = 0; i < implemented.size(); i++) {
                if (i + 1 < implemented.size()) {
                    returns += implemented.get(i) + ",";
                }
                else {
                    returns += implemented.get(i);
                }
            }
        }
        return returns;
    }

    /**
     * Overrides compareTo() to compare lexicographically based
     * on the name of the construct.
     */
    @Override
    public int compareTo(Construct c) {
        String compareName = ((Construct) c).getName();
        return this.name.compareTo(compareName);
    }
}