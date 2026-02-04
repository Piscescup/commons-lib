package io.github.piscescup.util;

import io.github.piscescup.counter.AtomicCounter;
import io.github.piscescup.counter.Counter;

import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility class for {@code Array}.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public final class ArrayUtils {
    private ArrayUtils() {}

    /**
     * Represents a constant value used to indicate that an index was not found.
     * This is typically used in scenarios where a search operation does not find
     * the specified element, and -1 is returned to signify the absence of a match.
     */
    public static final int INDEX_NOT_FOUND = -1;


    private static final String START_INDEX_OUT_OF_BOUNDS_MSG = "Start index out of bounds - %s";

    /**
     * Copies the given array and grows it by one.
     * <p>
     * This is a private helper method. If the input {@code array} is not null,
     * it creates a new array of the same component type with a length increased by one.
     * The original elements are copied to the new array.
     * </p>
     * <p>
     * If the input array is {@code null}, it creates a new array of length 1
     * using the provided {@code newArrayComponentType}.
     * </p>
     *
     * @param array                 the array to copy and grow, may be {@code null}.
     * @param newArrayComponentType the component type for the new array if {@code array} is {@code null}.
     * @return A new array containing the original elements plus one empty slot, or a new array of size 1 if the input was {@code null}.
     */
    private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
        if (array != null) {
            final int arrayLength = Array.getLength(array);
            final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, 1);
    }

    /**
     * Checks if the provided object is an array.
     *
     * @param obj the object to check
     * @return true if the object is an array, false otherwise
     */
    public static boolean isArray(final Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    /**
     * Gets the length of an array, handling {@code null} inputs safely.
     * <p>
     * This method can be used for any array type (primitive or object)
     * because its parameter is of type {@link Object}.
     * </p>
     * <p>
     * If the input array is {@code null}, this method returns 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.getLength({10, 20});          // 2
     * ArrayUtils.getLength{});                 // 0
     * ArrayUtils.getLength(null);              // 0
     * }</pre>
     *
     * @param array the array to get the length of, may be {@code null}.
     * @return The length of the array, or 0 if the array is {@code null}.
     * @since 1.1.2
     */
    public static int getLength(final Object array) {
        return array == null ? 0 : Array.getLength(array);
    }

    /**
     * Checks if a generic array is empty (has a length of 0).
     * <p>
     * This private helper method considers a {@code null} array as empty.
     * </p>
     *
     * @param array the array to check.
     * @return {@code true} if the array is {@code null} or its length is 0.
     */
    private static boolean isArrayEmpty(final Object array) {
        return getLength(array) == 0;
    }

    /**
     * Checks if an array of Objects is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty{"a"});       // false
     * ArrayUtils.isEmpty({});         // true
     * ArrayUtils.isEmpty(null);       // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @param <T>   the component type of the array.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static <T> boolean isEmpty(final T[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if a {@code byte} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({1});           // false
     * ArrayUtils.isEmpty({});            // true
     * ArrayUtils.isEmpty(null);          // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final byte[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if a {@code short} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({1});            // false
     * ArrayUtils.isEmpty({});             // true
     * ArrayUtils.isEmpty(null);           // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final short[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if an {@code int} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({1});          // false
     * ArrayUtils.isEmpty({});           // true
     * ArrayUtils.isEmpty(null);         // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final int[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if a {@code long} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({1L});           // false
     * ArrayUtils.isEmpty({});             // true
     * ArrayUtils.isEmpty(null);           // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final long[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if a {@code float} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({1.0F});        // false
     * ArrayUtils.isEmpty({});            // true
     * ArrayUtils.isEmpty(null);          // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final float[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if a {@code double} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({1.0});             // false
     * ArrayUtils.isEmpty({});                // true
     * ArrayUtils.isEmpty(null);              // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final double[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if a {@code char} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({'a'});           // false
     * ArrayUtils.isEmpty({});              // true
     * ArrayUtils.isEmpty(null);            // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final char[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if a {@code boolean} array is empty or {@code null}.
     * <p>
     * An array is considered empty if it is {@code null} or its length is 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isEmpty({true});              // false
     * ArrayUtils.isEmpty({});                  // true
     * ArrayUtils.isEmpty(null);                // true
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is empty or {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isEmpty(final boolean[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Checks if an array of Objects is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({"a"});             // true
     * ArrayUtils.isNotEmpty({});                // false
     * ArrayUtils.isNotEmpty(null);              // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @param <T>   the component type of the array.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if a {@code byte} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({1});           // true
     * ArrayUtils.isNotEmpty({});            // false
     * ArrayUtils.isNotEmpty(null);          // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final byte[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if a {@code short} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({1});          // true
     * ArrayUtils.isNotEmpty({});           // false
     * ArrayUtils.isNotEmpty(null);         // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final short[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if an {@code int} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({1});          // true
     * ArrayUtils.isNotEmpty({});           // false
     * ArrayUtils.isNotEmpty(null);         // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final int[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if a {@code long} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({1L});           // true
     * ArrayUtils.isNotEmpty({});             // false
     * ArrayUtils.isNotEmpty(null);           // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final long[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if a {@code float} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({1.0F});            // true
     * ArrayUtils.isNotEmpty({});                // false
     * ArrayUtils.isNotEmpty(null);              // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final float[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if a {@code double} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({1.0});            // true
     * ArrayUtils.isNotEmpty({});               // false
     * ArrayUtils.isNotEmpty(null);             // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final double[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if a {@code char} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({'a'});            // true
     * ArrayUtils.isNotEmpty({});               // false
     * ArrayUtils.isNotEmpty(null);             // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final char[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if a {@code boolean} array is not empty and not {@code null}.
     * <p>
     * An array is considered not empty if it is not {@code null} and its length is greater than 0.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotEmpty({true});              // true
     * ArrayUtils.isNotEmpty({});                  // false
     * ArrayUtils.isNotEmpty(null);                // false
     * }</pre>
     *
     * @param array the array to check, may be {@code null}.
     * @return {@code true} if the array is not empty and not {@code null}, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotEmpty(final boolean[] array) {
        return !isEmpty(array);
    }

    /**
     * Checks if an index is a valid access index for the given array.
     * <p>
     * An index is considered valid if it is greater than or equal to 0 and
     * less than the length of the array. This method safely handles {@code null}
     * arrays by returning {@code false}.
     * </p>
     * <pre>{@code
     * ArrayUtils.isValidIndex({'a', 'b'}, 1);  // true
     * ArrayUtils.isValidIndex({'a', 'b'}, 2);  // false
     * ArrayUtils.isValidIndex({'a', 'b'}, -1); // false
     * ArrayUtils.isValidIndex(null, 0);        // false
     * }</pre>
     *
     * @param array the array to check against, may be {@code null}.
     * @param index the index to validate.
     * @return {@code true} if the index is valid for the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isValidIndex(final Object array, final int index) {
        return index >= 0 && index < getLength(array);
    }

    /**
     * Checks if an index is not a valid access index for the given array.
     * <p>
     * An index is considered invalid if it is less than 0, or greater than or
     * equal to the length of the array. This method safely handles {@code null}
     * arrays by returning {@code true}.
     * </p>
     * <pre>{@code
     * ArrayUtils.isNotValidIndex({'a', 'b'}, 2);  // true
     * ArrayUtils.isNotValidIndex({'a', 'b'}, -1); // true
     * ArrayUtils.isNotValidIndex({'a', 'b'}, 1);  // false
     * ArrayUtils.isNotValidIndex(null, 0);        // true
     * }</pre>
     *
     * @param array the array to check against, may be {@code null}.
     * @param index the index to validate.
     * @return {@code true} if the index is not valid for the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean isNotValidIndex(final Object array, final int index) {
        return !isValidIndex(array, index);
    }


    /**
     * Gets the component type of the given array.
     * <p>
     * This is a private helper that delegates to {@code ClassUtils}.
     * </p>
     *
     * @param array the array.
     * @param <T>   the array type.
     * @return the component type.
     */
    private static <T> Class<T> getComponentType(final T[] array) {
        return ClassUtils.getComponentType(
            ClassUtils.getClass(array)
        );
    }

    /**
     * Creates a new array with the specified component type and length.
     * <p>
     * This method is a convenient wrapper around {@link java.lang.reflect.Array#newInstance(Class, int)}.
     * </p>
     * <pre>{@code
     * String[] array = ArrayUtils.newInstance(String.class, 3);
     * // array is now a new String[3]
     *
     * Integer[] empty = ArrayUtils.newInstance(Integer.class, 0);
     * // empty is now a new Integer[0]
     * }</pre>
     *
     * @param clazz  the class of the component type for the new array, must not be {@code null}.
     * @param length the length of the new array.
     * @param <T>    the component type.
     * @return A new array of the specified type and length.
     * @throws NullPointerException if {@code clazz} is {@code null}.
     * @since 1.1.2
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(final Class<T> clazz, final int length) {
        Objects.requireNonNull(clazz, "The class for the array must be provided");
        return (T[]) Array.newInstance(clazz, length);
    }

    /**
     * Clones a {@code byte} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({1, 2, 3});      // returns a new array {1, 2, 3}
     * ArrayUtils.clone({});            // returns a new empty array
     * ArrayUtils.clone(null);          // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static byte[] clone(final byte[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Clones a {@code short} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({1, 2, 3});      // returns a new array {1, 2, 3}
     * ArrayUtils.clone({});            // returns a new empty array
     * ArrayUtils.clone(null);          // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static short[] clone(final short[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Clones an {@code int} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({1, 2, 3});      // returns a new array {1, 2, 3}
     * ArrayUtils.clone({});            // returns a new empty array
     * ArrayUtils.clone(null);          // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static int[] clone(final int[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Clones a {@code long} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({1L, 2L, 3L});   // returns a new array {1L, 2L, 3L}
     * ArrayUtils.clone({});           // returns a new empty array
     * ArrayUtils.clone(null);         // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static long[] clone(final long[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Clones a {@code float} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({1.0f, 2.0f});   // returns a new array {1.0f, 2.0f}
     * ArrayUtils.clone({});           // returns a new empty array
     * ArrayUtils.clone(null);         // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static float[] clone(final float[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Clones a {@code double} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({1.0, 2.0});     // returns a new array {1.0, 2.0}
     * ArrayUtils.clone({});           // returns a new empty array
     * ArrayUtils.clone(null);         // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static double[] clone(final double[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Clones a {@code char} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({'a', 'b'});      // returns a new array {'a', 'b'}
     * ArrayUtils.clone({});            // returns a new empty array
     * ArrayUtils.clone(null);          // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static char[] clone(final char[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Clones a {@code boolean} array.
     * <p>
     * This method returns a new array containing the same elements as the input array.
     * If the input array is {@code null}, it returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({true, false});  // returns a new array {true, false}
     * ArrayUtils.clone({});           // returns a new empty array
     * ArrayUtils.clone(null);         // returns null
     * }</pre>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static boolean[] clone(final boolean[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Shallowly clones an array of Objects.
     * <p>
     * This method returns a new array containing the same element references as the input array.
     * The elements themselves are not cloned. If the input array is {@code null},
     * this method returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.clone({"a", "b"});      // returns a new array {"a", "b"}
     * ArrayUtils.clone({});              // returns a new empty array
     * ArrayUtils.clone(null);            // returns null
     * }</pre>
     *
     * @param array the array to shallow clone, may be {@code null}.
     * @param <T>   the component type of the array.
     * @return the cloned array, or {@code null} if the input array was {@code null}.
     * @since 1.1.2
     */
    public static <T> T[] clone(final T[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code byte}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, 1)       = {1}
     * ArrayUtils.add({1}, 2)        = {1, 2}
     * ArrayUtils.add({1, 2}, 3)     = {1, 2, 3}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static byte[] add(final byte[] array, final byte element) {
        final byte[] newArray = (byte[]) copyArrayGrow1(array, Byte.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code short}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, 1)       = {1}
     * ArrayUtils.add({1}, 2)        = {1, 2}
     * ArrayUtils.add({1, 2}, 3)     = {1, 2, 3}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static short[] add(final short[] array, final short element) {
        final short[] newArray = (short[]) copyArrayGrow1(array, Short.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code int}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, 1)       = {1}
     * ArrayUtils.add({1}, 2)        = {1, 2}
     * ArrayUtils.add({1, 2}, 3)     = {1, 2, 3}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static int[] add(final int[] array, final int element) {
        final int[] newArray = (int[]) copyArrayGrow1(array, Integer.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code long}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, 1L)       = {1L}
     * ArrayUtils.add({1L}, 2L)       = {1L, 2L}
     * ArrayUtils.add({1L, 2L}, 3L)   = {1L, 2L, 3L}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static long[] add(final long[] array, final long element) {
        final long[] newArray = (long[]) copyArrayGrow1(array, Long.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code float}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, 1.0f)          = {1.0f}
     * ArrayUtils.add({1.0f}, 2.0f)        = {1.0f, 2.0f}
     * ArrayUtils.add({1.0f, 2.0f}, 3.0f)  = {1.0f, 2.0f, 3.0f}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static float[] add(final float[] array, final float element) {
        final float[] newArray = (float[]) copyArrayGrow1(array, Float.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code double}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, 1.0)          = {1.0}
     * ArrayUtils.add({1.0}, 2.0)         = {1.0, 2.0}
     * ArrayUtils.add({1.0, 2.0}, 3.0)    = {1.0, 2.0, 3.0}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static double[] add(final double[] array, final double element) {
        final double[] newArray = (double[]) copyArrayGrow1(array, Double.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code char}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, 'a')          = {'a'}
     * ArrayUtils.add({'a'}, 'b')         = {'a', 'b'}
     * ArrayUtils.add({'a', 'b'}, 'c')    = {'a', 'b', 'c'}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static char[] add(final char[] array, final char element) {
        final char[] newArray = (char[]) copyArrayGrow1(array, Character.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is {@code boolean}.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, true)          = {true}
     * ArrayUtils.add({true}, false)       = {true, false}
     * ArrayUtils.add({true, false}, true) = {true, false, true}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 1.1.2
     */
    public static boolean[] add(final boolean[] array, final boolean element) {
        final boolean[] newArray = (boolean[]) copyArrayGrow1(array, Boolean.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Copies the given array and adds the given element to the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one-element array is returned
     * whose component type is the same as the element, unless the element is also
     * {@code null}, in which case an {@code IllegalArgumentException} is thrown.
     * </p>
     * <pre>{@code
     * ArrayUtils.add(null, "a")         = {"a"}
     * ArrayUtils.add({"a"}, "b")        = {"a", "b"}
     * ArrayUtils.add({"a", "b"}, "c")   = {"a", "b", "c"}
     * }</pre>
     *
     * @param array   the array to copy and add the element to, may be {@code null}.
     * @param element the object to add at the last index of the new array.
     * @param <T>     the component type of the array.
     * @return A new array containing the existing elements plus the new element.
     * @throws IllegalArgumentException if both {@code array} and {@code element} are {@code null}.
     * @since 1.1.2
     */
    public static <T> T[] add(final T[] array, final T element) {
        final Class<?> type;
        if (array != null) {
            type = array.getClass().getComponentType();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        @SuppressWarnings("unchecked")
        final T[] newArray = (T[]) copyArrayGrow1(array, type);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({1}, {2, 3})     = {1, 2, 3}
     * ArrayUtils.addAll({1, 2}, {3})     = {1, 2, 3}
     * ArrayUtils.addAll(null, {1, 2})    = {1, 2}
     * ArrayUtils.addAll({1, 2}, null)    = {1, 2}
     * ArrayUtils.addAll(null, null)      = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static byte[] addAll(final byte[] array1, final byte... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final byte[] resultArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({1}, {2, 3})     = {1, 2, 3}
     * ArrayUtils.addAll({1, 2}, {3})     = {1, 2, 3}
     * ArrayUtils.addAll(null, {1, 2})    = {1, 2}
     * ArrayUtils.addAll({1, 2}, null)    = {1, 2}
     * ArrayUtils.addAll(null, null)      = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static short[] addAll(final short[] array1, final short... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final short[] resultArray = new short[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({1}, {2, 3})     = {1, 2, 3}
     * ArrayUtils.addAll({1, 2}, {3})     = {1, 2, 3}
     * ArrayUtils.addAll(null, {1, 2})    = {1, 2}
     * ArrayUtils.addAll({1, 2}, null)    = {1, 2}
     * ArrayUtils.addAll(null, null)      = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static int[] addAll(final int[] array1, final int... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final int[] resultArray = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({1L}, {2L, 3L})   = {1L, 2L, 3L}
     * ArrayUtils.addAll({1L, 2L}, {3L})   = {1L, 2L, 3L}
     * ArrayUtils.addAll(null, {1L, 2L})   = {1L, 2L}
     * ArrayUtils.addAll({1L, 2L}, null)   = {1L, 2L}
     * ArrayUtils.addAll(null, null)       = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static long[] addAll(final long[] array1, final long... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final long[] resultArray = new long[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({1f}, {2f, 3f})    = {1f, 2f, 3f}
     * ArrayUtils.addAll({1f, 2f}, {3f})    = {1f, 2f, 3f}
     * ArrayUtils.addAll(null, {1f, 2f})    = {1f, 2f}
     * ArrayUtils.addAll({1f, 2f}, null)    = {1f, 2f}
     * ArrayUtils.addAll(null, null)        = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static float[] addAll(final float[] array1, final float... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final float[] resultArray = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({1.0}, {2.0, 3.0})   = {1.0, 2.0, 3.0}
     * ArrayUtils.addAll({1.0, 2.0}, {3.0})   = {1.0, 2.0, 3.0}
     * ArrayUtils.addAll(null, {1.0, 2.0})    = {1.0, 2.0}
     * ArrayUtils.addAll({1.0, 2.0}, null)    = {1.0, 2.0}
     * ArrayUtils.addAll(null, null)          = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static double[] addAll(final double[] array1, final double... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final double[] resultArray = new double[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({'a'}, {'b', 'c'})   = {'a', 'b', 'c'}
     * ArrayUtils.addAll({'a', 'b'}, {'c'})   = {'a', 'b', 'c'}
     * ArrayUtils.addAll(null, {'a', 'b'})    = {'a', 'b'}
     * ArrayUtils.addAll({'a', 'b'}, null)    = {'a', 'b'}
     * ArrayUtils.addAll(null, null)          = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static char[] addAll(final char[] array1, final char... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final char[] resultArray = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({true}, {false, true}) = {true, false, true}
     * ArrayUtils.addAll({true, false}, {true}) = {true, false, true}
     * ArrayUtils.addAll(null, {true})          = {true}
     * ArrayUtils.addAll({true}, null)          = {true}
     * ArrayUtils.addAll(null, null)            = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    public static boolean[] addAll(final boolean[] array1, final boolean... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final boolean[] resultArray = new boolean[array1.length + array2.length];
        System.arraycopy(array1, 0, resultArray, 0, array1.length);
        System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        return resultArray;
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all the elements from {@code array1} followed by
     * all the elements from {@code array2}. When an array is {@code null}, it is
     * treated as an empty array. The first array determines the new array's component type.
     * </p>
     * <pre>{@code
     * ArrayUtils.addAll({"a"}, {"b", "c"})   = {"a", "b", "c"}
     * ArrayUtils.addAll({"a", "b"}, {"c"})   = {"a", "b", "c"}
     * ArrayUtils.addAll(null, {"a", "b"})    = {"a", "b"}
     * ArrayUtils.addAll({"a", "b"}, null)    = {"a", "b"}
     * ArrayUtils.addAll(null, null)          = null
     * }</pre>
     *
     * @param array1 the first array, may be {@code null}. Its component type determines the type of the new array.
     * @param array2 the second array, may be {@code null}.
     * @param <T>    the component type of the arrays.
     * @return The new array, or {@code null} if both arrays are {@code null}.
     * @since 1.1.2
     */
    @SafeVarargs
    public static <T> T[] addAll(final T[] array1, final T... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final Class<T> type1 = getComponentType(array1);
        final int totalLength = array1.length + array2.length;
        final T[] resultArray = arraycopy(
            array1,
            0,
            0,
            array1.length,
            () -> newInstance(type1, totalLength)
        );

        try {
            System.arraycopy(array2, 0, resultArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            final Class<?> type2 = getComponentType(array2);
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot add " + type2.getName() + " to an array of " + type1.getName(), ase);
            }
            throw ase;
        }
        return resultArray;
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({2, 3}, 1)    = {1, 2, 3}
     * ArrayUtils.addToFirst({}, 1)        = {1}
     * ArrayUtils.addToFirst(null, 1)      = {1}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static byte[] addToFirst(final byte[] array, final byte element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({2, 3}, 1)    = {1, 2, 3}
     * ArrayUtils.addToFirst({}, 1)        = {1}
     * ArrayUtils.addToFirst(null, 1)      = {1}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static short[] addToFirst(final short[] array, final short element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({2, 3}, 1)    = {1, 2, 3}
     * ArrayUtils.addToFirst({}, 1)        = {1}
     * ArrayUtils.addToFirst(null, 1)      = {1}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static int[] addToFirst(final int[] array, final int element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({2L, 3L}, 1L)    = {1L, 2L, 3L}
     * ArrayUtils.addToFirst({}, 1L)          = {1L}
     * ArrayUtils.addToFirst(null, 1L)        = {1L}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static long[] addToFirst(final long[] array, final long element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({2f, 3f}, 1f)    = {1f, 2f, 3f}
     * ArrayUtils.addToFirst({}, 1f)          = {1f}
     * ArrayUtils.addToFirst(null, 1f)        = {1f}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static float[] addToFirst(final float[] array, final float element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({2.0, 3.0}, 1.0)    = {1.0, 2.0, 3.0}
     * ArrayUtils.addToFirst({}, 1.0)            = {1.0}
     * ArrayUtils.addToFirst(null, 1.0)          = {1.0}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static double[] addToFirst(final double[] array, final double element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({'b', 'c'}, 'a')    = {'a', 'b', 'c'}
     * ArrayUtils.addToFirst({}, 'a')            = {'a'}
     * ArrayUtils.addToFirst(null, 'a')          = {'a'}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static char[] addToFirst(final char[] array, final char element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({false, true}, true)    = {true, false, true}
     * ArrayUtils.addToFirst({}, true)               = {true}
     * ArrayUtils.addToFirst(null, true)             = {true}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static boolean[] addToFirst(final boolean[] array, final boolean element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }

    /**
     * Inserts the specified element at the beginning of a new array.
     * <p>
     * This is a convenience method for {@code insert(array, 0, element)}.
     * If the input array is {@code null}, a new one-element array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.addToFirst({"b", "c"}, "a")    = {"a", "b", "c"}
     * ArrayUtils.addToFirst({}, "a")            = {"a"}
     * ArrayUtils.addToFirst(null, "a")          = {"a"}
     * }</pre>
     *
     * @param array   the array to add the element to, may be {@code null}.
     * @param element the element to add to the start.
     * @param <T>     the component type of the array.
     * @return a new array with the element at the beginning.
     * @since 1.1.2
     */
    public static <T> T[] addToFirst(final T[] array, final T element) {
        return array == null ? add(array, element) : insert(array, 0, element);
    }


    /**
     * Inserts elements into a {@code byte} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <p>
     * If the input array is {@code null}, it returns {@code null}.
     * If the elements to insert are null or empty, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({1, 4}, 1, 2, 3)    = {1, 2, 3, 4}
     * ArrayUtils.insert({1, 4}, 0, 0)        = {0, 1, 4}
     * ArrayUtils.insert({1, 4}, 2, 5)        = {1, 4, 5}
     * ArrayUtils.insert(null, 0, 1)          = null
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static byte[] insert(byte[] target, int index, byte... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final byte[] result = new byte[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Inserts elements into a {@code short} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({1, 4}, 1, 2, 3)    = {1, 2, 3, 4}
     * ArrayUtils.insert({1, 4}, 0, 0)        = {0, 1, 4}
     * ArrayUtils.insert({1, 4}, 2, 5)        = {1, 4, 5}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static short[] insert(short[] target, int index, short... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final short[] result = new short[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Inserts elements into an {@code int} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({1, 4}, 1, 2, 3)    = {1, 2, 3, 4}
     * ArrayUtils.insert({1, 4}, 0, 0)        = {0, 1, 4}
     * ArrayUtils.insert({1, 4}, 2, 5)        = {1, 4, 5}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static int[] insert(int[] target, int index, int... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final int[] result = new int[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Inserts elements into a {@code long} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({1L, 4L}, 1, 2L, 3L)    = {1L, 2L, 3L, 4L}
     * ArrayUtils.insert({1L, 4L}, 0, 0L)        = {0L, 1L, 4L}
     * ArrayUtils.insert({1L, 4L}, 2, 5L)        = {1L, 4L, 5L}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static long[] insert(long[] target, int index, long... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final long[] result = new long[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Inserts elements into a {@code float} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({1f, 4f}, 1, 2f, 3f)    = {1f, 2f, 3f, 4f}
     * ArrayUtils.insert({1f, 4f}, 0, 0f)        = {0f, 1f, 4f}
     * ArrayUtils.insert({1f, 4f}, 2, 5f)        = {1f, 4f, 5f}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static float[] insert(float[] target, int index, float... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final float[] result = new float[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }

        return result;
    }

    /**
     * Inserts elements into a {@code double} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({1.0, 4.0}, 1, 2.0, 3.0)    = {1.0, 2.0, 3.0, 4.0}
     * ArrayUtils.insert({1.0, 4.0}, 0, 0.0)         = {0.0, 1.0, 4.0}
     * ArrayUtils.insert({1.0, 4.0}, 2, 5.0)         = {1.0, 4.0, 5.0}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static double[] insert(double[] target, int index, double... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final double[] result = new double[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Inserts elements into a {@code char} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({'a', 'd'}, 1, 'b', 'c')    = {'a', 'b', 'c', 'd'}
     * ArrayUtils.insert({'a', 'd'}, 0, 'z')         = {'z', 'a', 'd'}
     * ArrayUtils.insert({'a', 'd'}, 2, 'e')         = {'a', 'd', 'e'}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static char[] insert(char[] target, int index, char... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final char[] result = new char[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Inserts elements into a {@code boolean} array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({true, false}, 1, false, true)    = {true, false, true, false}
     * ArrayUtils.insert({true}, 0, false)                  = {false, true}
     * ArrayUtils.insert({true}, 1, false)                  = {true, false}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    public static boolean[] insert(boolean[] target, int index, boolean... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        final boolean[] result = new boolean[target.length + elements.length];
        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }

        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Inserts elements into a generic array at the specified index.
     * <p>
     * This method returns a new array with the new elements inserted. The elements
     * from the original array at and after the {@code index} are shifted to the right.
     * </p>
     * <pre>{@code
     * ArrayUtils.insert({"a", "d"}, 1, "b", "c")    = {"a", "b", "c", "d"}
     * ArrayUtils.insert({"a", "d"}, 0, "z")         = {"z", "a", "d"}
     * ArrayUtils.insert({"a", "d"}, 2, "e")         = {"a", "d", "e"}
     * }</pre>
     *
     * @param target   the array to insert into, may be {@code null}.
     * @param index    the position in the array to insert at.
     * @param elements the elements to insert.
     * @param <T>      the component type of the array.
     * @return A new array with the elements inserted.
     * @throws IndexOutOfBoundsException if {@code index} is out of bounds (less than 0 or greater than target.length).
     * @since 1.1.2
     */
    @SafeVarargs
    public static <T> T[] insert(T[] target, int index, T... elements) {
        if (target == null) return null;
        if (isEmpty(elements)) return target.clone();
        if (index < 0 || index > target.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + target.length);

        Class<T> componentType = getComponentType(target);
        int totalLength = target.length + elements.length;
        final T[] result = newInstance(componentType, totalLength);

        System.arraycopy(elements, 0, result, index, elements.length);
        if (index > 0) {
            System.arraycopy(target, 0, result, 0, index);
        }
        if (index < target.length) {
            System.arraycopy(target, index, result, index + elements.length, target.length - index);
        }
        return result;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 2, 0)   = 1
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 2, 2)   = 3
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 4, 0)   = -1
     * ArrayUtils.indexOf(null, 2, 0)              = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final byte[] array, final byte target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 2, 0)   = 1
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 2, 2)   = 3
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 4, 0)   = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final short[] array, final short target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 2, 0)   = 1
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 2, 2)   = 3
     * ArrayUtils.indexOf({1, 2, 3, 2, 1}, 4, 0)   = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final int[] array, final int target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1L, 2L, 3L, 2L, 1L}, 2L, 0)   = 1
     * ArrayUtils.indexOf({1L, 2L, 3L, 2L, 1L}, 2L, 2)   = 3
     * ArrayUtils.indexOf({1L, 2L, 3L, 2L, 1L}, 4L, 0)   = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final long[] array, final long target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1f, 2f, 3f, 2f, 1f}, 2f, 0)   = 1
     * ArrayUtils.indexOf({1f, 2f, 3f, 2f, 1f}, 2f, 2)   = 3
     * ArrayUtils.indexOf({1f, 2f, 3f, 2f, 1f}, 4f, 0)   = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final float[] array, final float target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1.0, 2.0, 3.0, 2.0, 1.0}, 2.0, 0)   = 1
     * ArrayUtils.indexOf({1.0, 2.0, 3.0, 2.0, 1.0}, 2.0, 2)   = 3
     * ArrayUtils.indexOf({1.0, 2.0, 3.0, 2.0, 1.0}, 4.0, 0)   = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final double[] array, final double target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({'a', 'b', 'c', 'b', 'a'}, 'b', 0)   = 1
     * ArrayUtils.indexOf({'a', 'b', 'c', 'b', 'a'}, 'b', 2)   = 3
     * ArrayUtils.indexOf({'a', 'b', 'c', 'b', 'a'}, 'd', 0)   = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final char[] array, final char target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({true, false, true, false}, false, 0)   = 1
     * ArrayUtils.indexOf({true, false, true, false}, false, 2)   = 3
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndex the index to start searching from.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final boolean[] array, final boolean target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array starting from the given index.
     * <p>
     * A negative {@code startIndex} is treated as 0. An empty or {@code null} array
     * will return {@link #INDEX_NOT_FOUND}. This method handles a {@code null} target
     * by searching for a {@code null} element in the array.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({"a", "b", null, "b"}, "b", 0)    = 1
     * ArrayUtils.indexOf({"a", "b", null, "b"}, "b", 2)    = 3
     * ArrayUtils.indexOf({"a", "b", null, "b"}, null, 0)   = 2
     * ArrayUtils.indexOf({"a", "b", null, "b"}, "d", 0)    = -1
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find, may be {@code null}.
     * @param startIndex the index to start searching from.
     * @param <T>        the component type of the array.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static <T> int indexOf(final T[] array, final T target, final int startIndex) {
        if (isEmpty(array)) return INDEX_NOT_FOUND;
        int start = Math.max(startIndex, 0);
        if (target == null) {
            for (int i = start; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < array.length; i++) {
                if (target.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1, 2, 3}, 2) = 1
     * ArrayUtils.indexOf({1, 2, 3}, 4) = -1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final byte[] array, final byte target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1, 2, 3}, 2) = 1
     * ArrayUtils.indexOf({1, 2, 3}, 4) = -1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final short[] array, final short target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1, 2, 3}, 2) = 1
     * ArrayUtils.indexOf({1, 2, 3}, 4) = -1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final int[] array, final int target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1L, 2L, 3L}, 2L) = 1
     * ArrayUtils.indexOf({1L, 2L, 3L}, 4L) = -1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final long[] array, final long target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1f, 2f, 3f}, 2f) = 1
     * ArrayUtils.indexOf({1f, 2f, 3f}, 4f) = -1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final float[] array, final float target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({1.0, 2.0, 3.0}, 2.0) = 1
     * ArrayUtils.indexOf({1.0, 2.0, 3.0}, 4.0) = -1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final double[] array, final double target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({'a', 'b', 'c'}, 'b') = 1
     * ArrayUtils.indexOf({'a', 'b', 'c'}, 'd') = -1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final char[] array, final char target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({true, false, true}, false) = 1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static int indexOf(final boolean[] array, final boolean target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds the first index of the given element in the array, searching from the beginning.
     * <p>
     * This is a convenience method for {@code indexOf(array, target, 0)}.
     * This method handles a {@code null} target by searching for a {@code null} element.
     * </p>
     * <pre>{@code
     * ArrayUtils.indexOf({"a", "b", "c"}, "b") = 1
     * ArrayUtils.indexOf({"a", "b", "c"}, "d") = -1
     * ArrayUtils.indexOf({"a", null, "c"}, null) = 1
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @param <T>    the component type of the array.
     * @return the first index of the element, or {@link #INDEX_NOT_FOUND} if not found.
     * @since 1.1.2
     */
    public static <T> int indexOf(final T[] array, final T target) {
        return indexOf(array, target, 0);
    }

    /**
     * Finds all indices of the given element within the specified range of a {@code char} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({'a', 'b', 'a', 'c', 'a'}, 'a', 0, 5);
     * // bs2 will contain {2}
     * BitSet bs2 = ArrayUtils.indexesOf({'a', 'b', 'a', 'c', 'a'}, 'a', 1, 4);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final char[] array, final char target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of a {@code byte} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1, 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final byte[] array, final byte target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of a {@code short} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1, 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final short[] array, final short target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of an {@code int} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1, 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final int[] array, final int target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of a {@code long} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1L, 2L, 1L, 3L, 1L}, 1L, 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final long[] array, final long target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of a {@code float} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1f, 2f, 1f, 3f, 1f}, 1f, 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final float[] array, final float target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of a {@code double} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1.0, 2.0, 1.0, 3.0, 1.0}, 1.0, 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final double[] array, final double target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndex(%d) cannot be greater than endIndex(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of a {@code boolean} array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({true, false, true, false, true}, true, 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final boolean[] array, final boolean target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element within the specified range of a generic array.
     * <p>
     * The search is conducted in the range from {@code startIndexInclusive} (inclusive) to
     * {@code endIndexExclusive} (exclusive). This method handles a {@code null} target by searching
     * for a {@code null} element. A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs will contain {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({"a", "b", "a", "c", "a"}, "a", 0, 5);
     * }</pre>
     *
     * @param array      the array to search in, may be {@code null}.
     * @param target     the element to find, may be {@code null}.
     * @param startIndexInclusive the starting index (inclusive) of the search range.
     * @param endIndexExclusive   the ending index (exclusive) of the search range.
     * @param <T>        the component type of the array.
     * @return a {@link BitSet} where each set bit corresponds to an index where the target element was found.
     * @throws IllegalArgumentException if {@code startIndexInclusive > endIndexExclusive}.
     * @since 1.1.2
     */
    public static <T> BitSet indexesOf(final T[] array, final T target, final int startIndexInclusive, final int endIndexExclusive) {
        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException(
                String.format("startIndexInclusive(%d) cannot be greater than endIndexExclusive(%d)", startIndexInclusive, endIndexExclusive)
            );
        }

        BitSet indexes = new BitSet();
        if (isEmpty(array)) return indexes;

        int start = Math.max(startIndexInclusive, 0);
        int end = Math.min(endIndexExclusive, array.length);

        int found;
        while ((found = indexOf(array, target, start)) != INDEX_NOT_FOUND && found < end) {
            indexes.set(found);
            start = found + 1;
        }
        return indexes;
    }

    /**
     * Finds all indices of the given element in a {@code byte} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1, 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final byte[] array, final byte target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in an {@code int} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1, 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final int[] array, final int target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in a {@code short} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1, 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final short[] array, final short target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in a {@code long} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1L, 2L, 1L, 3L, 1L}, 1L, 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final long[] array, final long target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in a {@code float} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1f, 2f, 1f, 3f, 1f}, 1f, 1);
     * }</pre>
     *
     * @param arrays     the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final float[] arrays, final float target, final int startIndexInclusive) {
        return indexesOf(arrays, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in a {@code double} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1.0, 2.0, 1.0, 3.0, 1.0}, 1.0, 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final double[] array, final double target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in a {@code boolean} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({true, false, true, false, true}, true, 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final boolean[] array, final boolean target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in a {@code char} array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({'a', 'b', 'a', 'c', 'a'}, 'a', 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final char[] array, final char target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in a generic array from a given starting position.
     * <p>
     * The search is conducted from {@code startIndexInclusive} (inclusive) to the end of the array.
     * </p>
     * <pre>{@code
     * // bs contains {2, 4}
     * BitSet bs = ArrayUtils.indexesOf({"a", "b", "a", "c", "a"}, "a", 1);
     * }</pre>
     *
     * @param array      the array to search in.
     * @param target     the element to find.
     * @param startIndexInclusive the index to start searching from.
     * @param <T>        the component type of the array.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static <T> BitSet indexesOf(final T[] array, final T target, final int startIndexInclusive) {
        return indexesOf(array, target, startIndexInclusive, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code byte} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1);
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final byte[] array, final byte target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code short} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1);
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final short[] array, final short target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code int} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1, 2, 1, 3, 1}, 1);
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final int[] array, final int target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code long} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1L, 2L, 1L, 3L, 1L}, 1L);
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final long[] array, final long target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code float} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1f, 2f, 1f, 3f, 1f}, 1f);
     * }</pre>
     *
     * @param arrays the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final float[] arrays, final float target) {
        return indexesOf(arrays, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code double} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({1.0, 2.0, 1.0, 3.0, 1.0}, 1.0);
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final double[] array, final double target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code boolean} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({true, false, true, false, true}, true);
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final boolean[] array, final boolean target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire {@code char} array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({'a', 'b', 'a', 'c', 'a'}, 'a');
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static BitSet indexesOf(final char[] array, final char target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Finds all indices of the given element in the entire generic array.
     * <p>
     * A {@code null} or empty array will return an empty {@link BitSet}.
     * This method handles a {@code null} target by searching for {@code null} elements.
     * </p>
     * <pre>{@code
     * // bs contains {0, 2, 4}
     * BitSet bs = ArrayUtils.indexesOf({"a", "b", "a", "c", "a"}, "a");
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @param <T>    the component type of the array.
     * @return a {@link BitSet} of all indices where the element was found.
     * @since 1.1.2
     */
    public static <T> BitSet indexesOf(final T[] array, final T target) {
        return indexesOf(array, target, 0, Integer.MAX_VALUE);
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({1, 2, 3}, 2)   = true
     * ArrayUtils.contains({1, 2, 3}, 4)   = false
     * ArrayUtils.contains(null, 1)        = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final byte[] array, final byte target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({1, 2, 3}, 2)   = true
     * ArrayUtils.contains({1, 2, 3}, 4)   = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final short[] array, final short target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({1, 2, 3}, 2)   = true
     * ArrayUtils.contains({1, 2, 3}, 4)   = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final int[] array, final int target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({1L, 2L, 3L}, 2L)   = true
     * ArrayUtils.contains({1L, 2L, 3L}, 4L)   = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final long[] array, final long target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({1f, 2f, 3f}, 2f)   = true
     * ArrayUtils.contains({1f, 2f, 3f}, 4f)   = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final float[] array, final float target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({1.0, 2.0, 3.0}, 2.0)   = true
     * ArrayUtils.contains({1.0, 2.0, 3.0}, 4.0)   = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final double[] array, final double target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({'a', 'b', 'c'}, 'b')   = true
     * ArrayUtils.contains({'a', 'b', 'c'}, 'd')   = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final char[] array, final char target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({true, false, true}, false)   = true
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static boolean contains(final boolean[] array, final boolean target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Checks if the array contains the given element.
     * <p>
     * Returns {@code false} if the array is {@code null} or empty.
     * This method handles a {@code null} target by searching for a {@code null} element.
     * </p>
     * <pre>{@code
     * ArrayUtils.contains({"a", "b", "c"}, "b")      = true
     * ArrayUtils.contains({"a", "b", null}, null)    = true
     * ArrayUtils.contains({"a", "b", "c"}, "d")      = false
     * }</pre>
     *
     * @param array  the array to search in.
     * @param target the element to find.
     * @param <T>    the component type of the array.
     * @return {@code true} if the element is in the array, {@code false} otherwise.
     * @since 1.1.2
     */
    public static <T> boolean contains(final T[] array, final T target) {
        return indexOf(array, target) != INDEX_NOT_FOUND;
    }

    /**
     * Removes an element at the specified index from a generic {@link Object} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * All subsequent elements are shifted to the left.
     * </p>
     * <p>
     * If the input array is {@code null}, this method returns {@code null}.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex(new String[]{"a", "b", "c"}, 1) = {"a", "c"}
     * }</pre>
     *
     * @param array the array to remove the element from, may be {@code null}.
     * @param index the index of the element to remove.
     * @return A new array with the element removed, or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if the index is out of bounds for the array.
     * @since 1.1.2
     */
    public static Object removeByIndex(final Object array, final int index) {
        if (array == null) return null;
        int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        Object newArray = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, newArray, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, newArray, index, length - index - 1);
        }
        return newArray;
    }

    /**
     * Removes an element at the specified index from a {@code byte} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({1, 2, 3}, 0)    = {2, 3}
     * ArrayUtils.removeByIndex({1, 2, 3}, 1)    = {1, 3}
     * ArrayUtils.removeByIndex({1, 2, 3}, 2)    = {1, 2}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static byte[] removeByIndex(final byte[] array, final int index) {
        return (byte[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from a {@code short} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({1, 2, 3}, 1)    = {1, 3}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static short[] removeByIndex(final short[] array, final int index) {
        return (short[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from an {@code int} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({1, 2, 3}, 1)    = {1, 3}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static int[] removeByIndex(final int[] array, final int index) {
        return (int[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from a {@code long} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({1L, 2L, 3L}, 1)    = {1L, 3L}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static long[] removeByIndex(final long[] array, final int index) {
        return (long[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from a {@code float} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({1f, 2f, 3f}, 1)    = {1f, 3f}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static float[] removeByIndex(final float[] array, final int index) {
        return (float[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from a {@code double} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({1.0, 2.0, 3.0}, 1)    = {1.0, 3.0}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static double[] removeByIndex(final double[] array, final int index) {
        return (double[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from a {@code char} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({'a', 'b', 'c'}, 1)    = {'a', 'c'}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static char[] removeByIndex(final char[] array, final int index) {
        return (char[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from a {@code boolean} array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({true, false, true}, 1)    = {true, true}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    public static boolean[] removeByIndex(final boolean[] array, final int index) {
        return (boolean[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes an element at the specified index from a generic array.
     * <p>
     * This method creates a new array with the element at the specified index removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeByIndex({"a", "b", "c"}, 1)    = {"a", "c"}
     * }</pre>
     *
     * @param array the array to remove the element from.
     * @param index the index of the element to remove.
     * @param <T>   the component type of the array.
     * @return A new array with the element removed.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @since 1.1.2
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] removeByIndex(final T[] array, final int index) {
        return (T[]) removeByIndex((Object) array, index);
    }

    /**
     * Removes the first occurrence of the specified element from a {@code byte} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({1, 2, 3, 2}, 2)    = {1, 3, 2}
     * ArrayUtils.removeElement({1, 2, 3}, 4)    = {1, 2, 3}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed, or a clone of the original array if the element is not found.
     * @since 1.1.2
     */
    public static byte[] removeElement(final byte[] array, final byte target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from a {@code short} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({1, 2, 3, 2}, 2)    = {1, 3, 2}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static short[] removeElement(final short[] array, final short target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from an {@code int} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({1, 2, 3, 2}, 2)    = {1, 3, 2}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static int[] removeElement(final int[] array, final int target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from a {@code long} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({1L, 2L, 3L, 2L}, 2L)    = {1L, 3L, 2L}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static long[] removeElement(final long[] array, final long target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from a {@code float} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({1f, 2f, 3f, 2f}, 2f)    = {1f, 3f, 2f}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static float[] removeElement(final float[] array, final float target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from a {@code double} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({1.0, 2.0, 3.0, 2.0}, 2.0)    = {1.0, 3.0, 2.0}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static double[] removeElement(final double[] array, final double target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from a {@code char} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({'a', 'b', 'c', 'b'}, 'b')    = {'a', 'c', 'b'}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static char[] removeElement(final char[] array, final char target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from a {@code boolean} array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({true, false, true}, true)    = {false, true}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static boolean[] removeElement(final boolean[] array, final boolean target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes the first occurrence of the specified element from a generic array.
     * <p>
     * All subsequent elements are shifted to the left. If the array doesn't contain
     * the element, a clone of the original array is returned. This method handles a
     * {@code null} target by searching for and removing the first {@code null} element.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeElement({"a", "b", "c", "b"}, "b")    = {"a", "c", "b"}
     * ArrayUtils.removeElement({"a", null, "b"}, null)    = {"a", "b"}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @param <T>    the component type of the array.
     * @return A new array with the first occurrence of the element removed.
     * @since 1.1.2
     */
    public static <T> T[] removeElement(final T[] array, final T target) {
        int found = indexOf(array, target);
        return found == INDEX_NOT_FOUND ? clone(array) : removeByIndex(array, found);
    }

    /**
     * Removes all elements from an array at the given indices.
     * <p>
     * This is a package-private helper method that creates a new array,
     * skipping the elements at the indices specified in the {@link BitSet}.
     * </p>
     *
     * @param array   the array to remove from, may be {@code null}.
     * @param indices a BitSet representing the indices to remove.
     * @return a new array with elements at the specified indices removed, or {@code null} if the input array is {@code null}.
     */
    static Object removeAll(final Object array, final BitSet indices) {
        if (array == null) {
            return null;
        }
        final int srcLength = getLength(array);

        final int removals = indices.cardinality();
        final Object result = Array.newInstance(array.getClass().getComponentType(), srcLength - removals);
        int srcIndex = 0;
        int destIndex = 0;
        int count;
        int set;
        while ((set = indices.nextSetBit(srcIndex)) != -1) {
            count = set - srcIndex;
            if (count > 0) {
                System.arraycopy(array, srcIndex, result, destIndex, count);
                destIndex += count;
            }
            srcIndex = indices.nextClearBit(set);
        }
        count = srcLength - srcIndex;
        if (count > 0) {
            System.arraycopy(array, srcIndex, result, destIndex, count);
        }
        return result;
    }

    /**
     * Removes elements from a {@code byte} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes one '2' and one '4'
     * ArrayUtils.removeElements({1, 2, 3, 2, 4}, 2, 4)    = {1, 3, 2}
     * // Removes two '2's
     * ArrayUtils.removeElements({1, 2, 3, 2, 4}, 2, 2)    = {1, 3, 4}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static byte[] removeElements(final byte[] array, final byte... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Byte, Counter> times = new HashMap<>(targets.length);

        for (byte target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final byte key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (byte[]) removeAll(array, removes);
    }

    /**
     * Removes elements from a {@code short} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes two '2's
     * ArrayUtils.removeElements({1, 2, 3, 2, 4}, 2, 2)    = {1, 3, 4}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static short[] removeElements(final short[] array, final short... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Short, Counter> times = new HashMap<>(targets.length);

        for (short target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final short key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (short[]) removeAll(array, removes);
    }

    /**
     * Removes elements from an {@code int} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes two '2's
     * ArrayUtils.removeElements({1, 2, 3, 2, 4}, 2, 2)    = {1, 3, 4}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static int[] removeElements(final int[] array, final int... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Integer, Counter> times = new HashMap<>(targets.length);

        for (int target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final int key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (int[]) removeAll(array, removes);
    }

    /**
     * Removes elements from a {@code long} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes two '2L's
     * ArrayUtils.removeElements({1L, 2L, 3L, 2L, 4L}, 2L, 2L)    = {1L, 3L, 4L}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static long[] removeElements(final long[] array, final long... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Long, Counter> times = new HashMap<>(targets.length);

        for (long target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final long key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (long[]) removeAll(array, removes);
    }

    /**
     * Removes elements from a {@code float} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes two '2f's
     * ArrayUtils.removeElements({1f, 2f, 3f, 2f, 4f}, 2f, 2f)    = {1f, 3f, 4f}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static float[] removeElements(final float[] array, final float... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Float, Counter> times = new HashMap<>(targets.length);

        for (float target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final float key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (float[]) removeAll(array, removes);
    }

    /**
     * Removes elements from a {@code double} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes two '2.0's
     * ArrayUtils.removeElements({1.0, 2.0, 3.0, 2.0, 4.0}, 2.0, 2.0)    = {1.0, 3.0, 4.0}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static double[] removeElements(final double[] array, final double... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Double, Counter> times = new HashMap<>(targets.length);

        for (double target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final double key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (double[]) removeAll(array, removes);
    }

    /**
     * Removes elements from a {@code char} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes two 'b's
     * ArrayUtils.removeElements({'a', 'b', 'c', 'b', 'd'}, 'b', 'b')    = {'a', 'c', 'd'}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static char[] removeElements(final char[] array, final char... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Character, Counter> times = new HashMap<>(targets.length);

        for (char target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final char key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (char[]) removeAll(array, removes);
    }

    /**
     * Removes elements from a {@code boolean} array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * </p>
     * <pre>{@code
     * // Removes two 'true's
     * ArrayUtils.removeElements({true, false, true, false, true}, true, true) = {false, false, true}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    public static boolean[] removeElements(final boolean[] array, final boolean... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<Boolean, Counter> times = new HashMap<>(2);

        for (boolean target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final boolean key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }

        return (boolean[]) removeAll(array, removes);
    }

    /**
     * Removes elements from a generic array.
     * <p>
     * This method removes a number of occurrences of an element from the source {@code array}
     * that corresponds to the number of occurrences of that element in the {@code targets} array.
     * It properly handles {@code null} elements.
     * </p>
     * <pre>{@code
     * // Removes two "b"s
     * ArrayUtils.removeElements({"a", "b", "c", "b", "d"}, "b", "b") = {"a", "c", "d"}
     * }</pre>
     *
     * @param array   the array to remove elements from.
     * @param targets the elements to remove.
     * @param <T>     the component type of the array.
     * @return A new array with the specified elements removed.
     * @since 1.1.2
     */
    @SafeVarargs
    public static <T> T[] removeElements(final T[] array, final T... targets) {
        if (isEmpty(array) || isEmpty(targets)) return clone(array);
        final Map<T, Counter> times = new HashMap<>(2);

        for (T target : targets) {
            final Counter counter = times.get(target);

            if (counter == null) {
                times.put(target, AtomicCounter.createFor(1));
            } else {
                counter.increment();
            }
        }

        final BitSet removes = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final T key = array[i];
            final Counter counter = times.get(key);

            if (counter != null) {
                if (counter.decrementAndGet() == 0) {
                    times.remove(key);
                }
                removes.set(i);
            }
        }
        @SuppressWarnings("unchecked")
        T[] result = (T[]) removeAll(array, removes);
        return result;
    }

    /**
     * Removes all occurrences of the specified element from a {@code byte} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * If the element is not found, a clone of the original array is returned.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({1, 2, 1, 3, 1}, 1) = {2, 3}
     * ArrayUtils.removeAllAppearance({1, 2, 3}, 4)       = {1, 2, 3}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static byte[] removeAllAppearance(final byte[] array, final byte target) {
        return (byte[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from a {@code short} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({1, 2, 1, 3, 1}, 1) = {2, 3}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static short[] removeAllAppearance(final short[] array, final short target) {
        return (short[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from an {@code int} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({1, 2, 1, 3, 1}, 1) = {2, 3}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static int[] removeAllAppearance(final int[] array, final int target) {
        return (int[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from a {@code long} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({1L, 2L, 1L, 3L, 1L}, 1L) = {2L, 3L}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static long[] removeAllAppearance(final long[] array, final long target) {
        return (long[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from a {@code float} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({1f, 2f, 1f, 3f, 1f}, 1f) = {2f, 3f}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static float[] removeAllAppearance(final float[] array, final float target) {
        return (float[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from a {@code double} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({1.0, 2.0, 1.0, 3.0, 1.0}, 1.0) = {2.0, 3.0}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static double[] removeAllAppearance(final double[] array, final double target) {
        return (double[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from a {@code char} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({'a', 'b', 'a', 'c', 'a'}, 'a') = {'b', 'c'}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static char[] removeAllAppearance(final char[] array, final char target) {
        return (char[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from a {@code boolean} array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({true, false, true, true}, true) = {false}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static boolean[] removeAllAppearance(final boolean[] array, final boolean target) {
        return (boolean[]) removeAll((Object) array, indexesOf(array, target));
    }

    /**
     * Removes all occurrences of the specified element from a generic array.
     * <p>
     * A new array is returned with all instances of the target element removed.
     * This method handles a {@code null} target by removing all {@code null} elements.
     * </p>
     * <pre>{@code
     * ArrayUtils.removeAllAppearance({"a", "b", "a", null, "a"}, "a") = {"b", null}
     * ArrayUtils.removeAllAppearance({"a", "b", null, "a"}, null)     = {"a", "b", "a"}
     * }</pre>
     *
     * @param array  the array to remove the element from.
     * @param target the element to remove.
     * @param <T>    the component type of the array.
     * @return A new array with all occurrences of the element removed.
     * @since 1.1.2
     */
    public static <T> T[] removeAllAppearance(final T[] array, final T target) {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) removeAll((Object) array, indexesOf(array, target));
        return result;
    }

    /**
     * A fluent version of {@link System#arraycopy(Object, int, Object, int, int)} that returns the destination array.
     *
     * @param <T>       the type.
     * @param source    the source array.
     * @param sourcePos starting position in the source array.
     * @param destPos   starting position in the destination data.
     * @param length    the number of array elements to be copied.
     * @param allocator allocates the array to populate and return.
     * @return dest
     * @throws IndexOutOfBoundsException if copying causes access of data outside array bounds.
     * @throws ArrayStoreException       if an element in the {@code src} array could not be stored into the {@code dest} array because of a type
     *                                   mismatch.
     * @throws NullPointerException      if either {@code src} or {@code dest} is {@code null}.
     * @since 1.1.2
     */
    public static <T> T arraycopy(final T source, final int sourcePos, final int destPos, final int length, final Function<Integer, T> allocator) {
        return arraycopy(source, sourcePos, allocator.apply(length), destPos, length);
    }

    /**
     * A fluent version of {@link System#arraycopy(Object, int, Object, int, int)} that returns the destination array.
     *
     * @param <T>       the type.
     * @param source    the source array.
     * @param sourcePos starting position in the source array.
     * @param destPos   starting position in the destination data.
     * @param length    the number of array elements to be copied.
     * @param allocator allocates the array to populate and return.
     * @return dest
     * @throws IndexOutOfBoundsException if copying causes access of data outside array bounds.
     * @throws ArrayStoreException       if an element in the {@code src} array could not be stored into the {@code dest} array because of a type
     *                                   mismatch.
     * @throws NullPointerException      if either {@code src} or {@code dest} is {@code null}.
     * @since 1.1.2
     */
    public static <T> T arraycopy(final T source, final int sourcePos, final int destPos, final int length, final Supplier<T> allocator) {
        return arraycopy(source, sourcePos, allocator.get(), destPos, length);
    }

    /**
     * A fluent version of {@link System#arraycopy(Object, int, Object, int, int)} that returns the destination array.
     *
     * @param <T>       the type
     * @param source    the source array.
     * @param sourcePos starting position in the source array.
     * @param dest      the destination array.
     * @param destPos   starting position in the destination data.
     * @param length    the number of array elements to be copied.
     * @return dest
     * @throws IndexOutOfBoundsException if copying causes access of data outside array bounds.
     * @throws ArrayStoreException       if an element in the {@code src} array could not be stored into the {@code dest} array because of a type
     *                                   mismatch.
     * @throws NullPointerException      if either {@code src} or {@code dest} is {@code null}.
     * @since 1.1.2
     */
    public static <T> T arraycopy(final T source, final int sourcePos, final T dest, final int destPos, final int length) {
        System.arraycopy(source, sourcePos, dest, destPos, length);
        return dest;
    }


}
