package io.github.piscescup.equalators;

import io.github.piscescup.interfaces.HashEqualator;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.text.Collator;
import java.util.Locale;

/**
 * Provides commonly used {@link HashEqualator} implementations for
 * {@link String} values.
 *
 * <p>The equalators defined by this class provide both equality comparison
 * and hash-code generation, making them suitable for hash-based collections
 * and algorithms such as distinct, grouping, joining, set operations, and
 * map construction.</p>
 *
 * <p>This class is modeled after the idea of .NET's string comparers, while
 * using Java string comparison semantics.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
public final class StringEqualators {

    /**
     * Prevents instantiation.
     */
    private StringEqualators() {
        throw new UnsupportedOperationException(
            "StringHashEqualators cannot be instantiated."
        );
    }
    /**
     * A hash equalator that compares strings using the default Java
     * case-sensitive equality and hashing semantics.
     *
     * <p>Equality is equivalent to {@link String#equals(Object)}, and hash
     * values are equivalent to {@link String#hashCode()}.</p>
     *
     * <p>{@code null} values are supported. Two {@code null} references are
     * considered equal, while a {@code null} reference and a non-null string
     * are considered different. The hash value of {@code null} is
     * {@code 0}.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Enumerable<String> values = Linq.of(
     *     "apple",
     *     "Apple",
     *     "apple"
     * );
     *
     * Enumerable<String> result = values.distinct(
     *     StringEqualators.ORDINAL
     * );
     *
     * // apple
     * // Apple
     * }</pre>
     */
    @NotNull
    public static final HashEqualator<String> ORDINAL =
        HashEqualator.defaultHashEqualator();

    /**
     * A hash equalator that compares strings without regard to case using
     * Java's case-insensitive string comparison semantics.
     *
     * <p>Equality is determined using
     * {@link String#equalsIgnoreCase(String)}. Hash values are computed from
     * a normalized representation of each string using {@link Locale#ROOT},
     * ensuring that strings considered equivalent by this equalator are
     * assigned compatible hash values.</p>
     *
     * <p>{@code null} values are supported.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * Enumerable<String> values = Linq.of(
     *     "apple",
     *     "APPLE",
     *     "Apple",
     *     "orange"
     * );
     *
     * Enumerable<String> result = values.distinct(
     *     StringEqualators.ORDINAL_IGNORE_CASE
     * );
     *
     * // apple
     * // orange
     * }</pre>
     */
    @NotNull
    public static final HashEqualator<String> ORDINAL_IGNORE_CASE =
        HashEqualator.of(
            String::equalsIgnoreCase,
            StringEqualators::hashIgnoreCase
        );


    /**
     * Returns a locale-sensitive hash equalator using the invariant locale.
     *
     * <p>This method uses {@link Locale#ROOT} together with
     * {@link Collator#TERTIARY} comparison strength. Case and other tertiary
     * differences are therefore taken into account.</p>
     *
     * @return a locale-sensitive hash equalator using the invariant locale
     */
    @NotNull
    public static HashEqualator<String> invariantCulture() {
        return forLocale(Locale.ROOT);
    }

    /**
     * Returns a case-insensitive locale-sensitive hash equalator using the
     * invariant locale.
     *
     * <p>This method uses {@link Locale#ROOT} together with
     * {@link Collator#SECONDARY} comparison strength. Case differences are
     * ignored while secondary differences, such as accents where applicable,
     * remain significant.</p>
     *
     * @return a case-insensitive locale-sensitive hash equalator using the
     *     invariant locale
     */
    @NotNull
    public static HashEqualator<String> invariantCultureIgnoreCase() {
        return forLocaleIgnoreCase(Locale.ROOT);
    }

    /**
     * Returns a locale-sensitive hash equalator using the current default
     * {@link Locale}.
     *
     * <p>The default locale is obtained when this method is invoked. Changing
     * the JVM default locale afterward does not change the behavior of an
     * already created equalator.</p>
     *
     * @return a locale-sensitive hash equalator using the current default
     *     locale
     */
    @NotNull
    public static HashEqualator<String> currentCulture() {
        return forLocale(Locale.getDefault());
    }

    /**
     * Returns a case-insensitive locale-sensitive hash equalator using the
     * current default {@link Locale}.
     *
     * <p>The default locale is obtained when this method is invoked. Changing
     * the JVM default locale afterward does not change the behavior of an
     * already created equalator.</p>
     *
     * @return a case-insensitive locale-sensitive hash equalator using the
     *     current default locale
     */
    @NotNull
    public static HashEqualator<String> currentCultureIgnoreCase() {
        return forLocaleIgnoreCase(Locale.getDefault());
    }

    /**
     * Returns a locale-sensitive hash equalator using the specified locale.
     *
     * <p>The returned equalator uses {@link Collator#TERTIARY} comparison
     * strength. Therefore, case and other tertiary differences are taken
     * into account when determining equality.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<String> equalator =
     *     StringEqualators.forLocale(Locale.CHINA);
     *
     * Enumerable<String> result =
     *     values.distinct(equalator);
     * }</pre>
     *
     * @param locale the locale whose collation rules are used
     * @return a hash equalator using the collation rules of the specified
     *     locale
     * @throws NullPointerException if {@code locale} is {@code null}
     */
    @NotNull
    public static HashEqualator<String> forLocale(
        @NotNull Locale locale
    ) {
        NullCheck.requireNonNull(locale);

        return new CollatorHashEqualator(
            locale,
            Collator.TERTIARY
        );
    }

    /**
     * Returns a case-insensitive locale-sensitive hash equalator using the
     * specified locale.
     *
     * <p>The returned equalator uses {@link Collator#SECONDARY} comparison
     * strength. At this strength, case differences are ignored while
     * secondary differences are preserved according to the collation rules
     * of the specified locale.</p>
     *
     * <b>Usage:</b>
     * <pre>{@code
     * HashEqualator<String> equalator =
     *     StringEqualators.forLocaleIgnoreCase(
     *         Locale.ENGLISH
     *     );
     *
     * Enumerable<String> result =
     *     values.distinct(equalator);
     * }</pre>
     *
     * @param locale the locale whose collation rules are used
     * @return a case-insensitive hash equalator using the collation rules of
     *     the specified locale
     * @throws NullPointerException if {@code locale} is {@code null}
     */
    @NotNull
    public static HashEqualator<String> forLocaleIgnoreCase(
        @NotNull Locale locale
    ) {
        NullCheck.requireNonNull(locale);

        return new CollatorHashEqualator(
            locale,
            Collator.SECONDARY
        );
    }

    /**
     * Computes a hash value for a string using a case-normalized
     * representation.
     *
     * @param value the string whose hash value is computed
     * @return the case-insensitive hash value
     */
    private static int hashIgnoreCase(
        @NotNull String value
    ) {
        return value
            .toUpperCase(Locale.ROOT)
            .hashCode();
    }

    /**
     * A {@link HashEqualator} implementation backed by a locale-specific
     * {@link Collator}.
     *
     * <p>A separate collator is maintained for each thread because
     * {@link Collator} instances are mutable. Equality and hashing are based
     * on the same collation rules and comparison strength.</p>
     */
    private static final class CollatorHashEqualator
        implements HashEqualator<String> {

        /**
         * The locale used to create collators.
         */
        @NotNull
        private final Locale locale;

        /**
         * The comparison strength used by the collator.
         */
        private final int strength;

        /**
         * The collator associated with each thread.
         */
        @NotNull
        private final ThreadLocal<Collator> collator;

        /**
         * Creates a locale-sensitive string hash equalator.
         *
         * @param locale   the locale whose collation rules are used
         * @param strength the comparison strength
         */
        private CollatorHashEqualator(
            @NotNull Locale locale,
            int strength
        ) {
            this.locale = locale;
            this.strength = strength;

            this.collator = ThreadLocal.withInitial(
                () -> createCollator(locale, strength)
            );
        }

        /**
         * Computes a hash value according to the configured collation rules.
         *
         * @param value the string whose hash value is computed
         * @return the collation-compatible hash value
         */
        @Override
        public int hash(String value) {
            if (value == null) {
                return 0;
            }

            return collator
                .get()
                .getCollationKey(value)
                .hashCode();
        }

        /**
         * Determines whether two strings are equal according to the configured
         * collation rules.
         *
         * @param left  the first string
         * @param right the second string
         * @return {@code true} if the strings are considered equal
         */
        @Override
        public boolean equals(
            String left,
            String right
        ) {
            if (left == right) {
                return true;
            }

            if (left == null || right == null) {
                return false;
            }

            return collator
                .get()
                .compare(left, right) == 0;
        }

        /**
         * Returns the locale used by this equalator.
         *
         * @return the locale used for comparison
         */
        @NotNull
        public Locale locale() {
            return locale;
        }

        /**
         * Returns the comparison strength used by this equalator.
         *
         * @return the collator comparison strength
         */
        public int strength() {
            return strength;
        }

        /**
         * Creates a collator for the specified locale and comparison strength.
         *
         * @param locale   the locale
         * @param strength the comparison strength
         * @return the configured collator
         */
        @NotNull
        private static Collator createCollator(
            @NotNull Locale locale,
            int strength
        ) {
            Collator collator =
                Collator.getInstance(locale);

            collator.setStrength(strength);

            return collator;
        }
    }

}