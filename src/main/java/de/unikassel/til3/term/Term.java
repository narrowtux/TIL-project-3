package de.unikassel.til3.term;

import de.unikassel.til3.formula.FunctionSymbol;

import java.util.Vector;

public class Term {

    private FunctionSymbol symbol;
    private Vector<Term> subterms;

    public Term(FunctionSymbol s, Vector<Term> ts) {
        symbol = s;
        subterms = ts;
    }

    protected Term() {
        symbol = null;
        subterms = new Vector<Term>();
    }

    public FunctionSymbol getTopSymbol() {
        return symbol;
    }

    public Vector<Term> getSubterms() {
        return subterms;
    }

    public Term makeCopy() {
        Vector<Term> ts = new Vector<Term>();

        for (Term t : subterms) {
            ts.add(t.makeCopy());
        }

        return new Term(symbol, ts);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        boolean first = true;

        s.append(symbol.toString());
        if (symbol.getArity() > 0) {
            s.append("(");
            for (Term t : subterms) {
                if (!first) {
                    s.append(",");
                }
                s.append(t.toString());
                first = false;
            }
            s.append(")");
        }
        return s.toString();
    }
}
