package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on four {@code float} operands that produces a result of
 * the {@code float} type. This is a specialization of {@link ShortQuadFunction} where the
 * operand and result are both {@code float}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsFloat}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface FloatQuadOperator {

    /**
     * Applies this function to the given arguments.
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @param d the fourth operand
     * @return the result of the operation
     */
    float applyAsFloat(float a, float b, float c, float d);
}

