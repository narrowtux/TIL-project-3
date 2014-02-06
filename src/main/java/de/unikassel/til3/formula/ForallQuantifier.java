package de.unikassel.til3.formula;

public class ForallQuantifier extends Quantifier {

    private String variable;

    public ForallQuantifier(String v, Formula arg) {
        super(arg);
        variable = v;
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