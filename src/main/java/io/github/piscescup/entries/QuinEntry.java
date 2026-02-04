package io.github.piscescup.entries;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a 5 element entry.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public record QuinEntry<X1, X2, X3, X4, X5>(X1 x1, X2 x2, X3 x3, X4 x4, X5 x5) implements Entry, Serializable {
    @Serial
    private static final long serialVersionUID = 7861L;

    @Override
    public int arity() {
        return 5;
    }

    @Override
    public List<?> toList() {
        return List.of(x1, x2, x3, x4, x5);
    }
}
