package io.github.piscescup.counter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * A simple mutable counter implementation backed by a {@code long} value.
 *
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public class SimpleCounter
    extends Number
    implements Comparable<SimpleCounter>, Counter, Serializable
{
    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 157788711L;

    /**
     * The current value of this counter.
     */
    private long value;

    /**
     * The immutable default value used when resetting this counter.
     */
    private final long defaultValue;

    /**
     * Creates a new counter with the specified initial value.
     *
     * @param value the initial value of the counter
     */
    private SimpleCounter(final long value) {
        this.value = value;
        this.defaultValue = value;
    }

    /**
     * Creates a new counter with an initial value of {@code 0}.
     *
     * @return a new {@code SimpleCounter} instance
     */
    @Contract(" -> new")
    public static @NotNull SimpleCounter create() {
        return new SimpleCounter(0);
    }

    /**
     * Creates a new counter with the specified initial value.
     *
     * @param value the initial value
     * @return a new {@code SimpleCounter} instance
     */
    public static @NotNull SimpleCounter create(final long value) {
        return new SimpleCounter(value);
    }

    /**
     * Creates a new counter using the {@code long} value of the given {@link Number}.
     *
     * @param value the number providing the initial value
     * @return a new {@code SimpleCounter} instance
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @Contract("_ -> new")
    public static @NotNull SimpleCounter createFor(final @NotNull Number value) {
        return new SimpleCounter(value.longValue());
    }

    /**
     * Creates a new counter by parsing the given string as a {@code long}.
     *
     * @param value the string to parse
     * @return a new {@code SimpleCounter} instance
     * @throws NumberFormatException if the string cannot be parsed as a {@code long}
     */
    @Contract("_ -> new")
    public static @NotNull SimpleCounter createFor(final String value) {
        return new SimpleCounter(Long.parseLong(value));
    }

    /**
     * Returns the current value of this counter.
     *
     * @return the current counter value
     */
    @Override
    public long getValue() {
        return this.value;
    }

    /**
     * Increments this counter by one.
     */
    @Override
    public void increment() {
        this.value++;
    }

    /**
     * Decrements this counter by one.
     */
    @Override
    public void decrement() {
        this.value--;
    }

    /**
     * Sets this counter to the specified value.
     *
     * @param value the new value
     */
    @Override
    public void set(long value) {
        this.value = value;
    }

    /**
     * Resets this counter to its initial default value.
     */
    @Override
    public void reset() {
        this.value = defaultValue;
    }

    /**
     * Adds the given value to this counter and returns the updated value.
     *
     * @param value the value to add
     * @return the updated counter value
     */
    @Override
    public long addAndGet(long value) {
        this.value += value;
        return this.value;
    }

    /**
     * Subtracts the given value from this counter and returns the updated value.
     *
     * @param value the value to subtract
     * @return the updated counter value
     */
    @Override
    public long subtractAndGet(long value) {
        this.value -= value;
        return this.value;
    }

    /**
     * Returns the current value of this counter.
     *
     * @return the current counter value
     */
    @Override
    public long get() {
        return this.value;
    }

    /**
     * Increments this counter by one and returns the updated value.
     *
     * @return the incremented value
     */
    @Override
    public long incrementAndGet() {
        return ++this.value;
    }

    /**
     * Decrements this counter by one and returns the updated value.
     *
     * @return the decremented value
     */
    @Override
    public long decrementAndGet() {
        return --this.value;
    }

    /**
     * Compares this counter with the specified counter for order.
     *
     * @param other the counter to be compared
     * @return a negative integer, zero, or a positive integer as this counter
     *         is less than, equal to, or greater than the specified counter
     */
    @Override
    public int compareTo(@NotNull SimpleCounter other) {
        return Long.compare(this.value, other.value);
    }

    /**
     * Returns the value of this counter as an {@code int}.
     *
     * @return the numeric value represented by this counter after conversion to {@code int}
     */
    @Override
    public int intValue() {
        return (int) this.value;
    }

    /**
     * Returns the value of this counter as a {@code long}.
     *
     * @return the numeric value represented by this counter
     */
    @Override
    public long longValue() {
        return this.value;
    }

    /**
     * Returns the value of this counter as a {@code float}.
     *
     * @return the numeric value represented by this counter after conversion to {@code float}
     */
    @Override
    public float floatValue() {
        return this.value;
    }

    /**
     * Returns the value of this counter as a {@code double}.
     *
     * @return the numeric value represented by this counter after conversion to {@code double}
     */
    @Override
    public double doubleValue() {
        return this.value;
    }
}
