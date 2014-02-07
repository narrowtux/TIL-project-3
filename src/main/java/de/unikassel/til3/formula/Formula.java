package de.unikassel.til3.formula;

public abstract class Formula implements Walkable<Formula> {

    public abstract FormulaType getType();

    public abstract StringBuffer toString(StringBuffer s);

    protected Formula parent;

    public String toString() {
        return toString(new StringBuffer()).toString();
    }

    /**
     * Formula is a relation
     * @return if the formula is an atom
     */
    public boolean isAtom() {
        return false;
    }

    /**
     * Formula is either a relation or a negated relation
     * @return if the formula is a literal
     */
    public boolean isLiteral() {
        return isAtom();
    }

    /**
     * Formula only consists of symbols &, |, EXISTS, FORALL and Literals
     * @return if the formula is in the positive normal form
     */
    public boolean isInPositiveNormalForm() {
        return isLiteral();
    }

    /**
     * Formula is in form Q_1 x_1,...,Q_n x_n v
     * where Q_i is a quantifier and v is quantifier free
     * @return if the formula is in the prenex normal form
     */
    public boolean isInPrenexNormalForm() {
        return isInPositiveNormalForm() && isQuantifierFree();
    }

    /**
     * Formula is in prenex normal form and doesn't consist of EXISTS quantifiers
     * @return if the formula is in the skolem normal form
     */
    public boolean isInSkolemNormalForm() {
        return isInPrenexNormalForm();
    }

    /**
     * @return if the formula contains any quantifiers
     */
    public abstract boolean containsQuantifiers();

    /**
     * @return if the root of the formula is a quantifier
     */
    public boolean isQuantifier() {
        return false;
    }

    /**
     * @return if the formula is free of quantifiers
     */
    public boolean isQuantifierFree() {
        return !(isQuantifier() || containsQuantifiers());
    }

    public void walkDown(Walker<Formula> walker) {
        walkDown((FormulaDownWalker) walker);
    }

    protected abstract void walkDown(FormulaDownWalker walker);

    /**
     * Walks the given walker down the formula (from root to leaves).
     * If the walker returns a value, the walker is run on that value again until it returns null
     * @param formula the formula to walk down
     * @param walker the walker to use
     * @return the replacement of the root
     */
    public static Formula walkDown(Formula formula, FormulaDownWalker walker) {
        Formula replace = walker.handle(formula);
        while (replace != null) {
            formula = replace;
            replace = walker.handle(formula);
        }
        formula.walkDown(walker);
        return formula;
    }

    public interface FormulaDownWalker extends Walker<Formula> {
        /**
         * Handles a formula
         * @param f the formula to handle
         * @return the formula to replace this, or null if no replacement
         */
        public Formula handle(Formula f);
    }
}

