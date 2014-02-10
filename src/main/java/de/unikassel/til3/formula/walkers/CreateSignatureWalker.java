package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.RelationFormula;
import de.unikassel.til3.formula.Walker;
import de.unikassel.til3.term.Signature;
import de.unikassel.til3.term.Term;

import java.util.Stack;

/**
 * Created by tux on 10.02.14.
 */
public class CreateSignatureWalker implements Walker<Formula> {
    private Stack<String> variables;
    Signature signature = new Signature();
    SearchFunctionSymbolsWalker functionSymbolsWalker = new SearchFunctionSymbolsWalker();

    public CreateSignatureWalker(Stack<String> variables) {
        this.variables = variables;
    }

    @Override
    public Formula handle(Formula on) {
        if (on instanceof RelationFormula) {
            for (Term term : ((RelationFormula) on).getTerms()) {
                if (!variables.contains(term.getTopSymbol().getName())) {
                    signature.add(term.getTopSymbol());
                    term.walkDown(functionSymbolsWalker);

                }
            }
        }
        return null;
    }

    public Signature getSignature() {
        return signature;
    }

    private class SearchFunctionSymbolsWalker implements Walker<Term> {
        @Override
        public Term handle(Term on) {
            if (!variables.contains(on.getTopSymbol().getName())) {
                signature.add(on.getTopSymbol());
            }
            return null;
        }
    }
}
