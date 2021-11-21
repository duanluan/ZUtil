package top.zhogjianhao;

import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * 时间工具类
 *
 * @author duanluan
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

  // region java.time 下的类相关使用
  public static final String PATTERN_UUUU_MM_DD_HH_MM_SS = "uuuu-MM-dd HH:mm:ss";
  public static final String PATTERN_UUUUMMDDHHMMSS = "uuuuMMddHHmmss";
  public static final String PATTERN_UUUU_MM_DD_HH_MM = "uuuu-MM-dd HH:mm";
  public static final String PATTERN_UUUU_MM_DD = "uuuu-MM-dd";
  public static final String PATTERN_UUUU_MM = "uuuu-MM";
  // endregion

  // region java.util.Date 相关使用
  public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
  public static final String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
  public static final String PATTERN_YYYY_MM = "yyyy-MM";
  // endregion

  public static final String PATTERN_HH_MM_SS = "HH:mm:ss";
  public static final String PATTERN_HH_MM = "HH:mm";

  /**
   * 默认内容类型，比如月份是中文还是英文
   */
  private static final Locale DEFAULT_LOCALE;
  /**
   * 默认日期时间格式器
   */
  private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER;
  /**
   * 系统时区
   */
  public static final ZoneId SYSTEM_ZONE_ID;
  /**
   * 系统时区偏移
   */
  public static final ZoneOffset SYSTEM_ZONE_OFFSET;
  /**
   * 默认解析模式
   */
  public static final ResolverStyle DEFAULT_RESOLVER_STYLE;

  static {
    // 内容类型为英文
    DEFAULT_LOCALE = Locale.ENGLISH;
    // 默认时间格式：uuuu-MM-dd HH:mm:ss
    DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_UUUU_MM_DD_HH_MM_SS, DEFAULT_LOCALE);
    // 系统时区
    SYSTEM_ZONE_ID = ZoneId.systemDefault();
    // 系统时区偏移
    SYSTEM_ZONE_OFFSET = OffsetDateTime.now(SYSTEM_ZONE_ID).getOffset();
    // 解析模式为严格模式（https://rumenz.com/java-topic/java/date-time/resolverstyle-strict-date-parsing/index.html）
    DEFAULT_RESOLVER_STYLE = ResolverStyle.STRICT;
  }

  /**
   * Date 默认格式
   */
  public static String defaultDatePattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
  /**
   * LocalDate 默认格式
   */
  public static String defaultLocalDatePattern = PATTERN_UUUU_MM_DD;
  /**
   * LocalDateTime 默认格式
   */
  public static String defaultLocalDateTimePattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
  /**
   * LocalTime 默认格式
   */
  public static String defaultLocalTimePattern = PATTERN_HH_MM_SS;

  /**
   * 获取时间格式化构造器，并给不同时间级别赋默认值
   *
   * @param pattern       格式
   * @param fieldValueMap 时间类型和值
   * @return 时间格式构造器
   */
  public static DateTimeFormatterBuilder getFormatterBuilder(@NotNull String pattern, Map<TemporalField, Long> fieldValueMap) {
    // 根据格式创建时间格式化构造器
    DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder().appendPattern(pattern);

    // 如果格式中存在特定字符，则不赋默认值
    TemporalField maybeExistTemporalField = ChronoField.YEAR;
    if (!pattern.toLowerCase().contains("y") && fieldValueMap.containsKey(maybeExistTemporalField)) {
      formatterBuilder.parseDefaulting(maybeExistTemporalField, fieldValueMap.get(maybeExistTemporalField));
      fieldValueMap.remove(maybeExistTemporalField);
    }
    maybeExistTemporalField = ChronoField.MONTH_OF_YEAR;
    if (!pattern.contains("M") && !pattern.contains("L") && fieldValueMap.containsKey(maybeExistTemporalField)) {
      formatterBuilder.parseDefaulting(maybeExistTemporalField, fieldValueMap.get(maybeExistTemporalField));
      fieldValueMap.remove(maybeExistTemporalField);
    }
    maybeExistTemporalField = ChronoField.DAY_OF_MONTH;
    if (!pattern.toLowerCase().contains("d") && !pattern.contains("F") && fieldValueMap.containsKey(maybeExistTemporalField)) {
      formatterBuilder.parseDefaulting(maybeExistTemporalField, fieldValueMap.get(maybeExistTemporalField));
      fieldValueMap.remove(maybeExistTemporalField);
    }
    maybeExistTemporalField = ChronoField.HOUR_OF_DAY;
    if (!pattern.toLowerCase().contains("h") && !pattern.toLowerCase().contains("k") && fieldValueMap.containsKey(maybeExistTemporalField)) {
      formatterBuilder.parseDefaulting(maybeExistTemporalField, fieldValueMap.get(maybeExistTemporalField));
      fieldValueMap.remove(maybeExistTemporalField);
    }

    // 循环给不同时间级别赋默认值
    for (TemporalField temporalField : fieldValueMap.keySet()) {
      formatterBuilder.parseDefaulting(temporalField, fieldValueMap.get(temporalField));
    }
    return formatterBuilder;
  }

  /**
   * 获取默认的时间格式器（严格模式），对应时间级别没有就赋默认值：1970-01-01 00:00:00.00000000
   *
   * @param pattern 格式
   * @param locale  区域
   * @param zoneId  时区
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NotNull String pattern, Locale locale, ZoneId zoneId) {
    Map<TemporalField, Long> fieldValueMap = new HashMap<>();
    fieldValueMap.put(ChronoField.YEAR, 1970L);
    fieldValueMap.put(ChronoField.MONTH_OF_YEAR, 1L);
    fieldValueMap.put(ChronoField.DAY_OF_MONTH, 1L);
    fieldValueMap.put(ChronoField.HOUR_OF_DAY, 0L);
    DateTimeFormatter dateTimeFormatter;
    DateTimeFormatterBuilder formatterBuilder = getFormatterBuilder(pattern, fieldValueMap);
    if (locale != null) {
      dateTimeFormatter = formatterBuilder.toFormatter(locale);
    } else {
      dateTimeFormatter = formatterBuilder.toFormatter();
    }
    dateTimeFormatter.withResolverStyle(DEFAULT_RESOLVER_STYLE);
    if (zoneId != null) {
      dateTimeFormatter.withZone(zoneId);
    }
    return dateTimeFormatter;
  }

  /**
   * 获取默认的时间格式器（严格模式），对应时间级别没有就赋默认值：1970-01-01 00:00:00.0
   *
   * @param pattern 格式
   * @param locale  区域
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NotNull String pattern, @NotNull Locale locale) {
    return getDefaultFormatter(pattern, locale, null);
  }

  /**
   * 获取默认的时间格式器（严格模式），对应时间级别没有就赋默认值：1970-01-01 00:00:00.0
   *
   * @param pattern 格式
   * @param zoneId  时区
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NotNull String pattern, @NotNull ZoneId zoneId) {
    return getDefaultFormatter(pattern, null, zoneId);
  }

  /**
   * 获取默认的时间格式器（严格模式），对应时间级别没有就赋默认值：1970-01-01 00:00:00.0，内容格式为英文
   *
   * @param pattern 格式
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NotNull String pattern) {
    return getDefaultFormatter(pattern, DEFAULT_LOCALE);
  }

  /**
   * 转换需要格式化的字符串，比如英文月份转换为首字母大写
   *
   * @param source  被转换的字符串
   * @param pattern 转换格式
   * @return 转换后的字符串
   */
  private static String convertByPattern(@NotNull String source, @NotNull String pattern) {
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
   * 格式化为指定格式的字符串
   *
   * @param date    时间对象
   * @param pattern 格式
   * @return 格式化后的字符串
   */
  public static String format(@NotNull Date date, @NotNull String pattern) {
    return new SimpleDateFormat(pattern).format(date);
  }

  /**
   * 格式化为 DateUtils#defaultDatePattern
   *
   * @param date
   * @return
   */
  public static String format(@NotNull Date date) {
    return format(date, defaultDatePattern);
  }

  /**
   * 格式化为指定格式的字符串
   *
   * @param temporal 时间对象
   * @param pattern  格式
   * @return 格式化后的字符串
   */
  public static String format(@NotNull Temporal temporal, @NotNull String pattern) {
    return getDefaultFormatter(pattern).format(temporal);
  }

  /**
   * 格式化为 DateUtils#defaultLocalDatePattern/defaultLocalDateTimePattern/defaultLocalTimePattern
   *
   * @param temporal 时间对象
   * @return 格式化后的字符串
   */
  public static String format(@NotNull Temporal temporal) {
    if (temporal == null) {
      return null;
    }
    String pattern = null;
    if (temporal instanceof LocalDate) {
      pattern = defaultLocalDatePattern;
    } else if (temporal instanceof LocalDateTime) {
      pattern = defaultLocalDateTimePattern;
    } else if (temporal instanceof LocalTime) {
      pattern = defaultLocalTimePattern;
    }
    return format(temporal, pattern);
  }

  /**
   * 时间戳解析为 Date
   *
   * @param timeStamp 被解析的时间戳
   * @return Date 对象
   */
  public static Date parseDate(@NotNull long timeStamp) {
    return new Date(timeStamp);
  }

  /**
   * String 解析为 Date，根据被解析字符串的长度判断格式
   * <p>
   * 19：DateUtils#PATTERN_UUUU_MM_DD_HH_MM_SS
   * <br>16：DateUtils#PATTERN_UUUU_MM_DD_HH_MM
   * <br>10：DateUtils#PATTERN_UUUU_MM_DD
   * <br>7：DateUtils#PATTERN_UUUU_MM
   * <br>8：DateUtils#PATTERN_HH_MM_SS
   * <br>5：DateUtils#PATTERN_HH_MM
   *
   * @param source 被解析的字符串
   * @return Date 对象
   */
  public static Date parseDate(@NotNull String source) {
    try {
      // 如果需要解析的字符串中含 - 或 :
      if (source.contains("-") || source.contains(":")) {
        String pattern = null;
        switch (source.length()) {
          case 19:
            pattern = PATTERN_UUUU_MM_DD_HH_MM_SS;
            break;
          case 16:
            pattern = PATTERN_UUUU_MM_DD_HH_MM;
            break;
          case 10:
            pattern = PATTERN_UUUU_MM_DD;
            break;
          case 7:
            pattern = PATTERN_UUUU_MM;
            break;
          case 8:
            pattern = PATTERN_HH_MM_SS;
            break;
          case 5:
            pattern = PATTERN_HH_MM;
            break;
          default:
        }
        if (pattern != null) {
          return new SimpleDateFormat(pattern).parse(source);
        }
      }
    } catch (ParseException e) {
      log.error(e.getMessage(), e);
      return null;
    }
    return null;
  }

  /**
   * 时间戳解析为 LocalDate
   *
   * @param timeStamp 被解析的时间戳
   * @param zoneId    时区
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NotNull long timeStamp, @NotNull ZoneId zoneId) {
    return Instant.ofEpochMilli(timeStamp).atZone(zoneId).toLocalDate();
  }

  /**
   * 时间戳解析为 LocalDate
   *
   * @param timeStamp 被解析的时间戳
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NotNull long timeStamp) {
    return parseLocalDate(timeStamp, SYSTEM_ZONE_ID);
  }

  /**
   * String 解析为 LocalDate
   *
   * @param source  被解析的字符串
   * @param pattern 格式
   * @param zoneId  时区
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NotNull String source, @NotNull String pattern, @NotNull ZoneId zoneId) {
    source = convertByPattern(source, pattern);
    return LocalDate.parse(source, getDefaultFormatter(pattern, zoneId));
  }

  /**
   * String 解析为 LocalDate
   *
   * @param source  被解析的字符串
   * @param pattern 格式
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NotNull String source, @NotNull String pattern) {
    return parseLocalDate(source, pattern, SYSTEM_ZONE_ID);
  }


  /**
   * String 解析为 LocalDate
   *
   * @param source   被解析的字符串
   * @param zoneId   时区
   * @param patterns 多钟格式
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NotNull String source, @NotNull ZoneId zoneId, @NotNull String... patterns) {
    LocalDate result = null;
    for (String pattern : patterns) {
      try {
        result = parseLocalDate(source, pattern, zoneId);
      } catch (Exception ignore) {
      }
    }
    return result;
  }

  /**
   * String 解析为 LocalDate
   *
   * @param source   被解析的字符串
   * @param patterns 多钟格式
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NotNull String source, @NotNull String... patterns) {
    LocalDate result = null;
    for (String pattern : patterns) {
      try {
        result = parseLocalDate(source, pattern);
      } catch (Exception ignore) {
      }
    }
    return result;
  }

  /**
   * String 解析为 LocalDate，根据被解析字符串的长度判断格式
   * <p>
   * 10：DateUtils#PATTERN_UUUU_MM_DD
   * <br>7：DateUtils#PATTERN_UUUU_MM
   *
   * @param source 被解析的字符串
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(@NotNull String source) {
    int length = source.length();

    String pattern = null;
    if (source.contains("-") || source.contains(":")) {
      switch (length) {
        case 10:
          pattern = PATTERN_UUUU_MM_DD;
          break;
        case 7:
          pattern = PATTERN_UUUU_MM;
          break;
      }
      if (pattern != null) {
        return parseLocalDate(source, pattern);
      }
    }
    return null;
  }

  /**
   * 时间戳解析为 LocalDateTime
   *
   * @param timeStamp 被解析的时间戳
   * @param zoneId    时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NotNull long timeStamp, @NotNull ZoneId zoneId) {
    return Instant.ofEpochMilli(timeStamp).atZone(zoneId).toLocalDateTime();
  }

  /**
   * 时间戳解析为 LocalDateTime
   *
   * @param timeStamp 被解析的时间戳
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NotNull long timeStamp) {
    return parseLocalDateTime(timeStamp, SYSTEM_ZONE_ID);
  }

  /**
   * String 解析为 LocalDateTime
   *
   * @param source  被解析的字符串
   * @param pattern 格式
   * @param zoneId  时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NotNull String source, @NotNull String pattern, @NotNull ZoneId zoneId) {
    if (StringUtils.isBlank(source)) {
      return null;
    }
    source = convertByPattern(source, pattern);
    return LocalDateTime.parse(source, getDefaultFormatter(pattern, zoneId));
  }

  /**
   * String 解析为 LocalDateTime
   *
   * @param source  被解析的字符串
   * @param pattern 格式
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NotNull String source, @NotNull String pattern) {
    return parseLocalDateTime(source, pattern, SYSTEM_ZONE_ID);
  }

  /**
   * String 解析为 LocalDateTime
   *
   * @param source   被解析的字符串
   * @param zoneId   时区
   * @param patterns 多种格式
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NotNull String source, @NotNull ZoneId zoneId, @NotNull String... patterns) {
    LocalDateTime result = null;
    for (String pattern : patterns) {
      try {
        result = parseLocalDateTime(source, pattern, zoneId);
      } catch (Exception ignore) {
      }
    }
    return result;
  }

  /**
   * String 解析为 LocalDateTime
   *
   * @param source   被解析的字符串
   * @param patterns 多种格式
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NotNull String source, @NotNull String... patterns) {
    LocalDateTime result = null;
    for (String pattern : patterns) {
      try {
        result = parseLocalDateTime(source, pattern);
      } catch (Exception ignore) {
      }
    }
    return result;
  }

  /**
   * String 解析为 LocalDateTime，根据被解析字符串的长度判断格式
   * <p>
   * 19：DateUtils#PATTERN_UUUU_MM_DD_HH_MM_SS
   * <br>16：DateUtils#PATTERN_UUUU_MM_DD_HH_MM
   * <br>10：DateUtils#PATTERN_UUUU_MM_DD
   * <br>7：DateUtils#PATTERN_UUUU_MM
   * <br>8：DateUtils#PATTERN_HH_MM_SS
   * <br>5：DateUtils#PATTERN_HH_MM
   *
   * @param source 被解析的字符串
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NotNull String source) {
    String pattern = null;
    if (source.contains("-") || source.contains(":")) {
      switch (source.length()) {
        case 19:
          pattern = PATTERN_UUUU_MM_DD_HH_MM_SS;
          break;
        case 16:
          pattern = PATTERN_UUUU_MM_DD_HH_MM;
          break;
        case 10:
          pattern = PATTERN_UUUU_MM_DD;
          break;
        case 7:
          pattern = PATTERN_UUUU_MM;
          break;
        case 8:
          pattern = PATTERN_HH_MM_SS;
          break;
        case 5:
          pattern = PATTERN_HH_MM;
          break;
        default:
      }
      if (pattern != null) {
        return parseLocalDateTime(source, pattern);
      }
    }
    return null;
  }

  /**
   * 时间戳解析为 LocalTime
   *
   * @param timeStamp 被解析的时间戳
   * @param zoneId    时区
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NotNull long timeStamp, @NotNull ZoneId zoneId) {
    return Instant.ofEpochMilli(timeStamp).atZone(zoneId).toLocalTime();
  }

  /**
   * 时间戳解析为 LocalTime
   *
   * @param timeStamp 被解析的时间戳
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NotNull long timeStamp) {
    return parseLocalTime(timeStamp, SYSTEM_ZONE_ID);
  }

  /**
   * String 解析为 LocalTime
   *
   * @param source  被解析的字符串
   * @param pattern 格式
   * @param zoneId  时区
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NotNull String source, @NotNull String pattern, @NotNull ZoneId zoneId) {
    if (StringUtils.isBlank(source)) {
      return null;
    }
    source = convertByPattern(source, pattern);
    return LocalTime.parse(source, getDefaultFormatter(pattern, zoneId));
  }

  /**
   * String 解析为 LocalTime
   *
   * @param source  被解析的字符串
   * @param pattern 格式
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NotNull String source, @NotNull String pattern) {
    return parseLocalTime(source, pattern, SYSTEM_ZONE_ID);
  }

  /**
   * String 解析为 LocalTime
   *
   * @param source   被解析的字符串
   * @param zoneId   时区
   * @param patterns 多种格式
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NotNull String source, @NotNull ZoneId zoneId, @NotNull String... patterns) {
    LocalTime result = null;
    for (String pattern : patterns) {
      try {
        result = parseLocalTime(source, pattern, zoneId);
      } catch (Exception ignore) {
      }
    }
    return result;
  }

  /**
   * String 解析为 LocalTime
   *
   * @param source   被解析的字符串
   * @param patterns 多种格式
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NotNull String source, @NotNull String... patterns) {
    LocalTime result = null;
    for (String pattern : patterns) {
      try {
        result = parseLocalTime(source, pattern);
      } catch (Exception ignore) {
      }
    }
    return result;
  }

  /**
   * String 解析为 LocalTime，根据被解析字符串的长度判断格式
   * <p>
   * 8：DateUtils#PATTERN_HH_MM_SS
   * <br>5：DateUtils#PATTERN_HH_MM
   *
   * @param source 被解析的字符串
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(@NotNull String source) {
    String pattern = null;
    if (source.contains("-") || source.contains(":")) {
      switch (source.length()) {
        case 8:
          pattern = PATTERN_HH_MM_SS;
          break;
        case 5:
          pattern = PATTERN_HH_MM;
          break;
        default:
      }
      if (pattern != null) {
        return parseLocalTime(source, pattern);
      }
    }
    return null;
  }

  /**
   * java.util.time 对象 转 Date
   *
   * @param temporal 时间对象
   * @return Date 对象
   */
  public static Date toDate(@NotNull Temporal temporal) {
    if (temporal instanceof LocalDate) {
      return Date.from(((LocalDate) temporal).atStartOfDay().atZone(SYSTEM_ZONE_ID).toInstant());
    } else if (temporal instanceof LocalDateTime) {
      return Date.from(((LocalDateTime) temporal).atZone(SYSTEM_ZONE_ID).toInstant());
    } else if (temporal instanceof LocalTime) {
      return Date.from(minLocalDateTime(temporal).atZone(SYSTEM_ZONE_ID).toInstant());
    }
    return null;
  }

  /**
   * Date 转 LocalDate
   *
   * @param date Date 对象
   * @return LocalDate 对象
   */
  public static LocalDate toLocalDate(@NotNull Date date) {
    return parseLocalDate(date.getTime());
  }

  /**
   * Date 转 LocalDateTime
   *
   * @param date Date 对象
   * @return LocalDateTime 对象
   */
  public static LocalDateTime toLocalDateTime(@NotNull Date date) {
    return parseLocalDateTime(date.getTime());
  }

  /**
   * Date 转 LocalTime
   *
   * @param date Date 对象
   * @return LocalTime 对象
   */
  public static LocalTime toLocalTime(@NotNull Date date) {
    return parseLocalTime(date.getTime());
  }

  /**
   * 最小时间：1970-01-01 00:00:00.0
   *
   * @return LocalDateTime 对象
   */
  public static LocalDateTime minLocalDateTime(@NotNull Temporal temporal) {
    if (temporal instanceof LocalDateTime) {
      return (LocalDateTime) temporal;
    }
    LocalDateTime localDateTime = LocalDateTime.now();
    if (temporal instanceof LocalDate) {
      localDateTime.with(ChronoField.YEAR, ((LocalDate) temporal).getYear());
      localDateTime.with(ChronoField.MONTH_OF_YEAR, ((LocalDate) temporal).getMonthValue());
      localDateTime.with(ChronoField.DAY_OF_MONTH, ((LocalDate) temporal).getDayOfMonth());
    } else {
      localDateTime.with(ChronoField.YEAR, 1970);
      localDateTime.with(ChronoField.MONTH_OF_YEAR, 1);
      localDateTime.with(ChronoField.DAY_OF_MONTH, 1);
    }
    if (temporal instanceof LocalTime) {
      localDateTime.with(ChronoField.HOUR_OF_DAY, ((LocalTime) temporal).getHour());
      localDateTime.with(ChronoField.MINUTE_OF_HOUR, ((LocalTime) temporal).getMinute());
      localDateTime.with(ChronoField.SECOND_OF_MINUTE, ((LocalTime) temporal).getSecond());
      localDateTime.with(ChronoField.MILLI_OF_SECOND, temporal.getLong(ChronoField.MILLI_OF_SECOND));
    } else {
      localDateTime.with(ChronoField.HOUR_OF_DAY, 0);
      localDateTime.with(ChronoField.MINUTE_OF_HOUR, 0);
      localDateTime.with(ChronoField.SECOND_OF_MINUTE, 0);
      localDateTime.with(ChronoField.MILLI_OF_SECOND, 0);
    }
    return localDateTime;
  }

  /**
   * 最小时间：1970-01-01 00:00:00.0
   *
   * @return LocalDateTime 对象
   */
  public static LocalDateTime minLocalDateTime() {
    LocalDateTime localDateTime = LocalDateTime.now();
    localDateTime.with(ChronoField.YEAR, 1970);
    localDateTime.with(ChronoField.MONTH_OF_YEAR, 1);
    localDateTime.with(ChronoField.DAY_OF_MONTH, 1);
    localDateTime.with(ChronoField.HOUR_OF_DAY, 0);
    localDateTime.with(ChronoField.MINUTE_OF_HOUR, 0);
    localDateTime.with(ChronoField.SECOND_OF_MINUTE, 0);
    localDateTime.with(ChronoField.MILLI_OF_SECOND, 0);
    return localDateTime;
  }

  /**
   * 转换时区
   *
   * @param localDate 被转换时区的 LocalDate 对象
   * @param oldZoneId 旧时区
   * @param newZoneId 新时区
   * @return 转换时区后的 LocalDate 对象
   */
  public static LocalDate withZoneInstant(@NotNull LocalDate localDate, @NotNull ZoneId oldZoneId, @NotNull ZoneId newZoneId) {
    return localDate.atStartOfDay().atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDate();
  }

  /**
   * 转换时区
   *
   * @param localDateTime 被转换时区的 LocalDateTime 对象
   * @param oldZoneId     旧时区
   * @param newZoneId     新时区
   * @return 转换时区后的 LocalDateTime 对象
   */
  public static LocalDateTime withZoneInstant(@NotNull LocalDateTime localDateTime, @NotNull ZoneId oldZoneId, @NotNull ZoneId newZoneId) {
    return localDateTime.atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDateTime();
  }

  /**
   * 转换时区
   *
   * @param localTime 被转换时区的 LocalTime 对象
   * @param oldZoneId 旧时区
   * @param newZoneId 新时区
   * @return 转换时区后的 LocalTime 对象
   */
  public static LocalTime withZoneInstant(@NotNull LocalTime localTime, @NotNull ZoneId oldZoneId, @NotNull ZoneId newZoneId) {
    return minLocalDateTime(localTime).atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalTime();
  }

  /**
   * 当前时间字符串
   *
   * @return 当前时间字符串
   */
  public static String now() {
    return format(new Date());
  }

  /**
   * 当前时间字符串
   *
   * @param zoneId 时区
   * @return 当前时间字符串
   */
  public static String now(@NotNull ZoneId zoneId) {
    return format(LocalDateTime.now().atZone(zoneId));
  }

  /**
   * 今天开始时间
   *
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinDateTime() {
    return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
  }

  /**
   * 今天开始时间
   *
   * @param zoneId 时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinDateTime(@NotNull ZoneId zoneId) {
    return todayMinDateTime().atZone(zoneId).toLocalDateTime();
  }

  /**
   * 今天开始时间
   *
   * @return LocalTime 对象
   */
  public static LocalTime todayMinTime() {
    return todayMinDateTime().toLocalTime();
  }

  /**
   * 今天开始时间
   *
   * @param zoneId 时区
   * @return LocalTime 对象
   */
  public static LocalTime todayMinTime(@NotNull ZoneId zoneId) {
    return todayMinDateTime(zoneId).toLocalTime();
  }

  /**
   * 获取今天开始时间
   *
   * @return 今天开始时间字符串
   */
  public static String todayMinStr(@NotNull String pattern) {
    return format(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), pattern);
  }

  /**
   * 获取今天开始时间
   *
   * @return 今天开始时间字符串
   */
  public static String todayMinStr() {
    return format(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), defaultLocalTimePattern);
  }

  /**
   * 今天结束时间
   *
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxDateTime() {
    return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
  }

  /**
   * 今天结束时间
   *
   * @param zoneId 时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxDateTime(@NotNull ZoneId zoneId) {
    return todayMaxDateTime().atZone(zoneId).toLocalDateTime();
  }

  /**
   * 今天结束时间
   *
   * @return LocalTime 对象
   */
  public static LocalTime todayMaxTime() {
    return todayMaxDateTime().toLocalTime();
  }

  /**
   * 今天结束时间
   *
   * @param zoneId 时区
   * @return LocalTime 对象
   */
  public static LocalTime todayMaxTime(@NotNull ZoneId zoneId) {
    return todayMaxDateTime(zoneId).toLocalTime();
  }

  /**
   * 获取今天结束时间字符串
   *
   * @return 今天开始时间字符串
   */
  public static String todayMaxStr(@NotNull String pattern) {
    return format(LocalDateTime.of(LocalDate.now(), LocalTime.MAX), pattern);
  }

  /**
   * 获取今天结束时间字符串
   *
   * @return 今天开始时间字符串
   */
  public static String todayMaxStr() {
    return format(LocalDateTime.of(LocalDate.now(), LocalTime.MAX), defaultLocalTimePattern);
  }

  /**
   * 获取减去或加上指定类型数量的时间
   *
   * @param temporal        被加减的时间
   * @param augendOrMinuend 数量
   * @param chronoUnit      时间类型
   * @return 加减后的时间
   */
  public static <T extends Temporal> T plusOrMinus(@NotNull T temporal, @NotNull long augendOrMinuend, ChronoUnit chronoUnit) {
    if (chronoUnit == null) {
      chronoUnit = ChronoUnit.MILLIS;
    }
    if (augendOrMinuend < 0) {
      return (T) temporal.minus(Math.abs(augendOrMinuend), chronoUnit);
    } else if (augendOrMinuend > 0) {
      return (T) temporal.plus(augendOrMinuend, chronoUnit);
    }
    return temporal;
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
  public static boolean isIntersection(@NotNull LocalDateTime x1, @NotNull LocalDateTime y1, @NotNull LocalDateTime x2, @NotNull LocalDateTime y2) {
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
  public static LocalDateTime[] getIntersection(@NotNull LocalDateTime x1, @NotNull LocalDateTime y1, @NotNull LocalDateTime x2, @NotNull LocalDateTime y2) {
    if (!isIntersection(x1, y1, x2, y2)) {
      return null;
    }

    LocalDateTime[] result = new LocalDateTime[2];
    if (x1.isBefore(x2) || x1.isEqual(x2)) {
      result[0] = x2;
    } else if (x1.isAfter(x2)) {
      result[0] = x1;
    }
    if (y1.isBefore(y2) || y1.isEqual(y2)) {
      result[1] = y1;
    } else if (y1.isAfter(y2)) {
      result[1] = y2;
    }
    return result;
  }

  /**
   * 获取两个时间段交集时差集的时间段
   *
   * @param x1 时间段 1 开始时间
   * @param y1 时间段 1 结束时间
   * @param x2 时间段 2 开始时间
   * @param y2 时间段 2 结束时间
   * @return 差集
   */
  public static List<LocalDateTime[]> getDifferenceSetsByIntersectional(@NotNull LocalDateTime x1, @NotNull LocalDateTime y1, @NotNull LocalDateTime x2, @NotNull LocalDateTime y2) {
    // 交集的部分
    LocalDateTime[] intersections = getIntersection(x1, y1, x2, y2);
    if (intersections == null || intersections.length == 0) {
      return null;
    }

    // 获取差集
    List<LocalDateTime[]> result = new ArrayList<>();
    if (x1.isBefore(x2)) {
      result.add(new LocalDateTime[]{x1, x2});
    } else if (x1.isAfter(x2)) {
      result.add(new LocalDateTime[]{x2, x1});
    }
    if (y1.isBefore(y2)) {
      result.add(new LocalDateTime[]{y1, y2});
    } else if (y1.isAfter(y2)) {
      result.add(new LocalDateTime[]{y2, y1});
    }

    for (Iterator<LocalDateTime[]> iterator = result.iterator(); iterator.hasNext(); ) {
      LocalDateTime[] localDateTimes = iterator.next();

      // 如果差集的开始时间和交集的结束时间相等，开始时间 += 1
      if (localDateTimes[0].equals(intersections[1])) {
        localDateTimes[0] = plusOrMinus(localDateTimes[0], 1, ChronoUnit.DAYS);
      }
      // 如果差集的结束时间和交集的开始时间相等，结束时间 -= 1
      if (localDateTimes[1].equals(intersections[0])) {
        localDateTimes[1] = plusOrMinus(localDateTimes[1], -1, ChronoUnit.DAYS);
      }

      // 如果开始时间小于结束时间，删除这段时间
      if (localDateTimes[1].isBefore(localDateTimes[0])) {
        iterator.remove();
      }
    }
    return result;
  }
  // endregion 交集差集并集


  /**
   * 转换星期字符串为星期数组
   *
   * @param weeks      星期字符串
   * @param splitRegex 星期分隔符
   * @return 星期数组
   */
  public static String[] convertWeeks(@NotNull String weeks, @NotNull String splitRegex) {
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
  public static String[] convertWeeks(@NotNull String weeks) {
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
  public static List<LocalDateTime> getByWeeks(@NotNull String weeks, @NotNull LocalDateTime... times) {
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
  public static List<String> getByRange(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String pattern) {
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
   * @param weeks   周几，逗号或者无分隔，1 代表周一
   * @param pattern 结果集元素的格式
   * @return 所有指定星期的天集合
   */
  public static List<String> getByRangeAndWeeks(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String weeks, @NotNull String pattern) {
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
   * @param weeks 周几，逗号或者无分隔，1 代表周一
   * @return 所有指定星期的天集合
   */
  public static List<LocalDateTime> getByRangeAndWeeks(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String weeks) {
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
}
