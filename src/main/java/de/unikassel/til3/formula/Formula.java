package de.unikassel.til3.formula;

public abstract class Formula {

    public abstract FormulaType getType();

    public abstract StringBuffer toString(StringBuffer s);

    public String toString() {
        return toString(new StringBuffer()).toString();
    }
}

