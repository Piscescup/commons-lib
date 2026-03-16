package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a predicate (boolean-valued function) of four {@code int}-valued arguments.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(int, int, int, int)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface IntQuadPredicate {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(int x1, int x2, int x3, int x4);

    /**
     * Returns a composed predicate representing the logical AND of this predicate and another.
     *
     * @param other the predicate to be logically-ANDed with this predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default IntQuadPredicate and(IntQuadPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> test(x1, x2, x3, x4) && other.test(x1, x2, x3, x4);
    }

    /**
     * Returns a predicate representing the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default IntQuadPredicate negate() {
        return (x1, x2, x3, x4) -> !test(x1, x2, x3, x4);
    }

    /**
     * Returns a composed predicate representing the logical OR of this predicate and another.
     *
     * @param other the predicate to be logically-ORed with this predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default IntQuadPredicate or(IntQuadPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> test(x1, x2, x3, x4) || other.test(x1, x2, x3, x4);
    }
}