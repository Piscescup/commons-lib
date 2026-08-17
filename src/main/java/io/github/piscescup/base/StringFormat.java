package io.github.piscescup.base;

import com.google.common.base.Ascii;
import org.jspecify.annotations.NonNull;

import java.util.Locale;

/**
 * @author REN YuanTong
 * @since 1.2.1
 */
public enum StringFormat {
    /**
     * The lower camel case format, used to Java variable naming convention, e.g. "lowerCamelCase".
     */
    LOWER_CAMEL("") {
        @Override
        String normalizeWord(String word) {
            return firstCharToUpper(word);
        }

        @Override
        String normalizeFirstWord(String word) {
            return word.toLowerCase(Locale.ROOT);
        }
    },

    /**
     * The upper camel case format, used to Java class or C++ class naming convention, e.g. "UpperCamelCase".
     */
    UPPER_CAMEL("") {
        @Override
        String normalizeWord(String word) {
            return firstCharToUpper(word);
        }

    },

    /**
     * The lower underscore format, used to C++ variable naming convention, e.g. "lower_underscore".
     */
    LOWER_UNDERSCORE("_") {
        @Override
        String normalizeWord(String word) {
            return word.toLowerCase(Locale.ROOT);
        }
    },

    /**
     * The upper underscore format, used to C++ constant naming convention, e.g. "UPPER_UNDERSCORE".
     */
    UPPER_UNDERSCORE("_") {
        @Override
        String normalizeWord(String word) {
            return word.toUpperCase(Locale.ROOT);
        }

    },

    /**
     * The hyphenated variable naming convention in lower case, e.g., "lower-hyphen".
     */
    LOWER_HYPHEN("-") {
        @Override
        String normalizeWord(String word) {
            return word.toLowerCase(Locale.ROOT);
        }
    },

    /**
     * The hyphenated variable naming convention in upper case, e.g., "UPPER-HYPHEN".
     */
    UPPER_HYPHEN("-") {
        @Override
        String normalizeWord(String word) {
            return word.toUpperCase(Locale.ROOT);
        }

    },

    /**
     * The space-separated variable naming convention in lower case, e.g., "lower space".
     */
    LOWER_SPACE(" ") {
        @Override
        String normalizeWord(String word) {
            return word.toLowerCase(Locale.ROOT);
        }

    },

    /**
     * The space-separated variable naming convention in upper case, e.g., "UPPER SPACE".
     */
    UPPER_SPACE(" ") {
        @Override
        String normalizeWord(String word) {
            return word.toUpperCase(Locale.ROOT);
        }

        // Handle formats except camel case formats
        @Override
        String convert(StringFormat format, String word) {
            if (isCamelCase(format)) return super.convert(format, word);

            if (isAllUpperCaseFormat(format)) {
                return word.toUpperCase(Locale.ROOT)
                    .replace(this.getWordSeparator(), format.getWordSeparator());
            }

            if (isAllLowerCaseFormat(format)) {
                return word.toLowerCase(Locale.ROOT)
                    .replace(this.getWordSeparator(), format.getWordSeparator());
            }

            if (isTitleCaseFormat(format)) {

            }
        }
    },

    /**
     * The title lower case format in underscore-separated form, e.g. "Title_Case".
     */
    TITLE_UNDERSCORE("_") {
        @Override
        String normalizeWord(String word) {
            return firstCharToUpper(word);
        }
    },

    /**
     * The title lower case format in hyphen-separated form, e.g. "Title-Case".
     */
    TITLE_HYPHEN("-") {
        @Override
        String normalizeWord(String word) {
            return firstCharToUpper(word);
        }
    },

    /**
     * The title lower case format in space-separated form, e.g. "Title Case".
     */
    TITLE_SPACE(" ") {
        @Override
        String normalizeWord(String word) {
            return firstCharToUpper(word);
        }
    };

    private static @NonNull String firstCharToUpper(String word) {
        return word.isEmpty()
            ? word
            : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase(Locale.ROOT);
    }

    private final String wordSeparator;

    StringFormat(String wordSeparator) {
        this.wordSeparator = wordSeparator;
    }

    public String getWordSeparator() {
        return wordSeparator;
    }

    String convert(StringFormat format, String word) {

        String[] words = separateWords(word);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (i == 0 && format == LOWER_CAMEL) {
                result.append(w.toLowerCase(Locale.ROOT));
            } else {
                result.append(Character.toUpperCase(w.charAt(0)))
                    .append(w.substring(1)
                        .toLowerCase(Locale.ROOT)
                    );
            }
        }

        return result.toString();
    }

    abstract String normalizeWord(String word);

    String normalizeFirstWord(String word) {
        return normalizeWord(word);
    }

    public String[] separateWords(String word) {
        return word.split(wordSeparator);
    }

    public String convertString(StringFormat targetFormat, String word) {
        return convert(this, word);
    }

    public static boolean isCamelCase(StringFormat format) {
        return format == LOWER_CAMEL || format == UPPER_CAMEL;
    }

    public static boolean isAllUpperCaseFormat(StringFormat format) {
        return format == UPPER_UNDERSCORE || format == UPPER_HYPHEN || format == UPPER_SPACE;
    }

    public static boolean isAllLowerCaseFormat(StringFormat format) {
        return format == LOWER_UNDERSCORE || format == LOWER_HYPHEN || format == LOWER_SPACE;
    }

    public static boolean isTitleCaseFormat(StringFormat format) {
        return format == TITLE_UNDERSCORE || format == TITLE_HYPHEN || format == TITLE_SPACE;
    }

}
