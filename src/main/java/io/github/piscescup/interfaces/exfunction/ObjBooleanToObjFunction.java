package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.entries.BinEntry;
import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.interfaces.exfunction.primitive.BooleanFunction;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Represents a function that accepts an object-valued argument and a
 * {@code boolean}-valued argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object, boolean)}.
 *
 * @param <X> the type of the object argument
 * @param <Y> the type of the result
 *
 * @since 1.2.0
 */
@FunctionalInterface
public interface ObjBooleanToObjFunction<X, Y> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x the object argument
     * @param value the {@code boolean}-valued argument
     * @return the function result
     */
    Y apply(X x, boolean value);

    /**
     * Partially applies the object argument to this function.
     *
     * @param x the object argument to bind
     * @return a function accepting the remaining {@code boolean} value
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default BooleanFunction<Y> apply(X x) {
        NullCheck.requireNonNull(x);
        return value -> apply(x, value);
    }

    /**
     * Returns a composed function that first applies this function and then
     * applies the {@code after} function to its result.
     *
     * @param after the function to apply after this function
     * @param <V> the result type of the composed function
     * @return the composed function
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default <V> ObjBooleanToObjFunction<X, V> andThen(
        Function<? super Y, ? extends V> after
    ) {
        NullCheck.requireNonNull(after);
        return (x, value) -> after.apply(apply(x, value));
    }

    /**
     * Returns the curried form of this function.
     *
     * @return a function that accepts the object argument and returns a
     *         function accepting the primitive argument
     */
    default Function<X, BooleanFunction<Y>> curried() {
        return x -> value -> apply(x, value);
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
    default ObjBooleanToObjFunction<X, Y> memorized() {
        if (isMemorized()) return this;

        final Map<BinEntry<X, Boolean>, Y> cache = new ConcurrentHashMap<>();
        return (ObjBooleanToObjFunction<X, Y> & Memorized) (x, value) -> {
            final BinEntry<X, Boolean> entry = new BinEntry<>(x, value);
            return cache.computeIfAbsent(
                entry,
                e -> apply(e.x1(), e.x2())
            );
        };
    }

    /**
     * Returns a function that always returns the given constant.
     *
     * @param constant the constant result
     * @param <X> the type of the object argument
     * @param <Y> the type of the result
     * @return a constant function
     * @throws NullPointerException if {@code constant} is {@code null}
     */
    static <X, Y> ObjBooleanToObjFunction<X, Y> constant(Y constant) {
        NullCheck.requireNonNull(constant);
        return (x, value) -> constant;
    }

    /**
     * Returns the given function.
     *
     * @param function the function to return
     * @param <X> the type of the object argument
     * @param <Y> the type of the result
     * @return the given function
     * @throws NullPointerException if {@code function} is {@code null}
     */
    static <X, Y> ObjBooleanToObjFunction<X, Y> of(
        ObjBooleanToObjFunction<X, Y> function
    ) {
        NullCheck.requireNonNull(function);
        return function;
    }

    /**
     * Narrows a function with broader input and narrower output bounds.
     *
     * @param function the function to narrow
     * @param <X> the target object argument type
     * @param <Y> the target result type
     * @return the narrowed function
     * @throws NullPointerException if {@code function} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X, Y> ObjBooleanToObjFunction<X, Y> narrow(
        ObjBooleanToObjFunction<? super X, ? extends Y> function
    ) {
        NullCheck.requireNonNull(function);
        return (ObjBooleanToObjFunction<X, Y>) function;
    }
}

