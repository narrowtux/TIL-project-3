package de.unikassel.til3.formula;

public class Negation extends Formula {

    private Formula arg;

    public Negation(Formula f) {
        arg = f;
    }

    public Formula getArg() {
        return arg;
    }

    public FormulaType getType() {
        return FormulaType.NEG;
    }

    public StringBuffer toString(StringBuffer s) {
        boolean par = arg.getType().getPrecedence() < this.getType().getPrecedence();

        s.append("-");
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