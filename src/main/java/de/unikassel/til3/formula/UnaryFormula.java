package de.unikassel.til3.formula;

/**
 * Created by tux on 06.02.14.
 */
public abstract class UnaryFormula extends Formula {
    protected Formula arg;

    protected UnaryFormula(Formula arg) {
        this.arg = arg;
    }

    public Formula getArg() {
        return arg;
    }

    @Override
    public boolean isInSkolemNormalForm() {
        return isInPrenexNormalForm() && arg.isInSkolemNormalForm();
    }
}
