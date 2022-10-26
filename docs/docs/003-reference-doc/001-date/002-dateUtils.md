# DateUtils 时间工具类

继承`org.apache.commons.lang3.time.DateUtils`。

大量使用 Java 8 时间类型，即`LocalDate`、`LocalTime`、`LocalDateTime`、`ZonedDateTime`等。

## getFormatterBuilder

获取时间格式化构造器，即 `DateTimeFormatterBuilder`。

可以指定格式和时间级别的默认值。

```java
// 不包含年时获取年会报错：Unsupported field: Year，但是赋值了默认时间级别后就不会
Map<TemporalField, Long> fieldValueMap = new HashMap<>();
fieldValueMap.put(ChronoField.YEAR, 0L);
DateTimeFormatterBuilder formatterBuilder = DateUtils.getFormatterBuilder("MM-dd", fieldValueMap);
```

# getStartDayOfWeekOfMonth 

![](DateUtils.getStartDayOfWeekOfMonth.png)

# getEndDayOfWeekOfMonth

![](DateUtils.getEndDayOfWeekOfMonth.png)
