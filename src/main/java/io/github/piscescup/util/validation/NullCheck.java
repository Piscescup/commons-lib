package io.github.piscescup.util.validation;

import java.util.function.Supplier;

/**
 * Utility class providing null-related validation methods.
 *
 * <p>
 * This class offers a collection of <em>fail-fast</em> utilities for validating
 * {@code null} values, enforcing non-null constraints, and ensuring that
 * {@link Iterable iterables} or arrays do not contain {@code null} elements.
 * </p>
 *
 * <p>
 * All validation methods in this class throw {@link RuntimeException runtime exceptions}
 * (typically {@link NullPointerException}) when validation fails.
 * </p>
 *
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public final class NullCheck {

    private static final String DEFAULT_NULL_MESSAGE =
        "Object must not be null";

    private static final String DEFAULT_ITERABLE_NULL_MESSAGE =
        "Iterable must not contain null elements";

    private static final String DEFAULT_ARRAY_NULL_MESSAGE =
        "Array must not contain null elements";

    private NullCheck() {}

    /**
     * Returns {@code true} if the given object reference is {@code null}.
     *
     * <pre>{@code
     * if (NullCheck.isNull(value)) {
     *     // handle null
     * }
     * }</pre>
     *
     * @param obj the object reference to check
     * @return {@code true} if {@code obj} is {@code null}
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Returns {@code true} if the given object reference is not {@code null}.
     *
     * <pre>{@code
     * if (NullCheck.nonNull(config)) {
     *     use(config);
     * }
     * }</pre>
     *
     * @param obj the object reference to check
     * @return {@code true} if {@code obj} is not {@code null}
     */
    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    // ---------------------------------------------------------------------
    // requireNonNull
    // ---------------------------------------------------------------------

    /**
     * Ensures that the given object reference is not {@code null}.
     *
     * <p>
     * This method is typically used to validate method parameters
     * or internal state before further processing.
     * </p>
     *
     * <pre>{@code
     * this.id = NullCheck.requireNonNull(id);
     * }</pre>
     *
     * @param obj the object reference to check
     * @param <T> the object type
     * @return the validated non-null object
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null) {
            throw new NullPointerException(DEFAULT_NULL_MESSAGE);
        }
        return obj;
    }

    /**
     * Ensures that the given object reference is not {@code null}, using
     * the provided message for the thrown exception.
     *
     * <pre>{@code
     * NullCheck.requireNonNull(settings, "Item settings must not be null");
     * }</pre>
     *
     * @param obj the object reference to check
     * @param message the exception message to use if validation fails
     * @param <T> the object type
     * @return the validated non-null object
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(
                message != null ? message : DEFAULT_NULL_MESSAGE
            );
        }
        return obj;
    }

    /**
     * Ensures that the given object reference is not {@code null}, using
     * a lazily-evaluated message supplier.
     *
     * <p>
     * This overload is recommended when constructing the error message
     * is expensive or rarely needed.
     * </p>
     *
     * <pre>{@code
     * NullCheck.requireNonNull(
     *     provider,
     *     () -> "DataGen provider '" + id + "' must not be null"
     * );
     * }</pre>
     *
     * @param obj the object reference to check
     * @param messageSupplier supplier providing the exception message
     * @param <T> the object type
     * @return the validated non-null object
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(
        T obj,
        Supplier<String> messageSupplier
    ) {
        if (obj == null) {
            String msg = messageSupplier == null
                ? DEFAULT_NULL_MESSAGE
                : messageSupplier.get();
            throw new NullPointerException(
                msg != null ? msg : DEFAULT_NULL_MESSAGE
            );
        }
        return obj;
    }

    // ---------------------------------------------------------------------
    // requireNonNullOrElse
    // ---------------------------------------------------------------------

    /**
     * Returns the given object if non-null; otherwise returns {@code defaultObj}.
     *
     * <pre>{@code
     * Identifier id = NullCheck.requireNonNullOrElse(
     *     providedId,
     *     DEFAULT_ID
     * );
     * }</pre>
     *
     * @param obj the object reference to check
     * @param defaultObj the fallback value to return if {@code obj} is {@code null}
     * @param <T> the object type
     * @return {@code obj} if non-null; otherwise {@code defaultObj}
     */
    public static <T> T requireNonNullOrElse(T obj, T defaultObj) {
        return obj != null ? obj : defaultObj;
    }

    /**
     * Returns the given object if non-null; otherwise returns the value
     * supplied by {@code defaultSupplier}.
     *
     * <pre>{@code
     * Item.Settings settings = NullCheck.requireNonNullOrElseGet(
     *     providedSettings,
     *     Item.Settings::new
     * );
     * }</pre>
     *
     * @param obj the object reference to check
     * @param defaultSupplier supplier providing a fallback value
     * @param <T> the object type
     * @return {@code obj} if non-null; otherwise the supplied value
     * @throws NullPointerException if {@code defaultSupplier} is {@code null}
     */
    public static <T> T requireNonNullOrElse(
        T obj,
        Supplier<? extends T> defaultSupplier
    ) {
        if (obj != null) return obj;
        requireNonNull(defaultSupplier, "defaultSupplier must not be null");
        return defaultSupplier.get();
    }

    // ---------------------------------------------------------------------
    // requireNonNullOrThrow
    // ---------------------------------------------------------------------

    /**
     * Returns the given object if non-null; otherwise throws the
     * {@link RuntimeException} supplied by {@code exceptionSupplier}.
     *
     * <pre>{@code
     * RegistryEntry<?> entry = NullCheck.requireNonNullOrThrow(
     *     lookup(id),
     *     () -> new IllegalStateException("Registry entry not found: " + id)
     * );
     * }</pre>
     *
     * @param obj the object reference to check
     * @param exceptionSupplier supplier providing the exception to throw
     * @param <T> the object type
     * @return the validated non-null object
     * @throws RuntimeException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNullOrThrow(
        T obj,
        Supplier<? extends RuntimeException> exceptionSupplier
    ) {
        if (obj != null) return obj;
        requireNonNull(
            exceptionSupplier,
            "exceptionSupplier must not be null"
        );
        throw exceptionSupplier.get();
    }

    // ---------------------------------------------------------------------
    // Iterable checks
    // ---------------------------------------------------------------------

    /**
     * Returns {@code true} if the given iterable is {@code null}
     * or contains at least one {@code null} element.
     *
     * <pre>{@code
     * if (NullCheck.isNullOrContainsNull(entries)) {
     *     throw new IllegalArgumentException("Invalid entries");
     * }
     * }</pre>
     *
     * @param iterable the iterable to check
     * @return {@code true} if null or containing a null element
     */
    public static boolean isNullOrContainsNull(Iterable<?> iterable) {
        if (iterable == null) return true;
        for (Object o : iterable) {
            if (o == null) return true;
        }
        return false;
    }

    /**
     * Ensures that the given iterable and all of its elements are non-null.
     *
     * <pre>{@code
     * NullCheck.requireAllNonNull(entries);
     * }</pre>
     *
     * @param iterable the iterable to validate
     * @param <T> the element type
     * @return the validated iterable
     * @throws NullPointerException if the iterable or any element is {@code null}
     */
    public static <T> Iterable<T> requireAllNonNull(Iterable<T> iterable) {
        return requireAllNonNull(
            iterable,
            DEFAULT_ITERABLE_NULL_MESSAGE
        );
    }

    /**
     * Ensures that the given iterable and all of its elements are non-null,
     * using the provided message.
     *
     * <pre>{@code
     * NullCheck.requireAllNonNull(
     *     providers,
     *     "DataGen providers must not contain null"
     * );
     * }</pre>
     *
     * @param iterable the iterable to validate
     * @param message the exception message to use
     * @param <T> the element type
     * @return the validated iterable
     * @throws NullPointerException if validation fails
     */
    public static <T> Iterable<T> requireAllNonNull(
        Iterable<T> iterable,
        String message
    ) {
        requireNonNull(iterable);
        for (T obj : iterable) {
            if (obj == null) {
                throw new NullPointerException(
                    message != null ? message : DEFAULT_ITERABLE_NULL_MESSAGE
                );
            }
        }
        return iterable;
    }

    // ---------------------------------------------------------------------
    // Array checks
    // ---------------------------------------------------------------------

    /**
     * Ensures that the given array and all of its elements are non-null.
     *
     * <pre>{@code
     * NullCheck.requireAllNonNull(arguments);
     * }</pre>
     *
     * @param array the array to validate
     * @param <T> the element type
     * @return the validated array
     * @throws NullPointerException if the array or any element is {@code null}
     */
    public static <T> T[] requireAllNonNull(T[] array) {
        return requireAllNonNull(
            array,
            DEFAULT_ARRAY_NULL_MESSAGE
        );
    }

    /**
     * Ensures that the given array and all of its elements are non-null,
     * using the provided message.
     *
     * <pre>{@code
     * NullCheck.requireAllNonNull(
     *     values,
     *     "Command arguments must not contain null"
     * );
     * }</pre>
     *
     * @param array the array to validate
     * @param message the exception message to use
     * @param <T> the element type
     * @return the validated array
     * @throws NullPointerException if validation fails
     */
    public static <T> T[] requireAllNonNull(T[] array, String message) {
        requireNonNull(array);
        for (T obj : array) {
            if (obj == null) {
                throw new NullPointerException(
                    message != null ? message : DEFAULT_ARRAY_NULL_MESSAGE
                );
            }
        }
        return array;
    }
}
