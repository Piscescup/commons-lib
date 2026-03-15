package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on five {@code long} operands that produces a result of
 * the {@code long} type. This is a specialization of {@link LongQuinFunction} where the
 * operand and result are both {@code long}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsLong}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface LongQuinOperator {

    /**
     * Applies this function to the given arguments.
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @param d the fourth operand
     * @param e the fifth operand
     * @return the result of the operation
     */
    long applyAsLong(long a, long b, long c, long d, long e);
}

