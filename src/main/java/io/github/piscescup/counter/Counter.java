package io.github.piscescup.counter;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a contract for a numeric counter.
 */
public interface Counter {

    /**
     * Increments the current value of the counter by one.
     * This operation is thread-safe and can be used in a multi-threaded environment.
     */
    void increment();

    /**
     * Decrements the current value of the counter by one.
     * This operation is thread-safe and can be used in a multi-threaded environment.
     */
    void decrement();

    /**
     * Gets the current value of the counter.
     * @return the current value.
     */
    long getValue();

    /**
     * Sets the counter to a new value.
     * @param newValue the value to set.
     */
    void set(long newValue);

    /**
     * Atomically increments the value by one and returns the new value.
     * @return the updated value.
     */
    long incrementAndGet();

    /**
     * Atomically decrements the value by one and returns the new value.
     * @return the updated value.
     */
    long decrementAndGet();

    /**
     * Atomically adds the given amount and returns the new value.
     * @param delta the amount to add.
     * @return the updated value.
     */
    long addAndGet(long delta);

    /**
     * Atomically subtracts the given amount and returns the new value.
     * @param delta the amount to subtract.
     * @return the updated value.
     */
    long subtractAndGet(long delta);

    /**
     * Resets the counter to its initial value.
     */
    void reset();

    /**
     * Retrieves the current value of the counter.
     * @return the current value of the counter.
     */
    long get();

    @Contract(" -> new")
    static @NotNull Counter atomic() {
        return AtomicCounter.create();
    }

    @Contract(" -> new")
    static @NotNull Counter simple() {
        return SimpleCounter.create();
    }

}