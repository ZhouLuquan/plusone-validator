/*
 * Copyright 2025-present ZhouXY
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

/**
 * 校验失败异常
 *
 * @author ZhouXY108 <luquanlion@outlook.com>
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -49385010625414401L;

    public static final String DEFAULT_MESSAGE = "Validation failed.";

    private ValidationException(String message) {
        super(message);
    }

    private ValidationException(Throwable cause) {
        super(cause);
    }

    private ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 创建 {@code ValidationException} 实例
     *
     * @return {@code ValidationException} 实例
     */
    public static ValidationException withDefaultMessage() {
        return new ValidationException(DEFAULT_MESSAGE);
    }

    /**
     * 创建 {@code ValidationException} 实例
     *
     * @param message 异常信息
     * @return {@code ValidationException} 实例
     */
    public static ValidationException withMessage(String message) {
        return new ValidationException(message);
    }

    /**
     * 创建 {@code ValidationException} 实例
     *
     * @param errorMessageTemplate 异常信息模板
     * @param errorMessageArgs 异常信息参数列表
     * @return {@code ValidationException} 实例
     */
    public static ValidationException withMessage(String errorMessageTemplate, Object... errorMessageArgs) {
        return new ValidationException(String.format(errorMessageTemplate, errorMessageArgs));
    }

    /**
     * 创建 {@code ValidationException} 实例
     *
     * @param cause 导致校验失败的根本异常
     * @return {@code ValidationException} 实例
     */
    public static ValidationException withCause(Throwable cause) {
        return new ValidationException(cause);
    }

    /**
     * 创建 {@code ValidationException} 实例
     *
     * @param message 异常信息
     * @param cause 导致校验失败的根本异常
     * @return {@code ValidationException} 实例
     */
    public static ValidationException withMessageAndCause(String message, Throwable cause) {
        return new ValidationException(message, cause);
    }
}
