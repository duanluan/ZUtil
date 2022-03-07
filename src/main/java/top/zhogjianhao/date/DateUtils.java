package top.zhogjianhao.date;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.text.WordUtils;
import top.zhogjianhao.CollectionUtils;
import top.zhogjianhao.RegExUtils;
import top.zhogjianhao.StringUtils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Stream;

import static top.zhogjianhao.date.DatePattern.*;

/**
 * 时间工具类
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

  /**
   * Date 对象最小年份
   */
  public static final int MIN_DATE_YEAR = 1970;

  /**
   * 默认内容类型，比如月份是中文还是英文
   */
  private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
  /**
   * 系统时区
   */
  public static final ZoneId SYSTEM_ZONE_ID = ZoneId.systemDefault();
  /**
   * 系统时区偏移
   */
  public static final ZoneOffset SYSTEM_ZONE_OFFSET = OffsetDateTime.now(SYSTEM_ZONE_ID).getOffset();
  /**
   * 默认解析模式：严格模式（https://rumenz.com/java-topic/java/date-time/resolverstyle-strict-date-parsing/index.html）
   */
  public static final ResolverStyle DEFAULT_RESOLVER_STYLE = ResolverStyle.STRICT;

  // static {
  //   // 内容类型为英文
  //   DEFAULT_LOCALE = Locale.ENGLISH;
  //   // 系统时区
  //   SYSTEM_ZONE_ID = ZoneId.systemDefault();
  //   // 系统时区偏移
  //   SYSTEM_ZONE_OFFSET = OffsetDateTime.now(SYSTEM_ZONE_ID).getOffset();
  //   // 严格模式
  //   DEFAULT_RESOLVER_STYLE = ResolverStyle.STRICT;
  // }

  /**
   * Date 默认格式
   */
  public static String defaultDatePattern = YYYY_MM_DD_HH_MM_SS;
  /**
   * LocalDate 默认格式
   */
  public static String defaultLocalDatePattern = UUUU_MM_DD;
  /**
   * LocalDateTime 默认格式
   */
  public static String defaultLocalDateTimePattern = YYYY_MM_DD_HH_MM_SS;
  /**
   * LocalTime 默认格式
   */
  public static String defaultLocalTimePattern = HH_MM_SS;

  /**
   * 获取时间格式化构造器，并给不同时间级别赋默认值
   *
   * @param pattern       格式
   * @param fieldValueMap 时间类型和值
   * @return 时间格式构造器
   */
  public static DateTimeFormatterBuilder getFormatterBuilder(@NonNull String pattern, Map<TemporalField, Long> fieldValueMap) {
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
      if ((lowercasePattern.contains("d") || pattern.contains("F")) && fieldValueMap.containsKey(maybeExistTemporalField)) {
        fieldValueMap.remove(maybeExistTemporalField);
      }
      maybeExistTemporalField = ChronoField.HOUR_OF_DAY;
      if ((lowercasePattern.contains("h") || lowercasePattern.contains("k")) && fieldValueMap.containsKey(maybeExistTemporalField)) {
        fieldValueMap.remove(maybeExistTemporalField);
      }
    }

    for (TemporalField temporalField : fieldValueMap.keySet()) {
      formatterBuilder.parseDefaulting(temporalField, fieldValueMap.get(temporalField));
    }
    return formatterBuilder;
  }

  /**
   * 获取默认的时间格式器（严格模式），对应时间级别没有就赋默认值：1970-01-01 00:00:00.00000000
   *
   * @param pattern  格式
   * @param locale   区域
   * @param atZoneId 时区
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NonNull String pattern, Locale locale, ZoneId atZoneId) {
    Map<TemporalField, Long> fieldValueMap = new HashMap<>();
    fieldValueMap.put(ChronoField.YEAR_OF_ERA, (long) 1);
    fieldValueMap.put(ChronoField.YEAR, 0L);
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
    if (atZoneId != null) {
      dateTimeFormatter = dateTimeFormatter.withZone(atZoneId);
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
  public static DateTimeFormatter getDefaultFormatter(@NonNull String pattern, @NonNull Locale locale) {
    return getDefaultFormatter(pattern, locale, null);
  }

  /**
   * 获取默认的时间格式器（严格模式），对应时间级别没有就赋默认值：1970-01-01 00:00:00.0
   *
   * @param pattern  格式
   * @param atZoneId 时区
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NonNull String pattern, @NonNull ZoneId atZoneId) {
    return getDefaultFormatter(pattern, null, atZoneId);
  }

  /**
   * 获取默认的时间格式器（严格模式），对应时间级别没有就赋默认值：1970-01-01 00:00:00.0，内容格式为英文
   *
   * @param pattern 格式
   * @return 时间格式器
   */
  public static DateTimeFormatter getDefaultFormatter(@NonNull String pattern) {
    return getDefaultFormatter(pattern, DEFAULT_LOCALE);
  }

  /**
   * 转换需要格式化的字符串，比如英文月份转换为首字母大写
   *
   * @param source  被转换的字符串
   * @param pattern 转换格式
   * @return 转换后的字符串
   */
  private static String convertByPattern(@NonNull String source, @NonNull String pattern) {
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
   * 格式化为指定时区和格式的字符串
   *
   * @param temporal 时间对象
   * @param zoneId   时区
   * @param pattern  格式
   * @return 指定时区和格式的字符串
   */
  public static String format(@NonNull Temporal temporal, ZoneId zoneId, @NonNull String pattern) {
    DateTimeFormatter dateTimeFormatter;
    if (pattern.equals(defaultLocalDateTimePattern)) {
      dateTimeFormatter = FORMATTER_YYYY_MM_DD_HH_MM_SS;
    } else if (pattern.equals(defaultLocalDatePattern)) {
      dateTimeFormatter = FORMATTER_YYYY_MM_DD;
    } else if (pattern.equals(defaultLocalTimePattern)) {
      dateTimeFormatter = FORMATTER_HH_MM_SS;
    } else {
      dateTimeFormatter = getDefaultFormatter(pattern);
    }
    if (zoneId != null) {
      // 因为 LocalDate 和 LocalTime 不存在时区信息，所以先根据当前时间补全为 LocalDateTime
      LocalDateTime localDateTime = null;
      if (temporal instanceof LocalDateTime) {
        localDateTime = ((LocalDateTime) temporal);
      } else if (temporal instanceof LocalDate) {
        localDateTime = ((LocalDate) temporal).atTime(LocalTime.now());
      } else if (temporal instanceof LocalTime) {
        localDateTime = ((LocalTime) temporal).atDate(LocalDate.now());
      }
      if (localDateTime != null) {
        return localDateTime.atZone(SYSTEM_ZONE_ID).withZoneSameInstant(zoneId).format(dateTimeFormatter);
      }
      if (temporal instanceof ZonedDateTime) {
        return ((ZonedDateTime) temporal).withZoneSameInstant(zoneId).format(dateTimeFormatter);
      }
    }
    return dateTimeFormatter.format(temporal);
  }

  /**
   * 格式化为指定时区和格式的字符串，格式根据时间对象的类型判断
   * <p>
   * LocalDateTime、ZonedDateTime：DateUtils#defaultLocalDatePattern<br>
   * LocalDate：DateUtils#defaultLocalDateTimePattern<br>
   * LocalTime：DateUtils#defaultLocalTimePattern
   *
   * @param temporal 时间对象
   * @param zoneId   时区
   * @return 指定时区和格式的字符串
   */
  public static String format(@NonNull Temporal temporal, ZoneId zoneId) {
    // 根据不同的时间类型获取不同的默认格式
    String pattern;
    if (temporal instanceof LocalDateTime || temporal instanceof ZonedDateTime) {
      pattern = defaultLocalDateTimePattern;
    } else if (temporal instanceof LocalDate) {
      pattern = defaultLocalDatePattern;
    } else if (temporal instanceof LocalTime) {
      pattern = defaultLocalTimePattern;
    } else {
      pattern = defaultLocalDateTimePattern;
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
  public static String format(@NonNull Temporal temporal, @NonNull String pattern) {
    return format(temporal, null, pattern);
  }

  /**
   * 格式化为指定格式的字符串，格式根据时间对象的类型判断
   * <p>
   * LocalDateTime、ZonedDateTime：DateUtils#defaultLocalDatePattern<br>
   * LocalDate：DateUtils#defaultLocalDateTimePattern<br>
   * LocalTime：DateUtils#defaultLocalTimePattern
   *
   * @param temporal 时间对象
   * @return 指定格式的字符串
   */
  public static String format(@NonNull Temporal temporal) {
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
  public static String format(@NonNull Date date, ZoneId zoneId, @NonNull String pattern) {
    if (StringUtils.isBlank(pattern)) {
      return null;
    }
    ZonedDateTime zonedDateTime = date.toInstant().atZone(SYSTEM_ZONE_ID);
    if (zoneId != null) {
      zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
    }
    return format(zonedDateTime.toLocalDateTime(), pattern);
  }

  /**
   * 格式化为指定时区的字符串
   *
   * @param date   时间对象
   * @param zoneId 时区
   * @return 指定时区的字符串
   */
  public static String format(@NonNull Date date, @NonNull ZoneId zoneId) {
    return format(date, zoneId, defaultDatePattern);
  }

  /**
   * 格式化为指定格式的字符串
   *
   * @param date    时间对象
   * @param pattern 格式
   * @return 指定格式的字符串
   */
  public static String format(@NonNull Date date, @NonNull String pattern) {
    if (StringUtils.isBlank(pattern)) {
      return null;
    }
    return format(date, null, pattern);
  }

  /**
   * 格式化为 DateUtils#defaultDatePattern 的字符串
   *
   * @param date 时间对象
   * @return DateUtils#defaultDatePattern 格式的字符串
   */
  public static String format(@NonNull Date date) {
    return format(date, defaultDatePattern);
  }

  /**
   * 转换为指定时区的 Date 对象
   *
   * @param temporal 时间对象
   * @param zoneId   时区
   * @return 指定时区的 Date 对象
   */
  public static Date toDate(@NonNull Temporal temporal, ZoneId zoneId) {
    if (temporal instanceof LocalDateTime) {
      LocalDateTime localDateTime = ((LocalDateTime) temporal);
      final ZonedDateTime[] zonedDateTime = new ZonedDateTime[1];
      if (zoneId != null) {
        // 设置时区为 当前偏移量 - (指定偏移量 - 当前偏移量)，比如 zoneId 偏移量为 10，理论上 toDate 后时间需要 +2，但是因为 Date.from 之后是反的，所以真实偏移量要为 6 才对，假设当前偏移量为 8，那 8 - (10 -8) = 6
        zoneId.getRules().getOffset(localDateTime).query((TemporalQuery<Integer>) temporal1 -> {
          zonedDateTime[0] = localDateTime.atZone(ZoneOffset.ofTotalSeconds(SYSTEM_ZONE_OFFSET.getTotalSeconds() - (((ZoneOffset) temporal1).getTotalSeconds() - SYSTEM_ZONE_OFFSET.getTotalSeconds())));
          return null;
        });
      } else {
        zonedDateTime[0] = localDateTime.atZone(SYSTEM_ZONE_ID);
      }
      return Date.from(zonedDateTime[0].toInstant());
    } else if (temporal instanceof LocalDate) {
      return toDate(((LocalDate) temporal).atStartOfDay(), zoneId);
    } else if (temporal instanceof LocalTime) {
      return toDate(((LocalTime) temporal).atDate(LocalDate.of(MIN_DATE_YEAR, 1, 1)), zoneId);
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
        zoneId.getRules().getOffset(localDateTime).query((TemporalQuery<Integer>) temporal1 -> {
          zonedDateTime[0] = localDateTime.atZone(ZoneOffset.ofTotalSeconds(zonedDateTimeTotalSeconds[0] - (((ZoneOffset) temporal1).getTotalSeconds() - zonedDateTimeTotalSeconds[0])));
          return null;
        });
      }
      return Date.from(zonedDateTime[0].toInstant());
    }
    return null;
  }

  /**
   * 转换为 Date 对象
   *
   * @param temporal 时间对象
   * @return Date 对象
   */
  public static Date toDate(@NonNull Temporal temporal) {
    return toDate(temporal, null);
  }

  /**
   * 解析为指定时区的 LocalDateTime 对象
   *
   * @param timeStamp 时间戳
   * @param zoneId    时区
   * @return 指定时区的 LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(long timeStamp, ZoneId zoneId) {
    ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timeStamp).atZone(SYSTEM_ZONE_ID);
    if (zoneId != null) {
      zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
    }
    return zonedDateTime.toLocalDateTime();
  }

  /**
   * 解析为 LocalDateTime 对象
   *
   * @param timeStamp 时间戳
   * @return LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(long timeStamp) {
    return parseLocalDateTime(timeStamp, null);
  }

  /**
   * 满足任意格式时解析为指定时区的 LocalDateTime 对象
   *
   * @param source   字符串
   * @param zoneId   时区
   * @param patterns 多种格式
   * @return 指定时区的 LocalDateTime 对象
   */
  public static LocalDateTime parseLocalDateTime(@NonNull String source, ZoneId zoneId, @NonNull String... patterns) {
    if (StringUtils.isAllBlank(patterns)) {
      return null;
    }
    for (String pattern : patterns) {
      source = convertByPattern(source, pattern);
      ZonedDateTime zonedDateTime = LocalDateTime.parse(source, getDefaultFormatter(pattern)).atZone(SYSTEM_ZONE_ID);
      if (zoneId != null) {
        zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
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
  public static LocalDateTime parseLocalDateTime(@NonNull String source, ZoneId zoneId) {
    String pattern = null;
    if (source.contains("-") || source.contains("/") || source.contains(".") || source.contains(":")) {
      switch (source.length()) {
        case 19:
          if (source.contains("-")) {
            pattern = UUUU_MM_DD_HH_MM_SS;
          } else if (source.contains("/")) {
            pattern = UUUU_MM_DD_SLASH_HH_MM_SS;
          } else if (source.contains(".")) {
            pattern = UUUU_MM_DD_DOT_HH_MM_SS;
          }
          break;
        case 16:
          if (source.contains("-")) {
            pattern = UUUU_MM_DD_HH_MM;
          } else if (source.contains("/")) {
            pattern = UUUU_MM_DD_SLASH_HH_MM;
          } else if (source.contains(".")) {
            pattern = UUUU_MM_DD_DOT_HH_MM;
          }
          break;
        case 10:
          if (source.contains("-")) {
            pattern = UUUU_MM_DD;
          } else if (source.contains("/")) {
            pattern = UUUU_MM_DD_SLASH;
          } else if (source.contains(".")) {
            pattern = UUUU_MM_DD_DOT;
          }
          break;
        case 7:
          if (source.contains("-")) {
            pattern = UUUU_MM;
          } else if (source.contains("/")) {
            pattern = UUUU_MM_SLASH;
          } else if (source.contains(".")) {
            pattern = UUUU_MM_DOT;
          }
          break;
        case 8:
          pattern = HH_MM_SS;
          break;
        case 5:
          pattern = HH_MM;
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
  public static LocalDateTime parseLocalDateTime(@NonNull String source, @NonNull String... patterns) {
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
  public static LocalDateTime parseLocalDateTime(@NonNull String source) {
    return parseLocalDateTime(source, (ZoneId) null);
  }

  /**
   * 解析为指定时区的 LocalDate 对象
   *
   * @param timeStamp 被解析的时间戳
   * @param zoneId    时区
   * @return 指定时区的 LocalDate 对象
   */
  public static LocalDate parseLocalDate(long timeStamp, ZoneId zoneId) {
    ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timeStamp).atZone(SYSTEM_ZONE_ID);
    if (zoneId != null) {
      zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
    }
    return zonedDateTime.toLocalDate();
  }

  /**
   * 解析为 LocalDate 对象
   *
   * @param timeStamp 时间戳
   * @return LocalDate 对象
   */
  public static LocalDate parseLocalDate(long timeStamp) {
    return parseLocalDate(timeStamp, null);
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
  public static LocalDate parseLocalDate(@NonNull String source, ZoneId zoneId, @NonNull String... patterns) {
    if (StringUtils.isAllBlank(patterns)) {
      return null;
    }
    for (String pattern : patterns) {
      source = convertByPattern(source, pattern);
      LocalDate localDate = LocalDate.parse(source, getDefaultFormatter(pattern));
      if (zoneId != null) {
        return localDate.atTime(LocalTime.MIN).atZone(SYSTEM_ZONE_ID).withZoneSameInstant(zoneId).toLocalDate();
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
  public static LocalDate parseLocalDate(@NonNull String source, ZoneId zoneId) {
    String pattern = null;
    if (source.contains("-") || source.contains("/") || source.contains(".") || source.contains(":")) {
      switch (source.length()) {
        case 10:
          if (source.contains("-")) {
            pattern = UUUU_MM_DD;
          } else if (source.contains("/")) {
            pattern = UUUU_MM_DD_SLASH;
          } else if (source.contains(".")) {
            pattern = UUUU_MM_DD_DOT;
          }
          break;
        case 7:
          if (source.contains("-")) {
            pattern = UUUU_MM;
          } else if (source.contains("/")) {
            pattern = UUUU_MM_SLASH;
          } else if (source.contains(".")) {
            pattern = UUUU_MM_DOT;
          }
          break;
        case 8:
          pattern = HH_MM_SS;
          break;
        case 5:
          pattern = HH_MM;
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
  public static LocalDate parseLocalDate(@NonNull String source, @NonNull String... pattern) {
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
  public static LocalDate parseLocalDate(@NonNull String source) {
    return parseLocalDate(source, (ZoneId) null);
  }

  /**
   * 解析为指定时区的 LocalTime 对象
   *
   * @param timeStamp 时间戳
   * @param zoneId    时区
   * @return 指定时区的 LocalTime 对象
   */
  public static LocalTime parseLocalTime(long timeStamp, ZoneId zoneId) {
    ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timeStamp).atZone(SYSTEM_ZONE_ID);
    if (zoneId != null) {
      zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
    }
    return zonedDateTime.toLocalTime();
  }

  /**
   * 解析为 LocalTime 对象
   *
   * @param timeStamp 时间戳
   * @return LocalTime 对象
   */
  public static LocalTime parseLocalTime(long timeStamp) {
    return parseLocalTime(timeStamp, null);
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
  public static LocalTime parseLocalTime(@NonNull String source, ZoneId zoneId, @NonNull String... patterns) {
    if (StringUtils.isAllBlank(source)) {
      return null;
    }
    for (String pattern : patterns) {
      source = convertByPattern(source, pattern);
      LocalTime localTime = LocalTime.parse(source, getDefaultFormatter(pattern));
      if (zoneId != null) {
        return localTime.atDate(LocalDate.now()).atZone(SYSTEM_ZONE_ID).withZoneSameInstant(zoneId).toLocalTime();
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
  public static LocalTime parseLocalTime(@NonNull String source, ZoneId zoneId) {
    String pattern = null;
    if (source.contains(":")) {
      int srcLen = source.length();
      if (srcLen == 8 || srcLen == 5) {
        switch (srcLen) {
          case 8:
            pattern = HH_MM_SS;
            break;
          case 5:
            pattern = HH_MM;
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
  public static LocalTime parseLocalTime(@NonNull String source, @NonNull String... patterns) {
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
  public static LocalTime parseLocalTime(@NonNull String source) {
    return parseLocalTime(source, (ZoneId) null);
  }

  /**
   * 解析为指定时区的 Date 对象
   *
   * @param timeStamp 时间戳
   * @param zoneId    时区
   * @return 指定时区的 Date 对象
   */
  public static Date parseDate(long timeStamp, ZoneId zoneId) {
    Date date = new Date(timeStamp);
    if (zoneId != null) {
      date = Date.from(date.toInstant().atZone(SYSTEM_ZONE_ID).withZoneSameInstant(zoneId).toInstant());
    }
    return date;
  }

  /**
   * 解析为 Date 对象
   *
   * @param timeStamp 时间戳
   * @return Date 对象
   */
  public static Date parseDate(long timeStamp) {
    return parseDate(timeStamp, null);
  }

  /**
   * 满足任意格式时解析为指定时区的 Date 对象
   *
   * @param source   字符串
   * @param zoneId   时区
   * @param patterns 多个格式
   * @return 指定时区的 Date 对象
   */
  public static Date parseDate(@NonNull String source, ZoneId zoneId, @NonNull String... patterns) {
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
  public static Date parseDate(@NonNull String source, @NonNull ZoneId zoneId) {
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
  public static Date parseDate(@NonNull String source, @NonNull String... patterns) {
    if (StringUtils.isAllBlank(patterns)) {
      return null;
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
  public static Date parseDate(@NonNull String source) {
    LocalDateTime localDateTime = parseLocalDateTime(source);
    if (localDateTime != null) {
      return toDate(localDateTime);
    }
    return null;
  }

  /**
   * 解析为指定时区的时间对象
   *
   * @param timeStamp 时间戳
   * @param zoneId    时区
   * @param clazz     时间类
   * @param <T>       时间类
   * @return 指定时区的时间对象
   */
  public static <T> T parse(long timeStamp, ZoneId zoneId, @NonNull Class<T> clazz) {
    if (clazz.equals(LocalDateTime.class)) {
      return (T) parseLocalDateTime(timeStamp, zoneId);
    } else if (clazz.equals(LocalDate.class)) {
      return (T) parseLocalDate(timeStamp, zoneId);
    } else if (clazz.equals(LocalTime.class)) {
      return (T) parseLocalTime(timeStamp, zoneId);
    } else if (clazz.equals(Date.class)) {
      return (T) parseDate(timeStamp, zoneId);
    }
    return null;
  }

  /**
   * 解析为时间对象
   *
   * @param timeStamp 时间戳
   * @param clazz     时间类
   * @param <T>       时间类
   * @return 时间对象
   */
  public static <T> T parse(long timeStamp, @NonNull Class<T> clazz) {
    return parse(timeStamp, null, clazz);
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
  public static <T> T parse(@NonNull String source, ZoneId zoneId, @NonNull Class<T> clazz, @NonNull String... patterns) {
    if (clazz.equals(LocalDateTime.class)) {
      return (T) parseLocalDateTime(source, zoneId, patterns);
    } else if (clazz.equals(LocalDate.class)) {
      return (T) parseLocalDate(source, zoneId, patterns);
    } else if (clazz.equals(LocalTime.class)) {
      return (T) parseLocalTime(source, zoneId, patterns);
    } else if (clazz.equals(Date.class)) {
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
  public static <T> T parse(@NonNull String source, @NonNull ZoneId zoneId, @NonNull Class<T> clazz) {
    if (clazz.equals(LocalDateTime.class)) {
      return (T) parseLocalDateTime(source, zoneId);
    } else if (clazz.equals(LocalDate.class)) {
      return (T) parseLocalDate(source, zoneId);
    } else if (clazz.equals(LocalTime.class)) {
      return (T) parseLocalTime(source, zoneId);
    } else if (clazz.equals(Date.class)) {
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
  public static <T> T parse(@NonNull String source, @NonNull Class<T> clazz, @NonNull String... patterns) {
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
  public static <T> T parse(@NonNull String source, @NonNull Class<T> clazz) {
    if (clazz.equals(LocalDateTime.class)) {
      return (T) parseLocalDateTime(source);
    } else if (clazz.equals(LocalDate.class)) {
      return (T) parseLocalDate(source);
    } else if (clazz.equals(LocalTime.class)) {
      return (T) parseLocalTime(source);
    } else if (clazz.equals(Date.class)) {
      return (T) parseDate(source);
    }
    return null;
  }

  // /**
  //  * 转换时区
  //  *
  //  * @param localDateTime LocalDateTime 对象
  //  * @param oldZoneId     旧时区
  //  * @param newZoneId     新时区
  //  * @return 转换时区后的 LocalDateTime 对象
  //  */
  // public static LocalDateTime withZoneInstant(@NonNull LocalDateTime localDateTime, @NonNull ZoneId oldZoneId, @NonNull ZoneId newZoneId) {
  //   return localDateTime.atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDateTime();
  // }
  //
  // /**
  //  * 转换时区
  //  *
  //  * @param localDateTime LocalDateTime 对象
  //  * @param newZoneId     新时区
  //  * @return 转换时区后的 LocalDateTime 对象
  //  */
  // public static LocalDateTime withZoneInstant(@NonNull LocalDateTime localDateTime, @NonNull ZoneId newZoneId) {
  //   return withZoneInstant(localDateTime, SYSTEM_ZONE_ID, newZoneId);
  // }
  //
  // /**
  //  * 转换时区
  //  * <p>
  //  * 需要注意的是 LocalDate 不存在时区概念，此处是将 time 补足为 LocalTime.MIN 后再转换为 LocalDate
  //  *
  //  * @param localDate LocalDate 对象
  //  * @param oldZoneId 旧时区
  //  * @param newZoneId 新时区
  //  * @return 转换时区后的 LocalDate 对象
  //  */
  // public static LocalDate withZoneInstant(@NonNull LocalDate localDate, @NonNull ZoneId oldZoneId, @NonNull ZoneId newZoneId) {
  //   return localDate.atStartOfDay().atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDate();
  // }
  //
  // /**
  //  * 转换时区
  //  * <p>
  //  * 需要注意的是 LocalDate 不存在时区概念，此处是将 time 补足为 LocalTime.MIN 后再转换为 LocalDate
  //  *
  //  * @param localDate LocalDate 对象
  //  * @param newZoneId 新时区
  //  * @return 转换时区后的 LocalDate 对象
  //  */
  // public static LocalDate withZoneInstant(@NonNull LocalDate localDate, @NonNull ZoneId newZoneId) {
  //   return withZoneInstant(localDate, SYSTEM_ZONE_ID, newZoneId);
  // }
  //
  // /**
  //  * 转换时区
  //  * <p>
  //  * 需要注意的是 LocalTime 不存在时区概念，此处是将 date 补足为 LocalDate.now() 后再转换为 LocalTime
  //  *
  //  * @param localTime LocalTime 对象
  //  * @param oldZoneId 旧时区
  //  * @param newZoneId 新时区
  //  * @return 转换时区后的 LocalTime 对象
  //  */
  // public static LocalTime withZoneInstant(@NonNull LocalTime localTime, @NonNull ZoneId oldZoneId, @NonNull ZoneId newZoneId) {
  //   return localTime.atDate(LocalDate.of(MIN_YEAR, 1, 1)).atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalTime();
  // }
  //
  // /**
  //  * 转换时区
  //  * <p>
  //  * 需要注意的是 LocalTime 不存在时区概念，此处是将 date 补足为 LocalDate.now() 后再转换为 LocalTime
  //  *
  //  * @param localTime LocalTime 对象
  //  * @param newZoneId 新时区
  //  * @return 转换时区后的 LocalTime 对象
  //  */
  // public static LocalTime withZoneInstant(@NonNull LocalTime localTime, @NonNull ZoneId newZoneId) {
  //   return withZoneInstant(localTime, SYSTEM_ZONE_ID, newZoneId);
  // }

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
  public static <T extends Temporal> T withZoneInstant(@NonNull T temporal, @NonNull ZoneId oldZoneId, @NonNull ZoneId newZoneId) {
    if (temporal instanceof LocalDateTime) {
      return (T) ((LocalDateTime) temporal).atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDateTime();
    } else if (temporal instanceof LocalDate) {
      return (T) ((LocalDate) temporal).atStartOfDay().atZone(oldZoneId).withZoneSameInstant(newZoneId).toLocalDate();
    } else if (temporal instanceof LocalTime) {
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
  public static <T extends Temporal> T withZoneInstant(@NonNull T temporal, @NonNull ZoneId newZoneId) {
    return withZoneInstant(temporal, SYSTEM_ZONE_ID, newZoneId);
  }

  /**
   * 从旧时区转换到新时区
   *
   * @param date      Date 对象
   * @param oldZoneId 旧时区
   * @param newZoneId 新时区
   * @return 转换时区后的 Date 对象
   */
  public static Date withZoneInstant(@NonNull Date date, @NonNull ZoneId oldZoneId, @NonNull ZoneId newZoneId) {
    LocalDateTime localDateTime = withZoneInstant(LocalDateTime.ofInstant(date.toInstant(), SYSTEM_ZONE_ID), oldZoneId, newZoneId);
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
  public static Date withZoneInstant(@NonNull Date date, @NonNull ZoneId newZoneId) {
    return withZoneInstant(date, SYSTEM_ZONE_ID, newZoneId);
  }

  /**
   * 获取当天时间字符串
   *
   * @return 格式为 yyyy-MM-dd 的当天时间字符串
   */
  public static String today() {
    return FORMAT_YYYY_MM_DD.format(new Date());
  }

  /**
   * 获取当前时间字符串
   *
   * @return 格式为 yyyy-MM-dd HH:mm:ss 的当前时间字符串
   */
  public static String now() {
    return FORMAT_YYYY_MM_DD_HH_MM_SS.format(new Date());
  }

  /**
   * 获取指定格式的当前时间字符串
   *
   * @param pattern 格式
   * @return 指定格式的当前时间字符串
   */
  public static String now(@NonNull String pattern) {
    return FastDateFormat.getInstance(pattern).format(new Date());
  }

  /**
   * 获取指定时区的当前时间字符串
   *
   * @param zoneId 时区
   * @return 指定时区的当前时间字符串
   */
  public static String now(@NonNull ZoneId zoneId) {
    return DateTimeFormatter.ofPattern(DateUtils.defaultLocalDateTimePattern).format(ZonedDateTime.now(zoneId));
  }

  /**
   * 获取指定时区和格式的当前时间字符串
   *
   * @param zoneId  时区
   * @param pattern 格式
   * @return 指定时区和格式的当前时间字符串
   */
  public static String now(@NonNull ZoneId zoneId, @NonNull String pattern) {
    return DateTimeFormatter.ofPattern(pattern).format(ZonedDateTime.now(zoneId));
  }

  /**
   * 指定多个时间级别的最小时间
   *
   * @param temporal       时间对象
   * @param temporalFields 多个最小时间的级别
   * @param <T>            时间类
   * @return 时间对象
   */
  public static <T extends Temporal> T min(@NonNull Temporal temporal, @NonNull TemporalField... temporalFields) {
    if (CollectionUtils.isAllEmpty(temporalFields)) {
      return null;
    }
    for (TemporalField temporalField : temporalFields) {
      temporal = temporal.with(temporalField, temporalField.range().getMinimum());
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
  public static <T extends Temporal> T max(@NonNull Temporal temporal, @NonNull TemporalField... temporalFields) {
    if (CollectionUtils.isAllEmpty(temporalFields)) {
      return null;
    }
    for (TemporalField temporalField : temporalFields) {
      long newValue;
      ValueRange valueRange = temporalField.range();
      // 最大值大小不固定，因为月的天最大可能 28 ~ 31 天
      if (!(temporal instanceof LocalTime) && (temporalField.equals(ChronoField.YEAR_OF_ERA)
        || temporalField.equals(ChronoField.ALIGNED_WEEK_OF_MONTH)
        || temporalField.equals(ChronoField.DAY_OF_YEAR)
        || temporalField.equals(ChronoField.DAY_OF_MONTH))) {
        // 获取今天的 2 月份是否只有 28 天
        int dayOfMonth = temporal.with(ChronoField.MONTH_OF_YEAR, 2).with(TemporalAdjusters.lastDayOfMonth()).get(ChronoField.DAY_OF_MONTH);
        if (dayOfMonth == 28) {
          newValue = valueRange.getSmallestMaximum();
        } else if (temporalField.equals(ChronoField.DAY_OF_MONTH)) {
          newValue = dayOfMonth;
        } else {
          newValue = valueRange.getMaximum();
        }
      } else {
        newValue = valueRange.getMaximum();
      }

      temporal = temporal.with(temporalField, newValue);
    }
    return (T) temporal;
  }

  /**
   * 指定多个时间级别的最小时间
   *
   * @param temporal       时间对象
   * @param temporalFields 多个最小时间的级别
   * @return 时间对象
   */
  public static Date minDate(@NonNull Temporal temporal, @NonNull TemporalField... temporalFields) {
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
  public static Date maxDate(@NonNull Temporal temporal, @NonNull TemporalField... temporalFields) {
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
  public static LocalDateTime todayMinTime(ZoneId zoneId, Long addOrSubtractDays) {
    LocalDateTime localDateTime = todayMinTime();
    if (addOrSubtractDays != null && addOrSubtractDays != 0) {
      localDateTime = plusOrMinus(localDateTime, addOrSubtractDays, ChronoUnit.DAYS);
    }
    if (zoneId != null) {
      return localDateTime.atZone(SYSTEM_ZONE_ID).withZoneSameInstant(zoneId).toLocalDateTime();
    }
    return localDateTime;
  }

  /**
   * 获取指定时区的今天开始时间
   *
   * @param zoneId 时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinTime(@NonNull ZoneId zoneId) {
    return todayMinTime(zoneId, null);
  }

  /**
   * 获取指定加减天的今天开始时间
   *
   * @param days 加减天
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMinTime(long days) {
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
  public static String todayMinTimeStr(ZoneId zoneId, @NonNull String pattern, Long addOrSubtractDays) {
    return format(todayMinTime(zoneId, addOrSubtractDays), pattern);
  }

  /**
   * 获取指定时区和格式的今天开始时间字符串
   *
   * @param zoneId  时区
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMinTimeStr(@NonNull ZoneId zoneId, @NonNull String pattern) {
    return todayMinTimeStr(zoneId, pattern, null);
  }

  /**
   * 获取指定格式的今天开始时间字符串
   *
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMinTimeStr(@NonNull String pattern) {
    return todayMinTimeStr(null, pattern, null);
  }

  /**
   * 获取今天开始时间字符串
   *
   * @return 时间字符串
   */
  public static String todayMinTimeStr() {
    return todayMinTimeStr(defaultLocalDateTimePattern);
  }

  /**
   * 获取指定时区和加减天的今天结束时间
   *
   * @param zoneId            时区
   * @param addOrSubtractDays 加减天
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxTime(ZoneId zoneId, Long addOrSubtractDays) {
    LocalDateTime localDateTime = todayMaxTime();
    if (addOrSubtractDays != null && addOrSubtractDays != 0) {
      localDateTime = plusOrMinus(localDateTime, addOrSubtractDays, ChronoUnit.DAYS);
    }
    if (zoneId != null) {
      return localDateTime.atZone(SYSTEM_ZONE_ID).withZoneSameInstant(zoneId).toLocalDateTime();
    }
    return localDateTime;
  }

  /**
   * 获取指定时区的今天结束时间
   *
   * @param zoneId 时区
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxTime(@NonNull ZoneId zoneId) {
    return todayMaxTime(zoneId, null);
  }

  /**
   * 获取指定加减天的今天结束时间
   *
   * @param days 加减天
   * @return LocalDateTime 对象
   */
  public static LocalDateTime todayMaxTime(long days) {
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
  public static String todayMaxTimeStr(ZoneId zoneId, @NonNull String pattern, Long addOrSubtractDays) {
    return format(todayMaxTime(zoneId, addOrSubtractDays), pattern);
  }

  /**
   * 获取指定时区和格式的今天结束时间字符串
   *
   * @param zoneId  时区
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMaxTimeStr(@NonNull ZoneId zoneId, @NonNull String pattern) {
    return todayMaxTimeStr(zoneId, pattern, null);
  }

  /**
   * 获取指定格式的今天结束时间字符串
   *
   * @param pattern 格式
   * @return 时间字符串
   */
  public static String todayMaxTimeStr(@NonNull String pattern) {
    return todayMaxTimeStr(null, pattern, null);
  }

  /**
   * 获取今天结束时间字符串
   *
   * @return 时间字符串
   */
  public static String todayMaxTimeStr() {
    return todayMaxTimeStr(defaultLocalDateTimePattern);
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
  public static <T extends Temporal> T plusOrMinus(@NonNull T temporal, long augendOrMinuend, @NonNull ChronoUnit... chronoUnits) {
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
  public static <T extends Temporal> T plusOrMinus(@NonNull T temporal, long augendOrMinuend) {
    return plusOrMinus(temporal, augendOrMinuend, ChronoUnit.MILLIS);
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
  public static boolean isIntersection(@NonNull LocalDateTime x1, @NonNull LocalDateTime y1, @NonNull LocalDateTime x2, @NonNull LocalDateTime y2) {
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
  public static LocalDateTime[] getIntersection(@NonNull LocalDateTime x1, @NonNull LocalDateTime y1, @NonNull LocalDateTime x2, @NonNull LocalDateTime y2) {
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
  public static LocalDateTime[][] getDifferenceSetsByIntersection(@NonNull LocalDateTime x1, @NonNull LocalDateTime y1, @NonNull LocalDateTime x2, @NonNull LocalDateTime y2, int augendOrMinuendType, long augendOrMinuend, @NonNull ChronoUnit... chronoUnits) {
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
  public static LocalDateTime[][] getDifferenceSetsByIntersection(@NonNull LocalDateTime x1, @NonNull LocalDateTime y1, @NonNull LocalDateTime x2, @NonNull LocalDateTime y2, long augendOrMinuend, @NonNull ChronoUnit... chronoUnits) {
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
  public static LocalDateTime[][] getDifferenceSetsByIntersection(@NonNull LocalDateTime x1, @NonNull LocalDateTime y1, @NonNull LocalDateTime x2, @NonNull LocalDateTime y2) {
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
  public static String[] convertWeeks(@NonNull String weeks, @NonNull String splitRegex) {
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
  public static String[] convertWeeks(@NonNull String weeks) {
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
  public static List<LocalDateTime> getByWeeks(@NonNull String weeks, @NonNull LocalDateTime... times) {
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
  public static List<String> getByRange(@NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime, @NonNull String pattern) {
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
  public static List<String> getByRangeAndWeeks(@NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime, @NonNull String weeks, @NonNull String pattern) {
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
  public static List<LocalDateTime> getByRangeAndWeeks(@NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime, @NonNull String weeks) {
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
