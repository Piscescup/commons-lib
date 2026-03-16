package io.github.piscescup.interfaces.exfunction.primitive;


/**
 * Represents an operation on three {@code char} operands that produces a result of
 * the {@code char} type. This is a specialization of {@link CharTriFunction} where the
 * operand and result are both {@code char}.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #applyAsChar}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface CharTriOperator {

    /**
     * Applies this function to the given arguments.
     * @param a the first char operand
     * @param b the second char operand
     * @param c the third char operand
     * @return the result of the operation
     */
    char applyAsChar(char a, char b, char c);
}

