package de.unikassel.til3.formula.clause;

import de.unikassel.til3.formula.FunctionSymbol;
import de.unikassel.til3.formula.RelationFormula;
import de.unikassel.til3.term.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model extends HashMap<RelationFormula, Boolean> {
    private HashMap<String, List<Term>> terms = new HashMap<String, List<Term>>();

    public static Model fromSAT4J(int model[]) {
        Model m = new Model();
        for (int i = 0; i < model.length; i++) {
            int key = Math.abs(model[i]);
            RelationFormula formula = ClauseSet.getRelationForKey(key);
            boolean value = model[i] >= 0;
            m.put(formula, value);
        }
        Model ret = new Model();
        ret.applyPositive(m);
        return ret;
    }

    public void applyPositive(Model model) {
        outer: for (Map.Entry<RelationFormula, Boolean> e : model.entrySet()) {
            if (e.getValue()) {
                put(e.getKey(), true);
                String relationName = e.getKey().getName();
                List<Term> termlist = terms.get(relationName);
                if (termlist == null) {
                    termlist = new ArrayList<Term>();
                    terms.put(relationName, termlist);
                }
                Term toAdd = new Term(new FunctionSymbol("", e.getKey().getTerms().size()), e.getKey().getTerms());
                for (Term t : termlist) {
                    if (t.toString().equals(toAdd.toString())) {
                        continue outer;
                    }
                }
                termlist.add(toAdd);
            }
        }
    }

    @Override
    public String toString() {
        return terms.toString();
    }
}
