package edu.kit.informatik;

public class TypeConversion {
    
    public static void main(String[] args) {

        int i = 42;

        //Casting Conversion and Narrowing Conversion from int to byte
        byte b = (byte) (0xFF & (i << 24));

        //Widening Conversion for 'a', 'a' gets its ASCII value from char to int
        //Widening Conversion is done automatically
        int j = 'a' + 42;

        //Automatic String Conversion from char to String
        System.out.println("Some letters are ... " + 'b' + 'c');

        short s = 42;

        //Casting Conversion and Widening Conversion from short to char
        //Widening Conversion is done automatically --> Casting isn't necessary here
        char c = (char) s;

        //If I don't recall wrong all numeric literals are Integers by default. In that case
        //this should be an automatically done Widening Conversion from int to long
        long l = 99999999999999L;

        //Widening Conversion from float to double
        double d = 19.0f;

        //Automatic Widening Conversion from long to double because of the arithmetic operation
        d += l;

        //Casting Conversion to long for the Widening Conversion from int to double.
        //Automatic Widening Conversion from short to int.
        //Automatic Widening Conversion from int to double because its a mixed arithmetic operation
        long l2 = (long) (i * 2.0d) + (j + s);

        //Widening Conversion from long to  float
        //Casting and Widening Conversion from double to float
        float[] Array = { 0xFF, l2, (float) d };

        //Cast and String Conversion from Array elements to String
        //String Conversion from Array
        String o = (String) Array.toString();

        //String Conversion from float to String
        System.out.println(Array[0]);
    }
}
