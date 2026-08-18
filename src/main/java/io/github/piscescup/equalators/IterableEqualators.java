package io.github.piscescup.equalators;

import io.github.piscescup.interfaces.HashEqualator;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * Provides {@link HashEqualator} implementations for {@link Iterable}
 * sequences.
 *
 * <p>The equalators provided by this class compare sequences element by
 * element in iteration order. Two sequences are considered equal when they
 * contain the same number of elements and every corresponding pair of
 * elements is considered equal.</p>
 *
 * @author REN YuanTong
 * @see HashEqualator
 * @see Iterable
 * @since 1.1.0
 */
public final class IterableEqualators {

    /**
     * Prevents instantiation.
     */
    private IterableEqualators() {
        throw new UnsupportedOperationException(
            "IterableEqualators cannot be instantiated."
        );
    }

    /**
     * Returns a hash equalator that compares iterable sequences element by
     * element using the default equality and hashing semantics.
     *
     * <p>The comparison is order-sensitive. Therefore, the sequences
     * {@code [1, 2, 3]} and {@code [3, 2, 1]} are not considered equal.</p>
     *
     * @param <E> the type of elements contained in the sequences
     * @return a sequence hash equalator using the default element equality
     */
    @NotNull
    public static <E> HashEqualator<Iterable<? extends E>> sequence() {
        return sequence(
            HashEqualator.defaultHashEqualator()
        );
    }

    /**
     * Returns a hash equalator that compares iterable sequences element by
     * element using the specified element hash equalator.
     *
     * <p>Two sequences are considered equal when they contain the same number
     * of elements and every corresponding pair of elements is considered
     * equal by {@code elementEqualator}.</p>
     *
     * <p>The hash value is order-sensitive and is computed using the same
     * accumulation strategy used by Java lists:</p>
     *
     * <pre>
     *     hash = 31 * hash + elementHash
     * </pre>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<Iterable<? extends String>> equalator =
     *     IterableEqualators.sequence(
     *         StringEqualators.ORDINAL_IGNORE_CASE
     *     );
     *
     * Iterable<String> first =
     *     List.of("Apple", "Orange");
     *
     * Iterable<String> second =
     *     List.of("APPLE", "orange");
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <E> the type of elements contained in the sequences
     * @param elementEqualator the hash equalator used to compare and hash
     *     sequence elements
     * @return a sequence hash equalator using the specified element equalator
     * @throws NullPointerException if {@code elementEqualator} is {@code null}
     */
    @NotNull
    public static <E> HashEqualator<Iterable<? extends E>> sequence(
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        NullCheck.requireNonNull(elementEqualator);

        return HashEqualator.of(
            (left, right) -> sequenceEquals(
                left,
                right,
                elementEqualator
            ),
            iterable -> sequenceHash(
                iterable,
                elementEqualator
            )
        );
    }

    /**
     * Determines whether two iterable sequences are equal.
     */
    private static <E> boolean sequenceEquals(
        @NotNull Iterable<? extends E> left,
        @NotNull Iterable<? extends E> right,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        Iterator<? extends E> leftIterator =
            left.iterator();

        Iterator<? extends E> rightIterator =
            right.iterator();

        while (
            leftIterator.hasNext()
                && rightIterator.hasNext()
        ) {
            if (!elementEqualator.equals(
                leftIterator.next(),
                rightIterator.next()
            )) {
                return false;
            }
        }

        return !leftIterator.hasNext()
            && !rightIterator.hasNext();
    }

    /**
     * Computes an order-sensitive hash value for an iterable sequence.
     */
    private static <E> int sequenceHash(
        @NotNull Iterable<? extends E> iterable,
        @NotNull HashEqualator<? super E> elementEqualator
    ) {
        int hash = 1;

        for (E element : iterable) {
            hash =
                31 * hash
                    + elementEqualator.hash(element);
        }

        return hash;
    }
}