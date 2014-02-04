package de.unikassel.til3.formula;

public enum FormulaType {
    REL(4), AND(3), OR(2), NEG(4), IMP(1), BIIMP(0), EXISTS(4), FORALL(4);

    private int precedence;

    FormulaType(int p) {
	precedence = p;
    }

    public int getPrecedence() {
	return precedence;
    }
}