package io.github.piscescup.interfaces.exfunction.failable;

import io.github.piscescup.interfaces.exfunction.TriPredicate;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * Represents a predicate (boolean-valued function) of three arguments,
 * potentially throwing an exception.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #testOrThrow(Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument to the predicate
 * @param <X2> the type of the second argument to the predicate
 * @param <X3> the type of the third argument to the predicate
 * @param <E>  the type of exception that may be thrown by this predicate
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableTriPredicate<X1, X2, X3, E extends Throwable> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @return {@code true} if the input arguments match the predicate,
     *         otherwise {@code false}
     * @throws E if an error occurs during predicate evaluation
     */
    boolean testOrThrow(X1 x1, X2 x2, X3 x3) throws E;

    /**
     * Returns a composed {@code FailableTriPredicate} that represents a short-circuiting
     * logical AND of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code false},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of either predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ANDed with this predicate
     * @return a composed {@code FailableTriPredicate} that represents the
     *         short-circuiting logical AND of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableTriPredicate<X1, X2, X3, E> and(
        FailableTriPredicate<? super X1, ? super X2, ? super X3, E> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3) -> this.testOrThrow(x1, x2, x3) && other.testOrThrow(x1, x2, x3);
    }

    /**
     * Returns a composed {@code FailableTriPredicate} that represents a short-circuiting
     * logical AND of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code false},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of this predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ANDed with this predicate
     * @return a composed {@code FailableTriPredicate} that represents the
     *         short-circuiting logical AND of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableTriPredicate<X1, X2, X3, E> and(
        TriPredicate<? super X1, ? super X2, ? super X3> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3) -> this.testOrThrow(x1, x2, x3) && other.test(x1, x2, x3);
    }

    /**
     * Returns a composed {@code FailableTriPredicate} that represents a short-circuiting
     * logical OR of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code true},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of either predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ORed with this predicate
     * @return a composed {@code FailableTriPredicate} that represents the
     *         short-circuiting logical OR of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableTriPredicate<X1, X2, X3, E> or(
        FailableTriPredicate<? super X1, ? super X2, ? super X3, E> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3) -> this.testOrThrow(x1, x2, x3) || other.testOrThrow(x1, x2, x3);
    }

    /**
     * Returns a composed {@code FailableTriPredicate} that represents a short-circuiting
     * logical OR of this predicate and another.
     * <p>
     * When evaluating the composed predicate, if this predicate is {@code true},
     * then the {@code other} predicate is not evaluated.
     * <p>
     * If evaluation of this predicate throws an exception, it is relayed to
     * the caller of the composed predicate.
     *
     * @param other a predicate that will be logically ORed with this predicate
     * @return a composed {@code FailableTriPredicate} that represents the
     *         short-circuiting logical OR of this predicate and the {@code other}
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default FailableTriPredicate<X1, X2, X3, E> or(
        TriPredicate<? super X1, ? super X2, ? super X3> other
    ) {
        NullCheck.requireNonNull(other);
        return (x1, x2, x3) -> this.testOrThrow(x1, x2, x3) || other.test(x1, x2, x3);
    }

    /**
     * Returns a predicate that represents the logical negation of this predicate.
     * <p>
     * If evaluation of this predicate throws an exception, it is relayed to
     * the caller of the negated predicate.
     *
     * @return a predicate that represents the logical negation of this predicate
     */
    default FailableTriPredicate<X1, X2, X3, E> negate() {
        return (x1, x2, x3) -> !testOrThrow(x1, x2, x3);
    }

    /**
     * Returns a {@code FailableTriPredicate} that always evaluates to {@code true}.
     *
     * @param <X1> the type of the first argument to the predicate
     * @param <X2> the type of the second argument to the predicate
     * @param <X3> the type of the third argument to the predicate
     * @param <E>  the type of exception that may be thrown by the predicate
     * @return a predicate that always returns {@code true}
     */
    @Contract(pure = true)
    static <X1, X2, X3, E extends Throwable> @NotNull FailableTriPredicate<X1, X2, X3, E> always() {
        return (x1, x2, x3) -> true;
    }

    /**
     * Returns a {@code FailableTriPredicate} that always evaluates to {@code false}.
     *
     * @param <X1> the type of the first argument to the predicate
     * @param <X2> the type of the second argument to the predicate
     * @param <X3> the type of the third argument to the predicate
     * @param <E>  the type of exception that may be thrown by the predicate
     * @return a predicate that always returns {@code false}
     */
    @Contract(pure = true)
    static <X1, X2, X3, E extends Throwable> @NotNull FailableTriPredicate<X1, X2, X3, E> never() {
        return (x1, x2, x3) -> false;
    }

    /**
     * Narrows a {@code FailableTriPredicate} with broader generic bounds to a more
     * specific type.
     * <p>
     * This method performs an unchecked cast and is intended for use in situations
     * where the type safety is guaranteed by the caller.
     *
     * @param predicate the predicate to be narrowed
     * @param <X1> the type of the first argument to the predicate
     * @param <X2> the type of the second argument to the predicate
     * @param <X3> the type of the third argument to the predicate
     * @param <E>  the type of exception that may be thrown by the predicate
     * @return the given predicate, narrowed to the desired generic type
     * @throws NullPointerException if {@code predicate} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, X3, E extends Throwable> FailableTriPredicate<X1, X2, X3, E> narrow(
        FailableTriPredicate<? super X1, ? super X2, ? super X3, ? super E> predicate
    ) {
        NullCheck.requireNonNull(predicate);
        return (FailableTriPredicate<X1, X2, X3, E>) predicate;
    }
}
