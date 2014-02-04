package de.unikassel.til3.formula;

public class Biimplication extends Formula {

    private Formula left_arg, right_arg;

    public Biimplication(Formula f, Formula g) {
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
        return FormulaType.BIIMP;
    }

    public StringBuffer toString(StringBuffer s) {
        s = left_arg.toString(s);
        s.append(" <-> ");
        s = right_arg.toString(s);
        return s;
    }
}