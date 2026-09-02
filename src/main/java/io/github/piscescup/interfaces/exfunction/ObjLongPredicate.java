package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.entries.BinEntry;
import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.LongPredicate;

/**
 * Represents a predicate of an object-valued argument and a
 * {@code long}-valued argument.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object, long)}.
 *
 * @param <X> the type of the object argument
 *
 * @since 1.2.0
 */
@FunctionalInterface
public interface ObjLongPredicate<X> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param x the object argument
     * @param value the {@code long}-valued argument
     * @return {@code true} if the arguments match the predicate
     */
    boolean test(X x, long value);

    /**
     * Partially applies the object argument to this predicate.
     *
     * @param x the object argument to bind
     * @return a predicate accepting the remaining primitive value
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default LongPredicate test(X x) {
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
    default ObjLongPredicate<X> and(ObjLongPredicate<? super X> other) {
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
    default ObjLongPredicate<X> or(ObjLongPredicate<? super X> other) {
        NullCheck.requireNonNull(other);
        return (x, value) -> test(x, value) || other.test(x, value);
    }

    /**
     * Returns the logical negation of this predicate.
     *
     * @return the negated predicate
     */
    default ObjLongPredicate<X> negate() {
        return (x, value) -> !test(x, value);
    }

    /**
     * Returns the curried form of this predicate.
     *
     * @return a function that accepts the object argument and returns a
     *         predicate accepting the primitive argument
     */
    default Function<X, LongPredicate> curried() {
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
    default ObjLongPredicate<X> memorized() {
        if (isMemorized()) return this;

        final Map<BinEntry<X, Long>, Boolean> cache =
            new ConcurrentHashMap<>();

        return (ObjLongPredicate<X> & Memorized) (x, value) -> {
            final BinEntry<X, Long> entry = new BinEntry<>(x, value);
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
    static <X> ObjLongPredicate<X> always() {
        return (x, value) -> true;
    }

    /**
     * Returns a predicate that always evaluates to {@code false}.
     *
     * @param <X> the type of the object argument
     * @return a predicate that always returns {@code false}
     */
    static <X> ObjLongPredicate<X> never() {
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
    static <X> ObjLongPredicate<X> narrow(
        ObjLongPredicate<? super X> predicate
    ) {
        NullCheck.requireNonNull(predicate);
        return (ObjLongPredicate<X>) predicate;
    }
}
