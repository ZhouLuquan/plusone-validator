/*
 * Copyright 2025 the original author or authors.
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
 * 验证失败的异常
 *
 * @author ZhouXY
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
     * 创建一个验证失败异常
     *
     * @return 异常
     */
    public static ValidationException withDefaultMessage() {
        return new ValidationException(DEFAULT_MESSAGE);
    }

    /**
     * 创建一个验证失败异常
     *
     * @param message 错误信息
     * @return 异常
     */
    public static ValidationException withMessage(String message) {
        return new ValidationException(message);
    }

    /**
     * 创建一个验证失败异常
     *
     * @param errorMessageTemplate 异常信息模版
     * @param errorMessageArgs 异常信息参数
     * @return 异常
     */
    public static ValidationException withMessage(String errorMessageTemplate, Object... errorMessageArgs) {
        return new ValidationException(String.format(errorMessageTemplate, errorMessageArgs));
    }

    /**
     * 创建一个验证失败异常
     *
     * @param cause 错误 cause
     * @return 异常
     */
    public static ValidationException withCause(Throwable cause) {
        return new ValidationException(cause);
    }

    /**
     * 创建一个验证失败异常
     *
     * @param message  错误信息
     * @param cause 错误 cause
     * @return 异常
     */
    public static ValidationException withMessageAndCause(String message, Throwable cause) {
        return new ValidationException(message, cause);
    }
}
