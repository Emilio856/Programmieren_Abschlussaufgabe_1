package edu.kit.informatik;

/**
 * Class for a Method.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Method implements Comparable<Method> {
    
    /**
     * Name of the method.
     */
    private String name;
    
    /**
     * Visibility of the method.
     */
    private String visibility;
    
    /**
     * Type of the method.
     */
    private String type;
    
    /**
     * Parameters of the method.
     */
    private String[] parameters;
    
    /**
     * Construct of the method.
     */
    private String construct;
    
    /**
     * Decides if method is of type final.
     */
    private boolean isFinal;
    
    /**
     * Constructor for method.
     * @param visibility Visibility of the method.
     * @param isFinal Decides if method is of type final.
     * @param name Name of the method.
     * @param parameters Parameters of the method.
     * @param type Type of the method.
     * @param construct Construct of the method.
     */
    public Method(String visibility, boolean isFinal, String name, String parameters[], String type,
            String construct) {
        this.visibility = visibility;
        this.isFinal = isFinal;
        this.name = name;
        this.type = type;
        this.parameters = parameters;
        this.construct = construct;
    }
    
    /**
     * Lists all parameters from.
     * @return List of parameters.
     */
    public String listParameters() {
        String returns = "";
        for (int i = 0; i < parameters.length; i++) {
            if (i + 1 < parameters.length) {
                returns += parameters[i] + ",";
            }
            else {
                returns += parameters[i];
            }
        }
        return returns;
    }
    
    /**
     * Getter for visibility.
     * @return Visibility of the method.
     */
    public String getVisibility() {
        return this.visibility;
    }
    
    /**
     * Getter for isFinal.
     * @return Value of isFinal.
     */
    public boolean getIsFinal() {
        return isFinal;
    }
    
    /**
     * Getter for name.
     * @return Name of the method.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Getter for type.
     * @return Type of the method.
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Getter for parameter at index i.
     * @param i Index of the parameter.
     * @return Parameter at index i.
     */
    public String getParameter(int i) {
        return this.parameters[i];
    }
    
    /**
     * Getter for parameters.
     * @return Parameters of the method.
     */
    public String[] getParameters() {
        return this.parameters;
    }
    
    /**
     * Overrides compareTo() to compare lexicographically based on <MethodSignature>.
     * Tutor: Ich weiß, dass diese Verschachtelung (4 ifs) vollkommen unelegant ist und man
     * sollte sowas vermeiden (ich habe deine Anmerkung der letzten Übung gelesen). Allerdings
     * habe ich in der Aufgabenstellung nicht verstanden wie genau man nach MethodSignature
     * sortiert. Deshalb habe ich beschlossen einfach alle Bestandteile der Syntax zu vergleichen.
     * War das so gefragt? Wenn ja, wie hätte man die Verschachtelung vermeiden können?
     */
    @Override
    public int compareTo(Method m) {
        Method some = ((Method) m);
        if (this.construct.compareTo(m.construct) == 0) {
            if (this.visibility.compareTo(m.visibility) == 0) {
                if (this.type.compareTo(m.name) == 0) {
                    if (this.name.compareTo(m.type) == 0) {
                        return 0;
                    }
                    else if (this.name.compareTo(m.type) == 1) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
                else if (this.type.compareTo(m.name) == 1) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else if (this.visibility.compareTo(m.visibility) == 1) {
                return 1;
            }
            else {
                return -1;
            }
        }
        else if (this.construct.compareTo(m.construct) == 1) {
            return 1;
        }
        else {
            return -1;
        }
    }
}