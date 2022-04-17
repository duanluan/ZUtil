# JSON

经测试，以下三种转换的实现均为 FastJSON 更快，吞吐量为 Jackson、Gson 的 10~55 倍。

## 转 JSON 字符串

```java
// 支持指定多个序列化特性
JsonUtils.toJson(object, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
```

## 转对象

```java
// 支持指定多个反序列化特性
JsonUtils.parseObject(json, SysUser.class, Feature.AllowArbitraryCommas)
```

## 转集合

```java
// 支持自定义反序列化配置
ParserConfig parserConfig = new ParserConfig();
parserConfig.setAsmEnable(false);
JsonUtils.parseArray(json, SysUser.class, parserConfig);
```

## 序列化规则

### SerializerFeature（FastJSON）

|              SerializerFeature | 说明                                  |
|-------------------------------:|-------------------------------------|
|                QuoteFieldNames | key 双引号，默认为 true                    |
|                UseSingleQuotes | 使用单引号                               |
|              WriteMapNullValue | 输出值为 null 的属性                       |
|         WriteEnumUsingToString | 用枚举 toString() 值输出                  |
|             WriteEnumUsingName | 用枚举 name() 输出                       |
|           UseISO8601DateFormat | Date 使用 ISO 8601 格式输出               |
|         WriteDateUseDateFormat | 用 JSON.DEFAULT_DATE_FORMAT 定义日期输出格式 |
|           WriteNullListAsEmpty | 用 [] 输出值为 null 的列表                  |
|         WriteNullStringAsEmpty | 用 "" 输出值为 null 的字符串                 |
|          WriteNullNumberAsZero | 用 0 输出值为 null 的数字                   |
|        WriteNullBooleanAsFalse | 用 false 输出值为 null 的布尔值              |
|             SkipTransientField | 跳过 Getter 为 getTransient() 的属性      |
|           IgnoreNonFieldGetter | 忽略没有对应属性的 Getter                    |
|              IgnoreErrorGetter | 忽略错误的 Getter                        |
|                      SortField | 属性名排序                               |
|                   MapSortField | 感觉和上一个作用相同，没测出来                     |
|                   PrettyFormat | 格式化                                 |
|                 WriteClassName | 序列化时输出类名（"@type":"……"）              |
|          NotWriteRootClassName | 不输出最外层结构的类名                         |
| DisableCircularReferenceDetect | 禁用循环引用检测                            |
|            WriteSlashAsSpecial | 用 \/ 输出斜杠（/）                        |
|              BrowserCompatible | 浏览器兼容，即 URL 编码                      |
|                  BrowserSecure | 浏览器安全，即转义“<”、“>”                    |
|        DisableCheckSpecialChar | 禁用特殊字符（会转义）检查                       |
|                    BeanToArray | Bean 转换为数组                          |
|      WriteNonStringKeyAsString | 用字符串输出非字符串的 key                     |
|    WriteNonStringValueAsString | 用字符串输出非字符串的 value                   |
|           NotWriteDefaultValue | 不输出基础类型为默认值的属性                      |
|         WriteBigDecimalAsPlain | 输出 BigDecimal 为实际值                  |

### JsonInclude（Jackson）

|  JsonInclude | 说明                                                         |
|-------------:|------------------------------------------------------------|
|       ALWAYS | 输出所有属性，默认为 true                                            |
|     NON_NULL | 输出值不为 null 的属性                                             |
|   NON_ABSENT | 输出值不为 null（非 AtomicReference 类型）的属性                        |
|   NON_ABSENT | 输出值不为 null（非 AtomicReference 类型）、字符串或数组长度为 0、集合或 Map 为空的属性 |
|  NON_DEFAULT | 不输出基础类型为默认值的属性                                             |
|       CUSTOM | 指定 valueFilter，自定义是否序列化                                    |
| USE_DEFAULTS | 优先使用类设置的 JsonInclude（注解）                                   |

### JsonSerialiser（Gson）

略

## 反序列化规则

略
