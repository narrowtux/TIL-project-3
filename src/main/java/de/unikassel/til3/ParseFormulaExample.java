package de.unikassel.til3;/*
import parser;
import scanner;
import formula;
*/

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.clause.ClauseSet;
import de.unikassel.til3.formula.walkers.*;
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
                System.out.println("Input:                   " + f.toString());
                if (!f.isInPositiveNormalForm()) {
                    f = Formula.walkDown(f, new ReplaceImplicationsWalker());
                    f = Formula.walkDown(f, new PushNegationDownWalker());
                }
                System.out.println("Positive normal form:    " + f.toString());
                while (!f.isInPrenexNormalForm()) {
                    f = Formula.walkDown(f, new PushQuantifiersUpWalker());
                }
                System.out.println("Prenex normal form:      " + f.toString());
                if (!f.isInSkolemNormalForm()) {
                    f = Formula.walkDown(f, new EliminateExistsQuantifierWalker());
                }
                f = Formula.walkDown(f, new PushDisjunctionsDownWalker());

                CreateClauseSetWalker clauseSetCreator = new CreateClauseSetWalker();
                Formula.walkDown(f, clauseSetCreator);
                System.out.println("Skolem normal form:      " + f.toString());
                System.out.println("Clause set:              " + clauseSetCreator.getClauseSet());
            }


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
