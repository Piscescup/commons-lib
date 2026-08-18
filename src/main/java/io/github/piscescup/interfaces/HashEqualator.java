package io.github.piscescup.interfaces;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;
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
 * @see io.github.piscescup.equalators Provided Hash Equalators.
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
                return object == null ? 0 : hasher.applyAsInt(object);
            }

            @Override
            public boolean equals(E o1, E o2) {
                if (o1 == o2) return true;
                if (o1 == null || o2 == null) return false;
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

    /**
     * Returns a hash equalator that compares objects by a key extracted using
     * the specified key selector.
     *
     * <p>Two objects are considered equal when the keys produced by the
     * {@code keySelector} are equal according to
     * {@link Objects#equals(Object, Object)}. The hash code of an object is
     * determined by applying {@link Objects#hashCode(Object)} to its extracted
     * key.</p>
     *
     * <p>The returned hash equalator satisfies the required consistency between
     * equality and hashing, provided that the key type itself satisfies the
     * standard {@link Object#equals(Object)} and {@link Object#hashCode()}
     * contract.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * record Product(String name, int code) {}
     *
     * HashEqualator<Product> equalator =
     *     HashEqualator.comparing(Product::name);
     *
     * Product first = new Product("apple", 1);
     * Product second = new Product("apple", 2);
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <T> the type of objects compared by the returned hash equalator
     * @param <K> the type of the comparison key
     * @param keySelector the function used to extract the comparison key
     * @return a hash equalator that compares and hashes objects by the
     *     extracted key
     * @throws NullPointerException if {@code keySelector} is {@code null}
     */
    @NotNull
    static <T, K> HashEqualator<T> comparing(
        @NotNull Function<? super T, ? extends K> keySelector
    ) {
        Objects.requireNonNull(keySelector, "keySelector");

        return new HashEqualator<>() {

            @Override
            public boolean equals(T left, T right) {
                if (left == right) {
                    return true;
                }

                if (left == null || right == null) {
                    return false;
                }

                return Objects.equals(
                    keySelector.apply(left),
                    keySelector.apply(right)
                );
            }

            @Override
            public int hash(T value) {
                if (value == null) {
                    return 0;
                }

                return Objects.hashCode(
                    keySelector.apply(value)
                );
            }
        };
    }

    /**
     * Returns a hash equalator that compares objects by a key extracted using
     * the specified key selector and compares the extracted keys using the
     * specified hash equalator.
     *
     * <p>For two objects {@code x} and {@code y}, equality is determined by
     * extracting their keys and applying {@code keyEqualator}:</p>
     *
     * <pre>
     *     keyEqualator.equals(
     *         keySelector.apply(x),
     *         keySelector.apply(y)
     *     )
     * </pre>
     *
     * <p>The hash value of an object is computed by extracting its key and
     * applying {@link HashEqualator#hash(Object)} to that key. Consequently,
     * the equality and hashing semantics of the returned hash equalator are
     * entirely determined by {@code keyEqualator}.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * record Product(String name, int code) {}
     *
     * HashEqualator<String> ignoreCase =
     *     HashEqualator.of(
     *         String::equalsIgnoreCase,
     *         value -> value.toLowerCase(Locale.ROOT).hashCode()
     *     );
     *
     * HashEqualator<Product> equalator =
     *     HashEqualator.comparing(
     *         Product::name,
     *         ignoreCase
     *     );
     *
     * Product first = new Product("apple", 1);
     * Product second = new Product("APPLE", 2);
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <T> the type of objects compared by the returned hash equalator
     * @param <K> the type of the comparison key
     * @param keySelector the function used to extract the comparison key
     * @param keyEqualator the hash equalator used to compare and hash extracted
     *     keys
     * @return a hash equalator that compares and hashes objects by their
     *     extracted keys
     * @throws NullPointerException if {@code keySelector} or
     *     {@code keyEqualator} is {@code null}
     */
    @NotNull
    static <T, K> HashEqualator<T> comparing(
        @NotNull Function<? super T, ? extends K> keySelector,
        @NotNull HashEqualator<? super K> keyEqualator
    ) {
        NullCheck.requireNonNull(keySelector);
        NullCheck.requireNonNull(keyEqualator);

        return new HashEqualator<>() {

            @Override
            public boolean equals(T left, T right) {
                if (left == right) {
                    return true;
                }

                if (left == null || right == null) {
                    return false;
                }

                return keyEqualator.equals(
                    keySelector.apply(left),
                    keySelector.apply(right)
                );
            }

            @Override
            public int hash(T value) {
                if (value == null) {
                    return 0;
                }

                return keyEqualator.hash(
                    keySelector.apply(value)
                );
            }
        };
    }

}