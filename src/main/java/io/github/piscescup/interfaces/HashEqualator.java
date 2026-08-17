package io.github.piscescup.interfaces;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * An equality function that additionally provides a hash function consistent
 * with the equality relation imposed by this equalator.
 *
 * <p>A {@code HashEqualator} extends {@link Equalator} by defining a hash
 * value for objects participating in equality comparisons. It can be used by
 * hash-based data structures and algorithms that require both custom equality
 * semantics and a corresponding hash function.</p>
 *
 * <p>The hash function must be consistent with the equality relation defined
 * by {@link #equals(Object, Object)}. In particular, for any two objects
 * {@code x} and {@code y}, if:</p>
 *
 * <pre>
 *     equals(x, y) == true
 * </pre>
 *
 * <p>then the following must also be true:</p>
 *
 * <pre>
 *     hash(x) == hash(y)
 * </pre>
 *
 * <p>The reverse is not required. Two objects that are not considered equal
 * may have the same hash value.</p>
 *
 * <p>The hash value returned for an object should remain consistent while the
 * information used by this equalator to determine equality remains unchanged.
 * Implementations should also attempt to distribute hash values uniformly
 * whenever practical, as poor hash distribution may reduce the performance of
 * hash-based data structures.</p>
 *
 * <p>Unlike {@link Object#hashCode()}, the hash function defined by this
 * interface is external to the object itself. This allows hashing semantics
 * to remain consistent with custom equality rules supplied by
 * {@link Equalator}.</p>
 *
 * @param <T> the type of objects that may be compared and hashed by this
 *            equalator
 *
 * @author REN YuanTong
 * @see Equalator
 * @see Object#hashCode()
 * @since 1.1.0
 */
public interface HashEqualator<T> extends Equalator<T> {

    /**
     * Returns a hash value for the specified object.
     *
     * <p>If two objects are considered equal by
     * {@link #equals(Object, Object)}, this method must return the same hash
     * value for both objects.</p>
     *
     * @param object the object whose hash value is to be computed
     * @return the hash value of the specified object
     *
     * @throws NullPointerException if {@code object} is {@code null} and this
     *         equalator does not permit null arguments
     * @throws ClassCastException if the object's type prevents its hash value
     *         from being computed by this equalator
     */
    int hash(T object);

    /**
     * Returns a hash equalator that determines equality using the specified
     * {@link Equalator} and computes hash values using the specified hash
     * function.
     *
     * <p>The supplied hash function must be consistent with the equality
     * relation imposed by {@code equalator}. In particular, if two objects
     * {@code x} and {@code y} are considered equal, the hash function must
     * produce the same hash value for both objects:</p>
     *
     * <pre>
     *     equalator.equals(x, y) == true
     *         implies
     *     hasher.applyAsInt(x) == hasher.applyAsInt(y)
     * </pre>
     *
     * <p>Failure to satisfy this requirement may cause incorrect behavior when
     * the returned equalator is used by hash-based data structures.</p>
     *
     * @param equalator the equalator used to determine object equality
     * @param hasher    the function used to compute hash values
     * @param <E>       the type of objects compared and hashed
     * @return a hash equalator backed by the specified equality and hash
     *         functions
     * @throws NullPointerException if {@code equalator} or {@code hasher} is
     *                              {@code null}
     */
    @NotNull
    static <E> HashEqualator<E> of(
        @NotNull Equalator<? super E> equalator,
        @NotNull ToIntFunction<? super E> hasher
    ) {
        NullCheck.requireNonNull(equalator);
        NullCheck.requireNonNull(hasher);

        return new HashEqualator<>() {

            @Override
            public int hash(E object) {
                return hasher.applyAsInt(object);
            }

            @Override
            public boolean equals(E o1, E o2) {
                return equalator.equals(o1, o2);
            }
        };
    }

    /**
     * Returns a hash equalator that uses the default Java equality and hashing
     * semantics.
     *
     * <p>Equality is determined by {@link Objects#equals(Object, Object)}, and
     * hash values are computed by {@link Objects#hashCode(Object)}. Therefore,
     * {@code null} values are supported: two {@code null} references are
     * considered equal, and the hash value of {@code null} is {@code 0}.</p>
     *
     * <p>For non-null objects, this equalator is equivalent to using
     * {@link Object#equals(Object)} together with {@link Object#hashCode()}.</p>
     *
     * @param <E> the type of objects compared and hashed
     * @return a hash equalator using the default Java equality and hashing
     *         semantics
     */
    @NotNull
    static <E> HashEqualator<E> defaultHashEqualator() {
        return of(
            Objects::equals,
            Objects::hashCode
        );
    }

}