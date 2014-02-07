package de.unikassel.til3.formula.walkers;

import de.unikassel.til3.formula.BinaryFormula;
import de.unikassel.til3.formula.Formula;
import de.unikassel.til3.formula.Quantifier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by tux on 07.02.14.
 */
public class PushQuantifiersUpWalker implements Formula.FormulaDownWalker {
    @Override
    public Formula handle(Formula f) {
        try {
            if (f instanceof BinaryFormula) {
                BinaryFormula bf = (BinaryFormula) f;
                Formula left = ((BinaryFormula) f).getLeft_arg(), right = ((BinaryFormula) f).getRight_arg();
                Quantifier l = null, r = null;
                Constructor leftConstructor = null, rightConstructor = null;

                Class operatorClass = f.getClass();
                Constructor operatorConstructor = null;
                operatorConstructor = operatorClass.getConstructor(Formula.class, Formula.class);

                if (left instanceof Quantifier) {
                    l = replaceVariable((Quantifier) left);
                    leftConstructor = l.getClass().getConstructor(String.class, Formula.class);
                }
                if (right instanceof Quantifier) {
                    r = replaceVariable((Quantifier) right);
                    rightConstructor = r.getClass().getConstructor(String.class, Formula.class);
                }

                if (left instanceof Quantifier && right instanceof Quantifier) {
                    return (Formula) leftConstructor.newInstance(l.getVariable(), rightConstructor.newInstance(r.getVariable(), operatorConstructor.newInstance(l.getArg(), r.getArg())));
                } else if (left instanceof Quantifier) {
                    return (Formula) leftConstructor.newInstance(l.getVariable(), operatorConstructor.newInstance(l.getArg(), bf.getRight_arg()));
                } else if (right instanceof Quantifier) {
                    return (Formula) rightConstructor.newInstance(r.getVariable(), operatorConstructor.newInstance(r.getArg(), bf.getLeft_arg()));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // unary formulas don't have to be handled.
        return null;
    }

    private Quantifier replaceVariable(Quantifier q) {
        return (Quantifier) Formula.walkDown(q, new ReplaceVariablesWalker(q.getVariable(), ReplaceVariablesWalker.getNextFreeVariable()));
    }
}
