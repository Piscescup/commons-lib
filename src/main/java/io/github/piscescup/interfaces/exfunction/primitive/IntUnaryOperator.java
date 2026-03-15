package io.github.piscescup.interfaces.exfunction.primitive;

import java.util.function.IntFunction;

/**
 * Represents an operation on a single {@code int} operand that produces a result of
 * the {@code int} type. This is a specialization of {@link IntFunction} where the
 * operand and result are both {@code int}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsInt}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface IntUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param value the input operand
     * @return the result of the operation
     */
    int applyAsInt(int value);
}

