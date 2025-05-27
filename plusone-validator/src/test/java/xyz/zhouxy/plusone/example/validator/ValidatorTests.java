package xyz.zhouxy.plusone.example.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static xyz.zhouxy.plusone.commons.constant.PatternConsts.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.commons.collection.CollectionTools;
import xyz.zhouxy.plusone.commons.function.PredicateTools;
import xyz.zhouxy.plusone.commons.util.AssertTools;
import xyz.zhouxy.plusone.commons.util.RegexTools;
import xyz.zhouxy.plusone.commons.util.StringTools;
import xyz.zhouxy.plusone.validator.Validator;

class ValidatorTests {
    @Test
    void testValidate() {
        RegisterCommand registerCommand = new RegisterCommand(
                "null", "luquanlion@outlook.com", "22336",
                "A1b2C3d4", "A1b2C3d4",
                Arrays.asList(new String[] { "admin", "editor" }));

        Validator<RegisterCommand> registerCommandValidator = new Validator<RegisterCommand>()
                // 传入 predicate 和 Function<T, E extends RuntimeException>
                .addRule(command -> {
                    String username = command.getUsername();
                    return Objects.nonNull(username)
                            && StringTools.isNotEmpty(username)
                            && StringTools.isNotBlank(username)
                            && RegexTools.matches(username, USERNAME);
                }, command -> new IllegalArgumentException(String.format("用户名【%s】不符合规范", command.getUsername())))
                // 传入 predicate 和 error message
                .addRule(command -> PredicateTools
                        .<String>from(Objects::nonNull)
                        .and(account -> RegexTools.matchesOne(account, new Pattern[] { EMAIL, MOBILE_PHONE }))
                        .test(command.getAccount()),
                        "请输入邮箱地址或手机号")
                // 传入 rule
                .addRule(command -> {
                    String code = command.getCode();
                    AssertTools.checkArgument(Objects.nonNull(code), "验证码不能为空");
                    AssertTools.checkArgument(RegexTools.matches(code, CAPTCHA), "验证码不符合规范");
                })
                // 传入 rule
                .addRule(command -> {
                    String password = command.getPassword();
                    AssertTools.checkArgument(StringTools.isNotEmpty(password), "密码不能为空");
                    AssertTools.checkArgument(RegexTools.matches(password, PASSWORD), "密码不符合规范");
                })
                // 传入 predicate 和 Supplier<E extends RuntimeException>
                .addRule(command -> CollectionTools.isNotEmpty(command.getRoles()),
                        () -> new RuntimeException("角色列表不能为空"))
                // 传入 predicate 和 error message
                .addRule(command -> Objects.equals(command.getPassword(), command.getPassword2()),
                        "两次输入的密码不一致");
        assertDoesNotThrow(() -> registerCommandValidator.validate(registerCommand));
    }

    /**
     * RegisterCommand
     */
    static class RegisterCommand {

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
}
