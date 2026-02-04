package io.github.piscescup.interfaces.exfunction;


import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents an operation that accepts five input arguments and returns no result.
 * This is the five-arity specialization of {@link Consumer}.
 * Unlike most other functional interfaces, {@code QuinConsumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object, Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument to the operation
 * @param <X2> the type of the second argument to the operation
 * @param <X3> the type of the third argument to the operation
 * @param <X4> the type of the fourth argument to the operation
 * @param <X5> the type of the fifth argument to the operation
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface QuinConsumer<X1, X2, X3, X4, X5> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     * @param x5 the fifth input argument
     */
    void accept(X1 x1, X2 x2, X3 x3, X4 x4, X5 x5);

    /**
     * Partially applies the first argument to this consumer.
     *
     * <p>
     * The returned {@link QuadConsumer} accepts the remaining four arguments and
     * performs this consumer using the bound first argument.
     *
     * @param x1 the first argument to bind
     * @return a quad-consumer of the remaining arguments
     * @throws NullPointerException if {@code x1} is {@code null}
     */
    default QuadConsumer<X2, X3, X4, X5> accept(X1 x1) {
        NullCheck.requireNonNull(x1);
        return (x2, x3, x4, x5) -> accept(x1, x2, x3, x4, x5);
    }

    /**
     * Partially applies the first and second arguments to this consumer.
     *
     * <p>
     * The returned {@link TriConsumer} accepts the remaining three arguments and
     * performs this consumer using the bound first and second arguments.
     *
     * @param x1 the first argument to bind
     * @param x2 the second argument to bind
     * @return a tri-consumer of the remaining arguments
     * @throws NullPointerException if {@code x1} or {@code x2} is {@code null}
     */
    default TriConsumer<X3, X4, X5> accept(X1 x1, X2 x2) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        return (x3, x4, x5) -> accept(x1, x2, x3, x4, x5);
    }

    /**
     * Partially applies the first three arguments to this consumer.
     *
     * <p>
     * The returned {@link BinConsumer} accepts the remaining two arguments and
     * performs this consumer using the bound first three arguments.
     *
     * @param x1 the first argument to bind
     * @param x2 the second argument to bind
     * @param x3 the third argument to bind
     * @return a bi-consumer of the remaining arguments
     * @throws NullPointerException if any argument is {@code null}
     */
    default BinConsumer<X4, X5> accept(X1 x1, X2 x2, X3 x3) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        NullCheck.requireNonNull(x3);
        return (x4, x5) -> accept(x1, x2, x3, x4, x5);
    }

    /**
     * Partially applies the first four arguments to this consumer.
     *
     * <p>
     * The returned {@link Consumer} accepts the remaining argument and
     * performs this consumer using the bound first four arguments.
     *
     * @param x1 the first argument to bind
     * @param x2 the second argument to bind
     * @param x3 the third argument to bind
     * @param x4 the fourth argument to bind
     * @return a consumer of the remaining argument
     * @throws NullPointerException if any argument is {@code null}
     */
    default Consumer<X5> accept(X1 x1, X2 x2, X3 x3, X4 x4) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        NullCheck.requireNonNull(x3);
        NullCheck.requireNonNull(x4);
        return (x5) -> accept(x1, x2, x3, x4, x5);
    }



    /**
     * Returns a composed {@code QuinConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation. If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code QuinConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default QuinConsumer<X1, X2, X3, X4, X5> andThen(QuinConsumer<? super X1, ? super X2, ? super X3, ? super X4, ? super X5> after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4, x5) -> {
            accept(x1, x2, x3, x4, x5);
            after.accept(x1, x2, x3, x4, x5);
        };
    }

    /**
     * Returns a curried version of this {@code QuinConsumer} as a function that
     * accepts one argument at a time, returning functions that accept the next
     * arguments until all arguments are provided and the operation is performed.
     *
     * @return a function that takes the first argument and returns a chain of
     * functions ending in a consumer for the fifth argument
     */
    default Function<X1, Function<X2, Function<X3, Function<X4, Consumer<X5>>>>> curried() {
        return x1 -> x2 -> x3 -> x4 -> x5 -> this.accept(x1, x2, x3, x4, x5);
    }

    /**
     * Returns a new {@code QuinConsumer} whose parameters are in reverse order compared to this one.
     * The returned consumer will accept its arguments in the order (x1, x2, x3, x4, x5) and pass them
     * to the original consumer in the reversed order (x5, x4, x3, x2, x1).
     *
     * @return a new {@code QuinConsumer} with reversed parameter order
     */
    default QuinConsumer<X5, X4, X3, X2, X1> reversed() {
        return (x5, x4, x3, x2, x1) -> accept(x1, x2, x3, x4, x5);
    }

    /**
     * Returns a {@code QuinConsumer} that performs no operation.
     *
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param <X3> the type of the third argument to the operation
     * @param <X4> the type of the fourth argument to the operation
     * @param <X5> the type of the fifth argument to the operation
     * @return a {@code QuinConsumer} that does nothing when its {@link #accept} method is called
     */
    @Contract(pure = true)
    static <X1, X2, X3, X4, X5> @NotNull QuinConsumer<X1, X2, X3, X4, X5> empty() {
        return (x1, x2, x3, x4, x5) -> {};
    }

    /**
     * Narrows the type of the given {@code QuinConsumer} to the specified types.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <X3> the type of the third argument
     * @param <X4> the type of the fourth argument
     * @param <X5> the type of the fifth argument
     * @param quinConsumer the consumer to narrow, must not be null
     * @return a narrowed version of the input {@code QuinConsumer}
     * @throws NullPointerException if the provided {@code quinConsumer} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, X3, X4, X5> QuinConsumer<X1, X2, X3, X4, X5> narrow(QuinConsumer<? super X1, ? super X2, ? super X3, ? super X4, ? super X5> quinConsumer) {
        NullCheck.requireNonNull(quinConsumer);
        return (QuinConsumer<X1, X2, X3, X4, X5>) quinConsumer;
    }

    /**
     * Schedules a collection of {@code QuinConsumer} instances to be executed in sequence on the given arguments.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <X3> the type of the third argument
     * @param <X4> the type of the fourth argument
     * @param <X5> the type of the fifth argument
     * @param consumers a collection of consumers to be scheduled, must not be null or contain null elements
     * @return a composed {@code QuinConsumer} that will execute all the given consumers in sequence with the provided arguments
     * @throws NullPointerException if the provided collection or any of its elements is null
     */
    static <X1, X2, X3, X4, X5> QuinConsumer<X1, X2, X3, X4, X5> schedule(
        final Collection<? extends QuinConsumer<? super X1, ? super X2, ? super X3, ? super X4, ? super X5>> consumers
    ) {
        NullCheck.requireAllNonNull(consumers);
        return (x1, x2, x3, x4, x5) -> {
            for (QuinConsumer<? super X1, ? super X2, ? super X3, ? super X4, ? super X5> consumer : consumers) {
                consumer.accept(x1, x2, x3, x4, x5);
            }
        };
    }
}