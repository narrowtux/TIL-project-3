package de.unikassel.til3.formula;

public class Negation extends UnaryFormula {
    public Negation(Formula arg) {
        super(arg);
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

    @Override
    public boolean isLiteral() {
        return arg.isAtom();
    }

    @Override
    public boolean isInPositiveNormalForm() {
        return isLiteral();
    }

    @Override
    public boolean containsQuantifiers() {
        return arg.isQuantifier() || arg.containsQuantifiers();
    }
}