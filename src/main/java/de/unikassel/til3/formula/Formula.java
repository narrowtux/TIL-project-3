package de.unikassel.til3.formula;

public abstract class Formula {

    public abstract FormulaType getType();

    public abstract StringBuffer toString(StringBuffer s);

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
}

