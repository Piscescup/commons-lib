package io.github.piscescup.interfaces.exfunction.primitive;

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code char}-valued results. This is the
 * {@code char}-producing primitive specialization of {@link Supplier}.
 *
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsChar()}.
 *
 * @see Supplier
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface CharSupplier {

    /**
     * Gets a {@code char} result.
     *
     * @return a {@code char} result
     */
    char getAsChar();
}