package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.date.DatePattern;
import top.zhogjianhao.date.DateUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

@Slf4j
@DisplayName("时间工具类测试")
public class DateUtilsTest {

  private final ZonedDateTime nowZonedDateTime = ZonedDateTime.now();
  private final LocalDateTime nowLocalDateTime = LocalDateTime.now();
  private final LocalDate nowLocalDate = LocalDate.now();
  private final LocalTime nowLocalTime = LocalTime.now();
  private final Date nowDate = new Date();
  private final long currentTimeMillis = System.currentTimeMillis();
  private final String customSource = "21/08/08 08.08.08";
  private final String customPattern = "uu/MM/dd HH.mm.ss";

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("探寻 ChronoField")
  @Test
  void testChronoField() {
    String indent = "\t\t\t\t\t\t\t\t\t\t\t\t\t";
    LocalDateTime now = nowLocalDateTime;
    // 时代：公元前，相当于当前时间的负数
    println(now.with(ChronoField.ERA, 0) + indent.replaceFirst("\t", "") + "时代：公元前");
    // 时代：公元，即当前时间
    println(now.with(ChronoField.ERA, 1) + indent + "时代：公元");
    // 公元前所属年：以当前时间为基础，年修改为公元前 2 年，结果 -0001-10-01T02:30:32.723 加上当前时间的月份往后 10-01T02:30:32.723 为 2 年
    println(now.with(ChronoField.ERA, 0).with(ChronoField.YEAR_OF_ERA, 2) + indent.replaceFirst("\t", "") + "公元前所属年");
    // 公元后所属年：以当前年月为基础，年修改为 2 年
    println(now.with(ChronoField.ERA, 1).with(ChronoField.YEAR_OF_ERA, 2) + indent + "公元后所属年");
    // 年
    println(now.with(ChronoField.YEAR, 2020) + indent + "年");
    // 预期月，从 0 年开始计算月（从 0 开始），2021 年 10 月的值为 2021 * 12 + 10 - 1
    println(now.with(ChronoField.PROLEPTIC_MONTH, 0) + indent + "预期月");
    // 年的月
    println(now.with(ChronoField.MONTH_OF_YEAR, 9) + indent + "年的月");
    // 年的对齐周：年的第一天为第一周的第一天
    println(now.with(ChronoField.ALIGNED_WEEK_OF_YEAR, 2) + indent + "年的对齐周");
    // 月的对齐周：月的第一天为此月第一周的第一天
    println(now.with(ChronoField.ALIGNED_WEEK_OF_MONTH, 2) + indent + "月的对齐周");
    // 年的对齐周的天
    println(now.with(ChronoField.ALIGNED_WEEK_OF_YEAR, 2).with(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR, 5) + indent + "年的对齐周的天");
    // 月的对齐周的天
    println(now.with(ChronoField.ALIGNED_WEEK_OF_MONTH, 2).with(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH, 5) + indent + "月的对齐周的天");
    // 年的天
    println(now.with(ChronoField.DAY_OF_YEAR, 1) + indent + "年的天");
    // 月的天
    println(now.with(ChronoField.DAY_OF_MONTH, 1) + indent + "月的天");
    // 周的天
    println(now.with(ChronoField.DAY_OF_WEEK, 1) + indent + "周的天");
    // 以 1970-01-01 为 0 开始的天（忽略偏移量和时区）
    println(now.with(ChronoField.EPOCH_DAY, 1) + indent + "以 1970-01-01 为 0 开始的天（忽略偏移量和时区）");
    // 上午（0-12）
    println(now.with(ChronoField.AMPM_OF_DAY, 0) + indent + "上午（0-12）");
    // 下午（13-23）
    println(now.with(ChronoField.AMPM_OF_DAY, 1) + indent + "下午（13-23）");
    // 上午或下午的小时，以当前 AMPM 为准，从 0 开始
    println(now.with(ChronoField.HOUR_OF_AMPM, 0) + indent + "上午或下午的小时");
    // 上午的小时
    println(now.with(ChronoField.AMPM_OF_DAY, 0).with(ChronoField.HOUR_OF_AMPM, 0) + indent + "上午的小时");
    // 下午的小时
    println(now.with(ChronoField.AMPM_OF_DAY, 1).with(ChronoField.HOUR_OF_AMPM, 0) + indent + "下午的小时");
    // 12 小时制，以当前 AMPM 为准，从 1 开始
    println(now.with(ChronoField.CLOCK_HOUR_OF_AMPM, 1) + indent + "12 小时制");
    // 上午的 12 小时制小时
    println(now.with(ChronoField.AMPM_OF_DAY, 0).with(ChronoField.CLOCK_HOUR_OF_AMPM, 1) + indent + "上午的 12 小时制小时");
    // 下午的 12 小时制小时
    println(now.with(ChronoField.AMPM_OF_DAY, 1).with(ChronoField.CLOCK_HOUR_OF_AMPM, 1) + indent + "下午的 12 小时制小时");
    // 24 小时制，从 1 开始，24 就是 00:00（非第二天）
    println(now.with(ChronoField.CLOCK_HOUR_OF_DAY, 24) + indent + "24 小时制");
    // 天的小时
    println(now.with(ChronoField.HOUR_OF_DAY, 0) + indent + "天的小时");
    // 天的分钟
    println(now.with(ChronoField.MINUTE_OF_DAY, 1) + indent + "天的分钟");
    // 小时的分钟
    println(now.with(ChronoField.MINUTE_OF_HOUR, 1) + indent + "小时的分钟");
    // 天的秒
    println(now.with(ChronoField.SECOND_OF_DAY, 1) + indent + "天的秒");
    // 分钟的秒
    println(now.with(ChronoField.SECOND_OF_MINUTE, 1) + indent + "分钟的秒");
    // 以 1970-01-01T00:00Z (ISO) 为 0 开始的秒，必须和时区结合使用（+时区小时）
    println(now.atZone(ZoneId.systemDefault()).with(ChronoField.INSTANT_SECONDS, 1) + "\t\t以 1970-01-01T00:00Z (ISO) 为 0 开始的秒，必须和时区结合使用（+时区小时）");
    // 天的毫秒
    println(now.with(ChronoField.MILLI_OF_DAY, 1) + indent + "天的毫秒");
    // 秒的毫秒
    println(now.with(ChronoField.MILLI_OF_SECOND, 1) + indent + "秒的毫秒");
    // 天的微秒
    println(now.with(ChronoField.MICRO_OF_DAY, 1) + indent.replaceFirst("\t\t", "") + "天的微秒");
    // 秒的微秒
    println(now.with(ChronoField.MICRO_OF_SECOND, 1) + indent.replaceFirst("\t\t", "") + "秒的微秒");
    // 天的纳秒
    println(now.with(ChronoField.NANO_OF_DAY, 1) + indent.replaceFirst("\t\t\t", "") + "天的纳秒");
    // 秒的纳秒
    println(now.with(ChronoField.NANO_OF_SECOND, 1) + indent.replaceFirst("\t\t\t", "") + "秒的纳秒");
  }

