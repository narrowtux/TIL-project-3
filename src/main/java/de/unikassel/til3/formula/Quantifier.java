package de.unikassel.til3.formula;

/**
 * Created by tux on 06.02.14.
 */
public abstract class Quantifier extends UnaryFormula {
    protected String variable;
    protected Quantifier(String variable, Formula arg) {
        super(arg);
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public boolean isQuantifier() {
        return true;
    }

    @Override
    public boolean isInPositiveNormalForm() {
        return arg.isInPositiveNormalForm();
    }

    @Override
    public boolean containsQuantifiers() {
        return arg.isQuantifier() || arg.containsQuantifiers();
    }

    @Override
    public boolean isInPrenexNormalForm() {
        return arg.isInPrenexNormalForm();
    }
}
