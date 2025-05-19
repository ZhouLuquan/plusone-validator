package xyz.zhouxy.plusone.map.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.util.StringTools;
import xyz.zhouxy.plusone.validator.MapValidator;

class MapValidatorTests {

    private static final MapValidator<String, Object> validator = ParamsValidator.INSTANCE;

    @Test
    void testValidateAndCopy() {
        Map<String, Object> params = new HashMap<>();
        params.put(ParamsValidator.USERNAME, "ZhouXY");
        params.put(ParamsValidator.ACCOUNT, "zhouxy@code108.cn");
        params.put(ParamsValidator.PASSWORD, "99Code108");
        params.put(ParamsValidator.PASSWORD2, "99Code108");
        params.put(ParamsValidator.AGE, 18);
        params.put(ParamsValidator.BOOLEAN, true);

        params.put(ParamsValidator.ROLE_LIST, Arrays.asList("admin", ""));
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validateAndCopy(params);
        });

        params.put(ParamsValidator.ROLE_LIST, Arrays.asList("admin", "developer"));
        Map<String, Object> validatedParams = validator.validateAndCopy(params);
        System.out.println(validatedParams);
    }
}

class ParamsValidator extends MapValidator<String, Object> {
    public static final String USERNAME = "username";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String PASSWORD2 = "password2";
    public static final String AGE = "age";
    public static final String BOOLEAN = "boolean";
    public static final String ROLE_LIST = "roleList";

    public static final ParamsValidator INSTANCE = new ParamsValidator();

    private ParamsValidator() {
        super(new String[] { USERNAME, ACCOUNT, PASSWORD, AGE, BOOLEAN, ROLE_LIST });
        ruleForString(USERNAME)
                .notBlank("用户名不能为空")
                .matches(PatternConsts.USERNAME,
                        username -> new IllegalArgumentException(String.format("用户名【%s】不符合规范", username)));

        ruleForString(ACCOUNT)
                .notBlank("账号不能为空")
                .matchesOne(new Pattern[] { PatternConsts.EMAIL, PatternConsts.MOBILE_PHONE }, "请输入正确的邮箱地址或手机号");

        ruleForString(PASSWORD)
                .notEmpty("密码不能为空")
                .matches(PatternConsts.PASSWORD, "密码不符合规范");

        // 校验到多个属性，只能针对 map 本身进行校验
        withRule(m -> Objects.equals(m.get(PASSWORD), m.get(PASSWORD2)),
                "两次输入的密码不一样！");

        ruleForInt(AGE)
                .withRule(Objects::nonNull)
                .ge(18)
                .le(60);

        ruleForBool(BOOLEAN)
                .notNull("Boolean property could not be null.")
                .isTrueValue("Boolean property must be true.");

        this.<String>ruleForCollection(ROLE_LIST)
                .notEmpty("角色列表不能为空！")
                .withRule(l -> l.stream().allMatch(StringTools::isNotBlank),
                        () -> new IllegalArgumentException("角色标识不能为空！"));
    }
}
