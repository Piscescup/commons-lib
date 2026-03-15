package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents an operation on two {@code double} operands that produces a result of
 * the {@code double} type. This is a specialization of {@link DoubleBinFunction} where the
 * operand and result are both {@code double}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsDouble}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface DoubleBinOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the result of the operation
     */
    double applyAsDouble(double a, double b);
}

