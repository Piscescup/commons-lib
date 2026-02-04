package io.github.piscescup.exception;

/**
 * Thrown to indicate that an illegal interval has been attempted to be created
 * with a start point greater than the end point.
 *
 * <p>This exception is intended to be used by classes that represent intervals
 * when the provided start and end points do not form a valid interval, specifically
 * when the start point is greater than the end point. It extends {@link RuntimeException}
 * as it typically indicates a programming error rather than a recoverable condition.</p>
 *
 * @author REN Yuantong
 * @since 1.0.0
 */
public class IllegalMathIntervalEndpointException extends RuntimeException {

    /**
     * Thrown to indicate that an illegal interval has been attempted to be created
     * with a start point greater than the end point.
     *
     * <p>This exception is intended to be used by classes that represent intervals
     * when the provided start and end points do not form a valid interval, specifically
     * when the start point is greater than the end point.</p>
     *
     * @param <V> the type of the interval endpoints
     * @param start the start point of the interval
     * @param end the end point of the interval
     */
    public <V> IllegalMathIntervalEndpointException(V start, V end) {
        super("Illegal interval endpoints: [start=" + start + "] > [end=" + end + "]");
    }
}
