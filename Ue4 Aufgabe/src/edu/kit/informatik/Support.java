package edu.kit.informatik;

/**
 * Utility class for the class editor.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public final class Support {
    
    /**
     * Private constructor.
     */
    private Support() { 
    }
    
    /**
     * Checks if a String is lowerCamelCase.
     * @param str String to analyze.
     * @return True if str is lowerCamelCase.
     */
    public static boolean isLowerCamelCase(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks if a String is upperCamelCase.
     * @param str String to analyze.
     * @return True if str is upperCamelCase.
     */
    public static boolean isUpperCamelCase(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks if parameters are correct.
     * @param parameters Parameters to analyze.
     * @return True if parameters are correct.
     */
    public static boolean correctParameters(String[] parameters) {
        boolean flag = false;
        
        for (String s : parameters) {
            flag = isAttribute(s);
            if (flag) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if a string is equal to a type of construct.
     * @param str The string to analyze.
     * @return True if str matches a construct.
     */
    public static boolean isConstruct(String str) {
        boolean flag = false;
        for (String c : Construct.CONSTRUCTS) {
            if (c.equals(str)) {
                flag = true;
            }
        }
        return flag;
    }
    
    /**
     * Checks if a string is equal to a type of visibility.
     * @param str The string to analyze.
     * @return True if str matches a visibility.
     */
    public static boolean isVisibility(String str) {
        boolean flag = false;
        for (String v : Attribute.VISIBILITY) {
            if (v.equals(str)) {
                flag = true;
            }
        }
        return flag;
    }
    
    /**
     * Checks if a string is valid type of attributes.
     * @param str The string to analyze.
     * @return True if str matches type of attributes.
     */
    public static boolean isAttribute(String str) {
        boolean flag = false;
        for (String a : Attribute.getAttributeTypes()) {
            if (a.equals(str)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Compares two arrays of parameters and checks if they are equal.
     * @param p1 Array 1 of parameters.
     * @param p2 Array 2 of parameters.
     * @return True if both arrays are equal.
     */
    public static boolean sameParameters(String[] p1, String[] p2) {
        boolean flag = false;
        if (p1.length == p2.length) {
            for (int i = 0; i < p1.length; i++) {
                if (p1[i].equals(p2[i])) {
                    continue;
                }
                else {
                    flag = false;
                    break;
                }
            }
            flag = true;
        }
        else {
            flag = false;
        }
        return flag;
    }
}