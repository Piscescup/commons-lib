package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on three {@code long} operands that produces a result of
 * the {@code long} type. This is a specialization of {@link LongTriFunction} where the
 * operand and result are both {@code long}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsLong}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface LongTriOperator {

    /**
     * Applies this function to the given arguments.
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @return the result of the operation
     */
    long applyAsLong(long a, long b, long c);
}

