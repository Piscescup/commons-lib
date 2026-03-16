package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.interfaces.Memorized;
import io.github.piscescup.util.validation.NullCheck;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an operation on a single operand that produces a result of the
 * same type as its operand. This is a specialization of {@code Function} for
 * the case where the operand and result are of the same type.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the operand and result of the operator
 * @see BinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {

    /**
     * Returns a function that always returns its input (identity).
     *
     * @param <T> the type of the operand and result
     * @return a {@link UnaryOperator} that returns its input argument
     */
    static <T> @NotNull UnaryOperator<T> identity() {
        return t -> t;
    }

    /**
     * Returns a function that always returns the given constant, ignoring input.
     *
     * @param constant the constant value to return (must not be {@code null})
     * @param <T> the type of the operand and result
     * @return a {@link UnaryOperator} that always returns {@code constant}
     * @throws NullPointerException if {@code constant} is {@code null}
     */
    static <T> @NotNull UnaryOperator<T> constant(T constant) {
        NullCheck.requireNonNull(constant);
        return t -> constant;
    }

    /**
     * Returns the given operator instance (for API symmetry).
     *
     * @param op the operator instance, must not be {@code null}
     * @param <T> the type of the operand and result
     * @return the same {@code UnaryOperator} instance
     * @throws NullPointerException if {@code op} is {@code null}
     */
    static <T> UnaryOperator<T> of(UnaryOperator<T> op) {
        NullCheck.requireNonNull(op);
        return op;
    }

    /**
     * Narrows an operator with broader generic bounds to a more specific type.
     *
     * @param op the operator to narrow, must not be {@code null}
     * @param <T> the target operand/result type
     * @return the narrowed operator
     * @throws NullPointerException if {@code op} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <T> UnaryOperator<T> narrow(UnaryOperator<? super T> op) {
        NullCheck.requireNonNull(op);
        return (UnaryOperator<T>) op;
    }

    /**
     * Returns {@code true} if this operator is already memorized.
     *
     * @return {@code true} when this operator implements {@link Memorized}
     */
    default boolean isMemorized() {
        return this instanceof Memorized;
    }

    /**
     * Returns a memorized version of this operator (caches results by input).
     *
     * <p>The returned operator caches computed results in a thread-safe map keyed
     * by the input value. Subsequent calls with the same input will return the
     * cached result instead of recomputing it.
     *
     * @return a memorized {@link UnaryOperator} that caches results for inputs
     */
    default UnaryOperator<T> memorized() {
        if (isMemorized()) return this;
        final Map<T, T> cache = new ConcurrentHashMap<>();
        return (UnaryOperator<T> & Memorized) t -> cache.computeIfAbsent(t, this);
    }
}

