# Plusone Validator

## 简介

Plusone Validator 是一个受 [FluentValidation](https://github.com/FluentValidation/FluentValidation) 启发的 Java 校验库，使用 Lambda 表达式（方法引用）和流式 API 构建声明式校验规则。

**特性：**
- 链式调用
- 支持 **POJO** 和 **Map** 两种校验模式
- 每种校验规则支持四种错误信息提供方式（默认/自定义字符串/自定义异常/含属性值的自定义异常）
- 基于 JDK 1.8

## 环境要求

- **JDK**：1.8 及以上
- **编码**：UTF-8

## 安装

Plusone Validator 暂未发布至 Maven 中央仓库，需 clone 代码仓库后通过 `mvn install` 安装到本地仓库，然后在项目中引入：

```xml
<dependency>
  <groupId>xyz.zhouxy.plusone</groupId>
  <artifactId>plusone-validator</artifactId>
  <version>${plusone-validator.version}</version>
</dependency>
```

> 该项目依赖 [plusone-commons](https://gitea.zhouxy.xyz/plusone/plusone-commons)，它本身依赖 Guava。

## 示例

以下示例基于一个包含姓名、邮箱、会员等级、客户编号、生日、地址等字段的 `Customer` 对象。

### 校验 POJO 对象

继承 `BaseValidator<T>`，在构造器中通过 `ruleFor` 添加规则链：
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
        ruleFor(Customer::getVipLevel, Customer::getBirthday)
            .must((vipLevel, birthday) -> vipLevel <= 5 || LocalDate.now().minusYears(18).isAfter(birthday),
                "5级以上会员必须满18周岁");
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

继承 `MapValidator<K,V>`，在构造器中通过 `ruleForString`、`ruleForInt` 等方法按 key 添加规则链。

`validateAndCopy()` 会**先校验，然后仅保留白名单 key**（构造时传入的 `FIELD_NAMES`），多余字段会被自动剥离，可防止 Map 注入攻击。

> 注意泛型类型见证语法 `this.<LocalDate>ruleFor(...)` — 当 Map 的 value 类型是 `Object` 时，需显式指定实际类型。
```java
class CustomerMapValidator extends MapValidator<String, Object> {

    private static final CustomerMapValidator INSTANCE = new CustomerMapValidator();

    private static final String[] FIELD_NAMES = {
        "name", "emailAddress", "vipLevel", "customerId", "birthday", "address"
    };

    private CustomerMapValidator() {
        // validateAndCopy() 时默认保留的 key
        super(FIELD_NAMES);

        ruleForString("name").notBlank("姓名不能为空");
        ruleForString("emailAddress").notBlank().emailAddress();
        ruleForInt("vipLevel").notNull().inRange(Range.closed(0, 10), "会员等级必须在0-10之间");
        ruleForString("customerId").notBlank("客户编号不能为空");
        this.<LocalDate>ruleFor("birthday")
            .notNull("生日不能为空")
            .must(LocalDate.now().minusYears(16)::isAfter, "用户必须大于16周岁");
        ruleForString("address").length(20, 250, "地址长度必须在20-250之间");
        this.<Integer, LocalDate>ruleForPair("vipLevel", "birthday")
            .must((vipLevel, birthday) -> vipLevel <= 5 || LocalDate.now().minusYears(18).isAfter(birthday),
                "5级以上会员必须满18周岁");
    }

    public static CustomerMapValidator getInstance() {
        return INSTANCE;
    }
}
```

使用：
```java
public void foo(Map<String, Object> customer) {
    Map<String, Object> validatedCustomer = CustomerMapValidator.getInstance().validateAndCopy(customer);
    // ...
}
```

## 快速参考

### ruleFor 方法一览

| 方法 | 属性类型 | 适用场景 |
|------|---------|---------|
| `ruleFor(getter)` | 任意类型 | 通用对象属性校验 |
| `ruleForString(getter)` | `String` | 字符串属性校验 |
| `ruleForInt(getter)` | `Integer` | 整数属性校验 |
| `ruleForLong(getter)` | `Long` | 长整数属性校验 |
| `ruleForDouble(getter)` | `Double` | 浮点数属性校验 |
| `ruleForBool(getter)` | `Boolean` | 布尔属性校验 |
| `ruleForComparable(getter)` | `Comparable` | 可比较类型属性校验 |
| `ruleForCollection(getter)` | `Collection<E>` | 集合属性校验 |
| `ruleForArray(getter)` | `E[]` | 数组属性校验 |
| `ruleFor(g1, g2)` | 二元组 | 两个属性的联合校验 |

### 校验规则一览

| 规则 | Object | Comparable | 数值 | String | Bool | 集合/数组 | 说明 |
|------|:---:|:---:|:---:|:---:|:---:|:---:|------|
| `notNull()` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 不为 null |
| `isNull()` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 必须为 null |
| `equal(obj)` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 等于给定值 |
| `notEqual(obj)` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 不等于给定值 |
| `must(pred)` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 自定义条件 |
| `gt/ge/lt/le` | — | — | ✅ | — | — | — | 大于/大于等于/小于/小于等于 |
| `inRange(range)` | — | ✅ | ✅ | ✅ | — | — | 值在指定区间内 |
| `notBlank()` | — | — | — | ✅ | — | — | 不为空白字符串 |
| `notEmpty()` | — | — | — | ✅ | — | ✅ | 不为空（长度 > 0） |
| `isEmpty()` | — | — | — | — | — | ✅ | 必须为空 |
| `length(min, max)` | — | — | — | ✅ | — | ✅ | 长度/大小在范围内 |
| `emailAddress()` | — | — | — | ✅ | — | — | 满足邮箱格式 |
| `matches(pattern)` | — | — | — | ✅ | — | — | 匹配正则表达式 |
| `matchesAny(patterns)` | — | — | — | ✅ | — | — | 匹配任一正则 |
| `matchesAll(patterns)` | — | — | — | ✅ | — | — | 匹配所有正则 |
| `allMatch(pred)` | — | — | — | — | — | ✅ | 所有元素满足条件 |
| `isTrueValue()` | — | — | — | — | ✅ | — | 必须为 true |
| `isFalseValue()` | — | — | — | — | ✅ | — | 必须为 false |

### 自定义错误信息

每个校验规则都支持四种错误信息提供方式：

```java
// 1. 默认消息
ruleFor(Person::getName).notNull();

// 2. 自定义字符串
ruleFor(Person::getName).notNull("姓名不能为空");

// 3. 自定义异常（Supplier）
ruleFor(Person::getName).notNull(() -> new BizException("姓名不能为空"));

// 4. 自定义异常 + 属性值注入（Function）
ruleFor(Person::getAge).gt(0, age -> new BizException("年龄必须 > 0，当前值：%d", age));
```

### Null 值处理

多数校验规则对 `null` **宽松处理**（视为通过），让用户通过 `notNull()` 显式控制 null 行为：

```java
// ✅ null 视为通过 — 不会因 NPE 失败
ruleFor(Person::getEmail).emailAddress();

// ✅ 显式拒绝 null
ruleFor(Person::getEmail).notNull("邮箱不能为空").emailAddress();
```

例外：`notBlank()` 和 `notEmpty()` 将 `null` 视为不通过（null 本身就是 blank/empty）。

### 嵌套对象校验

使用 `withRule(Consumer)` 在子类构造器中调用嵌套校验器：

```java
class OrderValidator extends BaseValidator<Order> {
    private static final OrderValidator INSTANCE = new OrderValidator();

    private OrderValidator() {
        ruleFor(Order::getOrderId).notBlank("订单号不能为空");

        // 嵌套校验 Customer
        withRule(order -> {
            Customer customer = order.getCustomer();
            CustomerValidator.getInstance().validate(customer);
        });

        // 需要自定义错误信息时，try-catch 重新包装
        withRule(order -> {
            try {
                AddressValidator.getInstance().validate(order.getAddress());
            } catch (ValidationException e) {
                throw ValidationException.withMessage("地址校验失败：%s", e.getMessage());
            }
        });
    }
}
```

### 对象整体校验

使用 `withRule(Predicate)` 对多个属性进行联合校验：

```java
// 三个及以上字段的联合校验
withRule(order -> order.getTotalAmount().compareTo(order.getPaidAmount()) >= 0,
    "订单金额不能小于已付金额");
```

---

## 关于本项目

Plusone Validator 受 .NET 的 [FluentValidation](https://github.com/FluentValidation/FluentValidation) API 启发，使用 [Apache License 2.0](./LICENSE) 开源。
