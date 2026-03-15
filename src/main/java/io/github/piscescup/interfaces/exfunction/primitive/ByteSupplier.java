package io.github.piscescup.interfaces.exfunction.primitive;

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code byte}-valued results. This is the
 * {@code byte}-producing primitive specialization of {@link Supplier}.
 *
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsByte()}.
 *
 * @see Supplier
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ByteSupplier {

    /**
     * Gets a {@code byte} result.
     *
     * @return a {@code byte} result
     */
    byte getAsByte();
}