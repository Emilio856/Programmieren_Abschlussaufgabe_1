package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for an Attribute.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Attribute implements Comparable<Attribute> {
    
    /**
     * Types of visibility.
     */
    public static final String[] VISIBILITY = {"public", "private", "protected"};
    
    /**
     * Types of attributes.
     */
    private static ArrayList<String> attributes = new ArrayList<>(Arrays.asList("boolean", "byte", "short", "int",
            "long", "float", "double", "char", "String"));
    
    /**
     * Name of the attribute.
     */
    private String name;
    
    /**
     * Type of the attribute.
     */
    private String type;
    
    /**
     * Decides if attribute is of type final.
     */
    private boolean isFinal;
    
    /**
     * Visibility of the attribute.
     */
    private String visibility;
    
    /**
     * Construct of the attribute.
     */
    private String construct;
    
    /**
     * Constructor for Object of type Attribute.
     * @param visibility Visibility of the attribute.
     * @param isFinal Decides if attribute is of type final.
     * @param type Type of the attribute.
     * @param name Name of the attribute.
     * @param construct Construct of the attribute.
     */
    public Attribute(String visibility, boolean isFinal, String type, String name, String construct) {
        this.visibility = visibility;
        this.isFinal = isFinal;
        this.type = type;
        this.name = name;
        this.construct = construct;
    }
    
    /**
     * Adds new typ for an attribute.
     * @param str Type of attribute to add.
     */
    public static void addNewAttributeType(String str) {
        attributes.add(str);
    }
    
    /**
     * Getter for name.
     * @return Name of the attribute.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Getter for types of attributes.
     * @return Types of attributes
     */
    public static ArrayList<String> getAttributeTypes() {
        return attributes;
    }
    
    /**
     * Getter for visibility.
     * @return Visibility of the attribute.
     */
    public String getVisibility() {
        return this.visibility;
    }
    
    /**
     * Getter for isFinal
     * @return isFinal value of the attribute.
     */
    public boolean getIsFinal() {
        return this.isFinal;
    }
    
    /**
     * Getter for type.
     * @return Type of the attribute.
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Getter for construct.
     * @return Construct of the attribute.
     */
    public String getConstruct() {
        return this.construct;
    }

    /**
     * Overrides compareTo() to compare lexicographically based on <AttributeSignature>.
     * Tutor: Ich weiß, dass diese Verschachtelung (4 ifs) vollkommen unelegant ist und man
     * sollte sowas vermeiden (ich habe deine Anmerkung der letzten Übung gelesen). Allerdings
     * habe ich in der Aufgabenstellung nicht verstanden wie genau man nach AttributeSignature
     * sortiert. Deshalb habe ich beschlossen einfach alle Bestandteile der Syntax zu vergleichen.
     * War das so gefragt? Wenn ja, wie hätte man die Verschachtelung vermeiden können?
     */
    @Override
    public int compareTo(Attribute a) {
        Attribute some = ((Attribute) a);
        if (this.construct.compareTo(a.construct) == 0) {
            if (this.visibility.compareTo(a.visibility) == 0) {
                if (this.type.compareTo(a.type) == 0) {
                    if (this.name.compareTo(a.name) == 0) {
                        return 0;
                    }
                    else if (this.name.compareTo(a.name) == 1) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
                else if (this.type.compareTo(a.type) == 1) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else if (this.visibility.compareTo(a.visibility) == 1) {
                return 1;
            }
            else {
                return -1;
            }
        }
        else if (this.construct.compareTo(a.construct) == 1) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
