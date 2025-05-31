# Plusone Validator

## 简介
Plusone Validator 是一个校验库，使用 lambda 表达式（包括方法引用）和流式 API 构建校验规则，对对象进行校验。

## 安装
Plusone Validator 暂未提交 maven 中央仓库，可 clone 代码仓库，通过 maven 安装到本地库，然后在项目中引入。

## 示例

### 校验对象

定义校验器：
```java
class CustomerValidator extends BaseValidator<Customer> {

    private static final CustomerValidator INSTANCE = new CustomerValidator();

    private CustomerValidator() {
        ruleFor(Customer::getName).notBlank("姓名不能为空");
        ruleFor(Customer::getEmailAddress).notBlank().emailAddress();
        ruleFor(Customer::getVipLevel).notNull().inRange(Range.closed(0, 10), "会员等级必须在0-10之间");
        ruleFor(Customer::getCustomerId).notBlank("客户编号不能为空");
        ruleFor(Customer::getBirthday)
            .notNull("生日不能为空")
            .must(LocalDate.now().minusYears(16)::isAfter, "用户必须大于16周岁");
        ruleFor(Customer::getAddress).length(20, 250, "地址长度必须在20-250之间");
        ruleFor((Customer customer) -> Pair.of(customer.getVipLevel(), customer.getBirthday()))
            .must(CustomerValidator::validateAge, "5级以上会员必须满18周岁");
    }

    private static boolean validateAge(Pair<Integer, LocalDate> vipLevelAndBirthday) {
        Integer vipLevel = vipLevelAndBirthday.getLeft();
        LocalDate birthday = vipLevelAndBirthday.getRight();
        return vipLevel <= 5 || LocalDate.now().minusYears(18).isAfter(birthday);
    }

    public static CustomerValidator getInstance() {
        return INSTANCE;
    }
}
```

使用：
```java
public void foo(Customer customer) {
    CustomerValidator.getInstance().validate(customer);
    // ...
}
```

### 校验 Map

定义校验器：
```java
class CustomerMapValidator extends MapValidator<String, Object> {

    private static final CustomerMapValidator INSTANCE = new CustomerMapValidator();

    private static final String[]  FIELD_NAMES = {
        "name", "emailAddress", "vipLevel", "customerId", "birthday", "address"
    };

    private CustomerMapValidator() {
        super(FIELD_NAMES);

        ruleForString("name").notBlank("姓名不能为空");
        ruleForString("emailAddress").notBlank().emailAddress();
        ruleForInt("vipLevel").notNull().inRange(Range.closed(0, 10), "会员等级必须在0-10之间");
        ruleForString("customerId").notBlank("客户编号不能为空");
        this.<LocalDate>ruleFor("birthday")
            .notNull("生日不能为空")
            .must(LocalDate.now().minusYears(16)::isAfter, "用户必须大于16周岁");
        ruleForString("address").length(20, 250, "地址长度必须在20-250之间");
        this.<Pair<Integer, LocalDate>>ruleFor((Map<String, Object> customer) ->
                Pair.of(MapUtils.getInteger(customer, "vipLevel"), (LocalDate) customer.get("birthday")))
            .must(CustomerMapValidator::validateAge, "5级以上会员必须满18周岁");
    }

    private static boolean validateAge(Pair<Integer, LocalDate> vipLevelAndBirthday) {
        Integer vipLevel = vipLevelAndBirthday.getLeft();
        LocalDate birthday = vipLevelAndBirthday.getRight();
        return vipLevel <= 5 || LocalDate.now().minusYears(18).isAfter(birthday);
    }

    public static CustomerMapValidator getInstance() {
        return INSTANCE;
    }
}
```

使用：
```java
public void foo(Map<String, Object> customer) {
    CustomerMapValidator.getInstance().validate(customer);
    // ...
}
```
---

## 其他

Plusone Validator 是个人在学习 Microsoft 的 [eShop](https://github.com/dotnet/eShop) 时，被其中 [FluentValidation](https://github.com/FluentValidation/FluentValidation) 的 API 所吸引，出于学习和个人使用的目的进行开发和维护。使用 [Apache License 2.0](./LICENSE) 开源。

欢迎通过 issue 反馈使用过程中发现的问题和建议。
