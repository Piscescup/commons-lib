package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a predicate (boolean-valued function) of four {@code char}-valued arguments.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(char, char, char, char)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface CharQuadPredicate {

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
    boolean test(char x1, char x2, char x3, char x4);

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.
     *
     * <p>When evaluating the composed predicate, if this predicate returns
     * {@code false}, then the {@code other} predicate is not evaluated.</p>
     *
     * @param other the predicate to be logically-ANDed with this predicate
     * @return a composed predicate representing the logical AND
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default CharQuadPredicate and(CharQuadPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) ->
            test(x1, x2, x3, x4) && other.test(x1, x2, x3, x4);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate representing the logical negation
     */
    default CharQuadPredicate negate() {
        return (x1, x2, x3, x4) ->
            !test(x1, x2, x3, x4);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.
     *
     * <p>When evaluating the composed predicate, if this predicate returns
     * {@code true}, then the {@code other} predicate is not evaluated.</p>
     *
     * @param other the predicate to be logically-ORed with this predicate
     * @return a composed predicate representing the logical OR
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default CharQuadPredicate or(CharQuadPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) ->
            test(x1, x2, x3, x4) || other.test(x1, x2, x3, x4);
    }
}