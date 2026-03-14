package io.github.piscescup.util.validation;


import java.util.function.Supplier;

/**
 * Utility class for validating arguments.
 *
 * <p>
 * Each validation method throws {@link IllegalArgumentException}
 * when a constraint is violated.
 *
 * <h2>Example</h2>
 * <pre>{@code
 * public void setSize(int size) {
 *     ArgumentCheck.requiresPositive(size);
 *     this.size = size;
 * }
 *
 * public void setRatio(double ratio) {
 *     ArgumentCheck.requiresNonNegative(
 *         ratio,
 *         () -> "Ratio must be >= 0 but was: " + ratio
 *     );
 * }
 * }</pre>
 *
 * <p>
 * This class cannot be instantiated.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public final class ArgumentCheck {

    private ArgumentCheck() {
        throw new UnsupportedOperationException(
            "No instance of " + ArgumentCheck.class.getCanonicalName() + " for you."
        );
    }

    /* ============================================================ */
    /* Boolean checks                                               */
    /* ============================================================ */

    /**
     * Ensures that the given expression is {@code true}.
     *
     * @param expression the expression to validate
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void requiresTrue(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("Expression must be true.");
        }
    }

    /**
     * Ensures that the given expression is {@code true}.
     *
     * @param expression the expression to validate
     * @param message custom error message
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void requiresTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures that the given expression is {@code true}.
     *
     * <p>
     * The message supplier is only invoked if validation fails.
     *
     * @param expression the expression to validate
     * @param supplier supplies the error message lazily
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void requiresTrue(boolean expression, Supplier<String> supplier) {
        if (!expression) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    /**
     * Ensures that the given expression is {@code false}.
     *
     * @param expression the expression to validate
     * @throws IllegalArgumentException if {@code expression} is {@code true}
     */
    public static void requiresFalse(boolean expression) {
        if (expression) {
            throw new IllegalArgumentException("Expression must be false.");
        }
    }

    /**
     * Ensures that the given expression is {@code false}.
     *
     * @param expression the expression to validate
     * @param message custom error message
     * @throws IllegalArgumentException if {@code expression} is {@code true}
     */
    public static void requiresFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures that the given expression is {@code false}.
     *
     * <p>
     * The message supplier is evaluated only when validation fails.
     *
     * @param expression the expression to validate
     * @param supplier supplies the error message lazily
     * @throws IllegalArgumentException if {@code expression} is {@code true}
     */
    public static void requiresFalse(boolean expression, Supplier<String> supplier) {
        if (expression) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    /* ============================================================ */
    /* Positive checks                                              */
    /* ============================================================ */

    /**
     * Ensures that the given integer is strictly positive ({@code > 0}).
     *
     * @param number the value to validate
     * @throws IllegalArgumentException if {@code number <= 0}
     */
    public static void requiresPositive(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Value must be positive.");
        }
    }

    /**
     * Ensures that the given integer is strictly positive ({@code > 0}).
     *
     * @param number the value to validate
     * @param message custom error message
     * @throws IllegalArgumentException if {@code number <= 0}
     */
    public static void requiresPositive(int number, String message) {
        if (number <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures that the given integer is strictly positive ({@code > 0}).
     *
     * <p>
     * The message supplier is invoked only if validation fails.
     *
     * @param number the value to validate
     * @param supplier supplies the error message lazily
     * @throws IllegalArgumentException if {@code number <= 0}
     */
    public static void requiresPositive(int number, Supplier<String> supplier) {
        if (number <= 0) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    /* ============================================================ */
    /* Negative checks                                              */
    /* ============================================================ */

    /**
     * Ensures that the given integer is strictly negative ({@code < 0}).
     *
     * @param number the value to validate
     * @throws IllegalArgumentException if {@code number >= 0}
     */
    public static void requiresNegative(int number) {
        if (number >= 0) {
            throw new IllegalArgumentException("Value must be negative.");
        }
    }

    /**
     * Ensures that the given integer is strictly negative ({@code < 0}).
     *
     * @param number the value to validate
     * @param message custom error message
     * @throws IllegalArgumentException if {@code number >= 0}
     */
    public static void requiresNegative(int number, String message) {
        if (number >= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures that the given integer is strictly negative ({@code < 0}).
     *
     * @param number the value to validate
     * @param supplier supplies the error message lazily
     * @throws IllegalArgumentException if {@code number >= 0}
     */
    public static void requiresNegative(int number, Supplier<String> supplier) {
        if (number >= 0) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    /* ============================================================ */
    /* Non-negative checks                                          */
    /* ============================================================ */

    /**
     * Ensures that the given integer is non-negative ({@code >= 0}).
     *
     * @param number the value to validate
     * @throws IllegalArgumentException if {@code number < 0}
     */
    public static void requiresNonNegative(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Value must be non-negative.");
        }
    }

    /**
     * Ensures that the given integer is non-positive ({@code <= 0}).
     *
     * @param number the value to validate
     * @throws IllegalArgumentException if {@code number > 0}
     */
    public static void requiresNonPositive(int number) {
        if (number > 0) {
            throw new IllegalArgumentException("Value must be non-positive.");
        }
    }
}
