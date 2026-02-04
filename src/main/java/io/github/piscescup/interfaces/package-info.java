/**
 * Core interface for java.lang.
 *
 * <h2>Functional Interfaces</h2>
 * <p>The {@code exfunction} sub-packages define a rich set of functional interfaces
 * extending the standard Java functional programming model:
 * <ul>
 *     <li>{@code Bin*}, {@code Tri*}, {@code Quad*}, {@code Quin*} variants for
 *     multi-argument functions, predicates, and consumers</li>
 *     <li>{@code failable} variants that allow checked or unchecked exceptions
 *     to be thrown during execution</li>
 * </ul>
 *
 * <h2>Structural and Utility Interfaces</h2>
 * <ul>
 *     <li>{@link io.github.piscescup.interfaces.Builder} – a minimal contract for
 *     builder-style object construction</li>
 *     <li>{@link io.github.piscescup.interfaces.Pair} – a lightweight two-value
 *     structural container</li>
 *     <li>{@link io.github.piscescup.interfaces.JsonElementable} – a contract for
 *     objects that can be represented as JSON elements</li>
 * </ul>
 *
 * <p>This package is intended to serve as a <strong>low-level, reusable interface layer</strong>
 * for higher-level modules such as data generation, command frameworks, and utility libraries.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
package io.github.piscescup.interfaces;
