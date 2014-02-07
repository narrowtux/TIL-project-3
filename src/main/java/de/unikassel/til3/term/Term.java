package de.unikassel.til3.term;

import de.unikassel.til3.formula.FunctionSymbol;
import de.unikassel.til3.formula.Walkable;
import de.unikassel.til3.formula.Walker;

import java.util.Vector;

public class Term implements Walkable<Term> {

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

    @Override
    public void walkDown(Walker<Term> walker) {
        for (int i = 0; i < subterms.size(); i++) {
            Term term = subterms.elementAt(i);
            Term replace = walker.handle(term);
            while (replace != null) {
                term = replace;
                replace = walker.handle(term);
            }
            subterms.set(i, term);
        }
    }

    public static Term walkDown(Term term, Walker<Term> walker) {
        Term replace = walker.handle(term);
        while (replace != null) {
            term = replace;
            replace = walker.handle(term);
        }
        for (int i = 0; i < term.subterms.size(); i++) {
            Term term1 = term.subterms.elementAt(i);
            replace = walker.handle(term1);
            while (replace != null) {
                term1 = replace;
                replace = walker.handle(term1);
            }
            term.subterms.set(i, term1);
        }
        return term;
    }
}
