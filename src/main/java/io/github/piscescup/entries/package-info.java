
/**
 * Provides a set of generic {@code Entry}s with fixed arity,
 * intended to model small, ordered tuples with semantic names.
 *
 * <p>This package defines a hierarchy of entry types such as
 * {@link io.github.piscescup.entries.Entry},
 * {@link io.github.piscescup.entries.BinEntry},
 * {@link io.github.piscescup.entries.TriEntry},
 * {@link io.github.piscescup.entries.QuadEntry},
 * and {@link io.github.piscescup.entries.QuinEntry},
 * representing entries with one to five components respectively.
 *
 * <p>Unlike generic collections or arrays, the entry types in this package:
 * <ul>
 *   <li>Have a fixed and explicit arity</li>
 *   <li>Provide strong generic type safety</li>
 *   <li>Emphasize semantic meaning over positional indexing</li>
 * </ul>
 *
 * <p>This package is designed to be independent of any concrete storage
 * or collection framework and can be freely used across different modules.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
package io.github.piscescup.entries;
