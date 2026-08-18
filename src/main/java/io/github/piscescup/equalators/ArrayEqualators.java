package io.github.piscescup.equalators;

import io.github.piscescup.interfaces.HashEqualator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Provides commonly used {@link HashEqualator} implementations for arrays.
 *
 * <p>The equalators provided by this class use the corresponding methods in
 * {@link Arrays} to determine equality and compute hash values. Therefore,
 * the equality and hashing semantics are consistent with Java's standard
 * array utility methods.</p>
 *
 * <p>Primitive array equalators compare array elements in sequence. Object
 * array equalators are available in both shallow and deep forms.</p>
 *
 * @author REN YuanTong
 * @see HashEqualator
 * @see Arrays
 * @since 1.1.0
 */
public final class ArrayEqualators {

    /**
     * A hash equalator for {@code boolean[]} arrays.
     */
    @NotNull
    public static final HashEqualator<boolean[]> BOOLEAN =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * A hash equalator for {@code byte[]} arrays.
     */
    @NotNull
    public static final HashEqualator<byte[]> BYTE =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * A hash equalator for {@code short[]} arrays.
     */
    @NotNull
    public static final HashEqualator<short[]> SHORT =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * A hash equalator for {@code char[]} arrays.
     */
    @NotNull
    public static final HashEqualator<char[]> CHAR =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * A hash equalator for {@code int[]} arrays.
     */
    @NotNull
    public static final HashEqualator<int[]> INT =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * A hash equalator for {@code long[]} arrays.
     */
    @NotNull
    public static final HashEqualator<long[]> LONG =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * A hash equalator for {@code float[]} arrays.
     */
    @NotNull
    public static final HashEqualator<float[]> FLOAT =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * A hash equalator for {@code double[]} arrays.
     */
    @NotNull
    public static final HashEqualator<double[]> DOUBLE =
        HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );

    /**
     * Prevents instantiation.
     */
    private ArrayEqualators() {
        throw new UnsupportedOperationException(
            "ArrayEqualators cannot be instantiated."
        );
    }

    /**
     * Returns a hash equalator that compares object arrays using shallow
     * element equality.
     *
     * <p>Two arrays are considered equal when they have the same length and
     * corresponding elements are equal according to
     * {@link Object#equals(Object)}. Nested arrays are not recursively
     * compared.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<String[]> equalator =
     *     ArrayEqualators.object();
     *
     * String[] first = {"a", "b"};
     * String[] second = {"a", "b"};
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <E> the component type of the arrays
     * @return a shallow hash equalator for object arrays
     */
    @NotNull
    public static <E> HashEqualator<E[]> object() {
        return HashEqualator.of(
            Arrays::equals,
            Arrays::hashCode
        );
    }

    /**
     * Returns a hash equalator that compares object arrays recursively.
     *
     * <p>Nested arrays are compared recursively using
     * {@link Arrays#deepEquals(Object[], Object[])}, and hash values are
     * computed using {@link Arrays#deepHashCode(Object[])}.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Object[] first = {
     *     new int[]{1, 2},
     *     new String[]{"a", "b"}
     * };
     *
     * Object[] second = {
     *     new int[]{1, 2},
     *     new String[]{"a", "b"}
     * };
     *
     * HashEqualator<Object[]> equalator =
     *     ArrayEqualators.deep();
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @return a deep hash equalator for object arrays
     */
    @NotNull
    public static HashEqualator<Object[]> deep() {
        return HashEqualator.of(
            Arrays::deepEquals,
            Arrays::deepHashCode
        );
    }
}