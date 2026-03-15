package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an operation upon five operands of the same type, producing a
 * result of the same type. This is a specialization of the five-arity
 * function where inputs and output share the same type.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #apply(Object, Object, Object, Object, Object)}.
 *
 * @param <T> the operand/result type
 * @see QuinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface QuinOperator<T> extends QuinFunction<T, T, T, T, T, T> {

	/**
	 * Returns a quin-operator that always returns the given constant value,
	 * ignoring inputs.
	 *
	 * @param constant the constant to return, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return a {@code QuinOperator} that always returns {@code constant}
	 * @throws NullPointerException if {@code constant} is {@code null}
	 */
	static <T> @NotNull QuinOperator<T> constant(T constant) {
		NullCheck.requireNonNull(constant);
		return (a, b, c, d, e) -> constant;
	}

	/**
	 * Returns the supplied quin-operator instance.
	 *
	 * @param op the operator instance, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return the same {@code QuinOperator} instance
	 * @throws NullPointerException if {@code op} is {@code null}
	 */
	static <T> QuinOperator<T> of(QuinOperator<T> op) {
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
	static <T> QuinOperator<T> narrow(QuinOperator<? super T> op) {
		NullCheck.requireNonNull(op);
		return (QuinOperator<T>) op;
	}
}

