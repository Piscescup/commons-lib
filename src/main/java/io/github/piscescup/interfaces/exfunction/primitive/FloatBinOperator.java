package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on two {@code float} operands that produces a result of
 * the {@code float} type. This is a specialization of {@link ShortBinFunction} where the
 * operand and result are both {@code float}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsFloat}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface FloatBinOperator {

    /**
     * Applies this function to the given arguments.
     * @param a the first operand
     * @param b the second operand
     * @return the result of the operation
     */
    float applyAsFloat(float a, float b);
}

