package io.github.piscescup.collection;

import io.github.piscescup.interfaces.Equalator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link Map} implementation that determines key equality using a specified
 * {@link Equalator}.
 *
 * <p>Unlike {@link java.util.HashMap}, this map does not use
 * {@link Object#equals(Object)} to determine whether two keys are equal.
 * Instead, key equality is determined exclusively by the supplied
 * {@link Equalator}.</p>
 *
 * <p>Since {@link Equalator} defines only an equality operation and does not
 * provide a corresponding hash function, this implementation performs a
 * linear search when locating keys. Consequently, key-based operations such
 * as {@link #get(Object)}, {@link #put(Object, Object)},
 * {@link #containsKey(Object)}, and {@link #remove(Object)} have
 * {@code O(n)} time complexity.</p>
 *
 * <p>Operations that would otherwise require several map lookups, such as
 * {@link #computeIfAbsent(Object, Function)},
 * {@link #putIfAbsent(Object, Object)},
 * {@link #compute(Object, BiFunction)}, and
 * {@link #merge(Object, Object, BiFunction)}, are overridden so that a key
 * is located at most once per operation whenever possible.</p>
 *
 * <p>This implementation permits {@code null} values. Whether {@code null}
 * keys are supported depends on the supplied {@link Equalator}. Callers may
 * impose additional restrictions on keys where required.</p>
 *
 * <p>This implementation is not synchronized. If multiple threads access an
 * instance concurrently and at least one thread modifies the map, external
 * synchronization is required.</p>
 *
 * <p>Because the equality semantics of this map may differ from
 * {@link Object#equals(Object)}, it should primarily be used when operations
 * are explicitly expected to honor the supplied {@link Equalator}.</p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author REN YuanTong
 * @since 1.1.0
 */
public final class EqualatorMap<K, V>
    extends AbstractMap<K, V>
    implements Map<K, V> {

    /**
     * The equalator used to determine key equality.
     */
    @NotNull
    private final Equalator<? super K> equalator;

    /**
     * The entries contained in this map.
     *
     * <p>The list preserves insertion order.</p>
     */
    @NotNull
    private final List<Entry<K, V>> entries;

    /**
     * Lazily created entry-set view.
     */
    @Nullable
    private transient Set<Entry<K, V>> entrySet;

    /**
     * Creates an empty map whose key equality is determined by the specified
     * equalator.
     *
     * @param equalator the equalator used to determine key equality
     * @throws NullPointerException if {@code equalator} is {@code null}
     */
    public EqualatorMap(
        @NotNull final Equalator<? super K> equalator
    ) {
        this(0, equalator);
    }

    /**
     * Creates an empty map with the specified initial capacity whose key
     * equality is determined by the specified equalator.
     *
     * @param initialCapacity the initial capacity of this map
     * @param equalator       the equalator used to determine key equality
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     * @throws NullPointerException     if {@code equalator} is {@code null}
     */
    public EqualatorMap(
        final int initialCapacity,
        @NotNull final Equalator<? super K> equalator
    ) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                "initialCapacity must not be negative: " + initialCapacity
            );
        }

        this.equalator =
            Objects.requireNonNull(equalator, "equalator");

        this.entries =
            new ArrayList<>(initialCapacity);
    }

    /**
     * Returns the equalator used by this map to determine key equality.
     *
     * @return the key equalator
     */
    @NotNull
    public Equalator<? super K> equalator() {
        return equalator;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of mappings
     */
    @Override
    public int size() {
        return entries.size();
    }

    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map is empty
     */
    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key according to this map's {@link Equalator}.
     *
     * @param key the key whose presence is to be tested
     * @return {@code true} if this map contains a matching key
     */
    @Override
    public boolean containsKey(final Object key) {
        return findEntryIndex(key) >= 0;
    }

    /**
     * Returns the value associated with the specified key according to this
     * map's {@link Equalator}.
     *
     * @param key the key whose associated value is to be returned
     * @return the associated value, or {@code null} if no mapping exists
     */
    @Override
    public V get(final Object key) {
        int index = findEntryIndex(key);

        return index < 0
            ? null
            : entries.get(index).getValue();
    }

    /**
     * Returns the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key.
     *
     * @param key          the key whose associated value is to be returned
     * @param defaultValue the default value
     * @return the mapped value or {@code defaultValue}
     */
    @Override
    public V getOrDefault(
        final Object key,
        final V defaultValue
    ) {
        int index = findEntryIndex(key);

        return index < 0
            ? defaultValue
            : entries.get(index).getValue();
    }

    /**
     * Associates the specified value with the specified key.
     *
     * <p>If this map already contains a key considered equal to the specified
     * key by the configured {@link Equalator}, the value of the existing
     * mapping is replaced. The originally stored key is retained.</p>
     *
     * @param key   the key with which the value is to be associated
     * @param value the value to be associated with the key
     * @return the previous value associated with the key, or {@code null}
     *         if there was no mapping
     */
    @Override
    public V put(
        final K key,
        final V value
    ) {
        int index = findEntryIndex(key);

        if (index >= 0) {
            return entries
                .get(index)
                .setValue(value);
        }

        entries.add(
            new SimpleEntry<>(key, value)
        );

        return null;
    }

    /**
     * Associates the specified value with the specified key if the key is not
     * already associated with a non-null value.
     *
     * @param key   the key with which the value is to be associated
     * @param value the value to be associated with the key
     * @return the previous value associated with the key
     */
    @Override
    public V putIfAbsent(
        final K key,
        final V value
    ) {
        int index = findEntryIndex(key);

        if (index < 0) {
            entries.add(
                new SimpleEntry<>(key, value)
            );

            return null;
        }

        Entry<K, V> entry = entries.get(index);
        V oldValue = entry.getValue();

        if (oldValue == null) {
            entry.setValue(value);
        }

        return oldValue;
    }

    /**
     * Removes the mapping for the specified key if present.
     *
     * @param key the key whose mapping is to be removed
     * @return the previously associated value, or {@code null} if no mapping
     *         existed
     */
    @Override
    public V remove(final Object key) {
        int index = findEntryIndex(key);

        if (index < 0) {
            return null;
        }

        return entries
            .remove(index)
            .getValue();
    }

    /**
     * Removes the mapping for the specified key only if it is currently
     * mapped to the specified value.
     *
     * @param key   the key whose mapping is to be removed
     * @param value the expected mapped value
     * @return {@code true} if the mapping was removed
     */
    @Override
    public boolean remove(
        final Object key,
        final Object value
    ) {
        int index = findEntryIndex(key);

        if (index < 0) {
            return false;
        }

        Entry<K, V> entry = entries.get(index);

        if (!Objects.equals(entry.getValue(), value)) {
            return false;
        }

        entries.remove(index);
        return true;
    }

    /**
     * Replaces the value associated with the specified key if present.
     *
     * @param key   the key whose value is to be replaced
     * @param value the replacement value
     * @return the previous value, or {@code null} if no mapping exists
     */
    @Override
    public V replace(
        final K key,
        final V value
    ) {
        int index = findEntryIndex(key);

        if (index < 0) {
            return null;
        }

        return entries
            .get(index)
            .setValue(value);
    }

    /**
     * Replaces the value associated with the specified key only if it is
     * currently mapped to the specified old value.
     *
     * @param key      the key whose value is to be replaced
     * @param oldValue the expected current value
     * @param newValue the replacement value
     * @return {@code true} if the value was replaced
     */
    @Override
    public boolean replace(
        final K key,
        final V oldValue,
        final V newValue
    ) {
        int index = findEntryIndex(key);

        if (index < 0) {
            return false;
        }

        Entry<K, V> entry = entries.get(index);

        if (!Objects.equals(entry.getValue(), oldValue)) {
            return false;
        }

        entry.setValue(newValue);
        return true;
    }

    /**
     * Computes and stores a value for the specified key if it is not already
     * associated with a non-null value.
     *
     * <p>The key is searched for only once.</p>
     *
     * @param key             the key whose value is to be computed
     * @param mappingFunction the function used to compute a value
     * @return the existing or computed value
     * @throws NullPointerException if {@code mappingFunction} is {@code null}
     */
    @Override
    public V computeIfAbsent(
        final K key,
        @NotNull final Function<? super K, ? extends V> mappingFunction
    ) {
        Objects.requireNonNull(
            mappingFunction,
            "mappingFunction"
        );

        int index = findEntryIndex(key);

        if (index >= 0) {
            Entry<K, V> entry = entries.get(index);
            V oldValue = entry.getValue();

            if (oldValue != null) {
                return oldValue;
            }

            V newValue =
                mappingFunction.apply(entry.getKey());

            if (newValue != null) {
                entry.setValue(newValue);
            }

            return newValue;
        }

        V newValue =
            mappingFunction.apply(key);

        if (newValue != null) {
            entries.add(
                new SimpleEntry<>(key, newValue)
            );
        }

        return newValue;
    }

    /**
     * Computes a new value for the specified key if it is currently
     * associated with a non-null value.
     *
     * <p>The key is searched for only once.</p>
     *
     * @param key               the key whose value is to be recomputed
     * @param remappingFunction the function used to compute the new value
     * @return the new value, or {@code null} if no mapping remains
     * @throws NullPointerException if {@code remappingFunction} is
     *                              {@code null}
     */
    @Override
    public V computeIfPresent(
        final K key,
        @NotNull final BiFunction<? super K, ? super V, ? extends V> remappingFunction
    ) {
        Objects.requireNonNull(
            remappingFunction,
            "remappingFunction"
        );

        int index = findEntryIndex(key);

        if (index < 0) {
            return null;
        }

        Entry<K, V> entry = entries.get(index);
        V oldValue = entry.getValue();

        if (oldValue == null) {
            return null;
        }

        V newValue = remappingFunction.apply(
            entry.getKey(),
            oldValue
        );

        if (newValue == null) {
            entries.remove(index);
            return null;
        }

        entry.setValue(newValue);
        return newValue;
    }

    /**
     * Computes a new mapping for the specified key.
     *
     * <p>The key is searched for only once.</p>
     *
     * @param key               the key whose mapping is to be computed
     * @param remappingFunction the function used to compute the new value
     * @return the resulting value, or {@code null} if no mapping remains
     * @throws NullPointerException if {@code remappingFunction} is
     *                              {@code null}
     */
    @Override
    public V compute(
        final K key,
        @NotNull final BiFunction<? super K, ? super V, ? extends V> remappingFunction
    ) {
        Objects.requireNonNull(
            remappingFunction,
            "remappingFunction"
        );

        int index = findEntryIndex(key);

        if (index >= 0) {
            Entry<K, V> entry = entries.get(index);

            V newValue = remappingFunction.apply(
                entry.getKey(),
                entry.getValue()
            );

            if (newValue == null) {
                entries.remove(index);
                return null;
            }

            entry.setValue(newValue);
            return newValue;
        }

        V newValue =
            remappingFunction.apply(key, null);

        if (newValue != null) {
            entries.add(
                new SimpleEntry<>(key, newValue)
            );
        }

        return newValue;
    }

    /**
     * Merges the specified value with the value currently associated with the
     * specified key.
     *
     * <p>The key is searched for only once.</p>
     *
     * @param key               the key whose value is to be merged
     * @param value             the non-null value to merge
     * @param remappingFunction the function used to combine values
     * @return the resulting value, or {@code null} if the mapping was removed
     * @throws NullPointerException if {@code value} or
     *                              {@code remappingFunction} is {@code null}
     */
    @Override
    public V merge(
        final K key,
        @NotNull final V value,
        @NotNull final BiFunction<? super V, ? super V, ? extends V> remappingFunction
    ) {
        Objects.requireNonNull(
            value,
            "value"
        );

        Objects.requireNonNull(
            remappingFunction,
            "remappingFunction"
        );

        int index = findEntryIndex(key);

        if (index < 0) {
            entries.add(
                new SimpleEntry<>(key, value)
            );

            return value;
        }

        Entry<K, V> entry = entries.get(index);
        V oldValue = entry.getValue();

        if (oldValue == null) {
            entry.setValue(value);
            return value;
        }

        V newValue =
            remappingFunction.apply(oldValue, value);

        if (newValue == null) {
            entries.remove(index);
            return null;
        }

        entry.setValue(newValue);
        return newValue;
    }

    /**
     * Removes all mappings from this map.
     */
    @Override
    public void clear() {
        entries.clear();
    }

    /**
     * Returns a set view of the mappings contained in this map.
     *
     * <p>The returned set is backed by this map. Removing entries from the
     * iterator or invoking {@link Set#clear()} modifies this map.</p>
     *
     * @return a set view of the mappings
     */
    @Override
    @NotNull
    public Set<Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new EntrySet();
        }

        return entrySet;
    }

    /**
     * Finds the index of the entry whose key is considered equal to the
     * specified key.
     *
     * @param key the candidate key
     * @return the index of the matching entry, or {@code -1} if no matching
     *         entry exists
     */
    private int findEntryIndex(final Object key) {
        for (int i = 0; i < entries.size(); i++) {
            Entry<K, V> entry = entries.get(i);

            if (keyEquals(entry.getKey(), key)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Determines whether the specified stored key and candidate key are equal
     * according to this map's equalator.
     *
     * <p>If the candidate key is not compatible with the type expected by the
     * configured {@link Equalator}, a {@link ClassCastException} may be
     * propagated. This is permitted by the {@link Map} contract and avoids
     * accidentally suppressing exceptions thrown from inside a custom
     * equalator implementation.</p>
     *
     * @param storedKey    the key stored in this map
     * @param candidateKey the candidate key
     * @return {@code true} if the keys are considered equal
     */
    @SuppressWarnings("unchecked")
    private boolean keyEquals(
        final K storedKey,
        final Object candidateKey
    ) {
        return equalator.equals(
            storedKey,
            (K) candidateKey
        );
    }

    /**
     * Entry-set view backed by this map.
     */
    private final class EntrySet
        extends AbstractSet<Entry<K, V>> {

        /**
         * Returns an iterator over the mappings in insertion order.
         *
         * @return the entry iterator
         */
        @Override
        @NotNull
        public Iterator<Entry<K, V>> iterator() {
            return entries.iterator();
        }

        /**
         * Returns the number of mappings in this set.
         *
         * @return the number of mappings
         */
        @Override
        public int size() {
            return entries.size();
        }

        /**
         * Returns whether this entry set contains the specified mapping.
         *
         * <p>Key equality is determined using the enclosing map's
         * {@link Equalator}; value equality uses
         * {@link Objects#equals(Object, Object)}.</p>
         *
         * @param object the object whose presence is to be tested
         * @return {@code true} if a matching mapping exists
         */
        @Override
        public boolean contains(final Object object) {
            if (!(object instanceof Entry<?, ?> candidate)) {
                return false;
            }

            int index =
                findEntryIndex(candidate.getKey());

            if (index < 0) {
                return false;
            }

            return Objects.equals(
                entries.get(index).getValue(),
                candidate.getValue()
            );
        }

        /**
         * Removes the specified mapping if present.
         *
         * <p>Key equality is determined using the enclosing map's
         * {@link Equalator}; value equality uses
         * {@link Objects#equals(Object, Object)}.</p>
         *
         * @param object the mapping to remove
         * @return {@code true} if a mapping was removed
         */
        @Override
        public boolean remove(final Object object) {
            if (!(object instanceof Entry<?, ?> candidate)) {
                return false;
            }

            return EqualatorMap.this.remove(
                candidate.getKey(),
                candidate.getValue()
            );
        }

        /**
         * Removes all mappings from the enclosing map.
         */
        @Override
        public void clear() {
            EqualatorMap.this.clear();
        }
    }
}
