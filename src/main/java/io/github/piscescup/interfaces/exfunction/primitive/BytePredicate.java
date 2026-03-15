package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents a predicate (boolean-valued function) of one {@code byte}-valued argument.
 *
 * <p>This is the {@code byte}-consuming primitive specialization of {@link java.util.function.Predicate}.</p>
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(byte)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface BytePredicate {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate
     */
    boolean test(byte value);

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND
     * of this predicate and another.
     *
     * @param other the other predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is null
     */
    default BytePredicate and(BytePredicate other) {
        NullCheck.requireNonNull(other);
        return v -> test(v) && other.test(v);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default BytePredicate negate() {
        return v -> !test(v);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical OR
     * of this predicate and another.
     *
     * @param other the other predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is null
     */
    default BytePredicate or(BytePredicate other) {
        NullCheck.requireNonNull(other);
        return v -> test(v) || other.test(v);
    }
}
