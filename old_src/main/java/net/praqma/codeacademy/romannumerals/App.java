package net.praqma.codeacademy.romannumerals;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    public static String convert (int number){
    	String out;
    	if(number==1)
    		out="I";
    	else
    		out=""+number;
		return out;
    }
}
