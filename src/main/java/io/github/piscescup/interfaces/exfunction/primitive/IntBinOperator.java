package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents an operation on two {@code int} operands that produces a result of
 * the {@code int} type. This is a specialization of {@link IntBinFunction} where the
 * operand and result are both {@code int}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsInt}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface IntBinOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the result of the operation
     */
    int applyAsInt(int a, int b);
}

