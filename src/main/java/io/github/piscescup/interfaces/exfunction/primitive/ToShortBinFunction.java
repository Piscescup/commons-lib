package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.BinFunction;
import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts two arguments and produces a {@code short}-valued result.
 *
 * <p>This is the {@code short}-producing primitive specialization for {@link BinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsShort(Object, Object)}.
 *
 * @param <X1> the type of the first argument
 * @param <X2> the type of the second argument
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToShortBinFunction<X1, X2> {

    short applyAsShort(X1 x1, X2 x2);

    default Short boxedApply(X1 x1, X2 x2) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        return applyAsShort(x1, x2);
    }
}