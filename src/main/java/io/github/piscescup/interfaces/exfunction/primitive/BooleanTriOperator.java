package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents an operation on three {@code boolean} operands that produces a result of
 * the {@code boolean} type. This is a specialization of {@link BooleanTriFunction} where the
 * operand and result are both {@code boolean}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsBoolean}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface BooleanTriOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @return the result of the operation
     */
    boolean applyAsBoolean(boolean a, boolean b, boolean c);
}

