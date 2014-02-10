package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.*;
import de.unikassel.til3.term.Term;

import javax.management.relation.Relation;
import java.util.Vector;

/**
 * Created by tux on 07.02.14.
 */
public class ReplaceVariablesWalker implements Walker<Formula> {
    private String from;
    private Term to;
    private ReplaceVariablesInTermsWalker termsWalker = new ReplaceVariablesInTermsWalker();
    private static int variableCounter = 0;

    public ReplaceVariablesWalker(String from, String to) {
        this.from = from;
        this.to = new Term(new FunctionSymbol(to, 0), new Vector<Term>());
    }

    public ReplaceVariablesWalker(String from, Term to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Formula handle(Formula f) {
        if (f instanceof ExistsQuantifier) {
            ExistsQuantifier eq = (ExistsQuantifier) f;
            if (eq.getVariable().equals(from)) {
                return new ExistsQuantifier(to.getTopSymbol().getName(), eq.getArg());
            }
        }
        if (f instanceof ForallQuantifier) {
            ForallQuantifier fa = (ForallQuantifier) f;
            if (fa.getVariable().equals(from)) {
                return new ForallQuantifier(to.getTopSymbol().getName(), fa.getArg());
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
                return to;
            }
            return null;
        }
    }

    public static String getNextFreeVariable() {
        return "rv" + (++variableCounter);
    }
}
