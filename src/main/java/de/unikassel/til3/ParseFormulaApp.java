package de.unikassel.til3;

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.FunctionSymbol;
import de.unikassel.til3.formula.clause.ClauseSet;
import de.unikassel.til3.formula.clause.Model;
import de.unikassel.til3.formula.walkers.*;
import de.unikassel.til3.scanner.Scanner;
import de.unikassel.til3.scanner.parser;
import de.unikassel.til3.term.Signature;
import de.unikassel.til3.term.Term;
import de.unikassel.til3.term.TermEnumerator;
import org.sat4j.specs.TimeoutException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Stack;

public class ParseFormulaApp {

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Formula f = null;
                try {
                    parser p = new parser(new Scanner(new StringReader(line)));
                    f = (Formula) p.parse().value;
                } catch (Exception e) {
                    System.out.println("Invalid formula");
                    continue;
                }
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
                System.out.println("Skolem normal form:      " + f.toString());

                f = Formula.walkDown(f, new PushDisjunctionsDownWalker());
                System.out.println("Conjunctive normal form: " + f.toString());

                CreateClauseSetWalker clauseSetCreator = new CreateClauseSetWalker();
                Formula.walkDown(f, clauseSetCreator);
                System.out.println("Clause set:              " + clauseSetCreator.getClauseSet());

                CreateVariableStackWalker createVariableStackWalker = new CreateVariableStackWalker();
                Formula.walkDown(f, createVariableStackWalker);
                Stack<String> variables = createVariableStackWalker.getVariables();

                CreateSignatureWalker createSignatureWalker = new CreateSignatureWalker(variables);
                Formula.walkDown(f, createSignatureWalker);
                Signature s = createSignatureWalker.getSignature();
                System.out.println("Signature:               " + s);

                boolean foundConstant = false;
                for (FunctionSymbol symbol : s) {
                    if (symbol.getArity() == 0) {
                        foundConstant = true;
                        break;
                    }
                }
                if (!foundConstant) {
                    s.add(new FunctionSymbol("someconstant", 0));
                }

                Model model = null;
                try {
                    model = solveHerbrand(clauseSetCreator.getClauseSet(), s, variables);
                } catch (TimeoutException e) {
                    System.out.println("timeout");
                } catch (OutOfMemoryError e) {
                    System.out.println("timeout");
                }
                System.out.println("Solution:                " + model);

                if (model != null) {
                    LinkedList<Term> universe = new LinkedList<Term>();
                    TermEnumerator enumerator = new TermEnumerator(s);
                    while (enumerator.hasNext()) {
                        universe.add(enumerator.getNext());
                    }
                    System.out.println("Universe:                " + universe);

                    System.out.print("Variable assignments:    {");
                    for (FunctionSymbol symbol : s) {
                        if (symbol.getArity() == 0) {
                            System.out.print(symbol.getName() + " => \"" + symbol.getName() + "\" ");
                        }
                    }
                    System.out.println("}");
                }
            }


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Model solveHerbrand(ClauseSet set, Signature s, Stack<String> variables) throws TimeoutException {
        Model model = new Model();
        String variable = variables.isEmpty() ? null : variables.pop();
        TermEnumerator enumerator = new TermEnumerator(s);
        while(enumerator.hasNext()) {
            Term term = enumerator.getNext();
            ClauseSet currentSet = set;
            if (variable != null) {
                currentSet = set.replaceBoundVariables(variable, term);
            }
            if (variables.isEmpty()) {
                Model current = currentSet.solve();
                if (current == null) {
                    return null;
                } else {
                    model.applyPositive(current);
                }
            } else {
                Stack<String> copiedStack = new Stack<String>();
                copiedStack.addAll(variables);
                Model current = solveHerbrand(currentSet, s, copiedStack);
                if (current == null) {
                    return null;
                } else {
                    model.applyPositive(current);
                }
            }
            System.gc();
        }
        return model;
    }
}
