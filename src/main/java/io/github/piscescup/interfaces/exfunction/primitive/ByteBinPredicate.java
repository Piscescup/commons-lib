package io.github.piscescup.interfaces.exfunction.primitive;

/**
 * Represents a predicate (boolean-valued function) of two {@code byte}-valued arguments.
 *
 * <p>This is the {@code byte}-consuming primitive specialization of
 * a two-argument predicate.</p>
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(byte, byte)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ByteBinPredicate {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(byte x1, byte x2);

    /**
     * Returns a composed predicate representing the logical AND of this
     * predicate and another.
     *
     * @param other the other predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default ByteBinPredicate and(ByteBinPredicate other) {
        java.util.Objects.requireNonNull(other);
        return (x1, x2) -> test(x1, x2) && other.test(x1, x2);
    }

    /**
     * Returns a predicate representing the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default ByteBinPredicate negate() {
        return (x1, x2) -> !test(x1, x2);
    }

    /**
     * Returns a composed predicate representing the logical OR of this
     * predicate and another.
     *
     * @param other the other predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default ByteBinPredicate or(ByteBinPredicate other) {
        java.util.Objects.requireNonNull(other);
        return (x1, x2) -> test(x1, x2) || other.test(x1, x2);
    }
}