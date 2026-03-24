package io.github.piscescup.interfaces;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/**
 * An equality function, which imposes an <i>equivalence relation</i> on
 * some collection of objects. Equalators can be passed to algorithms
 * or data structures to allow precise control over equality semantics.
 * <p>
 * Equalators can also be used when the natural equality defined by
 * {@link Object#equals(Object)} or {@link Equalable} is not appropriate
 * for a particular algorithm or operation.<p>
 * <p>
 * The equality relation imposed by an equalator {@code c} on a set
 * of elements {@code S} is defined as:<pre>
 *       {(x, y) such that c.equals(x, y) == true}.
 * </pre>
 * <p>
 * This relation must satisfy the properties of an equivalence relation:
 *
 * <ul>
 *   <li><b>Reflexive</b>: {@code c.equals(x, x)} is {@code true}</li>
 *   <li><b>Symmetric</b>: {@code c.equals(x, y)} is {@code true}
 *       if and only if {@code c.equals(y, x)} is {@code true}</li>
 *   <li><b>Transitive</b>: if {@code c.equals(x, y)} and
 *       {@code c.equals(y, z)} are {@code true}, then
 *       {@code c.equals(x, z)} should also be {@code true}</li>
 * </ul>
 * <p>
 * Unlike {@link Equalable}, an equalator may provide equality rules
 * external to the compared objects themselves.<p>
 *
 * @param <T> the type of objects that may be compared by this equalator
 * @author REN YuanTong
 * @see Equalable
 * @since 1.1.0
 */
@FunctionalInterface
public interface Equalator<T> {

    /**
     * Determines whether two objects are considered equal.
     *
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return {@code true} if the objects are considered equal,
     * {@code false} otherwise
     * @throws NullPointerException if an argument is null and this
     *                              equalator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them
     *                              from being compared by this equalator
     */
    boolean equals(T o1, T o2);

    /**
     * Returns an equalator that delegates to {@link Objects#equals(Object, Object)}.
     *
     * @param <T> the object type
     * @return an equalator based on {@link Object#equals(Object)}
     */
    public static <T> Equalator<T> defaultEqualator() {
        return Objects::equals;
    }

    /**
     * Returns an equalator that uses reference equality ({@code ==}).
     *
     * @param <T> the object type
     * @return an equalator based on reference identity
     */
    public static <T> Equalator<T> identityEqualator() {
        return (o1, o2) -> o1 == o2;
    }

    /**
     * Returns an equalator that delegates to {@link Equalable#equalsTo(Object)}.
     *
     * <p>If both references are identical, this equalator returns {@code true}.
     * If exactly one argument is {@code null}, it returns {@code false}.</p>
     *
     * @param <T> the object type
     * @return an equalator based on {@link Equalable#equalsTo(Object)}
     */
    public static <T extends Equalable<? super T>> Equalator<T> equalableEqualator() {
        return (o1, o2) -> {
            if (o1 == o2) {
                return true;
            }
            if (o1 == null || o2 == null) {
                return false;
            }
            return o1.equalsTo(o2);
        };
    }

    /**
     * Returns an equalator that considers two objects equal when the supplied
     * comparator reports them as equal.
     *
     * <p>If both references are identical, this equalator returns {@code true}.
     * If exactly one argument is {@code null}, it returns {@code false}.</p>
     *
     * @param comparator the comparator used to determine equality
     * @param <T>        the object type
     * @return an equalator backed by the specified comparator
     * @throws NullPointerException if {@code comparator} is {@code null}
     */
    public static <T> Equalator<T> comparing(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator");
        return (o1, o2) -> {
            if (o1 == o2) {
                return true;
            }
            if (o1 == null || o2 == null) {
                return false;
            }
            return comparator.compare(o1, o2) == 0;
        };
    }

    /**
     * Returns an equalator that compares extracted keys using
     * {@link Objects#equals(Object, Object)}.
     *
     * @param keyExtractor the function used to extract comparison keys
     * @param <T>          the object type
     * @param <U>          the extracted key type
     * @return an equalator based on the extracted keys
     * @throws NullPointerException if {@code keyExtractor} is {@code null}
     */
    public static <T, U> Equalator<T> comparing(final Function<? super T, ? extends U> keyExtractor) {
        return comparing(keyExtractor, defaultEqualator());
    }

    /**
     * Returns an equalator that compares extracted keys using the supplied
     * key equalator.
     *
     * @param keyExtractor the function used to extract comparison keys
     * @param keyEqualator the equalator applied to the extracted keys
     * @param <T>          the object type
     * @param <U>          the extracted key type
     * @return an equalator based on the extracted keys
     * @throws NullPointerException if {@code keyExtractor} or {@code keyEqualator} is {@code null}
     */
    public static <T, U> Equalator<T> comparing(
        final Function<? super T, ? extends U> keyExtractor,
        final Equalator<? super U> keyEqualator
    ) {
        Objects.requireNonNull(keyExtractor, "keyExtractor");
        Objects.requireNonNull(keyEqualator, "keyEqualator");
        return (o1, o2) -> {
            if (o1 == o2) {
                return true;
            }
            if (o1 == null || o2 == null) {
                return false;
            }
            return keyEqualator.equals(keyExtractor.apply(o1), keyExtractor.apply(o2));
        };
    }

}
