package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.Conjunction;
import de.unikassel.til3.formula.Disjunction;
import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.Walker;

/**
 * Created by tux on 10.02.14.
 */
public class PushDisjunctionsDownWalker implements Walker<Formula> {
    @Override
    public Formula handle(Formula on) {
        if (on instanceof Disjunction) {
            Formula a = null,b = null,c = null;
            if (((Disjunction) on).getLeft_arg() instanceof Conjunction) {
                a = ((Disjunction) on).getRight_arg();
                b = ((Conjunction) ((Disjunction) on).getLeft_arg()).getLeft_arg();
                c = ((Conjunction) ((Disjunction) on).getLeft_arg()).getRight_arg();
            }
            if (((Disjunction) on).getRight_arg() instanceof Conjunction) {
                a = ((Disjunction) on).getLeft_arg();
                b = ((Conjunction) ((Disjunction) on).getRight_arg()).getLeft_arg();
                c = ((Conjunction) ((Disjunction) on).getRight_arg()).getRight_arg();
            }
            if (a != null && b != null && c != null) {
                return new Conjunction(new Disjunction(a, b), new Disjunction(a, c));
            }
        }
        return null;
    }
}
