package io.github.piscescup.util;

import io.github.piscescup.exception.IllegalBuilderPatternConfigurationException;
import io.github.piscescup.interfaces.Builder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public class CompareUtils {

    @Contract(value = " -> new", pure = true)
    public static @NotNull CompareBuilder createCompareBuilder() {
        return new CompareBuilder();
    }


    /**
     * A fluent builder for performing chained, lexicographical comparisons
     * and producing a final comparison result.
     *
     * <p>This class implements a comparison strategy similar to
     * {@code Comparable#compareTo(Object)} and is intended to simplify
     * multi-field comparison logic by allowing comparisons to be
     * expressed in a fluent, readable manner.
     *
     * <h2>Comparison Rules</h2>
     *
     * <p>The comparison follows these rules strictly:
     *
     * <ol>
     *   <li><b>Short-circuit evaluation</b><br>
     *       Once a non-zero comparison result is produced, all subsequent
     *       {@code compare(...)} calls are ignored.</li>
     *
     *   <li><b>Lexicographical order</b><br>
     *       Fields are compared in the order they are added to the builder.
     *       The first differing field determines the final result.</li>
     *
     *   <li><b>Primitive comparison</b><br>
     *       Primitive values are compared using their corresponding
     *       {@code compare} methods, such as
     *       {@link Integer#compare(int, int)} and
     *       {@link Double#compare(double, double)}.</li>
     *
     *   <li><b>Array comparison</b><br>
     *       Arrays are compared lexicographically using
     *       {@link java.util.Arrays#compare}:
     *       <ul>
     *         <li>Elements are compared in index order</li>
     *         <li>The first differing element determines the result</li>
     *         <li>If all elements are equal, the shorter array is considered smaller</li>
     *       </ul>
     *       This applies to both primitive arrays and object arrays.</li>
     *
     *   <li><b>Object comparison</b><br>
     *       For object comparisons:
     *       <ul>
     *         <li>{@code null} is considered less than any non-null value</li>
     *         <li>If both references are identical ({@code self == other}),
     *             the values are considered equal</li>
     *         <li>If a {@link Comparator} is provided, it is used</li>
     *         <li>Otherwise, {@link Comparable#compareTo(Object)} is used</li>
     *       </ul>
     *   </li>
     * </ol>
     *
     * <h2>Usage Example</h2>
     *
     * <pre>{@code
     * int result = CompareUtils.create()
     *     .compareInt(id1, id2)
     *     .compare(name1, name2)
     *     .compare(age1, age2)
     *     .compare(tags1, tags2)
     *     .executeCompare(); // Recommended
     *     // .build()
     * }</pre>
     *
     * <p>This class is mutable and not thread-safe.
     * Each {@code CompareBuilder} instance is intended to be used for
     * a single comparison operation.
     *
     * @author REN YuanTong
     * @since 1.0.0
     */
    public static final class CompareBuilder implements Builder<Integer> {

        private int comparison;

        private CompareBuilder() {}

        /**
         * Creates a new {@code CompareBuilder} instance.
         *
         * <p>The returned builder starts with an initial comparison result of {@code 0}.</p>
         *
         * @return a new {@code CompareBuilder}
         */
        @Contract(value = " -> new", pure = true)
        public static @NotNull CompareBuilder create() {
            return new CompareBuilder();
        }

        /**
         * Compares two {@code char} values.
         *
         * @param self  the first value
         * @param other the second value
         * @return this builder instance for method chaining
         */
        public @NotNull CompareBuilder compareChar(final char self, final char other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Character.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code char} arrays lexicographically.
         *
         * @param self  the first array
         * @param other the second array
         * @return this builder instance for method chaining
         */
        public @NotNull CompareBuilder compareCharArray(final char[] self, final char[] other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Arrays.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code byte} values.
         */
        public @NotNull CompareBuilder compareByte(final byte self, final byte other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Byte.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code byte} arrays lexicographically.
         */
        public @NotNull CompareBuilder compareByteArray(final byte[] self, final byte[] other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Arrays.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code short} values.
         */
        public @NotNull CompareBuilder compareShort(final short self, final short other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Short.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code short} arrays lexicographically.
         */
        public @NotNull CompareBuilder compareShortArray(final short[] self, final short[] other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Arrays.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code int} values.
         */
        public @NotNull CompareBuilder compareInt(final int self, final int other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Integer.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code int} arrays lexicographically.
         */
        public @NotNull CompareBuilder compareIntArray(final int[] self, final int[] other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Arrays.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code long} values.
         */
        public @NotNull CompareBuilder compareLong(final long self, final long other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Long.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code long} arrays lexicographically.
         */
        public @NotNull CompareBuilder compareLongArray(final long[] self, final long[] other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Arrays.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code float} values.
         */
        public @NotNull CompareBuilder compareFloat(final float self, final float other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Float.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code float} arrays lexicographically.
         */
        public @NotNull CompareBuilder compareFloatArray(final float[] self, final float[] other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Arrays.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code double} values.
         */
        public @NotNull CompareBuilder compareDouble(final double self, final double other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Double.compare(self, other);
            return this;
        }

        /**
         * Compares two {@code boolean} values.
         */
        public @NotNull CompareBuilder compareBoolean(final boolean self, final boolean other) {
            if (comparison != 0) {
                return this;
            }
            comparison = Boolean.compare(self, other);
            return this;
        }

        /**
         * Compares two objects using the specified comparator or natural ordering.
         *
         * <p>If {@code comparator} is {@code null}, natural ordering is used and the
         * objects must implement {@link Comparable}.</p>
         *
         * @param <T>        the type of objects being compared
         * @param self       the first object
         * @param other      the second object
         * @param comparator the comparator to use, or {@code null}
         * @return this builder instance for method chaining
         */
        public <T extends Comparable<? super T>>
        @NotNull CompareBuilder compare(final T self, final T other, final Comparator<? super T> comparator) {
            if (comparison != 0) {
                return this;
            }
            if (self == other) {
                return this;
            }
            if (self == null || other == null) {
                comparison = self == null ? -1 : 1;
                return this;
            }
            comparison = comparator == null
                ? self.compareTo(other)
                : comparator.compare(self, other);
            return this;
        }

        /**
         * Compares two objects using their natural ordering.
         *
         * @param <T>   the type of objects being compared
         * @param self  the first object
         * @param other the second object
         * @return this builder instance for method chaining
         */
        public <T extends Comparable<? super T>>
        @NotNull CompareBuilder compare(final T self, final T other) {
            return compare(self, other, null);
        }

        /**
         * Attempts to compare two objects using a comparator or natural ordering.
         *
         * <p>This method performs runtime checks and is more permissive than
         * {@link #compare}.</p>
         *
         * @param <T>        the type of objects being compared
         * @param self       the first object
         * @param other      the second object
         * @param comparator the comparator to use, or {@code null}
         * @return this builder instance for method chaining
         *
         * @throws IllegalArgumentException if natural ordering is required but the
         *                                  objects do not implement {@link Comparable}
         * @throws ClassCastException if natural ordering fails at runtime
         */
        public <T> CompareBuilder tryCompare(final T self, final T other, final Comparator<? super T> comparator) throws ClassCastException {
            if (comparison != 0) {
                return this;
            }
            if (self == other) {
                return this;
            }
            if (self == null || other == null) {
                comparison = self == null ? -1 : 1;
                return this;
            }
            if (comparator != null) {
                comparison = comparator.compare(self, other);
                return this;
            }
            if (!(self instanceof Comparable && other instanceof Comparable)) {
                throw new IllegalArgumentException("self and other must implement Comparable");
            }
            try {
                @SuppressWarnings("unchecked")
                Comparable<? super T> selfComparable = (Comparable<? super T>) self;
                comparison = selfComparable.compareTo(other);
            } catch (ClassCastException e) {
                throw new ClassCastException(
                    "Natural comparison failed: self is Comparable but cannot compare to other. " +
                        "self=" + self.getClass().getName() +
                        ", other=" + other.getClass().getName()
                );
            }
            return this;
        }

        /**
         * Attempts to compare two objects using natural ordering.
         */
        public <T> CompareBuilder tryCompare(final T self, final T other) {
            return tryCompare(self, other, null);
        }

        /**
         * Returns the accumulated comparison result.
         *
         * @return the comparison result
         */
        public int executeCompare() {
            return comparison;
        }

        /**
         * Builds and returns the final comparison result.
         *
         * @return the comparison result
         */
        @Override
        public Integer build() throws IllegalBuilderPatternConfigurationException {
            return executeCompare();
        }
    }

}
