package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an operation upon four operands of the same type, producing a
 * result of the same type. This is a specialization of the four-arity
 * function where inputs and output share the same type.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #apply(Object, Object, Object, Object)}.
 *
 * @param <T> the operand/result type
 * @see QuadFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface QuadOperator<T> extends QuadFunction<T, T, T, T, T> {

	/**
	 * Returns a quad-operator that always returns the given constant value,
	 * ignoring inputs.
	 *
	 * @param constant the constant to return, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return a {@code QuadOperator} that always returns {@code constant}
	 * @throws NullPointerException if {@code constant} is {@code null}
	 */
	static <T> @NotNull QuadOperator<T> constant(T constant) {
		NullCheck.requireNonNull(constant);
		return (a, b, c, d) -> constant;
	}

	/**
	 * Returns the supplied quad-operator instance.
	 *
	 * @param op the operator instance, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return the same {@code QuadOperator} instance
	 * @throws NullPointerException if {@code op} is {@code null}
	 */
	static <T> QuadOperator<T> of(QuadOperator<T> op) {
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
	static <T> QuadOperator<T> narrow(QuadOperator<? super T> op) {
		NullCheck.requireNonNull(op);
		return (QuadOperator<T>) op;
	}
}

