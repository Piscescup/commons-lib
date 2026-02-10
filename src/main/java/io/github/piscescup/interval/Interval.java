package io.github.piscescup.interval;

import java.io.Serializable;

/**
 * Represents a (possibly open/closed) interval over an ordered domain.
 *
 * @param <T> the element type
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public interface Interval extends Serializable {

    /**
     * Returns the {@link IntervalType} describing endpoint inclusiveness.
     *
     * <p>The interval type determines whether the start/end bounds are inclusive
     * or exclusive. Convenience methods {@link #isStartInclusive()} and
     * {@link #isEndInclusive()} are derived from this value.
     *
     * @return the interval type
     */
    IntervalType getIntervalType();

    /**
     * Returns {@code true} if the start endpoint of this interval is inclusive.
     *
     * @return {@code true} if the start is inclusive
     */
    default boolean isStartInclusive() {
        return getIntervalType().isStartInclusive();
    }

    /**
     * Returns {@code true} if the end endpoint of this interval is inclusive.
     *
     * @return {@code true} if the end is inclusive
     */
    default boolean isEndInclusive() {
        return getIntervalType().isEndInclusive();
    }


    /**
     * Returns {@code true} if the start endpoint of this interval is exclusive.
     * @return {@code true} if the start is exclusive
     */
    default boolean isStartExclusive() {
        return !isStartInclusive();
    }

    /**
     * Returns {@code true} if the end endpoint of this interval is exclusive.
     * @return {@code true} if the end is exclusive
     */
    default boolean isEndExclusive() {
        return !isEndInclusive();
    }
}
