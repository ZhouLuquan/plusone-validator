package xyz.zhouxy.plusone.validator.map.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.commons.collection.CollectionTools;
import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.function.PredicateTools;
import xyz.zhouxy.plusone.commons.util.RegexTools;
import xyz.zhouxy.plusone.validator.map.MapValidator;

public //
class MapValidatorTests {

    private static final String USERNAME = "username";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String PASSWORD2 = "password2";
    private static final String AGE = "age";
    private static final String BOOLEAN = "boolean";
    private static final String ROLE_LIST = "roleList";

    private static final MapValidator<String, Object> validator = new MapValidator<String, Object>(
            new String[] { USERNAME, ACCOUNT, PASSWORD, AGE, BOOLEAN, ROLE_LIST }) {
        {

            checkValue(USERNAME, String.class).withRule(
                    PredicateTools.from(StringUtils::isNotBlank)
                            .and(username -> RegexTools.matches(username, PatternConsts.USERNAME)),
                    username -> new IllegalArgumentException(String.format("用户名【%s】不符合规范", username)));

            checkValue(ACCOUNT, String.class).withRule(
                    PredicateTools.from(StringUtils::isNotBlank)
                            .and(account -> RegexTools.matchesOne(account,
                                    new Pattern[] { PatternConsts.EMAIL, PatternConsts.MOBILE_PHONE })),
                    "请输入正确的邮箱地址或手机号");

            checkValue(PASSWORD, String.class)
                    .withRule(StringUtils::isNotEmpty, "密码不能为空")
                    .withRule(pwd -> RegexTools.matches(pwd, PatternConsts.PASSWORD), "密码不符合规范");

            // 校验到多个属性，只能针对 map 本身进行校验
            withRule(m -> Objects.equals(m.get(PASSWORD), m.get(PASSWORD2)),
                    "两次输入的密码不一样！");

            // 通过泛型方式调用方法，指定数据类型
            this.<Integer>checkValue(AGE)
                    .withRule(Objects::nonNull)
                    .withRule(age -> (18 <= age && 60 >= age));

            checkValue(BOOLEAN, Boolean.class)
                    .withRule(Objects::nonNull, "Boolean property could not be null.")
                    .withRule(b -> b, "Boolean property must be true.");

            this.<Collection<String>>checkValue(ROLE_LIST)
                    .withRule(CollectionTools::isNotEmpty, "角色列表不能为空！")
                    .withRule(l -> l.stream().allMatch(StringUtils::isNotBlank),
                            () -> new IllegalArgumentException("角色标识不能为空！"));

        }
    };

    @Test
    void testValidateAndCopy() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "ZhouXY");
        params.put("account", "zhouxy@code108.cn");
        params.put("password", "99Code108");
        params.put("password2", "99Code108");
        params.put("age", 18);
        params.put("boolean", true);
        params.put("roleList", Arrays.asList("admin", ""));

        Map<String, Object> validedParams = validator.validateAndCopy(params);
        System.out.println(validedParams);
    }
}
