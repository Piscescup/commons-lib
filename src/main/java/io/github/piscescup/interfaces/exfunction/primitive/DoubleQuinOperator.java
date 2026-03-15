package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation five {@code double} operands that produces a result of
 * the {@code double} type. This is a specialization of {@link DoubleQuadFunction} where the
 * operand and result are both {@code double}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsDouble}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface DoubleQuinOperator {

    /**
     * Applies this function to the given arguments.
     * @param a the first argument
     * @param b the second argument
     * @param c the third argument
     * @param d the fourth argument
     * @param e the fifth argument
     * @return the result of the operation
     */
    double applyAsDouble(double a, double b, double c, double d, double e);
}

