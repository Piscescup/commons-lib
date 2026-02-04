package io.github.piscescup.interfaces.exfunction.failable;


import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Represents a function that accepts one argument and produces a result,
 * potentially throwing a checked exception.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #applyOrThrow(Object)}.
 * <p>
 * This interface is similar to {@link java.util.function.Function},
 * but allows the operation to throw a specified exception type.
 *
 * @param <X> the type of the input to the function
 * @param <Y> the type of the result of the function
 * @param <E> the type of exception that may be thrown by this function
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableFunction<X, Y, E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param x the function argument
     * @return the function result
     * @throws E if an error occurs during function execution
     */
    Y applyOrThrow(X x) throws E;

    /**
     * Returns a composed {@code FailableFunction} that first applies this function to its input, and then applies the
     * {@code after} function to the result. If evaluation of either function throws an exception, it is relayed to the
     * caller of the composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed {@code FailableFunction} that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default <V> FailableFunction<X, V, E> andThen(
        Function<? super Y, ? extends V> after
    ) {
        NullCheck.requireNonNull(after);
        return (final X x) -> after.apply(applyOrThrow(x));
    }

    /**
     * Returns a composed {@code FailableFunction} that first applies this function
     * to its input, and then applies the {@code after} function to the result.
     * <p>
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed {@code FailableFunction} that first applies this function
     *         and then applies the {@code after} function
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default <V> FailableFunction<X, V, E> andThen(
        FailableFunction<? super Y, ? extends V, E> after
    ) {
        NullCheck.requireNonNull(after);
        return (final X x) -> after.applyOrThrow(applyOrThrow(x));
    }

    /**
     * Creates a {@code FailableFunction} that always returns the same constant value,
     * regardless of the input it receives.
     *
     * @param <X> the type of the input to the function
     * @param <Y> the type of the result of the function
     * @param <E> the type of exception that may be thrown by this function, though in this case, no exception is expected to be thrown
     * @param constant the constant value to return
     * @return a {@code FailableFunction} which always returns the given constant value
     */
    static <X, Y, E extends Throwable> @NotNull FailableFunction<X, Y, E> constant(Y constant) {
        NullCheck.requireNonNull(constant);
        return x -> constant;
    }

    /**
     * Narrows the type of a given {@code FailableFunction} to the specified
     * parameter and return types, as well as the exception type.
     *
     * @param <X> the narrowed input type of the function
     * @param <Y> the narrowed output type of the function
     * @param <E> the narrowed type of the exception that may be thrown by the function
     * @param failableFunction the function to narrow
     * @return a {@code FailableFunction} with narrowed types
     * @throws NullPointerException if the provided function is null
     */
    @SuppressWarnings("unchecked")
    static <X, Y, E extends Throwable> FailableFunction<X, Y, E> narrow(FailableFunction<? super X, ? super Y, E> failableFunction) {
        NullCheck.requireNonNull(failableFunction);
        return (FailableFunction<X, Y, E>) failableFunction;
    }

    /**
     * Returns a {@code FailableFunction} that always returns its input argument.
     *
     * @param <Y> the type of the input and output to the function
     * @param <E> the type of exception that may be thrown by this function, though in this case, no exception is expected to be thrown
     * @return a {@code FailableFunction} which always returns the input argument it receives
     */
    @Contract(pure = true)
    static <Y,  E extends Throwable> @NotNull FailableFunction<Y, Y, E> identity() {
        return y -> y;
    }
}
