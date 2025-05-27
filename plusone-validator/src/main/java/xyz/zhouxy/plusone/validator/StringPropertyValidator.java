/*
 * Copyright 2023-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.zhouxy.plusone.validator;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.util.AssertTools;
import xyz.zhouxy.plusone.commons.util.RegexTools;
import xyz.zhouxy.plusone.commons.util.StringTools;

/**
 * StringPropertyValidator
 *
 * <p>
 * 针对文本字段的验证器。
 * </p>
 *
 * @author <a href="http://zhouxy.xyz:3000/ZhouXY108">ZhouXY</a>
 */
public class StringPropertyValidator<T> extends BaseComparablePropertyValidator<T, String, StringPropertyValidator<T>> {

    StringPropertyValidator(Function<T, String> getter) {
        super(getter);
    }

    // ================================
    // #region - matches
    // ================================

    public StringPropertyValidator<T> matches(Pattern regex, String errMsg) {
        return matches(regex, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matches(
            Pattern regex,
            Supplier<E> exceptionCreator) {
        return matches(regex, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matches(
            Pattern regex,
            Function<String, E> exceptionCreator) {
        withRule(input -> (input == null || RegexTools.matches(input, regex)), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - matches
    // ================================

    // ================================
    // #region - matchesOne
    // ================================

    public StringPropertyValidator<T> matchesOne(Pattern[] regexs, String errMsg) {
        return matchesOne(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            Pattern[] regexs,
            Supplier<E> exceptionCreator) {
        return matchesOne(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            Pattern[] regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexTools.matchesOne(input, regexs), exceptionCreator);
        return this;
    }

    public StringPropertyValidator<T> matchesOne(List<Pattern> regexs, String errMsg) {
        return matchesOne(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            List<Pattern> regexs,
            Supplier<E> exceptionCreator) {
        return matchesOne(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesOne(
            List<Pattern> regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> input == null || RegexTools.matchesOne(input, regexs.toArray(new Pattern[regexs.size()])), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - matchesOne
    // ================================

    // ================================
    // #region - matchesAll
    // ================================

    public StringPropertyValidator<T> matchesAll(Pattern[] regexs, String errMsg) {
        return matchesAll(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Pattern[] regexs,
            Supplier<E> exceptionCreator) {
        return matchesAll(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Pattern[] regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> RegexTools.matchesAll(input, regexs), exceptionCreator);
        return this;
    }

    public StringPropertyValidator<T> matchesAll(Collection<Pattern> regexs, String errMsg) {
        return matchesAll(regexs, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Collection<Pattern> regexs,
            Supplier<E> exceptionCreator) {
        return matchesAll(regexs, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> matchesAll(
            Collection<Pattern> regexs,
            Function<String, E> exceptionCreator) {
        withRule(input -> input == null || RegexTools.matchesAll(input, regexs.toArray(new Pattern[regexs.size()])), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - matchesAll
    // ================================

    // ================================
    // #region - notBlank
    // ================================

    public StringPropertyValidator<T> notBlank() {
        return notBlank("The value must have text; it must not be null, empty, or blank.");
    }

    public StringPropertyValidator<T> notBlank(String errMsg) {
        return notBlank(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> notBlank(Supplier<E> exceptionCreator) {
        return notBlank(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> notBlank(
            Function<String, E> exceptionCreator) {
        withRule(StringTools::isNotBlank, exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - notBlank
    // ================================

    // ================================
    // #region - emailAddress
    // ================================

    public StringPropertyValidator<T> emailAddress() {
        return emailAddress("The value is not an email address.");
    }

    public StringPropertyValidator<T> emailAddress(String errMsg) {
        return emailAddress(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> emailAddress(
            Supplier<E> exceptionCreator) {
        return emailAddress(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> emailAddress(
            Function<String, E> exceptionCreator) {
        return matches(PatternConsts.EMAIL, exceptionCreator);
    }

    // ================================
    // #endregion - emailAddress
    // ================================

    // ================================
    // #region - notEmpty
    // ================================

    public StringPropertyValidator<T> notEmpty() {
        return notEmpty("The value must not be empty.");
    }

    public StringPropertyValidator<T> notEmpty(String errMsg) {
        return notEmpty(convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> notEmpty(Supplier<E> exceptionCreator) {
        return notEmpty(convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> notEmpty(
            Function<String, E> exceptionCreator) {
        withRule(s -> s != null && !s.isEmpty(), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - notEmpty
    // ================================

    // ================================
    // #region - length
    // ================================

    public StringPropertyValidator<T> length(int length, String errMsg) {
        return length(length, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> length(int length,
            Supplier<E> exceptionCreator) {
        return length(length, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> length(int length,
            Function<String, E> exceptionCreator) {
        AssertTools.checkArgument(length >= 0,
                "The required length must be greater than or equal to 0.");
        withRule(s -> s == null || s.length() == length, exceptionCreator);
        return this;
    }

    static boolean length(String str, int min, int max) {
        if (str == null) {
            return true;
        }
        final int len = str.length();
        return len >= min && len <= max;
    }

    public StringPropertyValidator<T> length(int min, int max, String errMsg) {
        return length(min, max, convertExceptionCreator(errMsg));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> length(int min, int max,
            Supplier<E> exceptionCreator) {
        return length(min, max, convertExceptionCreator(exceptionCreator));
    }

    public <E extends RuntimeException> StringPropertyValidator<T> length(int min, int max,
            Function<String, E> exceptionCreator) {
        AssertTools.checkArgument(min >= 0, "The minimum value must be greater than equal to 0.");
        AssertTools.checkArgument(min < max, "The minimum value must be less than the maximum value.");
        withRule(s -> length(s, min, max), exceptionCreator);
        return this;
    }

    // ================================
    // #endregion - length
    // ================================

    @Override
    protected StringPropertyValidator<T> thisObject() {
        return this;
    }
}
