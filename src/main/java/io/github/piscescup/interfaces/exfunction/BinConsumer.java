package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result.  This is the two-arity specialization of {@link Consumer}.
 * Unlike most other functional interfaces, {@code BinConsumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object)}.
 *
 * @param <X1> the type of the first argument to the operation
 * @param <X2> the type of the second argument to the operation
 *
 * @since 1.0.0
 */
@FunctionalInterface
public interface BinConsumer<X1, X2> extends BiConsumer<X1, X2> {

    /**
     * Creates a new {@code Consumer} that partially applies this {@code BinConsumer} to the given first argument.
     *
     * @param x1 the first input argument, must not be null
     * @return a new {@code Consumer} which accepts the second argument and performs the operation
     * @throws NullPointerException if the provided first argument is null
     */
    default Consumer<X2> accept(X1 x1) {
        NullCheck.requireNonNull(x1);
        return x2 -> this.accept(x1, x2);
    }

    /**
     * Returns a new {@code BinConsumer} with the argument order of this consumer reversed.
     *
     * @return a new {@code BinConsumer} with the argument order reversed
     */
    default BinConsumer<X2, X1> reversed() {
        return (x2, x1) -> accept(x1, x2);
    }

    /**
     * Returns a curried version of this {@code BinConsumer} as a function.
     *
     * @return a function that takes the first argument and returns a consumer
     *         which accepts the second argument to perform the operation
     */
    default Function<X1, Consumer<X2>> curried() {
        return x1 -> x2 -> this.accept(x1, x2);
    }

    /**
     * Returns a {@code BinConsumer} that performs no operation.
     *
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @return a {@code BinConsumer} that does nothing when its {@link #accept(Object, Object)} method is called
     */
    static <X1, X2> BinConsumer<X1, X2> empty() {
        return (x1, x2) -> {};
    }

    /**
     * Narrows the given {@code BinConsumer} with wildcard type parameters to a specific
     * type.
     *
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param binConsumer the consumer to be narrowwed, must not be {@code null}
     * @return a narrowed version of the input {@code BinConsumer}
     * @throws NullPointerException if the provided {@code binConsumer} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2> BinConsumer<X1, X2> narrow(BinConsumer<? super X1, ? super X2> binConsumer) {
        NullCheck.requireNonNull(binConsumer);
        return (BinConsumer<X1, X2>) binConsumer;
    }

    /**
     * Schedules a collection of {@code BinConsumer} instances to be executed in sequence.
     *
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param consumers a collection of {@code BinConsumer} instances that will be scheduled, must not be null
     * @return a new {@code BinConsumer} that, when its {@link #accept(Object, Object)} method is called, will invoke the accept method of each consumer in the provided collection in
     *  sequence
     * @throws NullPointerException if the provided collection or any of its elements is null
     */
    static <X1, X2> @NotNull BinConsumer<X1, X2> schedule(final Collection<? extends BinConsumer<? super X1, ? super X2>> consumers) {
        NullCheck.requireAllNonNull(consumers);

        if (consumers.isEmpty()) return empty();

        return (x1, x2) -> {
            for (BinConsumer<? super X1, ? super X2> consumer : consumers) {
                consumer.accept(x1, x2);
            }
        };
    }
}
