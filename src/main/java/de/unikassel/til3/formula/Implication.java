package de.unikassel.til3.formula;

import java.lang.StringBuffer;

public class Implication extends Formula {

    private Formula left_arg, right_arg;

    public Implication(Formula f, Formula g) {
	left_arg = f;
	right_arg = g;
    }

    public Formula getLeftArg() {
	return left_arg;
    }

    public Formula getRightArg() {
	return right_arg;
    }

    public FormulaType getType() {
	return FormulaType.IMP;
    }

    public StringBuffer toString(StringBuffer s) {
	boolean lpar = left_arg.getType().getPrecedence() <= this.getType().getPrecedence();
	boolean rpar = right_arg.getType().getPrecedence() < this.getType().getPrecedence();

	if (lpar) {
	    s.append("(");
	}
	s = left_arg.toString(s);
	if (lpar) {
	    s.append(")");
	}
	s.append(" -> ");
	if (rpar) {
	    s.append("(");
	}
	s = right_arg.toString(s);
	if (rpar) {
	    s.append(")");
	}
	return s;
    }
}