package de.unikassel.til3.formula;

public class Disjunction extends BinaryFormula {

    public Disjunction(Formula left, Formula right) {
        super(left, right);
    }

    public FormulaType getType() {
        return FormulaType.OR;
    }

    public StringBuffer toString(StringBuffer s) {
        boolean lpar = left_arg.getType().getPrecedence() < this.getType().getPrecedence();
        boolean rpar = right_arg.getType().getPrecedence() < this.getType().getPrecedence();

        if (lpar) {
            s.append("(");
        }
        s = left_arg.toString(s);
        if (lpar) {
            s.append(")");
        }
        s.append(" | ");
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