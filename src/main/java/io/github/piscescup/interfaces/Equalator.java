package io.github.piscescup.interfaces;

/**
 * An equality function, which imposes an <i>equivalence relation</i> on
 * some collection of objects. Equalators can be passed to algorithms
 * or data structures to allow precise control over equality semantics.
 *
 * Equalators can also be used when the natural equality defined by
 * {@link Object#equals(Object)} or {@link Equalable} is not appropriate
 * for a particular algorithm or operation.<p>
 *
 * The equality relation imposed by an equalator {@code c} on a set
 * of elements {@code S} is defined as:<pre>
 *       {(x, y) such that c.equals(x, y) == true}.
 * </pre>
 *
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
 *
 * Unlike {@link Equalable}, an equalator may provide equality rules
 * external to the compared objects themselves.<p>
 *
 * @param <T> the type of objects that may be compared by this equalator
 *
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
     *         {@code false} otherwise
     *
     * @throws NullPointerException if an argument is null and this
     *         equalator does not permit null arguments
     * @throws ClassCastException if the arguments' types prevent them
     *         from being compared by this equalator
     */
    boolean equals(T o1, T o2);
}
