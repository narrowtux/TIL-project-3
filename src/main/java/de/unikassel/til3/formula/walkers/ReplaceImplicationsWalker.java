package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.*;

/**
 * Created by tux on 07.02.14.
 */
public class ReplaceImplicationsWalker implements Walker<Formula> {
    @Override
    public Formula handle(Formula f) {
        if (f instanceof Biimplication) {
            Biimplication fi = (Biimplication) f;
            Formula a = fi.getLeft_arg();
            Formula b = fi.getRight_arg();
            return new Conjunction(new Disjunction(new Negation(a), b), new Disjunction(a, new Negation(b)));
        }
        if (f instanceof Implication) {
            Implication fi = (Implication) f;
            return new Disjunction(new Negation(fi.getLeft_arg()), fi.getRight_arg());
        }
        return null;
    }
}
