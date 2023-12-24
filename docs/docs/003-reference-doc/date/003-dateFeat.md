# DateFeat 时间特性

可以通过临时或总是（Always）修改其静态成员变量，来决定`DateUtil`中方法对时间的处理方式。

## RESOLVER_STYLE - 解析器模式/风格

```java
// 临时设置解析器模式为宽容
DateFeat.set(ResolverStyle.LENIENT);
// 默认为严格模式，此方法超出时间范围会报错，每个月最多 6 周，此处却需要获取第 7 周的周一
// 但如果在调用方法前将模式修改为宽容，则不会报错，正常返回，如果修改为智能，结果超出本月时会返回本月的最后一天
  DateUtil.getStartDayOfWeekOfMonth(LocalDate.now(),7);
```

## LOCALE - 区域

```java
// 总是设置解析器模式为中文 
DateFeat.setAlways(Locale.SIMPLIFIED_CHINESE);
// 输出周时结果为中文
  DateUtil.format(LocalDate.now(),"E")
// 输出月时结果仍为中文
  DateUtil.format(LocalDate.now(),"MMM")
```

## ZONE_ID - 时区/区域 ID

```java
// 临时设置时区
DateFeat.set(ZoneId.from(ZoneOffset.UTC));
// 假设系统时区为 UTC+8，因为上一行设置了时区，所以实际结果会比系统时间少 8 小时
  DateUtil.format(LocalDateTime.now());
```

## MIN_DATE_YEAR - 最小 Date 年

```java
// 临时设置最小 Date 年
DateFeat.setMinDateYear(1L);
// Date 的年份为 1，而不是默认的 1970
  DateUtil.toDate(LocalTime.now());

```
