package xyz.zhouxy.plusone.validator;

/**
 * 自带校验方法，校验不通过时直接抛异常。
 *
 * @author <a href="https://gitee.com/zhouxy108">ZhouXY</a>
 *
 * @see ValidateUtil
 * @see BaseValidator
 */
public interface IValidateRequired {
    void validate();
}
