package io.github.piscescup.interfaces.exfunction.failable;

import io.github.piscescup.interfaces.exfunction.QuadPredicate;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents a five-argument predicate (boolean-valued function),
 * potentially throwing an exception..
 * This is the five-arity specialization of {@link Predicate}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #testOrThrow(Object, Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument to the predicate
 * @param <X2> the type of the second argument to the predicate
 * @param <X3> the type of the third argument to the predicate
 * @param <X4> the type of the fourth argument to the predicate
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableQuadPredicate<X1, X2, X3, X4, E extends Throwable> {

    /**
     * Evaluates this predicate on the given arguments, possibly throwing an exception.
     *
     * @param x1 the first input
     * @param x2 the second input
     * @param x3 the third input
     * @param x4 the fourth input
     * @return {@code true} if the inputs match the predicate, otherwise {@code false}
     * @throws E if the predicate evaluation fails
     */
    boolean testOrThrow(X1 x1, X2 x2, X3 x3, X4 x4) throws E;

    /**
     * Returns a composed {@code FailableQuadPredicate} that represents a short-circuiting logical AND
     * of this predicate and the {@code other} predicate.
     *
     * <p>If this predicate evaluates to {@code false}, {@code other} is not evaluated.
     *
     * @param other the other failable predicate to combine with
     * @return a composed predicate that represents {@code this && other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableQuadPredicate<X1, X2, X3, X4, E> and(
        FailableQuadPredicate<? super X1, ? super X2, ? super X3, ? super X4, E> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> this.testOrThrow(x1, x2, x3, x4) && other.testOrThrow(x1, x2, x3, x4);
    }

    /**
     * Returns a composed {@code FailableQuadPredicate} that represents a short-circuiting logical AND
     * of this predicate and the {@code other} non-throwing predicate.
     *
     * <p>If this predicate evaluates to {@code false}, {@code other} is not evaluated.
     *
     * @param other the other predicate to combine with
     * @return a composed predicate that represents {@code this && other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableQuadPredicate<X1, X2, X3, X4, E> and(
        QuadPredicate<? super X1, ? super X2, ? super X3, ? super X4> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> this.testOrThrow(x1, x2, x3, x4) && other.test(x1, x2, x3, x4);
    }

    /**
     * Returns a composed {@code FailableQuadPredicate} that represents a short-circuiting logical OR
     * of this predicate and the {@code other} predicate.
     *
     * <p>If this predicate evaluates to {@code true}, {@code other} is not evaluated.
     *
     * @param other the other failable predicate to combine with
     * @return a composed predicate that represents {@code this || other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableQuadPredicate<X1, X2, X3, X4, E> or(
        FailableQuadPredicate<? super X1, ? super X2, ? super X3, ? super X4, E> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> this.testOrThrow(x1, x2, x3, x4) || other.testOrThrow(x1, x2, x3, x4);
    }

    /**
     * Returns a composed {@code FailableQuadPredicate} that represents a short-circuiting logical OR
     * of this predicate and the {@code other} non-throwing predicate.
     *
     * <p>If this predicate evaluates to {@code true}, {@code other} is not evaluated.
     *
     * @param other the other predicate to combine with
     * @return a composed predicate that represents {@code this || other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableQuadPredicate<X1, X2, X3, X4, E> or(
        QuadPredicate<? super X1, ? super X2, ? super X3, ? super X4> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3, x4) -> this.testOrThrow(x1, x2, x3, x4) || other.test(x1, x2, x3, x4);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     *
     * @return a predicate that represents {@code !this}
     */
    default FailableQuadPredicate<X1, X2, X3, X4, E> negate() {
        return (x1, x2, x3, x4) -> !testOrThrow(x1, x2, x3, x4);
    }

    /**
     * Returns a predicate that always evaluates to {@code true}.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <X3> the type of the third argument
     * @param <X4> the type of the fourth argument
     * @param <E>  the throwable type
     * @return a predicate that always returns {@code true}
     */
    @Contract(pure = true)
    static <X1, X2, X3, X4, E extends Throwable> @NotNull FailableQuadPredicate<X1, X2, X3, X4, E> always() {
        return (x1, x2, x3, x4) -> true;
    }

    /**
     * Returns a predicate that always evaluates to {@code false}.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <X3> the type of the third argument
     * @param <X4> the type of the fourth argument
     * @param <E>  the throwable type
     * @return a predicate that always returns {@code false}
     */
    @Contract(pure = true)
    static <X1, X2, X3, X4, E extends Throwable> @NotNull FailableQuadPredicate<X1, X2, X3, X4, E> never() {
        return (x1, x2, x3, x4) -> false;
    }

    /**
     * Narrows a contravariant failable predicate to the desired type parameters.
     *
     * <p>This is an unchecked cast utility intended to improve type inference at call sites.
     *
     * @param predicate the predicate to narrow
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <X3> the type of the third argument
     * @param <X4> the type of the fourth argument
     * @param <E>  the throwable type
     * @return the same instance, casted to {@code FailableQuadPredicate<X1, X2, X3, X4, E>}
     * @throws NullPointerException if {@code predicate} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, X3, X4, E extends Throwable> FailableQuadPredicate<X1, X2, X3, X4, E> narrow(
        FailableQuadPredicate<? super X1, ? super X2, ? super X3, ? super X4, ? super E> predicate
    ) {
        NullCheck.requireNonNull(predicate);
        return (FailableQuadPredicate<X1, X2, X3, X4, E>) predicate;
    }
}
