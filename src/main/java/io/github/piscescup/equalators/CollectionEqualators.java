package io.github.piscescup.equalators;

import io.github.piscescup.collection.EqualatorHashMap;
import io.github.piscescup.collection.EqualatorHashSet;
import io.github.piscescup.interfaces.HashEqualator;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * Provides commonly used {@link HashEqualator} implementations for
 * {@link Collection} values.
 *
 * <p>This class provides several collection comparison strategies:</p>
 *
 * <ul>
 *     <li>
 *         {@link #sequence()} compares elements in iteration order and
 *         considers duplicate occurrences significant.
 *     </li>
 *     <li>
 *         {@link #set()} ignores both iteration order and duplicate
 *         occurrences.
 *     </li>
 *     <li>
 *         {@link #multiset()} ignores iteration order but considers the
 *         number of occurrences of each distinct element significant.
 *     </li>
 * </ul>
 *
 * <p>Each strategy also provides an overload that accepts a
 * {@link HashEqualator} for controlling the equality and hashing semantics
 * of collection elements.</p>
 *
 * <p>The returned equalators are suitable for use by hash-based collections
 * and algorithms because their hash functions are consistent with their
 * corresponding equality relations.</p>
 *
 * @author REN YuanTong
 * @see HashEqualator
 * @see Collection
 * @see EqualatorHashSet
 * @see EqualatorHashMap
 * @since 1.1.0
 */
public final class CollectionEqualators {

    /**
     * Prevents instantiation.
     */
    private CollectionEqualators() {
        throw new UnsupportedOperationException(
            "CollectionEqualators cannot be instantiated."
        );
    }

    /**
     * Returns a hash equalator that compares collections element by element
     * in iteration order using the default equality and hashing semantics.
     *
     * <p>Two collections are considered equal when they contain the same
     * number of elements and every corresponding pair of elements is equal.
     * Both iteration order and duplicate occurrences are significant.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<Collection<? extends Integer>> equalator =
     *     CollectionEqualators.sequence();
     *
     * Collection<Integer> first =
     *     List.of(1, 2, 3);
     *
     * Collection<Integer> second =
     *     List.of(1, 2, 3);
     *
     * Collection<Integer> third =
     *     List.of(3, 2, 1);
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     *
     * System.out.println(equalator.equals(first, third));
     * // false
     * }</pre>
     *
     * @param <E> the type of elements contained in the collections
     * @return an order-sensitive hash equalator for collections
     */
    @NotNull
    public static <E>
    HashEqualator<Collection<? extends E>> sequence() {
        return sequence(
            HashEqualator.defaultHashEqualator()
        );
    }

    /**
     * Returns a hash equalator that compares collections element by element
     * in iteration order using the specified element hash equalator.
     *
     * <p>Two collections are considered equal when they contain the same
     * number of elements and each corresponding pair of elements is
     * considered equal by {@code elementEqualator}.</p>
     *
     * <p>The generated hash value is order-sensitive and follows the same
     * accumulation strategy used by Java's {@link java.util.List} contract:</p>
     *
     * <pre>
     *     hash = 31 * hash + elementHash
     * </pre>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<Collection<? extends String>> equalator =
     *     CollectionEqualators.sequence(
     *         StringEqualators.ORDINAL_IGNORE_CASE
     *     );
     *
     * Collection<String> first =
     *     List.of("Apple", "Orange");
     *
     * Collection<String> second =
     *     List.of("APPLE", "orange");
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <E> the type of elements contained in the collections
     * @param elementEqualator the hash equalator used to compare and hash
     *     collection elements
     * @return an order-sensitive hash equalator for collections
     *
     * @throws NullPointerException if {@code elementEqualator} is
     *                              {@code null}
     */
    @NotNull
    public static <E>
    HashEqualator<Collection<? extends E>> sequence(
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        NullCheck.requireNonNull(elementEqualator);

        return HashEqualator.of(
            (left, right) ->
                sequenceEquals(
                    left,
                    right,
                    elementEqualator
                ),
            collection ->
                sequenceHash(
                    collection,
                    elementEqualator
                )
        );
    }

    /**
     * Returns a hash equalator that compares collections using set semantics
     * and the default equality and hashing semantics.
     *
     * <p>Iteration order and duplicate occurrences are ignored. Therefore,
     * the collections {@code [1, 2, 2, 3]} and {@code [3, 2, 1]} are
     * considered equal.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<Collection<? extends Integer>> equalator =
     *     CollectionEqualators.set();
     *
     * Collection<Integer> first =
     *     List.of(1, 2, 2, 3);
     *
     * Collection<Integer> second =
     *     List.of(3, 2, 1);
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <E> the type of elements contained in the collections
     * @return a set-based hash equalator for collections
     */
    @NotNull
    public static <E>
    HashEqualator<Collection<? extends E>> set() {
        return set(
            HashEqualator.defaultHashEqualator()
        );
    }

    /**
     * Returns a hash equalator that compares collections using set semantics
     * and the specified element hash equalator.
     *
     * <p>Two collections are considered equal when they contain the same
     * distinct elements according to {@code elementEqualator}. Iteration
     * order and duplicate occurrences are ignored.</p>
     *
     * <p>The generated hash value is independent of both iteration order and
     * duplicate occurrences.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<Collection<? extends String>> equalator =
     *     CollectionEqualators.set(
     *         StringEqualators.ORDINAL_IGNORE_CASE
     *     );
     *
     * Collection<String> first =
     *     List.of("Apple", "Orange", "Apple");
     *
     * Collection<String> second =
     *     List.of("orange", "APPLE");
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <E> the type of elements contained in the collections
     * @param elementEqualator the hash equalator used to compare and hash
     *     collection elements
     * @return a set-based hash equalator for collections
     *
     * @throws NullPointerException if {@code elementEqualator} is
     *                              {@code null}
     */
    @NotNull
    public static <E>
    HashEqualator<Collection<? extends E>> set(
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        NullCheck.requireNonNull(elementEqualator);

        return HashEqualator.of(
            (left, right) ->
                setEquals(
                    left,
                    right,
                    elementEqualator
                ),
            collection ->
                setHash(
                    collection,
                    elementEqualator
                )
        );
    }

    /**
     * Returns a hash equalator that compares collections using multiset
     * semantics and the default equality and hashing semantics.
     *
     * <p>Iteration order is ignored, but the number of occurrences of each
     * distinct element is significant.</p>
     *
     * <p>For example, {@code [1, 2, 2, 3]} and {@code [3, 2, 2, 1]} are
     * considered equal, while {@code [1, 2, 2, 3]} and {@code [1, 2, 3]}
     * are not.</p>
     *
     * @param <E> the type of elements contained in the collections
     * @return a multiset-based hash equalator for collections
     */
    @NotNull
    public static <E>
    HashEqualator<Collection<? extends E>> multiset() {
        return multiset(
            HashEqualator.defaultHashEqualator()
        );
    }

    /**
     * Returns a hash equalator that compares collections using multiset
     * semantics and the specified element hash equalator.
     *
     * <p>Two collections are considered equal when every distinct element
     * occurs the same number of times in both collections according to
     * {@code elementEqualator}. Iteration order is ignored.</p>
     *
     * <p>The generated hash value is order-independent but preserves the
     * contribution of duplicate occurrences.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<Collection<? extends String>> equalator =
     *     CollectionEqualators.multiset(
     *         StringEqualators.ORDINAL_IGNORE_CASE
     *     );
     *
     * Collection<String> first =
     *     List.of("Apple", "APPLE", "Orange");
     *
     * Collection<String> second =
     *     List.of("orange", "apple", "APPLE");
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     *
     * Collection<String> third =
     *     List.of("apple", "orange");
     *
     * System.out.println(equalator.equals(first, third));
     * // false
     * }</pre>
     *
     * @param <E> the type of elements contained in the collections
     * @param elementEqualator the hash equalator used to compare and hash
     *     collection elements
     * @return a multiset-based hash equalator for collections
     *
     * @throws NullPointerException if {@code elementEqualator} is
     *                              {@code null}
     */
    @NotNull
    public static <E>
    HashEqualator<Collection<? extends E>> multiset(
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        NullCheck.requireNonNull(elementEqualator);

        return HashEqualator.of(
            (left, right) ->
                multisetEquals(
                    left,
                    right,
                    elementEqualator
                ),
            collection ->
                multisetHash(
                    collection,
                    elementEqualator
                )
        );
    }

    /**
     * Determines whether two collections are equal using sequence semantics.
     *
     * @param left             the first collection
     * @param right            the second collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return {@code true} if the collections are sequence-equal
     */
    private static <E> boolean sequenceEquals(
        Collection<? extends E> left,
        Collection<? extends E> right,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        if (left == right) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        if (left.size() != right.size()) {
            return false;
        }

        Iterator<? extends E> leftIterator =
            left.iterator();

        Iterator<? extends E> rightIterator =
            right.iterator();

        while (leftIterator.hasNext()) {
            if (!elementEqualator.equals(
                leftIterator.next(),
                rightIterator.next()
            )) {
                return false;
            }
        }

        return true;
    }

    /**
     * Computes an order-sensitive hash value for a collection.
     *
     * @param collection       the collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return the sequence hash value
     */
    private static <E> int sequenceHash(
        Collection<? extends E> collection,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        if (collection == null) {
            return 0;
        }

        int hash = 1;

        for (E element : collection) {
            hash =
                31 * hash
                    + elementEqualator.hash(element);
        }

        return hash;
    }

    /**
     * Determines whether two collections are equal using set semantics.
     *
     * @param left             the first collection
     * @param right            the second collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return {@code true} if the collections contain the same distinct
     *         elements
     */
    private static <E> boolean setEquals(
        Collection<? extends E> left,
        Collection<? extends E> right,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        if (left == right) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        EqualatorHashSet<E> leftSet =
            createSet(
                left,
                elementEqualator
            );

        EqualatorHashSet<E> rightSet =
            createSet(
                right,
                elementEqualator
            );

        if (leftSet.size() != rightSet.size()) {
            return false;
        }

        return leftSet.containsAll(rightSet);
    }

    /**
     * Computes an order-independent and duplicate-independent hash value for a
     * collection using set semantics.
     *
     * @param collection       the collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return the set hash value
     */
    private static <E> int setHash(
        Collection<? extends E> collection,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        if (collection == null) {
            return 0;
        }

        EqualatorHashSet<E> set =
            createSet(
                collection,
                elementEqualator
            );

        int hash = 0;

        for (E element : set) {
            hash +=
                elementEqualator.hash(element);
        }

        return hash;
    }

    /**
     * Determines whether two collections are equal using multiset semantics.
     *
     * @param left             the first collection
     * @param right            the second collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return {@code true} if both collections contain equivalent elements
     *         with identical occurrence counts
     */
    private static <E> boolean multisetEquals(
        Collection<? extends E> left,
        Collection<? extends E> right,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        if (left == right) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        if (left.size() != right.size()) {
            return false;
        }

        EqualatorHashMap<E, Integer> counts =
            createCounts(
                left,
                elementEqualator
            );

        for (E element : right) {
            Integer count =
                counts.get(element);

            if (count == null) {
                return false;
            }

            if (count == 1) {
                counts.remove(element);
            } else {
                counts.put(
                    element,
                    count - 1
                );
            }
        }

        return counts.isEmpty();
    }

    /**
     * Computes an order-independent hash value that preserves duplicate
     * occurrences using multiset semantics.
     *
     * @param collection       the collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return the multiset hash value
     */
    private static <E> int multisetHash(
        Collection<? extends E> collection,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        if (collection == null) {
            return 0;
        }

        int hash = 0;

        for (E element : collection) {
            hash +=
                elementEqualator.hash(element);
        }

        return hash;
    }

    /**
     * Creates a set containing the distinct elements of the specified
     * collection using the supplied element hash equalator.
     *
     * @param collection       the source collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return a set containing the distinct source elements
     */
    @NotNull
    private static <E> EqualatorHashSet<E> createSet(
        @NotNull Collection<? extends E> collection,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        EqualatorHashSet<E> set =
            new EqualatorHashSet<>(
                collection.size(),
                elementEqualator
            );

        set.addAll(collection);

        return set;
    }

    /**
     * Creates a map containing the number of occurrences of each distinct
     * element in the specified collection.
     *
     * @param collection       the source collection
     * @param elementEqualator the element hash equalator
     * @param <E>              the type of collection elements
     * @return a map containing occurrence counts
     */
    @NotNull
    private static <E> EqualatorHashMap<E, Integer> createCounts(
        @NotNull Collection<? extends E> collection,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        EqualatorHashMap<E, Integer> counts =
            new EqualatorHashMap<>(
                collection.size(),
                elementEqualator
            );

        for (E element : collection) {
            counts.merge(
                element,
                1,
                Math::addExact
            );
        }

        return counts;
    }
}