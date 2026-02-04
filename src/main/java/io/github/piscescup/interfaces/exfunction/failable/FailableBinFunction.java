package io.github.piscescup.interfaces.exfunction.failable;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;


/**
 * Represents a function that accepts two arguments and produces a result,
 * potentially throwing an exception.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #applyOrThrow(Object, Object)}.
 *
 * @param <X1> the type of the first argument to the function
 * @param <X2> the type of the second argument to the function
 * @param <Y>  the type of the result of the function
 * @param <E>  the type of exception that may be thrown by this function
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableBinFunction<X1, X2, Y, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @return the function result
     * @throws E if an error occurs during function evaluation
     */
    Y applyOrThrow(X1 x1, X2 x2) throws E;

    /**
     * Returns a composed {@code FailableBinFunction} that first applies this function
     * to its input, and then applies the {@code after} function to the result.
     * <p>
     * If evaluation of either function throws an exception, it is relayed to the
     * caller of the composed function.
     *
     * @param after the function to apply after this function is applied
     * @param <V> the type of output of the {@code after} function, and of the composed function
     * @return a composed {@code FailableBinFunction} that first applies this function
     *         and then applies the {@code after} function
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default <V> FailableBinFunction<X1, X2, V, E> andThen(
        Function<? super Y, ? extends V> after
    ) {
        NullCheck.requireNonNull(after);
        return (x1, x2) -> after.apply(this.applyOrThrow(x1, x2));
    }

    /**
     * Returns a composed {@code FailableBinFunction} that first applies this function
     * to its input, and then applies the {@code after} function to the result.
     * <p>
     * If evaluation of either function throws an exception, it is relayed to the
     * caller of the composed function.
     *
     * @param after the function to apply after this function is applied
     * @param <V> the type of output of the {@code after} function, and of the composed function
     * @return a composed {@code FailableBinFunction} that first applies this function
     *         and then applies the {@code after} function
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default <V> FailableBinFunction<X1, X2, V, E> andThen(
        FailableFunction<? super Y, ? extends V, E> after
    ) {
        NullCheck.requireNonNull(after);
        return (x1, x2) -> after.applyOrThrow(this.applyOrThrow(x1, x2));
    }

    /**
     * Narrows a {@code FailableBinFunction} with broader generic bounds to a more
     * specific type.
     * <p>
     * This method performs an unchecked cast and is intended for use in situations
     * where the type safety is guaranteed by the caller.
     *
     * @param failableBinFunction the function to be narrowed
     * @param <X1> the type of the first argument to the function
     * @param <X2> the type of the second argument to the function
     * @param <Y>  the type of the result of the function
     * @param <E>  the type of exception that may be thrown by the function
     * @return the given function, narrowed to the desired generic type
     * @throws NullPointerException if {@code failableBinFunction} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, Y, E extends Throwable> FailableBinFunction<X1, X2, Y, E> narrow(
        FailableBinFunction<? super X1, ? super X2, ? super Y, E> failableBinFunction
    ) {
        NullCheck.requireNonNull(failableBinFunction);
        return (FailableBinFunction<X1, X2, Y, E>) failableBinFunction;
    }

    /**
     * Returns a {@code FailableBinFunction} that always returns the given constant value.
     *
     * @param constant the constant value to return
     * @param <X1> the type of the first argument to the function
     * @param <X2> the type of the second argument to the function
     * @param <Y>  the type of the constant and the result of the function
     * @param <E>  the type of exception that may be thrown by the function
     * @return a function that always returns {@code constant}
     * @throws NullPointerException if {@code constant} is {@code null}
     */
    static <X1, X2, Y, E extends Throwable> @NotNull FailableBinFunction<X1, X2, Y, E> constant(
        Y constant
    ) {
        NullCheck.requireNonNull(constant);
        return (x1, x2) -> constant;
    }
}
