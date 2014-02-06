package de.unikassel.til3.term;

import de.unikassel.til3.formula.FunctionSymbol;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Vector;

public class TermEnumerator {

    private Signature signature;
    private Queue<Term> worklist;

    public TermEnumerator(Signature s) {
        signature = s;
        worklist = new LinkedList<Term>();
        worklist.add(new Placeholder(0));
    }

    public boolean hasNext() {
        return !worklist.isEmpty();
    }

    public Term getNext() {
        Term termpattern,t=null;
        boolean found = false;

        while (!found && hasNext()) {
            termpattern = worklist.poll();
            //	    System.out.println("Next term pattern: " + termpattern.toString());
            t = instantiate(termpattern, signature);
            if (isFullyInstantiated(t)) {
                found = true;
            } else {
                worklist.add(t);
            }
            if (!increaseToNextTerm(termpattern, signature.size(), true)) {
                worklist.add(termpattern);
            }
        }
        return t;
    }

    private static boolean isFullyInstantiated(Term t) {
        if (t instanceof Placeholder) {
            return false;
        }
        for (Term s: t.getSubterms()) {
            if (!isFullyInstantiated(s)) {
                return false;
            }
        }
        return true;
    }

    private static Term instantiate(Term t, Signature sig) {
        Vector<Term> ts = new Vector<Term>();
        // boolean found = false;

        if (t instanceof Placeholder) {
            FunctionSymbol s = sig.get(((Placeholder) t).getPoint());
            int arity = s.getArity();
            for (int i=0; i<arity; i++) {
                ts.add(new Placeholder(0));
            }
            return new Term(s,ts);
        } else {
            for (Term s: t.getSubterms()) {
                ts.add(instantiate(s, sig));
            }
            return new Term(t.getTopSymbol(), ts);
        }
    }

    private static boolean increaseToNextTerm(Term t, int l, boolean b) {
        Vector<Term> ts = new Vector<Term>();
        boolean increasefurther = b;
        int i;

        if (t instanceof Placeholder) {
            Placeholder p = (Placeholder) t;
            if (b) {
                p.incrPoint();
                if (p.getPoint() >= l) {
                    p.resetPoint();
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } else {
            for (Term s: t.getSubterms()) {
                if (increasefurther) {
                    increasefurther = increaseToNextTerm(s, l, increasefurther);
                }
            }
        }

        return increasefurther;
    }
}

class Placeholder extends Term {

    private int point;

    public Placeholder(int p) {
        point = p;
    }

    public int getPoint() {
        return point;
    }

    public void incrPoint() {
        point++;
    }

    public void resetPoint() {
        point = 0;
    }

    public FunctionSymbol getTopSymbol() {
        return null;
    }

    public Vector<Term> getSubterms() {
        return new Vector<Term>();
    }

    public String toString() {
        return ("[" + point + "]");
    }

    public Term makeCopy() {
        return new Placeholder(point);
    }
}