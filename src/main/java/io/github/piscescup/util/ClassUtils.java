package io.github.piscescup.util;

/**
 * Utility class for {@link Class}
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public final class ClassUtils {
    private ClassUtils() {}

    /**
     * Returns the class of the given object.
     *
     * @param <T> the type of the object
     * @param object the object to get the class from
     * @return the Class object associated with the object, or {@code null} if the object is {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(final T object) {
        return object == null ? null : (Class<T>) object.getClass();
    }

    /**
     * Returns the component type of an array class.
     *
     * @param <T> the type of the array
     * @param clazz the Class object representing the array type
     * @return the Class representing the component type of this array, or {@code null} if the input is {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getComponentType(Class<T[]> clazz) {
        return clazz == null ? null : (Class<T>) clazz.getComponentType();
    }


}
