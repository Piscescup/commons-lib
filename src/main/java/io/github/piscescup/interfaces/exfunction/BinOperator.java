package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an operation upon two operands of the same type, producing a
 * result of the same type. This is a specialization of {@code BiFunction}
 * where both arguments and the result share the same type.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #apply(Object, Object)}.
 *
 * @param <T> the type of the operands and result of the operator
 * @see BinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface BinOperator<T> extends BinFunction<T, T, T> {

	/**
	 * Returns a binary operator that always returns the given constant value,
	 * ignoring its input operands.
	 *
	 * @param constant the constant value to return, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return a {@code BinOperator} that always returns {@code constant}
	 * @throws NullPointerException if {@code constant} is {@code null}
	 */
	static <T> @NotNull BinOperator<T> constant(T constant) {
		NullCheck.requireNonNull(constant);
		return (a, b) -> constant;
	}

	/**
	 * Returns the supplied operator instance.
	 *
	 * @param op the operator instance, must not be {@code null}
	 * @param <T> the operand/result type
	 * @return the same {@code BinOperator} instance
	 * @throws NullPointerException if {@code op} is {@code null}
	 */
	static <T> BinOperator<T> of(BinOperator<T> op) {
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
	static <T> BinOperator<T> narrow(BinOperator<? super T> op) {
		NullCheck.requireNonNull(op);
		return (BinOperator<T>) op;
	}
}

