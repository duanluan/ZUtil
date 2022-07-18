package top.zhogjianhao.date;

import lombok.extern.slf4j.Slf4j;
import top.zhogjianhao.date.constant.DateConstant;

import java.time.ZoneId;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * 时间特性，决定着时间工具类细微处的处理方式，“_ALWAYS”结尾的总是生效
 */
@Slf4j
public class DateFeature {

  /**
   * 解析器模式
   */
  protected static ResolverStyle RESOLVER_STYLE = null;
  /**
   * 解析器模式
   */
  protected static ResolverStyle RESOLVER_STYLE_ALWAYS = null;

  public static void set(final ResolverStyle resolverStyle) {
    RESOLVER_STYLE = resolverStyle;
    log.info("the value of RESOLVER_STYLE updated to " + resolverStyle.toString());
  }

  public static void setAlways(final ResolverStyle resolverStyle) {
    RESOLVER_STYLE_ALWAYS = resolverStyle;
    log.info("the value of RESOLVER_STYLE_ALWAYS updated to " + resolverStyle.toString());
  }

  public static ResolverStyle get(final ResolverStyle resolverStyle) {
    if (resolverStyle != null) {
      return resolverStyle;
    } else if (RESOLVER_STYLE_ALWAYS != null) {
      return RESOLVER_STYLE_ALWAYS;
    } else if (RESOLVER_STYLE != null) {
      ResolverStyle resolverStyle1 = RESOLVER_STYLE;
      RESOLVER_STYLE = null;
      return resolverStyle1;
    }
    return null;
  }

  public static ResolverStyle getLazy(final ResolverStyle resolverStyle) {
    if (RESOLVER_STYLE_ALWAYS != null) {
      return RESOLVER_STYLE_ALWAYS;
    } else if (RESOLVER_STYLE != null) {
      ResolverStyle resolverStyle1 = RESOLVER_STYLE;
      RESOLVER_STYLE = null;
      return resolverStyle1;
    }
    return resolverStyle;
  }

  public static ResolverStyle getResolverStyle() {
    if (RESOLVER_STYLE_ALWAYS != null) {
      return RESOLVER_STYLE_ALWAYS;
    } else if (RESOLVER_STYLE != null) {
      ResolverStyle resolverStyle1 = RESOLVER_STYLE;
      RESOLVER_STYLE = null;
      return resolverStyle1;
    }
    return DateConstant.DEFAULT_RESOLVER_STYLE;
  }

  /**
   * 区域，比如月份是中文还是英文
   */
  public static Locale LOCALE = null;
  /**
   * 区域，比如月份是中文还是英文
   */
  public static Locale LOCALE_ALWAYS = null;

  public static void set(final Locale locale) {
    LOCALE = locale;
    log.info("the value of LOCALE updated to " + locale.toString());
  }

  public static void setAlways(final Locale locale) {
    LOCALE_ALWAYS = locale;
    log.info("the value of LOCALE_ALWAYS updated to " + locale.toString());
  }

  public static Locale get(final Locale locale) {
    if (locale != null) {
      return locale;
    } else if (LOCALE_ALWAYS != null) {
      return LOCALE_ALWAYS;
    } else if (LOCALE != null) {
      Locale locale1 = LOCALE;
      LOCALE = null;
      return locale1;
    }
    return null;
  }

  public static Locale getLazy(final Locale locale) {
    if (LOCALE_ALWAYS != null) {
      return LOCALE_ALWAYS;
    } else if (ZONE_ID != null) {
      Locale locale1 = LOCALE;
      LOCALE = null;
      return locale1;
    }
    return locale;
  }

  public static Locale getLocale() {
    if (LOCALE_ALWAYS != null) {
      return LOCALE_ALWAYS;
    } else if (LOCALE != null) {
      Locale locale1 = LOCALE;
      LOCALE = null;
      return locale1;
    }
    return DateConstant.DEFAULT_LOCALE;
  }

