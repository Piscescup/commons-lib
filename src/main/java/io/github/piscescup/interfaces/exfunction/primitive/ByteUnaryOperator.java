package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents an operation on a single byte operand that produces a result of
 * the same type. This is a specialization of {@code ByteFunction} for the
 * case where the operand and result are of the same primitive type.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsByte(byte)}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ByteUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param value the input operand
     * @return the result of the operation
     */
    byte applyAsByte(byte value);
}

