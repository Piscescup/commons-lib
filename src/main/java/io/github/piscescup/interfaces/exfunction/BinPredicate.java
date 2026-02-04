package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.entries.BinEntry;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a predicate (boolean-valued function) of two arguments.  This is
 * the two-arity specialization of {@link Predicate}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object, Object)}.
 *
 * @param <X1> the type of the first argument to the predicate
 * @param <X2> the type of the second argument the predicate
 *
 * @since 1.0.0
 */
@FunctionalInterface
public interface BinPredicate<X1, X2> extends BiPredicate<X1, X2> {
    /**
     * Partially applies the first argument to this predicate.
     *
     * <p>
     * The returned {@link Predicate} accepts the second argument and evaluates
     * this predicate using the bound first argument.
     *
     * @param x1 the first argument to bind
     * @return a predicate of one argument
     * @throws NullPointerException if {@code x1} is {@code null}
     */
    default Predicate<X2> test(X1 x1) {
        NullCheck.requireNonNull(x1);
        return x2 -> test(x1, x2);
    }

    /**
     * Returns a new {@code BinPredicate} with its argument order reversed.
     *
     * <p>
     * The returned predicate evaluates {@code test(x1, x2)} as
     * {@code test(x2, x1)}.
     *
     * @return a predicate with reversed argument order
     */
    default BinPredicate<X2, X1> reversed() {
        return (x2, x1) -> test(x1, x2);
    }

    /**
     * Returns the curried form of this predicate.
     *
     * <p>
     * The returned function first accepts the first argument and then returns
     * a {@link Predicate} that accepts the second argument.
     *
     * @return a curried representation of this predicate
     */
    default Function<X1, Predicate<X2>> curried() {
        return x1 -> x2 -> test(x1, x2);
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
     * @return a memoized binary predicate
     */
    default BinPredicate<X1, X2> memorized() {
        if (isMemorized()) return this;

        final Map<BinEntry<X1, X2>, Boolean> cache = new ConcurrentHashMap<>();

        return (BinPredicate<X1, X2> & Memorized) (x1, x2) -> {
            final BinEntry<X1, X2> entry = new BinEntry<>(x1, x2);
            return cache.computeIfAbsent(entry, e -> test(e.x1(), e.x2()));
        };
    }

    /**
     * Returns a predicate that always evaluates to {@code true}.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @return a predicate that always returns {@code true}
     */
    static <X1, X2> BinPredicate<X1, X2> always() {
        return (x1, x2) -> true;
    }

    /**
     * Returns a predicate that always evaluates to {@code false}.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @return a predicate that always returns {@code false}
     */
    static <X1, X2> BinPredicate<X1, X2> never() {
        return (x1, x2) -> false;
    }

    /**
     * Narrows the given binary predicate to a specific type.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param binPredicate the binary predicate to narrow, must not be {@code null}
     * @return a narrowed version of the binary predicate
     * @throws NullPointerException if the provided binary predicate is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2> BinPredicate<X1, X2> narrow(BinPredicate<? super X1, ? super X2> binPredicate) {
        NullCheck.requireNonNull(binPredicate);
        return (BinPredicate<X1, X2>) binPredicate;
    }
}
