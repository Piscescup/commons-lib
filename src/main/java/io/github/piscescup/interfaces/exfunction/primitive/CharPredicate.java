package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a predicate (boolean-valued function) of one {@code char}-valued argument.
 *
 * <p>This is the {@code char}-consuming primitive specialization of
 * {@link java.util.function.Predicate}.</p>
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(char)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface CharPredicate {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(char value);

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.
     *
     * <p>If this predicate returns {@code false}, the {@code other} predicate
     * will not be evaluated.</p>
     *
     * @param other the predicate to be logically-ANDed with this predicate
     * @return a composed predicate representing the logical AND
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default CharPredicate and(CharPredicate other) {
        NullCheck.requireNonNull(other);
        return value -> test(value) && other.test(value);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate representing the logical negation
     */
    default CharPredicate negate() {
        return value -> !test(value);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.
     *
     * <p>If this predicate returns {@code true}, the {@code other} predicate
     * will not be evaluated.</p>
     *
     * @param other the predicate to be logically-ORed with this predicate
     * @return a composed predicate representing the logical OR
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default CharPredicate or(CharPredicate other) {
        NullCheck.requireNonNull(other);
        return value -> test(value) || other.test(value);
    }
}