  @DisplayName("探寻时区")
  @Test
  void atZone() {
    LocalDateTime now = nowLocalDateTime;
    // 无时区信息
    println(now);

    // 系统区域，atZone 只是给对象赋时区，并不会修改小时
    println(now.atZone(DateUtils.SYSTEM_ZONE_ID));
    // 设置区域
    println(now.atZone(ZoneId.of("Asia/Shanghai")));
    // 设置偏移量
    println(now.atZone(ZoneOffset.ofHours(1)));

    now = now.withMonth(8);
    // 指定时区修改小时，本地和 UTC 相差多少就会加上多少，比如 Asia/Shanghai 和 UTC 相差 -8 小时
    println(now.atZone(DateUtils.SYSTEM_ZONE_ID).withZoneSameInstant(ZoneOffset.UTC));
    // 指定时区，其他保持不变，TODO 但会转换为此区域的有效时间（夏令时）？
    println(now.atZone(DateUtils.SYSTEM_ZONE_ID).withZoneSameLocal(ZoneOffset.ofHours(-7)));
    // 指定区域，其他保持不变
    println(now.atZone(DateUtils.SYSTEM_ZONE_ID).withZoneSameLocal(ZoneId.of("America/Los_Angeles")));
    // 将区域设置为偏移量（ISO-8601），方便网络传输
    println(now.atZone(DateUtils.SYSTEM_ZONE_ID).withZoneSameLocal(ZoneId.of("America/Los_Angeles")).withFixedOffsetZone());

    Instant instant = now.atZone(DateUtils.SYSTEM_ZONE_ID).toInstant();
    // 夏令时在 ZoneOffset 当中表现，同一时区（使用夏令时），不同月份，甚至不同年份（tz database），夏季和冬季的 ZoneOffset 不同：https://www.itranslater.com/qa/details/2326279995771061248、https://en.wikipedia.org/wiki/Tz_database
    println(ZoneId.of("America/Los_Angeles").getRules().getOffset(instant));
    instant = now.withMonth(1).atZone(DateUtils.SYSTEM_ZONE_ID).toInstant();
    println(ZoneId.of("America/Los_Angeles").getRules().getOffset(instant));
  }

