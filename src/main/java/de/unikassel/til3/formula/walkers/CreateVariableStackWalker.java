package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.Quantifier;
import de.unikassel.til3.formula.Walker;

import java.util.Stack;

/**
 * Created by tux on 10.02.14.
 */
public class CreateVariableStackWalker implements Walker<Formula> {
    private Stack<String> variables = new Stack<String>();

    @Override
    public Formula handle(Formula on) {
        if (on instanceof Quantifier) {
            variables.add(((Quantifier) on).getVariable());
        }
        return null;
    }

    public Stack<String> getVariables() {
        return variables;
    }
}