  /**
   * 时区
   */
  public static ZoneId ZONE_ID = null;
  /**
   * 时区
   */
  public static ZoneId ZONE_ID_ALWAYS = null;

  public static void set(final ZoneId zoneId) {
    ZONE_ID = zoneId;
    log.info("the value of ZONE_ID updated to " + zoneId.toString());
  }

  public static void setAlways(final ZoneId zoneId) {
    ZONE_ID_ALWAYS = zoneId;
    log.info("the value of ZONE_ID_ALWAYS updated to " + zoneId.toString());
  }

  public static ZoneId get(final ZoneId zoneId) {
    if (zoneId != null) {
      return zoneId;
    } else if (ZONE_ID_ALWAYS != null) {
      return ZONE_ID_ALWAYS;
    } else if (ZONE_ID != null) {
      ZoneId zoneId1 = ZONE_ID;
      ZONE_ID = null;
      return zoneId1;
    }
    return null;
  }

  public static ZoneId getLazy(final ZoneId zoneId) {
    if (ZONE_ID_ALWAYS != null) {
      return ZONE_ID_ALWAYS;
    } else if (ZONE_ID != null) {
      ZoneId zoneId1 = ZONE_ID;
      ZONE_ID = null;
      return zoneId1;
    }
    return zoneId;
  }

  public static ZoneId getZoneId() {
    if (ZONE_ID_ALWAYS != null) {
      return ZONE_ID_ALWAYS;
    } else if (ZONE_ID != null) {
      ZoneId zoneId1 = ZONE_ID;
      ZONE_ID = null;
      return zoneId1;
    }
    return DateConstant.SYSTEM_ZONE_ID;
  }

  /**
   * 最小 Date 年
   */
  public static Long MIN_DATE_YEAR = null;
  /**
   * 最小 Date 年
   */
  public static Long MIN_DATE_YEAR_ALWAYS = null;

  public static void setMinDateYear(final Long minDateYear) {
    MIN_DATE_YEAR = minDateYear;
    log.info("the value of MIN_DATE_YEAR updated to " + minDateYear.toString());
  }

  public static void setMinDateYearAlways(final Long minDateYear) {
    MIN_DATE_YEAR_ALWAYS = minDateYear;
    log.info("the value of MIN_DATE_YEAR_ALWAYS updated to " + minDateYear.toString());
  }

  public static Long getMinDateYear(final Long minDateYear) {
    if (minDateYear != null && minDateYear > 0) {
      return minDateYear;
    } else if (MIN_DATE_YEAR_ALWAYS != null && MIN_DATE_YEAR_ALWAYS > 0) {
      return MIN_DATE_YEAR_ALWAYS;
    } else if (MIN_DATE_YEAR != null && MIN_DATE_YEAR > 0) {
      Long minDateYear1 = MIN_DATE_YEAR;
      MIN_DATE_YEAR = null;
      return minDateYear1;
    }
    return null;
  }

  public static Long getLazyMinDateYear(final Long minDateYear) {
    if (MIN_DATE_YEAR_ALWAYS != null && MIN_DATE_YEAR_ALWAYS > 0) {
      return MIN_DATE_YEAR_ALWAYS;
    } else if (MIN_DATE_YEAR != null && MIN_DATE_YEAR > 0) {
      Long minDateYear1 = MIN_DATE_YEAR;
      MIN_DATE_YEAR = null;
      return minDateYear1;
    } else if (minDateYear != null && minDateYear > 0) {
      return minDateYear;
    }
    return null;
  }

  public static Long getMinDateYear() {
    if (MIN_DATE_YEAR_ALWAYS > 0) {
      return MIN_DATE_YEAR_ALWAYS;
    } else if (MIN_DATE_YEAR > 0) {
      Long minDateYear1 = MIN_DATE_YEAR;
      MIN_DATE_YEAR = null;
      return minDateYear1;
    }
    return DateConstant.DEFAULT_MIN_DATE_YEAR;
  }
}
