package io.github.piscescup.pair;

import io.github.piscescup.interfaces.Pair;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A mutable implementation of {@link Pair}.
 *
 * <p>Represents a pair of values whose left and right elements
 * can be modified after construction via setter methods.</p>
 *
 * <h2>Mutability and thread-safety</h2>
 * <ul>
 *   <li>This class is <strong>mutable</strong>.</li>
 *   <li>This class is <strong>not thread-safe</strong>. External synchronization
 *       is required if instances are accessed by multiple threads.</li>
 * </ul>
 *
 * <p><strong>Important:</strong> Because this class is mutable, modifying the
 * elements after the instance has been used as a key in a hash-based or
 * sorted collection (such as {@link java.util.HashMap} or
 * {@link java.util.TreeSet}) may break collection invariants.</p>
 *
 * @param <L> the left element type
 * @param <R> the right element type
 *
 * @see ImmutablePair
 * @author REN YuanTong
 * @since 1.0.0
 */
public class MutablePair<L, R>
    extends AbstractPair<L, R>
    implements Pair<L, R>
{

    /**
     * The left element of this pair.
     */
    private L left;

    /**
     * The right element of this pair.
     */
    private R right;

    /**
     * Private constructor.
     *
     * <p>Instances must be created via static factory methods.</p>
     *
     * @param left  the left element (may be {@code null})
     * @param right the right element (may be {@code null})
     */
    private MutablePair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Creates a {@code MutablePair} containing the given elements.
     *
     * <p>{@code null} values are permitted.</p>
     *
     * @param left  the left element (may be {@code null})
     * @param right the right element (may be {@code null})
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code MutablePair}
     */
    @Contract("_, _ -> new")
    public static <L, R> @NotNull MutablePair<L, R> of(final L left, final R right) {
        return new MutablePair<>(left, right);
    }

    /**
     * Creates a {@code MutablePair} containing the given non-null elements.
     *
     * @param left  the left element (must not be {@code null})
     * @param right the right element (must not be {@code null})
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code MutablePair}
     * @throws NullPointerException if either {@code left} or {@code right} is {@code null}
     */
    @Contract("_, _ -> new")
    public static <L, R> @NotNull MutablePair<L, R> ofAllNonNull(final L left, final R right) {
        NullCheck.requireNonNull(left);
        NullCheck.requireNonNull(right);
        return new MutablePair<>(left, right);
    }

    /**
     * Creates a {@code MutablePair} from the given {@link Map.Entry}.
     *
     * @param entry the source map entry
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code MutablePair} containing the entry's key and value
     * @throws NullPointerException if {@code entry} is {@code null}
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull MutablePair<L, R> of(final Map.Entry<L, R> entry) {
        NullCheck.requireNonNull(entry);
        return new MutablePair<>(entry.getKey(), entry.getValue());
    }

    /**
     * Creates a {@code MutablePair} from the given {@link Map.Entry},
     * requiring both key and value to be non-null.
     *
     * @param entry the source map entry
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new {@code MutablePair}
     * @throws NullPointerException if {@code entry}, its key, or its value is {@code null}
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull MutablePair<L, R> ofAllNonNull(final Map.Entry<L, R> entry) {
        NullCheck.requireNonNull(entry);
        NullCheck.requireNonNull(entry.getKey());
        NullCheck.requireNonNull(entry.getValue());
        return new MutablePair<>(entry.getKey(), entry.getValue());
    }

    /**
     * Creates a {@code MutablePair} with the given left element and a {@code null} right element.
     *
     * @param left the left element (maybe {@code null})
     * @param <L>  the left element type
     * @param <R>  the right element type
     * @return a new {@code MutablePair} with a {@code null} right element
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull MutablePair<L, R> left(final L left) {
        return new MutablePair<>(left, null);
    }

    /**
     * Returns the left element of this pair.
     *
     * @return the left element (maybe {@code null})
     */
    @Override
    public L getLeft() {
        return left;
    }

    /**
     * Returns the right element of this pair.
     *
     * @return the right element (maybe {@code null})
     */
    @Override
    public R getRight() {
        return right;
    }

    /**
     * Sets the left element of this pair.
     *
     * @param left the new left element (may be {@code null})
     */
    public void setLeft(L left) {
        this.left = left;
    }

    /**
     * Sets the right element of this pair.
     *
     * @param right the new right element (may be {@code null})
     */
    public void setRight(R right) {
        this.right = right;
    }
}
