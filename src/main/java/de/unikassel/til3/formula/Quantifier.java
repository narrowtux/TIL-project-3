package de.unikassel.til3.formula;

/**
 * Created by tux on 06.02.14.
 */
public abstract class Quantifier extends UnaryFormula {
    protected Quantifier(Formula arg) {
        super(arg);
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
