package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.entries.BinEntry;
import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.interfaces.exfunction.primitive.ByteFunction;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Represents a function that accepts an object-valued argument and a
 * {@code byte}-valued argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object, byte)}.
 *
 * @param <X> the type of the object argument
 * @param <Y> the type of the result
 *
 * @since 1.2.0
 */
@FunctionalInterface
public interface ObjByteToObjFunction<X, Y> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x the object argument
     * @param value the {@code byte}-valued argument
     * @return the function result
     */
    Y apply(X x, byte value);

    /**
     * Partially applies the object argument to this function.
     *
     * @param x the object argument to bind
     * @return a function accepting the remaining {@code byte} value
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default ByteFunction<Y> apply(X x) {
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
    default <V> ObjByteToObjFunction<X, V> andThen(
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
    default Function<X, ByteFunction<Y>> curried() {
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
    default ObjByteToObjFunction<X, Y> memorized() {
        if (isMemorized()) return this;

        final Map<BinEntry<X, Byte>, Y> cache = new ConcurrentHashMap<>();
        return (ObjByteToObjFunction<X, Y> & Memorized) (x, value) -> {
            final BinEntry<X, Byte> entry = new BinEntry<>(x, value);
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
    static <X, Y> ObjByteToObjFunction<X, Y> constant(Y constant) {
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
    static <X, Y> ObjByteToObjFunction<X, Y> of(
        ObjByteToObjFunction<X, Y> function
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
    static <X, Y> ObjByteToObjFunction<X, Y> narrow(
        ObjByteToObjFunction<? super X, ? extends Y> function
    ) {
        NullCheck.requireNonNull(function);
        return (ObjByteToObjFunction<X, Y>) function;
    }
}

