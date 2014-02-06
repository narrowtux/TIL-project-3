package de.unikassel.til3.formula;

public class ExistsQuantifier extends Quantifier {
    private String variable;

    public ExistsQuantifier(String v, Formula arg) {
        super(arg);
        variable = v;
    }

    public String getVariable() {
        return variable;
    }

    public FormulaType getType() {
        return FormulaType.EXISTS;
    }

    public StringBuffer toString(StringBuffer s) {
        boolean par = arg.getType().getPrecedence() < this.getType().getPrecedence();

        s.append("EXISTS ");
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

    @Override
    public boolean isInSkolemNormalForm() {
        return false;
    }
}