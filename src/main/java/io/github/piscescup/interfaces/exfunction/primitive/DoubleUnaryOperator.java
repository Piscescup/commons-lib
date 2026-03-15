package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents an operation on a single {@code double} operand that produces a result of
 * the {@code double} type. This is a specialization of {@link DoubleFunction} where the
 * operand and result are both {@code double}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsDouble}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface DoubleUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param value the input operand
     * @return the result of the operation
     */
    double applyAsDouble(double value);
}

