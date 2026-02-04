package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.entries.BinEntry;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is the two-arity specialization of {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <X1> the type of the first argument to the function
 * @param <X2> the type of the second argument to the function
 * @param <Y> the type of the result of the function
 *
 * @since 1.8
 */
@FunctionalInterface
public interface BinFunction<X1, X2, Y> extends BiFunction<X1, X2, Y> {

    /**
     * Partially applies the first argument to this function.
     *
     * <p>
     * The returned {@link Function} accepts the second argument and produces
     * the final result.
     *
     * @param x1 the first argument to bind
     * @return a function of one argument producing the result
     * @throws NullPointerException if {@code x1} is {@code null}
     */
    default Function<X2, Y> apply(X1 x1) {
        NullCheck.requireNonNull(x1);
        return x2 -> apply(x1, x2);
    }

    /**
     * Returns a new {@code BinFunction} with its argument order reversed.
     *
     * <p>
     * The returned function applies {@code (x2, x1)} to this function
     * as {@code apply(x1, x2)}.
     *
     * @return a reversed-argument binary function
     */
    default BinFunction<X2, X1, Y> reversed() {
        return (x2, x1) -> apply(x1, x2);
    }

    /**
     * Returns the curried form of this binary function.
     *
     * <p>
     * The returned function first accepts {@code X1}, then returns another
     * function that accepts {@code X2} and produces the result.
     *
     * @return a curried representation of this function
     */
    default Function<X1, Function<X2, Y>> curried() {
        return x1 -> x2 -> apply(x1, x2);
    }

    /**
     * Indicates whether this function is memoized.
     *
     * <p>
     * A memoized function caches computation results based on input arguments.
     *
     * @return {@code true} if this function is memoized; {@code false} otherwise
     */
    default boolean isMemorized() {
        return this instanceof Memorized;
    }

    /**
     * Returns a memoized version of this function.
     *
     * <p>
     * The returned function caches results in a thread-safe map keyed by
     * the pair of input arguments. Subsequent calls with the same arguments
     * will return the cached result instead of recomputing it.
     *
     * <p>
     * If this function is already memoized, it is returned directly.
     *
     * @return a memoized binary function
     */
    default BinFunction<X1, X2, Y> memorized() {
        if (isMemorized()) return this;

        final Map<BinEntry<X1, X2>, Y> cache = new ConcurrentHashMap<>();
        return (BinFunction<X1, X2, Y> & Memorized) (x1, x2) -> {
            final BinEntry<X1, X2> entry = new BinEntry<>(x1, x2);
            return cache.computeIfAbsent(entry, e -> apply(e.x1(), e.x2()));
        };
    }

    /**
     * Returns a binary function that always returns the given constant value.
     *
     * <p>
     * The input arguments are ignored.
     *
     * @param constant the constant value to return
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <Y>  the type of the result
     * @return a constant binary function
     * @throws NullPointerException if {@code constant} is {@code null}
     */
    static <X1, X2, Y> BinFunction<X1, X2, Y> constant(Y constant) {
        NullCheck.requireNonNull(constant);
        return (x1, x2) -> constant;
    }

    /**
     * Returns the given {@code BinFunction} instance.
     *
     * <p>
     * This method is primarily intended for API symmetry and readability.
     *
     * @param binFunc the binary function
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <Y>  the type of the result
     * @return the given function
     * @throws NullPointerException if {@code binFunc} is {@code null}
     */
    static <X1, X2, Y> BinFunction<X1, X2, Y> of(BinFunction<X1, X2, Y> binFunc) {
        NullCheck.requireNonNull(binFunc);
        return binFunc;
    }

    /**
     * Narrows a {@code BinFunction} with broader generic bounds to a more
     * specific type.
     *
     * <p>
     * This method performs an unchecked cast and should be used with caution.
     *
     * @param binFunc the binary function to narrow
     * @param <X1> the target first argument type
     * @param <X2> the target second argument type
     * @param <Y>  the target result type
     * @return the narrowed binary function
     * @throws NullPointerException if {@code binFunc} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, Y> BinFunction<X1, X2, Y> narrow(
        BinFunction<? super X1, ? super X2, ? super Y> binFunc
    ) {
        NullCheck.requireNonNull(binFunc);
        return (BinFunction<X1, X2, Y>) binFunc;
    }
}
