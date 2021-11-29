package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

@Slf4j
@DisplayName("时间工具类测试")
public class DateUtilsTest {

  public static void main(String[] args) {

  }

  @DisplayName("探寻 ChronoField")
  @Test
  void testChronoField() {
    String indent = "\t\t\t\t\t\t\t\t\t\t\t\t\t";
    LocalDateTime now = LocalDateTime.now();
    // 时代：公元前，相当于当前时间的负数
    System.out.println(now.with(ChronoField.ERA, 0) + indent.replaceFirst("\t", "") + "时代：公元前");
    // 时代：公元，即当前时间
    System.out.println(now.with(ChronoField.ERA, 1) + indent + "时代：公元");
    // 公元前所属年：以当前时间为基础，年修改为公元前 2 年，结果 -0001-10-01T02:30:32.723 加上当前时间的月份往后 10-01T02:30:32.723 为 2 年
    System.out.println(now.with(ChronoField.ERA, 0).with(ChronoField.YEAR_OF_ERA, 2) + indent.replaceFirst("\t", "") + "公元前所属年");
    // 公元后所属年：以当前年月为基础，年修改为 2 年
    System.out.println(now.with(ChronoField.ERA, 1).with(ChronoField.YEAR_OF_ERA, 2) + indent + "公元后所属年");
    // 年
    System.out.println(now.with(ChronoField.YEAR, 2020) + indent + "年");
    // 预期月，从 0 年开始计算月（从 0 开始），2021 年 10 月的值为 2021 * 12 + 10 - 1
    System.out.println(now.with(ChronoField.PROLEPTIC_MONTH, 0) + indent + "预期月");
    // 年的月
    System.out.println(now.with(ChronoField.MONTH_OF_YEAR, 9) + indent + "年的月");
    // 年的对齐周：年的第一天为第一周的第一天
    System.out.println(now.with(ChronoField.ALIGNED_WEEK_OF_YEAR, 2) + indent + "年的对齐周");
    // 月的对齐周：月的第一天为此月第一周的第一天
    System.out.println(now.with(ChronoField.ALIGNED_WEEK_OF_MONTH, 2) + indent + "月的对齐周");
    // 年的对齐周的天
    System.out.println(now.with(ChronoField.ALIGNED_WEEK_OF_YEAR, 2).with(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR, 5) + indent + "年的对齐周的天");
    // 月的对齐周的天
    System.out.println(now.with(ChronoField.ALIGNED_WEEK_OF_MONTH, 2).with(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH, 5) + indent + "月的对齐周的天");
    // 年的天
    System.out.println(now.with(ChronoField.DAY_OF_YEAR, 1) + indent + "年的天");
    // 月的天
    System.out.println(now.with(ChronoField.DAY_OF_MONTH, 1) + indent + "月的天");
    // 周的天
    System.out.println(now.with(ChronoField.DAY_OF_WEEK, 1) + indent + "周的天");
    // 以 1970-01-01 为 0 开始的天（忽略偏移量和时区）
    System.out.println(now.with(ChronoField.EPOCH_DAY, 1) + indent + "以 1970-01-01 为 0 开始的天（忽略偏移量和时区）");
    // 上午（0-12）
    System.out.println(now.with(ChronoField.AMPM_OF_DAY, 0) + indent + "上午（0-12）");
    // 下午（13-23）
    System.out.println(now.with(ChronoField.AMPM_OF_DAY, 1) + indent + "下午（13-23）");
    // 上午或下午的小时，以当前 AMPM 为准，从 0 开始
    System.out.println(now.with(ChronoField.HOUR_OF_AMPM, 0) + indent + "上午或下午的小时");
    // 上午的小时
    System.out.println(now.with(ChronoField.AMPM_OF_DAY, 0).with(ChronoField.HOUR_OF_AMPM, 0) + indent + "上午的小时");
    // 下午的小时
    System.out.println(now.with(ChronoField.AMPM_OF_DAY, 1).with(ChronoField.HOUR_OF_AMPM, 0) + indent + "下午的小时");
    // 12 小时制，以当前 AMPM 为准，从 1 开始
    System.out.println(now.with(ChronoField.CLOCK_HOUR_OF_AMPM, 1) + indent + "12 小时制");
    // 上午的 12 小时制小时
    System.out.println(now.with(ChronoField.AMPM_OF_DAY, 0).with(ChronoField.CLOCK_HOUR_OF_AMPM, 1) + indent + "上午的 12 小时制小时");
    // 下午的 12 小时制小时
    System.out.println(now.with(ChronoField.AMPM_OF_DAY, 1).with(ChronoField.CLOCK_HOUR_OF_AMPM, 1) + indent + "下午的 12 小时制小时");
    // 天的小时
    System.out.println(now.with(ChronoField.HOUR_OF_DAY, 0) + indent + "天的小时");
    // 天的分钟
    System.out.println(now.with(ChronoField.MINUTE_OF_DAY, 1) + indent + "天的分钟");
    // 小时的分钟
    System.out.println(now.with(ChronoField.MINUTE_OF_HOUR, 1) + indent + "小时的分钟");
    // 天的秒
    System.out.println(now.with(ChronoField.SECOND_OF_DAY, 1) + indent + "天的秒");
    // 分钟的秒
    System.out.println(now.with(ChronoField.SECOND_OF_MINUTE, 1) + indent + "分钟的秒");
    // 以 1970-01-01T00:00Z (ISO) 为 0 开始的秒，必须和时区结合使用（+时区小时）
    System.out.println(now.atZone(ZoneId.systemDefault()).with(ChronoField.INSTANT_SECONDS, 1) + "\t\t以 1970-01-01T00:00Z (ISO) 为 0 开始的秒，必须和时区结合使用（+时区小时）");
    // 天的毫秒
    System.out.println(now.with(ChronoField.MILLI_OF_DAY, 1) + indent + "天的毫秒");
    // 秒的毫秒
    System.out.println(now.with(ChronoField.MILLI_OF_SECOND, 1) + indent + "秒的毫秒");
    // 天的微秒
    System.out.println(now.with(ChronoField.MICRO_OF_DAY, 1) + indent.replaceFirst("\t\t", "") + "天的微秒");
    // 秒的微秒
    System.out.println(now.with(ChronoField.MICRO_OF_SECOND, 1) + indent.replaceFirst("\t\t", "") + "秒的微秒");
    // 天的纳秒
    System.out.println(now.with(ChronoField.NANO_OF_DAY, 1) + indent.replaceFirst("\t\t\t", "") + "天的纳秒");
    // 秒的纳秒
    System.out.println(now.with(ChronoField.NANO_OF_SECOND, 1) + indent.replaceFirst("\t\t\t", "") + "秒的纳秒");
  }

  @DisplayName("getDefaultFormatter")
  @Test
  void getDefaultFormatter() {
    // 格式中不含年
    DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("MM-dd").toFormatter();
    // 获取年报错：Unsupported field: Year
    try {
      dateTimeFormatter.parse("08-08").get(ChronoField.YEAR);
    } catch (Exception e) {
      log.warn(e.getMessage());
    }

    // 使用 getDefaultFormatter() 时会赋默认值
    dateTimeFormatter = DateUtils.getDefaultFormatter("MM-dd", Locale.ENGLISH, ZoneId.systemDefault());
    System.out.println(dateTimeFormatter.parse("08-08").get(ChronoField.YEAR));

    // 如果格式中已存在年，说明被转换的内容中已存在年，则不需要赋默认值
    dateTimeFormatter = DateUtils.getDefaultFormatter("yyyy-MM-dd", Locale.ENGLISH, ZoneId.systemDefault());
    System.out.println(dateTimeFormatter.parse("2021-08-08").get(ChronoField.YEAR));
  }
}
