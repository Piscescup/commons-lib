package io.github.piscescup.collection;

import io.github.piscescup.interfaces.Equalator;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A {@link Map} implementation that determines key equality using a specified
 * {@link Equalator}.
 *
 * <p>Unlike {@link java.util.HashMap}, this map does not use
 * {@link Object#equals(Object)} to determine whether two keys are equal.
 * Instead, key equality is determined exclusively by the supplied
 * {@code Equalator}.</p>
 *
 * <p>Since {@link Equalator} defines only an equality operation and does not
 * provide a corresponding hash function, this implementation performs a
 * linear search when locating keys. Consequently, key-based operations such
 * as {@link #get(Object)}, {@link #put(Object, Object)},
 * {@link #containsKey(Object)}, and {@link #remove(Object)} have
 * {@code O(n)} time complexity.</p>
 *
 * <p>This implementation permits {@code null} values. Whether {@code null}
 * keys are supported depends on the supplied {@link Equalator}. However,
 * callers may impose additional restrictions on keys.</p>
 *
 * <p>This implementation is not synchronized. If multiple threads access an
 * instance concurrently and at least one thread modifies the map, external
 * synchronization is required.</p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author REN YuanTong
 * @since 1.1.0
 */
public final class EqualatorMap<K, V> extends AbstractMap<K, V> {

    /**
     * The equalator used to determine key equality.
     */
    @NotNull
    private final Equalator<? super K> equalator;

    /**
     * The entries contained in this map.
     */
    @NotNull
    private final List<Entry<K, V>> entries;

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
        this.equalator = Objects.requireNonNull(equalator, "equalator");
        this.entries = new ArrayList<>();
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

        this.equalator = Objects.requireNonNull(equalator, "equalator");
        this.entries = new ArrayList<>(initialCapacity);
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
        return findEntry(key) != null;
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
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.getValue();
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
    public V put(final K key, final V value) {
        Entry<K, V> entry = findEntry(key);

        if (entry != null) {
            return entry.setValue(value);
        }

        entries.add(new SimpleEntry<>(key, value));
        return null;
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
        Iterator<Entry<K, V>> iterator = entries.iterator();

        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();

            if (keyEquals(entry.getKey(), key)) {
                V oldValue = entry.getValue();
                iterator.remove();
                return oldValue;
            }
        }

        return null;
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
     * @return a set view of the mappings
     */
    @Override
    @NotNull
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<>() {

            @Override
            @NotNull
            public Iterator<Entry<K, V>> iterator() {
                return entries.iterator();
            }

            @Override
            public int size() {
                return entries.size();
            }

            @Override
            public void clear() {
                entries.clear();
            }
        };
    }

    /**
     * Finds the entry whose key is considered equal to the specified key.
     *
     * @param key the key to search for
     * @return the matching entry, or {@code null} if no matching entry exists
     */
    private Entry<K, V> findEntry(final Object key) {
        for (Entry<K, V> entry : entries) {
            if (keyEquals(entry.getKey(), key)) {
                return entry;
            }
        }

        return null;
    }

    /**
     * Determines whether the specified stored key and candidate key are equal
     * according to this map's equalator.
     *
     * @param storedKey   the key stored in this map
     * @param candidateKey the candidate key
     * @return {@code true} if the keys are considered equal
     */
    @SuppressWarnings("unchecked")
    private boolean keyEquals(
        final K storedKey,
        final Object candidateKey
    ) {
        try {
            return equalator.equals(
                storedKey,
                (K) candidateKey
            );
        } catch (ClassCastException ignored) {
            return false;
        }
    }
}
