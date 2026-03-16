package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an operation upon three operands of the same type, producing a
 * result of the same type. This is a specialization of a tri-arity function
 * where all inputs and the output share the same type.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #apply(Object, Object, Object)}.
 *
 * @param <T> the operand and result type
 * @see TriFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface TriOperator<T> extends TriFunction<T, T, T, T> {

	/**
	 * Returns a tri-operator that always returns the given constant value,
	 * ignoring its inputs.
	 *
	 * @param constant the constant value to return, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return a {@code TriOperator} that always returns {@code constant}
	 * @throws NullPointerException if {@code constant} is {@code null}
	 */
	static <T> @NotNull TriOperator<T> constant(T constant) {
		NullCheck.requireNonNull(constant);
		return (a, b, c) -> constant;
	}

	/**
	 * Returns the supplied tri-operator instance.
	 *
	 * @param op the operator instance, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return the same {@code TriOperator} instance
	 * @throws NullPointerException if {@code op} is {@code null}
	 */
	static <T> TriOperator<T> of(TriOperator<T> op) {
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
	static <T> TriOperator<T> narrow(TriOperator<? super T> op) {
		NullCheck.requireNonNull(op);
		return (TriOperator<T>) op;
	}
}

