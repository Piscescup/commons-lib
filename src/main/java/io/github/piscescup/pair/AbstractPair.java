package io.github.piscescup.pair;

import io.github.piscescup.interfaces.Pair;
import io.github.piscescup.util.CompareUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * An abstract base class for {@link Pair} implementations.
 *
 * <p>This class provides standard implementations of {@link #equals(Object)},
 * {@link #hashCode()}, and {@link #compareTo(Pair)} based solely on the
 * {@code left} and {@code right} elements.</p>
 *
 * <p>Subclasses are required to implement {@link #getLeft()} and
 * {@link #getRight()}.</p>
 *
 * <h2>Equality and hashing</h2>
 * <ul>
 *   <li>Two {@code Pair} instances are considered equal if both their left
 *       and right elements are equal according to {@link Objects#equals(Object, Object)}.</li>
 *   <li>The {@link #hashCode()} implementation follows the same strategy used
 *       by {@code HashMap.Node}: {@code Objects.hashCode(left) ^ Objects.hashCode(right)}.</li>
 * </ul>
 *
 * <h2>Ordering</h2>
 * <p>The natural ordering defined by {@link #compareTo(Pair)} is
 * lexicographical:</p>
 * <ol>
 *   <li>First compare the left elements.</li>
 *   <li>If the left elements are equal, compare the right elements.</li>
 * </ol>
 *
 * <p>The comparison is performed using {@link CompareUtils} and follows a
 * nulls-first policy.</p>
 *
 * <h2>Serialization</h2>
 * <p>This class implements {@link Serializable}. Subclasses are responsible
 * for ensuring that their state is serializable if required.</p>
 *
 * <p>This class is intended to be extended by concrete, typically immutable,
 * {@code Pair} implementations.</p>
 *
 * @param <L> the left element type
 * @param <R> the right element type
 *
 * @see MutablePair
 * @see ImmutablePair
 * @author REN YuanTong
 * @since 1.0.0
 */
public abstract class AbstractPair<L, R> implements Pair<L, R> {

    /**
     * The serialization version UID.
     */
    @Serial
    private static final long serialVersionUID = 1547447L;

    /**
     * Protected constructor for subclassing.
     */
    protected AbstractPair() {}

    /**
     * Returns the left element of this pair.
     *
     * @return the left element (may be {@code null})
     */
    @Override
    public abstract L getLeft();

    /**
     * Returns the right element of this pair.
     *
     * @return the right element (may be {@code null})
     */
    @Override
    public abstract R getRight();

    /**
     * Compares this pair to the specified object for equality.
     *
     * <p>This method returns {@code true} if and only if the specified object
     * is also a {@link Pair} and both the left and right elements are equal.</p>
     *
     * @param obj the object to compare with
     * @return {@code true} if the specified object is equal to this pair;
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Pair<?, ?> pair) {
            final Map.Entry<?, ?> otherEntry = pair.toMapEntry();
            Map.Entry<L, R> thisEntry = this.toMapEntry();
            return Objects.equals(thisEntry.getKey(), otherEntry.getKey()) &&
                Objects.equals(thisEntry.getValue(), otherEntry.getValue());
        }
        return false;
    }

    /**
     * Returns a hash code value for this pair.
     *
     * <p>The hash code is computed as:</p>
     * <pre>{@code
     * Objects.hashCode(left) ^ Objects.hashCode(right)
     * }</pre>
     *
     * <p>This implementation is consistent with {@link #equals(Object)}.</p>
     *
     * @return a hash code value for this pair
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getLeft()) ^ Objects.hashCode(getRight());
    }

    /**
     * Compares this pair with the specified pair for order.
     *
     * <p>The comparison is lexicographical: the left elements are compared
     * first; if they are equal, the right elements are compared.</p>
     *
     * <p>This method uses {@link CompareUtils} and follows a nulls-first
     * comparison policy.</p>
     *
     * @param o the pair to be compared
     * @return a negative integer, zero, or a positive integer as this pair
     *         is less than, equal to, or greater than the specified pair
     *
     * @throws NullPointerException if {@code o} is {@code null}
     */
    @Override
    public int compareTo(@NotNull Pair<L, R> o) {
        return CompareUtils.createCompareBuilder()
            .tryCompare(this.getLeft(), o.getLeft())
            .tryCompare(this.getRight(), o.getRight())
            .executeCompare();
    }
}
