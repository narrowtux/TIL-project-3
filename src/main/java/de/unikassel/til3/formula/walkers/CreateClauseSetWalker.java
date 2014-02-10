package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.*;
import de.unikassel.til3.formula.clause.Clause;
import de.unikassel.til3.formula.clause.ClauseSet;

/**
 * Created by tux on 10.02.14.
 */
public class CreateClauseSetWalker implements Walker<Formula> {

    ClauseSet clauseSet = new ClauseSet();
    Clause currentClause = null;
    Disjunction currentDisjunction = null;

    @Override
    public Formula handle(final Formula on) {
        if (on instanceof RelationFormula && on.getParent() == null) {
            clauseSet.add(new Clause() {{ add((on));}});
        }
        if (on instanceof Conjunction) {
            if (((Conjunction) on).getLeft_arg().isLiteral()) {
                clauseSet.add(new Clause() {{ add(((Conjunction) on).getLeft_arg());}});
            }
            if (((Conjunction) on).getRight_arg().isLiteral()) {
                clauseSet.add(new Clause() {{ add(((Conjunction) on).getRight_arg());}});
            }
        }
        if (on instanceof Disjunction) {
            if (on == getHighestDisjunction(on)) {
                currentClause = new Clause();
                clauseSet.add(currentClause);
                currentDisjunction = (Disjunction) on;
            }
            if (((Disjunction) on).getLeft_arg().isLiteral()) {
                currentClause.add(((Disjunction) on).getLeft_arg());
            }
            if (((Disjunction) on).getRight_arg().isLiteral()) {
                currentClause.add(((Disjunction) on).getRight_arg());
            }
        }
        return null;
    }

    private Disjunction getHighestDisjunction(Formula f) {
        if (f instanceof Disjunction && (f.getParent() == null || f.getParent() instanceof Conjunction)) {
            return (Disjunction) f;
        }
        return getHighestDisjunction(f.getParent());
    }

    public ClauseSet getClauseSet() {
        return clauseSet;
    }
}
