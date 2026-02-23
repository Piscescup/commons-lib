package io.github.piscescup.util.validation;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Utility class for validating object or component state.
 *
 * <p>This class provides a set of static methods for checking whether
 * a program is currently in a valid state. If a state check fails,
 * an {@link IllegalStateException} (or a custom runtime exception)
 * will be thrown.
 *
 * <h2>Usage Examples</h2>
 *
 * <pre>{@code
 * public class Service {
 *
 *     private boolean started;
 *
 *     public void start() {
 *         StateCheck.checkState(!started, "Service already started");
 *         started = true;
 *     }
 * }
 * }</pre>
 *
 * @author REN YuanTong
 * @since 1.0.2
 */
public final class StateCheck {

    /**
     * Prevents instantiation.
     *
     * @throws UnsupportedOperationException always
     */
    private StateCheck() {
        throw new UnsupportedOperationException(
            "No " + StateCheck.class.getCanonicalName() + " instance for you!"
        );
    }

    /**
     * Ensures that the given state expression is {@code true}.
     *
     * <p>If the expression is {@code false}, an {@link IllegalStateException}
     * will be thrown with an error message supplied by the given supplier.
     *
     * <p>The supplier is evaluated lazily, and will only be invoked
     * when the check fails.
     *
     * <h3>Example</h3>
     *
     * <pre>{@code
     * StateCheck.checkState(
     *     connected,
     *     () -> "Connection lost: " + remoteAddress
     * );
     * }</pre>
     *
     * @param expression            the state condition to check
     * @param errorMessageSupplier  supplier of the exception message
     *
     * @throws IllegalStateException if {@code expression} is {@code false}
     * @throws NullPointerException  if {@code errorMessageSupplier} is {@code null}
     */
    public static void checkState(
        boolean expression,
        Supplier<String> errorMessageSupplier
    ) {
        if (!expression) {
            throw new IllegalStateException(
                errorMessageSupplier.get()
            );
        }
    }

    /**
     * Ensures that the given state expression is {@code true}.
     *
     * <p>If the expression is {@code false}, an {@link IllegalStateException}
     * will be thrown with the specified error message.
     *
     * <h3>Example</h3>
     *
     * <pre>{@code
     * StateCheck.checkState(
     *     initialized,
     *     "Component is not initialized"
     * );
     * }</pre>
     *
     * @param expression   the state condition to check
     * @param errorMessage the error message, may be {@code null}
     *
     * @throws IllegalStateException if {@code expression} is {@code false}
     */
    public static void checkState(
        boolean expression,
        @Nullable String errorMessage
    ) {
        if (!expression) {
            throw new IllegalStateException(
                String.valueOf(errorMessage)
            );
        }
    }

    /**
     * Ensures that the given state expression is {@code true}.
     *
     * <p>If the expression is {@code false}, an {@link IllegalStateException}
     * will be thrown with a formatted error message.
     *
     * <p>The message is formatted using {@link String#format(String, Object...)}.
     *
     * <h3>Example</h3>
     *
     * <pre>{@code
     * StateCheck.checkState(
     *     size > 0,
     *     "Invalid size: %d",
     *     size
     * );
     * }</pre>
     *
     * @param expression     the state condition to check
     * @param errorTemplate  the format string
     * @param args           arguments referenced by the format specifiers
     *
     * @throws IllegalStateException if {@code expression} is {@code false}
     * @throws NullPointerException  if {@code errorTemplate} is {@code null}
     */
    public static void checkState(
        boolean expression,
        String errorTemplate,
        Object... args
    ) {
        if (!expression) {
            throw new IllegalStateException(
                String.format(errorTemplate, args)
            );
        }
    }

    /**
     * Ensures that the given state expression is {@code true},
     * otherwise throws a custom runtime exception.
     *
     * <p>If the expression is {@code false}, an exception provided by
     * the given supplier will be thrown.
     *
     * <p>This method is useful when callers need fine-grained control
     * over the exception type.
     *
     * <h3>Example</h3>
     *
     * <pre>{@code
     * StateCheck.checkStateOrThrow(
     *     permissionGranted,
     *     () -> new SecurityException("Access denied")
     * );
     * }</pre>
     *
     * @param expression         the state condition to check
     * @param exceptionSupplier supplier of the exception to be thrown
     * @param <E>                the type of runtime exception
     *
     * @throws E                    if {@code expression} is {@code false}
     * @throws NullPointerException if {@code exceptionSupplier} is {@code null}
     */
    public static <E extends RuntimeException> void checkStateOrThrow(
        boolean expression,
        Supplier<E> exceptionSupplier
    ) {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Ensures that the given state expression is {@code true}.
     *
     * <p>If the expression is {@code false}, an {@link IllegalStateException}
     * will be thrown without an error message.
     *
     * <h3>Example</h3>
     *
     * <pre>{@code
     * StateCheck.checkState(running);
     * }</pre>
     *
     * @param expression the state condition to check
     *
     * @throws IllegalStateException if {@code expression} is {@code false}
     */
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

}