package xyz.zhouxy.plusone.validator2.test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.commons.constant.PatternConsts;
import xyz.zhouxy.plusone.commons.function.Predicates;
import xyz.zhouxy.plusone.commons.util.RegexUtil;
import xyz.zhouxy.plusone.validator.BaseValidator;

class BaseValidator2Test {
    @Test
    void testValidate() {
        RegisterCommand registerCommand = new RegisterCommand("null", "luquanlion@outlook.com", "22336",
                "A1b2C3d4", "A1b2C3d4", Arrays.asList(new String[] { "admin", "editor" }));
        RegisterCommandValidator.INSTANCE.validate(registerCommand);
        System.out.println(registerCommand);
    }
}

class RegisterCommandValidator extends BaseValidator<RegisterCommand> {

    static final RegisterCommandValidator INSTANCE = new RegisterCommandValidator();

    private RegisterCommandValidator() {
        ruleForString(RegisterCommand::getUsername)
                .isTrue(Predicates.<String>of(Objects::nonNull)
                        .and(StringUtils::isNotEmpty)
                        .and(StringUtils::isNotBlank)
                        .and(username -> RegexUtil.matches(username, PatternConsts.EMAIL)),
                        username -> new IllegalArgumentException(String.format("用户名【%s】不符合规范", username)));
    }
}

/**
 * RegisterCommand
 */
class RegisterCommand {

    private String username;
    private String account;
    private String code;
    private String password;
    private String password2;
    private List<String> roles;

    public RegisterCommand() {
    }

    public RegisterCommand(String username, String account, String code, String password, String password2,
            List<String> roles) {
        this.username = username;
        this.account = account;
        this.code = code;
        this.password = password;
        this.password2 = password2;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegisterCommand [username=").append(username).append(", account=").append(account)
                .append(", code=").append(code).append(", password=").append(password).append(", password2=")
                .append(password2).append(", roles=").append(roles).append("]");
        return builder.toString();
    }
}
