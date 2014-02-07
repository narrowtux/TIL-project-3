package de.unikassel.til3.formula;

/**
 * Created by tux on 07.02.14.
 */
public interface Walker<T> {
    public T handle(T on);
}
