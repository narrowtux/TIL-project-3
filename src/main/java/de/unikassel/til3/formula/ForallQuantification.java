package de.unikassel.til3.formula;

import java.lang.StringBuffer;

public class ForallQuantification extends Formula {

    private Formula arg;
    private String variable;

    public ForallQuantification(String v, Formula f) {
	arg = f;
	variable = v;
    }

    public Formula getArg() {
	return arg;
    }

    public String getVariable() {
	return variable;
    }

    public FormulaType getType() {
	return FormulaType.FORALL;
    }

    public StringBuffer toString(StringBuffer s) {
	boolean par = arg.getType().getPrecedence() < this.getType().getPrecedence();

	s.append("FORALL ");
	s.append(variable);
	s.append(" ");
	if (par) {
	    s.append("(");
	}
	s = arg.toString(s);
	if (par) {
	    s.append(")");
	}
	return s;
    }
}