package ppp;

import java.util.Arrays;

public class lam {

    public static void main(String[] args) {
        String separator = ",";
        Arrays.asList( "a", "b", "d" ).forEach(  ( String e ) -> System.out.print( e + separator ) );
    }
}
