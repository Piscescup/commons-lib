package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a predicate (boolean-valued function) of one {@code short}-valued argument.
 *
 * <p>This is the {@code short}-consuming primitive specialization of
 * {@link java.util.function.Predicate}.</p>
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(short)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortPredicate {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(short value);

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
    default ShortPredicate and(ShortPredicate other) {
        NullCheck.requireNonNull(other);
        return value -> test(value) && other.test(value);
    }

    /**
     * Returns a predicate representing the logical negation of this predicate.
     *
     * @return a predicate representing the logical negation
     */
    default ShortPredicate negate() {
        return value -> !test(value);
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
    default ShortPredicate or(ShortPredicate other) {
        NullCheck.requireNonNull(other);
        return value -> test(value) || other.test(value);
    }
}