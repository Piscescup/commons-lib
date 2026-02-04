package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents an operation that accepts four input arguments and returns no result.
 * This is the four-arity specialization of {@link Consumer}.
 * Unlike most other functional interfaces, {@code QuadConsumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument to the operation
 * @param <X2> the type of the second argument to the operation
 * @param <X3> the type of the third argument to the operation
 * @param <X4> the type of the fourth argument to the operation
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface QuadConsumer<X1, X2, X3, X4> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     */
    void accept(X1 x1, X2 x2, X3 x3, X4 x4);

    /**
     * Partially applies the first argument to this consumer.
     *
     * <p>
     * The returned {@link TriConsumer} accepts the remaining three arguments and
     * performs this consumer using the bound first argument.
     *
     * @param x1 the first argument to bind
     * @return a tri-consumer of the remaining arguments
     * @throws NullPointerException if {@code x1} is {@code null}
     */
    default TriConsumer<X2, X3, X4> accept(X1 x1) {
        NullCheck.requireNonNull(x1);
        return (x2, x3, x4) -> accept(x1, x2, x3, x4);
    }

    /**
     * Partially applies the first and second arguments to this consumer.
     *
     * <p>
     * The returned {@link BinConsumer} accepts the remaining two arguments and
     * performs this consumer using the bound first and second arguments.
     *
     * @param x1 the first argument to bind
     * @param x2 the second argument to bind
     * @return a binary consumer of the remaining arguments
     * @throws NullPointerException if {@code x1} or {@code x2} is {@code null}
     */
    default BinConsumer<X3, X4> accept(X1 x1, X2 x2) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        return (x3, x4) -> accept(x1, x2, x3, x4);
    }

    /**
     * Partially applies the first, second, and third arguments to this consumer.
     *
     * <p>
     * The returned {@link Consumer} accepts the remaining fourth argument and
     * performs this consumer using the bound first three arguments.
     *
     * @param x1 the first argument to bind
     * @param x2 the second argument to bind
     * @param x3 the third argument to bind
     * @return a consumer of the remaining argument
     * @throws NullPointerException if {@code x1}, {@code x2}, or {@code x3} is {@code null}
     */
    default Consumer<X4> accept(X1 x1, X2 x2, X3 x3) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        NullCheck.requireNonNull(x3);
        return x4 -> accept(x1, x2, x3, x4);
    }


    /**
     * Returns a composed {@code QuadConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation. If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code QuadConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default QuadConsumer<X1, X2, X3, X4> andThen(QuadConsumer<? super X1, ? super X2, ? super X3, ? super X4> after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4) -> {
            accept(x1, x2, x3, x4);
            after.accept(x1, x2, x3, x4);
        };
    }

    /**
     * Returns a curried version of this {@code QuadConsumer} as a function that
     * accepts one argument at a time, returning functions that accept the next
     * arguments until all arguments are provided and the operation is performed.
     *
     * @return a function that takes the first argument and returns a function
     *         which takes the second argument and returns a function that takes
     *         the third argument and finally returns a consumer that accepts the
     *         fourth argument to perform the operation
     */
    default Function<X1, Function<X2, Function<X3, Consumer<X4>>>> curried() {
        return x1 -> x2 -> x3 -> x4 -> this.accept(x1, x2, x3, x4);
    }

    /**
     * Returns a new {@code QuadConsumer} whose parameters are in reverse order compared to this one.
     * The returned consumer will accept its arguments in the order (x1, x2, x3, x4) and pass them
     * to the original consumer in the reversed order (x4, x3, x2, x1).
     *
     * @return a new {@code QuadConsumer} with reversed parameter order
     */
    default QuadConsumer<X4, X3, X2, X1> reversed() {
        return (x1, x2, x3, x4) -> accept(x4, x3, x2, x1);
    }

    /**
     * Returns a {@code QuadConsumer} that performs no operation.
     *
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param <X3> the type of the third argument to the operation
     * @param <X4> the type of the fourth argument to the operation
     * @return a {@code QuadConsumer} that does nothing when its {@link #accept(Object, Object, Object, Object)} method is called
     */
    @Contract(pure = true)
    static <X1, X2, X3, X4> @NotNull QuadConsumer<X1, X2, X3, X4> empty() {
        return (x1, x2, x3, x4) -> {};
    }

    /**
     * Narrows the given {@code QuadConsumer} to a specific type, ensuring that
     * the provided consumer is not null.
     *
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param <X3> the type of the third argument to the operation
     * @param <X4> the type of the fourth argument to the operation
     * @param quadConsumer the consumer to narrow, must not be {@code null}
     * @return a narrowed {@code QuadConsumer} of the specified types
     * @throws NullPointerException if the provided consumer is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, X3, X4> QuadConsumer<X1, X2, X3, X4> narrow(QuadConsumer<? super X1, ? super X2, ? super X3, ? super X4> quadConsumer) {
        NullCheck.requireNonNull(quadConsumer);
        return (QuadConsumer<X1, X2, X3, X4>) quadConsumer;
    }

    /**
     * Schedules a collection of {@code QuadConsumer} instances to be executed in sequence.
     *
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param <X3> the type of the third argument to the operation
     * @param <X4> the type of the fourth argument to the operation
     * @param consumers a collection of {@code QuadConsumer} instances to be scheduled
     * @return a new {@code QuadConsumer} that, when accepted, will execute all the provided consumers in sequence
     * @throws NullPointerException if any of the provided consumers or the collection is {@code null}
     */
    static <X1, X2, X3, X4> @NotNull QuadConsumer<X1, X2, X3, X4> schedule(final Collection<? extends QuadConsumer<? super X1, ? super X2, ? super X3, ? super X4>> consumers) {
        NullCheck.requireAllNonNull(consumers);
        if (consumers.isEmpty()) return empty();

        return (x1, x2, x3, x4) -> {
            for (QuadConsumer<? super X1, ? super X2, ? super X3, ? super X4> quadConsumer : consumers) {
                quadConsumer.accept(x1, x2, x3, x4);
            }
        };
    }
}
