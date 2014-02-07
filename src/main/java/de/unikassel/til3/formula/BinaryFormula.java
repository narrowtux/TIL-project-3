package de.unikassel.til3.formula;

/**
 * Created by tux on 06.02.14.
 */
public abstract class BinaryFormula extends Formula {
    protected Formula left_arg, right_arg;

    protected BinaryFormula(Formula left_arg, Formula right_arg) {
        left_arg.parent = this;
        right_arg.parent = this;
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

    @Override
    public void walkDown(FormulaDownWalker walker) {
        Formula replace_left = walker.handle(left_arg);
        while (replace_left != null) {
            left_arg = replace_left;
            replace_left = walker.handle(left_arg);
        }
        Formula replace_right = walker.handle(right_arg);
        while (replace_right != null) {
            right_arg = replace_right;
            replace_right = walker.handle(right_arg);
        }
        left_arg.walkDown(walker);
        right_arg.walkDown(walker);
    }
}
