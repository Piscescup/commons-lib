package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents an operation on a single {@code float} operands that produces a result of
 * the {@code float} type. This is a specialization of {@link ShortFunction} where the
 * operand and result are both {@code float}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsFloat}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface FloatUnaryOperator {

    /**
     * Applies this function to the given arguments.
     * @param value the input operand
     * @return the result of the operation
     */
    float applyAsFloat(float value);
}

