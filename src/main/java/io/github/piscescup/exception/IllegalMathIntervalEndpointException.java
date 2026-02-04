package io.github.piscescup.exception;

/**
 * @author REN YuanTong
 * @since
 */
public class IllegalMathIntervalEndpointException extends RuntimeException {

    public <V> IllegalMathIntervalEndpointException(V start, V end) {
        super("Illegal interval endpoints: [start=" + start + "] > [end=" + end + "]");
    }
}
