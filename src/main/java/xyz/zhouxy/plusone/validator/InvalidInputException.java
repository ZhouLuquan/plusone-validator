package xyz.zhouxy.plusone.validator;

import xyz.zhouxy.plusone.commons.exception.BaseException;

/**
 * 4040200 - 无效的用户输入
 *
 * @author <a href="https://gitee.com/zhouxy108">ZhouXY</a>
 */
public class InvalidInputException extends BaseException {

    private static final long serialVersionUID = 7956661913360059670L;

    public static final String ERROR_CODE = "4040200";

    protected InvalidInputException(String code, String msg) {
        super(code, msg);
    }

    protected InvalidInputException(String code, Throwable cause) {
        super(code, cause);
    }

    protected InvalidInputException(String code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public static InvalidInputException of(String msg) {
        return new InvalidInputException(ERROR_CODE, msg);
    }

    public static InvalidInputException of(Throwable cause) {
        return new InvalidInputException(ERROR_CODE, cause);
    }

    public static InvalidInputException of(String msg, Throwable cause) {
        return new InvalidInputException(ERROR_CODE, msg, cause);
    }
}
