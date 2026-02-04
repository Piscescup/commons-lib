package io.github.piscescup.entries;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a 3 element tuple.
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
