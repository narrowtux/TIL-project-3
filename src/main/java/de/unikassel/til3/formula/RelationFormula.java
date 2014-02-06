package de.unikassel.til3.formula;

import de.unikassel.til3.term.Term;

import java.util.Vector;

public class RelationFormula extends Formula {

    private Vector<Term> terms;
    private String name;

    public RelationFormula(String s, Vector<Term> ts) {
        terms = ts;
        name = s;
    }

    public Vector<Term> getTerms() {
        return terms;
    }

    public FormulaType getType() {
        return FormulaType.REL;
    }

    public StringBuffer toString(StringBuffer s) {
        s.append(name);
        if (terms.size() > 0) {
            s.append("(");
            s.append(terms.get(0).toString());
            for (int i = 1; i < terms.size(); i++) {
                s.append(",");
                s.append(terms.get(i).toString());
            }
            s.append(")");
        }
        return s;
    }

    @Override
    public boolean isAtom() {
        return true;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public boolean containsQuantifiers() {
        return false;
    }
}