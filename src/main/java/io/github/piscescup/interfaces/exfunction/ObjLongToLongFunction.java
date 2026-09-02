package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.entries.BinEntry;
import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.interfaces.exfunction.primitive.LongUnaryOperator;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Represents a function that accepts an object-valued argument and a
 * {@code long}-valued argument and produces a {@code long}-valued
 * result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(Object, long)}.
 *
 * @param <X> the type of the object argument
 *
 * @since 1.2.0
 */
@FunctionalInterface
public interface ObjLongToLongFunction<X> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x the object argument
     * @param value the {@code long}-valued argument
     * @return the function result
     */
    long applyAsLong(X x, long value);

    /**
     * Applies this function and returns its result as a {@link Long}.
     *
     * @param x the object argument
     * @param value the {@code long}-valued argument
     * @return the boxed function result
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default Long boxedApply(X x, long value) {
        NullCheck.requireNonNull(x);
        return applyAsLong(x, value);
    }

    /**
     * Partially applies the object argument to this function.
     *
     * @param x the object argument to bind
     * @return an operator accepting the remaining primitive value
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default LongUnaryOperator apply(X x) {
        NullCheck.requireNonNull(x);
        return value -> applyAsLong(x, value);
    }

    /**
     * Returns a composed function that first applies this function and then
     * applies the {@code after} operator.
     *
     * @param after the operator to apply after this function
     * @return the composed function
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default ObjLongToLongFunction<X> andThen(LongUnaryOperator after) {
        NullCheck.requireNonNull(after);
        return (x, value) -> after.applyAsLong(applyAsLong(x, value));
    }

    /**
     * Returns the curried form of this function.
     *
     * @return a function that accepts the object argument and returns an
     *         operator accepting the primitive argument
     */
    default Function<X, LongUnaryOperator> curried() {
        return x -> value -> applyAsLong(x, value);
    }

    /**
     * Indicates whether this function is memoized.
     *
     * @return {@code true} if this function is memoized; {@code false} otherwise
     */
    default boolean isMemorized() {
        return this instanceof Memorized;
    }

    /**
     * Returns a memoized version of this function.
     *
     * @return a memoized function
     */
    default ObjLongToLongFunction<X> memorized() {
        if (isMemorized()) return this;

        final Map<BinEntry<X, Long>, Long> cache =
            new ConcurrentHashMap<>();

        return (ObjLongToLongFunction<X> & Memorized) (x, value) -> {
            final BinEntry<X, Long> entry = new BinEntry<>(x, value);
            return cache.computeIfAbsent(
                entry,
                e -> applyAsLong(e.x1(), e.x2())
            );
        };
    }

    /**
     * Returns a function that always returns the given constant.
     *
     * @param constant the constant result
     * @param <X> the type of the object argument
     * @return a constant function
     */
    static <X> ObjLongToLongFunction<X> constant(long constant) {
        return (x, value) -> constant;
    }

    /**
     * Returns the given function.
     *
     * @param function the function to return
     * @param <X> the type of the object argument
     * @return the given function
     * @throws NullPointerException if {@code function} is {@code null}
     */
    static <X> ObjLongToLongFunction<X> of(ObjLongToLongFunction<X> function) {
        NullCheck.requireNonNull(function);
        return function;
    }

    /**
     * Narrows the given function to a specific object argument type.
     *
     * @param function the function to narrow
     * @param <X> the target object argument type
     * @return the narrowed function
     * @throws NullPointerException if {@code function} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X> ObjLongToLongFunction<X> narrow(
        ObjLongToLongFunction<? super X> function
    ) {
        NullCheck.requireNonNull(function);
        return (ObjLongToLongFunction<X>) function;
    }
}

