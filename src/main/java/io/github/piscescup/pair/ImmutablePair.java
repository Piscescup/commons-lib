package io.github.piscescup.pair;

import io.github.piscescup.interfaces.Pair;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * An immutable implementation of {@link Pair}.
 *
 * <p>Represents a pair of values whose left and right elements
 * are fixed at construction time and can never be modified.</p>
 *
 * <p>This class is thread-safe due to its immutability.</p>
 *
 * @param <L> the left element type
 * @param <R> the right element type
 *
 * @see MutablePair
 * @author REN YuanTong
 * @since 1.0.0
 */
public class ImmutablePair<L, R>
    extends AbstractPair<L, R>
    implements Pair<L, R>, Serializable
{

    /**
     * The serialization version UID.
     */
    @Serial
    private static final long serialVersionUID = 154515599454L;

    /**
     * The left element of this pair.
     */
    private final L left;

    /**
     * The right element of this pair.
     */
    private final R right;

    /**
     * Private constructor.
     *
     * <p>Instances must be created via static factory methods.</p>
     *
     * @param left  the left element (maybe {@code null})
     * @param right the right element (maybe {@code null})
     */
    private ImmutablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Creates an {@code ImmutablePair} containing the given elements.
     *
     * <p>{@code null} values are permitted.</p>
     *
     * @param left  the left element (maybe {@code null})
     * @param right the right element (maybe {@code null})
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code ImmutablePair}
     */
    @Contract("_, _ -> new")
    public static <L, R> @NotNull ImmutablePair<L, R> of(final L left, final R right) {
        return new ImmutablePair<>(left, right);
    }

    /**
     * Creates an {@code ImmutablePair} containing the given non-null elements.
     *
     * <p>Both elements are validated to be non-null.</p>
     *
     * @param left  the left element (must not be {@code null})
     * @param right the right element (must not be {@code null})
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code ImmutablePair}
     * @throws NullPointerException if either {@code left} or {@code right} is {@code null}
     */
    @Contract("_, _ -> new")
    public static <L, R> @NotNull ImmutablePair<L, R> ofAllNonNull(final L left, final R right) {
        NullCheck.requireNonNull(left);
        NullCheck.requireNonNull(right);
        return new ImmutablePair<>(left, right);
    }

    /**
     * Creates an {@code ImmutablePair} from the given {@link Map.Entry}.
     *
     * @param entry the source map entry
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code ImmutablePair} containing the entry's key and value
     * @throws NullPointerException if {@code entry} is {@code null}
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull ImmutablePair<L, R> of(final Map.Entry<L, R> entry) {
        NullCheck.requireNonNull(entry);
        return new ImmutablePair<>(entry.getKey(), entry.getValue());
    }

    /**
     * Creates an {@code ImmutablePair} from the given {@link Map.Entry},
     * requiring both key and value to be non-null.
     *
     * @param entry the source map entry
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code ImmutablePair}
     * @throws NullPointerException if {@code entry}, its key, or its value is {@code null}
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull ImmutablePair<L, R> ofAllNonNull(final Map.Entry<L, R> entry) {
        NullCheck.requireNonNull(entry);
        NullCheck.requireNonNull(entry.getKey());
        NullCheck.requireNonNull(entry.getValue());
        return new ImmutablePair<>(entry.getKey(), entry.getValue());
    }

    /**
     * Creates an {@code ImmutablePair} with the given left element and a {@code null} right element.
     *
     * @param left the left element (may be {@code null})
     * @param <L>  the left element type
     * @param <R>  the right element type
     * @return a new {@code ImmutablePair} with a {@code null} right element
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull ImmutablePair<L, R> left(final L left) {
        return new ImmutablePair<>(left, null);
    }

    /**
     * Creates an {@code ImmutablePair} with the given right element and a {@code null} left element.
     *
     * @param right the right element (may be {@code null})
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code ImmutablePair} with a {@code null} left element
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull ImmutablePair<L, R> right(final R right) {
        return new ImmutablePair<>(null, right);
    }

    /**
     * Returns the left element of this pair.
     *
     * @return the left element (may be {@code null})
     */
    @Override
    public L getLeft() {
        return left;
    }

    /**
     * Returns the right element of this pair.
     *
     * @return the right element (may be {@code null})
     */
    @Override
    public R getRight() {
        return right;
    }
}
