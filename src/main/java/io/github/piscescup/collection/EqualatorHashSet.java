package io.github.piscescup.collection;

import io.github.piscescup.interfaces.HashEqualator;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * A hash-based {@link Set} implementation that determines element equality
 * and hash values using a specified {@link HashEqualator}.
 *
 * <p>Unlike {@link java.util.HashSet}, which uses
 * {@link Object#equals(Object)} and {@link Object#hashCode()} to determine
 * element equality and hash values, this set delegates these operations to a
 * supplied {@link HashEqualator}. This allows custom equality and hashing
 * semantics to be used without modifying the element objects themselves.</p>
 *
 * <p>For example, a case-insensitive string set can be created using a
 * {@code HashEqualator<String>} whose equality function ignores case and
 * whose hash function produces equal hash values for strings that differ only
 * in case.</p>
 *
 * <p>The supplied {@code HashEqualator} must satisfy the standard hash
 * consistency requirement. For any two elements {@code x} and {@code y}, if:</p>
 *
 * <pre>
 *     equalator.equals(x, y) == true
 * </pre>
 *
 * <p>then the following must also be true:</p>
 *
 * <pre>
 *     equalator.hash(x) == equalator.hash(y)
 * </pre>
 *
 * <p>The reverse is not required. Two elements that are not considered equal
 * may produce the same hash value.</p>
 *
 * <p>This implementation provides constant-time performance for the basic
 * operations ({@link #add(Object)}, {@link #contains(Object)}, and
 * {@link #remove(Object)}), assuming that the supplied hash function disperses
 * elements properly among the hash buckets.</p>
 *
 * <p>Whether {@code null} elements are supported depends on the supplied
 * {@link HashEqualator}.</p>
 *
 * <p>This implementation is not synchronized. If multiple threads access an
 * instance concurrently and at least one thread modifies the set, external
 * synchronization is required.</p>
 *
 * @param <E> the type of elements maintained by this set
 *
 * @author REN YuanTong
 * @see HashEqualator
 * @see Set
 * @see java.util.HashSet
 * @since 1.1.0
 */
public final class EqualatorHashSet<E>
    extends AbstractSet<E>
    implements Set<E> {

    /**
     * The dummy value associated with an element in the backing map.
     */
    @NotNull
    private static final Object PRESENT = new Object();

    /**
     * The backing map used to store elements.
     */
    @NotNull
    private final EqualatorHashMap<E, Object> map;

    /**
     * Creates an empty set whose element equality and hash values are
     * determined by the specified hash equalator.
     *
     * @param equalator the hash equalator used to compare and hash elements
     *
     * @throws NullPointerException if {@code equalator} is {@code null}
     */
    public EqualatorHashSet(
        @NotNull final HashEqualator<? super E> equalator
    ) {
        NullCheck.requireNonNull(equalator);

        this.map =
            new EqualatorHashMap<>(equalator);
    }

    /**
     * Creates an empty set with the specified initial capacity whose element
     * equality and hash values are determined by the specified hash equalator.
     *
     * @param initialCapacity the initial capacity of the set
     * @param equalator       the hash equalator used to compare and hash
     *                        elements
     *
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     * @throws NullPointerException if {@code equalator} is {@code null}
     */
    public EqualatorHashSet(
        final int initialCapacity,
        @NotNull final HashEqualator<? super E> equalator
    ) {
        NullCheck.requireNonNull(equalator);

        this.map =
            new EqualatorHashMap<>(
                initialCapacity,
                equalator
            );
    }

    /**
     * Creates a set containing the elements of the specified collection whose
     * element equality and hash values are determined by the specified hash
     * equalator.
     *
     * <p>If the collection contains multiple elements considered equal by the
     * supplied equalator, only the first equivalent element is retained.</p>
     *
     * @param collection the collection whose elements are initially placed
     *                   into this set
     * @param equalator  the hash equalator used to compare and hash elements
     *
     * @throws NullPointerException if {@code collection} or
     *                              {@code equalator} is {@code null}
     */
    public EqualatorHashSet(
        @NotNull final Collection<? extends E> collection,
        @NotNull final HashEqualator<? super E> equalator
    ) {
        NullCheck.requireNonNull(collection);
        NullCheck.requireNonNull(equalator);

        this.map =
            new EqualatorHashMap<>(
                collection.size(),
                equalator
            );

        addAll(collection);
    }

    /**
     * Returns the hash equalator used by this set to determine element equality
     * and hash values.
     *
     * @return the hash equalator used by this set
     */
    @NotNull
    public HashEqualator<? super E> equalator() {
        return map.equalator();
    }

    /**
     * Returns the number of elements contained in this set.
     *
     * @return the number of elements in this set
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * Returns {@code true} if this set contains no elements.
     *
     * @return {@code true} if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns {@code true} if this set contains an element equivalent to the
     * specified object according to the configured {@link HashEqualator}.
     *
     * @param object the object whose presence is to be tested
     *
     * @return {@code true} if this set contains an equivalent element
     */
    @Override
    public boolean contains(final Object object) {
        return map.containsKey(object);
    }

    /**
     * Returns an iterator over the elements contained in this set.
     *
     * <p>The iterator is backed by this set. Removing an element through the
     * iterator removes the corresponding element from this set.</p>
     *
     * @return an iterator over the elements in this set
     */
    @Override
    @NotNull
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    /**
     * Returns an array containing all elements in this set.
     *
     * @return an array containing all elements in this set
     */
    @Override
    @NotNull
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    /**
     * Returns an array containing all elements in this set. The runtime type
     * of the returned array is that of the specified array.
     *
     * @param array the array into which the elements of this set are to be
     *              stored, if it is large enough
     * @param <T>   the component type of the array
     *
     * @return an array containing all elements in this set
     *
     * @throws ArrayStoreException if the runtime type of the specified array
     *                             is not a supertype of every element in this
     *                             set
     * @throws NullPointerException if {@code array} is {@code null}
     */
    @Override
    @NotNull
    public <T> T[] toArray(
        @NotNull final T[] array
    ) {
        NullCheck.requireNonNull(array);

        return map
            .keySet()
            .toArray(array);
    }

    /**
     * Adds the specified element to this set if no equivalent element is
     * already present.
     *
     * <p>Element equality is determined using the configured
     * {@link HashEqualator}. If an equivalent element is already present, the
     * originally stored element is retained.</p>
     *
     * @param element the element to be added
     *
     * @return {@code true} if this set did not already contain an equivalent
     *         element
     */
    @Override
    public boolean add(final E element) {
        return map.putIfAbsent(
            element,
            PRESENT
        ) == null;
    }

    /**
     * Removes an element equivalent to the specified object from this set if
     * present.
     *
     * @param object the object whose equivalent element is to be removed
     *
     * @return {@code true} if an element was removed
     */
    @Override
    public boolean remove(final Object object) {
        return map.remove(
            object,
            PRESENT
        );
    }

    /**
     * Returns {@code true} if this set contains all elements in the specified
     * collection according to the equality semantics of this set.
     *
     * @param collection the collection to be checked for containment
     *
     * @return {@code true} if this set contains all specified elements
     *
     * @throws NullPointerException if {@code collection} is {@code null}
     */
    @Override
    public boolean containsAll(
        @NotNull final Collection<?> collection
    ) {
        NullCheck.requireNonNull(collection);

        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds all elements in the specified collection to this set.
     *
     * <p>Elements considered equal according to the configured
     * {@link HashEqualator} are not added more than once.</p>
     *
     * @param collection the collection containing elements to be added
     *
     * @return {@code true} if this set changed as a result of the operation
     *
     * @throws NullPointerException if {@code collection} is {@code null}
     */
    @Override
    public boolean addAll(
        @NotNull final Collection<? extends E> collection
    ) {
        NullCheck.requireNonNull(collection);

        boolean modified = false;

        for (E element : collection) {
            modified |= add(element);
        }

        return modified;
    }

    /**
     * Retains only the elements in this set that are equivalent to at least one
     * element in the specified collection.
     *
     * <p>The comparison semantics are determined by this set's
     * {@link HashEqualator}, rather than by the equality semantics of the
     * specified collection.</p>
     *
     * @param collection the collection containing elements to be retained
     *
     * @return {@code true} if this set changed as a result of the operation
     *
     * @throws NullPointerException if {@code collection} is {@code null}
     */
    @Override
    public boolean retainAll(
        @NotNull final Collection<?> collection
    ) {
        NullCheck.requireNonNull(collection);

        boolean modified = false;

        Iterator<E> iterator =
            iterator();

        while (iterator.hasNext()) {
            E element =
                iterator.next();

            if (!containsEquivalent(
                collection,
                element
            )) {
                iterator.remove();
                modified = true;
            }
        }

        return modified;
    }

    /**
     * Removes from this set every element that is equivalent to at least one
     * element in the specified collection.
     *
     * <p>The comparison semantics are determined by this set's
     * {@link HashEqualator}, rather than by the equality semantics of the
     * specified collection.</p>
     *
     * @param collection the collection containing elements to be removed
     *
     * @return {@code true} if this set changed as a result of the operation
     *
     * @throws NullPointerException if {@code collection} is {@code null}
     */
    @Override
    public boolean removeAll(
        @NotNull final Collection<?> collection
    ) {
        NullCheck.requireNonNull(collection);

        boolean modified = false;

        for (Object element : collection) {
            modified |= remove(element);
        }

        return modified;
    }

    /**
     * Removes all elements from this set.
     */
    @Override
    public void clear() {
        map.clear();
    }

    /**
     * Performs the specified action for each element of this set.
     *
     * @param action the action to be performed for each element
     *
     * @throws NullPointerException if {@code action} is {@code null}
     */
    @Override
    public void forEach(
        @NotNull final Consumer<? super E> action
    ) {
        NullCheck.requireNonNull(action);

        map.keySet().forEach(action);
    }

    /**
     * Creates a {@link Spliterator} over the elements in this set.
     *
     * @return a spliterator over the elements in this set
     */
    @Override
    @NotNull
    public Spliterator<E> spliterator() {
        return map.keySet().spliterator();
    }

    /**
     * Determines whether the specified collection contains an element
     * equivalent to the specified element according to this set's
     * {@link HashEqualator}.
     *
     * @param collection the collection to search
     * @param element    the element whose equivalent value is searched for
     *
     * @return {@code true} if an equivalent element exists
     */
    private boolean containsEquivalent(
        @NotNull final Collection<?> collection,
        final E element
    ) {
        for (Object candidate : collection) {
            if (elementsEqual(
                element,
                candidate
            )) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines whether the specified stored element and candidate object are
     * considered equal by this set's hash equalator.
     *
     * @param storedElement the element stored in this set
     * @param candidate     the candidate object
     *
     * @return {@code true} if the objects are considered equal
     */
    @SuppressWarnings("unchecked")
    private boolean elementsEqual(
        final E storedElement,
        final Object candidate
    ) {
        try {
            return equalator().equals(
                storedElement,
                (E) candidate
            );
        } catch (ClassCastException ignored) {
            return false;
        }
    }
}