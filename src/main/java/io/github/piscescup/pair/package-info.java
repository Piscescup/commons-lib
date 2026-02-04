
/**
 * Provides generic {@code Pair}-based abstractions for holding two related values,
 * with explicit support for both immutable and mutable semantics.
 *
 * <p>The classed in this package are the implemention of the core {@link io.github.piscescup.interfaces.Pair} interface.
 * <ul>
 *   <li>{@link io.github.piscescup.pair.ImmutablePair} – an immutable pair whose
 *       components cannot be modified after creation</li>
 *   <li>{@link io.github.piscescup.pair.MutablePair} – a mutable pair allowing
 *       controlled updates to its components</li>
 * </ul>
 *
 * <p>The distinction between immutable and mutable variants is explicit and intentional:
 * <ul>
 *   <li>Immutable pairs are thread-safe by design and suitable for value objects</li>
 *   <li>Mutable pairs are intended for local, controlled mutation scenarios</li>
 * </ul>
 *
 * <p>This package avoids implicit mutability and encourages developers to select
 * the appropriate pair type based on their use case.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
package io.github.piscescup.pair;
