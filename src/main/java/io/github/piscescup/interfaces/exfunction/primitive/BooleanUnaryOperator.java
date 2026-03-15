package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on three {@code boolean} operands that produces a result of
 * the {@code boolean} type. This is a specialization of {@link BooleanBinFunction} where the
 * operand and result are both {@code boolean}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsBoolean}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface BooleanUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param value the input operand
     * @return the result of the operation
     */
    boolean applyAsBoolean(boolean value);
}

