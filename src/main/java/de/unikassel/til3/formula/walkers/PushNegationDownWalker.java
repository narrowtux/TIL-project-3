package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.*;

/**
 * Created by tux on 07.02.14.
 */
public class PushNegationDownWalker implements Walker<Formula> {
    @Override
    public Formula handle(Formula f) {
        if (f instanceof Negation) {
            Negation fn = (Negation) f;
            Formula arg = fn.getArg();
            if (arg instanceof Negation) {
                Negation fnn = (Negation) arg;
                return fnn.getArg();
            }
            if (arg instanceof Conjunction) {
                Conjunction fc = (Conjunction) arg;
                return new Disjunction(new Negation(fc.getLeft_arg()), new Negation(fc.getRight_arg()));
            }
            if (arg instanceof Disjunction) {
                Disjunction fd = (Disjunction) arg;
                return new Conjunction(new Negation(fd.getLeft_arg()), new Negation(fd.getRight_arg()));
            }
            if (arg instanceof ExistsQuantifier) {
                ExistsQuantifier fe = (ExistsQuantifier) arg;
                return new ForallQuantifier(fe.getVariable(), new Negation(fe.getArg()));
            }
            if (arg instanceof ForallQuantifier) {
                ForallQuantifier fa = (ForallQuantifier) arg;
                return new ExistsQuantifier(fa.getVariable(), new Negation(fa.getArg()));
            }
        }
        return null;
    }
}
