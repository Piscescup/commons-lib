package io.github.piscescup.interfaces;

/**
 * This interface imposes an <i>equality relation</i> on the objects of each
 * class that implements it. This equality relation is referred to as the
 * class's <i>natural equality</i>, and the class's {@code equalsTo} method
 * is referred to as its <i>natural equality method</i>.<p>
 *
 * Objects that implement this interface can define their own equality
 * semantics independent of {@link Object#equals(Object)}. This may be
 * useful when equality is defined by a subset of properties or by a
 * domain-specific rule.<p>
 *
 * The natural equality for a class {@code C} is said to be <i>consistent
 * with equals</i> if and only if {@code e1.equalsTo(e2)} has the same
 * boolean value as {@code e1.equals(e2)} for every {@code e1} and
 * {@code e2} of class {@code C}.<p>
 *
 * It is strongly recommended (though not required) that natural equality
 * be consistent with {@code equals}. If this condition is violated,
 * collections or algorithms relying on {@code equals} may behave
 * differently from those relying on {@code equalsTo}. In such cases,
 * implementations should clearly document this behavior.<p>
 *
 * For the mathematically inclined, the <i>relation</i> that defines the
 * natural equality on a given class {@code C} is:<pre>{@code
 *       {(x, y) such that x.equalsTo(y) == true}.
 * }</pre>
 *
 * This relation should satisfy the properties of an <i>equivalence relation</i>:
 *
 * <ul>
 *   <li><b>Reflexive</b>: {@code x.equalsTo(x)} should return {@code true}.</li>
 *   <li><b>Symmetric</b>: {@code x.equalsTo(y)} should return the same
 *       result as {@code y.equalsTo(x)}.</li>
 *   <li><b>Transitive</b>: if {@code x.equalsTo(y)} and
 *       {@code y.equalsTo(z)} are {@code true}, then
 *       {@code x.equalsTo(z)} should also be {@code true}.</li>
 * </ul>
 *
 * @param <T> the type of objects that this object may be compared to
 *
 * @author REN YuanTong
 * @see Equalator
 * @since 1.1.0
 */
public interface Equalable<T> {

    /**
     * Compares this object with the specified object for equality.
     *
     * @param other the object to be compared
     * @return {@code true} if this object is considered equal to the
     *         specified object
     *
     * @throws NullPointerException if the specified object is null
     *         and this implementation does not permit null arguments
     * @throws ClassCastException if the specified object's type prevents
     *         it from being compared to this object
     */
    boolean equalsTo(T other);
}
