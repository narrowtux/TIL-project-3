package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.*;
import de.unikassel.til3.term.Term;

import javax.management.relation.Relation;

/**
 * Created by tux on 07.02.14.
 */
public class ReplaceVariablesWalker implements Formula.FormulaDownWalker {
    private String from, to;
    private ReplaceVariablesInTermsWalker termsWalker = new ReplaceVariablesInTermsWalker();
    private static int variableCounter = 0;

    public ReplaceVariablesWalker(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Formula handle(Formula f) {
        if (f instanceof ExistsQuantifier) {
            ExistsQuantifier eq = (ExistsQuantifier) f;
            if (eq.getVariable().equals(from)) {
                return new ExistsQuantifier(to, eq.getArg());
            }
        }
        if (f instanceof ForallQuantifier) {
            ForallQuantifier fa = (ForallQuantifier) f;
            if (fa.getVariable().equals(from)) {
                return new ForallQuantifier(to, fa.getArg());
            }
        }
        if (f instanceof RelationFormula) {
            RelationFormula rf = (RelationFormula) f;

            for (int i = 0; i < rf.getTerms().size(); i++) {
                Term t = rf.getTerms().elementAt(i);
                rf.getTerms().set(i, Term.walkDown(t, termsWalker));
            }

            // no return since this leaf doesn't need a new instance
        }
        return null;
    }

    private class ReplaceVariablesInTermsWalker implements Walker<Term> {
        @Override
        public Term handle(Term on) {
            if (on.getTopSymbol().getName().equals(from)) {
                return new Term(new FunctionSymbol(to, on.getTopSymbol().getArity()), on.getSubterms());
            }
            return null;
        }
    }

    public static String getNextFreeVariable() {
        return "rv" + (++variableCounter);
    }
}
