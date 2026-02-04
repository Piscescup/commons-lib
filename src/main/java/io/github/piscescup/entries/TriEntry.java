package io.github.piscescup.entries;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a 3 element entry.
 *
 * @param x1 the first argument
 * @param x2 the second argument
 * @param x3 the third argument
 *
 * @param <X1> the type of the first argument to the function
 * @param <X2> the type of the second argument to the function
 * @param <X3> the type of the third argument to the function
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public record TriEntry<X1, X2, X3>(X1 x1, X2 x2, X3 x3) implements Entry, Serializable {
    @Serial
    private static final long serialVersionUID = 154487L;

    @Override
    public int arity() {
        return 3;
    }

    @Override
    public List<?> toList() {
        return List.of(x1, x2, x3);
    }
}
