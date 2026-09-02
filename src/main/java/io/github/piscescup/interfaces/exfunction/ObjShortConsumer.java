package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.interfaces.exfunction.primitive.ShortConsumer;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;

/**
 * Represents an operation that accepts an object-valued argument and a
 * {@code short}-valued argument and returns no result.
 *
 * <p>Unlike most other functional interfaces, this interface is expected to
 * operate via side effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, short)}.
 *
 * @param <X> the type of the object argument
 *
 * @since 1.2.0
 */
@FunctionalInterface
public interface ObjShortConsumer<X> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x the object argument
     * @param value the {@code short}-valued argument
     */
    void accept(X x, short value);

    /**
     * Partially applies the object argument to this consumer.
     *
     * @param x the object argument to bind
     * @return a consumer accepting the remaining primitive value
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default ShortConsumer accept(X x) {
        NullCheck.requireNonNull(x);
        return value -> accept(x, value);
    }

    /**
     * Returns a composed consumer that performs this operation followed by
     * the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return the composed consumer
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default ObjShortConsumer<X> andThen(ObjShortConsumer<? super X> after) {
        NullCheck.requireNonNull(after);
        return (x, value) -> {
            accept(x, value);
            after.accept(x, value);
        };
    }

    /**
     * Returns the curried form of this consumer.
     *
     * @return a function that accepts the object argument and returns a
     *         consumer accepting the primitive argument
     */
    default Function<X, ShortConsumer> curried() {
        return x -> value -> accept(x, value);
    }

    /**
     * Returns a consumer that performs no operation.
     *
     * @param <X> the type of the object argument
     * @return an empty consumer
     */
    static <X> ObjShortConsumer<X> empty() {
        return (x, value) -> {};
    }

    /**
     * Returns the given consumer.
     *
     * @param consumer the consumer to return
     * @param <X> the type of the object argument
     * @return the given consumer
     * @throws NullPointerException if {@code consumer} is {@code null}
     */
    static <X> ObjShortConsumer<X> of(ObjShortConsumer<X> consumer) {
        NullCheck.requireNonNull(consumer);
        return consumer;
    }

    /**
     * Narrows the given consumer to a specific object argument type.
     *
     * @param consumer the consumer to narrow
     * @param <X> the target object argument type
     * @return the narrowed consumer
     * @throws NullPointerException if {@code consumer} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X> ObjShortConsumer<X> narrow(
        ObjShortConsumer<? super X> consumer
    ) {
        NullCheck.requireNonNull(consumer);
        return (ObjShortConsumer<X>) consumer;
    }

    /**
     * Returns a consumer that executes the supplied consumers in iteration
     * order.
     *
     * @param consumers the consumers to execute
     * @param <X> the type of the object argument
     * @return a consumer that executes all supplied consumers
     * @throws NullPointerException if the collection or any consumer is
     *         {@code null}
     */
    static <X> @NotNull ObjShortConsumer<X> schedule(
        Collection<? extends ObjShortConsumer<? super X>> consumers
    ) {
        NullCheck.requireAllNonNull(consumers);

        if (consumers.isEmpty()) return empty();

        return (x, value) -> {
            for (ObjShortConsumer<? super X> consumer : consumers) {
                consumer.accept(x, value);
            }
        };
    }
}

