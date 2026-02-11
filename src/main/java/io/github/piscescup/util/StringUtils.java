package io.github.piscescup.util;

import io.github.piscescup.math.interval.ComparatorOrderedInterval;
import io.github.piscescup.math.interval.IntervalType;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for {@link String} operations.
 * <p>
 * This class provides null-safe utility methods for common
 * {@link String} checks and transformations.
 * </p>
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public final class StringUtils {

    private StringUtils() {}

    /**
     * An empty string constant: {@code ""}.
     */
    public static final String EMPTY_STRING = "";

    /**
     * A single space string constant: {@code " "}.
     */
    public static final String SPACE_STRING = " ";

    /**
     * A single space string constant: {@code "_"}.
     */
    public static final String UNDERLINE = "_";

    /**
     * A single space string constant: {@code "-"}.
     */
    public static final String HYPHEN = "-";

    /**
     * A single space string constant: {@code "..."}.
     */
    public static final String ELLIPSIS_3 = "...";

    /**
     * A single space string constant: {@code "... ..."}.
     */
    public static final String ELLIPSIS_6 = "... ...";


    /**
     * Checks whether the given string is {@code null}.
     *
     * @param string the string to check
     * @return {@code true} if the string is {@code null}, {@code false} otherwise
     */
    public static boolean isNull(final String string) {
        return string == null;
    }

    /**
     * Checks whether the given string is not {@code null}.
     *
     * @param string the string to check
     * @return {@code true} if the string is not {@code null}, {@code false} otherwise
     */
    public static boolean isNotNull(final String string) {
        return string != null;
    }

    /**
     * Checks whether the given string is {@code null} or empty.
     *
     * <p>
     * A string is considered empty if it is {@code null} or has
     * a length of {@code 0}.
     * </p>
     *
     * @param string the string to check
     * @return {@code true} if the string is {@code null} or empty,
     *         {@code false} otherwise
     */
    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Checks whether the given string is not {@code null} and not empty.
     *
     * @param string the string to check
     * @return {@code true} if the string is not empty,
     *         {@code false} otherwise
     */
    public static boolean isNotEmpty(final String string) {
        return !isEmpty(string);
    }

    /**
     * Checks whether the given string is blank.
     * <p>
     * A character is a Java
     * whitespace character if and only if it satisfies one of the
     * following criteria:
     * <ul>
     * <li> It is a Unicode space character ({@code SPACE_SEPARATOR},
     *      {@code LINE_SEPARATOR}, or {@code PARAGRAPH_SEPARATOR})
     *      but is not also a non-breaking space ({@code '\u005Cu00A0'},
     *      {@code '\u005Cu2007'}, {@code '\u005Cu202F'}).
     * <li> It is {@code '\u005Ct'}, U+0009 HORIZONTAL TABULATION.
     * <li> It is {@code '\u005Cn'}, U+000A LINE FEED.
     * <li> It is {@code '\u005Cu000B'}, U+000B VERTICAL TABULATION.
     * <li> It is {@code '\u005Cf'}, U+000C FORM FEED.
     * <li> It is {@code '\u005Cr'}, U+000D CARRIAGE RETURN.
     * <li> It is {@code '\u005Cu001C'}, U+001C FILE SEPARATOR.
     * <li> It is {@code '\u005Cu001D'}, U+001D GROUP SEPARATOR.
     * <li> It is {@code '\u005Cu001E'}, U+001E RECORD SEPARATOR.
     * <li> It is {@code '\u005Cu001F'}, U+001F UNIT SEPARATOR.
     * </ul>
     *
     * <p>
     * A string is considered blank if it is {@code null}, empty,
     * or contains only whitespace characters.
     * </p>
     *
     * @param string the string to check
     * @return {@code true} if the string is blank,
     *         {@code false} otherwise
     */
    public static boolean isBlank(final String string) {
        if (string == null) return true;
        int length = string.length();
        if (length == 0) return true;
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the given string is not blank.
     *
     * @param string the string to check
     * @return {@code true} if the string is not blank,
     *         {@code false} otherwise
     */
    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }

    /**
     * Trims leading and trailing whitespace from the given string.
     *
     * @param string the string to trim
     * @return the trimmed string, or {@code null} if the input string is {@code null}
     */
    public static String trim(final String string) {
        return string == null ? null : string.trim();
    }

    /**
     * Trims the given string or returns the specified default value
     * if the string is {@code null}.
     *
     * @param string the string to trim
     * @param defaultValue the value to return if the string is {@code null}
     * @return the trimmed string, or the default value if the string is {@code null}
     */
    public static String trimOrDefault(final String string, final String defaultValue) {
        return string == null ? defaultValue : string.trim();
    }

    /**
     * Trims the given string or returns an empty string
     * if the string is {@code null}.
     *
     * @param string the string to trim
     * @return the trimmed string, or an empty string if the string is {@code null}
     */
    public static String trimOrEmpty(final String string) {
        return trimOrDefault(string, EMPTY_STRING);
    }

    /**
     * Returns the length of the given string.
     *
     * <p>
     * If the string is {@code null}, this method returns {@code 0}.
     * </p>
     *
     * @param string the string to check
     * @return the length of the string, or {@code 0} if the string is {@code null}
     */
    public static int length(final String string) {
        return string == null ? 0 : string.length();
    }

    /**
     * Converts the given word to <strong>title case</strong>.
     *
     * <p>
     * A word is converted according to the following rules:
     * </p>
     * <ul>
     *   <li>If the input is {@code null} or empty, an empty string is returned.</li>
     *   <li>If the word consists entirely of uppercase letters
     *       (as determined by {@link #isAllUpper(String)}), it is returned unchanged.</li>
     *   <li>If the word contains a single character, that character is converted to upper case.</li>
     *   <li>Otherwise, the first character is converted to upper case and
     *       the remaining characters are converted to lower case.</li>
     * </ul>
     *
     * <p>
     * This method operates on a <em>single word</em> and does not perform
     * any word splitting. It is intended to be used as a building block
     * for higher-level case conversion methods (e.g. camel case, snake case,
     * space-separated title case).
     * </p>
     *
     * <pre>{@code
     * StringUtils.titleCaseWord("file");    // File
     * StringUtils.titleCaseWord("File");    // File
     * StringUtils.titleCaseWord("f");       // F
     *
     * StringUtils.titleCaseWord("XML");     // Xml
     * StringUtils.titleCaseWord("HTTP");    // Http
     *
     * StringUtils.titleCaseWord("xml");     // Xml
     * StringUtils.titleCaseWord("");        // ""
     * StringUtils.titleCaseWord(null);      // ""
     * }</pre>
     *
     * @param w the input word
     * @return the title-cased word, or an empty string if {@code w} is {@code null} or empty
     */
    public static String titleCaseWord(String w) {
        if ( w == null || w.isEmpty() ) return EMPTY_STRING;
        if (w.length() == 1) return String.valueOf(Character.toUpperCase(w.charAt(0)));
        return Character.toUpperCase(w.charAt(0)) + w.substring(1).toLowerCase(Locale.ROOT);
    }

    /**
     * Determines whether the given string is composed entirely of
     * uppercase letters.
     *
     * <p>
     * A string is considered {@code all upper case} if and only if:
     * </p>
     * <ul>
     *   <li>It contains <strong>at least one letter</strong>, and</li>
     *   <li>All letter characters in the string are uppercase
     *       according to {@link Character#isUpperCase(char)}</li>
     * </ul>
     *
     * <p>
     * Non-letter characters (such as digits, underscores, hyphens,
     * and other symbols) are ignored when determining the result.
     * </p>
     *
     * <pre>{@code
     * StringUtils.isAllUpper("XML");        // true
     * StringUtils.isAllUpper("HTTP2");      // true
     * StringUtils.isAllUpper("XML_FILE");   // true
     *
     * StringUtils.isAllUpper("Xml");        // false
     * StringUtils.isAllUpper("xml");        // false
     * StringUtils.isAllUpper("123");        // false
     * StringUtils.isAllUpper("");           // false
     * }</pre>
     *
     *
     * @param s the input string to check
     * @return {@code true} if the string contains at least one letter and
     *         all letter characters are uppercase; {@code false} otherwise
     * @throws NullPointerException if {@code s} is {@code null}
     */
    public static boolean isAllUpper(@NotNull String s) {
        if (isEmpty(s)) return false;

        boolean hasLetter = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                hasLetter = true;
                if (!Character.isUpperCase(c)) return false;
            }
        }

        return hasLetter;
    }

    /**
     * Converts the given string to <strong>UpperCamelCase</strong> (also known as PascalCase).
     *
     * <p>
     * Words are detected using spaces, underscores, hyphens, and camel-case boundaries.
     * Acronyms written in all-uppercase (e.g. {@code XML}, {@code HTTP}) are preserved
     * as whole words and will not be partially lower-cased.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toUpperCamel("hello world");   // HelloWorld
     * StringUtils.toUpperCamel("hello_world");   // HelloWorld
     * StringUtils.toUpperCamel("hello-world");   // HelloWorld
     * StringUtils.toUpperCamel("HelloWorld");    // HelloWorld
     * StringUtils.toUpperCamel("Hello World");   // HelloWorld
     *
     * StringUtils.toUpperCamel("XMLFile");       // XmlFile
     * StringUtils.toUpperCamel("XML File");      // XmlFile
     * StringUtils.toUpperCamel("XML_file");      // XmlFile
     * StringUtils.toUpperCamel("XML-File");      // XmlFile
     *
     * StringUtils.toUpperCamel("STATIC_FINAL");  // StaticFinal
     * }</pre>
     *
     * @param input the input string
     * @return the converted UpperCamelCase string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toUpperCamel(String input) {
        NullCheck.requireNonNull(input, "input must not be null");
        List<String> parts = splitWords(input);
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(titleCaseWord(part));
        }
        return sb.toString();
    }

    /**
     * Converts the given string to <strong>lowerCamelCase</strong>.
     *
     * <p>
     * Words are detected using spaces, underscores, hyphens, and camel-case boundaries.
     * The first word is always fully lower-cased, while subsequent words are converted
     * to title case. Acronyms written in all-uppercase (e.g. {@code XML}, {@code HTTP})
     * are preserved as whole words and will not be partially lower-cased.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toLowerCamel("hello world");   // helloWorld
     * StringUtils.toLowerCamel("hello_world");   // helloWorld
     * StringUtils.toLowerCamel("hello-world");   // helloWorld
     * StringUtils.toLowerCamel("HelloWorld");    // helloWorld
     * StringUtils.toLowerCamel("Hello World");   // helloWorld
     *
     * StringUtils.toLowerCamel("XMLFile");       // xmlFile
     * StringUtils.toLowerCamel("XML File");      // xmlFile
     * StringUtils.toLowerCamel("XML_file");      // xmlFile
     * StringUtils.toLowerCamel("XML-File");      // xmlFile
     *
     * StringUtils.toLowerCamel("STATIC_FINAL");  // staticFinal
     * }</pre>
     *
     * @param input the input string
     * @return the converted lowerCamelCase string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toLowerCamel(String input) {
        NullCheck.requireNonNull(input, "input must not be null");

        List<String> words = splitWords(input);
        if (words.isEmpty()) return EMPTY_STRING;

        StringBuilder sb = new StringBuilder();

        // first word: always lower-case
        sb.append(words.get(0).toLowerCase(Locale.ROOT));

        // remaining words
        for (int i = 1; i < words.size(); i++) {
            String w = words.get(i);
            sb.append(titleCaseWord(w));
        }

        return sb.toString();
    }

    /**
     * Converts the given string to <strong>snake_lower_case</strong>.
     *
     * <p>
     * Word boundaries are detected using spaces, underscores, hyphens,
     * and camel-case or Pascal-case transitions. All characters in the
     * resulting string are converted to lower case.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSnakeLowerCase("hello world");   // hello_world
     * StringUtils.toSnakeLowerCase("helloWorld");    // hello_world
     * StringUtils.toSnakeLowerCase("HelloWorld");    // hello_world
     * StringUtils.toSnakeLowerCase("Hello World");   // hello_world
     * StringUtils.toSnakeLowerCase("hello-world");   // hello_world
     *
     * StringUtils.toSnakeLowerCase("XMLFile");       // xml_file
     * StringUtils.toSnakeLowerCase("XML File");      // xml_file
     * StringUtils.toSnakeLowerCase("XML_File");      // xml_file
     * StringUtils.toSnakeLowerCase("XML-File");      // xml_file
     *
     * StringUtils.toSnakeLowerCase("STATIC_FINAL");  // static_final
     * }</pre>
     *
     * @param input the input string
     * @return the converted snake_lower_case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSnakeLowerCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");

        List<String> words = splitWords(input);

        if (words.isEmpty()) return "";

        return String.join("_", words).toLowerCase(Locale.ROOT);
    }

    /**
     * Converts the given string to <strong>SNAKE_UPPER_CASE</strong>.
     *
     * <p>
     * Words are detected using spaces, underscores, hyphens, and camel-case
     * or Pascal-case boundaries. The resulting words are joined using
     * underscores ({@code _}) and converted entirely to upper case.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSnakeUpperCase("hello world");   // HELLO_WORLD
     * StringUtils.toSnakeUpperCase("helloWorld");    // HELLO_WORLD
     * StringUtils.toSnakeUpperCase("HelloWorld");    // HELLO_WORLD
     * StringUtils.toSnakeUpperCase("Hello World");   // HELLO_WORLD
     * StringUtils.toSnakeUpperCase("hello-world");   // HELLO_WORLD
     *
     * StringUtils.toSnakeUpperCase("XMLFile");       // XML_FILE
     * StringUtils.toSnakeUpperCase("XML File");      // XML_FILE
     * StringUtils.toSnakeUpperCase("XML_File");      // XML_FILE
     * StringUtils.toSnakeUpperCase("XML-File");      // XML_FILE
     *
     * StringUtils.toSnakeUpperCase("STATIC_FINAL");  // STATIC_FINAL
     * }</pre>
     *
     * @param input the input string
     * @return the converted SNAKE_UPPER_CASE string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSnakeUpperCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");
        List<String> words = splitWords(input);

        if (words.isEmpty()) return "";

        return String.join("_", words).toUpperCase();
    }

    /**
     * Converts the given string to <strong>snake_Title_Upper_Case</strong>,
     * where words are separated by underscores ({@code _}) and each word
     * is converted to title case.
     *
     * <p>
     * Words are detected using spaces, underscores, hyphens, and camel-case
     * or Pascal-case boundaries. Acronyms written in all-uppercase
     * (e.g. {@code XML}, {@code HTTP}) are preserved as whole words and will
     * not be partially lower-cased.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSnakeTitleUpperCase("hello world");   // Hello_World
     * StringUtils.toSnakeTitleUpperCase("helloWorld");    // Hello_World
     * StringUtils.toSnakeTitleUpperCase("HelloWorld");    // Hello_World
     * StringUtils.toSnakeTitleUpperCase("Hello World");   // Hello_World
     * StringUtils.toSnakeTitleUpperCase("hello-world");   // Hello_World
     *
     * StringUtils.toSnakeTitleUpperCase("XMLFile");       // Xml_File
     * StringUtils.toSnakeTitleUpperCase("XML File");      // Xml_File
     * StringUtils.toSnakeTitleUpperCase("XML_File");      // Xml_File
     * StringUtils.toSnakeTitleUpperCase("XML-File");      // Xml_File
     *
     * StringUtils.toSnakeTitleUpperCase("STATIC_FINAL");  // Static_Final
     * }</pre>
     *
     * @param input the input string
     * @return the converted snake_Title_Upper_Case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSnakeTitleUpperCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");
        List<String> words = splitWords(input);
        if (words.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.isEmpty()) continue;
            if (!sb.isEmpty()) sb.append('_');
            sb.append(titleCaseWord(w));
        }
        return sb.toString();
    }

    /**
     * Converts the given string to <strong>space lower case</strong>,
     * where words are separated by single spaces and all characters
     * are converted to lower case.
     *
     * <p>
     * This method first converts the input string to
     * {@code snake_lower_case} using {@link #toSnakeLowerCase(String)},
     * then replaces underscores ({@code _}) with spaces.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSeparatorLowerCase("hello world", "=");   // hello=world
     * StringUtils.toSeparatorLowerCase("helloWorld", "?");    // hello?world
     * StringUtils.toSeparatorLowerCase("HelloWorld", "[");    // hello"[world
     * StringUtils.toSeparatorLowerCase("Hello World", "@");   // hello@world
     * StringUtils.toSeparatorLowerCase("hello-world", "$");   // hello$world
     *
     * StringUtils.toSeparatorLowerCase("XMLFile", "z");       // xmlzfile
     * StringUtils.toSeparatorLowerCase("XML File", "+");      // xml+file
     * StringUtils.toSeparatorLowerCase("XML_File", "^");      // xml^file
     * StringUtils.toSeparatorLowerCase("XML-File", " ");      // xml file
     *
     * StringUtils.toSeparatorLowerCase("STATIC_FINAL", "-");  // static-final
     * }</pre>
     *
     * @param input the input string
     * @param separator the separator to use between words
     * @return the converted space lower case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSeparatorLowerCase(String input, String separator) {
        NullCheck.requireNonNull(input, "input must not be null");

        String snakeLowerCase = toSnakeLowerCase(input);

        return snakeLowerCase.replaceAll(UNDERLINE, separator);
    }

    /**
     * Converts the given string to <strong>space upper case</strong>,
     * where words are separated by single spaces and all characters
     * are converted to upper case.
     *
     * <p>
     * This method first converts the input string to
     * {@code SNAKE_UPPER_CASE} using {@link #toSnakeUpperCase(String)},
     * then replaces underscores ({@code _}) with spaces.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSeparatorUpperCase("hello world", "=");   // HELLO=WORLD
     * StringUtils.toSeparatorUpperCase("helloWorld", "?");    // HELLO?WORLD
     * StringUtils.toSeparatorUpperCase("HelloWorld", "[");    // HELLO[WORLD
     * StringUtils.toSeparatorUpperCase("Hello World", "@");   // HELLO@WORLD
     * StringUtils.toSeparatorUpperCase("hello-world", "$");   // HELLO$WORLD
     *
     * StringUtils.toSeparatorUpperCase("XMLFile", "z");       // XMLzFILE
     * StringUtils.toSeparatorUpperCase("XML File", "+");      // XML+FILE
     * StringUtils.toSeparatorUpperCase("XML_File", "^");      // XML^FILE
     * StringUtils.toSeparatorUpperCase("XML-File", " ");      // XML FILE
     *
     * StringUtils.toSeparatorUpperCase("STATIC_FINAL", "-");  // STATIC-FINAL
     * }</pre>
     *
     * @param input the input string
     * @param separator the separator to use between words
     * @return the converted space upper case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSeparatorUpperCase(String input, String separator) {
        NullCheck.requireNonNull(input, "input must not be null");

        String snakeUpperCase = toSnakeUpperCase(input);

        return snakeUpperCase.replaceAll(UNDERLINE, separator);
    }

    /**
     * Converts the given string to <strong>space Title Upper Case</strong>,
     * where words are separated by single spaces and each word is converted
     * to title case.
     *
     * <p>
     * This method first converts the input string to
     * {@code snake_Title_Upper_Case} using
     * {@link #toSnakeTitleUpperCase(String)}, then replaces underscores
     * ({@code _}) with spaces.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSeparatorTitleUpperCase("hello world", "=");   // Hello=World
     * StringUtils.toSeparatorTitleUpperCase("helloWorld", "?");    // Hello?World
     * StringUtils.toSeparatorTitleUpperCase("HelloWorld", "[");    // Hello[World
     * StringUtils.toSeparatorTitleUpperCase("Hello World", "@");   // Hello@World
     * StringUtils.toSeparatorTitleUpperCase("hello-world", "$");   // Hello$World
     *
     * StringUtils.toSeparatorTitleUpperCase("XMLFile", "z");       // XmlzFile
     * StringUtils.toSeparatorTitleUpperCase("XML File", "+");      // Xml+File
     * StringUtils.toSeparatorTitleUpperCase("XML_File", "^");      // Xml^File
     * StringUtils.toSeparatorTitleUpperCase("XML-File", " ");      // Xml File
     *
     * StringUtils.toSeparatorTitleUpperCase("STATIC_FINAL", "-");  // Static-Final
     * }</pre>
     *
     * @param input the input string
     * @param separator the separator to use between words
     * @return the converted space Title Upper Case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSeparatorTitleUpperCase(String input, String separator) {
        NullCheck.requireNonNull(input, "input must not be null");

        String snakeUpperCase = toSnakeTitleUpperCase(input);

        return snakeUpperCase.replaceAll(UNDERLINE, separator);
    }


    /**
     * Converts the given string to <strong>space lower case</strong>,
     * where words are separated by single spaces and all characters
     * are converted to lower case.
     *
     * <p>
     * This method first converts the input string to
     * {@code snake_lower_case} using {@link #toSnakeLowerCase(String)},
     * then replaces underscores ({@code _}) with spaces.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSpaceLowerCase("hello world");   // hello world
     * StringUtils.toSpaceLowerCase("helloWorld");    // hello world
     * StringUtils.toSpaceLowerCase("HelloWorld");    // hello world
     * StringUtils.toSpaceLowerCase("Hello World");   // hello world
     * StringUtils.toSpaceLowerCase("hello-world");   // hello world
     *
     * StringUtils.toSpaceLowerCase("XMLFile");       // xml file
     * StringUtils.toSpaceLowerCase("XML File");      // xml file
     * StringUtils.toSpaceLowerCase("XML_File");      // xml file
     * StringUtils.toSpaceLowerCase("XML-File");      // xml file
     *
     * StringUtils.toSpaceLowerCase("STATIC_FINAL");      // static final
     * }</pre>
     *
     * @param input the input string
     *
     * @return the converted space lower case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSpaceLowerCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");
        return toSeparatorLowerCase(input, SPACE_STRING);
    }

    /**
     * Converts the given string to <strong>space upper case</strong>,
     * where words are separated by single spaces and all characters
     * are converted to upper case.
     *
     * <p>
     * This method first converts the input string to
     * {@code SNAKE_UPPER_CASE} using {@link #toSnakeUpperCase(String)},
     * then replaces underscores ({@code _}) with spaces.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSpaceUpperCase("hello world");   // HELLO WORLD
     * StringUtils.toSpaceUpperCase("helloWorld");    // HELLO WORLD
     * StringUtils.toSpaceUpperCase("HelloWorld");    // HELLO WORLD
     * StringUtils.toSpaceUpperCase("Hello World");   // HELLO WORLD
     * StringUtils.toSpaceUpperCase("hello-world");   // HELLO WORLD
     *
     * StringUtils.toSpaceUpperCase("XMLFile");       // XML FILE
     * StringUtils.toSpaceUpperCase("XML File");      // XML FILE
     * StringUtils.toSpaceUpperCase("XML_File");      // XML FILE
     * StringUtils.toSpaceUpperCase("XML-File");      // XML FILE
     *
     * StringUtils.toSpaceUpperCase("STATIC_FINAL");  // STATICFINAL
     * }</pre>
     *
     * @param input the input string
     * @return the converted space upper case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSpaceUpperCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");

        return toSeparatorUpperCase(input, SPACE_STRING);
    }

    /**
     * Converts the given string to <strong>space Title Upper Case</strong>,
     * where words are separated by single spaces and each word is converted
     * to title case.
     *
     * <p>
     * This method first converts the input string to
     * {@code snake_Title_Upper_Case} using
     * {@link #toSnakeTitleUpperCase(String)}, then replaces underscores
     * ({@code _}) with spaces.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toSpaceTitleUpperCase("hello world");   // Hello World
     * StringUtils.toSpaceTitleUpperCase("helloWorld");    // Hello World
     * StringUtils.toSpaceTitleUpperCase("HelloWorld");    // Hello World
     * StringUtils.toSpaceTitleUpperCase("Hello World");   // Hello World
     * StringUtils.toSpaceTitleUpperCase("hello-world");   // Hello World
     *
     * StringUtils.toSpaceTitleUpperCase("XMLFile");       // Xml File
     * StringUtils.toSpaceTitleUpperCase("XML File");      // Xml File
     * StringUtils.toSpaceTitleUpperCase("XML_File");      // Xml File
     * StringUtils.toSpaceTitleUpperCase("XML-File");      // Xml File
     *
     * StringUtils.toSpaceTitleUpperCase("STATIC_FINAL");      // Static Final
     * }</pre>
     *
     * @param input the input string
     * @return the converted space Title Upper Case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toSpaceTitleUpperCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");

        return toSeparatorTitleUpperCase(input, SPACE_STRING);
    }

    /**
     * Converts the given string to <strong>hyphen lower case</strong>
     * (also known as kebab-case), where words are separated by hyphens
     * ({@code -}) and all characters are converted to lower case.
     *
     * <p>
     * This method first converts the input string to
     * {@code snake_lower_case} using {@link #toSnakeLowerCase(String)},
     * then replaces underscores ({@code _}) with hyphens.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toHyphenLowerCase("hello world");   // hello-world
     * StringUtils.toHyphenLowerCase("helloWorld");    // hello-world
     * StringUtils.toHyphenLowerCase("HelloWorld");    // hello-world
     * StringUtils.toHyphenLowerCase("Hello World");   // hello-world
     * StringUtils.toHyphenLowerCase("hello_world");   // hello-world
     *
     * StringUtils.toHyphenLowerCase("XMLFile");       // xml-file
     * StringUtils.toHyphenLowerCase("XML File");      // xml-file
     * StringUtils.toHyphenLowerCase("XML_File");      // xml-file
     * StringUtils.toHyphenLowerCase("XML-File");      // xml-file
     *
     * StringUtils.toHyphenLowerCase("STATIC_FINAL");  // static-final
     * }</pre>
     *
     * @param input the input string
     * @return the converted hyphen lower case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toHyphenLowerCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");

        return toSeparatorLowerCase(input, HYPHEN);
    }

    /**
     * Converts the given string to <strong>hyphen upper case</strong>,
     * where words are separated by hyphens ({@code -}) and all characters
     * are converted to upper case.
     *
     * <p>
     * This method first converts the input string to
     * {@code SNAKE_UPPER_CASE} using {@link #toSnakeUpperCase(String)},
     * then replaces underscores ({@code _}) with hyphens.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toHyphenUpperCase("hello world");   // HELLO-WORLD
     * StringUtils.toHyphenUpperCase("helloWorld");    // HELLO-WORLD
     * StringUtils.toHyphenUpperCase("HelloWorld");    // HELLO-WORLD
     * StringUtils.toHyphenUpperCase("Hello World");   // HELLO-WORLD
     * StringUtils.toHyphenUpperCase("hello_world");   // HELLO-WORLD
     *
     * StringUtils.toHyphenUpperCase("XMLFile");       // XML-FILE
     * StringUtils.toHyphenUpperCase("XML File");      // XML-FILE
     * StringUtils.toHyphenUpperCase("XML_File");      // XML-FILE
     * StringUtils.toHyphenUpperCase("XML-File");      // XML-FILE
     *
     * StringUtils.toHyphenUpperCase("STATIC_FINAL");  // STATIC-FINAL
     * }</pre>
     *
     * @param input the input string
     * @return the converted hyphen upper case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toHyphenUpperCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");

        return toSeparatorUpperCase(input, HYPHEN);
    }


    /**
     * Converts the given string to <strong>hyphen Title Upper Case</strong>,
     * where words are separated by hyphens ({@code -}) and each word is
     * converted to title case.
     *
     * <p>
     * This method first converts the input string to
     * {@code snake_Title_Upper_Case} using
     * {@link #toSnakeTitleUpperCase(String)}, then replaces underscores
     * ({@code _}) with hyphens.
     * </p>
     *
     * <pre>{@code
     * StringUtils.toHyphenTitleUpperCase("hello world");   // Hello-World
     * StringUtils.toHyphenTitleUpperCase("helloWorld");    // Hello-World
     * StringUtils.toHyphenTitleUpperCase("HelloWorld");    // Hello-World
     * StringUtils.toHyphenTitleUpperCase("Hello World");   // Hello-World
     * StringUtils.toHyphenTitleUpperCase("hello_world");   // Hello-World
     *
     * StringUtils.toHyphenTitleUpperCase("XMLFile");       // Xml-File
     * StringUtils.toHyphenTitleUpperCase("XML File");      // Xml-File
     * StringUtils.toHyphenTitleUpperCase("XML_File");      // Xml-File
     * StringUtils.toHyphenTitleUpperCase("XML-File");      // Xml-File
     *
     * StringUtils.toHyphenTitleUpperCase("STATIC_FINAL");      // Static-Final
     * }</pre>
     *
     * @param input the input string
     * @return the converted hyphen Title Upper Case string
     * @throws NullPointerException if {@code input} is null
     */
    public static @NotNull String toHyphenTitleUpperCase(String input) {
        NullCheck.requireNonNull(input, "input must not be null");

        return toSeparatorTitleUpperCase(input, HYPHEN);
    }

    /**
     * Splits an identifier-like string into word tokens.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>{@code ' '}, {@code '_'}, {@code '-'} are treated as hard separators</li>
     *   <li>{@code lower/digit -> Upper} starts a new word (e.g. {@code helloWorld})</li>
     *   <li>Acronym boundary: {@code XMLFile -> XML + File}, {@code HTTPRequest -> HTTP + Request}</li>
     *   <li>Consecutive separators do not produce empty tokens</li>
     * </ul>
     */
    private static @NotNull List<String> splitWords(@NotNull String input) {
        String s = input.trim();
        if (s.isEmpty()) return List.of();

        List<String> words = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // hard separators
            if (c == ' ' || c == '_' || c == '-') {
                flush(words, current);
                continue;
            }

            if (current.isEmpty()) {
                current.append(c);
                continue;
            }

            char prev = current.charAt(current.length() - 1);

            boolean prevLowerOrDigit = Character.isLowerCase(prev) || Character.isDigit(prev);
            boolean currUpper = Character.isUpperCase(c);
            boolean prevUpper = Character.isUpperCase(prev);
            boolean currLower = Character.isLowerCase(c);

            // Case 1: helloWorld / v2World
            if (prevLowerOrDigit && currUpper) {
                flush(words, current);
                current.append(c);
                continue;
            }

            // Case 2: XMLFile / HTTPRequest
            if (prevUpper && currLower) {
                if (current.length() >= 2) {
                    char beforePrev = current.charAt(current.length() - 2);
                    if (Character.isUpperCase(beforePrev)) {
                        char lastUpper = current.charAt(current.length() - 1);
                        current.deleteCharAt(current.length() - 1);
                        flush(words, current);
                        current.append(lastUpper);
                    }
                }
                current.append(c);
                continue;
            }

            current.append(c);
        }

        flush(words, current);
        return words;
    }

    private static void flush(List<String> words, @NotNull StringBuilder current) {
        if (current.isEmpty()) return;
        words.add(current.toString());
        current.setLength(0);
    }
}

