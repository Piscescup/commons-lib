package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on four {@code short} operands that produces a result of
 * the {@code short} type. This is a specialization of {@link ShortQuadFunction} where the
 * operand and result are both {@code short}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsShort}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortQuadOperator {

    /**
     * Applies this function to the given arguments.
     * @param a the first operand
     * @param b the second operand
     * @param c the third operand
     * @param d the fourth operand
     * @return the result of the operation
     */
    short applyAsShort(short a, short b, short c, short d);
}

