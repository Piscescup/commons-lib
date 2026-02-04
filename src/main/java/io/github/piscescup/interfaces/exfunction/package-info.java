/**
 * Extended functional interfaces beyond {@link java.util.function}.
 *
 * <p>This package provides multi-arity functional interfaces that complement the
 * standard Java {@code java.util.function} set. The goal is to offer a consistent
 * and readable functional programming model when more than two parameters are
 * required.
 *
 * <h2>Provided Arity Levels</h2>
 * <ul>
 *     <li>{@code Bin*} – two-argument variants (e.g., {@code BinFunction}, {@code BinPredicate}, {@code BinConsumer})</li>
 *     <li>{@code Tri*} – three-argument variants</li>
 *     <li>{@code Quad*} – four-argument variants</li>
 *     <li>{@code Quin*} – five-argument variants</li>
 * </ul>
 *
 * <h2>Composition & Fluent Usage</h2>
 * <p>Interfaces in this package are typically designed to support:
 * <ul>
 *     <li>Fluent partial application (currying-style helpers)</li>
 *     <li>Logical composition for predicates ({@code and}/{@code or}/{@code negate})</li>
 *     <li>Chaining for consumers/functions ({@code andThen}/{@code compose})</li>
 * </ul>
 *
 * <h2>Exception Semantics</h2>
 * <p>These interfaces follow standard Java functional semantics and therefore do not
 * declare checked exceptions. If you need exception-aware variants, see
 * the {@code failable} subpackage.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
package io.github.piscescup.interfaces.exfunction;
