package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on a single {@code short} operands that produces a result of
 * the {@code short} type. This is a specialization of {@link ShortFunction} where the
 * operand and result are both {@code short}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsShort}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortUnaryOperator {

    /**
     * Applies this function to the given arguments.
     * @param value the input operand
     * @return the result of the operation
     */
    short applyAsShort(short value);
}

