package io.github.piscescup.interfaces.exfunction.failable;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents a predicate (boolean-valued function) of one argument,
 * potentially throwing an exception.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #testOrThrow(Object)}.
 * <p>
 * This interface is similar to {@link java.util.function.Predicate},
 * but allows the predicate evaluation to throw a specified exception type.
 *
 * @param <X> the type of the input to the predicate
 * @param <E> the type of exception that may be thrown by this predicate
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailablePredicate<X, E extends Throwable> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param x the input argument
     * @return {@code true} if the input argument matches the predicate,
     *         otherwise {@code false}
     * @throws E if an error occurs during predicate evaluation
     */
    boolean testOrThrow(X x) throws E;

    /**
     * Returns a composed {@code FailablePredicate} that represents a short-circuiting
     * logical AND of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code false},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of either predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ANDed with this predicate
     * @return a composed {@code FailablePredicate} that represents the
     *         short-circuiting logical AND of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailablePredicate<X, E> and(
        FailablePredicate<? super X, E> other
    ) {
        NullCheck.requireNonNull(other);
        return x -> this.testOrThrow(x) && other.testOrThrow(x);
    }

    /**
     * Returns a composed {@code FailablePredicate} that represents a short-circuiting
     * logical AND of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code false},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of either predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ANDed with this predicate
     * @return a composed {@code FailablePredicate} that represents the
     *         short-circuiting logical AND of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailablePredicate<X, E> and(
        Predicate<? super X> other
    ) {
        NullCheck.requireNonNull(other);
        return x -> this.testOrThrow(x) && other.test(x);
    }

    /**
     * Returns a composed {@code FailablePredicate} that represents a short-circuiting
     * logical OR of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code true},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of either predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ORed with this predicate
     * @return a composed {@code FailablePredicate} that represents the
     *         short-circuiting logical OR of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailablePredicate<X, E> or(
        FailablePredicate<? super X, E> other
    ) {
        NullCheck.requireNonNull(other);
        return x -> this.testOrThrow(x) || other.testOrThrow(x);
    }

    /**
     * Returns a composed {@code FailablePredicate} that represents a short-circuiting
     * logical OR of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code true},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of either predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ORed with this predicate
     * @return a composed {@code FailablePredicate} that represents the
     *         short-circuiting logical OR of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailablePredicate<X, E> or(
        Predicate<? super X> other
    ) {
        NullCheck.requireNonNull(other);
        return x -> this.testOrThrow(x) || other.test(x);
    }


    /**
     * Returns a predicate that represents the logical negation of this predicate.
     * <p>
     * If evaluation of this predicate throws an exception, it is relayed to
     * the caller of the negated predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    default FailablePredicate<X, E> negate() {
        return x -> !testOrThrow(x);
    }

    /**
     * Returns a {@code FailablePredicate} that always evaluates to {@code true}.
     *
     * @param <X> the type of the input to the predicate
     * @param <E> the type of exception that may be thrown by the predicate
     * @return a predicate that always returns {@code true}
     */
    @Contract(pure = true)
    static <X, E extends Throwable> @NotNull FailablePredicate<X, E> always() {
        return x -> true;
    }

    /**
     * Returns a {@code FailablePredicate} that always evaluates to {@code false}.
     *
     * @param <X> the type of the input to the predicate
     * @param <E> the type of exception that may be thrown by the predicate
     * @return a predicate that always returns {@code false}
     */
    @Contract(pure = true)
    static <X, E extends Throwable> @NotNull FailablePredicate<X, E> never() {
        return x -> false;
    }

    /**
     * Narrows a {@code FailablePredicate} with broader generic bounds to a more
     * specific type.
     * <p>
     * This method performs an unchecked cast and is intended for use in situations
     * where the type safety is guaranteed by the caller.
     *
     * @param failablePredicate the predicate to be narrowed
     * @param <X> the type of the input to the predicate
     * @param <E> the type of exception that may be thrown by the predicate
     * @return the given predicate, narrowed to the desired generic type
     * @throws NullPointerException if {@code failablePredicate} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X, E extends Throwable> FailablePredicate<X, E> narrow(
        FailablePredicate<? super X, ? super E> failablePredicate
    ) {
        NullCheck.requireNonNull(failablePredicate);
        return (FailablePredicate<X, E>) failablePredicate;
    }
}
