package io.github.piscescup.exception;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Thrown to indicate an illegal or incomplete configuration of a builder pattern.
 *
 * <p>This exception is intended to be used by builder implementations when
 * required configuration elements are missing, inconsistent, or otherwise
 * invalid at build time.</p>
 *
 * <p>It is an unchecked exception extending {@link RuntimeException}, as
 * builder configuration errors typically represent programming mistakes
 * rather than recoverable conditions.</p>
 *
 * <h2>Typical use cases</h2>
 * <ul>
 *   <li>A required field was not provided before calling {@code build()}.</li>
 *   <li>Mutually exclusive builder options were set simultaneously.</li>
 *   <li>A builder is used in an invalid state or order.</li>
 * </ul>
 *
 * <h2>Example</h2>
 * <pre>{@code
 * public Product build() {
 *     if (name == null) {
 *         throw IllegalBuilderPatternConfigurationException.missing("name");
 *     }
 *     return new Product(name, price);
 * }
 * }</pre>
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public class IllegalBuilderPatternConfigurationException extends RuntimeException {

    /**
     * Constructs a new exception with no detail message.
     */
    public IllegalBuilderPatternConfigurationException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public IllegalBuilderPatternConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public IllegalBuilderPatternConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public IllegalBuilderPatternConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates an exception indicating that a required builder configuration
     * field is missing.
     *
     * <p>This is a convenience factory method for producing a standardized
     * error message.</p>
     *
     * @param fieldName the name of the missing configuration field
     * @return a new {@code IllegalBuilderPatternConfigurationException}
     */
    @Contract("_ -> new")
    public static @NotNull IllegalBuilderPatternConfigurationException missing(String fieldName) {
        return new IllegalBuilderPatternConfigurationException(
            "Missing required builder configuration: " + fieldName
        );
    }
}
