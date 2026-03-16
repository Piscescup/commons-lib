package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a predicate (boolean-valued function) of four {@code long}-valued arguments.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(long, long, long, long)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface LongQuadPredicate {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     * @return {@code true} if the input arguments match the predicate
     */
    boolean test(long x1, long x2, long x3, long x4);

    /**
     * Returns a composed predicate representing the logical AND of this predicate and another.
     *
     * @param other the predicate to be logically-ANDed with this predicate
     * @return a composed predicate
     */
    default LongQuadPredicate and(LongQuadPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> test(x1, x2, x3, x4) && other.test(x1, x2, x3, x4);
    }

    /**
     * Returns a predicate representing the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default LongQuadPredicate negate() {
        return (x1, x2, x3, x4) -> !test(x1, x2, x3, x4);
    }

    /**
     * Returns a composed predicate representing the logical OR of this predicate and another.
     *
     * @param other the predicate to be logically-ORed with this predicate
     * @return a composed predicate
     */
    default LongQuadPredicate or(LongQuadPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> test(x1, x2, x3, x4) || other.test(x1, x2, x3, x4);
    }
}