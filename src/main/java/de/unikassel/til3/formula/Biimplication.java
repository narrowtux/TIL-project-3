package de.unikassel.til3.formula;

public class Biimplication extends BinaryFormula {

    public Biimplication(Formula left, Formula right) {
        super(left, right);
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

    @Override
    public boolean isInPositiveNormalForm() {
        return false;
    }

    @Override
    public boolean isInPrenexNormalForm() {
        return false;
    }
}