package de.unikassel.til3;/*
import parser;
import scanner;
import formula;
*/

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.walkers.EliminateExistsQuantifierWalker;
import de.unikassel.til3.formula.walkers.PushNegationDownWalker;
import de.unikassel.til3.formula.walkers.PushQuantifiersUpWalker;
import de.unikassel.til3.formula.walkers.ReplaceImplicationsWalker;
import de.unikassel.til3.scanner.Scanner;
import de.unikassel.til3.scanner.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

public class ParseFormulaExample {

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                parser p = new parser(new Scanner(new StringReader(line)));

                Formula f = (Formula) p.parse().value;
                if (!f.isInPositiveNormalForm()) {
                    f = Formula.walkDown(f, new ReplaceImplicationsWalker());
                    f = Formula.walkDown(f, new PushNegationDownWalker());
                }
                while (!f.isInPrenexNormalForm()) {
                    f = Formula.walkDown(f, new PushQuantifiersUpWalker());
                }
                if (!f.isInSkolemNormalForm()) {
                    f = Formula.walkDown(f, new EliminateExistsQuantifierWalker());
                }
                System.out.println("Input:                   " + f.toString());
                System.out.println("In positive normal form: " + f.isInPositiveNormalForm());
                System.out.println("In prenex normal form:   " + f.isInPrenexNormalForm());
                System.out.println("In skolem normal form:   " + f.isInSkolemNormalForm());
            }


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
