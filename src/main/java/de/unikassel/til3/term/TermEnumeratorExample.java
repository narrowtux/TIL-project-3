package de.unikassel.til3.term;

import de.unikassel.til3.formula.FunctionSymbol;

public class TermEnumeratorExample {

    public static void main(String[] args) {


	// create a new signature
	// signatures contain functions symbols only!
	Signature sig = new Signature();

	// add as many function symbols as you like to your signature
	sig.add(new FunctionSymbol("c",0));
	sig.add(new FunctionSymbol("f",1));
	// sig.add(new FunctionSymbol("d",0));
	// sig.add(new FunctionSymbol("g",2));
	// sig.add(new FunctionSymbol("h",1));

	// create a new term enumerator object for this signature
	TermEnumerator te = new TermEnumerator(sig);

	Term t;
	boolean first = true;

	// print out all terms over this signature
	System.out.print("{ ");
	while (te.hasNext()) {
	    t = te.getNext();
	    if (!first) {
		System.out.print(", ");
	    }
	    System.out.print(t.toString());
	    first = false;
	}
	System.out.println(" }");
    }
}