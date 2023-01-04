package xyz.zhouxy.plusone.validator;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import cn.hutool.core.util.StrUtil;
import xyz.zhouxy.plusone.constant.RegexConsts;
import xyz.zhouxy.plusone.util.RegexUtil;

class StringValidator<DTO> extends PropertyValidator<DTO, String, StringValidator<DTO>> {

    StringValidator(Function<DTO, String> getter) {
        super(getter);
    }

    // ====================
    // ====== String ======
    // ====================

    // ===== matches =====

    public StringValidator<DTO> matches(String regex, String errMsg) {
        return matches(regex, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matches(
            String regex,
            Supplier<E> exceptionCreator) {
        return matches(regex, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matches(
            String regex,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexUtil.matches(input, regex), exceptionCreator);
        return this;
    }

    // ===== matchesOr =====

    public StringValidator<DTO> matchesOr(String[] regexs, String errMsg) {
        return matchesOr(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOr(
            String[] regexs,
            Supplier<E> exceptionCreator) {
        return matchesOr(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOr(
            String[] regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexUtil.matchesOr(input, regexs), exceptionCreator);
        return this;
    }

    public StringValidator<DTO> matchesOr(List<String> regexs, String errMsg) {
        return matchesOr(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOr(
            List<String> regexs,
            Supplier<E> exceptionCreator) {
        return matchesOr(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesOr(
            List<String> regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexUtil.matchesOr(input, regexs.toArray(new String[regexs.size()])), exceptionCreator);
        return this;
    }

    // ===== matchesAnd =====

    public StringValidator<DTO> matchesAnd(String[] regexs, String errMsg) {
        return matchesAnd(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesAnd(
            String[] regexs,
            Supplier<E> exceptionCreator) {
        return matchesAnd(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringValidator<DTO> matchesAnd(
            String[] regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexUtil.matchesAnd(input, regexs), exceptionCreator);
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
        withRule(input -> StrUtil.isNotBlank(input), exceptionCreator);
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
        return matches(RegexConsts.EMAIL, exceptionCreator);
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
        withRule(value -> {
            if (value == null) {
                return false;
            }
            return !(value.isEmpty());
        }, exceptionCreator);
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
        withRule(value -> {
            if (value == null) {
                return false;
            }
            return value.isEmpty();
        }, exceptionCreator);
        return this;
    }

    @Override
    protected StringValidator<DTO> returnThis() {
        return this;
    }
}
