/**
 * Exception-aware ("failable") functional interfaces.
 *
 * <p>This package contains {@code Failable} functional interfaces mirroring those in
 * {@link io.github.piscescup.interfaces.exfunction}, but allowing the functional method
 * to throw an exception of type {@code E}.
 *
 * <p>These interfaces are useful when integrating with APIs that may throw checked
 * exceptions (I/O, reflection, parsing, database operations, etc.) while still keeping
 * functional composition and pipeline readability.
 *
 * <h2>Typed Exception Model</h2>
 * <p>Most interfaces declare a type parameter {@code E extends Throwable} and a
 * primary method such as:
 * <ul>
 *     <li>{@code applyOrThrow(...)} for functions</li>
 *     <li>{@code testOrThrow(...)} for predicates</li>
 *     <li>{@code acceptOrThrow(...)} for consumers</li>
 * </ul>
 *
 * <p>Convenience default methods may be provided to adapt to non-throwing counterparts
 * or to handle exceptions via user-supplied strategies.
 *
 * <h2>Design Goals</h2>
 * <ul>
 *     <li>Make exception flow explicit and type-safe</li>
 *     <li>Enable composition without losing checked-exception information</li>
 *     <li>Minimize boilerplate in high-arity functional pipelines</li>
 * </ul>
 *
 * <p>If you do not need checked-exception support, prefer the non-failable interfaces in
 * {@link io.github.piscescup.interfaces.exfunction}.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
package io.github.piscescup.interfaces.exfunction.failable;
