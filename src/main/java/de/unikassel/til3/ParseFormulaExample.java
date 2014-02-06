package de.unikassel.til3;/*
import parser;
import scanner;
import formula;
*/

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.scanner.Scanner;
import de.unikassel.til3.scanner.parser;

import java.io.StringReader;

public class ParseFormulaExample {

    public static void main(String[] args) {

        try {
            // parser p = new parser(new Scanner(new InputStreamReader(System.in)));
            parser p = new parser(new Scanner(new StringReader("FORALL x EXISTS y A(x, y) & EXISTS z B(x,y,z)")));

            Formula f = (Formula) p.parse().value;
            System.out.println("Input:                   " + f.toString());
            System.out.println("In positive normal form: " + f.isInPositiveNormalForm());
            System.out.println("In prenex normal form:   " + f.isInPrenexNormalForm());
            System.out.println("In skolem normal form:   " + f.isInSkolemNormalForm());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
