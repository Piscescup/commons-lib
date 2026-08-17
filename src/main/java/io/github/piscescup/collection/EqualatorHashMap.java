package io.github.piscescup.collection;

import io.github.piscescup.interfaces.HashEqualator;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A hash-based {@link Map} implementation that determines key equality and
 * hash values using a specified {@link HashEqualator}.
 *
 * <p>Unlike {@link HashMap}, which uses {@link Object#equals(Object)} and
 * {@link Object#hashCode()} to determine key equality and hash values, this
 * map delegates these operations to a supplied {@link HashEqualator}. This
 * allows custom equality and hashing semantics to be used without modifying
 * the key objects themselves.</p>
 *
 * <p>For example, a case-insensitive map for {@link String} keys can be
 * created by supplying a hash equalator that compares strings without regard
 * to case and computes hash values from their normalized representation.</p>
 *
 * <p>The supplied {@code HashEqualator} must satisfy the standard hash
 * consistency requirement. For any two keys {@code x} and {@code y}, if:</p>
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
 * <p>The reverse is not required. Two keys that are not considered equal may
 * produce the same hash value.</p>
 *
 * <p>This implementation provides constant-time performance for the basic
 * operations ({@link #get(Object)}, {@link #put(Object, Object)},
 * {@link #containsKey(Object)}, and {@link #remove(Object)}), assuming that
 * the supplied hash function disperses keys properly among the hash
 * buckets.</p>
 *
 * <p>This implementation permits {@code null} values. Whether {@code null}
 * keys are supported depends on the supplied {@link HashEqualator}.</p>
 *
 * <p>This implementation is not synchronized. If multiple threads access an
 * instance concurrently and at least one thread modifies the map, external
 * synchronization is required.</p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author REN YuanTong
 * @see HashEqualator
 * @see Map
 * @see HashMap
 * @since 1.1.0
 */
public final class EqualatorHashMap<K, V> extends AbstractMap<K, V> {

    /**
     * The hash equalator used to determine key equality and hash values.
     */
    @NotNull
    private final HashEqualator<? super K> equalator;

    /**
     * The backing map containing wrapped keys and their associated values.
     */
    @NotNull
    private final Map<HashKey<K>, V> map;

    /**
     * Creates an empty map whose key equality and hash values are determined
     * by the specified hash equalator.
     *
     * @param equalator the hash equalator used to compare and hash keys
     *
     * @throws NullPointerException if {@code equalator} is {@code null}
     */
    public EqualatorHashMap(
        @NotNull final HashEqualator<? super K> equalator
    ) {
        NullCheck.requireNonNull(equalator);

        this.equalator = equalator;
        this.map = new HashMap<>();
    }

    /**
     * Creates an empty map with the specified initial capacity whose key
     * equality and hash values are determined by the specified hash
     * equalator.
     *
     * @param initialCapacity the initial capacity of the map
     * @param equalator       the hash equalator used to compare and hash keys
     *
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     * @throws NullPointerException if {@code equalator} is {@code null}
     */
    public EqualatorHashMap(
        final int initialCapacity,
        @NotNull final HashEqualator<? super K> equalator
    ) {
        NullCheck.requireNonNull(equalator);

        this.equalator = equalator;
        this.map = new HashMap<>(initialCapacity);
    }

    /**
     * Returns the hash equalator used by this map to determine key equality
     * and hash values.
     *
     * @return the hash equalator used by this map
     */
    @NotNull
    public HashEqualator<? super K> equalator() {
        return equalator;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map contains no mappings
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key according to the configured {@link HashEqualator}.
     *
     * @param key the key whose presence is to be tested
     * @return {@code true} if this map contains a matching key
     */
    @Override
    public boolean containsKey(final Object key) {
        HashKey<K> hashKey = createLookupKey(key);

        return hashKey != null && map.containsKey(hashKey);
    }

    /**
     * Returns {@code true} if this map maps one or more keys to the specified
     * value.
     *
     * @param value the value whose presence is to be tested
     * @return {@code true} if this map contains the specified value
     */
    @Override
    public boolean containsValue(final Object value) {
        return map.containsValue(value);
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null}
     * if this map contains no mapping for the key.
     *
     * <p>Key equality is determined by the configured
     * {@link HashEqualator}.</p>
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or {@code null} if no
     *         mapping exists
     */
    @Override
    public V get(final Object key) {
        HashKey<K> hashKey = createLookupKey(key);

        return hashKey == null
            ? null
            : map.get(hashKey);
    }

    /**
     * Associates the specified value with the specified key in this map.
     *
     * <p>If this map previously contained a key considered equal to the
     * specified key by the configured {@link HashEqualator}, the old value
     * is replaced by the specified value.</p>
     *
     * <p>When an equal key is already present, the originally stored key is
     * retained.</p>
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with an equal key, or
     *         {@code null} if there was no mapping
     */
    @Override
    public V put(final K key, final V value) {
        return map.put(
            new HashKey<>(key, equalator),
            value
        );
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * <p>Key equality is determined by the configured
     * {@link HashEqualator}.</p>
     *
     * @param key the key whose mapping is to be removed
     * @return the previous value associated with the key, or {@code null}
     *         if there was no mapping
     */
    @Override
    public V remove(final Object key) {
        HashKey<K> hashKey = createLookupKey(key);

        return hashKey == null
            ? null
            : map.remove(hashKey);
    }

    /**
     * Removes all mappings from this map.
     */
    @Override
    public void clear() {
        map.clear();
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     *
     * <p>The returned set is backed by this map, so changes to the map are
     * reflected in the set and vice versa. The entries exposed by the set
     * contain the original keys rather than the internal wrapped keys used
     * by this implementation.</p>
     *
     * @return a set view of the mappings contained in this map
     */
    @Override
    @NotNull
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    /**
     * Creates an internal key wrapper for a lookup key.
     *
     * <p>If the specified object cannot be interpreted as a key supported by
     * this map, this method returns {@code null}.</p>
     *
     * @param key the lookup key
     * @return the wrapped lookup key, or {@code null} if the key is
     *         incompatible with this map
     */
    @SuppressWarnings("unchecked")
    private HashKey<K> createLookupKey(final Object key) {
        try {
            return new HashKey<>(
                (K) key,
                equalator
            );
        } catch (ClassCastException ignored) {
            return null;
        }
    }

    /**
     * A set view of the mappings contained in this map.
     */
    private final class EntrySet extends AbstractSet<Entry<K, V>> {

        /**
         * Returns an iterator over the mappings contained in this map.
         *
         * @return an iterator over the mappings
         */
        @Override
        @NotNull
        public Iterator<Entry<K, V>> iterator() {
            Iterator<Entry<HashKey<K>, V>> iterator =
                map.entrySet().iterator();

            return new Iterator<>() {

                @Override
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                @Override
                public Entry<K, V> next() {
                    return new EntryView(iterator.next());
                }

                @Override
                public void remove() {
                    iterator.remove();
                }
            };
        }

        /**
         * Returns the number of mappings contained in this set.
         *
         * @return the number of mappings
         */
        @Override
        public int size() {
            return map.size();
        }

        /**
         * Returns {@code true} if this set contains no mappings.
         *
         * @return {@code true} if this set is empty
         */
        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        /**
         * Removes all mappings from this set and its backing map.
         */
        @Override
        public void clear() {
            map.clear();
        }

        /**
         * Returns {@code true} if this set contains a mapping equivalent to
         * the specified entry.
         *
         * @param object the object whose presence is to be tested
         * @return {@code true} if the specified mapping is present
         */
        @Override
        public boolean contains(final Object object) {
            if (!(object instanceof Entry<?, ?> entry)) {
                return false;
            }

            HashKey<K> key = createLookupKey(entry.getKey());

            if (key == null) {
                return false;
            }

            V value = map.get(key);

            if (!Objects.equals(value, entry.getValue())) {
                return false;
            }

            return value != null || map.containsKey(key);
        }

        /**
         * Removes the specified mapping from this set and its backing map if
         * present.
         *
         * @param object the mapping to be removed
         * @return {@code true} if a mapping was removed
         */
        @Override
        public boolean remove(final Object object) {
            if (!(object instanceof Entry<?, ?> entry)) {
                return false;
            }

            HashKey<K> key = createLookupKey(entry.getKey());

            if (key == null) {
                return false;
            }

            V value = map.get(key);

            if (!Objects.equals(value, entry.getValue())) {
                return false;
            }

            if (value == null && !map.containsKey(key)) {
                return false;
            }

            map.remove(key);

            return true;
        }
    }

    /**
     * A view of a mapping stored in the backing map.
     *
     * <p>This view exposes the original key instead of the internal
     * {@link HashKey} used by the backing {@link HashMap}.</p>
     */
    private final class EntryView implements Entry<K, V> {

        /**
         * The backing entry.
         */
        @NotNull
        private final Entry<HashKey<K>, V> entry;

        /**
         * Creates a new entry view backed by the specified internal entry.
         *
         * @param entry the backing entry
         */
        private EntryView(
            @NotNull final Entry<HashKey<K>, V> entry
        ) {
            this.entry = entry;
        }

        /**
         * Returns the original key corresponding to this entry.
         *
         * @return the original key
         */
        @Override
        public K getKey() {
            return entry.getKey().key;
        }

        /**
         * Returns the value corresponding to this entry.
         *
         * @return the value corresponding to this entry
         */
        @Override
        public V getValue() {
            return entry.getValue();
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value.
         *
         * @param value the new value to be stored in this entry
         * @return the previous value corresponding to the entry
         */
        @Override
        public V setValue(final V value) {
            return entry.setValue(value);
        }

        /**
         * Compares the specified object with this entry for equality.
         *
         * @param object the object to be compared for equality with this entry
         * @return {@code true} if the specified object is equal to this entry
         */
        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }

            if (!(object instanceof Entry<?, ?> other)) {
                return false;
            }

            return Objects.equals(getKey(), other.getKey())
                && Objects.equals(getValue(), other.getValue());
        }

        /**
         * Returns the hash code value for this map entry.
         *
         * @return the hash code value for this entry
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(getKey())
                ^ Objects.hashCode(getValue());
        }

        /**
         * Returns a string representation of this map entry.
         *
         * @return a string representation of this entry
         */
        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /**
     * An internal key wrapper that adapts a {@link HashEqualator} to the
     * equality and hashing contract required by {@link HashMap}.
     *
     * <p>The hash value is computed when the wrapper is created and retained
     * for subsequent hash-based operations.</p>
     *
     * @param <K> the type of the wrapped key
     */
    private static final class HashKey<K> {

        /**
         * The original key.
         */
        private final K key;

        /**
         * The hash equalator used to compare and hash the key.
         */
        @NotNull
        private final HashEqualator<? super K> equalator;

        /**
         * The cached hash value of the key.
         */
        private final int hash;

        /**
         * Creates a wrapper for the specified key.
         *
         * @param key       the key to wrap
         * @param equalator the hash equalator used to compare and hash keys
         */
        private HashKey(
            final K key,
            @NotNull final HashEqualator<? super K> equalator
        ) {
            this.key = key;
            this.equalator = equalator;
            this.hash = equalator.hash(key);
        }

        /**
         * Returns the cached hash value of the wrapped key.
         *
         * @return the hash value of the wrapped key
         */
        @Override
        public int hashCode() {
            return hash;
        }

        /**
         * Determines whether this wrapped key and the specified wrapped key
         * are equal according to their hash equalator.
         *
         * @param object the object to be compared for equality
         * @return {@code true} if the wrapped keys are considered equal
         */
        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }

            if (!(object instanceof HashKey<?> other)) {
                return false;
            }

            if (equalator != other.equalator) {
                return false;
            }

            try {
                @SuppressWarnings("unchecked")
                K otherKey = (K) other.key;

                return equalator.equals(
                    key,
                    otherKey
                );
            } catch (ClassCastException ignored) {
                return false;
            }
        }
    }
}