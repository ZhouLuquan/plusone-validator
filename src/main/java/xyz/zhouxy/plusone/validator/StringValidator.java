package xyz.zhouxy.plusone.validator;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.util.RegexTools;

public class StringValidator<DTO> extends BasePropertyValidator<DTO, String, StringValidator<DTO>> {

    StringValidator(Function<DTO, String> getter) {
        super(getter);
    }

    // ====================
    // ====== String ======
    // ====================

    // ===== matches =====

    public StringValidator<DTO> matches(Pattern regex, String errMsg) {
        return matches(regex, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matches(
            Pattern regex,
            Supplier<E> exceptionCreator) {
        return matches(regex, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matches(
            Pattern regex,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexTools.matches(input, regex), exceptionCreator);
        return this;
    }

    // ===== matchesOne =====

    public StringValidator<DTO> matchesOne(Pattern[] regexs, String errMsg) {
        return matchesOne(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOne(
            Pattern[] regexs,
            Supplier<E> exceptionCreator) {
        return matchesOne(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOne(
            Pattern[] regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexTools.matchesOne(input, regexs), exceptionCreator);
        return this;
    }

    public StringValidator<DTO> matchesOne(List<Pattern> regexs, String errMsg) {
        return matchesOne(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOne(
            List<Pattern> regexs,
            Supplier<E> exceptionCreator) {
        return matchesOne(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOne(
            List<Pattern> regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexTools.matchesOne(input, regexs.toArray(new Pattern[regexs.size()])), exceptionCreator);
        return this;
    }

    // ===== matchesAll =====

    public StringValidator<DTO> matchesAll(Pattern[] regexs, String errMsg) {
        return matchesAll(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesAll(
            Pattern[] regexs,
            Supplier<E> exceptionCreator) {
        return matchesAll(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesAll(
            Pattern[] regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexTools.matchesAll(input, regexs), exceptionCreator);
        return this;
    }

    public StringValidator<DTO> matchesAll(Collection<Pattern> regexs, String errMsg) {
        return matchesAll(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesAll(
            Collection<Pattern> regexs,
            Supplier<E> exceptionCreator) {
        return matchesAll(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesAll(
            Collection<Pattern> regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexTools.matchesAll(input, regexs.toArray(new Pattern[regexs.size()])), exceptionCreator);
        return this;
    }

    // ===== notBlank =====

    static boolean isNotBlank(final String cs) {
        if (cs == null || cs.isEmpty()) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public StringValidator<DTO> notBlank() {
        return notBlank("This String argument must have text; it must not be null, empty, or blank");
    }

    public StringValidator<DTO> notBlank(String errMsg) {
        return notBlank(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> notBlank(Supplier<E> exceptionCreator) {
        return notBlank(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> notBlank(
            Function<String, E> exceptionCreator) {
        withRule(StringValidator::isNotBlank, exceptionCreator);
        return this;
    }

    // ===== email =====

    public StringValidator<DTO> email() {
        return email("The value is not an email address.");
    }

    public StringValidator<DTO> email(String errMsg) {
        return email(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> email(Supplier<E> exceptionCreator) {
        return email(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> email(Function<String, E> exceptionCreator) {
        return matches(PatternConsts.EMAIL, exceptionCreator);
    }

    // ====== notEmpty =====

    public StringValidator<DTO> notEmpty(String errMsg) {
        return notEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> notEmpty(Supplier<E> exceptionCreator) {
        return notEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> notEmpty(
            Function<String, E> exceptionCreator) {
        withRule(s -> s != null && !s.isEmpty(), exceptionCreator);
        return this;
    }

    // ====== isNullOrEmpty =====

    public StringValidator<DTO> isNullOrEmpty(String errMsg) {
        return isNullOrEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> isNullOrEmpty(Supplier<E> exceptionCreator) {
        return isNullOrEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> isNullOrEmpty(
            Function<String, E> exceptionCreator) {
        withRule(s -> s == null || s.isEmpty(), exceptionCreator);
        return this;
    }

    // ====== length =====

    public StringValidator<DTO> length(int length, String errMsg) {
        return length(length, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> length(int length,
            Supplier<E> exceptionCreator) {
        return length(length, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> length(int length,
            Function<String, E> exceptionCreator) {
        Preconditions.checkArgument(length >= 0, "The minimum value must be less than the maximum value.");
        withRule(s -> s != null && s.length() == length, exceptionCreator);
        return this;
    }

    static boolean length(String str, int min, int max) {
        if (str == null) {
            return false;
        }
        final int len = str.length();
        return len >= min && len < max;
    }

    public StringValidator<DTO> length(int min, int max, String errMsg) {
        return length(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> length(int min, int max,
            Supplier<E> exceptionCreator) {
        return length(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> length(int min, int max,
            Function<String, E> exceptionCreator) {
        Preconditions.checkArgument(min >= 0, "The minimum value must be greater than equal to 0.");
        Preconditions.checkArgument(min < max, "The minimum value must be less than the maximum value.");
        withRule(s -> length(s, min, max), exceptionCreator);
        return this;
    }

    @Override
    protected StringValidator<DTO> thisObject() {
        return this;
    }
}
