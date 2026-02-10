/**
 * Provides classes for intervals.
 * <p>
 *     Below is the {@code Interval} system:
 * </p>
 * <pre>
 * Interval
 * |
 * |
 * |___ObjectInterval        // The interval of objects.
 * |     |
 * |     |
 * |     |___NaturalOrderedInterval    // Sorted by natural order, which requires {@code T} to implement {@code Comparable<T>}.
 * |     |___ComparatorOrderedInterval // Sorted by custom comparator, which need to provide a {@code Comparator<T>} .
 * |
 * |____PrimitiveInterval    // The interval of primitive types, which can be sorted by natural order.
 *       |
 *       |
 *       |___ByteInterval    // The interval of {@code byte} values, which can be sorted by natural order.
 *       |___ShortInterval   // The interval of {@code short} values, which can be sorted by natural order.
 *       |___IntInterval     // The interval of {@code int} values, which can be sorted by natural order.
 *       |___LongInterval    // The interval of {@code long} values, which can be sorted by natural order.
 *       |___FloatInterval   // The interval of {@code float} values, which can be sorted by natural order.
 *       |___DoubleInterval  // The interval of {@code double} values, which can be sorted by natural order.
 *       |___CharInterval    // The interval of {@code char} values, which can be sorted by natural order.
 * </pre>
 */
package io.github.piscescup.interval;