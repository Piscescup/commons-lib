package io.github.piscescup.interfaces;

import io.github.piscescup.interfaces.exfunction.BinConsumer;
import io.github.piscescup.interfaces.exfunction.BinFunction;
import io.github.piscescup.interfaces.exfunction.failable.FailableBinConsumer;
import io.github.piscescup.interfaces.exfunction.failable.FailableBinFunction;
import io.github.piscescup.interfaces.exfunction.failable.FailableFunction;
import io.github.piscescup.pair.ImmutablePair;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A two-element container holding a {@code left} and a {@code right} value.
 *
 * <p>This interface provides functional-style operations for transforming and consuming
 * either side (or both sides) of the pair.
 *
 * <p>The pair can be converted to a {@link Map.Entry} using {@link #toMapEntry()}.</p>
 *
 *
 * @param <L> the left element type, recommend to implement {@link Comparable}
 * @param <R> the right element type, recommend to implement {@link Comparable}
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public interface Pair<L, R> extends Comparable<Pair<L, R>>, Serializable {

    /**
     * Returns the left element of this pair.
     *
     * @return the left element (may be {@code null})
     */
    L getLeft();

    /**
     * Returns the right element of this pair.
     *
     * @return the right element (may be {@code null})
     */
    R getRight();

    /**
     * Creates a {@code Pair} from the given {@link Map.Entry}.
     *
     * @param entry the source entry
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a pair containing {@code entry.getKey()} as left and {@code entry.getValue()} as right
     * @throws NullPointerException if {@code entry} is {@code null} (implementation-dependent)
     */
    @Contract("_ -> new")
    static <L, R> @NotNull Pair<L, R> of(final Map.Entry<L, R> entry) {
        return ImmutablePair.of(entry);
    }

    /**
     * Creates a {@code Pair} from the given two values.
     *
     * @param left  the left value (may be {@code null})
     * @param right the right value (may be {@code null})
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a pair containing the provided {@code left} and {@code right}
     */
    @Contract("_, _ -> new")
    static <L, R> @NotNull Pair<L, R> of(final L left, final R right) {
        return ImmutablePair.of(left, right);
    }

    /**
     * Maps the left element using the provided mapper and returns a new pair.
     *
     * @param mapper the mapping function applied to the left element
     * @param <NL>   the new left element type
     * @return a new pair with mapped left element and original right element
     * @throws NullPointerException if {@code mapper} is {@code null}
     */
    default <NL> Pair<NL, R> mappedLeft(Function<? super L, ? extends NL> mapper) {
        NullCheck.requireNonNull(mapper);
        return of(mapper.apply(getLeft()), getRight());
    }

    /**
     * Maps the left element using the provided mapper which may throw, and returns a new pair.
     *
     * @param mapper the mapping function applied to the left element
     * @param <NL>   the new left element type
     * @param <E>    the throwable type
     * @return a new pair with mapped left element and original right element
     * @throws NullPointerException if {@code mapper} is {@code null}
     * @throws E if the mapper throws
     */
    default <NL, E extends Throwable> Pair<NL, R> mappedLeftOrThrow(
        FailableFunction<? super L, ? extends NL, E> mapper
    ) throws E {
        NullCheck.requireNonNull(mapper);
        return of(mapper.applyOrThrow(getLeft()), getRight());
    }

    /**
     * Applies a mapping function to the left element and returns the result.
     *
     * <p>This is a convenience method for extracting derived values from the left element
     * without constructing a new pair.</p>
     *
     * @param mapper the mapping function applied to the left element
     * @param <V>    the result type
     * @return the mapped value
     * @throws NullPointerException if {@code mapper} is {@code null}
     */
    default <V> V applyLeft(Function<? super L, ? extends V> mapper) {
        NullCheck.requireNonNull(mapper);
        return mapper.apply(getLeft());
    }

    /**
     * Applies a mapping function that may throw to the left element and returns the result.
     *
     * @param mapper the mapping function applied to the left element
     * @param <V>    the result type
     * @param <E>    the throwable type
     * @return the mapped value
     * @throws NullPointerException if {@code mapper} is {@code null}
     * @throws E if the mapper throws
     */
    default <V, E extends Throwable> V applyLeftOrThrow(
        FailableFunction<? super L, ? extends V, E> mapper
    ) throws E {
        NullCheck.requireNonNull(mapper);
        return mapper.applyOrThrow(getLeft());
    }

    /**
     * Maps the right element using the provided mapper and returns a new pair.
     *
     * @param mapper the mapping function applied to the right element
     * @param <NR>   the new right element type
     * @return a new pair with original left element and mapped right element
     * @throws NullPointerException if {@code mapper} is {@code null}
     */
    default <NR> Pair<L, NR> mappedRight(Function<? super R, ? extends NR> mapper) {
        NullCheck.requireNonNull(mapper);
        return of(getLeft(), mapper.apply(getRight()));
    }

    /**
     * Maps the right element using the provided mapper which may throw, and returns a new pair.
     *
     * @param mapper the mapping function applied to the right element
     * @param <NR>   the new right element type
     * @param <E>    the throwable type
     * @return a new pair with original left element and mapped right element
     * @throws NullPointerException if {@code mapper} is {@code null}
     * @throws E if the mapper throws
     */
    default <NR, E extends Throwable> Pair<L, NR> mappedRightOrThrow(
        FailableFunction<? super R, ? extends NR, E> mapper
    ) throws E {
        NullCheck.requireNonNull(mapper);
        return of(getLeft(), mapper.applyOrThrow(getRight()));
    }

    /**
     * Applies a mapping function to the right element and returns the result.
     *
     * @param mapper the mapping function applied to the right element
     * @param <V>    the result type
     * @return the mapped value
     * @throws NullPointerException if {@code mapper} is {@code null}
     */
    default <V> V applyRight(Function<? super R, ? extends V> mapper) {
        NullCheck.requireNonNull(mapper);
        return mapper.apply(getRight());
    }

    /**
     * Applies a mapping function that may throw to the right element and returns the result.
     *
     * @param mapper the mapping function applied to the right element
     * @param <V>    the result type
     * @param <E>    the throwable type
     * @return the mapped value
     * @throws NullPointerException if {@code mapper} is {@code null}
     * @throws E if the mapper throws
     */
    default <V, E extends Throwable> V applyRightOrThrow(
        FailableFunction<? super R, ? extends V, E> mapper
    ) throws E {
        NullCheck.requireNonNull(mapper);
        return mapper.applyOrThrow(getRight());
    }

    /**
     * Maps both elements using the provided mappers and returns a new pair.
     *
     * @param leftMapper  the mapping function applied to the left element
     * @param rightMapper the mapping function applied to the right element
     * @param <NL>        the new left element type
     * @param <NR>        the new right element type
     * @return a new pair containing the mapped elements
     * @throws NullPointerException if either mapper is {@code null}
     */
    default <NL, NR> Pair<NL, NR> mapped(
        Function<? super L, ? extends NL> leftMapper,
        Function<? super R, ? extends NR> rightMapper
    ) {
        NullCheck.requireNonNull(leftMapper);
        NullCheck.requireNonNull(rightMapper);
        return of(leftMapper.apply(getLeft()), rightMapper.apply(getRight()));
    }

    /**
     * Maps both elements using the provided mappers which may throw, and returns a new pair.
     *
     * @param leftMapper  the mapping function applied to the left element
     * @param rightMapper the mapping function applied to the right element
     * @param <NL>        the new left element type
     * @param <NR>        the new right element type
     * @param <E>         the throwable type
     * @return a new pair containing the mapped elements
     * @throws NullPointerException if either mapper is {@code null}
     * @throws E if any mapper throws
     */
    default <NL, NR, E extends Throwable> Pair<NL, NR> mappedOrThrow(
        FailableFunction<? super L, ? extends NL, E> leftMapper,
        FailableFunction<? super R, ? extends NR, E> rightMapper
    ) throws E {
        NullCheck.requireNonNull(leftMapper);
        NullCheck.requireNonNull(rightMapper);
        return of(leftMapper.applyOrThrow(getLeft()), rightMapper.applyOrThrow(getRight()));
    }

    /**
     * Applies a mapper to both elements and returns the result.
     *
     * @param mapper the function that receives both left and right elements
     * @param <V>    the result type
     * @return the computed value
     * @throws NullPointerException if {@code mapper} is {@code null}
     */
    default <V> V apply(BinFunction<? super L, ? super R, ? extends V> mapper) {
        NullCheck.requireNonNull(mapper);
        return mapper.apply(getLeft(), getRight());
    }

    /**
     * Applies a mapper to both elements and returns the result, allowing the mapper to throw.
     *
     * @param mapper the function that receives both left and right elements
     * @param <V>    the result type
     * @param <E>    the throwable type
     * @return the computed value
     * @throws NullPointerException if {@code mapper} is {@code null}
     * @throws E if the mapper throws
     */
    default <V, E extends Throwable> V applyOrThrow(
        FailableBinFunction<? super L, ? super R, ? extends V, E> mapper
    ) throws E {
        NullCheck.requireNonNull(mapper);
        return mapper.applyOrThrow(getLeft(), getRight());
    }

    /**
     * Performs the provided actions on the left and right elements for side effects,
     * then returns this pair unchanged.
     *
     * <p>This method is useful for debugging, logging, or instrumentation in fluent chains.</p>
     *
     * @param leftAction  the action applied to the left element
     * @param rightAction the action applied to the right element
     * @return this pair
     * @throws NullPointerException if either action is {@code null}
     */
    default Pair<L, R> peek(
        Consumer<? super L> leftAction,
        Consumer<? super R> rightAction
    ) {
        NullCheck.requireNonNull(leftAction);
        NullCheck.requireNonNull(rightAction);
        leftAction.accept(getLeft());
        rightAction.accept(getRight());
        return this;
    }

    /**
     * Consumes both elements using the provided consumer.
     *
     * @param consumer the consumer to accept both elements
     * @throws NullPointerException if {@code consumer} is {@code null}
     */
    default void accept(BinConsumer<? super L, ? super R> consumer) {
        NullCheck.requireNonNull(consumer);
        consumer.accept(getLeft(), getRight());
    }

    /**
     * Consumes both elements using the provided consumer which may throw.
     *
     * @param consumer the consumer to accept both elements
     * @param <E>      the throwable type
     * @throws NullPointerException if {@code consumer} is {@code null}
     * @throws E if the consumer throws
     */
    default <E extends Throwable> void acceptOrThrow(FailableBinConsumer<? super L, ? super R, E> consumer) throws E {
        NullCheck.requireNonNull(consumer);
        consumer.acceptOrThrow(getLeft(), getRight());
    }

    /**
     * Converts this pair to a {@link Map.Entry}.
     *
     * <p>The returned entry is typically immutable (e.g., via {@link Map#entry(Object, Object)}),
     * and its key/value correspond to this pair's left/right elements.</p>
     *
     * @return a {@link Map.Entry} view of this pair
     */
    default Map.Entry<L, R> toMapEntry() {
        return Map.entry(getLeft(), getRight());
    }

}
