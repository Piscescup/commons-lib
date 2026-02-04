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
public record BinEntry<X1, X2>(X1 x1, X2 x2) implements Entry, Serializable {

    @Serial
    private static final long serialVersionUID = 164674L;

    @Override
    public int arity() {
        return 2;
    }

    @Override
    public List<?> toList() {
        return List.of(x1, x2);
    }
}
