package de.unikassel.til3.formula.clause;

import de.unikassel.til3.formula.Formula;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by tux on 10.02.14.
 */
public class Clause extends HashSet<Formula> {
    @Override
    public boolean add(Formula formula) {
        if (!formula.isLiteral()) {
            throw new IllegalArgumentException("formula needs to be a literal");
        }
        return super.add(formula);
    }

    @Override
    public boolean addAll(Collection<? extends Formula> c) {
        for (Formula f : c) {
            if (!f.isLiteral()) {
                throw new IllegalArgumentException("formula needs to be a literal");
            }
        }
        return super.addAll(c);
    }
}
