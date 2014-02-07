package de.unikassel.til3.formula;

/**
 * Created by tux on 06.02.14.
 */
public abstract class UnaryFormula extends Formula {
    protected Formula arg;

    protected UnaryFormula(Formula arg) {
        arg.parent = this;
        this.arg = arg;
    }

    public Formula getArg() {
        return arg;
    }

    @Override
    public boolean isInSkolemNormalForm() {
        return isInPrenexNormalForm() && arg.isInSkolemNormalForm();
    }

    @Override
    public void walkDown(FormulaDownWalker walker) {
        Formula replace = walker.handle(arg);
        while (replace != null) {
            arg = replace;
            replace = walker.handle(arg);
        }
        arg.walkDown(walker);
    }
}
