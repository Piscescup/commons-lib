package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.entries.BinEntry;
import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.interfaces.exfunction.primitive.BooleanPredicate;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Represents a predicate of an object-valued argument and a
 * {@code boolean}-valued argument.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object, boolean)}.
 *
 * @param <X> the type of the object argument
 *
 * @since 1.2.0
 */
@FunctionalInterface
public interface ObjBooleanPredicate<X> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x the object argument
     * @param value the {@code boolean}-valued argument
     * @return {@code true} if the arguments match the predicate
     */
    boolean test(X x, boolean value);

    /**
     * Partially applies the object argument to this predicate.
     *
     * @param x the object argument to bind
     * @return a predicate accepting the remaining primitive value
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default BooleanPredicate test(X x) {
        NullCheck.requireNonNull(x);
        return value -> test(x, value);
    }

    /**
     * Returns a short-circuiting logical AND of this predicate and another.
     *
     * @param other the predicate combined with this predicate
     * @return the composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default ObjBooleanPredicate<X> and(ObjBooleanPredicate<? super X> other) {
        NullCheck.requireNonNull(other);
        return (x, value) -> test(x, value) && other.test(x, value);
    }

    /**
     * Returns a short-circuiting logical OR of this predicate and another.
     *
     * @param other the predicate combined with this predicate
     * @return the composed predicate
     * @throws NullPointerException if {@code other} is {@code null}
     */
    default ObjBooleanPredicate<X> or(ObjBooleanPredicate<? super X> other) {
        NullCheck.requireNonNull(other);
        return (x, value) -> test(x, value) || other.test(x, value);
    }

    /**
     * Returns the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default ObjBooleanPredicate<X> negate() {
        return (x, value) -> !test(x, value);
    }

    /**
     * Returns the curried form of this predicate.
     *
     * @return a function that accepts the object argument and returns a
     *         predicate accepting the primitive argument
     */
    default Function<X, BooleanPredicate> curried() {
        return x -> value -> test(x, value);
    }

    /**
     * Indicates whether this predicate is memoized.
     *
     * @return {@code true} if this predicate is memoized; {@code false} otherwise
     */
    default boolean isMemorized() {
        return this instanceof Memorized;
    }

    /**
     * Returns a memoized version of this predicate.
     *
     * @return a memoized predicate
     */
    default ObjBooleanPredicate<X> memorized() {
        if (isMemorized()) return this;

        final Map<BinEntry<X, Boolean>, Boolean> cache =
            new ConcurrentHashMap<>();

        return (ObjBooleanPredicate<X> & Memorized) (x, value) -> {
            final BinEntry<X, Boolean> entry = new BinEntry<>(x, value);
            return cache.computeIfAbsent(
                entry,
                e -> test(e.x1(), e.x2())
            );
        };
    }

    /**
     * Returns a predicate that always evaluates to {@code true}.
     *
     * @param <X> the type of the object argument
     * @return a predicate that always returns {@code true}
     */
    static <X> ObjBooleanPredicate<X> always() {
        return (x, value) -> true;
    }

    /**
     * Returns a predicate that always evaluates to {@code false}.
     *
     * @param <X> the type of the object argument
     * @return a predicate that always returns {@code false}
     */
    static <X> ObjBooleanPredicate<X> never() {
        return (x, value) -> false;
    }

    /**
     * Narrows the given predicate to a specific object argument type.
     *
     * @param predicate the predicate to narrow
     * @param <X> the target object argument type
     * @return the narrowed predicate
     * @throws NullPointerException if {@code predicate} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X> ObjBooleanPredicate<X> narrow(
        ObjBooleanPredicate<? super X> predicate
    ) {
        NullCheck.requireNonNull(predicate);
        return (ObjBooleanPredicate<X>) predicate;
    }
}

