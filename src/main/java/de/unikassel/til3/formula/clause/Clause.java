package de.unikassel.til3.formula.clause;

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.Negation;
import de.unikassel.til3.formula.RelationFormula;
import org.sat4j.core.VecInt;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Clause extends LinkedHashSet<Formula> implements Cloneable {
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

    public VecInt toVecInt() {
        int vec[] = new int[size()];
        int i = 0;
        for (Formula literal : this) {
            int key;
            if (literal instanceof RelationFormula) {
                key = ClauseSet.getKeyForRelation((RelationFormula) literal);
            } else {
                key = -ClauseSet.getKeyForRelation((RelationFormula) ((Negation) literal).getArg());
            }
            vec[i] = key;
            i++;
        }
        VecInt ret = new VecInt(vec);
        return ret;
    }

    @Override
    public Clause clone() {
        Clause ret = new Clause();
        ret.addAll(this);
        return ret;
    }
}
