package de.unikassel.til3.formula.walkers;

import com.sun.tools.javac.code.Type;
import de.unikassel.til3.formula.ExistsQuantifier;
import de.unikassel.til3.formula.ForallQuantifier;
import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.FunctionSymbol;
import de.unikassel.til3.term.Term;

import java.util.Vector;

public class EliminateExistsQuantifierWalker implements Formula.FormulaDownWalker {
    private String lastForAllVariable = null;

    @Override
    public Formula handle(Formula f) {
        if (f instanceof ForallQuantifier) {
            lastForAllVariable = ((ForallQuantifier) f).getVariable();
        }
        if (f instanceof ExistsQuantifier) {
            if (lastForAllVariable == null) {
                f = Formula.walkDown(f, new ReplaceVariablesWalker(((ExistsQuantifier) f).getVariable(), ReplaceVariablesWalker.getNextFreeVariable()));
                return ((ExistsQuantifier) f).getArg();
            } else {
                Term replace = new Term(new FunctionSymbol(ReplaceVariablesWalker.getNextFreeVariable(), 1), new Vector<Term>());
                replace.getSubterms().add(new Term(new FunctionSymbol(lastForAllVariable, 0), new Vector<Term>()));
                f = Formula.walkDown(f, new ReplaceVariablesWalker(((ExistsQuantifier) f).getVariable(), replace));
                return ((ExistsQuantifier) f).getArg();
            }
        }
        return null;
    }
}
