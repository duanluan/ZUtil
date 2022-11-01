package top.zhogjianhao.date;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.text.WordUtils;
import top.zhogjianhao.CollectionUtils;
import top.zhogjianhao.StringUtils;
import top.zhogjianhao.date.constant.*;
import top.zhogjianhao.regex.RegExUtils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * 时间工具类
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

  /**
   * 获取时间格式化构造器，并给不同时间级别赋默认值
   *
   * @param pattern       格式
   * @param fieldValueMap 时间类型和值
   * @return 时间格式构造器
   */
  public static DateTimeFormatterBuilder getFormatterBuilder(@NonNull final String pattern, final Map<TemporalField, Long> fieldValueMap) {
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    // 根据格式创建时间格式化构造器
    DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder().appendPattern(pattern);
    String lowercasePattern = pattern.toLowerCase();

    if (MapUtils.isNotEmpty(fieldValueMap)) {
      // 如果格式中存在特定时间级别的字符，则不赋默认值，因为被转换的值中理应已经存在对应时间级别的值了
      TemporalField maybeExistTemporalField = ChronoField.YEAR_OF_ERA;
      if (pattern.contains("u") && fieldValueMap.containsKey(maybeExistTemporalField)) {
        fieldValueMap.remove(maybeExistTemporalField);
      }
      maybeExistTemporalField = ChronoField.YEAR;
      if (lowercasePattern.contains("y") && fieldValueMap.containsKey(maybeExistTemporalField)) {
        fieldValueMap.remove(maybeExistTemporalField);
      }
      maybeExistTemporalField = ChronoField.MONTH_OF_YEAR;
      if ((pattern.contains("M") || pattern.contains("L")) && fieldValueMap.containsKey(maybeExistTemporalField)) {
        fieldValueMap.remove(maybeExistTemporalField);
      }
      maybeExistTemporalField = ChronoField.DAY_OF_MONTH;
      if ((pattern.contains("d")) && fieldValueMap.containsKey(maybeExistTemporalField)) {
        fieldValueMap.remove(maybeExistTemporalField);
      }
      maybeExistTemporalField = ChronoField.HOUR_OF_DAY;
      if ((lowercasePattern.contains("h") || lowercasePattern.contains("k")) && fieldValueMap.containsKey(maybeExistTemporalField)) {
        fieldValueMap.remove(maybeExistTemporalField);
      }
    }

    for (Map.Entry<TemporalField, Long> fieldValueEntry : fieldValueMap.entrySet()) {
      formatterBuilder.parseDefaulting(fieldValueEntry.getKey(), fieldValueEntry.getValue());
    }
    return formatterBuilder;
  }

  /**
   * 获取默认的时间格式器（默认严格模式、区域英语），对应时间级别没有就赋默认值：0000-01-01 00:00:00.00000000
   *
   * @param pattern 格式
   * @param locale  区域
   * @param zoneId  时区
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NonNull final String pattern, final Locale locale, final ZoneId zoneId) {
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    Map<TemporalField, Long> fieldValueMap = new HashMap<>();
    fieldValueMap.put(ChronoField.YEAR_OF_ERA, 1L);
    fieldValueMap.put(ChronoField.YEAR, 0L);
    fieldValueMap.put(ChronoField.MONTH_OF_YEAR, 1L);
    fieldValueMap.put(ChronoField.DAY_OF_MONTH, 1L);
    fieldValueMap.put(ChronoField.HOUR_OF_DAY, 0L);
    DateTimeFormatter dateTimeFormatter;
    DateTimeFormatterBuilder formatterBuilder = getFormatterBuilder(pattern, fieldValueMap);
    if (locale != null) {
      dateTimeFormatter = formatterBuilder.toFormatter(DateFeature.get(locale));
    } else {
      Locale locale1 = DateFeature.getLocale();
      if (locale1 != null) {
        dateTimeFormatter = formatterBuilder.toFormatter(locale1);
      } else {
        dateTimeFormatter = formatterBuilder.toFormatter();
      }
    }
    dateTimeFormatter.withResolverStyle(DateFeature.get(DateConstant.DEFAULT_RESOLVER_STYLE));
    if (zoneId != null) {
      dateTimeFormatter = dateTimeFormatter.withZone(DateFeature.get(zoneId));
    }
    return dateTimeFormatter;
  }

  /**
   * 获取默认的时间格式器（默认严格模式、区域英语），对应时间级别没有就赋默认值：0000-01-01 00:00:00.00000000
   *
   * @param pattern 格式
   * @param locale  区域
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NonNull final String pattern, @NonNull final Locale locale) {
    return getDefaultFormatter(pattern, locale, null);
  }

  /**
   * 获取默认的时间格式器（默认严格模式、区域英语），对应时间级别没有就赋默认值：0000-01-01 00:00:00.00000000
   *
   * @param pattern 格式
   * @param zoneId  时区
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NonNull final String pattern, @NonNull final ZoneId zoneId) {
    return getDefaultFormatter(pattern, null, zoneId);
  }

  /**
   * 获取默认的时间格式器（默认严格模式、区域英语），对应时间级别没有就赋默认值：0000-01-01 00:00:00.00000000
   *
   * @param pattern 格式
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NonNull final String pattern) {
    return getDefaultFormatter(pattern, null, null);
  }

  /**
   * 转换需要格式化的字符串，比如英文月份转换为首字母大写
   *
   * @param source  被转换的字符串
   * @param pattern 转换格式
   * @return 转换后的字符串
   */
  private static String convertByPattern(@NonNull String source, @NonNull final String pattern) {
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    // 如果格式为英文月份，转换字符串为首字母大写
    int mmmIndex = pattern.indexOf("MMM");
    if (mmmIndex != -1) {
      // 获取 MMM 对应在字符串的位置的内容
      String mmm = source.substring(mmmIndex, mmmIndex + 3);
      // 首字母大写
      source = source.replaceAll(mmm, WordUtils.capitalize(mmm.toLowerCase()));
    }
    return source;
  }

  /**
   * 转换数字月到短文本月
   *
   * @param monthNumber 数字月
   * @param locale      区域
   * @return 短文本月
   */
  public static String convertMonthShortText(@NonNull final String monthNumber, @NonNull final Locale locale) {
    if (StringUtils.isBlank(monthNumber)) {
      throw new IllegalArgumentException("MonthNumber: should not be all blank");
    }
    int monthNumberLen = monthNumber.length();
    if (monthNumberLen != 1 && monthNumberLen != 2) {
      throw new IllegalArgumentException("MonthNumber: Length should be 1 or 2");
    }
    try {
      FastDateFormat formatMmm;
      FastDateFormat formatMm;
      if (Locale.US.equals(locale)) {
        formatMmm = DateFormat.MMM_US;
        formatMm = DateFormat.MM_US;
      } else if (Locale.SIMPLIFIED_CHINESE.equals(locale)) {
        formatMmm = DateFormat.MMM_ZH_CN;
        formatMm = DateFormat.MM_ZH_CN;
      } else if (Locale.TRADITIONAL_CHINESE.equals(locale)) {
        formatMmm = DateFormat.MMM_ZH_TW;
        formatMm = DateFormat.MM_ZH_TW;
      } else {
        formatMmm = FastDateFormat.getInstance("MMM", DateFeature.get(locale));
        formatMm = FastDateFormat.getInstance("MM", DateFeature.get(locale));
      }
      return formatMmm.format(formatMm.parse(monthNumber));
    } catch (ParseException e) {
      log.warn(e.getMessage());
      return null;
    }
  }

  /**
   * 转换数字月到英文短文本月
   *
   * @param monthNumber 数字月
   * @return 英文短文本月
   */
  public static String convertMonthShortText(@NonNull final String monthNumber) {
    return convertMonthShortText(monthNumber, DateConstant.DEFAULT_LOCALE);
  }

  /**
   * 转换数字月到文本月
   *
   * @param monthNumber 数字月
   * @param locale      区域
   * @return 文本月
   */
  public static String convertMonthText(@NonNull final String monthNumber, @NonNull final Locale locale) {
    if (StringUtils.isBlank(monthNumber)) {
      throw new IllegalArgumentException("MonthNumber: should not be all blank");
    }
    int monthNumberLen = monthNumber.length();
    if (monthNumberLen != 1 && monthNumberLen != 2) {
      throw new IllegalArgumentException("MonthNumber: Length should be 1 or 2");
    }
    try {
      FastDateFormat formatMmm;
      FastDateFormat formatMm;
      if (Locale.US.equals(locale)) {
        formatMmm = DateFormat.MMMM_US;
        formatMm = DateFormat.MM_US;
      } else if (Locale.SIMPLIFIED_CHINESE.equals(locale)) {
        formatMmm = DateFormat.MMMM_ZH_CN;
        formatMm = DateFormat.MM_ZH_CN;
      } else if (Locale.TRADITIONAL_CHINESE.equals(locale)) {
        formatMmm = DateFormat.MMMM_ZH_TW;
        formatMm = DateFormat.MM_ZH_TW;
      } else {
        formatMmm = FastDateFormat.getInstance("MMMM", DateFeature.get(locale));
        formatMm = FastDateFormat.getInstance("MM", DateFeature.get(locale));
      }
      return formatMmm.format(formatMm.parse(monthNumber));
    } catch (ParseException e) {
      log.warn(e.getMessage());
      return null;
    }
  }

  /**
   * 转换数字月到英文文本月
   *
   * @param monthNumber 数字月
   * @return 英文文本月
   */
  public static String convertMonthText(@NonNull final String monthNumber) {
    return convertMonthText(monthNumber, DateConstant.DEFAULT_LOCALE);
  }

  /**
   * 格式化为指定时区和格式的字符串
   *
   * @param temporal 时间对象
   * @param zoneId   时区
   * @param pattern  格式
   * @return 指定时区和格式的字符串
   */
  public static String format(@NonNull final Temporal temporal, final ZoneId zoneId, @NonNull final String pattern) {
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    DateTimeFormatter dateTimeFormatter;
    // 使用已存在的格式转换器
    switch (pattern) {
      case DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN:
        dateTimeFormatter = DateFormatter.YYYY_MM_DD_HH_MM_SS;
        break;
      case DateConstant.DEFAULT_LOCAL_DATE_PATTERN:
        dateTimeFormatter = DateFormatter.YYYY_MM_DD;
        break;
      case DateConstant.DEFAULT_LOCAL_TIME_PATTERN:
        dateTimeFormatter = DateFormatter.HH_MM_SS;
        break;
      default:
        dateTimeFormatter = getDefaultFormatter(pattern);
        break;
    }
    ZoneId zoneId1 = DateFeature.get(zoneId);
    if (zoneId1 != null) {
      if (temporal instanceof LocalDateTime || temporal instanceof LocalDate || temporal instanceof LocalTime) {
        // 因为 LocalDate 和 LocalTime 不存在时区信息，所以先根据当前时间补全为 LocalDateTime
        LocalDateTime localDateTime;
        if (temporal instanceof LocalDateTime) {
          localDateTime = ((LocalDateTime) temporal);
        } else if (temporal instanceof LocalDate) {
          localDateTime = ((LocalDate) temporal).atTime(LocalTime.now());
        } else {
          localDateTime = ((LocalTime) temporal).atDate(LocalDate.now());
        }
        return localDateTime.atZone(DateConstant.SYSTEM_ZONE_ID).withZoneSameInstant(zoneId1).format(dateTimeFormatter);
      } else if (temporal instanceof ZonedDateTime) {
        return ((ZonedDateTime) temporal).withZoneSameInstant(zoneId1).format(dateTimeFormatter);
      } else {
        throw new IllegalArgumentException("Temporal: should be ZonedDateTime, LocalDateTime, LocalDate, or LocalTime");
      }
    }
    return dateTimeFormatter.format(temporal);
  }

  /**
   * 格式化为指定时区和格式的字符串，格式根据时间对象的类型判断
   * <p>
   * LocalDateTime、ZonedDateTime：DateConstant.DEFAULT_LOCAL_DATE_PATTERN<br>
   * LocalDate：DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN<br>
   * LocalTime：DateConstant.DEFAULT_LOCAL_TIME_PATTERN
   *
   * @param temporal 时间对象
   * @param zoneId   时区
   * @return 指定时区和格式的字符串
   */
  public static String format(@NonNull final Temporal temporal, final ZoneId zoneId) {
    // 根据不同的时间类型获取不同的默认格式
    String pattern;
    if (temporal instanceof LocalDateTime || temporal instanceof ZonedDateTime) {
      pattern = DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN;
    } else if (temporal instanceof LocalDate) {
      pattern = DateConstant.DEFAULT_LOCAL_DATE_PATTERN;
    } else if (temporal instanceof LocalTime) {
      pattern = DateConstant.DEFAULT_LOCAL_TIME_PATTERN;
    } else {
      throw new IllegalArgumentException("Temporal: should be ZonedDateTime, LocalDateTime, LocalDate, or LocalTime");
    }
    return format(temporal, zoneId, pattern);
  }

  /**
   * 格式化为指定格式的字符串
   *
   * @param temporal 时间对象
   * @param pattern  格式
   * @return 指定格式的字符串
   */
  public static String format(@NonNull final Temporal temporal, @NonNull final String pattern) {
    return format(temporal, null, pattern);
  }

  /**
   * 格式化为指定格式的字符串，格式根据时间对象的类型判断
   * <p>
   * LocalDateTime、ZonedDateTime：DateConstant.DEFAULT_LOCAL_DATE_PATTERN<br>
   * LocalDate：DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN<br>
   * LocalTime：DateConstant.DEFAULT_LOCAL_TIME_PATTERN
   *
   * @param temporal 时间对象
   * @return 指定格式的字符串
   */
  public static String format(@NonNull final Temporal temporal) {
    return format(temporal, (ZoneId) null);
  }

  /**
   * 格式化为指定时区和格式的字符串
   *
   * @param date    时间对象
   * @param zoneId  时区
   * @param pattern 格式
   * @return 指定时区和格式的字符串
   */
  public static String format(@NonNull final Date date, final ZoneId zoneId, @NonNull final String pattern) {
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    if (zoneId != null) {
      ZonedDateTime zonedDateTime = date.toInstant().atZone(DateConstant.SYSTEM_ZONE_ID);
      zonedDateTime = zonedDateTime.withZoneSameInstant(DateFeature.get(zoneId));
      return format(zonedDateTime.toLocalDateTime(), pattern);
    } else {
      FastDateFormat format = null;
      // 使用已存在的格式转换器
      switch (pattern) {
        case DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN:
          format = DateFormat.YYYY_MM_DD_HH_MM_SS;
          break;
        case DateConstant.DEFAULT_LOCAL_DATE_PATTERN:
          format = DateFormat.YYYY_MM_DD;
          break;
        case DateConstant.DEFAULT_LOCAL_TIME_PATTERN:
          format = DateFormat.HH_MM_SS;
          break;
      }
      if (format == null) {
        format = FastDateFormat.getInstance(pattern);
      }
      return format.format(date);
    }
  }

  /**
   * 格式化为指定时区的字符串
   *
   * @param date   时间对象
   * @param zoneId 时区
   * @return 指定时区的字符串
   */
  public static String format(@NonNull final Date date, @NonNull final ZoneId zoneId) {
    return format(date, zoneId, DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN);
  }

  /**
   * 格式化为指定格式的字符串
   *
   * @param date    时间对象
   * @param pattern 格式
   * @return 指定格式的字符串
   */
  public static String format(@NonNull final Date date, @NonNull final String pattern) {
    return format(date, null, pattern);
  }

  /**
   * 格式化为 DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN 的字符串
   *
   * @param date 时间对象
   * @return DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN 格式的字符串
   */
  public static String format(@NonNull final Date date) {
    return format(date, DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN);
  }

  /**
   * 格式化为指定时区和格式的字符串
   *
   * @param epochMilli 时间戳
   * @param zoneId     时区
   * @param pattern    格式
   * @return 指定时区和格式的字符串
   */
  public static String format(final long epochMilli, final ZoneId zoneId, @NonNull final String pattern) {
    return format(new Date(epochMilli), zoneId, pattern);
  }

  /**
   * 格式化为 DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN 的字符串
   *
   * @param epochMilli 时间戳
   * @param zoneId     时区
   * @return 指定时区的字符串
   */
  public static String format(final long epochMilli, @NonNull final ZoneId zoneId) {
    return format(epochMilli, zoneId, DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN);
  }

  /**
   * 格式化为指定时区和格式的字符串
   *
   * @param epochMilli 时间戳
   * @param pattern    格式
   * @return 指定时区和格式的字符串
   */
  public static String format(final long epochMilli, @NonNull final String pattern) {
    return format(epochMilli, null, pattern);
  }

  /**
   * 格式化为 DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN 的字符串
   *
   * @param epochMilli 时间戳
   * @return DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN 格式的字符串
   */
  public static String format(final long epochMilli) {
    return format(epochMilli, DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN);
  }

  /**
   * 格式化倒计时字符串
   *
   * @param epochMilli 时间戳
   * @param pattern    格式
   * @return 指定格式的倒计时字符串
   */
  public static String formatCountdown(long epochMilli, @NonNull String pattern) {
    if (epochMilli <= 0) {
      throw new IllegalArgumentException("EpochMilli: should be greater than 0");
    }
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    String lowerCasePattern = pattern.toLowerCase();

    if (pattern.contains("W")) {
      long week = epochMilli / DateDuration.WEEK_MILLIS;
      if (week > 0) {
        epochMilli -= week * DateDuration.WEEK_MILLIS;
      }

      // 1 位且前后都不是 W：1
      pattern = DateRegexPattern.WEEK_OF_MONTH.matcher(pattern).replaceAll(String.valueOf(week));
      if (pattern.contains("W")) {
        // 2 位且前后都不是 W：01
        pattern = DateRegexPattern.WEEK_OF_MONTH2.matcher(pattern).replaceAll(String.format("%02d", week));
      }
    }
    if (lowerCasePattern.contains("d")) {
      long dayOfMonth = epochMilli / DateDuration.DAY_OF_MONTH_MILLIS;
      if (dayOfMonth > 0) {
        epochMilli -= dayOfMonth * DateDuration.DAY_OF_MONTH_MILLIS;
      }

      pattern = DateRegexPattern.DAY_OF_MONTH.matcher(pattern).replaceAll(String.valueOf(dayOfMonth));
      if (pattern.contains("d")) {
        pattern = DateRegexPattern.DAY_OF_MONTH2.matcher(pattern).replaceAll(String.format("%02d", dayOfMonth));
      }
    }
    if (lowerCasePattern.contains("h") || lowerCasePattern.contains("k")) {
      long hour = epochMilli / DateDuration.HOUR_MILLIS;
      if (hour > 0) {
        epochMilli -= hour * DateDuration.HOUR_MILLIS;
      }

      if (pattern.contains("H")) {
        pattern = DateRegexPattern.HOUR_OF_DAY.matcher(pattern).replaceAll(String.valueOf(hour));
      }
      if (pattern.contains("H")) {
        pattern = DateRegexPattern.HOUR_OF_DAY2.matcher(pattern).replaceAll(String.format("%02d", hour));
      }
      if (pattern.contains("h")) {
        // h 为 (H+1)/2
        pattern = DateRegexPattern.CLOCK_HOUR_OF_AM_PM12.matcher(pattern).replaceAll(String.valueOf((hour + 1) / 2));
      }
      if (pattern.contains("h")) {
        pattern = DateRegexPattern.CLOCK_HOUR_OF_AM_PM12_2.matcher(pattern).replaceAll(String.format("%02d", (hour + 1) / 2));
      }
      if (pattern.contains("K")) {
        // K 为 H/2
        pattern = DateRegexPattern.HOUR_OF_AM_PM.matcher(pattern).replaceAll(String.valueOf(hour / 2));
      }
      if (pattern.contains("K")) {
        pattern = DateRegexPattern.HOUR_OF_AM_PM2.matcher(pattern).replaceAll(String.format("%02d", hour / 2));
      }
      if (pattern.contains("k")) {
        // k 为 H+1
        pattern = DateRegexPattern.CLOCK_HOUR_OF_AM_PM24.matcher(pattern).replaceAll(String.valueOf(hour + 1));
      }
      if (pattern.contains("k")) {
        pattern = DateRegexPattern.CLOCK_HOUR_OF_AM_PM24_2.matcher(pattern).replaceAll(String.format("%02d", hour + 1));
      }
    }
    if (pattern.contains("m")) {
      long minute = epochMilli / DateDuration.MINUTE_MILLIS;
      if (minute > 0) {
        epochMilli -= minute * DateDuration.MINUTE_MILLIS;
      }

      if (pattern.contains("m")) {
        pattern = DateRegexPattern.MINUTE_OF_HOUR.matcher(pattern).replaceAll(String.valueOf(minute));
      }
      if (pattern.contains("m")) {
        pattern = DateRegexPattern.MINUTE_OF_HOUR2.matcher(pattern).replaceAll(String.format("%02d", minute));
      }
    }
    if (pattern.contains("s")) {
      long second = epochMilli / DateDuration.SECOND_MILLIS;
      if (second > 0) {
        epochMilli -= second * DateDuration.SECOND_MILLIS;
      }

      if (pattern.contains("s")) {
        pattern = DateRegexPattern.SECOND_OF_MINUTE.matcher(pattern).replaceAll(String.valueOf(second));
      }
      if (pattern.contains("s")) {
        pattern = DateRegexPattern.SECOND_OF_MINUTE2.matcher(pattern).replaceAll(String.format("%02d", second));
      }
    }
    if (pattern.contains("S")) {
      long millis = epochMilli / DateDuration.MILLIS_1000;
      if (pattern.contains("S")) {
        pattern = DateRegexPattern.FRACTION_OF_SECOND3.matcher(pattern).replaceAll(String.format("%03d", millis));
      }
    }
    return pattern;
  }

  /**
   * 格式化倒计时字符串
   *
   * @param epochMilli       时间戳
   * @param isIgnoreZero     是否忽略为 0 的时间级别
   * @param weekSuffix       周后缀
   * @param dayOfMonthSuffix 天后缀
   * @param hourSuffix       小时后缀
   * @param minuteSuffix     分钟后缀
   * @param secondSuffix     秒后缀
   * @param millisSuffix     毫秒后缀
   * @return 指定格式的倒计时字符串
   */
  public static String formatCountdown(long epochMilli, final boolean isIgnoreZero, final String weekSuffix, final String dayOfMonthSuffix, final String hourSuffix, final String minuteSuffix, final String secondSuffix, final String millisSuffix) {
    String result = "";
    if (weekSuffix != null) {
      long week = epochMilli / DateDuration.WEEK_MILLIS;
      if (week > 0) {
        epochMilli -= week * DateDuration.WEEK_MILLIS;
      }
      if (week > 0 || !isIgnoreZero) {
        result += week + weekSuffix;
      }
    }
    if (dayOfMonthSuffix != null) {
      long dayOfMonth = epochMilli / DateDuration.DAY_OF_MONTH_MILLIS;
      if (dayOfMonth > 0) {
        epochMilli -= dayOfMonth * DateDuration.DAY_OF_MONTH_MILLIS;
      }
      if (dayOfMonth > 0 || !isIgnoreZero) {
        result += dayOfMonth + dayOfMonthSuffix;
      }
    }
    if (hourSuffix != null) {
      long hour = epochMilli / DateDuration.HOUR_MILLIS;
      if (hour > 0) {
        epochMilli -= hour * DateDuration.HOUR_MILLIS;
      }
      if (hour > 0 || !isIgnoreZero) {
        result += hour + hourSuffix;
      }
    }
    if (minuteSuffix != null) {
      long minute = epochMilli / DateDuration.MINUTE_MILLIS;
      if (minute > 0) {
        epochMilli -= minute * DateDuration.MINUTE_MILLIS;
      }
      if (minute > 0 || !isIgnoreZero) {
        result += minute + minuteSuffix;
      }
    }
    if (secondSuffix != null) {
      long second = epochMilli / DateDuration.SECOND_MILLIS;
      if (second > 0) {
        epochMilli -= second * DateDuration.SECOND_MILLIS;
      }
      if (second > 0 || !isIgnoreZero) {
        result += second + secondSuffix;
      }
    }
    if (millisSuffix != null) {
      long millis = epochMilli / DateDuration.MILLIS_1000;
      if (millis > 0 || !isIgnoreZero) {
        result += millis + millisSuffix;
      }
    }
    return result;
  }

  /**
   * 转换为指定时区的 Date 对象
   *
   * @param temporal 时间对象
   * @param zoneId   时区
   * @return 指定时区的 Date 对象
   */
  public static Date toDate(@NonNull final Temporal temporal, final ZoneId zoneId) {
    if (temporal instanceof LocalDateTime) {
      LocalDateTime localDateTime = ((LocalDateTime) temporal);
      final ZonedDateTime[] zonedDateTime = new ZonedDateTime[1];
      if (zoneId != null) {
        ZoneId zoneId1 = DateFeature.get(zoneId);
        // 设置时区为 当前偏移量 - (指定偏移量 - 当前偏移量)，比如 zoneId 偏移量为 10，理论上 toDate 后时间需要 +2，但是因为 Date.from 之后是反的，所以真实偏移量要为 6 才对，假设当前偏移量为 8，那 8 - (10 -8) = 6
        zoneId1.getRules().getOffset(localDateTime).query((TemporalQuery<Integer>) temporal1 -> {
          int systemZoneOffsetTotalSeconds = DateConstant.SYSTEM_ZONE_OFFSET.getTotalSeconds();
          zonedDateTime[0] = localDateTime.atZone(ZoneOffset.ofTotalSeconds(systemZoneOffsetTotalSeconds - (((ZoneOffset) temporal1).getTotalSeconds() - systemZoneOffsetTotalSeconds)));
          return null;
        });
      } else {
        zonedDateTime[0] = localDateTime.atZone(DateConstant.SYSTEM_ZONE_ID);
      }
      return Date.from(zonedDateTime[0].toInstant());
    } else if (temporal instanceof LocalDate) {
      return toDate(((LocalDate) temporal).atStartOfDay(), DateFeature.get(zoneId));
    } else if (temporal instanceof LocalTime) {
      //  遵循 Date 的默认规则，年为 1970
      return toDate(((LocalTime) temporal).atDate(LocalDate.of(DateFeature.getLazyMinDateYear(DateConstant.DEFAULT_MIN_DATE_YEAR).intValue(), 1, 1)), DateFeature.get(zoneId));
    } else if (temporal instanceof ZonedDateTime) {
      final ZonedDateTime[] zonedDateTime = {(ZonedDateTime) temporal};
      if (zoneId != null) {
        LocalDateTime localDateTime = zonedDateTime[0].toLocalDateTime();
        final int[] zonedDateTimeTotalSeconds = {0};
        // 设置时区为 zonedDateTime 的偏移量 - (指定偏移量 - zonedDateTime 的偏移量)，同理如上
        zonedDateTime[0].getZone().getRules().getOffset(localDateTime).query((TemporalQuery<Integer>) temporal1 -> {
          zonedDateTimeTotalSeconds[0] = ((ZoneOffset) temporal1).getTotalSeconds();
          return null;
        });
        ZoneId zoneId1 = DateFeature.get(zoneId);
        zoneId1.getRules().getOffset(localDateTime).query((TemporalQuery<Integer>) temporal1 -> {
          int zonedDateTimeTotalSecond = zonedDateTimeTotalSeconds[0];
          zonedDateTime[0] = localDateTime.atZone(ZoneOffset.ofTotalSeconds(zonedDateTimeTotalSecond - (((ZoneOffset) temporal1).getTotalSeconds() - zonedDateTimeTotalSecond)));
          return null;
        });
      }
      return Date.from(zonedDateTime[0].toInstant());
    } else {
      throw new IllegalArgumentException("Temporal: should be ZonedDateTime, LocalDateTime, LocalDate, or LocalTime");
    }
  }

  /**
   * 转换为 Date 对象
   *
   * @param temporal 时间对象
   * @return Date 对象
   */
  public static Date toDate(@NonNull final Temporal temporal) {
    return toDate(temporal, null);
  }

  /**
   * 转换为 LocalDateTime 对象
   *
   * @param date 时间对象
   * @return LocalDateTime 对象
   */
  public static LocalDateTime toLocalDateTime(@NonNull final Date date) {
    return date.toInstant().atZone(DateConstant.SYSTEM_ZONE_ID).toLocalDateTime();
  }

  /**
   * 转换为 LocalDate 对象
   *
   * @param date 时间对象
   * @return LocalDate 对象
   */
  public static LocalDate toLocalDate(@NonNull final Date date) {
    return date.toInstant().atZone(DateConstant.SYSTEM_ZONE_ID).toLocalDate();
  }

  /**
   * 转换为 LocalTime 对象
   *
   * @param date 时间对象
   * @return LocalTime 对象
   */
  public static LocalTime toLocalTime(@NonNull final Date date) {
    return date.toInstant().atZone(DateConstant.SYSTEM_ZONE_ID).toLocalTime();
  }

  /**
   * 转换为 Temporal（LocalDateTime、LocalDate、LocalTime、ZonedDateTime、OffsetDateTime）对象
   *
   * @param date  时间对象
   * @param clazz 时间类
   * @param <T>   时间类
   * @return Temporal 对象
   */
  public static <T extends Temporal> T toTemporal(@NonNull final Date date, @NonNull final Class<T> clazz) {
    if (LocalDateTime.class.equals(clazz)) {
      return (T) toLocalDateTime(date);
    } else if (LocalDate.class.equals(clazz)) {
      return (T) toLocalDate(date);
    } else if (LocalTime.class.equals(clazz)) {
      return (T) toLocalTime(date);
    } else if (ZonedDateTime.class.equals(clazz)) {
      return (T) toLocalDateTime(date).atZone(DateConstant.SYSTEM_ZONE_ID);
    } else if (OffsetDateTime.class.equals(clazz)) {
      return (T) toLocalDateTime(date).atZone(DateConstant.SYSTEM_ZONE_ID).toOffsetDateTime();
    }
    return null;
  }

  // TODO
  // public static long toEpochSecond(@NonNull final Temporal temporal) {
  //   if (LocalDateTime.class.equals(clazz)) {
  //     return (T) toLocalDateTime(date);
  //   } else if (LocalDate.class.equals(clazz)) {
  //     return (T) toLocalDate(date);
  //   } else if (LocalTime.class.equals(clazz)) {
  //     return (T) toLocalTime(date);
  //   }
  //   return null;
  // }

  public static long toEpochSecond(@NonNull final Date date) {
    return date.toInstant().atZone(DateConstant.SYSTEM_ZONE_ID).toEpochSecond();
  }

  /**
   * 解析为指定时区的 LocalDateTime 对象
   *
   * @param epochMilli 时间戳
   * @param zoneId     时区
   * @return 指定时区的 LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(final long epochMilli, final ZoneId zoneId) {
    ZonedDateTime zonedDateTime = Instant.ofEpochMilli(epochMilli).atZone(DateConstant.SYSTEM_ZONE_ID);
    if (zoneId != null) {
      zonedDateTime = zonedDateTime.withZoneSameInstant(DateFeature.get(zoneId));
    }
    return zonedDateTime.toLocalDateTime();
  }

  /**
   * 解析为 LocalDateTime 对象
   *
   * @param epochMilli 时间戳
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(final long epochMilli) {
    return parseLocalDateTime(epochMilli, null);
  }

  /**
   * 满足任意格式时解析为指定时区的 LocalDateTime 对象
   *
   * @param source   字符串
   * @param zoneId   时区
   * @param patterns 多种格式
   * @return 指定时区的 LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NonNull String source, final ZoneId zoneId, @NonNull final String... patterns) {
    if (StringUtils.isAllBlank(patterns)) {
      throw new IllegalArgumentException("Patterns: should not be all blank");
    }
    for (String pattern : patterns) {
      source = convertByPattern(source, pattern);
      ZonedDateTime zonedDateTime = LocalDateTime.parse(source, getDefaultFormatter(pattern)).atZone(DateConstant.SYSTEM_ZONE_ID);
      if (zoneId != null) {
        zonedDateTime = zonedDateTime.withZoneSameInstant(DateFeature.get(zoneId));
      }
      return zonedDateTime.toLocalDateTime();
    }
    return null;
  }

  /**
   * 解析为指定时区的 LocalDateTime 对象，根据被解析字符串的长度判断格式
   * <p>
   * 19：UUUU_MM_DD_HH_MM_SS、UUUU_MM_DD_SLASH_HH_MM_SS、UUUU_MM_DD_DOT_HH_MM_SS<br>
   * 16：UUUU_MM_DD_HH_MM、UUUU_MM_DD_SLASH_HH_MM、UUUU_MM_DD_DOT_HH_MM<br>
   * 10：UUUU_MM_DD、UUUU_MM_DD_SLASH、UUUU_MM_DD_DOT<br>
   * 7：UUUU_MM、UUUU_MM_SLASH、UUUU_MM_DOT<br>
   * 8：HH_MM_SS<br>
   * 5：HH_MM
   *
   * @param source 字符串
   * @param zoneId 时区
   * @return 指定时区的 LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NonNull final String source, final ZoneId zoneId) {
    String pattern = null;
    if (source.contains("-") || source.contains("/") || source.contains(".") || source.contains(":")) {
      switch (source.length()) {
        case 19:
          if (source.contains("-")) {
            pattern = DatePattern.UUUU_MM_DD_HH_MM_SS;
          } else if (source.contains("/")) {
            pattern = DatePattern.UUUU_MM_DD_SLASH_HH_MM_SS;
          } else if (source.contains(".")) {
            pattern = DatePattern.UUUU_MM_DD_DOT_HH_MM_SS;
          }
          break;
        case 16:
          if (source.contains("-")) {
            pattern = DatePattern.UUUU_MM_DD_HH_MM;
          } else if (source.contains("/")) {
            pattern = DatePattern.UUUU_MM_DD_SLASH_HH_MM;
          } else if (source.contains(".")) {
            pattern = DatePattern.UUUU_MM_DD_DOT_HH_MM;
          }
          break;
        case 10:
          if (source.contains("-")) {
            pattern = DatePattern.UUUU_MM_DD;
          } else if (source.contains("/")) {
            pattern = DatePattern.UUUU_MM_DD_SLASH;
          } else if (source.contains(".")) {
            pattern = DatePattern.UUUU_MM_DD_DOT;
          }
          break;
        case 7:
          if (source.contains("-")) {
            pattern = DatePattern.UUUU_MM;
          } else if (source.contains("/")) {
            pattern = DatePattern.UUUU_MM_SLASH;
          } else if (source.contains(".")) {
            pattern = DatePattern.UUUU_MM_DOT;
          }
          break;
        case 8:
          pattern = DatePattern.HH_MM_SS;
          break;
        case 5:
          pattern = DatePattern.HH_MM;
          break;
        default:
      }
      if (pattern == null) {
        return null;
      }
      return parseLocalDateTime(source, zoneId, pattern);
    }
    return null;
  }

  /**
   * 满足任意格式时解析为 LocalDateTime 对象
   *
   * @param source   字符串
   * @param patterns 多种格式
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NonNull final String source, @NonNull final String... patterns) {
    return parseLocalDateTime(source, null, patterns);
  }

  /**
   * 解析为 LocalDateTime 对象，根据被解析字符串的长度判断格式
   * <p>
   * 19：UUUU_MM_DD_HH_MM_SS、UUUU_MM_DD_SLASH_HH_MM_SS、UUUU_MM_DD_DOT_HH_MM_SS<br>
   * 16：UUUU_MM_DD_HH_MM、UUUU_MM_DD_SLASH_HH_MM、UUUU_MM_DD_DOT_HH_MM<br>
   * 10：UUUU_MM_DD、UUUU_MM_DD_SLASH、UUUU_MM_DD_DOT<br>
   * 7：UUUU_MM、UUUU_MM_SLASH、UUUU_MM_DOT<br>
   * 8：HH_MM_SS<br>
   * 5：HH_MM
   *
   * @param source 字符串
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NonNull final String source) {
    return parseLocalDateTime(source, (ZoneId) null);
  }

  /**
   * 解析为指定时区的 LocalDate 对象
   *
   * @param epochMilli 被解析的时间戳
   * @param zoneId     时区
   * @return 指定时区的 LocalDate 对象
   */
  public static LocalDate parseLocalDate(final long epochMilli, final ZoneId zoneId) {
    ZonedDateTime zonedDateTime = Instant.ofEpochMilli(epochMilli).atZone(DateConstant.SYSTEM_ZONE_ID);
    if (zoneId != null) {
      zonedDateTime = zonedDateTime.withZoneSameInstant(DateFeature.get(zoneId));
    }
    return zonedDateTime.toLocalDate();
  }

  /**
   * 解析为 LocalDate 对象
   *
   * @param epochMilli 时间戳
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(final long epochMilli) {
    return parseLocalDate(epochMilli, null);
  }

  /**
   * 满足任意格式时解析为指定时区的 LocalDate 对象
   * <p>
   * 需要注意的是 LocalDate 不存在时区概念，此处是将 time 补足为 LocalTime.MIN 后再转换为 LocalDate
   *
   * @param source   字符串
   * @param zoneId   时区
   * @param patterns 多种格式
   * @return 指定时区的 LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NonNull String source, final ZoneId zoneId, @NonNull final String... patterns) {
    if (StringUtils.isAllBlank(patterns)) {
      throw new IllegalArgumentException("Patterns: should not be all blank");
    }
    for (String pattern : patterns) {
      source = convertByPattern(source, pattern);
      LocalDate localDate = LocalDate.parse(source, getDefaultFormatter(pattern));
      if (zoneId != null) {
        return localDate.atTime(LocalTime.MIN).atZone(DateConstant.SYSTEM_ZONE_ID).withZoneSameInstant(DateFeature.get(zoneId)).toLocalDate();
      }
      return localDate;
    }
    return null;
  }

  /**
   * 解析为指定时区的 LocalDate 对象，根据被解析字符串的长度判断格式
   * <p>
   * 需要注意的是 LocalDate 不存在时区概念，此处是将 time 补足为 LocalTime.MIN 后再转换为 LocalDate
   * <p>
   * 10：UUUU_MM_DD、UUUU_MM_DD_SLASH、UUUU_MM_DD_DOT<br>
   * 7：UUUU_MM、UUUU_MM_SLASH、UUUU_MM_DOT<br>
   * 8：HH_MM_SS<br>
   * 5：HH_MM
   *
   * @param source 字符串
   * @param zoneId 时区
   * @return 指定时区的 LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NonNull final String source, final ZoneId zoneId) {
    String pattern = null;
    if (source.contains("-") || source.contains("/") || source.contains(".") || source.contains(":")) {
      switch (source.length()) {
        case 10:
          if (source.contains("-")) {
            pattern = DatePattern.UUUU_MM_DD;
          } else if (source.contains("/")) {
            pattern = DatePattern.UUUU_MM_DD_SLASH;
          } else if (source.contains(".")) {
            pattern = DatePattern.UUUU_MM_DD_DOT;
          }
          break;
        case 7:
          if (source.contains("-")) {
            pattern = DatePattern.UUUU_MM;
          } else if (source.contains("/")) {
            pattern = DatePattern.UUUU_MM_SLASH;
          } else if (source.contains(".")) {
            pattern = DatePattern.UUUU_MM_DOT;
          }
          break;
        case 8:
          pattern = DatePattern.HH_MM_SS;
          break;
        case 5:
          pattern = DatePattern.HH_MM;
          break;
        default:
      }
      if (pattern == null) {
        return null;
      }
      return parseLocalDate(source, zoneId, pattern);
    }
    return null;
  }

  /**
   * 满足任意格式就解析为 LocalDate 对象
   *
   * @param source  字符串
   * @param pattern 格式
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NonNull final String source, @NonNull final String... pattern) {
    return parseLocalDate(source, null, pattern);
  }

  /**
   * 解析为 LocalDate 对象，根据被解析字符串的长度判断格式
   * <p>
   * 10：UUUU_MM_DD、UUUU_MM_DD_SLASH、UUUU_MM_DD_DOT<br>
   * 7：UUUU_MM、UUUU_MM_SLASH、UUUU_MM_DOT<br>
   * 8：HH_MM_SS<br>
   * 5：HH_MM
   *
   * @param source 字符串
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NonNull final String source) {
    return parseLocalDate(source, (ZoneId) null);
  }

  /**
   * 解析为指定时区的 LocalTime 对象
   *
   * @param epochMilli 时间戳
   * @param zoneId     时区
   * @return 指定时区的 LocalTime 对象
   */
  public static LocalTime parseLocalTime(final long epochMilli, final ZoneId zoneId) {
    ZonedDateTime zonedDateTime = Instant.ofEpochMilli(epochMilli).atZone(DateConstant.SYSTEM_ZONE_ID);
    if (zoneId != null) {
      zonedDateTime = zonedDateTime.withZoneSameInstant(DateFeature.get(zoneId));
    }
    return zonedDateTime.toLocalTime();
  }

  /**
   * 解析为 LocalTime 对象
   *
   * @param epochMilli 时间戳
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(final long epochMilli) {
    return parseLocalTime(epochMilli, null);
  }

  /**
   * 满足任意格式时解析为指定时区的 LocalTime 对象
   * <p>
   * 需要注意的是 LocalTime 不存在时区概念，此处是将 date 补足为 LocalDate.now() 后再转换为 LocalTime
   *
   * @param source   字符串
   * @param zoneId   时区
   * @param patterns 多个格式
   * @return 指定时区的 LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NonNull String source, final ZoneId zoneId, @NonNull final String... patterns) {
    if (StringUtils.isAllBlank(source)) {
      return null;
    }
    for (String pattern : patterns) {
      source = convertByPattern(source, pattern);
      LocalTime localTime = LocalTime.parse(source, getDefaultFormatter(pattern));
      if (zoneId != null) {
        return localTime.atDate(LocalDate.now()).atZone(DateConstant.SYSTEM_ZONE_ID).withZoneSameInstant(DateFeature.get(zoneId)).toLocalTime();
      }
      return localTime;
    }
    return null;
  }

  /**
   * 解析为指定时区的 LocalTime 对象，根据被解析字符串的长度判断格式
   * <p>
   * 需要注意的是 LocalTime 不存在时区概念，此处是将 date 补足为 LocalDate.now() 后再转换为 LocalTime
   * <p>
   * 8：HH_MM_SS<br>
   * 5：HH_MM
   *
   * @param source 字符串
   * @param zoneId 时区
   * @return 指定时区的 LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NonNull final String source, final ZoneId zoneId) {
    String pattern = null;
    if (source.contains(":")) {
      int srcLen = source.length();
      if (srcLen == 8 || srcLen == 5) {
        switch (srcLen) {
          case 8:
            pattern = DatePattern.HH_MM_SS;
            break;
          case 5:
            pattern = DatePattern.HH_MM;
            break;
          default:
        }
        return parseLocalTime(source, zoneId, pattern);
      }
    }
    return null;
  }

  /**
   * 满足任意格式就解析为 LocalTime 对象
   *
   * @param source   字符串
   * @param patterns 多种格式
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NonNull final String source, @NonNull final String... patterns) {
    return parseLocalTime(source, null, patterns);
  }

  /**
   * 解析为 LocalTime，根据被解析字符串的长度判断格式
   * <p>
   * 8：HH_MM_SS
   * <br>5：HH_MM
   *
   * @param source 被解析的字符串
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NonNull final String source) {
    return parseLocalTime(source, (ZoneId) null);
  }

  /**
   * 解析为指定时区的 Date 对象
   *
   * @param epochMilli 时间戳
   * @param zoneId     时区
   * @return 指定时区的 Date 对象
   */
  public static Date parseDate(final long epochMilli, final ZoneId zoneId) {
    Date date = new Date(epochMilli);
    if (zoneId != null) {
      date = Date.from(date.toInstant().atZone(DateConstant.SYSTEM_ZONE_ID).withZoneSameInstant(DateFeature.get(zoneId)).toInstant());
    }
    return date;
  }

  /**
   * 解析为 Date 对象
   *
   * @param epochMilli 时间戳
   * @return Date 对象
   */
  public static Date parseDate(final long epochMilli) {
    return parseDate(epochMilli, null);
  }

  /**
   * 满足任意格式时解析为指定时区的 Date 对象
   *
   * @param source   字符串
   * @param zoneId   时区
   * @param patterns 多个格式
   * @return 指定时区的 Date 对象
   */
  public static Date parseDate(@NonNull final String source, final ZoneId zoneId, @NonNull final String... patterns) {
    LocalDateTime localDateTime = parseLocalDateTime(source, zoneId, patterns);
    if (localDateTime != null) {
      return toDate(localDateTime);
    }
    return null;
  }

  /**
   * 解析为指定时区的 Date 对象
   *
   * @param source 字符串
   * @param zoneId 时区
   * @return 指定时区的 Date 对象
   */
  public static Date parseDate(@NonNull final String source, @NonNull final ZoneId zoneId) {
    LocalDateTime localDateTime = parseLocalDateTime(source, zoneId);
    if (localDateTime != null) {
      return toDate(localDateTime);
    }
    return null;
  }

  /**
   * 满足任意格式时解析为 Date 对象（org.apache.commons.lang3.time.DateUtils.parseDate）
   *
   * @param source   字符串
   * @param patterns 多个格式
   * @return Date 对象
   */
  public static Date parseDate(@NonNull final String source, @NonNull final String... patterns) {
    if (StringUtils.isAllBlank(patterns)) {
      throw new IllegalArgumentException("Patterns: should not be all blank");
    }
    try {
      return org.apache.commons.lang3.time.DateUtils.parseDate(source, patterns);
    } catch (ParseException e) {
      log.warn(e.getMessage());
      return null;
    }
  }

  /**
   * 解析为 Date 对象
   *
   * @param source 字符串
   * @return Date 对象
   */
  public static Date parseDate(@NonNull final String source) {
    LocalDateTime localDateTime = parseLocalDateTime(source);
    if (localDateTime != null) {
      return toDate(localDateTime);
    }
    return null;
  }

  /**
   * 解析为指定时区的时间对象
   *
   * @param epochMilli 时间戳
   * @param zoneId     时区
   * @param clazz      时间类
   * @param <T>        时间类
   * @return 指定时区的时间对象
   */
  public static <T> T parse(final long epochMilli, final ZoneId zoneId, @NonNull final Class<T> clazz) {
    if (LocalDateTime.class.equals(clazz)) {
      return (T) parseLocalDateTime(epochMilli, zoneId);
    } else if (LocalDate.class.equals(clazz)) {
      return (T) parseLocalDate(epochMilli, zoneId);
    } else if (LocalTime.class.equals(clazz)) {
      return (T) parseLocalTime(epochMilli, zoneId);
    } else if (Date.class.equals(clazz)) {
      return (T) parseDate(epochMilli, zoneId);
    }
    return null;
  }

  /**
   * 解析为时间对象
   *
   * @param epochMilli 时间戳
   * @param clazz      时间类
   * @param <T>        时间类
   * @return 时间对象
   */
  public static <T> T parse(final long epochMilli, @NonNull final Class<T> clazz) {
    return parse(epochMilli, null, clazz);
  }

  /**
   * 满足任意格式时解析为指定时区的时间对象
   *
   * @param source   字符串
   * @param zoneId   时区
   * @param clazz    时间类
   * @param patterns 多个格式
   * @param <T>      时间类
   * @return 指定时区的时间对象
   */
  public static <T> T parse(@NonNull final String source, final ZoneId zoneId, @NonNull final Class<T> clazz, @NonNull final String... patterns) {
    if (LocalDateTime.class.equals(clazz)) {
      return (T) parseLocalDateTime(source, zoneId, patterns);
    } else if (LocalDate.class.equals(clazz)) {
      return (T) parseLocalDate(source, zoneId, patterns);
    } else if (LocalTime.class.equals(clazz)) {
      return (T) parseLocalTime(source, zoneId, patterns);
    } else if (Date.class.equals(clazz)) {
      return (T) parseDate(source, zoneId, patterns);
    }
    return null;
  }

  /**
   * 解析指定时区的时间对象
   *
   * @param source 字符串
   * @param zoneId 时区
   * @param clazz  时间类
   * @param <T>    时间类
   * @return 指定时区的时间对象
   */
  public static <T> T parse(@NonNull final String source, @NonNull final ZoneId zoneId, @NonNull final Class<T> clazz) {
    if (LocalDateTime.class.equals(clazz)) {
      return (T) parseLocalDateTime(source, zoneId);
    } else if (LocalDate.class.equals(clazz)) {
      return (T) parseLocalDate(source, zoneId);
    } else if (LocalTime.class.equals(clazz)) {
      return (T) parseLocalTime(source, zoneId);
    } else if (Date.class.equals(clazz)) {
      return (T) parseDate(source, zoneId);
    }
    return null;
  }

  /**
   * 满足任意格式时解析为时间对象
   *
   * @param source   字符串
   * @param clazz    时间类
   * @param patterns 多个格式
   * @param <T>      时间类
   * @return 时间对象
   */
  public static <T> T parse(@NonNull final String source, @NonNull final Class<T> clazz, @NonNull final String... patterns) {
    return parse(source, null, clazz, patterns);
  }

  /**
   * 解析为时间对象
   *
   * @param source 字符串
   * @param clazz  时间类
   * @param <T>    时间类
   * @return 时间对象
   */
  public static <T> T parse(@NonNull final String source, @NonNull final Class<T> clazz) {
    if (LocalDateTime.class.equals(clazz)) {
      return (T) parseLocalDateTime(source);
    } else if (LocalDate.class.equals(clazz)) {
      return (T) parseLocalDate(source);
    } else if (LocalTime.class.equals(clazz)) {
      return (T) parseLocalTime(source);
    } else if (Date.class.equals(clazz)) {
      return (T) parseDate(source);
    }
    return null;
  }

  /**
   * 校验字符串是否符合时间格式，如果稍后就要再 parse，不要使用这个方法，自己 try catch。
   *
   * @param text    字符串
   * @param pattern 格式
   * @return 时间格式是否正确
   */
  public static boolean validate(final String text, final String pattern) {
    try {
      parseLocalDateTime(text, pattern);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  /**
   * 从旧时区转换到新时区
   * <p>
   * ZonedDateTime 对象请直接用 .withZoneSameInstant(newZoneId)
   *
   * @param temporal  时间对象
   * @param oldZoneId 旧时区
   * @param newZoneId 新时区
   * @param <T>       时间类 extends Temporal
   * @return 转换时区后的时间对象
   */
  public static <T extends Temporal> T withZoneInstant(@NonNull final T temporal, @NonNull final ZoneId oldZoneId, @NonNull final ZoneId newZoneId) {
    if (temporal instanceof LocalDateTime) {
      return (T) ((LocalDateTime) temporal).atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDateTime();
    } else if (temporal instanceof LocalDate) {
      // LocalDate 不存在时区概念，此处是将 time 补足为 startOfDay 后再转换为 LocalDate
      return (T) ((LocalDate) temporal).atStartOfDay().atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDate();
    } else if (temporal instanceof LocalTime) {
      // LocalTime 不存在时区概念，此处是将 date 补足为 00-01-01 后再转换为 LocalTime
      return (T) ((LocalTime) temporal).atDate(LocalDate.of(0, 1, 1)).atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalTime();
    }
    return null;
  }

  /**
   * 从系统时区转换到新时区
   *
   * @param temporal  时间对象
   * @param newZoneId 新时区
   * @param <T>       时间类 extends Temporal
   * @return 转换时区后的时间对象
   */
  public static <T extends Temporal> T withZoneInstant(@NonNull final T temporal, @NonNull final ZoneId newZoneId) {
    return withZoneInstant(temporal, DateConstant.SYSTEM_ZONE_ID, newZoneId);
  }

  /**
   * 从旧时区转换到新时区
   *
   * @param date      Date 对象
   * @param oldZoneId 旧时区
   * @param newZoneId 新时区
   * @return 转换时区后的 Date 对象
   */
  public static Date withZoneInstant(@NonNull final Date date, @NonNull final ZoneId oldZoneId, final @NonNull ZoneId newZoneId) {
    LocalDateTime localDateTime = withZoneInstant(LocalDateTime.ofInstant(date.toInstant(), DateConstant.SYSTEM_ZONE_ID), oldZoneId, newZoneId);
    if (localDateTime == null) {
      return null;
    }
    return toDate(localDateTime);
  }

  /**
   * 从系统时区转换到新时区
   *
   * @param date      Date 对象
   * @param newZoneId 新时区
   * @return 转换时区后的 Date 对象
   */
  public static Date withZoneInstant(@NonNull final Date date, @NonNull final ZoneId newZoneId) {
    return withZoneInstant(date, DateConstant.SYSTEM_ZONE_ID, newZoneId);
  }

  /**
   * 获取当天时间字符串
   *
   * @return 格式为 yyyy-MM-dd 的当天时间字符串
   */
  public static String today() {
    return DateFormat.YYYY_MM_DD.format(new Date());
  }

  /**
   * 获取当前时间字符串
   *
   * @return 格式为 yyyy-MM-dd HH:mm:ss 的当前时间字符串
   */
  public static String now() {
    return DateFormat.YYYY_MM_DD_HH_MM_SS.format(new Date());
  }

  /**
   * 获取指定格式的当前时间字符串
   *
   * @param pattern 格式
   * @return 指定格式的当前时间字符串
   */
  public static String now(@NonNull final String pattern) {
    return FastDateFormat.getInstance(pattern).format(new Date());
  }

  /**
   * 获取指定时区的当前时间字符串
   *
   * @param zoneId 时区
   * @return 指定时区的当前时间字符串
   */
  public static String now(@NonNull final ZoneId zoneId) {
    return DateTimeFormatter.ofPattern(DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN).format(ZonedDateTime.now(DateFeature.get(zoneId)));
  }

  /**
   * 获取指定时区和格式的当前时间字符串
   *
   * @param zoneId  时区
   * @param pattern 格式
   * @return 指定时区和格式的当前时间字符串
   */
  public static String now(@NonNull final ZoneId zoneId, @NonNull final String pattern) {
    return DateTimeFormatter.ofPattern(pattern).format(ZonedDateTime.now(DateFeature.get(zoneId)));
  }

  /**
   * 指定多个时间级别的最小时间
   *
   * @param temporal       时间对象
   * @param temporalFields 多个最小时间的级别
   * @param <T>            时间类
   * @return 时间对象
   */
  public static <T extends Temporal> T min(@NonNull T temporal, @NonNull final TemporalField... temporalFields) {
    if (CollectionUtils.isAllEmpty(temporalFields)) {
      return null;
    }
    for (TemporalField temporalField : temporalFields) {
      temporal = (T) temporal.with(temporalField, temporalField.range().getMinimum());
    }
    return (T) temporal;
  }

  /**
   * 指定多个时间级别的最大时间
   *
   * @param temporal       时间对象
   * @param temporalFields 多个最大时间的级别
   * @param <T>            时间类
   * @return 时间对象
   */
  public static <T extends Temporal> T max(@NonNull T temporal, @NonNull final TemporalField... temporalFields) {
    if (CollectionUtils.isAllEmpty(temporalFields)) {
      return null;
    }
    for (TemporalField temporalField : temporalFields) {
      if (ChronoField.DAY_OF_MONTH.equals(temporalField)) {
        temporal = (T) temporal.with(TemporalAdjusters.lastDayOfMonth());
      } else if (ChronoField.DAY_OF_YEAR.equals(temporalField)) {
        temporal = (T) temporal.with(TemporalAdjusters.lastDayOfYear());
      } else {
        ValueRange valueRange = temporalField.range();
        long newValue = valueRange.getMaximum();
        temporal = (T) temporal.with(temporalField, newValue);
      }
    }
    return temporal;
  }

  /**
   * 指定多个时间级别的最小时间
   *
   * @param temporal       时间对象
   * @param temporalFields 多个最小时间的级别
   * @return 时间对象
   */
  public static Date minDate(@NonNull final Temporal temporal, @NonNull final TemporalField... temporalFields) {
    Temporal temporal1 = min(temporal, temporalFields);
    if (temporal1 == null) {
      return null;
    }
    return toDate(temporal1);
  }

  /**
   * 指定多个时间级别的最大时间
   *
   * @param temporal       时间对象
   * @param temporalFields 多个最大时间的级别
   * @return 时间对象
   */
  public static Date maxDate(@NonNull final Temporal temporal, @NonNull final TemporalField... temporalFields) {
    Temporal temporal1 = max(temporal, temporalFields);
    if (temporal1 == null) {
      return null;
    }
    return toDate(temporal1);
  }

  /**
   * 获取指定时区和加减天的今天开始时间
   *
   * @param zoneId            时区
   * @param addOrSubtractDays 加减天
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinTime(final ZoneId zoneId, final Long addOrSubtractDays) {
    LocalDateTime localDateTime = todayMinTime();
    if (addOrSubtractDays != null && addOrSubtractDays != 0) {
      localDateTime = plusOrMinus(localDateTime, addOrSubtractDays, ChronoUnit.DAYS);
    }
    if (zoneId != null) {
      return localDateTime.atZone(DateConstant.SYSTEM_ZONE_ID).withZoneSameInstant(DateFeature.get(zoneId)).toLocalDateTime();
    }
    return localDateTime;
  }

  /**
   * 获取指定时区的今天开始时间
   *
   * @param zoneId 时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinTime(@NonNull final ZoneId zoneId) {
    return todayMinTime(zoneId, null);
  }

  /**
   * 获取指定加减天的今天开始时间
   *
   * @param days 加减天
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinTime(final long days) {
    return todayMinTime(null, days);
  }

  /**
   * 获取今天开始时间
   *
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinTime() {
    return LocalDate.now().atStartOfDay();
  }

  /**
   * 获取指定时区、格式和加减天的今天开始时间字符串
   *
   * @param zoneId            时区
   * @param pattern           格式
   * @param addOrSubtractDays 加减天
   * @return 时间字符串
   */
  public static String todayMinTimeStr(final ZoneId zoneId, @NonNull final String pattern, final Long addOrSubtractDays) {
    return format(todayMinTime(zoneId, addOrSubtractDays), pattern);
  }

  /**
   * 获取指定时区和格式的今天开始时间字符串
   *
   * @param zoneId  时区
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMinTimeStr(@NonNull final ZoneId zoneId, @NonNull final String pattern) {
    return todayMinTimeStr(zoneId, pattern, null);
  }

  /**
   * 获取指定格式的今天开始时间字符串
   *
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMinTimeStr(@NonNull final String pattern) {
    return todayMinTimeStr(null, pattern, null);
  }

  /**
   * 获取今天开始时间字符串
   *
   * @return 时间字符串
   */
  public static String todayMinTimeStr() {
    return todayMinTimeStr(DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN);
  }

  /**
   * 获取指定时区和加减天的今天结束时间
   *
   * @param zoneId            时区
   * @param addOrSubtractDays 加减天
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxTime(final ZoneId zoneId, final Long addOrSubtractDays) {
    LocalDateTime localDateTime = todayMaxTime();
    if (addOrSubtractDays != null && addOrSubtractDays != 0) {
      localDateTime = plusOrMinus(localDateTime, addOrSubtractDays, ChronoUnit.DAYS);
    }
    if (zoneId != null) {
      return localDateTime.atZone(DateConstant.SYSTEM_ZONE_ID).withZoneSameInstant(DateFeature.get(zoneId)).toLocalDateTime();
    }
    return localDateTime;
  }

  /**
   * 获取指定时区的今天结束时间
   *
   * @param zoneId 时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxTime(@NonNull final ZoneId zoneId) {
    return todayMaxTime(zoneId, null);
  }

  /**
   * 获取指定加减天的今天结束时间
   *
   * @param days 加减天
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxTime(final long days) {
    return todayMaxTime(null, days);
  }

  /**
   * 获取今天结束时间
   *
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxTime() {
    return LocalDate.now().atTime(LocalTime.MAX);
  }

  /**
   * 获取指定时区、格式和加减天的今天结束时间字符串
   *
   * @param zoneId            时区
   * @param pattern           格式
   * @param addOrSubtractDays 加减天
   * @return 时间字符串
   */
  public static String todayMaxTimeStr(final ZoneId zoneId, @NonNull final String pattern, final Long addOrSubtractDays) {
    return format(todayMaxTime(zoneId, addOrSubtractDays), pattern);
  }

  /**
   * 获取指定时区和格式的今天结束时间字符串
   *
   * @param zoneId  时区
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMaxTimeStr(@NonNull final ZoneId zoneId, @NonNull final String pattern) {
    return todayMaxTimeStr(zoneId, pattern, null);
  }

  /**
   * 获取指定格式的今天结束时间字符串
   *
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMaxTimeStr(@NonNull final String pattern) {
    return todayMaxTimeStr(null, pattern, null);
  }

  /**
   * 获取今天结束时间字符串
   *
   * @return 时间字符串
   */
  public static String todayMaxTimeStr() {
    return todayMaxTimeStr(DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN);
  }

  /**
   * 加减指定时间类型数量的时间
   *
   * @param temporal        被加减的时间对象
   * @param augendOrMinuend 加减数量
   * @param chronoUnits     多个时间类型
   * @param <T>             时间类
   * @return 加减后的时间对象
   */
  public static <T extends Temporal> T plusOrMinus(@NonNull T temporal, final long augendOrMinuend, @NonNull final ChronoUnit... chronoUnits) {
    for (ChronoUnit chronoUnit : chronoUnits) {
      if (augendOrMinuend < 0) {
        temporal = (T) temporal.minus(Math.abs(augendOrMinuend), chronoUnit);
      } else if (augendOrMinuend > 0) {
        temporal = (T) temporal.plus(augendOrMinuend, chronoUnit);
      }
    }
    return temporal;
  }

  /**
   * 加减指定数量的时间，时间周期为毫秒
   *
   * @param temporal        被加减的时间对象
   * @param augendOrMinuend 加减数量
   * @param <T>             时间类
   * @return 加减后的时间对象
   */
  public static <T extends Temporal> T plusOrMinus(@NonNull final T temporal, final long augendOrMinuend) {
    return plusOrMinus(temporal, augendOrMinuend, ChronoUnit.MILLIS);
  }

  /**
   * 毫秒转换为指定时间类型
   *
   * @param milli      毫秒
   * @param chronoUnit 时间类型，当它大于周时，返回的是平均时间
   * @return 指定时间类型的时间
   */
  public static long toChronoUnit(final long milli, @NonNull final ChronoUnit chronoUnit) {
    if (ChronoUnit.MILLIS.equals(chronoUnit)) {
      return milli;
    }
    return milli / (chronoUnit.getDuration().getSeconds() * 1000);
  }

  /**
   * 时间差
   *
   * @param startInclusive 开始时间
   * @param endExclusive   结束时间
   * @return 时间量
   */
  public static Duration between(@NonNull final Temporal startInclusive, @NonNull final Temporal endExclusive) {
    return Duration.between(startInclusive, endExclusive);
  }

  /**
   * 时间差
   *
   * @param startInclusive 开始时间
   * @param endExclusive   结束时间
   * @param chronoUnit     时间周期
   * @return 指定时间周期的时间差
   */
  public static long between(@NonNull final Temporal startInclusive, @NonNull final Temporal endExclusive, @NonNull final ChronoUnit chronoUnit) {
    return chronoUnit.between(startInclusive, endExclusive);
  }

  /**
   * 时间差
   *
   * @param startInclusive 开始时间
   * @param endExclusive   结束时间
   * @return 时间量
   */
  public static Duration between(@NonNull final Date startInclusive, @NonNull final Date endExclusive) {
    return between(toLocalDateTime(startInclusive), toLocalDateTime(endExclusive));
  }

  /**
   * 时间差
   *
   * @param startInclusive 开始时间
   * @param endExclusive   结束时间
   * @param chronoUnit     时间周期
   * @return 指定时间周期的时间差
   */
  public static long between(@NonNull final Date startInclusive, @NonNull final Date endExclusive, @NonNull final ChronoUnit chronoUnit) {
    return toChronoUnit(startInclusive.getTime() - endExclusive.getTime(), chronoUnit);
  }

  /**
   * 格式化时间差
   *
   * @param startInclusive 开始时间
   * @param endExclusive   结束时间
   * @param pattern        格式
   * @return 时间差倒计时
   */
  public static String formatBetween(@NonNull final Temporal startInclusive, @NonNull final Temporal endExclusive, @NonNull final String pattern) {
    return formatCountdown(Math.abs(between(startInclusive, endExclusive, ChronoUnit.MILLIS)), pattern);
  }

  /**
   * 格式化时间差
   *
   * @param startInclusive 开始时间
   * @param endExclusive   结束时间
   * @param pattern        格式
   * @return 时间差倒计时
   */
  public static String formatBetween(@NonNull final Date startInclusive, @NonNull final Date endExclusive, @NonNull final String pattern) {
    return formatCountdown(Math.abs(between(startInclusive, endExclusive, ChronoUnit.MILLIS)), pattern);
  }

  /**
   * 格式化时间差
   *
   * @param startInclusive   开始时间
   * @param endExclusive     结束时间
   * @param isIgnoreZero     是否忽略为 0 的时间级别
   * @param weekSuffix       周后缀
   * @param dayOfMonthSuffix 天后缀
   * @param hourSuffix       小时后缀
   * @param minuteSuffix     分钟后缀
   * @param secondSuffix     秒后缀
   * @param millisSuffix     毫秒后缀
   * @return 时间差倒计时
   */
  public static String formatBetween(@NonNull final Date startInclusive, @NonNull final Date endExclusive, final boolean isIgnoreZero, final String weekSuffix, final String dayOfMonthSuffix, final String hourSuffix, final String minuteSuffix, final String secondSuffix, final String millisSuffix) {
    return formatCountdown(Math.abs(between(startInclusive, endExclusive, ChronoUnit.MILLIS)), isIgnoreZero, weekSuffix, dayOfMonthSuffix, hourSuffix, minuteSuffix, secondSuffix, millisSuffix);
  }

  /**
   * 格式化时间差
   *
   * @param startInclusive   开始时间
   * @param endExclusive     结束时间
   * @param isIgnoreZero     是否忽略为 0 的时间级别
   * @param weekSuffix       周后缀
   * @param dayOfMonthSuffix 天后缀
   * @param hourSuffix       小时后缀
   * @param minuteSuffix     分钟后缀
   * @param secondSuffix     秒后缀
   * @param millisSuffix     毫秒后缀
   * @return 时间差倒计时
   */
  public static String formatBetween(@NonNull final Temporal startInclusive, @NonNull final Temporal endExclusive, final boolean isIgnoreZero, final String weekSuffix, final String dayOfMonthSuffix, final String hourSuffix, final String minuteSuffix, final String secondSuffix, final String millisSuffix) {
    return formatCountdown(Math.abs(between(startInclusive, endExclusive, ChronoUnit.MILLIS)), isIgnoreZero, weekSuffix, dayOfMonthSuffix, hourSuffix, minuteSuffix, secondSuffix, millisSuffix);
  }

  /**
   * 是否为闰年
   *
   * @param temporal 时间对象
   * @return 是否为闰年
   */
  public static boolean isLeapYear(@NonNull final Temporal temporal) {
    if (temporal instanceof LocalDateTime) {
      return ((LocalDateTime) temporal).toLocalDate().isLeapYear();
    } else if (temporal instanceof LocalDate) {
      return ((LocalDate) temporal).isLeapYear();
    } else if (temporal instanceof ZonedDateTime) {
      return ((ZonedDateTime) temporal).toLocalDate().isLeapYear();
    } else {
      throw new IllegalArgumentException("Temporal: should be ZonedDateTime, LocalDateTime, LocalDate, or LocalTime");
    }
  }

  /**
   * 是否为闰年
   *
   * @param date 时间对象
   * @return 是否为闰年
   */
  public static boolean isLeapYear(@NonNull final Date date) {
    return toLocalDate(date).isLeapYear();
  }

  /**
   * 是否为闰年
   *
   * @param isoYear 年
   * @return 是否为闰年
   */
  public static boolean isLeapYear(final int isoYear) {
    return Year.of(isoYear).isLeap();
  }

  // region 交集差集并集

  /**
   * 两个时间段是否交集
   *
   * @param x1 时间段 1 开始时间
   * @param y1 时间段 1 结束时间
   * @param x2 时间段 2 开始时间
   * @param y2 时间段 2 结束时间
   * @return 是否交集
   */
  public static boolean isIntersection(@NonNull final LocalDateTime x1, @NonNull final LocalDateTime y1, @NonNull final LocalDateTime x2, @NonNull final LocalDateTime y2) {
    // x1——————y1
    //          x2——————y2
    // x2——————y2
    //          x1——————y1
    // (y1 < x2 || y2 < x1) 为不交集
    return !(y1.isBefore(x2) || y2.isBefore(x1));
  }

  /**
   * 获取两个时间段的交集
   *
   * @param x1 时间段 1 开始时间
   * @param y1 时间段 1 结束时间
   * @param x2 时间段 2 开始时间
   * @param y2 时间段 2 结束时间
   * @return 交集
   */
  public static LocalDateTime[] getIntersection(@NonNull final LocalDateTime x1, @NonNull final LocalDateTime y1, @NonNull final LocalDateTime x2, @NonNull final LocalDateTime y2) {
    if (!isIntersection(x1, y1, x2, y2)) {
      return null;
    }
    LocalDateTime[] result = new LocalDateTime[2];
    // x1——————y1
    //     x2——————y2
    //
    // x1—————————————y1
    //     x2——————y2
    //
    // x1 >= x2
    if (x1.isBefore(x2) || x1.isEqual(x2)) {
      // 交集的开始时间为 x2
      result[0] = x2;
    }
    //     x1——————y1
    // x2——————y2
    //
    //     x1——————y1
    // x2—————————————y2
    //
    // x1——————y1
    // x2————————y2
    //
    // x1 >= x2
    else if (x1.isAfter(x2) || x1.isEqual(x2)) {
      // 交集的开始时间为 x1
      result[0] = x1;
    }
    // x1——————y1
    //     x2——————y2
    //
    //     x1——————y1
    // x2—————————————y2
    //
    // y1 <= y2
    if (y1.isBefore(y2) || y1.isEqual(y2)) {
      // 交集的结束时间为 y1
      result[1] = y1;
    }
    //     x1——————y1
    // x2——————y2
    //
    // x1—————————————y1
    //     x2——————y2
    //
    //   x1——————y1
    // x2————————y2
    //
    // y1 > y2
    else if (y1.isAfter(y2) || y1.isEqual(y2)) {
      // 交集的结束时间为 y2
      result[1] = y2;
    }
    if (result[0] == null || result[1] == null) {
      return null;
    }
    return result;
  }

  /**
   * 获取两个时间段交集时的差集
   *
   * @param x1                  时间段 1 开始时间
   * @param y1                  时间段 1 结束时间
   * @param x2                  时间段 2 开始时间
   * @param y2                  时间段 2 结束时间
   * @param augendOrMinuendType 谁加减数量，差集 [[x1, y1], [x2, y2]]
   *                            1：y1 减
   *                            2：x2 加
   *                            其他：默认，见下文
   *                            <p>
   *                            x1——————y1
   *                            |     x2——————y2
   *                            时间段 1 在时间段 2 前面时，差集的第一段的结束时间（y1）减。
   *                            |     x1——————y1
   *                            x2——————y2
   *                            时间段 1 在时间段 2 后面时，差集的第二段的开始时间（x1）加；
   * @param augendOrMinuend     加减数量，避免差集与交集相交，小于 0 时忽略
   * @param chronoUnits         加减数量的时间周期
   * @return 差集
   */
  public static LocalDateTime[][] getDifferenceSetsByIntersection(@NonNull final LocalDateTime x1, @NonNull final LocalDateTime y1, @NonNull final LocalDateTime x2, @NonNull final LocalDateTime y2, final int augendOrMinuendType, final long augendOrMinuend, @NonNull final ChronoUnit... chronoUnits) {
    // 获取交集
    LocalDateTime[] intersections = getIntersection(x1, y1, x2, y2);
    if (intersections == null) {
      return null;
    }
    // 获取差集
    LocalDateTime[][] result = new LocalDateTime[2][2];

    // x1——————y1
    //     x2——————y2
    //
    // x1—————————————y1
    //     x2——————y2
    //
    // x1 < x2
    if (x1.isBefore(x2)) {
      // 差集的第一段为 x1 ~ x2
      result[0] = new LocalDateTime[]{x1, x2};
    }
    //     x1——————y1
    // x2——————y2
    //
    //     x1——————y1
    // x2—————————————y2
    //
    // x1 > x2
    else if (x1.isAfter(x2)) {
      // 差集的第一段为 x2 ~ x1
      result[0] = new LocalDateTime[]{x2, x1};
    }

    // x1——————y1
    //     x2——————y2
    //
    //     x1——————y1
    // x2—————————————y2
    //
    // y1 < y2
    if (y1.isBefore(y2)) {
      // 差集的第二段为 y1 ~ y2
      result[1] = new LocalDateTime[]{y1, y2};
    }
    //     x1——————y1
    // x2——————y2
    //
    // x1—————————————y1
    //     x2——————y2
    //
    // y1 > y2
    else if (y1.isAfter(y2)) {
      // 差集的第二段为 y2 ~ y1
      result[1] = new LocalDateTime[]{y2, y1};
    }

    if (augendOrMinuend > 0 && CollectionUtils.sizeIsNotEmpty(chronoUnits)) {
      boolean isPlusOrMinus = augendOrMinuendType != 1 && augendOrMinuendType != 2;
      // 循环两段差集
      for (LocalDateTime[] localDateTimes : result) {
        // x1——————y1
        //       x2——————y2
        // 如果差集的结束时间（y1）和交集的开始时间相等，结束时间 -= n，即差集与交集完全不相交
        if (localDateTimes[1].equals(intersections[0])) {
          if (isPlusOrMinus) {
            localDateTimes[1] = plusOrMinus(localDateTimes[1], -augendOrMinuend, chronoUnits);
          } else if (augendOrMinuendType == 1) {
            result[0][1] = plusOrMinus(result[0][1], -augendOrMinuend, chronoUnits);
          } else {
            result[1][0] = plusOrMinus(result[1][0], augendOrMinuend, chronoUnits);
          }
          break;
        }
        //       x1——————y1
        // x2——————y2
        // 如果差集的开始时间（x1）和交集的结束时间相等，开始时间 += n，即差集与交集完全不相交
        if (localDateTimes[0].equals(intersections[1])) {
          if (isPlusOrMinus) {
            localDateTimes[0] = plusOrMinus(localDateTimes[0], augendOrMinuend, chronoUnits);
          } else if (augendOrMinuendType == 1) {
            result[0][1] = plusOrMinus(result[0][1], -augendOrMinuend, chronoUnits);
          } else {
            result[1][0] = plusOrMinus(result[1][0], augendOrMinuend, chronoUnits);
          }
          break;
        }
      }
    }
    return result;
  }

  /**
   * 获取两个时间段交集时的差集
   *
   * @param x1              时间段 1 开始时间
   * @param y1              时间段 1 结束时间
   * @param x2              时间段 2 开始时间
   * @param y2              时间段 2 结束时间
   * @param augendOrMinuend 加减数量，避免差集与交集相交，为 0 时忽略
   *                        <p>
   *                        x1——————y1
   *                        |     x2——————y2
   *                        时间段 1 在时间段 2 前面时，差集的第一段的结束时间（y1）减。
   *                        |     x1——————y1
   *                        x2——————y2
   *                        时间段 1 在时间段 2 后面时，差集的第二段的开始时间（x1）加；
   * @param chronoUnits     加减数量的时间周期
   * @return 差集
   */
  public static LocalDateTime[][] getDifferenceSetsByIntersection(@NonNull final LocalDateTime x1, @NonNull final LocalDateTime y1, @NonNull final LocalDateTime x2, @NonNull final LocalDateTime y2, final long augendOrMinuend, @NonNull final ChronoUnit... chronoUnits) {
    return getDifferenceSetsByIntersection(x1, y1, x2, y2, 0, augendOrMinuend, chronoUnits);
  }

  /**
   * 获取两个时间段交集时的差集，如果差集和交集有相交就加减 1 毫秒
   *
   * <p>
   * x1——————y1
   * |     x2——————y2
   * 时间段 1 在时间段 2 前面时，差集的第一段的结束时间（y1）减 1 毫秒
   * |     x1——————y1
   * x2——————y2
   * 时间段 1 在时间段 2 后面时，差集的第二段的开始时间（x1）加 1 毫秒
   *
   * @param x1 时间段 1 开始时间
   * @param y1 时间段 1 结束时间
   * @param x2 时间段 2 开始时间
   * @param y2 时间段 2 结束时间
   * @return 差集
   */
  public static LocalDateTime[][] getDifferenceSetsByIntersection(@NonNull final LocalDateTime x1, @NonNull final LocalDateTime y1, @NonNull final LocalDateTime x2, @NonNull final LocalDateTime y2) {
    return getDifferenceSetsByIntersection(x1, y1, x2, y2, 1, ChronoUnit.MILLIS);
  }
  // endregion 交集差集并集


  /**
   * 转换星期字符串为星期数组
   *
   * @param weeks      星期字符串
   * @param splitRegex 星期分隔符
   * @return 星期数组
   */
  public static String[] convertWeeks(@NonNull String weeks, @NonNull final String splitRegex) {
    // 去除星期字符串中除了 1-7 和 , 之外的字符
    if (RegExUtils.isMatch("[^,1-7]*", weeks)) {
      weeks = weeks.replaceAll("[^,1-7]*", "");
    }
    if (StringUtils.isBlank(weeks)) {
      return new String[]{};
    }
    return weeks.split(splitRegex);
  }

  /**
   * 转换星期字符串为星期数组
   *
   * @param weeks 星期字符串
   * @return 星期数组
   */
  public static String[] convertWeeks(@NonNull final String weeks) {
    String regex = ",";
    if (!weeks.contains(regex)) {
      regex = "";
    }
    return convertWeeks(weeks, regex);
  }

  /**
   * 获取符合周几的时间
   *
   * @param times 时间
   * @param weeks 周几，逗号或者无分隔，1 代表周一
   * @return 符合周几的时间
   */
  public static List<LocalDateTime> getByWeeks(@NonNull final String weeks, @NonNull final LocalDateTime... times) {
    if (CollectionUtils.isAllEmpty(times)) {
      return null;
    }
    List<LocalDateTime> timeList = null;
    if (!CollectionUtils.sizeIsEmpty(times)) {
      List<String> weekList = Arrays.asList(convertWeeks(weeks));
      timeList = new ArrayList<>(Arrays.asList(times));
      // 周几中不包含就去除
      timeList.removeIf(time -> !weekList.contains(String.valueOf(time.getDayOfWeek().getValue())));
    }
    return timeList;
  }

  /**
   * 获取日期范围内的所有日期
   *
   * @param startTime 开始时间
   * @param endTime   结束时间
   * @param pattern   结果集元素格式
   * @return 日期范围内的所有日期
   */
  public static List<String> getByRange(@NonNull final LocalDateTime startTime, @NonNull final LocalDateTime endTime, @NonNull final String pattern) {
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    List<String> result = new ArrayList<>();
    long distance = ChronoUnit.DAYS.between(startTime, endTime);
    if (distance >= 1) {
      Stream.iterate(startTime, day -> day.plusDays(1)).limit(distance + 1).forEach(f -> result.add(format(f, pattern)));
    }
    return result;
  }

  /**
   * 获取日期范围内的所有指定星期，包含开始日期和结束日期
   *
   * @param startTime 开始时间
   * @param endTime   结束时间
   * @param weeks     周几，逗号或者无分隔，1 代表周一
   * @param pattern   结果集元素的格式
   * @return 所有指定星期的天集合
   */
  public static List<String> getByRangeAndWeeks(@NonNull final LocalDateTime startTime, @NonNull final LocalDateTime endTime, @NonNull final String weeks, @NonNull final String pattern) {
    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("Pattern: should not be blank");
    }
    List<String> result = new ArrayList<>();
    // 设置一周的开始为周一
    TemporalField field = WeekFields.of(DayOfWeek.of(1), 1).dayOfWeek();
    LocalDateTime dayByWeek;
    for (String week : convertWeeks(weeks)) {
      // 根据开始时间找到所在周对应星期的天
      dayByWeek = startTime.with(field, Long.parseLong(week));
      // 如果所在周对应星期的天 < 开始时间
      if (dayByWeek.isBefore(startTime)) {
        // 所在周对应星期的天 += 1 周
        dayByWeek = dayByWeek.plusWeeks(1);
      }
      // 循环：所在周对应星期的天 < 结束时间 或 所在周对应星期的天 == 结束时间
      while (dayByWeek.isBefore(endTime) || dayByWeek.isEqual(endTime)) {
        // 此天添加到结果集合中
        result.add(format(dayByWeek, pattern));
        // 所在周对应星期的天 += 1 周
        dayByWeek = dayByWeek.plusWeeks(1);
      }
    }
    return result;
  }

  /**
   * 获取日期范围内的所有指定星期，包含开始日期和结束日期
   *
   * @param startTime 开始时间
   * @param endTime   结束时间
   * @param weeks     周几，逗号或者无分隔，1 代表周一
   * @return 所有指定星期的天集合
   */
  public static List<LocalDateTime> getByRangeAndWeeks(@NonNull final LocalDateTime startTime, @NonNull final LocalDateTime endTime, @NonNull final String weeks) {
    List<LocalDateTime> result = new ArrayList<>();
    TemporalField field = WeekFields.of(DayOfWeek.of(1), 1).dayOfWeek();
    LocalDateTime tempDay;
    for (String week : convertWeeks(weeks)) {
      tempDay = startTime.with(field, Long.parseLong(week));
      if (tempDay.isBefore(startTime)) {
        tempDay = tempDay.plusWeeks(1);
      }
      while (tempDay.isBefore(endTime) || tempDay.isEqual(endTime)) {
        result.add(tempDay);
        tempDay = tempDay.plusWeeks(1);
      }
    }
    return result;
  }

  /**
   * 获取天在月中是第几周，到周一算新的周
   *
   * @param localDate 天
   * @return 第几周，从 1 开始
   */
  public static int getWeekOfMonth(@NonNull final LocalDate localDate) {
    int week = 1;
    // 获取这个月的第一天是周几
    LocalDate dayOfMonthDate = localDate.with(ChronoField.DAY_OF_MONTH, 1);
    int dayOfWeek = dayOfMonthDate.get(ChronoField.DAY_OF_WEEK);

    // 如果月的第一天不是周一
    if (dayOfWeek != 1) {
      // 获取下周一，往后最多只有五周
      dayOfMonthDate = dayOfMonthDate.plusDays(8 - dayOfMonthDate.getDayOfWeek().getValue());
      // 如果下周一 > 此天，则就是第一周
      if (dayOfMonthDate.isAfter(localDate)) {
        return 1;
      }
      week++;
    }
    // 就算月的第一天为周一，最多也只有 5 周
    for (int i = 1; i <= 5; i++) {
      // 每次 +7 天，即到了下一周
      dayOfMonthDate = dayOfMonthDate.plusDays(7);
      // 如果下个周一 > 此天
      if (dayOfMonthDate.isAfter(localDate)) {
        break;
      }
      // 如果下个周一 == 此天，周再 +1
      else if (dayOfMonthDate.isEqual(localDate)) {
        return week + 1;
      }
      week++;
    }
    return week;
  }

  /**
   * 获取这个月有几周，到周一算新的周
   *
   * @param localDate 天
   * @return 有几周，从 1 开始
   */
  public static int getWeeksOfMonth(@NonNull final LocalDate localDate) {
    int month = localDate.getMonthValue();
    // 获取这个月的第一天是周几
    LocalDate dayOfMonth = localDate.withDayOfMonth(1);
    int dayOfWeek = dayOfMonth.get(ChronoField.DAY_OF_WEEK);
    // 如果月的第一天为周一
    if (dayOfWeek == 1) {
      dayOfMonth = dayOfMonth.plusDays(28);
      // 1 号为周一的月最多只有 5 周，加 1 天如果是下月了就是 4 周
      if (dayOfMonth.plusDays(1).getMonthValue() != month) {
        return 4;
      }
      return 5;
    } else {
      // 获取最后一天是几号
      LocalDate lastDayOfMonth = max(dayOfMonth, ChronoField.DAY_OF_MONTH);
      int dayOfMonth1 = lastDayOfMonth.getDayOfMonth();
      if (dayOfMonth1 == 31) {
        // 本月 1 号 - (7 - 本月最后一天的周) + 36（往后最多只有 5 周，5 * 7），如果结果在下月，说明只有 5 周，否则有 6 周
        if (dayOfMonth.minusDays(7 - lastDayOfMonth.getDayOfWeek().getValue()).plusDays(35).getMonthValue() != month) {
          return 5;
        }
        return 6;
      } else if (dayOfMonth1 == 30) {
        if (dayOfMonth.minusDays(7 - lastDayOfMonth.getDayOfWeek().getValue()).plusDays(34).getMonthValue() != month) {
          return 5;
        }
        return 6;
      }
      return 5;
    }
  }

  /**
   * 获取月的周的开始天
   *
   * @param localDate 天
   * @return 月的周的开始天
   */
  public static LocalDate getStartDayOfWeekOfMonth(@NonNull final LocalDate localDate) {
    // 第几周
    int weekOfMonth = getWeekOfMonth(localDate);
    // 如果大于一周，说明开始天是周一
    if (weekOfMonth > 1) {
      return localDate.with(ChronoField.DAY_OF_WEEK, 1);
    }
    // 否则开始天是月的第一天
    return localDate.withDayOfMonth(1);
  }

  /**
   * 获取月的周的结束天
   *
   * @param localDate 天
   * @return 月的周的结束天
   */
  public static LocalDate getEndDayOfWeekOfMonth(@NonNull final LocalDate localDate) {
    // 总周数
    int weeksOfMonth = getWeeksOfMonth(localDate);
    // 第几周
    int weekOfMonth = getWeekOfMonth(localDate);
    if (weeksOfMonth == 4) {
      return localDate.withDayOfMonth(weekOfMonth * 7);
    } else {
      LocalDate dayOfMonth = localDate.withDayOfMonth(1);
      // 在第一周
      if (weekOfMonth == 1) {
        // 那就是第一周周日
        return dayOfMonth.plusDays(7 - dayOfMonth.getDayOfWeek().getValue());
      }
      // 在最后一周
      else if (weeksOfMonth == weekOfMonth) {
        // 那就是月的最后一天
        return DateUtils.max(localDate, ChronoField.DAY_OF_MONTH);
      } else {
        // 获取下周一
        dayOfMonth = dayOfMonth.plusDays(8 - dayOfMonth.getDayOfWeek().getValue());
        // 循环每次 +6 天到周日，如果周日 >= 天，那就是周日；此处 -2 是因为只需要判断中间的（总周数 - 2）周了
        for (int i = 0; i < weeksOfMonth - 2; i++) {
          // 每次 +7 天，即到下周一
          dayOfMonth = dayOfMonth.plusDays(7);
          // 如果下周一 > 此天，则为下周一 -1 天
          if (dayOfMonth.isAfter(localDate)) {
            return dayOfMonth.minusDays(1);
          }
          // 如果下周一 == 此天，则为下周日
          else if (dayOfMonth.isEqual(localDate)) {
            return dayOfMonth.plusDays(6);
          }
        }
        // 实际上走不到这
        return null;
      }
    }
  }

  /**
   * 获取月的周的开始天
   *
   * @param localDate   年月
   * @param weekOfMonth 在月的第几周，宽容模式、智能模式时允许超出指定年月
   * @return 月的周的开始天：宽容模式会超出指定年月；智能模式超出指定年月时为指定年月的最后一天；严格模式超出指定年月时报错
   */
  public static LocalDate getStartDayOfWeekOfMonth(@NonNull final LocalDate localDate, final int weekOfMonth) {
    if (weekOfMonth < 1) {
      throw new IllegalArgumentException("WeekOfMonth: should be greater than 0");
    }

    // 1. 从月的第一周周一，即月的第一天开始
    LocalDate dayOfMonth = localDate.withDayOfMonth(1);
    // 如果在第一周则直接返回
    if (weekOfMonth == 1) {
      return dayOfMonth;
    }
    // 2. 获取第二周周一，即下周一
    dayOfMonth = dayOfMonth.plusDays(8 - dayOfMonth.getDayOfWeek().getValue());
    if(weekOfMonth == 2) {
      return dayOfMonth;
    }
    // 因为上面已经是第二周了，加上循环中是先加减日期再 week++，所以从第三周开始
    int week = 3;
    // 宽容模式可能会超出指定年月
    ResolverStyle resolverStyle = DateFeature.getResolverStyle();
    // 当月的结束天
    LocalDate maxDayOfMonth = DateUtils.max(localDate, ChronoField.DAY_OF_MONTH);
    // 下次是否 +7 天
    boolean isPlusSevenDays = true;
    while (true) {
      if (isPlusSevenDays) {
        // 3. 每次 +7 天，即下周一
        dayOfMonth = dayOfMonth.plusDays(7);
      } else {
        // 5. 获取下周一
        dayOfMonth = dayOfMonth.plusDays(8 - dayOfMonth.getDayOfWeek().getValue());
        isPlusSevenDays = true;
      }
      // 如果是下月了
      if (dayOfMonth.isAfter(maxDayOfMonth)) {
        // 严格模式报错
        if (ResolverStyle.STRICT.equals(resolverStyle)) {
          throw new IllegalArgumentException("WeekOfMonth: should be less than 'week of month' or change 'DateFeature' to ResolverStyle.LENIENT or ResolverStyle.SMART");
        }
        // 智能模式返回指定年月的最后一周的周一
        else if (ResolverStyle.SMART.equals(resolverStyle)) {
          dayOfMonth = dayOfMonth.minusDays(dayOfMonth.getDayOfMonth());
          return dayOfMonth.minusDays(dayOfMonth.getDayOfWeek().getValue() - 1);
        }

        // 4. 那就取下月 1 号
        dayOfMonth = dayOfMonth.minusDays(dayOfMonth.getDayOfMonth() - 1);
        // 更新当月结束天
        maxDayOfMonth = DateUtils.max(dayOfMonth, ChronoField.DAY_OF_MONTH);
        // 下次就不是 +7 天了，而是获取下周一
        isPlusSevenDays = false;
      }

      // 6. 如果到达指定周了则返回
      if (week == weekOfMonth) {
        return dayOfMonth;
      }
      week++;
    }
  }

  /**
   * 获取月的周的结束天
   *
   * @param localDate   年月
   * @param weekOfMonth 在月的第几周，宽容模式、智能模式时允许超出指定年月
   * @return 月的周的结束天：宽容模式会超出指定年月；智能模式超出指定年月时为指定年月的最后一天；严格模式超出指定年月时报错
   */
  public static LocalDate getEndDayOfWeekOfMonth(@NonNull final LocalDate localDate, final int weekOfMonth) {
    if (weekOfMonth < 1) {
      throw new IllegalArgumentException("WeekOfMonth: should be greater than 0");
    }

    // 1. 从月的第一周的周日开始
    LocalDate dayOfMonth = localDate.withDayOfMonth(1).with(ChronoField.DAY_OF_WEEK, 7);
    // 如果在第一周则直接返回
    if (weekOfMonth == 1) {
      return dayOfMonth;
    }
    // 上面已经是第一周了，加上循环中是先加减日期再 week++，所以从第二周开始
    int week = 2;
    // 宽容模式可能会超出指定年月
    ResolverStyle resolverStyle = DateFeature.getResolverStyle();
    // 当月的结束天
    LocalDate maxDayOfMonth = DateUtils.max(localDate, ChronoField.DAY_OF_MONTH);
    // 下次是否 +7 天
    boolean isPlusSevenDays = true;
    while (true) {
      if (isPlusSevenDays) {
        // 2. 每次 +7 天，即下周日
        dayOfMonth = dayOfMonth.plusDays(7);
      } else {
        // 4. 获取下月第一周的周日
        dayOfMonth = dayOfMonth.plusDays(1).with(ChronoField.DAY_OF_WEEK, 7);
        isPlusSevenDays = true;
      }

      // 如果是下月了
      if (dayOfMonth.isAfter(maxDayOfMonth)) {
        // 严格模式报错
        if (ResolverStyle.STRICT.equals(resolverStyle)) {
          throw new IllegalArgumentException("WeekOfMonth: should be less than 'week of month' or change 'DateFeature' to ResolverStyle.LENIENT or ResolverStyle.SMART");
        }
        // 智能模式返回指定年月的最后一天
        else if (ResolverStyle.SMART.equals(resolverStyle)) {
          return dayOfMonth.minusDays(dayOfMonth.getDayOfMonth());
        }

        // 更新当月结束天
        maxDayOfMonth = DateUtils.max(dayOfMonth, ChronoField.DAY_OF_MONTH);
        // !(为 7 号 && 为周日)，比如 2022-07 ~ 2022-08，+7 天正常往下，不需要回退时间
        if (!(dayOfMonth.getDayOfMonth() == 7 && dayOfMonth.getDayOfWeek().getValue() == 7)) {
          // 3. 那就取指定年月的最后一天
          dayOfMonth = dayOfMonth.minusDays(dayOfMonth.getDayOfMonth());
          // 下次就不是 +7 天了，而是获取下周日
          isPlusSevenDays = false;
        }
      }

      // 5. 如果到达指定周了则返回
      if (week == weekOfMonth) {
        return dayOfMonth;
      }
      week++;
    }
  }
}
