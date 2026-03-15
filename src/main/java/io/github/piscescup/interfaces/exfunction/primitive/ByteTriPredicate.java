package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a predicate (boolean-valued function) of three {@code byte}-valued arguments.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(byte, byte, byte)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ByteTriPredicate {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(byte x1, byte x2, byte x3);

    /**
     * Returns a composed predicate representing the logical AND of this
     * predicate and another.
     *
     * @param other the predicate to be logically-ANDed with this predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default ByteTriPredicate and(ByteTriPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3) -> test(x1, x2, x3) && other.test(x1, x2, x3);
    }

    /**
     * Returns a predicate representing the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default ByteTriPredicate negate() {
        return (x1, x2, x3) -> !test(x1, x2, x3);
    }

    /**
     * Returns a composed predicate representing the logical OR of this
     * predicate and another.
     *
     * @param other the predicate to be logically-ORed with this predicate
     * @return a composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default ByteTriPredicate or(ByteTriPredicate other) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3) -> test(x1, x2, x3) || other.test(x1, x2, x3);
    }
}
