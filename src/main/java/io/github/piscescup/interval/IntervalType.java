package io.github.piscescup.interval;

import io.github.piscescup.util.StringUtils;

/**
 * Represents the endpoint inclusiveness type of an {@link io.github.piscescup.math.interval.Interval}.
 *
 * <p>An {@code IntervalType} defines whether the start and end bounds of an interval
 * are inclusive or exclusive. Each type corresponds to a common mathematical
 * interval notation using parentheses {@code ()} and brackets {@code []}.
 *
 * <h2>Mathematical interpretation</h2>
 * <div style="text-align: center;">
 *   <table style="border-collapse: collapse; margin: auto;" border="1">
 *       <caption style="font-weight: bold; padding: 6px;">
 *         Interval Type Notation Table
 *       </caption>
 *     <tr>
 *       <th style="padding:4px;">IntervalType</th>
 *       <th style="padding:4px;">Notation</th>
 *       <th style="padding:4px;">Meaning</th>
 *     </tr>
 *     <tr>
 *       <td style="padding:4px;">{@link #OPEN_INTERVAL}</td>
 *       <td style="padding:4px;">{@code (a, b)}</td>
 *       <td style="padding:4px;">Exclusive start and exclusive end</td>
 *     </tr>
 *     <tr>
 *       <td style="padding:4px;">{@link #CLOSED_INTERVAL}</td>
 *       <td style="padding:4px;">{@code [a, b]}</td>
 *       <td style="padding:4px;">Inclusive start and inclusive end</td>
 *     </tr>
 *     <tr>
 *       <td style="padding:4px;">{@link #OPEN_CLOSED_INTERVAL}</td>
 *       <td style="padding:4px;">{@code (a, b]}</td>
 *       <td style="padding:4px;">Exclusive start and inclusive end</td>
 *     </tr>
 *     <tr>
 *       <td style="padding:4px;">{@link #CLOSED_OPEN_INTERVAL}</td>
 *       <td style="padding:4px;">{@code [a, b)}</td>
 *       <td style="padding:4px;">Inclusive start and exclusive end</td>
 *     </tr>
 *   </table>
 * </div>
 *
 * <h2>Usage examples</h2>
 *
 * <pre>{@code
 * Interval<Integer> a =
 *     Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
 *
 * Interval<Integer> b =
 *     Interval.naturalOrderedInterval(2, 6, IntervalType.OPEN_OPEN_INTERVAL); // (2,6)
 *
 * a.contains(2); // true
 * b.contains(2); // false
 * }</pre>
 *
 * <p>This enum also provides formatting utilities to produce human-readable
 * representations of intervals.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public enum IntervalType {

    /**
     * Open interval {@code (a, b)}.
     *
     * <p>Both the start and end bounds are exclusive.
     */
    OPEN_INTERVAL("(", ")") {
        @Override
        public boolean isStartInclusive() {
            return false;
        }

        @Override
        public boolean isEndInclusive() {
            return false;
        }
    },

    /**
     * Closed interval {@code [a, b]}.
     *
     * <p>Both the start and end bounds are inclusive.
     */
    CLOSED_INTERVAL("[", "]") {
        @Override
        public boolean isStartInclusive() {
            return true;
        }

        @Override
        public boolean isEndInclusive() {
            return true;
        }
    },

    /**
     * Open-closed interval {@code (a, b]}.
     *
     * <p>The start bound is exclusive and the end bound is inclusive.
     */
    OPEN_CLOSED_INTERVAL("(", "]") {
        @Override
        public boolean isStartInclusive() {
            return false;
        }

        @Override
        public boolean isEndInclusive() {
            return true;
        }
    },

    /**
     * Closed-open interval {@code [a, b)}.
     *
     * <p>The start bound is inclusive and the end bound is exclusive.
     */
    CLOSED_OPEN_INTERVAL("[", ")") {
        @Override
        public boolean isStartInclusive() {
            return true;
        }

        @Override
        public boolean isEndInclusive() {
            return false;
        }
    };

    /**
     * The symbol representing the start endpoint.
     */
    private final String startSymbol;

    /**
     * The symbol representing the end endpoint.
     */
    private final String endSymbol;

    IntervalType(final String startSymbol, final String endSymbol) {
        this.startSymbol = startSymbol;
        this.endSymbol = endSymbol;
    }

    /**
     * Returns the symbol used for the start endpoint of this interval type.
     *
     * @return the start endpoint symbol ({@code "("} or {@code "["})
     */
    public String startSymbol() {
        return startSymbol;
    }

    /**
     * Returns the symbol used for the end endpoint of this interval type.
     *
     * @return the end endpoint symbol ({@code ")"} or {@code "]"})
     */
    public String endSymbol() {
        return endSymbol;
    }

    /**
     * Returns the {@code IntervalType} corresponding to the given inclusiveness flags.
     *
     * <pre>{@code
     * IntervalType.of(true, true);   // CLOSED_INTERVAL
     * IntervalType.of(false, false); // OPEN_INTERVAL
     * IntervalType.of(false, true);  // OPEN_CLOSED_INTERVAL
     * IntervalType.of(true, false);  // CLOSED_OPEN_INTERVAL
     * }</pre>
     *
     * @param startInclusive {@code true} if the start bound is inclusive
     * @param endInclusive   {@code true} if the end bound is inclusive
     * @return the corresponding {@code IntervalType}
     */
    public static IntervalType of(boolean startInclusive, boolean endInclusive) {
        if (startInclusive && endInclusive) return CLOSED_INTERVAL;
        if (!startInclusive && !endInclusive) return OPEN_INTERVAL;
        if (!startInclusive) return OPEN_CLOSED_INTERVAL;
        return CLOSED_OPEN_INTERVAL;
    }

    /**
     * Returns {@code true} if the start endpoint is inclusive.
     *
     * @return {@code true} if the start bound is inclusive
     */
    abstract boolean isStartInclusive();

    /**
     * Returns {@code true} if the end endpoint is inclusive.
     *
     * @return {@code true} if the end bound is inclusive
     */
    abstract boolean isEndInclusive();

    /**
     * Returns a human-readable name of this interval type.
     *
     * <p>The name is derived from the enum constant name and converted
     * to space-separated title case.
     *
     * @return a human-readable string representation
     */
    @Override
    public String toString() {
        return StringUtils.toSpaceTitleUpperCase(name());
    }
}
