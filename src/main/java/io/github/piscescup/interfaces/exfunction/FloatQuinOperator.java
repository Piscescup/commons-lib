package io.github.piscescup.interfaces.exfunction;

/**
 * Represents a function that accepts five {@code float}-valued arguments
 * and produces a {@code float}-valued result.
 *
 * <p>This is the primitive specialization of a five-argument function
 * with {@code float} inputs and output.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsFloat(float, float, float, float, float)}.
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface FloatQuinOperator {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @param x3 the third argument
     * @param x4 the fourth argument
     * @param x5 the fifth argument
     * @return the function result
     */
    float applyAsFloat(float x1, float x2, float x3, float x4, float x5);

}