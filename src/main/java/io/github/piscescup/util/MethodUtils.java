package io.github.piscescup.util;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * Utility class for {@link Method}.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public final class MethodUtils {

    private MethodUtils() {
        throw new UnsupportedOperationException(
            "No instance for " + MethodUtils.class.getCanonicalName()
        );
    }

    /**
     * Checks whether the given method is declared as {@code static}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is static, {@code false} otherwise
     */
    public static boolean isStatic(final Method method) {
        return method != null && Modifier.isStatic(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code public}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is public, {@code false} otherwise
     */
    public static boolean isPublic(final Method method) {
        return method != null && Modifier.isPublic(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code private}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is private, {@code false} otherwise
     */
    public static boolean isPrivate(final Method method) {
        return method != null && Modifier.isPrivate(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code protected}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is protected, {@code false} otherwise
     */
    public static boolean isProtected(final Method method) {
        return method != null && Modifier.isProtected(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code final}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is final, {@code false} otherwise
     */
    public static boolean isFinal(final Method method) {
        return method != null && Modifier.isFinal(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code public static}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is public and static, {@code false} otherwise
     */
    public static boolean isPublicStatic(final Method method) {
        return method != null
            && Modifier.isPublic(method.getModifiers())
            && Modifier.isStatic(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code private static}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is private and static, {@code false} otherwise
     */
    public static boolean isPrivateStatic(final Method method) {
        return method != null
            && Modifier.isPrivate(method.getModifiers())
            && Modifier.isStatic(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code public static final}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is public, static and final, {@code false} otherwise
     */
    public static boolean isPublicStaticFinal(final Method method) {
        return method != null
            && Modifier.isPublic(method.getModifiers())
            && Modifier.isStatic(method.getModifiers())
            && Modifier.isFinal(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code private static final}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is private, static and final, {@code false} otherwise
     */
    public static boolean isPrivateStaticFinal(final Method method) {
        return method != null
            && Modifier.isPrivate(method.getModifiers())
            && Modifier.isStatic(method.getModifiers())
            && Modifier.isFinal(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code abstract}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is abstract, {@code false} otherwise
     */
    public static boolean isAbstract(final Method method) {
        return method != null && Modifier.isAbstract(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code synchronized}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is synchronized, {@code false} otherwise
     */
    public static boolean isSynchronized(final Method method) {
        return method != null && Modifier.isSynchronized(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared as {@code native}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is native, {@code false} otherwise
     */
    public static boolean isNative(final Method method) {
        return method != null && Modifier.isNative(method.getModifiers());
    }

    /**
     * Checks whether the given method is declared with {@code strictfp}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is strictfp, {@code false} otherwise
     */
    public static boolean isStrict(final Method method) {
        return method != null && Modifier.isStrict(method.getModifiers());
    }

    /**
     * Checks whether the given method has package-private visibility.
     * <p>
     * A package-private method is one that is not declared as
     * {@code public}, {@code protected}, or {@code private}.
     *
     * @param method the method to inspect
     * @return {@code true} if the method is package-private, {@code false} otherwise
     */
    public static boolean isPackagePrivate(final Method method) {
        if (method == null) return false;

        int modifiers = method.getModifiers();
        return !(Modifier.isPublic(modifiers)
            || Modifier.isProtected(modifiers)
            || Modifier.isPrivate(modifiers));
    }
}