  @DisplayName("getDefaultFormatter：获取 DateTimeFormatter 对象")
  @Test
  void getDefaultFormatter() {
    // 格式中不含年
    DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern(DatePattern.MM_DD).toFormatter();
    // 获取年报错：Unsupported field: Year
    try {
      dateTimeFormatter.parse("08-08").get(ChronoField.YEAR);
    } catch (Exception e) {
      log.warn(e.getMessage());
    }

    // 使用 getDefaultFormatter() 时会赋默认值
    dateTimeFormatter = DateUtils.getDefaultFormatter(DatePattern.MM_DD, Locale.ENGLISH, ZoneId.systemDefault());
    println(dateTimeFormatter.parse("08-08").get(ChronoField.YEAR));

    // 如果格式中已存在年，说明被转换的内容中已存在年，则不会赋默认值
    dateTimeFormatter = DateUtils.getDefaultFormatter(DatePattern.UUUU_MM_DD, Locale.ENGLISH, ZoneId.systemDefault());
    println(dateTimeFormatter.parse("2021-08-08").get(ChronoField.YEAR));
  }

  @DisplayName("format：格式化为字符串")
  @Test
  void format() {
    ZoneOffset zoneOffset = ZoneOffset.ofHours(-18);

    println("ZonedDateTime 指定时区和格式" + DateUtils.format(nowZonedDateTime, zoneOffset, DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("ZonedDateTime 指定格式：" + DateUtils.format(nowZonedDateTime, DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("ZonedDateTime 指定时区：" + DateUtils.format(nowZonedDateTime, zoneOffset));
    println("ZonedDateTime：" + DateUtils.format(nowZonedDateTime));

    println("LocalDateTime 指定时区和格式：" + DateUtils.format(nowLocalDateTime, zoneOffset, DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("LocalDateTime 指定格式：" + DateUtils.format(nowLocalDateTime, DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("LocalDateTime 指定时区：" + DateUtils.format(nowLocalDateTime, zoneOffset));
    println("LocalDateTime：" + DateUtils.format(nowLocalDateTime));

    println("LocalDate 指定时区和格式：" + DateUtils.format(nowLocalDate, zoneOffset, DatePattern.UUUU_MM_DD));
    println("LocalDate 指定格式：" + DateUtils.format(nowLocalDate, DatePattern.UUUU_MM_DD));
    println("LocalDate 指定时区：" + DateUtils.format(nowLocalDate, zoneOffset));
    println("LocalDate：" + DateUtils.format(nowLocalDate));

    println("LocalTime 指定时区和格式：" + DateUtils.format(nowLocalTime, zoneOffset, DatePattern.HH_MM_SS));
    println("LocalTime 指定格式：" + DateUtils.format(nowLocalTime, DatePattern.HH_MM_SS));
    println("LocalTime 指定时区：" + DateUtils.format(nowLocalTime, zoneOffset));
    println("LocalTime：" + DateUtils.format(nowLocalTime));

    println("Date 指定时区和格式：" + DateUtils.format(nowDate, zoneOffset, DatePattern.YYYY_MM_DD_HH_MM_SS));
    println("Date 指定格式：" + DateUtils.format(nowDate, DatePattern.YYYY_MM_DD_HH_MM_SS));
    println("Date 指定时区：" + DateUtils.format(nowDate, zoneOffset));
    println("Date：" + DateUtils.format(nowDate));
  }

  @DisplayName("toDate：转换为 Date 对象")
  @Test
  void toDate() {
    // 当前时区偏移量为 8，指定时区偏移量为 18，则小时数会 +(18-8)
    ZoneOffset zoneOffset = ZoneOffset.ofHours(18);

    println("ZonedDateTime 指定时区：" + DateUtils.toDate(nowZonedDateTime, zoneOffset));
    println("LocalDateTime 指定时区：" + DateUtils.toDate(nowLocalDateTime, zoneOffset));
    // LocalDate 的时分秒此处默认为 0，所以时区偏移量 >=0 都没有什么意义
    println("LocalDate 指定时区：" + DateUtils.toDate(nowLocalDate, zoneOffset));
    println("LocalTime 指定时区：" + DateUtils.toDate(nowLocalTime, zoneOffset));

    println("ZonedDateTime：" + DateUtils.toDate(nowZonedDateTime));
    println("LocalDateTime：" + DateUtils.toDate(nowLocalDateTime));
    println("LocalDate：" + DateUtils.toDate(nowLocalDate));
    println("LocalTime：" + DateUtils.toDate(nowLocalTime));
  }

  @DisplayName("parse：解析为时间对象")
  @Test
  void parse() {
    // 当前时区偏移量为 8，指定时区偏移量为 18，则小时数会 +(18-8)
    ZoneOffset zoneOffset = ZoneOffset.ofHours(18);
    String yyyyMMddHHmmss = "2021-08-08 08:08:08";
    String yyyyMMddHHmm = "2021-08-08 08:08";
    String yyyyMMdd = "2021-08-08";
    String yyyyMM = "2021-08";
    String HHmmss = "08:08:08";
    String HHmm = "08:08";

    println("LocalDateTime 解析时间戳指定时区：" + DateUtils.parseLocalDateTime(currentTimeMillis, zoneOffset));
    println("LocalDateTime 指定时区和格式" + DateUtils.parseLocalDateTime(customSource, zoneOffset, customPattern));
    println("LocalDateTime 指定时区预定格式（uuuu-MM-dd HH:mm:ss）：" + DateUtils.parseLocalDateTime(yyyyMMddHHmmss, zoneOffset));
    println("LocalDateTime 指定时区预定格式（uuuu-MM-dd HH:mm）：" + DateUtils.parseLocalDateTime(yyyyMMddHHmm, zoneOffset));
    println("LocalDateTime 指定时区预定格式（uuuu-MM-dd）：" + DateUtils.parseLocalDateTime(yyyyMMdd, zoneOffset));
    println("LocalDateTime 指定时区预定格式（uuuu-MM）：" + DateUtils.parseLocalDateTime(yyyyMM, zoneOffset));
    println("LocalDateTime 指定时区预定格式（HH:mm:ss）：" + DateUtils.parseLocalDateTime(HHmmss, zoneOffset));
    println("LocalDateTime 指定时区预定格式（HH:mm）：" + DateUtils.parseLocalDateTime(HHmm, zoneOffset));
    println("——————————————————");
    println("LocalDateTime 解析时间戳：" + DateUtils.parseLocalDateTime(currentTimeMillis));
    println("LocalDateTime 指定格式" + DateUtils.parseLocalDateTime(customSource, customPattern));
    println("LocalDateTime 预定格式（uuuu-MM-dd HH:mm:ss）：" + DateUtils.parseLocalDateTime(yyyyMMddHHmmss));
    println("LocalDateTime 预定格式（uuuu-MM-dd HH:mm）：" + DateUtils.parseLocalDateTime(yyyyMMddHHmm));
    println("LocalDateTime 预定格式（uuuu-MM-dd）：" + DateUtils.parseLocalDateTime(yyyyMMdd));
    println("LocalDateTime 预定格式（uuuu-MM）：" + DateUtils.parseLocalDateTime(yyyyMM));
    println("LocalDateTime 预定格式（HH:mm:ss）：" + DateUtils.parseLocalDateTime(HHmmss));
    println("LocalDateTime 预定格式（HH:mm）：" + DateUtils.parseLocalDateTime(HHmm));
    println("——————————————————");
    println("LocalDate 解析时间戳指定时区：" + DateUtils.parseLocalDate(currentTimeMillis, zoneOffset));
    println("LocalDate 指定时区和格式" + DateUtils.parseLocalDate(customSource, zoneOffset, customPattern));
    println("LocalDate 指定时区预定格式（uuuu-MM-dd）：" + DateUtils.parseLocalDate(yyyyMMdd, zoneOffset));
    println("LocalDate 指定时区预定格式（uuuu-MM）：" + DateUtils.parseLocalDate(yyyyMM, zoneOffset));
    println("LocalDate 指定时区预定格式（HH:mm:ss）：" + DateUtils.parseLocalDateTime(HHmmss, zoneOffset));
    println("LocalDate 指定时区预定格式（HH:mm）：" + DateUtils.parseLocalDateTime(HHmm, zoneOffset));
    println("——————————————————");
    println("LocalDate 解析时间戳：" + DateUtils.parseLocalDate(currentTimeMillis));
    println("LocalDate 指定格式" + DateUtils.parseLocalDate(customSource, customPattern));
    println("LocalDate 预定格式（uuuu-MM-dd）：" + DateUtils.parseLocalDate(yyyyMMdd));
    println("LocalDate 预定格式（uuuu-MM）：" + DateUtils.parseLocalDate(yyyyMM));
    println("LocalDate 预定格式（HH:mm:ss）：" + DateUtils.parseLocalDateTime(HHmmss));
    println("LocalDate 预定格式（HH:mm）：" + DateUtils.parseLocalDateTime(HHmm));
    println("——————————————————");
    println("LocalTime 解析时间戳指定时区：" + DateUtils.parseLocalTime(currentTimeMillis, zoneOffset));
    println("LocalTime 指定时区和格式" + DateUtils.parseLocalTime(customSource, zoneOffset, customPattern));
    println("LocalTime 指定时区预定格式（HH:mm:ss）：" + DateUtils.parseLocalTime(HHmmss, zoneOffset));
    println("LocalTime 指定时区预定格式（HH:mm）：" + DateUtils.parseLocalTime(HHmm, zoneOffset));
    println("——————————————————");
    println("LocalTime 解析时间戳：" + DateUtils.parseLocalTime(currentTimeMillis));
    println("LocalTime 指定格式" + DateUtils.parseLocalTime(customSource, customPattern));
    println("LocalTime 预定格式（HH:mm:ss）：" + DateUtils.parseLocalTime(HHmmss));
    println("LocalTime 预定格式（HH:mm）：" + DateUtils.parseLocalTime(HHmm));
    println("——————————————————");
    println("Date 解析时间戳指定时区：" + DateUtils.parseDate(currentTimeMillis, zoneOffset));
    println("Date 指定时区和格式" + DateUtils.parseDate(customSource, zoneOffset, customPattern));
    println("Date 指定时区预定格式（uuuu-MM-dd HH:mm:ss）：" + DateUtils.parseDate(yyyyMMddHHmmss, zoneOffset));
    println("Date 指定时区预定格式（uuuu-MM-dd HH:mm）：" + DateUtils.parseDate(yyyyMMddHHmm, zoneOffset));
    println("Date 指定时区预定格式（uuuu-MM-dd）：" + DateUtils.parseDate(yyyyMMdd, zoneOffset));
    println("Date 指定时区预定格式（uuuu-MM）：" + DateUtils.parseDate(yyyyMM, zoneOffset));
    println("Date 指定时区预定格式（HH:mm:ss）：" + DateUtils.parseDate(HHmmss, zoneOffset));
    println("Date 指定时区预定格式（HH:mm）：" + DateUtils.parseDate(HHmm, zoneOffset));
    println("——————————————————");
    println("Date 解析时间戳：" + DateUtils.parseDate(currentTimeMillis));
    println("Date 指定格式" + DateUtils.parseDate(customSource, customPattern));
    println("Date 预定格式（uuuu-MM-dd HH:mm:ss）：" + DateUtils.parseDate(yyyyMMddHHmmss));
    println("Date 预定格式（uuuu-MM-dd HH:mm）：" + DateUtils.parseDate(yyyyMMddHHmm));
    println("Date 预定格式（uuuu-MM-dd）：" + DateUtils.parseDate(yyyyMMdd));
    println("Date 预定格式（uuuu-MM）：" + DateUtils.parseDate(yyyyMM));
    println("Date 预定格式（HH:mm:ss）：" + DateUtils.parseDate(HHmmss));
    println("Date 预定格式（HH:mm）：" + DateUtils.parseDate(HHmm));
  }

  @DisplayName("withZoneInstant：转换时区")
  @Test
  void withZoneInstant() {
    // 旧时区和新时区之间差 10 小时，所以小时数会 +10
    ZoneOffset oldZoneOffset = ZoneOffset.UTC;
    // 默认时区（此处为 8）和新时区之间差 2 小时，所以小时数会 +2
    ZoneOffset newZoneOffset = ZoneOffset.ofHours(10);

    println("LocalDateTime 旧时区转新时区：" + DateUtils.withZoneInstant(nowLocalDateTime, oldZoneOffset, newZoneOffset));
    println("LocalDateTime 默认时区转新时区：" + DateUtils.withZoneInstant(nowLocalDateTime, newZoneOffset));
    println("LocalDate 旧时区转新时区：" + DateUtils.withZoneInstant(nowLocalDate, oldZoneOffset, newZoneOffset));
    println("LocalDate 默认时区转新时区：" + DateUtils.withZoneInstant(nowLocalDate, newZoneOffset));
    println("LocalTime 旧时区转新时区：" + DateUtils.withZoneInstant(nowLocalTime, oldZoneOffset, newZoneOffset));
    println("LocalTime 默认时区转新时区：" + DateUtils.withZoneInstant(nowLocalTime, newZoneOffset));
    println("Date 旧时区转新时区：" + DateUtils.withZoneInstant(nowDate, oldZoneOffset, newZoneOffset));
    println("Date 默认时区转新时区：" + DateUtils.withZoneInstant(nowDate, newZoneOffset));
  }

  @DisplayName("now：当前时间字符串")
  @Test
  void now() {
    println("当前时间字符串：" + DateUtils.now());
    println("指定格式的当前时间字符串：" + DateUtils.now(customPattern));
    println("指定时区的当前时间字符串：" + DateUtils.now(ZoneOffset.ofHours(10)));
    println("指定时区和格式的当前时间字符串：" + DateUtils.now(ZoneOffset.ofHours(10), customPattern));
  }

  @DisplayName("min：指定级别的最小时间")
  @Test
  void min() {
    println("今天开始时间：" + DateUtils.min(nowLocalDateTime, ChronoField.HOUR_OF_DAY, ChronoField.MINUTE_OF_HOUR, ChronoField.SECOND_OF_MINUTE, ChronoField.NANO_OF_SECOND));
    println("今天开始时间 1：" + DateUtils.min(nowLocalDateTime, ChronoField.NANO_OF_DAY));
    println("——————————————————");
    LocalDateTime localDateTime = nowLocalDateTime.withYear(2020).withMonth(2);
    println("2020 年 2 月最后一天：" + DateUtils.max(localDateTime, ChronoField.DAY_OF_MONTH));
  }

  @DisplayName("today：获取今天开始或结束时间")
  @Test
  void today() {
    ZoneOffset zoneOffset = ZoneOffset.ofHours(-15);

    println("指定时区的昨天开始时间：" + DateUtils.todayMinTime(zoneOffset, -1L));
    println("指定时区今天开始时间：" + DateUtils.todayMinTime(zoneOffset));
    println("昨天开始时间：" + DateUtils.todayMinTime(-1L));
    println("今天开始时间：" + DateUtils.todayMinTime());

    println("指定时区和格式的昨天开始时间字符串：" + DateUtils.todayMinTimeStr(zoneOffset, DatePattern.UUUU_MM_DD_HH_MM, -1L));
    println("指定时区和格式的今天开始时间字符串：" + DateUtils.todayMinTimeStr(zoneOffset, DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("指定格式的今天开始时间字符串：" + DateUtils.todayMinTimeStr(DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("今天开始时间字符串：" + DateUtils.todayMinTimeStr());

    println("——————————————————");

    println("指定时区的昨天结束时间：" + DateUtils.todayMaxTime(zoneOffset, -1L));
    println("指定时区今天结束时间：" + DateUtils.todayMaxTime(zoneOffset));
    println("昨天结束时间：" + DateUtils.todayMaxTime(-1L));
    println("今天结束时间：" + DateUtils.todayMaxTime());

    println("指定时区和格式的昨天结束时间字符串：" + DateUtils.todayMaxTimeStr(zoneOffset, DatePattern.UUUU_MM_DD_HH_MM, -1L));
    println("指定时区和格式的今天结束时间字符串：" + DateUtils.todayMaxTimeStr(zoneOffset, DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("指定格式的今天结束时间字符串：" + DateUtils.todayMaxTimeStr(DatePattern.UUUU_MM_DD_HH_MM_SS));
    println("今天结束时间字符串：" + DateUtils.todayMaxTimeStr());
  }

  @DisplayName("plusOrMinus：加减时间")
  @Test
  void plusOrMinus() {
    println("加一天 1 小时：" + DateUtils.plusOrMinus(nowLocalDateTime, 1, ChronoUnit.DAYS, ChronoUnit.HOURS));
    println("减一天：" + DateUtils.plusOrMinus(nowLocalDateTime, -(1000 * 60 * 60 * 24)));
  }

  @DisplayName("交集差集并集")
  @Test
  void IntersectionAndDifferenceSetsAndConcatenation() {
    println("两个时间段（y1 == x2）是否交集：" + DateUtils.isIntersection(DateUtils.todayMinTime(), nowLocalDateTime, nowLocalDateTime, DateUtils.todayMaxTime()));
    println("两个时间段（y1 < x2）是否交集：" + DateUtils.isIntersection(DateUtils.todayMinTime(), nowLocalDateTime.minusNanos(1), nowLocalDateTime, DateUtils.todayMaxTime()));
    LocalDateTime[][] differenceSetsByIntersection = DateUtils.getDifferenceSetsByIntersection(DateUtils.todayMinTime(), nowLocalDateTime, nowLocalDateTime, DateUtils.todayMaxTime(), 1, ChronoUnit.SECONDS);
    println("交集的差集（y1 - 1 秒，避免有交集）：[" + differenceSetsByIntersection[0][0] + ", " + differenceSetsByIntersection[0][1] + "], [" + differenceSetsByIntersection[1][0] + ", " + differenceSetsByIntersection[1][1] + "]");
    differenceSetsByIntersection = DateUtils.getDifferenceSetsByIntersection(DateUtils.todayMinTime(), nowLocalDateTime, nowLocalDateTime, DateUtils.todayMaxTime(), 1, 1, ChronoUnit.SECONDS);
    println("交集的差集（y1 - 1 秒，避免有交集）：[" + differenceSetsByIntersection[0][0] + ", " + differenceSetsByIntersection[0][1] + "], [" + differenceSetsByIntersection[1][0] + ", " + differenceSetsByIntersection[1][1] + "]");
    differenceSetsByIntersection = DateUtils.getDifferenceSetsByIntersection(DateUtils.todayMinTime(), nowLocalDateTime, nowLocalDateTime, DateUtils.todayMaxTime(), 2, 1, ChronoUnit.SECONDS);
    println("交集的差集（x2 + 1 秒，避免有交集）：[" + differenceSetsByIntersection[0][0] + ", " + differenceSetsByIntersection[0][1] + "], [" + differenceSetsByIntersection[1][0] + ", " + differenceSetsByIntersection[1][1] + "]");
  }
}
