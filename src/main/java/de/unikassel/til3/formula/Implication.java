package de.unikassel.til3.formula;

public class Implication extends BinaryFormula {

    public Implication(Formula left_arg, Formula right_arg) {
        super(left_arg, right_arg);
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

    @Override
    public boolean isInPositiveNormalForm() {
        return false;
    }

    @Override
    public boolean isInPrenexNormalForm() {
        return false;
    }
}