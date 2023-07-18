package xyz.zhouxy.plusone.validator;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.util.RegexUtil;

public class StringValidator<DTO> extends PropertyValidator<DTO, String, StringValidator<DTO>> {

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
        withRule(input -> RegexUtil.matches(input, regex), exceptionCreator);
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
        withRule(input -> RegexUtil.matchesOne(input, regexs), exceptionCreator);
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
        withRule(input -> RegexUtil.matchesOne(input, regexs.toArray(new Pattern[regexs.size()])), exceptionCreator);
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
        withRule(input -> RegexUtil.matchesAll(input, regexs), exceptionCreator);
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
        withRule(input -> RegexUtil.matchesAll(input, regexs.toArray(new Pattern[regexs.size()])), exceptionCreator);
        return this;
    }

    // ===== notBlank =====

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
        withRule(StringUtils::isNotBlank, exceptionCreator);
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
        withRule(StringUtils::isNotEmpty, exceptionCreator);
        return this;
    }

    // ====== isEmpty =====

    public StringValidator<DTO> isEmpty(String errMsg) {
        return isEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> isEmpty(Supplier<E> exceptionCreator) {
        return isEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> isEmpty(
            Function<String, E> exceptionCreator) {
        withRule(StringUtils::isEmpty, exceptionCreator);
        return this;
    }

    @Override
    protected StringValidator<DTO> thisObject() {
        return this;
    }
}
