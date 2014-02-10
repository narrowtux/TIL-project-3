package de.unikassel.til3.formula.clause;

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.RelationFormula;
import de.unikassel.til3.formula.walkers.ReplaceVariablesWalker;
import de.unikassel.til3.term.Term;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class ClauseSet extends LinkedHashSet<Clause> implements Cloneable {
    private static HashMap<Integer, RelationFormula> sat4jToRelation = new HashMap<Integer, RelationFormula>();
    private static HashMap<Integer, Integer> relationToSat4j = new HashMap<Integer, Integer>();
    private static int nextKey = 0;

    public static int getKeyForRelation(RelationFormula formula) {
        if (!relationToSat4j.containsKey(formula.hashCode())) {
            int key = ++nextKey;
            sat4jToRelation.put(key, formula);
            relationToSat4j.put(formula.hashCode(), key);
            return key;
        } else {
            return relationToSat4j.get(formula.hashCode());
        }
    }

    static RelationFormula getRelationForKey(int key) {
        return sat4jToRelation.get(key);
    }

    public Model solve() throws TimeoutException {
        ISolver solver = SolverFactory.newDefault();
        for (Clause c : this) {
            try {
                solver.addClause(c.toVecInt());
            } catch (ContradictionException e) {
                return null;
            }
        }
        if (solver.isSatisfiable()) {
            return Model.fromSAT4J(solver.model());
        } else {
            return null;
        }
    }

    public ClauseSet replaceBoundVariables(String variable, Term withTerm) {
        ReplaceVariablesWalker walker = new ReplaceVariablesWalker(variable, withTerm);
        ClauseSet clone = clone();
        for (Clause c : clone) {
            for (Formula literal : c) {
                literal.walkDown(walker);
            }
        }
        return clone;
    }

    @Override
    public ClauseSet clone() {
        ClauseSet ret = new ClauseSet();
        for (Clause c : this) {
            ret.add(c.clone());
        }
        return ret;
    }
}
