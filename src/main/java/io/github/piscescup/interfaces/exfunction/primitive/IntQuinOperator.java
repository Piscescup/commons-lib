package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on five {@code int} operands that produces a result of
 * the {@code int} type. This is a specialization of {@link IntQuinFunction} where the
 * operand and result are both {@code int}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsInt}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface IntQuinOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @param d the fourth operand
     * @param e the fifth operand
     * @return the result of the operation
     */
    int applyAsInt(int a, int b, int c, int d, int e);
}

