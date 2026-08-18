package io.github.piscescup.equalators;

import io.github.piscescup.collection.EqualatorHashMap;
import io.github.piscescup.interfaces.HashEqualator;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Provides {@link HashEqualator} implementations for {@link Map} values.
 *
 * <p>Maps are compared by their mappings rather than iteration order.
 * Two maps are considered equal when they contain equivalent keys and the
 * values associated with those keys are also equivalent.</p>
 *
 * <p>Custom equality and hashing semantics may be independently specified
 * for keys and values.</p>
 *
 * @author REN YuanTong
 * @see HashEqualator
 * @see Map
 * @since 1.1.0
 */
public final class MapEqualators {

    /**
     * Prevents instantiation.
     */
    private MapEqualators() {
        throw new UnsupportedOperationException(
            "MapEqualators cannot be instantiated."
        );
    }

    /**
     * Returns a hash equalator that compares maps using their default key and
     * value equality and hashing semantics.
     *
     * <p>The iteration order of the maps does not affect equality.</p>
     *
     * @param <K> the type of map keys
     * @param <V> the type of map values
     * @return a hash equalator for maps
     */
    @NotNull
    public static <K, V> HashEqualator<Map<? extends K, ? extends V>> entries() {
        return entries(
            HashEqualator.defaultHashEqualator(),
            HashEqualator.defaultHashEqualator()
        );
    }

    /**
     * Returns a hash equalator that compares maps using the specified key and
     * value hash equalators.
     *
     * <p>Two maps are considered equal when they contain the same number of
     * mappings and every key in one map has an equivalent key in the other map
     * whose associated value is also equivalent.</p>
     *
     * <p>The iteration order of the maps does not affect either equality or
     * the resulting hash value.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<Map<? extends String, ? extends Integer>> equalator =
     *     MapEqualators.entries(
     *         StringEqualators.ORDINAL_IGNORE_CASE,
     *         HashEqualator.defaultHashEqualator()
     *     );
     *
     * Map<String, Integer> first = Map.of(
     *     "Apple", 1,
     *     "Orange", 2
     * );
     *
     * Map<String, Integer> second = Map.of(
     *     "APPLE", 1,
     *     "orange", 2
     * );
     *
     * System.out.println(equalator.equals(first, second));
     * // true
     * }</pre>
     *
     * @param <K> the type of map keys
     * @param <V> the type of map values
     * @param keyEqualator the hash equalator used to compare and hash keys
     * @param valueEqualator the hash equalator used to compare and hash values
     * @return a hash equalator for maps
     * @throws NullPointerException if {@code keyEqualator} or
     *     {@code valueEqualator} is {@code null}
     */
    @NotNull
    public static <K, V> HashEqualator<Map<? extends K, ? extends V>> entries(
        @NotNull HashEqualator<? super K> keyEqualator,
        @NotNull HashEqualator<? super V> valueEqualator
    ) {
        NullCheck.requireNonNull(keyEqualator);
        NullCheck.requireNonNull(valueEqualator);

        return HashEqualator.of(
            (left, right) ->
                mapEquals(
                    left,
                    right,
                    keyEqualator,
                    valueEqualator
                ),
            map ->
                mapHash(
                    map,
                    keyEqualator,
                    valueEqualator
                )
        );
    }

    /**
     * Determines whether two maps are equal according to the specified key and
     * value equality semantics.
     */
    private static <K, V> boolean mapEquals(
        @NotNull Map<? extends K, ? extends V> left,
        @NotNull Map<? extends K, ? extends V> right,
        @NotNull HashEqualator<? super K> keyEqualator,
        @NotNull HashEqualator<? super V> valueEqualator
    ) {
        if (left.size() != right.size()) {
            return false;
        }

        Map<K, V> lookup =
            new EqualatorHashMap<>(
                right.size(),
                keyEqualator
            );

        lookup.putAll(right);

        for (
            Map.Entry<? extends K, ? extends V> entry
            : left.entrySet()
        ) {
            K key =
                entry.getKey();

            if (!lookup.containsKey(key)) {
                return false;
            }

            V rightValue =
                lookup.get(key);

            if (!valueEqualator.equals(
                entry.getValue(),
                rightValue
            )) {
                return false;
            }
        }

        return true;
    }

    /**
     * Computes an order-independent hash value for a map.
     */
    private static <K, V> int mapHash(
        @NotNull Map<? extends K, ? extends V> map,
        @NotNull HashEqualator<? super K> keyEqualator,
        @NotNull HashEqualator<? super V> valueEqualator
    ) {
        int hash = 0;

        for (
            Map.Entry<? extends K, ? extends V> entry
            : map.entrySet()
        ) {
            int keyHash =
                keyEqualator.hash(
                    entry.getKey()
                );

            int valueHash =
                valueEqualator.hash(
                    entry.getValue()
                );

            hash += keyHash ^ valueHash;
        }

        return hash;
    }
}