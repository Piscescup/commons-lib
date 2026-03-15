package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on a single {@code long} operand that produces a result of
 * the {@code long} type. This is a specialization of {@link LongFunction} where the
 * operand and result are both {@code long}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsLong}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface LongUnaryOperator {

    /**
     * Applies this function to the given arguments.
     * @param value the input operand
     * @return the result of the operation
     */
    long applyAsLong(long value);
}

