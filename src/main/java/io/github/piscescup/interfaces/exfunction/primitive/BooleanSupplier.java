package io.github.piscescup.interfaces.exfunction.primitive;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code boolean}-valued results. This is the
 * {@code boolean}-producing primitive specialization of {@link Supplier}.
 *
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsBoolean()}.
 *
 * @see Supplier
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface BooleanSupplier {

    /**
     * Gets a {@code boolean} result.
     *
     * @return a {@code boolean} result
     */
    boolean getAsBoolean();

    /**
     * Returns a {@code BooleanSupplier} that always returns {@code true}.
     * @return a {@code BooleanSupplier} that always returns {@code true}.
     */
    @Contract(pure = true)
    static @NotNull BooleanSupplier always() {
        return () -> true;
    }

    /**
     * Returns a {@code BooleanSupplier} that always returns {@code false}.
     * @return a {@code BooleanSupplier} that always returns {@code false}
     */
    @Contract(pure = true)
    static @NotNull BooleanSupplier never() {
        return () -> false;
    }
}