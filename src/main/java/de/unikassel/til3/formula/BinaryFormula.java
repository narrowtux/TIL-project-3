package de.unikassel.til3.formula;

/**
 * Created by tux on 06.02.14.
 */
public abstract class BinaryFormula extends Formula {
    protected Formula left_arg, right_arg;

    protected BinaryFormula(Formula left_arg, Formula right_arg) {
        this.left_arg = left_arg;
        this.right_arg = right_arg;
    }

    public Formula getLeft_arg() {
        return left_arg;
    }

    public Formula getRight_arg() {
        return right_arg;
    }

    @Override
    public boolean containsQuantifiers() {
        return left_arg.isQuantifier() || left_arg.containsQuantifiers() || right_arg.isQuantifier() || right_arg.containsQuantifiers();
    }

    @Override
    public boolean isInPositiveNormalForm() {
        return right_arg.isInPositiveNormalForm() && left_arg.isInPositiveNormalForm();
    }

    @Override
    public boolean isInSkolemNormalForm() {
        return isInPrenexNormalForm() && left_arg.isInSkolemNormalForm() && right_arg.isInSkolemNormalForm();
    }
}
