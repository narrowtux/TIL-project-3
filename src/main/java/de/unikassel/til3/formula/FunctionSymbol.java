package de.unikassel.til3.formula;

public class FunctionSymbol {

    private String name;
    private int arity;

    public FunctionSymbol(String n, int a) {
	name = n;
	arity = a;
    }

    public String getName() {
	return name;
    }

    public int getArity() {
	return arity;
    }

    public String toString() {
	return name;
    }

    public boolean equals(FunctionSymbol s) {
	return name.equals(s.getName()) && arity == s.getArity();
    }
}