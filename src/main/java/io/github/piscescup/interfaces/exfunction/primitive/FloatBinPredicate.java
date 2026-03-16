package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a predicate (boolean-valued function) of two {@code float}-valued arguments.
 *
 * <p>This is the {@code float}-consuming primitive specialization of
 * a two-argument predicate.</p>
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(float, float)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface FloatBinPredicate {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @return {@code true} if the input arguments match the predicate
     */
    boolean test(float x1, float x2);

    /**
     * Returns a composed predicate representing the logical AND of this predicate and another.
     *
     * @param other the predicate to be logically-ANDed with this predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FloatBinPredicate and(FloatBinPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2) -> test(x1, x2) && other.test(x1, x2);
    }

    /**
     * Returns a predicate representing the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default FloatBinPredicate negate() {
        return (x1, x2) -> !test(x1, x2);
    }

    /**
     * Returns a composed predicate representing the logical OR of this predicate and another.
     *
     * @param other the predicate to be logically-ORed with this predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FloatBinPredicate or(FloatBinPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2) -> test(x1, x2) || other.test(x1, x2);
    }
}